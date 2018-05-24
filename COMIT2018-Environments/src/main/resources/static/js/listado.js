$(document).ready( function(){
	// ENSENIARLE A LAS TARJETAS QUE CUANDO LES HAGAN CLICK, SE MARQUEN SELECCIONADAS
	$(".tarjeta-usuario").click( function(){
		// agregar clase a la tarjeta
		$(this).toggleClass("seleccionada"); // toggle alterna, para agregar se puede poner add, y para quitar, remove
	} );
	
	$('#seleccionarTodas').change( function(){
		console.log($(this).is(":checked"));
	} );
	
	
} );
