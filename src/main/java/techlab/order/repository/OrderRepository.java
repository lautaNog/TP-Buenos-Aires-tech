package techlab.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techlab.order.entity.OrderEntity;
import techlab.user.entity.UserEntity;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <OrderEntity, Long> {
    List<OrderEntity> findByUser(UserEntity user);
}
