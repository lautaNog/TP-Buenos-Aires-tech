package techlab.exceptions.order;

import techlab.exceptions.OrderException;

public class NotValidQuantityException extends OrderException {
    public NotValidQuantityException(String message) {
        super(message);
    }
}
