package com.salesianostriana.dam.GarciaMariaPablo.global.controladores.otros;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControladorActiveTab {

    @ModelAttribute
    public void setActiveTab(Model model, WebRequest request) {
        String uri = request.getDescription(false);
        uri = uri.replace("uri=","");
        System.out.println("u: " + uri);
        model.addAttribute("activeTab", uri);
    }
}
