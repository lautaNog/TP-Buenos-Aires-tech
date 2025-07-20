package techlab.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techlab.product.entity.ProductEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private long id;
    private String nombre;
    private String descripcion;
    private String urlImagen;
    private String categoria;
    private float precio;
    private long stock;

    public ProductEntity toEntity() {
        return new ProductEntity(
                this.nombre,
                this.descripcion,
                this.categoria,
                this.urlImagen,
                this.precio,
                this.stock
        );
    }
}
