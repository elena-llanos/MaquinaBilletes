package querys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexionBD.Conexion;
import misclases.LineaDeAutobus;
import misclases.Parada;

public class ParadaBD extends Conexion {

	//método para obtener las paradas desde la base de datos.
	public static ArrayList<Parada> obtenerParadas(LineaDeAutobus lineaAutobus) {

		Connection conn = abrirConexion();
		Statement instruccion = null;
		ResultSet resultado = null;

		try {
			instruccion = conn.createStatement();
			resultado = instruccion.executeQuery(
					" SELECT num_orden, nom_parada, intervalo FROM parada WHERE cod_linea_autobus = '" + lineaAutobus.getCodLineaAutobus() + "'");
			ArrayList<Parada> listaParadas = new ArrayList<Parada>();

			while (resultado.next()) {
				Parada parada = new Parada();
				parada.setNumOrden(resultado.getInt("num_orden"));
				parada.setNomParada(resultado.getString("nom_parada"));
				parada.setIntervalo(resultado.getInt("intervalo"));
				listaParadas.add(parada);
			}
			return listaParadas;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			cerrarConexion(conn, instruccion, resultado);
		}

	}

}
