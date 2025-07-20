package techlab.exceptions.product;

import techlab.exceptions.ProductException;
import techlab.exceptions.TechlabException;

public class ProductNotFoundException extends ProductException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
