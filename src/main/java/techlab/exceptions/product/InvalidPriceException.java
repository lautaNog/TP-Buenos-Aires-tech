package techlab.exceptions.product;

import techlab.exceptions.ProductException;
import techlab.exceptions.TechlabException;

public class InvalidPriceException extends ProductException {
    public InvalidPriceException(String message) {
        super(message);
    }
}
