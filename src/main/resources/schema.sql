CREATE TABLE `Product_Entity` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    descripcion TEXT,
    urlImagen VARCHAR(512),
    categoria VARCHAR(100),
    precio INT,
    stock BIGINT
);

INSERT INTO `Product_Entity` (name, descripcion, urlImagen, categoria, precio, stock) VALUES
('Camisa', 'Camiseta de alta calidad para entrenamientos intensos.', 'https://example.com/img/camiseta.jpg', 'Ropa Deportiva', 3500, 120),
('Auriculares', 'Auriculares con cancelación de ruido y batería de larga duración.', 'https://example.com/img/auriculares.jpg', 'Electrónica', 15000, 50),
('Botella', 'Botella térmica de acero inoxidable, mantiene la temperatura por 12h.', 'https://example.com/img/botella.jpg', 'Accesorios', 4200, 200);
