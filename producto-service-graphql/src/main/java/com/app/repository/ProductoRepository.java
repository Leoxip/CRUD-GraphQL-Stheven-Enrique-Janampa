package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.Producto;


public interface ProductoRepository extends JpaRepository<Producto,String> {

}
