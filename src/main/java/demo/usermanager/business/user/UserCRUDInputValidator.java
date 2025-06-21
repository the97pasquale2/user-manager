package demo.usermanager.business.user;

import demo.usermanager.business.common.NotFoundException;
import demo.usermanager.model.user.UserRepository;
import demo.usermanager.presentation.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserCRUDInputValidator {
    final String PREFIX = "Error while validating input. ";

    final UserRepository userRepository;;

    public void validateCreate(UserDto user) {
        assertNull(user.getId(), PREFIX + "Id must be null.");
        assertEmailDoesNotExist(user.getEmail(), PREFIX + "Email already exists.");
    }

    public void validateUpdate(String id, UserDto user) {
        if(user.getId() != null)
            assertEquals(id, user.getId(), PREFIX + "Ids don't match. Id in path variable must be equal to id in body request");
        userRepository.findById(id).orElseThrow(() -> new NotFoundException(PREFIX + "User not found."));
        assertEmailIsNotChanged(id, user, PREFIX + "Cannot change email");
    }

    public void validateDelete(String id) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException(PREFIX + "User not found."));
    }

    private void assertEmailIsNotChanged(String id, UserDto user, String message) {
        final String emailInDb = userRepository.findById(id).orElseThrow().getEmail();
        if(!user.getEmail().equals(emailInDb)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void assertEmailDoesNotExist(String email, String message) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException(message);
        });
    }

    public void assertEquals(Object expected, Object actual, String message) {
        if(!expected.equals(actual)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void assertNull(Object o, String message) {
        if(o != null) {
            throw new IllegalArgumentException(message);
        }
    }
}
