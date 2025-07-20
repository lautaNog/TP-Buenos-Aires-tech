package techlab.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techlab.service.dto.ProductDTO;
import techlab.service.exceptions.InvalidPriceException;
import techlab.service.exceptions.InvalidStockException;
import techlab.service.exceptions.ProductNotFoundException;
import techlab.service.service.ProductService;

import java.util.List;

@RestController
@RequestMapping ("/producto")
public class ProductController {

    private ProductService service;

    public ProductController (ProductService service){
        this.service = service;
    }


    @GetMapping("/find")
    public ResponseEntity<List<ProductDTO>> findByName(@RequestParam String name){

        /***
         * Devuelve una lista de todos los productos con el nombre a buscar.
         * @param nombreBusqueda Nombre del/os producto/s a buscar.
         **/

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.service.findByName(name));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/id")
    public ResponseEntity<ProductDTO> findById (@RequestParam Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
        }
        catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> listProducts () {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.listProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<List<ProductDTO>> addProducts (@RequestBody List<ProductDTO> products) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.service.addProducts(products));
        }catch (Exception e) {
            //Cuando se le dio un stock o precio invalido
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct (@RequestParam Long id, @RequestBody ProductDTO productUpdate) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.service.updateProduct(id, productUpdate));
        }
        catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (InvalidPriceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (InvalidStockException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ProductDTO> deleteById (@RequestParam Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.service.deleteById(id));
        }
        catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
