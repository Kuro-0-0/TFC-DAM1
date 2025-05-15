package com.salesianostriana.dam.GarciaMariaPablo.old.servicios.base;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;

public abstract class 
		ServicioBaseImpl<T, ID, R extends JpaRepository<T, ID>> 
		implements ServicioBase<T, ID> {

	@Autowired
	protected R repositorio;
	
	
	@Override
	public List<T> findAll() {
		return repositorio.findAll();
	}

	@Override
	public Optional<T> findById(ID id) {
		return repositorio.findById(id);
	}

	@Override
	public T save(T t) {
		return repositorio.save(t);
	}

	@Override
	public T edit(T t) {
		return repositorio.save(t);
	}

	@Override
	public void delete(T t) {
		repositorio.delete(t);
		
	}

	@Override
	public void deleteById(ID id) {
		repositorio.deleteById(id);		
	}

	@Override
	public List<T> procesarPaginacion(List<T> lista, Model modelo, String paginaNumStr, String perPageStr) {
		int paginaNum, porPaginaNum, pagMax, pagMin = 1, perPageMin = 0, limit;

		try { // Se realiza en un try - catch para evitar crasheos en caso de que mande un String real y no un String
			// numérico como parámetro.
			porPaginaNum = Integer.parseInt(perPageStr);
			if (porPaginaNum <= perPageMin) {
				throw new Exception("perPage Not Avaible");
			}
		} catch (Exception e) {
			System.out.print("e: "+ e);
			porPaginaNum = 10;
		}

		try { // Exactamente lo mismo que arriba pero con el número de página.
			paginaNum = Integer.parseInt(paginaNumStr);
			if (paginaNum <= 0) {
				throw new Exception("Page Not Avaible");
			}
		} catch (Exception e) {
			System.out.print("e: "+ e);
			paginaNum = 1;
		}


		pagMax = lista.size() % porPaginaNum == 0 ? lista.size() / porPaginaNum : (lista.size() / porPaginaNum) + 1;

		pagMax = Math.max(pagMax, pagMin);
        paginaNum = Math.max(paginaNum, pagMin);
		paginaNum = Math.min(paginaNum, pagMax);

		limit = paginaNum * porPaginaNum;

		modelo.addAttribute("paginaFinal",pagMax);
		modelo.addAttribute("paginaActual",paginaNum);
		modelo.addAttribute("perPage",porPaginaNum);


		lista = lista.stream()
				.skip(limit-porPaginaNum)
				.limit(porPaginaNum)
				.toList();

		return lista;
	}

	
	
	
}
