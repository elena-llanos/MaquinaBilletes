package querys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import conexionBD.Conexion;
import misclases.LineaDeAutobus;

public class LineaDeAutobusBD extends Conexion {

	public static ArrayList<LineaDeAutobus> obtenerLineasDeAutobus() {

		Connection conn = abrirConexion();
		Statement instruccion = null;
		ResultSet resultado = null;

		try {
			instruccion = conn.createStatement();
			resultado = instruccion.executeQuery(
					"SELECT cod_linea_autobus, nombre FROM linea_de_autobus ORDER BY cod_linea_autobus asc");

			ArrayList<LineaDeAutobus> lineasDeAutobuses = new ArrayList<LineaDeAutobus>();

			while (resultado.next()) {
				LineaDeAutobus lineaDeAutobus = new LineaDeAutobus();
				lineaDeAutobus.setCodLineaAutobus(resultado.getString("cod_linea_autobus"));
				lineaDeAutobus.setNombre(resultado.getString("nombre"));

				lineasDeAutobuses.add(lineaDeAutobus);
			}
			return lineasDeAutobuses;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			cerrarConexion(conn, instruccion, resultado);
		}

	}

	public static LineaDeAutobus obtenerHoarioLineaDeAutobus(LineaDeAutobus lineaAutobus, String horaInicioFin) {
		Connection conn = abrirConexion();
		Statement instruccion = null;
		ResultSet resultado = null;

		try {
			instruccion = conn.createStatement();
			resultado = instruccion.executeQuery(" select hora_inicio_" + horaInicioFin + ", hora_fin_" + horaInicioFin
					+ ", frecuencia  from linea_de_autobus where cod_linea_autobus = '"
					+ lineaAutobus.getCodLineaAutobus() + "'");

			// https://stackoverflow.com/questions/24736427/how-to-get-date-from-a-resultset/24737131
			// al ser librerias distintas ya que en clase Linea la libreria era
			// java.util.Date y
			// el getTime java.sql.Data me salia el error
			// 1970-01-01
			LineaDeAutobus linea = null;

			while (resultado.next()) {
				linea = new LineaDeAutobus();
				if ("asc".equals(horaInicioFin)) {
					Date horaInicioAsc = new Date();
					horaInicioAsc.setTime(resultado.getTime("hora_inicio_asc").getTime());
					linea.setHoraInicio(horaInicioAsc);
					Date horaFinAsc = new Date();
					horaFinAsc.setTime(resultado.getTime("hora_fin_asc").getTime());
					linea.setHoraFin(horaFinAsc);
					linea.setFrecuencia(resultado.getInt("frecuencia"));
				} else {
					Date horaInicioDesc = new Date();
					horaInicioDesc.setTime(resultado.getTime("hora_inicio_desc").getTime());
					linea.setHoraInicio(horaInicioDesc);
					Date horaFinDesc = new Date();
					horaFinDesc.setTime(resultado.getTime("hora_fin_desc").getTime());
					linea.setHoraFin(horaFinDesc);
					linea.setFrecuencia(resultado.getInt("frecuencia"));
				}

			}

			return linea;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			cerrarConexion(conn, instruccion, resultado);
		}

	}

}
