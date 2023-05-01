package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.app.dto.ProductoRequest;
import com.app.entity.Categoria;
import com.app.entity.Producto;
import com.app.repository.CategoriaRepository;
import com.app.repository.ProductoRepository;

@Controller
public class ProductoGraphQLController {
	@Autowired      //acedr a ls rpsts prct y ctgr
	private ProductoRepository productoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	//4 mtds , las mnj solcitds de cnslts de grah
	@QueryMapping
	public List<Producto> listarProductos(){
		return productoRepository.findAll();
	}
	/* vrevre */
	
	@QueryMapping
	public Producto listarProductoPorId(@Argument String id) {
		return productoRepository.findById(id).orElseThrow(
				() -> new RuntimeException(String.format("Producto %s no encontrado",id))
				);
	}
	
	@QueryMapping
	public List<Categoria> listarCategorias(){
		return categoriaRepository.findAll();
	}
	
	@QueryMapping
	public Categoria listarCategoriaPorId(@Argument Long id) {
		return categoriaRepository.findById(id).orElseThrow(
				() -> new RuntimeException(String.format("Categoria %s no encontrado",id))
				);
	}
	
	@MutationMapping  // ls cles mnjan slctds de mutación GraphQL:
	public Producto guardarProducto(@Argument ProductoRequest productoRequest) {
		Categoria categoria = categoriaRepository.findById(productoRequest.categoriaId()).orElse(null);
		Producto productoBBDD = new Producto();
		productoBBDD.setId(productoRequest.id());
		productoBBDD.setNombre(productoRequest.nombre());
		productoBBDD.setPrecio(productoRequest.precio());
		productoBBDD.setCantidad(productoRequest.cantidad());
		productoBBDD.setCategoria(categoria);

		return productoRepository.save(productoBBDD);
	}
	
	
	@MutationMapping
	public Producto actualizarProducto(@Argument String id,@Argument ProductoRequest productoRequest) {
		Categoria categoria = categoriaRepository.findById(productoRequest.categoriaId()).orElse(null);
		Producto productoBBDD = new Producto();
		productoBBDD.setId(id);
		productoBBDD.setNombre(productoRequest.nombre());
		productoBBDD.setPrecio(productoRequest.precio());
		productoBBDD.setCantidad(productoRequest.cantidad());
		productoBBDD.setCategoria(categoria);

		return productoRepository.save(productoBBDD);
	}
	
	
	@MutationMapping
	public void eliminarProducto(@Argument String id) {
		productoRepository.deleteById(id);
	}

}
