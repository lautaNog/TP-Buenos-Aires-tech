package techlab.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table (name = "PRODUCTS")
@NoArgsConstructor
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @Column (name = "url_imagen")
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

}
