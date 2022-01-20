package misclases;

import java.util.ArrayList;

import querys.ParadaBD;

public class Parada {
	private int numOrden;
	private String nomParada;
	private int intervalo;
	private Direccion direccion;
	private LineaDeAutobus lineaDeAutobus;

	public Parada() {

	}

	public Parada(int numOrden, String nomParada, int intervalo, Direccion direccion, LineaDeAutobus lineaDeAutobus) {
		this.numOrden = numOrden;
		this.nomParada = nomParada;
		this.intervalo = intervalo;
		this.direccion = direccion;
		this.lineaDeAutobus = lineaDeAutobus;
	}
	
	//Con este método obtenemos la lista de paradas.

	public static ArrayList<Parada> obtenerParadas(LineaDeAutobus lineaAutobus) {
		ArrayList<Parada> listaParadas = ParadaBD.obtenerParadas(lineaAutobus);
		return listaParadas;
	}

	public int getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(int numOrden) {
		this.numOrden = numOrden;
	}

	public String getNomParada() {
		return nomParada;
	}

	public void setNomParada(String nomParada) {
		this.nomParada = nomParada;
	}

	public int getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public LineaDeAutobus getLineaDeAutobus() {
		return lineaDeAutobus;
	}

	public void setLineaDeAutobus(LineaDeAutobus lineaDeAutobus) {
		this.lineaDeAutobus = lineaDeAutobus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Parada [numOrden=");
		builder.append(numOrden);
		builder.append(", nomParada=");
		builder.append(nomParada);
		builder.append(", intervalo=");
		builder.append(intervalo);
		builder.append(", direccion=");
		builder.append(direccion);
		builder.append(", lineaDeAutobus=");
		builder.append(lineaDeAutobus);
		builder.append("]");
		return builder.toString();
	}

}