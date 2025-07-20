package techlab.exceptions.order;

import techlab.exceptions.OrderException;

public class InvalidCartException extends OrderException {
    public InvalidCartException(String message) {
        super(message);
    }
}
