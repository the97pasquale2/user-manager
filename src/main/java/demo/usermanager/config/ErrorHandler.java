package demo.usermanager.config;

import demo.usermanager.business.common.NotFoundException;
import demo.usermanager.presentation.error.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(404).body(
                ErrorDto.builder()
                        .status(404)
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(
            ErrorDto.builder()
                .status(400)
                .message(e.getMessage())
                .build()
        );
    }
}
