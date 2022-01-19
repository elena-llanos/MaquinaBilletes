package misclases;

import querys.ClienteBD;

public class Cliente {
	private int codCliente;
	private String dniNie;
	private String nombre;
	private String apellido;
	private String contrasena;
	//private ArrayList<Billete> listaBilletes;

	public Cliente() {

	}

	public Cliente(String dniNie, String nombre, String apellido, String contrasena) {
		this.dniNie = dniNie;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasena = contrasena;
	}

	// metodos de la clase cliente
	public static boolean insertarCliente(Cliente cliente) {
		boolean resultado = ClienteBD.insertar(cliente);
		return resultado;
	}
	
	public static Cliente loguearCliente(String dniNie, String contrasena) {
		Cliente cliente = ClienteBD.loguearCliente(dniNie, contrasena);
		return cliente;
	}
	 //aqui vamos validar si el formato es correcto
	public static boolean validarDNINIE(String dni) {
		//con el metodo matches nos obliga a que cumplemos el regex un exprecion regular
		//^ el inicio obligo a que sea de 0 a 9 y con las {} pongo el numero de repeticiones del 8 que son los numeros $ final y el
		boolean dniCorrecto = dni.matches("^[0-9]{8}[A-Z a-z]$");
		boolean nieCorrecto = dni.matches("^[XYZ xyz]\\d{7,8}[A-Z a-z]$");
		
		if(dniCorrecto == true || nieCorrecto == true) {
			return true;
		}else {
			return false;
		}
	}
	
	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}

	public String getDniNie() {
		return dniNie;
	}

	public void setDniNie(String dniNie) {
		this.dniNie = dniNie;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cliente [codCliente=");
		builder.append(codCliente);
		builder.append(", dniNie=");
		builder.append(dniNie);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", apellido=");
		builder.append(apellido);
		builder.append(", contrasena=");
		builder.append(contrasena);
		builder.append("]");
		return builder.toString();
	}

}