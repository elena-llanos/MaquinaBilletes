package misclases;

import java.util.ArrayList;
import java.util.Date;

import querys.LineaDeAutobusBD;

public class LineaDeAutobus {
	private String codLineaAutobus;
	private String nombre;
	private Date horaInicio;
	private Date horaFin;
	private int frecuencia;
	private double precioLinea;
	private ArrayList<Parada> paradas;

	public LineaDeAutobus() {

	}

	public LineaDeAutobus(String codLineaAutobus, String nombre, Date horaInicio, Date horaFin, int frecuencia,
			double precioLinea, ArrayList<Parada> paradas) {
		super();
		this.codLineaAutobus = codLineaAutobus;
		this.nombre = nombre;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.frecuencia = frecuencia;
		this.precioLinea = precioLinea;
		this.paradas = paradas;
	}

	public static ArrayList<LineaDeAutobus> mostrarLineasDeAutobus() {

		ArrayList<LineaDeAutobus> listaDeLineas = LineaDeAutobusBD.obtenerLineasDeAutobus();
		return listaDeLineas;
	}

	public static LineaDeAutobus mostrarHorarioLineaAscDesc(LineaDeAutobus lineaAutobus, String hora) {
		LineaDeAutobus linea = LineaDeAutobusBD.obtenerHoarioLineaDeAutobus(lineaAutobus, hora);
		return linea;
	}

	public String getCodLineaAutobus() {
		return codLineaAutobus;
	}

	public void setCodLineaAutobus(String codLineaAutobus) {
		this.codLineaAutobus = codLineaAutobus;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}

	public double getPrecioLinea() {
		return precioLinea;
	}

	public void setPrecioLinea(double precioLinea) {
		this.precioLinea = precioLinea;
	}

	public ArrayList<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(ArrayList<Parada> paradas) {
		this.paradas = paradas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LineaDeAutobus [codLineaAutobus=");
		builder.append(codLineaAutobus);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", frecuencia=");
		builder.append(frecuencia);
		builder.append(", precioLinea=");
		builder.append(precioLinea);
		builder.append(", paradas=");
		builder.append(paradas);
		builder.append("]");
		return builder.toString();
	}

}
