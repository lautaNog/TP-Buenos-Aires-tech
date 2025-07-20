package techlab.exceptions.user;

import techlab.exceptions.UserException;

public class ExisitingUserException extends UserException {
    public ExisitingUserException(String message) {
        super(message);
    }
}
