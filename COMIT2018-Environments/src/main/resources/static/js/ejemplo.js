/* 
 * 1. Hacer que cuando el usuario haga click en el cartel Un Mensaje, aparezca un alert y un cartel en consola.
 * 2. Hacer que cuando el usuario haga click en el boton, el mensaje se cambie a otra cosa. 
 *    Usar la funcion .text("lo que sea")
 * 3. Agregar un input de texto en el html a mano y hacer por jquery que si el usuario hace click en el boton, 
 *    ese input se rellene con el texto "comunidad-it". Usar la funcion .val("algo")  en lugar de text().
 * 4. Agregar un segundo boton en el html y hacer que si el usuario hace click en el, se muestre un cartel en 
 *    consola que incluya el valor del input, usando .val()
 * 
 *  */


function saludar(nombre) {
	console.log("Hola " + nombre);
}

function holaMundo() {
	//console.log("Hola Mundo");
	alert("Hola Mundo");
	
}



// ready con funcion no anonima
// $(document).ready(holaMundo);

// ready con funcion anonima
$(document).ready( function() {
	// todo lo que va despues de que cargue la pagina
	console.log("Termino de cargar");
	
	$("button").click( holaMundo );
	$("a").click( function(){
		return confirm("Vas a eliminar un usuario:");
	} );	
	
});

console.log("Un mensaje en la consola");