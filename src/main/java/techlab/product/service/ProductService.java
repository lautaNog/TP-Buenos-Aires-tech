package techlab.product.service;

import org.springframework.stereotype.Service;
import techlab.product.dto.ProductDTO;
import techlab.product.entity.ProductEntity;
import techlab.exceptions.product.InvalidPriceException;
import techlab.exceptions.product.InvalidStockException;
import techlab.exceptions.product.ProductNotFoundException;
import techlab.product.respository.ProductRepository;

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
        ProductEntity product = this.repository.findById(id).orElseThrow(() -> new ProductNotFoundException(""));
        return toDTO(product);


    }

    // GET
    public List<ProductDTO> listProducts() {
        List<ProductEntity> products = this.repository.findAll();
        List<ProductDTO> productsDTO = new ArrayList<>();

        for (ProductEntity product : products) {
            productsDTO.add(toDTO(product));
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

        List<ProductEntity> productEntities = new ArrayList<>();

        for (ProductDTO productDTO: products) {
            if (validProductDTO(productDTO)) {
                productEntities.add(productDTO.toEntity());
                this.repository.saveAll(productEntities);
            }
        }
        return null;
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


    //Auxiliares
    private boolean validProductDTO (ProductDTO productDTO) {
        try {
            if (productDTO.getPrecio() < 0) {
                throw new InvalidPriceException("");
            } else if (productDTO.getStock() < 0.0) {
                throw new InvalidStockException("");
            }else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public ProductDTO toDTO (ProductEntity productEntity) {
        return new ProductDTO(
                productEntity.getId(),
                productEntity.getNombre(),
                productEntity.getDescripcion(),
                productEntity.getUrlImagen(),
                productEntity.getCategoria(),
                productEntity.getPrecio(),
                productEntity.getStock()
        );
    }


}