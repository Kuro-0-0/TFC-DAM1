package com.salesianostriana.dam.GarciaMariaPablo.controladores.otros;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class ControladorActiveTab {

	@ModelAttribute
	public void setActiveTab(Model model, WebRequest request) {
		String uri = request.getDescription(false);
		
		if (uri.contains("/usuarios")) {
			model.addAttribute("activeTab","usuarios");
		} else if (uri.contains("/incidencias")) {
			model.addAttribute("activeTab","incidencias");
		} else if (uri.contains("/estados")) {
			model.addAttribute("activeTab","estados");
		} else if (uri.contains("/otros")) {
			model.addAttribute("activeTab","otros");
		}
	}
	
	
}
