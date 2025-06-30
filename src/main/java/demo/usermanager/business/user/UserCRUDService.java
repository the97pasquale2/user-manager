package demo.usermanager.business.user;

import demo.usermanager.eventing.event.UserCreatedEvent;
import demo.usermanager.model.role.RoleRepository;
import demo.usermanager.model.user.User;
import demo.usermanager.model.user.UserRepository;
import demo.usermanager.presentation.common.ListDto;
import demo.usermanager.presentation.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCRUDService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher publisher;

    public UserDto create(UserDto user) {
        User entity = mapToEntity(user);
        entity = userRepository.save(entity);

        publisher.publishEvent(new UserCreatedEvent(entity));
        return mapToDto(entity);
    }

    public UserDto update(UserDto user) {
        User entity = mapToEntity(user);
        entity = userRepository.save(entity);
        return mapToDto(entity);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public Optional<UserDto> findById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(new UserMapper()::toDto);
    }

    private User mapToEntity(UserDto user) {
        return new UserMapper().setRoleRepository(roleRepository).toEntity(user);
    }

    private UserDto mapToDto(User user) {
        return new UserMapper().setRoleRepository(roleRepository).toDto(user);
    }

    public ListDto<UserDto> list() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        List<UserDto> userDtos = users.stream().map(new UserMapper()::toDto).toList();
        return ListDto.<UserDto>builder().list(userDtos).build();
    }

}
