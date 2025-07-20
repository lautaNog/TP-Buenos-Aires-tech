package techlab.order.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NonNull
    private String userName;
    @NonNull
    private Long productId;
    private String productName;
    @NonNull
    private long quantity;
    private float costoTotal;
}
