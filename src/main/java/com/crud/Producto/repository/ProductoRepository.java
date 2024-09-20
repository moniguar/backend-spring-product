package com.crud.Producto.repository;

import com.crud.Producto.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
