package demo.usermanager.aspect.auth;

import demo.usermanager.auth.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UnauthorizedFieldHidingAspect {

    private final IdentityProvider identityProvider;

    @Around("execution(* demo.usermanager.presentation..*Controller.*(..))")
    public Object afterControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        if (!(result instanceof ResponseEntity<?>))
            return result;

        ResponseEntity<?> response = (ResponseEntity<?>) result;
        Object body = response.getBody();
        if(body == null)
            return result;

        hideUnauthorizedFields(body);

        return result;
    }

    private void hideUnauthorizedFields(Object body) throws IllegalAccessException {
        List<Field> fields = List.of(body.getClass().getDeclaredFields());
        if(body instanceof Collection collection) {
            for(Object item : collection) {
                hideUnauthorizedFields(item);
            }
            return;
        }

        for(Field field : fields) {
            field.setAccessible(true);

            if(mustSearchInside(field)) {
                hideUnauthorizedFields(field.get(body));
            }

            if (!isFieldAuthorized(field, getLoggedUser())) {
                field.set(body, null);
            }
        }
    }

    private static boolean isFieldAuthorized(Field field, Optional<LoggedUser> loggedUser) {
        HideFor annotation = field.getAnnotation(HideFor.class);

        if(annotation == null)
            return true;

        if(loggedUser.isEmpty())
            return false;

        List<String> roleToRestrict = Arrays.asList(annotation.roles());
        boolean containsRoleToRestrict = roleToRestrict.stream().anyMatch(loggedUser.get().getRoles()::contains);
        return !containsRoleToRestrict;
    }

    private static boolean mustSearchInside(Field field) {
        return field.getAnnotation(SearchHideFor.class) != null;
    }

    private Optional<LoggedUser> getLoggedUser() {
        try {
            return Optional.ofNullable(identityProvider.getLoggedUser());
        } catch(NoLoggedUserException e) {
            return Optional.empty();
        }
    }
}
