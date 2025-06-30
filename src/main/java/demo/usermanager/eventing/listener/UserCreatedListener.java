package demo.usermanager.eventing.listener;

import demo.usermanager.eventing.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserCreatedListener {

    @EventListener
    @Async
    public void handleUserCreated(UserCreatedEvent event) throws InterruptedException {
        log.info("User created event received: " + event.toString());
        Thread.sleep(5000L);
        log.info("User created event processed");
    }
}
