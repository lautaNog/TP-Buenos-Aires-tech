package techlab.exceptions.order;

import techlab.exceptions.OrderException;

public class ExcessiveQuantityException extends OrderException {
    public ExcessiveQuantityException(String message) {
        super(message);
    }
}
