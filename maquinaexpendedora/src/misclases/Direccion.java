package misclases;

import java.util.ArrayList;

public class Direccion {
	private int codDireccion;
	private String calle;
	private int numero;
	private int codPostal;
	private ArrayList<Parada> listaParadas;
	private Municipio municipio;
	
	public Direccion() {
		
	}

	public int getCodDireccion() {
		return codDireccion;
	}

	public void setCodDireccion(int codDireccion) {
		this.codDireccion = codDireccion;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(int codPostal) {
		this.codPostal = codPostal;
	}

	public ArrayList<Parada> getListaParadas() {
		return listaParadas;
	}

	public void setListaParadas(ArrayList<Parada> listaParadas) {
		this.listaParadas = listaParadas;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Direccion [codDireccion=");
		builder.append(codDireccion);
		builder.append(", calle=");
		builder.append(calle);
		builder.append(", numero=");
		builder.append(numero);
		builder.append(", codPostal=");
		builder.append(codPostal);
		builder.append(", listaParadas=");
		builder.append(listaParadas);
		builder.append(", municipio=");
		builder.append(municipio);
		builder.append("]");
		return builder.toString();
	}
	
}
