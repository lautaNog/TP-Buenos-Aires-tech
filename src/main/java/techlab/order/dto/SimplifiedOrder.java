package techlab.order.dto;

import lombok.*;

@Getter
@Setter
@NonNull
@NoArgsConstructor
@AllArgsConstructor
public class SimplifiedOrder {
    private Long productId;
    private long quantity;
}
