package techlab.user.service;

import org.springframework.stereotype.Service;
import techlab.exceptions.user.ExisitingUserException;
import techlab.exceptions.user.UserNotFoundException;
import techlab.exceptions.user.UsernameTakenException;
import techlab.order.dto.OrderDTO;
import techlab.order.entity.OrderEntity;
import techlab.order.repository.OrderRepository;
import techlab.user.dto.UserRegisterDTO;
import techlab.user.dto.UserResponseDTO;
import techlab.user.entity.UserEntity;
import techlab.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }


    //POST
    public String addUser(UserRegisterDTO userForm) {
        List<UserEntity> users = this.userRepository.findByUserNameOrPhone(userForm.getUserName(), userForm.getPhone());

        //Si hay alguno en la lista, entoces no se va a poder registrar
        if (!(users.isEmpty())) {
            throw new ExisitingUserException("");
        }
        else {
            UserEntity newUser = new UserEntity(userForm.getUserName(), userForm.getName(), userForm.getLastname(), userForm.getEmail(), userForm.getPassword(), userForm.getPhone());
            this.userRepository.save(newUser);
            return "Registration succesful.";
        }
    }

    //GET
    public List<UserResponseDTO> searchUsers(Long id, String username, String name) {
        List<UserEntity> users = this.userRepository.findByIdOrUserNameOrName(id, username, name);

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
        List<UserEntity> users = this.userRepository.findAll();
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
        UserEntity foundUser = this.userRepository.findById(String.valueOf(id)).orElseThrow(() -> new UserNotFoundException(""));

        if (this.userRepository.existsByUserName(userForm.getUserName()) &&
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

            this.userRepository.save(foundUser);

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
        UserEntity user = this.userRepository.findById(String.valueOf(id)).orElseThrow(()-> new UserNotFoundException(""));
        UserResponseDTO userDTO = new UserResponseDTO(
                user.getUserName(),
                user.getName(),
                user.getLastname(),
                user.getEmail()
        );
        this.userRepository.deleteById(String.valueOf(id));

        return userDTO;

    }

    private UserResponseDTO toDTO(UserEntity user) {
        return new UserResponseDTO(
                user.getUserName(),
                user.getName(),
                user.getLastname(),
                user.getEmail());
    }

    public List<OrderDTO> listOrders(long id) {
        UserEntity user = this.userRepository.findById(String.valueOf(id)).orElseThrow(() -> new UserNotFoundException(""));
        List<OrderEntity> orderEntities = this.orderRepository.findByUser(user);

        if (orderEntities.isEmpty()) {
            throw new RuntimeException("");
        }
        else{
            List<OrderDTO> orderDTOS = new ArrayList<>();
            for (OrderEntity orderEntity: orderEntities) {
                orderDTOS.add(new OrderDTO(
                        user.getUserName(),
                        orderEntity.getProduct().getId(),
                        orderEntity.getProduct().getNombre(),
                        orderEntity.getQuantity(),
                        orderEntity.getCostoTotal()));
            }
            return orderDTOS;
        }
    }
}
