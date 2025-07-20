package techlab.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartDTO {

    @NonNull
    private List<OrderDTO> orders;
}
