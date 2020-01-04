package mobileapps.springserver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/mobileapps")
public class Controller {

    private final GameRepository repository;
    private final SocketController controller;

    public Controller(GameRepository repository, SocketController controller) {
        this.repository = repository;
        this.controller = controller;
    }


    @GetMapping("/games")
    Iterable<Game> all() {
        Iterable<Game> all = repository.findAll();
        System.out.println("Retrieving " + ((Collection<?>) all).size() + "items");
        return all;
    }

    @PostMapping("/games")
    public @ResponseBody
    ResponseEntity<?> add(@RequestBody Game game) {
        repository.save(game);
        System.out.println("Added - " + game);
        try {
            System.out.println("WebSocket sending - " + controller.send(game));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String error(Exception e) {
        System.out.println(e.getLocalizedMessage());
        return e.getLocalizedMessage();
    }
}
