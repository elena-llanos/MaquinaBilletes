package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import misclases.Billete;
import misclases.Cliente;
import misclases.LineaDeAutobus;
import misclases.Parada;
import querys.ParadaBD;

public class MaquinaTicketsMain {

	static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		int opcion = 0;

		do {
			opcion = menu();

			if (opcion >= 1 && opcion <= 2) {
				switch (opcion) {
				case 1:
					registroUsuario();
					break;
				case 2:
					comprarBillete();
					break;
				}
			} else if (opcion != 3 && opcion != -989) {
				System.out.println("Opcion Inexistente  debes insertar valores del 1 - 3");
			}

		} while (opcion != 3 );

		System.out.println("Gracias por usar nuestros servicios");
		teclado.close();
	}

	public static int menu() {
		int opcion = 0;
		System.out.println("*** Bienvenido/a a la estación de Autobuses : *** \n");
		System.out.println(" * Para adquirir un billete debes registrarte primero pulsando la  Opción 1\n"
				+ "\t\r * Si ya eres usuario registrado pulsa directamente la Opción 2 \n"
				+ "\t\r * Para salir pulsa la Opción 3\n");
		System.out.println("1.- Registro de usuario");
		System.out.println("2.- Comprar billetes");
		System.out.println("3.- Salir \n");
		System.out.println("\t\r Introduce la opcion deseada: ");
		try {
			opcion = teclado.nextInt();
			teclado.nextLine();
		} catch (InputMismatchException i) {
			System.out.println("Has introducido un valor incorrecto debes insertar valores del 1 - 3");
			teclado.nextLine();
			opcion = -989;
		} catch (Exception e) {
			System.out.println("Error");
		}
		return opcion;
	}

	public static void registroUsuario() {
		String dniNie = "";
		boolean validarConstrasena;
		String contrasena;

		// validamos si el formato del dni o nie es correcto
		boolean validarDNINIE = false;
		do {
			System.out.println("\t\r Para registrate introduce el DNI / NIE: ");
			dniNie = teclado.next();
			validarDNINIE = Cliente.validarDNINIE(dniNie);

			if (validarDNINIE == false) {
				System.out.println("\t\r Has introducido un DNI o NIE incorrecto ");
			}

		} while (validarDNINIE == false);

		System.out.println("\t\r Introduce el nombre : ");
		String nombre = teclado.next();
		System.out.println("\t\r Introduce el apellido: ");
		String apellido = teclado.next();
		do {
			validarConstrasena = true;
			System.out.println("\t\r Introduce la contraseña: (Debe tener al menos 10 caracteres) ");
			contrasena = teclado.next();
			if (contrasena.length() != 10) {
				System.out.println("\t\r Contraseña incorrecta debes insertar al menos 10 caracteres");
				validarConstrasena = false;
			}
		} while (validarConstrasena == false);

		Cliente cliente = new Cliente(dniNie, nombre, apellido, contrasena);
		//System.out.println(cliente.toString());

		boolean resultado = Cliente.insertarCliente(cliente);
		if (resultado == true) {
			System.out.println("\t\r Cliente registrado con exito");
		} else {
			System.out.println("\t\r Error al intentar resgistrar el cliente");
		}

	}

	public static void comprarBillete() {
		Cliente cliente = null;
		do {
			System.out.println("\t\r Introduce el DNI / NIE : ");
			String dniNie = teclado.next();
			System.out.println("\t\r Introduce la contraseña : ");
			String contrasena = teclado.next();

			cliente = Cliente.loguearCliente(dniNie, contrasena);

			// cliente = ClienteBD.loguearCliente(dniNie, contrasena);

			if (cliente == null) {
				System.out.println("\t\r ** Usuario no registrado, pulsa la Opción 1 en el Menú Inicial **");
				//como hacer para que de aquí vuelva al menú inicial
			} else {
				System.out.println("\t\r Bienvenido/a " + cliente.getNombre() + " " + cliente.getApellido());
				System.out.println("\t\r Estas son los recorridos que ofrecemos;\n "
						+ "\t\r Elige la línea que mejor te convenga pulsando las Opciones 1, 2, o 3 \n ");
				
				LineaDeAutobus lineaSeleccionada = obtenerLineaDeAutobus();
				ArrayList<Parada> paradas = listarParadas(lineaSeleccionada);
				Parada paradaInicio = obtenerParadaInicio(paradas);
				Parada paradaFin = obtenerParadaFin(paradas);
				Calendar fechaInsertada = obtenerFecha();
				Calendar fechaSeleccionada = obtenerFechaHorario(paradaInicio, paradaFin, fechaInsertada,
						lineaSeleccionada, paradas);
				pagoBillete(lineaSeleccionada, paradaInicio, paradaFin, cliente, fechaSeleccionada, paradas);

			}
		} while (cliente == null);

	}

	public static LineaDeAutobus obtenerLineaDeAutobus() {
		ArrayList<LineaDeAutobus> lineasDeAutobuses = LineaDeAutobus.mostrarLineasDeAutobus();

		for (int i = 0; i < lineasDeAutobuses.size(); i++) {
			LineaDeAutobus linea = lineasDeAutobuses.get(i);
			System.out.println("\t\r ** Opcion " + (i + 1) + ": " + linea.getCodLineaAutobus() + " " + linea.getNombre());
		}

		System.out.println("\t\r Introduce la opción deseada : ");
		int lineaAutobus = teclado.nextInt();
		
		System.out.println("\r\t Has elegido la línea " + + linea.getCodLineaAutobus() + " " + linea.getNombre());

		LineaDeAutobus lineaSeleccionada = lineasDeAutobuses.get(lineaAutobus - 1);

		return lineaSeleccionada;
	}

	public static ArrayList<Parada> listarParadas(LineaDeAutobus lineaAutobus) {

		ArrayList<Parada> listaParadas = ParadaBD.obtenerParadas(lineaAutobus);

		for (int i = 0; i < listaParadas.size(); i++) {
			Parada parada = listaParadas.get(i);
			System.out.println("Opcion " + parada.getNumOrden() + " : " + " " + parada.getNomParada());
		}

		return listaParadas;
	}

	public static Parada obtenerParadaInicio(ArrayList<Parada> listaParadas) {
		System.out.println("Introduce la parada de inicio : ");
		int paradaInicio = teclado.nextInt();

		Parada paradaInicioSeleccionada = listaParadas.get(paradaInicio - 1);

		return paradaInicioSeleccionada;
	}

	public static Parada obtenerParadaFin(ArrayList<Parada> listaParadas) {
		System.out.println("Introduce la parada de fin");
		int paradaFin = teclado.nextInt();
		teclado.nextLine();

		Parada paradaFinSeleccionada = listaParadas.get(paradaFin - 1);

		return paradaFinSeleccionada;
	}

	public static Calendar obtenerFecha() {
		System.out.println("Introduce la fecha y hora de viaje");
		String fechaViaje = teclado.nextLine();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES"));

		try {
			cal.setTime(sdf.parse(fechaViaje));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return cal;
	}

	// METODO OBTENERFECHAHORARIO HAY QUE REDUCIR EL CODIGO

	public static Calendar obtenerFechaHorario(Parada paradaInicio, Parada paradaFin, Calendar horarioIntroducido,
			LineaDeAutobus linea, ArrayList<Parada> listaParadas) {

		Calendar resultado = Calendar.getInstance();

		String horaInicioFin = "";
		SimpleDateFormat sdfHoraMin = new SimpleDateFormat("HH:mm");

		if (paradaInicio.getNumOrden() < paradaFin.getNumOrden()) {
			horaInicioFin = "asc";
			LineaDeAutobus horariosLinea = LineaDeAutobus.mostrarHorarioLineaAscDesc(linea, horaInicioFin);

			System.out.println(sdfHoraMin.format(horariosLinea.getHoraInicio()) + " -"
					+ sdfHoraMin.format(horariosLinea.getHoraFin()) + " Frecuencia de : "
					+ horariosLinea.getFrecuencia() + "mins");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Calendar primeraHoraParadaInicial = Calendar.getInstance();

			primeraHoraParadaInicial.setTime(horariosLinea.getHoraInicio());
			primeraHoraParadaInicial.set(Calendar.DAY_OF_MONTH, horarioIntroducido.get(Calendar.DAY_OF_MONTH));
			primeraHoraParadaInicial.set(Calendar.MONTH, horarioIntroducido.get(Calendar.MONTH));
			primeraHoraParadaInicial.set(Calendar.YEAR, horarioIntroducido.get(Calendar.YEAR));

			for (int i = 1; i < paradaInicio.getNumOrden(); i++) {
				Parada p = listaParadas.get(i - 1);
				primeraHoraParadaInicial.add(Calendar.MINUTE, p.getIntervalo());
			}

			Calendar horaInicioParadaSeleccionada = Calendar.getInstance();
			horaInicioParadaSeleccionada.setTime(primeraHoraParadaInicial.getTime());

			while (primeraHoraParadaInicial.compareTo(horarioIntroducido) <= 0) {
				primeraHoraParadaInicial.add(Calendar.MINUTE, horariosLinea.getFrecuencia());
			}

			System.out.println("Estas son las horas disponibles sobre la hora solicitada:");

			Calendar primeraHora = Calendar.getInstance();
			primeraHora.setTime(primeraHoraParadaInicial.getTime());
			primeraHora.add(Calendar.MINUTE, -horariosLinea.getFrecuencia());

			Calendar segundaHora = Calendar.getInstance();
			segundaHora.setTime(primeraHoraParadaInicial.getTime());

			Calendar terceraHora = Calendar.getInstance();
			terceraHora.setTime(primeraHoraParadaInicial.getTime());
			terceraHora.add(Calendar.MINUTE, +horariosLinea.getFrecuencia());

			Calendar horaFinLinea = Calendar.getInstance();
			horaFinLinea.setTime(horariosLinea.getHoraFin());
			horaFinLinea.set(Calendar.DAY_OF_MONTH, horarioIntroducido.get(Calendar.DAY_OF_MONTH));
			horaFinLinea.set(Calendar.MONTH, horarioIntroducido.get(Calendar.MONTH));
			horaFinLinea.set(Calendar.YEAR, horarioIntroducido.get(Calendar.YEAR));

			if (terceraHora.compareTo(horaFinLinea) > 0) {
				terceraHora.setTime(horaInicioParadaSeleccionada.getTime());
				terceraHora.add(Calendar.DAY_OF_MONTH, +1);
			}

			System.out.println("Opcion 1 : " + sdf.format(primeraHora.getTime()));
			System.out.println("Opcion 2 : " + sdf.format(segundaHora.getTime()));
			System.out.println("Opcion 3 : " + sdf.format(terceraHora.getTime()));

			System.out.println("Introduce la hora deseada : ");
			int opcionFecha = teclado.nextInt();

			switch (opcionFecha) {
			case 1:
				resultado = primeraHora;
				break;
			case 2:
				resultado = segundaHora;
				break;
			case 3:
				resultado = terceraHora;
				break;
			}

		} else {
			horaInicioFin = "desc";
			LineaDeAutobus horariosLinea = LineaDeAutobus.mostrarHorarioLineaAscDesc(linea, horaInicioFin);
			System.out.println(horariosLinea.getHoraInicio() + " " + horariosLinea.getHoraFin() + " "
					+ horariosLinea.getFrecuencia());

			Calendar primeraHoraParadaInicial = Calendar.getInstance();
			primeraHoraParadaInicial.setTime(horariosLinea.getHoraInicio());

			for (int i = listaParadas.size() - 2; i >= (paradaInicio.getNumOrden() - 1); i--) {
				Parada p = listaParadas.get(i);
				primeraHoraParadaInicial.add(Calendar.MINUTE, p.getIntervalo());
			}

			System.out.println(sdfHoraMin.format(primeraHoraParadaInicial.getTime()));

			// compareTo compara entre dos objetos tipo calendar
			// devuleve un 0 si es igual /un -1 si es anterior a la hora / un 1 si es mayor
			// a la hora
			// https://www.tutorialspoint.com/java/util/calendar_compareto.htm

			while (primeraHoraParadaInicial.compareTo(horarioIntroducido) <= 0) {
				primeraHoraParadaInicial.add(Calendar.MINUTE, horariosLinea.getFrecuencia());
			}

			System.out.println("Estas son las horas disponibles sobre la hora solicitada:");

			Calendar primeraHora = Calendar.getInstance();
			primeraHora.setTime(primeraHoraParadaInicial.getTime());
			primeraHora.add(Calendar.MINUTE, -horariosLinea.getFrecuencia());
			System.out.println("Opcion 1 : " + sdfHoraMin.format(primeraHora.getTime()));

			Calendar segundaHora = Calendar.getInstance();
			segundaHora.setTime(primeraHoraParadaInicial.getTime());
			System.out.println("Opcion 2 : " + sdfHoraMin.format(segundaHora.getTime()));

			Calendar terceraHora = Calendar.getInstance();
			terceraHora.setTime(primeraHoraParadaInicial.getTime());
			terceraHora.add(Calendar.MINUTE, +horariosLinea.getFrecuencia());
			System.out.println("Opcion 3 : " + sdfHoraMin.format(terceraHora.getTime()));

			int opcionFecha = teclado.nextInt();

			switch (opcionFecha) {
			case 1:
				resultado = primeraHora;
				break;
			case 2:
				resultado = segundaHora;
				break;
			case 3:
				resultado = terceraHora;
				break;
			}

		}

		return resultado;

	}

	public static void pagoBillete(LineaDeAutobus lineaSeleccionada, Parada paradaInicio, Parada paradaFin,
			Cliente cliente, Calendar fechaSeleccionada, ArrayList<Parada> paradas) {

		double importe = calcularImporte(paradaInicio, paradaFin, lineaSeleccionada);
		double dinero = 0;
		boolean control;
		String respuesta = "";

		do {
			System.out.println("El importe total por pagar es : " + importe);
			System.out.println("Por favor introduce el dinero : ");

			try {
				dinero = teclado.nextDouble();
				control = true;
				if (dinero < 0) {
					System.out.println("Debes introducir un numero positivo");
					control = false;
				}
			} catch (InputMismatchException e) {
				teclado.nextLine();
				System.out.println("Debes insertar un valor numerico");
				control = false;
			}

		} while (control == false && dinero < 0);

		if (dinero >= importe) {

			// si introduce el dinero entonces se añade el billete
			Billete billete = new Billete();
			Calendar horaLlegada = calcularHoraLlegada(paradaInicio, paradaFin, paradas, fechaSeleccionada);

			billete.setCliente(cliente);
			billete.setFechaHoraLlegada(horaLlegada.getTime());
			billete.setFechaHoraSalida(fechaSeleccionada.getTime());
			billete.setImporteBillete(importe);
			billete.setLineaDeAutobus(lineaSeleccionada);
			billete.setParadaFin(paradaFin);
			billete.setParadaInicio(paradaInicio);

			boolean resultado = Billete.insertarBillete(billete);

			if (resultado == true) {
				System.out.println("Compra realizada con exito");
				System.out.println("Recoja su ticket por favor");
			} else {
				System.out.println("No se ha podido comprar el billete");
			}

			if (dinero > importe) {
				double dev = dinero - importe;
				dev = Math.round(dev * 100.00) / 100.00;
				System.out.println("Total a devolver es: " + dev);

				devolucion(dev);

			}
		}

		while (dinero < importe) {

			importe = importe - dinero;
			importe = Math.round(importe * 100.00) / 100.00;
//			Volvemos a asignar la variable importeTotal para redondearla y que salga con dos digitos,asi nos evitamos
//			los errores que hemos tenido para redondear
			System.out.println("El importe faltante por pagar es : " + importe);

			System.out.println("¿Desea realizar el pago restante S/N?");
			respuesta = teclado.next();
			respuesta = respuesta.toUpperCase();
			while (!(respuesta.equals("S") || respuesta.equals("N"))) {
				System.out.println("Error debes introducir un caracter S o N");
				System.out.println("¿Desea realizar el pago restante S/N?");
				respuesta = teclado.next();
				respuesta = respuesta.toUpperCase();
			}
			if (respuesta.equals("S")) {

				do {
					System.out.println("Introduce el dinero restante: ");

					try {
						dinero = teclado.nextDouble();
						control = true;
						if (dinero < 0) {
							System.out.println("Debes introducir un numero positivo");
							control = false;
						}
					} catch (InputMismatchException e) {
						teclado.nextLine();
						System.out.println("Debes insertar un valor numerico");
						control = false;
					}

				} while (control == false && dinero < 0);

			} else if (respuesta.equals("N")) {
				// debemos darle la opcion de calcelar y devolver el dinero insertado
			}
		}

	}

	public static void devolucion(double dev) {
		int devolver = (int) (dev * 100);
		int billetesMonedas[] = { 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50, 20, 10, 5, 2, 1 };

//		Al principio hicimos esta función con if,pero nos dimos cuenta que con for era un código más limpio
		for (int i = 0; i < billetesMonedas.length; i++) {
			if (devolver >= billetesMonedas[i]) {
				if (billetesMonedas[i] >= 500) {
					System.out.println(
							" Billetes de " + (billetesMonedas[i] / 100) + " euros : " + devolver / billetesMonedas[i]);
					devolver %= billetesMonedas[i];
				}
				if (billetesMonedas[i] >= 100 && billetesMonedas[i] <= 200) {
					System.out.println(
							" Monedas de " + (billetesMonedas[i] / 100) + " euros : " + devolver / billetesMonedas[i]);
					devolver %= billetesMonedas[i];
				}
				if (billetesMonedas[i] >= 1 && billetesMonedas[i] <= 50) {
					System.out.println(
							" Monedas de " + billetesMonedas[i] + " centimos : " + devolver / billetesMonedas[i]);
					devolver %= billetesMonedas[i];
				}
			}

		}

	}

	public static double calcularImporte(Parada paradaInicio, Parada paradaFin, LineaDeAutobus linea) {
		int numeroParadas = 0;
		if (paradaInicio.getNumOrden() < paradaFin.getNumOrden()) {
			for (int i = paradaInicio.getNumOrden(); i <= paradaFin.getNumOrden(); i++) {
				numeroParadas++;
			}
		} else {
			for (int i = paradaFin.getNumOrden(); i >= paradaInicio.getNumOrden(); i--) {
				numeroParadas++;
			}
		}

		double resultado = linea.getPrecioLinea() * numeroParadas;

		return resultado;
	}

	public static Calendar calcularHoraLlegada(Parada paradaInicio, Parada paradaFin, ArrayList<Parada> paradas,
			Calendar fechaSeleccionada) {
		Calendar resultado = Calendar.getInstance();
		resultado.setTime(fechaSeleccionada.getTime());

		if (paradaInicio.getNumOrden() < paradaFin.getNumOrden()) {
			for (int i = paradaInicio.getNumOrden(); i < paradaFin.getNumOrden(); i++) {
				Parada parada = paradas.get(i - 1);
				resultado.add(Calendar.MINUTE, +parada.getIntervalo());
			}
		} else {
			for (int i = paradaFin.getNumOrden() - 2; i >= (paradaInicio.getNumOrden() - 1); i--) {
				Parada parada = paradas.get(i);
				resultado.add(Calendar.MINUTE, +parada.getIntervalo());
			}
		}
		return resultado;

	}

}
