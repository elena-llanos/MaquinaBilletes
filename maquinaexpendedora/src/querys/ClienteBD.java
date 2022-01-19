package querys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexionBD.Conexion;
import misclases.Cliente;

public class ClienteBD extends Conexion {

//	public static ArrayList<Cliente> obtenerAlumnos() {
//		// llamamos al metodo abrir conexion
//		Connection conn = abrirConexion();
//		Statement instruccion = null;
//		ResultSet resultado = null;
//
//		try {
//
//			instruccion = conn.createStatement();
//			resultado = instruccion.executeQuery("SELECT * FROM cliente");
//
//			ArrayList<Cliente> clientes = new ArrayList<>();
//
//			while (resultado.next()) {
//				Cliente cliente = new Cliente();
//				cliente.setCodCliente(resultado.getInt("cod_cliente"));
//				cliente.setDniNie(resultado.getString("dni_nie"));
//				cliente.setNombre(resultado.getString("nombre"));
//				cliente.setApellido(resultado.getString("apellido"));
//				cliente.setContrasena(resultado.getString("contrasena"));
//
//				clientes.add(cliente);
//			}
//			return clientes;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			cerrarConexion(conn, instruccion, resultado);
//		}
//
//	}

	public static boolean insertar(Cliente cliente) {
		// llamamos al metodo para abrir la conexion
		Connection conn = abrirConexion();
		// vamos a utilizar solo statemente por que vamos a enviarle una instruccion
		Statement instruccion = null;

		try {
			// es la forma de crear objeto de Statement es decir se hace sobre la conexion
			instruccion = conn.createStatement();
			instruccion.executeUpdate("INSERT INTO cliente(dni_nie, nombre, apellido, contrasena) VALUES('"
					+ cliente.getDniNie() + "','" + cliente.getNombre() + "','" + cliente.getApellido() + "','"
					+ cliente.getContrasena() + "')");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			cerrarConexion(conn, instruccion, null);
		}

	}

	public static Cliente loguearCliente(String dniNie, String contrasena) {
		Cliente cliente = null;
		Connection conn = abrirConexion();
		Statement instruccion = null;
		ResultSet resultado = null;

		try {
			instruccion = conn.createStatement();
			resultado = instruccion.executeQuery(
					"SELECT cod_cliente, dni_nie, nombre , apellido,contrasena FROM cliente WHERE dni_nie = '" + dniNie
							+ "' AND contrasena = '" + contrasena + "'");

			while (resultado.next()) {

				cliente = new Cliente();
				cliente.setCodCliente(resultado.getInt("cod_cliente"));
				cliente.setNombre(resultado.getString("nombre"));
				cliente.setApellido(resultado.getString("apellido"));
				cliente.setDniNie(resultado.getString("dni_nie"));
				cliente.setContrasena(resultado.getString("contrasena"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			cerrarConexion(conn, instruccion, resultado);
		}

		return cliente;

	}

}
