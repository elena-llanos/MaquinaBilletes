package misclases;

import java.util.ArrayList;

public class Municipio {
	private String codMunicipio;
	private String nomMunicipio;
	private ArrayList<Direccion> listaDirecciones;

	public Municipio() {

	}

	public Municipio(String codMunicipio, String nomMunicipio) {
		this.codMunicipio = codMunicipio;
		this.nomMunicipio = nomMunicipio;
	}

	public String getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public String getNomMunicipio() {
		return nomMunicipio;
	}

	public void setNomMunicipio(String nomMunicipio) {
		this.nomMunicipio = nomMunicipio;
	}

	public ArrayList<Direccion> getListaDirecciones() {
		return listaDirecciones;
	}

	public void setListaDirecciones(ArrayList<Direccion> listaDirecciones) {
		this.listaDirecciones = listaDirecciones;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Municipio [codMunicipio=");
		builder.append(codMunicipio);
		builder.append(", nomMunicipio=");
		builder.append(nomMunicipio);
		builder.append(", listaDirecciones=");
		builder.append(listaDirecciones);
		builder.append("]");
		return builder.toString();
	}

}
