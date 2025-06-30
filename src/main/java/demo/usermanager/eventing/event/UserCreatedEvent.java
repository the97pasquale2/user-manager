package demo.usermanager.eventing.event;

import demo.usermanager.model.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreatedEvent extends ApplicationEvent {
    private final User user;

    public UserCreatedEvent(User user) {
        super(user);
        this.user = user;
    }
}
