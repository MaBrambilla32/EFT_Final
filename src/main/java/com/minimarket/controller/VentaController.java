package com.minimarket.controller;

import com.minimarket.entity.Venta;
import com.minimarket.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Operation(summary = "Listar todas las ventas (Solo Admins y Cajeros)")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO')")
    public CollectionModel<EntityModel<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.findAll();
        
        List<EntityModel<Venta>> ventasConLinks = ventas.stream()
            .map(v -> EntityModel.of(v,
                linkTo(methodOn(VentaController.class).obtenerVentaPorId(v.getId())).withSelfRel()))
            .collect(Collectors.toList());

        return CollectionModel.of(ventasConLinks,
            linkTo(methodOn(VentaController.class).listarVentas()).withSelfRel());
    }

    @Operation(summary = "Obtener detalle de venta por ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO')")
    public ResponseEntity<EntityModel<Venta>> obtenerVentaPorId(@PathVariable Long id) {
        Venta venta = ventaService.findById(id);
        
        if (venta == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Venta> model = EntityModel.of(venta,
            linkTo(methodOn(VentaController.class).obtenerVentaPorId(id)).withSelfRel(),
            linkTo(methodOn(VentaController.class).listarVentas()).withRel("ventas"));

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Registrar una nueva venta")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO')")
    public Venta guardarVenta(@RequestBody Venta venta) {
        return ventaService.save(venta);
    }
}