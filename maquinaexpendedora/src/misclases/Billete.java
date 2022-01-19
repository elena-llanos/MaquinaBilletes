package misclases;

import java.util.Date;

import querys.BilleteBD;

public class Billete {
	private int codBillete;
	private double importeBillete;
	private Date fechaHoraSalida;
	private Date fechaHoraLlegada;
	private Cliente cliente;
	private Parada paradaInicio;
	private Parada paradaFin;
	private LineaDeAutobus lineaDeAutobus;

	public Billete() {
	}

	public Billete(double importeBillete, Date fechaHoraSalida, Date fechaHoraLlegada, Cliente cliente,
			Parada numOrdenInicio, Parada numOrdenFin, LineaDeAutobus lineaDeAutobus) {
		this.importeBillete = importeBillete;
		this.fechaHoraSalida = fechaHoraSalida;
		this.fechaHoraLlegada = fechaHoraLlegada;
		this.cliente = cliente;
		this.paradaInicio = numOrdenInicio;
		this.paradaFin = numOrdenFin;
		this.lineaDeAutobus = lineaDeAutobus;
	}

	public static boolean insertarBillete(Billete billete) {
		boolean resultado = BilleteBD.insertar(billete);
		return resultado;
	}

	public int getCodBillete() {
		return codBillete;
	}

	public void setCodBillete(int codBillete) {
		this.codBillete = codBillete;
	}

	public double getImporteBillete() {
		return importeBillete;
	}

	public void setImporteBillete(double importeBillete) {
		this.importeBillete = importeBillete;
	}

	public Date getFechaHoraSalida() {
		return fechaHoraSalida;
	}

	public void setFechaHoraSalida(Date fechaHoraSalida) {
		this.fechaHoraSalida = fechaHoraSalida;
	}

	public Date getFechaHoraLlegada() {
		return fechaHoraLlegada;
	}

	public void setFechaHoraLlegada(Date fechaHoraLlegada) {
		this.fechaHoraLlegada = fechaHoraLlegada;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Parada getParadaInicio() {
		return paradaInicio;
	}

	public void setParadaInicio(Parada paradaInicio) {
		this.paradaInicio = paradaInicio;
	}

	public Parada getParadaFin() {
		return paradaFin;
	}

	public void setParadaFin(Parada paradaFin) {
		this.paradaFin = paradaFin;
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
		builder.append("Billete [codBillete=");
		builder.append(codBillete);
		builder.append(", importeBillete=");
		builder.append(importeBillete);
		builder.append(", fechaHoraSalida=");
		builder.append(fechaHoraSalida);
		builder.append(", fechaHoraLlegada=");
		builder.append(fechaHoraLlegada);
		builder.append(", cliente=");
		builder.append(cliente);
		builder.append(", numOrdenInicio=");
		builder.append(paradaInicio);
		builder.append(", numOrdenFin=");
		builder.append(paradaFin);
		builder.append(", lineaDeAutobus=");
		builder.append(lineaDeAutobus);
		builder.append("]");
		return builder.toString();
	}

}