package mobileapps.springserver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mobileapps")
public class Controller {

    private final GameRepository repository;

    public Controller(GameRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/games")
    Iterable<Game> all() {
        return repository.findAll();
    }

    @PostMapping("/games")
    public @ResponseBody
    ResponseEntity<?> add(@RequestBody Game game) {
        repository.save(game);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String error(Exception e) {
        System.out.println(e.getLocalizedMessage());
        return e.getLocalizedMessage();
    }
}
