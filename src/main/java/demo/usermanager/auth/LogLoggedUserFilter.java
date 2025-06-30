package demo.usermanager.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class LogLoggedUserFilter extends OncePerRequestFilter {
    private final IdentityProvider identityProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String uri = request.getRequestURI();
        final String method = request.getMethod();

        try {
            filterChain.doFilter(request, response);
        } finally {
            final LoggedUser loggedUser = safeGetLoggedUser().orElse(null);
            int status = response.getStatus();
            log.info("User [{}] requested access to [{} {}]. Response was {}", loggedUser == null ? "" : loggedUser.getPreferredUsername(), method, uri, status);
        }
    }

    private Optional<LoggedUser> safeGetLoggedUser() {
        try {
            return Optional.ofNullable(identityProvider.getLoggedUser());
        } catch(NoLoggedUserException e) {
            return Optional.empty();
        }
    }

}
