package techlab.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {

    /***
     * Clase usada para unicamente el registro de un nuevo usuario.
     */
    private String userName;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private int phone;



}
