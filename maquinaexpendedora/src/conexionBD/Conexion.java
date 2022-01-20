package conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import misclases.Cliente;
import misclases.LineaDeAutobus;
import misclases.Parada;

public class Conexion {

	// nuestro metodo para conectar

	public static Connection abrirConexion() {
		try {
			// la clase que se encuentra destro de nuestra libreria jar al ser via web
			// necesita esta clase
			Class.forName("com.mysql.cj.jdbc.Driver");
			// escribimos la cadena de conexion de la base de datos mysql tiene el propia
			// especificar todos estos parametros para podernos conectar correctamente al
			// mysql
			Connection conexion = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bd_maquina_tickets?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"maquina", "admin");
		//	System.out.println("Conexion Establecida");
			return conexion;
		} catch (ClassNotFoundException e) {
			System.out.println("No se conectó a la Base de Datos");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("No se conectó a la Base de Datos");
			e.printStackTrace();
		}
		return null;
	}

	// nuestro metodo para desconectar

	public static void cerrarConexion(Connection conn, Statement instruccion, ResultSet resultado) {

		if (resultado != null) {
			try {
				resultado.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (instruccion != null) {
			try {
				instruccion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

//}
	
	
	
}
