package com.salesianostriana.dam.GarciaMariaPablo.global.servicios.otros.base;

import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface ServicioBase<T, ID> {
	
	List<T> findAll();
	
	Optional<T> findById(ID id);
	
	T save(T t);
	
	T edit(T t);
	
	void delete(T t);
	
	void deleteById(ID id);
	
	List<T> procesarPaginacion(List<T> lista, Model modelo, String paginaNumStr, String perPageStr);

}
