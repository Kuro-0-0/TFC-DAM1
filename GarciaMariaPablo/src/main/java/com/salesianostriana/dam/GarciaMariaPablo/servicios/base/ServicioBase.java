package com.salesianostriana.dam.GarciaMariaPablo.servicios.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class ServicioBase<T, ID, R extends JpaRepository<T,ID>> {

	@Autowired
	protected R repositorio;

	/**
	 *
	 * Metodo usado para paginar los elementos de una lista pasada como parametro basandose en los datos facilitados
	 * por otros parametros.
	 * Probablemente, Deprecated debido a un posible futuro uso de SQL para la gestion de filtros y paginacion
	 *
	 * @param modelo
	 * @param lista Lista a paginar.
	 * @param paginaNumStr Número de la página actual
	 * @param perPageStr Elementos mostrados por pagina
	 * @return List<T> lista ya paginada.
	 * @param <T> Tipo del elemento almacenado en la pagina
	 */
	public static <T> List<T> procesarPaginacion(List<T> lista, Model modelo, String paginaNumStr, String perPageStr) {

		int paginaNum, porPaginaNum, pagMax, pagMin = 1, perPageMin = 0, limit;

		try { // Se realiza en un try - catch para evitar crasheos en caso de que mande un String real y no un String
			// numérico como parámetro.
			porPaginaNum = Integer.parseInt(perPageStr);
			if (porPaginaNum <= perPageMin) {
				throw new Exception("perPage Not Avaible");
			}
		} catch (Exception e) {
			log.error("e: ", e);
			porPaginaNum = 10;
		}

		try { // Exactamente lo mismo que arriba pero con el número de página.
			paginaNum = Integer.parseInt(paginaNumStr);
			if (paginaNum <= 0) {
				throw new Exception("Page Not Avaible");
			}
		} catch (Exception e) {
			log.error("e: ", e);
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

	/**
	 * Almacenamos una nueva entidad a través del repositorio
	 * 
	 * @param t
	 * @return
	 */
	public T save(T t) {
		return repositorio.save(t);
	}

	/**
	 * Localizamos una entidad en base a su Id.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<T> findById(ID id) {

		// Devolvemos la entidad si la encuentra u otro si no lo encuentra,
		// en este caso, hemos dicho que ese "otro" sea null
		// se podría hacer también, más bien se debería hacer 
		// con Optional usando el siguiente código
		/*
		 * @Override public Optional<T> findById(ID id) { return
		 * Optional.ofNullable(repositorio.findById(id).orElse(null)); }
		 */

		return repositorio.findById(id);
	}

	/**
	 * Obtenemos todas las entidades de un tipo de entidad
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return repositorio.findAll();
	}

	/**
	 * Editamos una instancia de una entidad (si no tiene Id, la insertamos).
	 * 
	 * @param t
	 * @return
	 */
	public T edit(T t) {
		return repositorio.save(t);
	}

	/**
	 * Eliminamos una instancia de una entidad
	 * 
	 * @param t
	 */
	public void delete(T t) {
		repositorio.delete(t);
	}

	/**
	 * Eliminamos una instancia en base a su ID
	 * 
	 * @param id
	 */
	public void deleteById(ID id) {
		repositorio.deleteById(id);
	}
	
}
