package demo.usermanager.presentation.auth;

import demo.usermanager.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> postLogin(@RequestBody LoginDto login) {
        return ResponseEntity.ok(tokenService.getToken(login.getUsername(), login.getPassword()));
    }
}
