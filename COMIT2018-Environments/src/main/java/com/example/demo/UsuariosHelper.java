package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.model.Usuario;

@Service
public class UsuariosHelper {

	@Autowired
	private Environment env;

	public boolean intentarLoguearse(HttpSession session, String nombre, String contra) throws SQLException{
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),"postgres","admin");
		
		PreparedStatement consulta = 
				connection.prepareStatement("SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?;");

		consulta.setString(1, nombre);
		consulta.setString(2, contra);
		
		ResultSet resultado = consulta.executeQuery();
		
		if ( resultado.next() ) {
			String codigo = UUID.randomUUID().toString();
			session.setAttribute("codigo-autorizacion", codigo);

			consulta =connection.prepareStatement("UPDATE usuarios SET codigo = ? WHERE nombre = ?");
			consulta.setString(1, codigo);
			consulta.setString(2, nombre);

			consulta.executeUpdate();
			
			return true;
		} else {
			return false;
		}
	}
	
	public Usuario usuarioLogueado(HttpSession session) throws SQLException{
		String codigo = (String)session.getAttribute("codigo-autorizacion");

		if (codigo != null) {
			// OBTENER EL USUARIO DE LA BASE
			Connection connection;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ejemplo","postgres","admin");
			
			PreparedStatement consulta = 
					connection.prepareStatement("SELECT * FROM usuarios WHERE codigo = ?;");
			consulta.setString(1, codigo);
			
			ResultSet resultado = consulta.executeQuery();
			
			if ( resultado.next() ) {
				// ARMAR Y DEVOLVE ESE USUARIO
				Usuario logueado = new Usuario( resultado.getInt("id"), resultado.getString("nombre"), resultado.getString("contrasenia"), resultado.getBoolean("activo"));
				return logueado;
			} else {
				return null;
			}
			
			
		} else {
			return null;
		}
	}
	
	public void cerrarSesion(HttpSession session) throws SQLException{

		String codigo = (String)session.getAttribute("codigo-autorizacion");
		
		session.removeAttribute("codigo-autorizacion");
		
		Connection connection;
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ejemplo","postgres","admin");
		
		PreparedStatement consulta = 
				connection.prepareStatement("UPDATE usuarios SET codigo = null WHERE codigo = ?;");

		consulta.setString(1, codigo);
		
		consulta.executeUpdate();
		connection.close();
		
	}
}

























