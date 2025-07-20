package techlab.service.exceptions;

public class ProductNotFoundException extends TechlabException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
