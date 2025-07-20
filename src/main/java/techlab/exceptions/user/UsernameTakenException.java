package techlab.exceptions.user;

import techlab.exceptions.UserException;

public class UsernameTakenException extends UserException {
    public UsernameTakenException(String message) {
        super(message);
    }
}
