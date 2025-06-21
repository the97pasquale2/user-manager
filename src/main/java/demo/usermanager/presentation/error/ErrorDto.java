package demo.usermanager.presentation.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    int status;
    String message;
}
