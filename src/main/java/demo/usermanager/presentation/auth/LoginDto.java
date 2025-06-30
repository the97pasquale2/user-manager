package demo.usermanager.presentation.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginDto {
    String username;
    String password;
}
