package techlab.user.controller;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import techlab.exceptions.user.UserNotFoundException;
import techlab.user.dto.UserRegisterDTO;
import techlab.user.dto.UserResponseDTO;
import techlab.user.service.UserService;
import techlab.exceptions.user.ExisitingUserException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUser (@RequestBody UserRegisterDTO userForm){
        /***
         * Metodo que recibe un formulario de registro. Si el nombre de usuario no esta tomado, el numero de telegono no
         * esta registrado, se registra al nuevo usuario.
         * @param userFrom
         */

        try
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.addUser(userForm));
        }
        catch (ExisitingUserException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken or phone number already registrated.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchUsers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false)  String username,
            @RequestParam(required = false) String name) {
        /**
         * Devuelve una lista de los usuarios que cumplen aunque sea una de esas condiciones.
         */

        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(this.service.searchUsers(id, username, name));
        }catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    @GetMapping ("/list")
    public ResponseEntity<List<UserResponseDTO>> listUsers () {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.listUsers());
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser (@RequestParam(required = true)Long id, @RequestBody(required = true) UserRegisterDTO userForm) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.service.updateUser(id, userForm));
        }catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<UserResponseDTO> deleteUser (@RequestParam(required = true) Long id){
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.service.deleteUser(id));
        }
        catch (UserNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
