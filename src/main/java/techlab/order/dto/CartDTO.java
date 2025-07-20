package techlab.order.dto;

import lombok.*;
import techlab.product.dto.ProductDTO;
import techlab.product.entity.ProductEntity;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    @NonNull
    private long userId;
    private String userName;

    @NonNull
    private List<SimplifiedOrder> cart;
}
