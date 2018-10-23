package Wolox.training.controllers;

import Wolox.training.DAO.UserDAO;
import Wolox.training.exceptions.*;
import Wolox.training.models.Book;
import Wolox.training.models.Role;
import Wolox.training.models.User;
import Wolox.training.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Arrays;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    public UserController() {
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    // Create
    @RequestMapping("/create/**")
    @PostMapping("/users/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    // Read
    @GetMapping("/view")
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/view/{id}")
    public User findById(@PathVariable int id) throws UserDoesNotExistException {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
    }

    @GetMapping(value = "/view/filter")
    public List<User> findByBirthdayBetweenAndUsernameContaining(@RequestParam String birthday1,
                                                                 @RequestParam String birthday2,
                                                                 @RequestParam String username) {
        return userRepository.findByBirthdayBetweenAndUsernameContainingAllIgnoreCase(LocalDate.parse(birthday1),
                                                                                      LocalDate.parse(birthday2),
                                                                                      username);
    }

    @GetMapping("/view/{id}/library")
    public Iterable viewLibrary(@PathVariable int id) throws UserDoesNotExistException {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"))
                .getLibrary();
    }

    // Update methods
    @PutMapping("/view/setName/{id}")
    public User updateName(@RequestParam (name = "name") String name, @PathVariable int id) throws UserDoesNotExistException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
        user.setName(name);
        return userRepository.save(user);
    }

    @PutMapping("/view/setUsername/{id}")
    public User updateUsername(@RequestParam (name = "username") String username, @PathVariable int id) throws UserDoesNotExistException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
        user.setUsername(username);
        return userRepository.save(user);
    }

    @PutMapping("/view/setBirthday/{id}")
    public User updateBirthday(@RequestParam (name = "birthday") LocalDate birthday, @PathVariable int id) throws UserDoesNotExistException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
        user.setBirthday(birthday);
        return userRepository.save(user);
    }

    // Delete
    @DeleteMapping("/view/{id}")
    public void delete(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/addBook/{id}")
    public void addBookToLibrary(@PathVariable int id, @RequestBody Book book) throws BookAlreadyOwnedException, UserDoesNotExistException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
        if (user.hasBook(book)) {
            throw new BookAlreadyOwnedException("The user already has this book in their library");
        }
        user.addBookToLibrary(book);
        userRepository.save(user);
    }

    @PutMapping("/removeBook/{id}")
    public void removeBookFromLibrary(@PathVariable int id, @RequestBody Book book) throws UserDoesNotExistException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("The user does not exist"));
        user.removeBookFromLibrary(book);
    }
  
    private boolean usernameExists(String username) {
        return this.userRepository.findByUsername(username) != null;
    }

    public User registerNewUserAccount(UserDAO userDAO) throws UserAlreadyExistsException {
        if (usernameExists(userDAO.getUsername())) {
            throw new UserAlreadyExistsException("There is an account with that username");
        }
        User user = new User(userDAO);
        user.setPassword(passwordEncoder.encode(userDAO.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("USER_ROLE").get()));
        return userRepository.save(user);
    }

    @PutMapping("/view/setPassword/{id}")
    public User changePassword(@RequestParam (name = "newPassword") String newPassword,
                               @RequestParam (name = "oldPassword") String oldPassword,
                               @PathVariable int id) throws BookDoesNotExistException, InvalidIdentityException {
        User user = userRepository.findById(id).orElseThrow(() -> new BookDoesNotExistException("The user does not exist"));
        if (!oldPassword.equals(user.getPassword())) {
            throw new InvalidIdentityException("Unable to validate identity for changing password");
        }
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @GetMapping("/currentUser")
    public String getCurrentlyAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
