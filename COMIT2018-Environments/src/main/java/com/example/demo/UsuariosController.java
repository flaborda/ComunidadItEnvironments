package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Usuario;

@Controller
public class UsuariosController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UsuariosHelper UsuariosHelper;

	// formulario vacio para loguearse
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/procesar-login")
	public String procesarLogin(HttpSession session, @RequestParam String nombre, @RequestParam String contra) throws SQLException {
		
		boolean sePudo = UsuariosHelper.intentarLoguearse(session, nombre, contra);
		
		if (sePudo) {
			return "redirect:/listado";
		} else {
			// TODO: pregarcar los datos que lleno, salvo la contrasenia
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) throws SQLException {
		UsuariosHelper helper=new UsuariosHelper();
		helper.cerrarSesion(session);
		return "redirect:/login";
	}
	
	
	// formulario vacio para insertar
	@GetMapping("/registro")
	public String registro() {
		return "registro";
	}
	
	@GetMapping("/A")
	public String A(){		
		return "A";
	}
	
	@GetMapping("/B")
	public String B(){	
		return "B";
	}
	
	
	// ruta para eliminar por id
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable int id) throws SQLException {
		
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
		
		PreparedStatement consulta = connection.prepareStatement("DELETE FROM usuarios WHERE id = ?;");
		consulta.setInt(1, id);
		
		consulta.executeUpdate();
		
		connection.close();
		
		return "redirect:/listado";
	}
	

	// muestra el listado completo de usuarios
	@GetMapping("/listado")
	public String listado(HttpSession session, Model template) throws SQLException {
		
		Usuario logueado = UsuariosHelper.usuarioLogueado(session);
		
		if (logueado == null) {
			return "redirect:/login";
		}
		
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
		
		PreparedStatement consulta = 
				connection.prepareStatement("SELECT * FROM usuarios;");
		
		ResultSet resultado = consulta.executeQuery();
		
		ArrayList<Usuario> listadoUsuarios = new ArrayList<Usuario>();
		
		while ( resultado.next() ) {
			int id = resultado.getInt("id");
			String nombre = resultado.getString("nombre");
			String password = resultado.getString("contrasenia");
			boolean activo = resultado.getBoolean("activo");
			
			Usuario x = new Usuario(id, nombre, password, activo);
			listadoUsuarios.add(x);
		}
		
		template.addAttribute("listadoUsuarios", listadoUsuarios);
		
		return "listadoUsuarios";
	}
	
	// muestra el listado completo de usuarios
		@GetMapping("/procesarBusqueda")
		public String procesarBusqueda(Model template, @RequestParam String palabraBuscada) throws SQLException {
			
			Connection connection;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ejemplo","postgres","admin");
			
			PreparedStatement consulta = 
					connection.prepareStatement("SELECT * FROM usuarios WHERE nombre LIKE ?;");
			consulta.setString(1, "%" + palabraBuscada +  "%");
			
			ResultSet resultado = consulta.executeQuery();
			
			ArrayList<Usuario> listadoUsuarios = new ArrayList<Usuario>();
			
			while ( resultado.next() ) {
				int id = resultado.getInt("id");
				String nombre = resultado.getString("nombre");
				String password = resultado.getString("contrasenia");
				boolean activo = resultado.getBoolean("activo");
				
				Usuario x = new Usuario(id, nombre, password, activo);
				listadoUsuarios.add(x);
			}
			
			template.addAttribute("listadoUsuarios", listadoUsuarios);
			
			return "listadoUsuarios";
		}
	
	
	// muestra un usuario en detalle
	@GetMapping("/detalle/{id}")
	public String detalle(Model template, @PathVariable int id) throws SQLException {
		
		Connection connection;
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ejemplo","postgres","admin");
		
		PreparedStatement consulta = 
				connection.prepareStatement("SELECT * FROM usuarios WHERE id = ?;");
		
		consulta.setInt(1, id);

		ResultSet resultado = consulta.executeQuery();
		
		if ( resultado.next() ) {
			String nombre = resultado.getString("nombre");
			String password = resultado.getString("contrasenia");
			boolean activo = resultado.getBoolean("activo");
			
			template.addAttribute("nombre", nombre);
			template.addAttribute("password", password);
			template.addAttribute("activo", activo);
		}
		
		return "detalleUsuario";
	}


	@GetMapping("/editar/{id}")
	public String editar(Model template, @PathVariable int id) throws SQLException {
		
		Connection connection;
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ejemplo","postgres","admin");
		
		PreparedStatement consulta = 
				connection.prepareStatement("SELECT * FROM usuarios WHERE id = ?;");
		
		consulta.setInt(1, id);

		ResultSet resultado = consulta.executeQuery();
		
		if ( resultado.next() ) {
			String nombre = resultado.getString("nombre");
			String password = resultado.getString("contrasenia");
			boolean activo = resultado.getBoolean("activo");
			
			template.addAttribute("nombre", nombre);
			template.addAttribute("password", password);
			template.addAttribute("activo", activo);
		}
		
		return "editar-usuario";
	}
	
	// ruta para insertar un usuario desde el formulario de registro
	@PostMapping("/insertar-usuario")
	public String insertarUsuario(@RequestParam String nombre, @RequestParam String password) throws SQLException {
		Connection connection;
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ejemplo","postgres","admin");
		
		PreparedStatement consulta = 
				connection.prepareStatement("INSERT INTO usuarios(nombre, contrasenia, activo) VALUES(?, ?, true);", PreparedStatement.RETURN_GENERATED_KEYS);
		consulta.setString(1, nombre);
		consulta.setString(2, password);
		
		int affected = consulta.executeUpdate();
		
		if(affected == 1) {
			ResultSet gk = consulta.getGeneratedKeys();
			
			
			if (gk.next()) {
				System.out.println(gk.getInt(1));
			}
		} else {
			System.out.println( " hola");
		}
		
		connection.close();
		return "redirect:/registro";
	}
	
	// ruta para insertar un usuario desde el formulario de registro
		@GetMapping("/modificar/{id}")
		public String modificarUsuario(@PathVariable int id, @RequestParam String nombre, @RequestParam String contrasenia, @RequestParam boolean activo) throws SQLException {
			Connection connection;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ejemplo","postgres","admin");
			
			PreparedStatement consulta = 
					connection.prepareStatement("UPDATE usuarios SET nombre = ?, contrasenia = ?, activo = ? WHERE id = ?;");
			consulta.setString(1, nombre);
			consulta.setString(2, contrasenia);
			consulta.setBoolean(3, activo);
			consulta.setInt(4, id);
			
			consulta.executeUpdate();
			

			
			connection.close();
			return "redirect:/detalle/" + id;
		}
		
	// ruta de prueba, no usar
	@GetMapping("/insertar-usuario-prueba")
	@ResponseBody
	public String insertarUsuarioPrueba() throws SQLException {
	
		Connection connection;
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ejemplo","postgres","admin");
		
		PreparedStatement consulta = 
				connection.prepareStatement("INSERT INTO usuarios(nombre, contrasenia, activo) VALUES('usuarioTest', '1234', true);", PreparedStatement.RETURN_GENERATED_KEYS);

		consulta.executeUpdate();
		
		connection.close();
		return "OK";
	}

	// ruta de prueba, no usar
	@GetMapping("/eliminar-usuario-prueba")
	@ResponseBody
	public String eliminarUsuarioPrueba() throws SQLException {
	
		Connection connection;
		connection = 
				DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ejemplo","postgres","admin");
		
		PreparedStatement consulta = 
				connection.prepareStatement("DELETE FROM usuarios WHERE nombre = 'usuarioTest';");

		consulta.executeUpdate();
		
		connection.close();
		return "OK";
	}

}






