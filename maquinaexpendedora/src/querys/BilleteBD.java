package querys;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import conexionBD.Conexion;
import misclases.Billete;

public class BilleteBD extends Conexion {

	public static boolean insertar(Billete billete) {
		// Llamamos al metodo para abrir la conexion
		Connection conn = abrirConexion();
		// Vamos a utilizar solo statement por que vamos a enviarle una instruccion
		Statement instruccion = null;

		try {
			// Es la forma de crear objeto de Statement, es decir se hace sobre la conexion
			instruccion = conn.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			instruccion.executeUpdate(
					"INSERT INTO billete(cod_linea_autobus, num_orden_ini, num_orden_fin, cod_cliente, fecha_hora_salida, fecha_hora_llegada, importe_billete)"
							+ "VALUES ('" + billete.getLineaDeAutobus().getCodLineaAutobus() + "', '"
							+ billete.getParadaInicio().getNumOrden() + "', '" + billete.getParadaFin().getNumOrden()
							+ "', '" + billete.getCliente().getCodCliente() + "', '"
							+ sdf.format(billete.getFechaHoraSalida()) + "', '"
							+ sdf.format(billete.getFechaHoraLlegada()) + "', '" + billete.getImporteBillete() + "');");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			cerrarConexion(conn, instruccion, null);
		}

	}

}
