package techlab.order.controller;

import jdk.dynalink.linker.LinkerServices;
import org.apache.coyote.Response;
import org.hibernate.query.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techlab.exceptions.order.InvalidCartException;
import techlab.exceptions.order.NotValidQuantityException;
import techlab.exceptions.order.OrderNotFoundException;
import techlab.exceptions.product.ProductNotFoundException;
import techlab.exceptions.user.UserNotFoundException;
import techlab.order.dto.CartDTO;
import techlab.order.dto.OrderDTO;
import techlab.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderControler {

    private final OrderService service;

    public OrderControler(OrderService service) {
        this.service = service;
    }

    @PostMapping("/add")
    private ResponseEntity<OrderDTO> addOrder (@RequestBody(required = true) OrderDTO order) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.addOrder(order));
        }catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ProductNotFoundException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/add/cart")
    private ResponseEntity<CartDTO> addCart (@RequestBody(required = true) CartDTO cart) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.addCart(cart));
        }catch (InvalidCartException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }


    @GetMapping("/id/{id}")
    private ResponseEntity<OrderDTO> findById (@PathVariable(required = true) Long id) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(this.service.findById(id));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((null));
        }
    }

    @GetMapping("/list")
    private ResponseEntity<List<OrderDTO>> listOrders () {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.listOrders());
    }

    @PutMapping("/update")
    private ResponseEntity<OrderDTO> updateOrder (@RequestParam(required = true) long id, @RequestParam(required = true) long quantity){
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.service.updateOrder(id, quantity));
        } catch (NotValidQuantityException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete")
    private ResponseEntity<OrderDTO> deleteOrder (@RequestParam(required = true) long id){
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.service.deleteOrder(id));
        }catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
