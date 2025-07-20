package techlab.user.service;

import org.springframework.stereotype.Service;
import techlab.exceptions.user.ExisitingUserException;
import techlab.exceptions.user.UserNotFoundException;
import techlab.exceptions.user.UsernameTakenException;
import techlab.user.dto.UserRegisterDTO;
import techlab.user.dto.UserResponseDTO;
import techlab.user.entity.UserEntity;
import techlab.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    //POST
    public String addUser(UserRegisterDTO userForm) {
        List<UserEntity> users = this.repository.findByUserNameOrPhone(userForm.getUserName(), userForm.getPhone());

        //Si hay alguno en la lista, entoces no se va a poder registrar
        if (!(users.isEmpty())) {
            throw new ExisitingUserException("");
        }
        else {
            UserEntity newUser = new UserEntity(userForm.getUserName(), userForm.getName(), userForm.getLastname(), userForm.getEmail(), userForm.getPassword(), userForm.getPhone());
            this.repository.save(newUser);
            return "Registration succesful.";
        }
    }

    //GET
    public List<UserResponseDTO> searchUsers(Long id, String username, String name) {
        List<UserEntity> users = this.repository.findByIdOrUserNameOrName(id, username, name);

        if (users.isEmpty()) {
            throw new UserNotFoundException("");
        }
        else {
            List<UserResponseDTO> foundUsers = new ArrayList<>();

            for (UserEntity user: users)
            {
                foundUsers.add(toDTO(user));
            }

            return foundUsers;
        }
    }

    public List<UserResponseDTO> listUsers() {
        List<UserEntity> users = this.repository.findAll();
        List<UserResponseDTO> usersDTOs = new ArrayList<>();

        try{
            for (UserEntity user : users) {
                usersDTOs.add(toDTO(user));
            }
            return usersDTOs;
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }

    //PUT
    public UserResponseDTO updateUser(Long id, UserRegisterDTO userForm) {
        UserEntity foundUser = this.repository.findById(String.valueOf(id)).orElseThrow(() -> new UserNotFoundException(""));

        if (this.repository.existsByUserName(userForm.getUserName()) &&
                !(Objects.equals(foundUser.getUserName(), userForm.getUserName()))) {
            throw new UsernameTakenException("");
        }
        else {
            foundUser.setUserName(userForm.getUserName());
            foundUser.setName(userForm.getName());
            foundUser.setLastname(userForm.getLastname());
            foundUser.setEmail(userForm.getEmail());
            foundUser.setPassword(userForm.getPassword());
            foundUser.setPhone(userForm.getPhone());

            this.repository.save(foundUser);

            return new UserResponseDTO(
                    foundUser.getUserName(),
                    foundUser.getName(),
                    foundUser.getLastname(),
                    foundUser.getEmail()
            );
        }
    }

    //DELETE
    public UserResponseDTO deleteUser(Long id) {
        UserEntity user = this.repository.findById(String.valueOf(id)).orElseThrow(()-> new UserNotFoundException(""));
        UserResponseDTO userDTO = new UserResponseDTO(
                user.getUserName(),
                user.getName(),
                user.getLastname(),
                user.getEmail()
        );
        this.repository.deleteById(String.valueOf(id));

        return userDTO;

    }

    private UserResponseDTO toDTO(UserEntity user) {
        return new UserResponseDTO(
                user.getUserName(),
                user.getName(),
                user.getLastname(),
                user.getEmail());
    }
}
