package techlab.exceptions.product;

import techlab.exceptions.ProductException;
import techlab.exceptions.TechlabException;

public class InvalidStockException extends ProductException {
    public InvalidStockException(String message) {
        super(message);
    }
}
