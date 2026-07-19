package com.minimarket.controller;

import com.minimarket.entity.Producto;
import com.minimarket.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar todos los productos con enlaces HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Producto>> listarProductos() {
        List<Producto> productos = productoService.findAll();
        
        List<EntityModel<Producto>> productosConLinks = productos.stream()
            .map(p -> EntityModel.of(p,
                linkTo(methodOn(ProductoController.class).obtenerProductoPorId(p.getId())).withSelfRel()))
            .collect(Collectors.toList());

        return CollectionModel.of(productosConLinks,
            linkTo(methodOn(ProductoController.class).listarProductos()).withSelfRel());
    }

    @Operation(summary = "Obtener un producto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Producto> model = EntityModel.of(producto,
            linkTo(methodOn(ProductoController.class).obtenerProductoPorId(id)).withSelfRel(),
            linkTo(methodOn(ProductoController.class).listarProductos()).withRel("productos"));

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Guardar un nuevo producto")
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @Operation(summary = "Eliminar un producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto != null) {
            productoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}