package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExampleController {
	@GetMapping("/ejercicio-css")
	public String ejercicioCss() {
		return "ejercicio-css";
	}
	
	@GetMapping("/ejercicio-css-bootstrap")
	public String ejercicioCssBootstrap() {
		return "ejercicio-css-bootstrap";
	}
	
	@GetMapping("/ejemplo-jquery")
	public String ejemplojquery() {
		return "ejemplo-jquery";
	}
	
	@GetMapping("/ejercicio-rutas-y-formularios")
	public String ejercicioRutasYFormularios() {
		return "ejercicio-rutas-formularios";
	}

}
