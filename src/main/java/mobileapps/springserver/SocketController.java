package mobileapps.springserver;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @MessageMapping("/ws")
    @SendTo("/topic/games")
    public Game send(final Game game) throws Exception {
        return game;
    }

}
