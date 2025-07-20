package techlab.service.service;

import org.springframework.stereotype.Service;
import techlab.service.dto.ProductDTO;
import techlab.service.entity.ProductEntity;
import techlab.service.exceptions.InvalidPriceException;
import techlab.service.exceptions.InvalidStockException;
import techlab.service.exceptions.ProductNotFoundException;
import techlab.service.respository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // GET
    public List<ProductDTO> findByName(String nombreBusqueda) {
        List<ProductEntity> products = repository.findByNombre(nombreBusqueda);

        if (products.isEmpty()) {
            throw new ProductNotFoundException("No fue encontrado el producto con el nombre '" + nombreBusqueda + "'.");
        } else {
            List<ProductDTO> foundProducts = new ArrayList<>();

            for (ProductEntity product : products) {
                ProductDTO productDTO = new ProductDTO();

                productDTO.setId(product.getId());
                productDTO.setNombre(product.getNombre());
                productDTO.setDescripcion(product.getDescripcion());
                productDTO.setUrlImagen(product.getUrlImagen());
                productDTO.setCategoria(product.getCategoria());
                productDTO.setPrecio(product.getPrecio());
                productDTO.setStock(product.getStock());


                foundProducts.add(productDTO);
            }
            return foundProducts;
        }
    }

    //GET
    public ProductDTO getById(Long id) {
        Optional<ProductEntity> product = this.repository.findById(id);

        if (product.isEmpty()) {
            throw new ProductNotFoundException("Producto con ID " + String.valueOf(id) + "No encontrado");
        } else {
            ProductEntity productGot = product.get();
            return new ProductDTO(productGot.getId(), productGot.getNombre(),
                    productGot.getDescripcion(), productGot.getUrlImagen(),
                    productGot.getCategoria(), productGot.getPrecio(), productGot.getStock());

        }
    }

    // GET
    public List<ProductDTO> listProducts() {
        List<ProductEntity> products = this.repository.findAll();
        List<ProductDTO> productsDTO = new ArrayList<>();

        for (ProductEntity product : products) {
            productsDTO.add(product.toDTO());
        }

        return productsDTO;
    }

    //POST
    @Deprecated
    public ProductDTO addProduct(ProductDTO product) {
        if (product.getStock() < 0) {
            throw new InvalidStockException("Stock negativo.");
        } else if (product.getPrecio() < 0) {
            throw new InvalidPriceException("Precio negativo.");
        } else {
            ProductEntity productToAdd = new ProductEntity();
            productToAdd.setNombre(product.getNombre());
            productToAdd.setCategoria(product.getCategoria());
            productToAdd.setPrecio(product.getPrecio());
            productToAdd.setStock(product.getStock());
            productToAdd.setDescripcion(product.getDescripcion());
            productToAdd.setUrlImagen(product.getUrlImagen());


            this.repository.save(productToAdd);
            product.setId(productToAdd.getId());
            return product;
        }

    }

    //POST
    public List<ProductDTO> addProducts(List<ProductDTO> products) {


        for (ProductDTO product : products) {

            if (product.getStock() < 0) {
                throw new InvalidStockException("");
            } else if (product.getPrecio() < 0) {
                throw new InvalidPriceException("");
            } else {
                ProductEntity productEntity = product.toEntity();
                product.setId(productEntity.getId());
                this.repository.save(productEntity);
            }
        }
        return products;
    }

    //PUT
    public ProductDTO updateProduct(Long id, ProductDTO productUpdate) {
        if (productUpdate.getPrecio() < 0) {
            throw new InvalidPriceException("");
        } else if (productUpdate.getStock() < 0) {
            throw new InvalidStockException("");
        } else {
            ProductEntity product = this.repository.findById(id).orElseThrow(() -> new ProductNotFoundException(""));

            product.setDescripcion(productUpdate.getDescripcion());
            product.setCategoria(productUpdate.getCategoria());
            product.setPrecio(productUpdate.getPrecio());
            product.setNombre(productUpdate.getNombre());
            product.setUrlImagen(productUpdate.getUrlImagen());
            product.setStock(productUpdate.getStock());

            this.repository.save(product);
            productUpdate.setId(product.getId());
            return productUpdate;


        }
    }

    //DELETE
    public ProductDTO deleteById(Long id) {
        ProductEntity product = this.repository.findById(id).orElseThrow(() -> new ProductNotFoundException(""));
        this.repository.deleteById(product.getId());

        return new ProductDTO(product.getId(), product.getNombre(),
                product.getDescripcion(), product.getUrlImagen(),
                product.getCategoria(), product.getPrecio(),
                product.getStock());
    }


}