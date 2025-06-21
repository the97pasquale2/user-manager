package demo.usermanager.presentation.user;

import demo.usermanager.business.common.NotFoundException;
import demo.usermanager.business.user.UserCRUDInputValidator;
import demo.usermanager.presentation.common.ListDto;
import demo.usermanager.business.user.UserCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserCRUDInputValidator validator;
    private final UserCRUDService service;

    @GetMapping
    public ResponseEntity<ListDto<UserDto>> get() {
        return ResponseEntity.ok(service.list());
    }

    @PostMapping
    public ResponseEntity<UserDto> post(@RequestBody UserDto user) {
        validator.validateCreate(user);
        var savedUser = service.create(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> putId(@PathVariable String id, @RequestBody UserDto user) {
        validator.validateUpdate(id, user);
        user.setId(id);
        var updatedUser = service.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getId(@PathVariable String id) {
        UserDto userDto = service.findById(id).orElseThrow(() -> new NotFoundException("Error while getting user. User not found."));
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteId(@PathVariable String id) {
        validator.validateDelete(id);
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
