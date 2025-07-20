package techlab.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.Unsigned;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techlab.service.dto.ProductDTO;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String urlImagen;
    private String categoria;
    private float precio;
    private long stock;

    public ProductEntity(String nombre, String descripcion, String categoria, String urlImagen, float precio, long stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.urlImagen = urlImagen;
        this.precio = precio;
        this.stock = stock;
    }

    public ProductDTO toDTO () {
        /***
         * Devuelve un DTO equivalente de la Entity.
         * @returns
         */
        return new ProductDTO(
                this.id,
                this.nombre,
                this.descripcion,
                this.urlImagen,
                this.categoria,
                this.precio,
                this.stock
        );
    }

}
