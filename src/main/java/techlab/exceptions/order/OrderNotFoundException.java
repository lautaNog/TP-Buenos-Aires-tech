package techlab.exceptions.order;

import techlab.exceptions.OrderException;

public class OrderNotFoundException extends OrderException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
