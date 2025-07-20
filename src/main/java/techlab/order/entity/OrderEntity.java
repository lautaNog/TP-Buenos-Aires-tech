package techlab.order.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import techlab.product.entity.ProductEntity;
import techlab.user.entity.UserEntity;

@Entity
@Getter
@Setter
@Table (name = "ORDERS")
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "user_name")
    private String userName;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "product_name")
    private String productName;

    @NonNull
    private long quantity;

    @NonNull
    private float costoTotal;

    public OrderEntity(UserEntity user, String userName, ProductEntity product, String productName, @NonNull long quantity, @NonNull float costoTotal) {
        this.user = user;
        this.userName = userName;
        this.product = product;
        this.productName = productName;
        this.quantity = quantity;
        this.costoTotal = costoTotal;
    }
}
