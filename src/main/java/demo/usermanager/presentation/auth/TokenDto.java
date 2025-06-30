package demo.usermanager.presentation.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    String token;
}
