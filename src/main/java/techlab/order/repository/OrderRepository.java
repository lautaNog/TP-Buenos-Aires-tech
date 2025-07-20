package techlab.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techlab.order.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository <OrderEntity, Long> {
}
