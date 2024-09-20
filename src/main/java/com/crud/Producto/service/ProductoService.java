package com.crud.Producto.service;

import com.crud.Producto.entity.Producto;
import com.crud.Producto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto detallesProducto) {
        Producto productoExistente = productoRepository.findById(id).orElse(null);
        if (productoExistente != null) {
            productoExistente.setNombre(detallesProducto.getNombre());
            productoExistente.setDescripcion(detallesProducto.getDescripcion());
            productoExistente.setPrecio(detallesProducto.getPrecio());
            productoExistente.setCantidad(detallesProducto.getCantidad());
            return productoRepository.save(productoExistente);
        }
        return null;
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }


    public Double calcularValorInventario() {
        return productoRepository.findAll().stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();
    }

    public List<List<String>> obtenerCombinacionesProductos(double valorMaximo) {
        List<Producto> productos = productoRepository.findAll();
        List<List<String>> combinaciones = new ArrayList<>();

        // Combinaciones de 2 productos
        for (int i = 0; i < productos.size(); i++) {
            for (int j = i + 1; j < productos.size(); j++) {
                double suma = productos.get(i).getPrecio() + productos.get(j).getPrecio();
                if (suma <= valorMaximo) {
                    List<String> combinacion = List.of(
                        productos.get(i).getNombre(), 
                        productos.get(j).getNombre(), 
                        Double.toString(suma)   // Conversión a String
                    );
                    combinaciones.add(combinacion);
                }
            }
        }


        // Ordenar por la suma de precios en orden descendente
        combinaciones.sort(Comparator.comparingDouble(comb -> -Double.valueOf(comb.get(comb.size() - 1))));

        // Limitar a un máximo de 5 combinaciones
        return combinaciones.size() > 5 ? combinaciones.subList(0, 5) : combinaciones;
    }
}
