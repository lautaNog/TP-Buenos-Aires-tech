package techlab.order.service;

import org.hibernate.query.Order;
import org.springframework.stereotype.Service;
import techlab.exceptions.OrderException;
import techlab.exceptions.order.ExcessiveQuantityException;
import techlab.exceptions.order.InvalidCartException;
import techlab.exceptions.order.NotValidQuantityException;
import techlab.exceptions.order.OrderNotFoundException;
import techlab.exceptions.product.ProductNotFoundException;
import techlab.exceptions.user.UserNotFoundException;
import techlab.order.dto.CartDTO;
import techlab.order.dto.OrderDTO;
import techlab.order.entity.OrderEntity;
import techlab.order.repository.OrderRepository;
import techlab.product.entity.ProductEntity;
import techlab.product.respository.ProductRepository;
import techlab.user.dto.UserResponseDTO;
import techlab.user.entity.UserEntity;
import techlab.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    //POST
    public OrderDTO addOrder(OrderDTO order) {
        UserEntity user = this.userRepository.findByUserName(order.getUserName()).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado."));
        ProductEntity product = this.productRepository.findById(order.getProductId()).orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        if (product.getStock() < order.getQuantity()) {
            throw new ExcessiveQuantityException("Stock mayor al solicitado");
        } else if (order.getQuantity() < 1) {
            throw new NotValidQuantityException("Cantidad solicitada menor a la minima (1)");
        }

        product.setStock(product.getStock() - order.getQuantity());
        this.productRepository.save(product);

        float costoTotal = order.getQuantity() * product.getPrecio();
        OrderEntity orderEntity = new OrderEntity(user, user.getUserName(), product, product.getNombre(), order.getQuantity(), costoTotal);
        OrderDTO orderDTO = new OrderDTO(user.getUserName(), product.getId(), product.getNombre(), order.getQuantity(), costoTotal);

        this.orderRepository.save(orderEntity);
        return orderDTO;
    }



    //GET
    public OrderDTO findById(Long id) {
        OrderEntity order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(""));
        return orderToDTO(order);
    }

    public List<OrderDTO> listOrders() {
        List<OrderEntity> orders = this.orderRepository.findAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (OrderEntity order: orders) {
            orderDTOS.add(orderToDTO(order));
        }

        return orderDTOS;
    }

    //PUT
    public OrderDTO updateOrder(Long id, long newQuantity) {
        OrderEntity order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(""));
        ProductEntity product = order.getProduct();

        long stock = product.getStock();
        long oldQuantity = order.getQuantity();

        if (newQuantity < 0 || oldQuantity - newQuantity + stock < 0) {
            throw new NotValidQuantityException("");
        }
        else {
            product.setStock(stock + oldQuantity - newQuantity);
            this.productRepository.save(product);

            order.setQuantity(newQuantity);
            order.setCostoTotal(product.getPrecio() * newQuantity);
            this.orderRepository.save(order);

            return orderToDTO(order);

        }

    }

    //DELETE
    public OrderDTO deleteOrder(long id) {
        OrderEntity order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(""));
        ProductEntity product = order.getProduct();

        //Como se borra el pedido, se cancela, osea, se recupera el stock
        product.setStock(product.getStock() + order.getQuantity());
        this.productRepository.save(product);
        this.orderRepository.delete(order);

        return null;
    }
    // Auxiliares

    private OrderDTO orderToDTO (OrderEntity order) {
        return new OrderDTO(
                order.getUserName(),
                order.getProduct().getId(),
                order.getProduct().getNombre(),
                order.getQuantity(),
                order.getCostoTotal()
        );
    }
}
