package mobileapps.springserver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(path = "/mobileapps")
public class Controller {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public Controller(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/sign-up")
    public @ResponseBody
    ResponseEntity<?> signUp(@RequestBody User user) {
        Optional<User> existing = userRepository.findById(user.getEmail());
        if (!existing.isPresent()) {
            userRepository.save(user);
            System.out.println("Added - " + user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            System.out.println("User with email " + user.getEmail() + " already exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sign-in")
    public @ResponseBody
    ResponseEntity<?> signIn(@RequestBody User user) {
        Optional<User> existing = userRepository.findById(user.getEmail());
        if (!existing.isPresent() || !user.getPassword().equals(existing.get().getPassword())) {
            System.out.println("User with email " + user.getEmail() + " not found / invalid password.");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            System.out.println("User with email " + user.getEmail() + " signed in.");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/games")
    Iterable<Game> all() {
        Iterable<Game> all = gameRepository.findAll();
        System.out.println("Retrieving " + ((Collection<?>) all).size() + " items");
        return all;
    }

    @PostMapping("/add-game")
    public @ResponseBody
    ResponseEntity<?> add(@RequestBody Game game) {
        gameRepository.save(game);
        System.out.println("Added - " + game);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String error(Exception e) {
        System.out.println(e.getLocalizedMessage());
        return e.getLocalizedMessage();
    }
}
