package demo.usermanager.business.user;

import demo.usermanager.business.common.EntityMapper;
import demo.usermanager.model.role.Role;
import demo.usermanager.model.role.RoleRepository;
import demo.usermanager.model.user.User;
import demo.usermanager.presentation.user.UserDto;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Optional;

@Accessors(chain = true)
public class UserMapper implements EntityMapper<UserDto, User> {

    @Setter
    RoleRepository roleRepository;

    @Override
    public UserDto toDto(User entity) {
        if (entity == null)
            return null;

        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .name(entity.getName())
                .email(entity.getEmail())
                .surname(entity.getSurname())
                .taxCode(entity.getTaxCode())
                .roles(
                        entity.getRoles() == null ? null :
                        entity.getRoles()
                                .stream()
                                .map(role -> UserDto.Role.fromString(role.getName()))
                                .toList()
                )
                .build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if(userDto == null)
            return null;

        List<Role> rolesFromDb = userDto.getRoles() == null ? null :
                userDto.getRoles().stream()
                        .map(UserDto.Role::toString)
                        .map(roleRepository::findByName)
                        .map(Optional::get)
                        .toList();

        return User
                .builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .surname(userDto.getSurname())
                .taxCode(userDto.getTaxCode())
                .roles(rolesFromDb)
                .build();
    }
}
