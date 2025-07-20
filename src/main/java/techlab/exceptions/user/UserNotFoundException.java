package techlab.exceptions.user;

import techlab.exceptions.UserException;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
