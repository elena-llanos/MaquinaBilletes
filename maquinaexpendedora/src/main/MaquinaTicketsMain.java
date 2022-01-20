package main;

import java.text.DecimalFormat;
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
	static double vueltas = 0;
	static double dev;
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

		} while (opcion != 3);

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

	// Método para registrar usuario nuevo. Validamos si el formato del DNI/NIE es
	// correcto y si la contraseña cumple el formato estableccido.
	public static void registroUsuario() {
		String dniNie = "";
		boolean validarConstrasena = true;
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
			System.out.println("\t\r Introduce la contraseña: (Debe tener al menos 8 caracteres) ");
			contrasena = teclado.next();

			if (contrasena.length() != 8) {
				System.out.println("\t\r Contraseña incorrecta debes insertar al menos 8 caracteres");
				validarConstrasena = false;
			}else {
				validarConstrasena = true;
			}
		} while (validarConstrasena == false);

		Cliente cliente = new Cliente(dniNie, nombre, apellido, contrasena);

		boolean resultado = Cliente.insertarCliente(cliente);
		if (resultado == true) {
			System.out.println("\t\r Cliente registrado con exito");
		} else {
			System.out.println("\t\r Error al intentar resgistrar el cliente");
		}

	}

	// Este método inicia el proceso de compra, con el login del cliente, y le da
	// las opciones de reocrridos al usuario.
	public static void comprarBillete() {
		Cliente cliente = null;
		do {
			System.out.println("\t\r Introduce el DNI / NIE : ");
			String dniNie = teclado.next();
			System.out.println("\t\r Introduce la contraseña : ");
			String contrasena = teclado.next();

			cliente = Cliente.loguearCliente(dniNie, contrasena);

			if (cliente == null) {
				System.out.println("\t\r ** Usuario o contraseña incorrecta **");
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

	// Mediante este arrayList de líneas podemos mostrar las opciones de línea al
	// usuario.
	public static LineaDeAutobus obtenerLineaDeAutobus() {
		ArrayList<LineaDeAutobus> lineasDeAutobuses = LineaDeAutobus.mostrarLineasDeAutobus();

		for (int i = 0; i < lineasDeAutobuses.size(); i++) {
			LineaDeAutobus linea = lineasDeAutobuses.get(i);
			System.out
					.println("\t\r ** Opcion " + (i + 1) + ": " + linea.getCodLineaAutobus() + " " + linea.getNombre());
		}
		int lineaAutobus = 0;
		boolean control;
		do {
			System.out.println("\t\r Introduce la opción deseada : ");
			control = true;
			try {
				lineaAutobus = teclado.nextInt();
				teclado.nextLine();
			} catch (InputMismatchException i) {
				System.out.println("Has introducido un valor incorrecto");
				teclado.nextLine();
				control = false;
			} catch (Exception e) {
				System.out.println("Error");
				control = false;
			}

			if ((!((lineaAutobus >= 1) && (lineaAutobus <= 3))) && (control == true)) {
				System.out.println("Linea no existente");
			}

		} while (!((lineaAutobus >= 1) && (lineaAutobus <= lineasDeAutobuses.size())) || (control == false));

		LineaDeAutobus lineaSeleccionada = lineasDeAutobuses.get(lineaAutobus - 1);
		System.out.println("\r\t Has elegido la línea " + lineaSeleccionada.getCodLineaAutobus() + "   "
				+ lineaSeleccionada.getNombre() + "\n");
		return lineaSeleccionada;
	}

	// Mediante este arrayList mostramos las paradas.
	public static ArrayList<Parada> listarParadas(LineaDeAutobus lineaAutobus) {

		ArrayList<Parada> listaParadas = ParadaBD.obtenerParadas(lineaAutobus);

		for (int i = 0; i < listaParadas.size(); i++) {
			Parada parada = listaParadas.get(i);
			System.out.println("\t\r * Parada " + parada.getNumOrden() + " : " + " " + parada.getNomParada());
		}

		return listaParadas;
	}

	// Este método es para dar la opción de elegir la parada de inicio.
	public static Parada obtenerParadaInicio(ArrayList<Parada> listaParadas) {

		int paradaInicio = 0;
		boolean control;
		do {
			System.out.println("\t\r Elige el número de la parada en que quieres subir : \n");
			try {
				paradaInicio = teclado.nextInt();
				teclado.nextLine();
				control = true;
			} catch (InputMismatchException i) {
				System.out.println("Has introducido un valor incorrecto");
				teclado.nextLine();
				control = false;
			} catch (Exception e) {
				System.out.println("Error");
				control = false;
			}

			if (!((paradaInicio >= 1) && (paradaInicio <= listaParadas.size())) && (control == true)) {
				System.out.println("Parada no existente");
			}

		} while (!((paradaInicio >= 1) && (paradaInicio <= listaParadas.size())) || (control == false));

		Parada paradaInicioSeleccionada = listaParadas.get(paradaInicio - 1);
		System.out.println("\t\r Parada incio elegida  : " + paradaInicioSeleccionada.getNomParada());

		return paradaInicioSeleccionada;
	}

	// Este método es para dar la opción de elegir la parada de fin
	public static Parada obtenerParadaFin(ArrayList<Parada> listaParadas) {
		int paradaFin = 0;
		boolean control;
		do {
			System.out.println("\t\r Elige el número de la parada en que quieres bajar : \n");
			try {
				paradaFin = teclado.nextInt();
				teclado.nextLine();
				control = true;
			} catch (InputMismatchException i) {
				System.out.println("Has introducido un valor incorrecto");
				teclado.nextLine();
				control = false;
			} catch (Exception e) {
				System.out.println("Error");
				control = false;
			}
			if (!((paradaFin >= 1) && (paradaFin <= listaParadas.size())) && (control == true)) {
				System.out.println("Parada no existente");
			}

		} while (!((paradaFin >= 1) && (paradaFin <= listaParadas.size())) || (control == false));

		Parada paradaFinSeleccionada = listaParadas.get(paradaFin - 1);
		System.out.println("\t\r Parada final elegida : " + paradaFinSeleccionada.getNomParada());

		return paradaFinSeleccionada;
	}

	// Al comparar un String con un Calenda no obtengo las fechas 15/16/17/18 de
	// febrero
	// Mediante la clase Calendar validamos las fechas en formato correcto teniendo
	// en cuenta la fecha actual.
	public static Calendar obtenerFecha() {
		String fechaViaje = null;
		Calendar calActual = Calendar.getInstance();
		Calendar calViaje = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES"));
		do {
			// try {
			System.out.println("\t\r Introduce la fecha y hora de viaje:    (dd/MM/yyyy HH:mm)");
			fechaViaje = teclado.nextLine();
			try {
				calViaje.setTime(sdf.parse(fechaViaje));
			} catch (ParseException e) {
				System.out.println("\t\r Formato incorrecto");
				e.printStackTrace();
			}
			if (calViaje.before(calActual)) {
				System.out.println("\t\r Tenga en cuenta que la fecha introdica no debe de ser posterior a hoy ");
			}

		} while (calViaje.before(calActual));

		return calViaje;

	}

	// Con este método obtenemos la fecha y hora que se aproxima al horario
	// solicitado por el usuario.
	// Se le da a elegir entre rtes opciones.
	public static Calendar obtenerFechaHorario(Parada paradaInicio, Parada paradaFin, Calendar horarioIntroducido,
			LineaDeAutobus linea, ArrayList<Parada> listaParadas) {

		Calendar resultado = Calendar.getInstance();

		SimpleDateFormat sdfHoraMin = new SimpleDateFormat("HH:mm");
		LineaDeAutobus horariosLinea = new LineaDeAutobus();
		Calendar primeraHoraParadaInicial = Calendar.getInstance();

		if (paradaInicio.getNumOrden() < paradaFin.getNumOrden()) {
			horariosLinea = LineaDeAutobus.mostrarHorarioLineaAscDesc(linea, "asc");

			primeraHoraParadaInicial.setTime(horariosLinea.getHoraInicio());

			for (int i = 1; i < paradaInicio.getNumOrden(); i++) {
				Parada p = listaParadas.get(i - 1);
				primeraHoraParadaInicial.add(Calendar.MINUTE, p.getIntervalo());
			}

		} else {
			horariosLinea = LineaDeAutobus.mostrarHorarioLineaAscDesc(linea, "desc");

			primeraHoraParadaInicial.setTime(horariosLinea.getHoraInicio());

			for (int i = listaParadas.size() - 2; i >= (paradaInicio.getNumOrden() - 1); i--) {
				Parada p = listaParadas.get(i);
				primeraHoraParadaInicial.add(Calendar.MINUTE, p.getIntervalo());
			}

		}

		System.out.println("\t\r El horario de la línea es: " + sdfHoraMin.format(horariosLinea.getHoraInicio()) + " - "
				+ sdfHoraMin.format(horariosLinea.getHoraFin()) + " Y tiene frecuencia de : "
				+ horariosLinea.getFrecuencia() + " mins");

		primeraHoraParadaInicial.set(Calendar.DAY_OF_MONTH, horarioIntroducido.get(Calendar.DAY_OF_MONTH));
		primeraHoraParadaInicial.set(Calendar.MONTH, horarioIntroducido.get(Calendar.MONTH));
		primeraHoraParadaInicial.set(Calendar.YEAR, horarioIntroducido.get(Calendar.YEAR));

		Calendar horaInicioParadaSeleccionada = Calendar.getInstance();
		horaInicioParadaSeleccionada.setTime(primeraHoraParadaInicial.getTime());

		// compareTo compara entre dos objetos tipo calendar
		// devuleve un 0 si es igual /un -1 si es anterior a la hora / un 1 si es mayor
		// a la hora
		// https://www.tutorialspoint.com/java/util/calendar_compareto.htm
		while (primeraHoraParadaInicial.compareTo(horarioIntroducido) < 0) {
			primeraHoraParadaInicial.add(Calendar.MINUTE, horariosLinea.getFrecuencia());
		}

		System.out.println("\t\r Estas son las horas disponibles sobre la hora solicitada:");

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

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");

		System.out.println("\t *Opcion 1 : " + sdf.format(primeraHora.getTime()));
		System.out.println("\t *Opcion 2 : " + sdf.format(segundaHora.getTime()));
		System.out.println("\t *Opcion 3 : " + sdf.format(terceraHora.getTime()));

		System.out.println("\t\r Introduce la Opción de la hora deseada : ");
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

		return resultado;

	}

	public static void pagoBillete(LineaDeAutobus lineaSeleccionada, Parada paradaInicio, Parada paradaFin,
			Cliente cliente, Calendar fechaSeleccionada, ArrayList<Parada> paradas) {

		double importe = calcularImporte(paradaInicio, paradaFin, lineaSeleccionada);
		double dinero = 0;
		boolean control;
		String respuesta = "";

		do {
			System.out.println("\t\r El importe total por pagar es : " + importe);

			try {

				dinero = puntocoma(dinero, dev);

				// dinero = teclado.nextDouble();

				control = true;
				if (dinero < 0) {
					System.out.println("\t\r Debes introducir un numero positivo");
					control = false;
				}
			} catch (InputMismatchException e) {
				teclado.nextLine();
				System.out.println("\t\r Debes insertar un valor numerico");
				control = false;
			}

		} while (control == false && dinero < 0);

		while (dinero < importe) {

			importe = importe - dinero;
			importe = Math.round(importe * 100.00) / 100.00;
			// Volvemos a asignar la variable importeTotal para redondearla y que salga con
			// dos digitos,asi nos evitamos
			// los errores que hemos tenido para redondear

			System.out.println("\t\r El importe faltante por pagar es : " + importe);

			System.out.println("\t\r ¿Desea realizar el pago restante S/N?");
			respuesta = teclado.next();
			respuesta = respuesta.toUpperCase();
			while (!(respuesta.equals("S") || respuesta.equals("N"))) {
				System.out.println("\t\r Error debes introducir un caracter S o N");
				System.out.println("\t\r ¿Desea realizar el pago restante S/N?");
				respuesta = teclado.next();
				respuesta = respuesta.toUpperCase();
			}
			if (respuesta.equals("S")) {

				do {
					dinero = puntocoma(dinero, dev);

					try {

						control = true;
						if (dinero < 0) {
							System.out.println("\t\r Debes introducir un numero positivo");
							control = false;
						}
					} catch (InputMismatchException e) {
						teclado.nextLine();
						System.out.println("\t\r Debes insertar un valor numerico");
						control = false;
					}

				} while (control == false && dinero < 0);

			} else if (respuesta.equals("N")) {

				dev = Math.round(dev * 100.00) / 100.00;
				DecimalFormat format = new DecimalFormat("#.##");
				System.out.print("VUELTAS : ");
				System.out.println(format.format(vueltas) + "");
				dev = vueltas;
				devolucion(dev);
				dinero = 0;
				importe = 0;
				control = false;

			}

		}
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
				System.out.println("\t\r  Compra realizada con exito");
				System.out.println("\t\r Recoja su ticket por favor");
				mostrarBillete(cliente, lineaSeleccionada, paradaInicio, paradaFin, fechaSeleccionada, importe,
						billete);
			} else {
				System.out.println("\t\r No se ha podido comprar el billete");
			}

			if (dinero > importe) {
				double dev = dinero - importe;
				dev = Math.round(dev * 100.00) / 100.00;
				System.out.println("\t\r Total a devolver es: " + dev);

				devolucion(dev);

			}
		}

	}

	public static double puntocoma(double dinero, double dev) {
		boolean control = false;
		// Esta funcion esta para que admita los puntos y las comas.
		do {
			String Sustituir = String.valueOf(dinero);
			System.out.println("\t\r Por favor introduce el dinero: ");
			Sustituir = teclado.next();
			teclado.nextLine();
			Sustituir = Sustituir.replace(",", ".");
			try {
				dinero = Double.parseDouble(Sustituir);
				vueltas = vueltas + dinero;

				control = false;
			} catch (Exception e) {
				System.out.println("\t\r Error. Debe ingresar un valor numérico");
				control = true;
			}
		} while (control);
		return dinero;
	}

	public static void devolucion(double dev) {
		int devolver = (int) (dev * 100);
		int billetesMonedas[] = { 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50, 20, 10, 5, 2, 1 };

//		Al principio hicimos esta función con if,pero nos dimos cuenta que con for era un código más limpio
		for (int i = 0; i < billetesMonedas.length; i++) {
			if (devolver >= billetesMonedas[i]) {
				if (billetesMonedas[i] >= 500) {
					System.out.println("\t\r Billetes de " + (billetesMonedas[i] / 100) + " euros : "
							+ devolver / billetesMonedas[i]);
					devolver %= billetesMonedas[i];
				}
				if (billetesMonedas[i] >= 100 && billetesMonedas[i] <= 200) {
					System.out.println("\t\r Monedas de " + (billetesMonedas[i] / 100) + " euros : "
							+ devolver / billetesMonedas[i]);
					devolver %= billetesMonedas[i];
				}
				if (billetesMonedas[i] >= 1 && billetesMonedas[i] <= 50) {
					System.out.println(
							"\t\r Monedas de " + billetesMonedas[i] + " centimos : " + devolver / billetesMonedas[i]);
					devolver %= billetesMonedas[i];
				}
			}

		}

	}

	// Cáculo del importe dependiendo del número de paradas que haga en el
	// recorrido.
	public static double calcularImporte(Parada paradaInicio, Parada paradaFin, LineaDeAutobus linea) {
		int numeroParadas = 0;
		if (paradaInicio.getNumOrden() < paradaFin.getNumOrden()) {
			for (int i = paradaInicio.getNumOrden(); i <= paradaFin.getNumOrden(); i++) {
				numeroParadas++;
			}
		} else {
			for (int i = paradaInicio.getNumOrden(); i >= paradaFin.getNumOrden(); i--) {
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
			for (int i = paradaInicio.getNumOrden() - 2; i >= (paradaFin.getNumOrden() - 1); i--) {
				Parada parada = paradas.get(i);
				resultado.add(Calendar.MINUTE, +parada.getIntervalo());
			}
		}
		return resultado;

	}

	public static void mostrarBillete(Cliente cliente, LineaDeAutobus lineaSeleccionada, Parada paradaInicio,
			Parada paradaFin, Calendar fechaSeleccionada, double importe, Billete fechaHoraLlegada) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println(
				"\t\t\r ************************************************************************************* \n");

		System.out.println(
				"\t **Cliente: " + cliente.getDniNie() + "\t *Nombre: " + cliente.getNombre() + "\t *Apellido: "
						+ cliente.getApellido() + "\n" + "\t *Línea: " + lineaSeleccionada.getCodLineaAutobus() + " "
						+ lineaSeleccionada.getNombre() + "\n" + "\t *Parada Inicial: " + paradaInicio.getNomParada()
						+ "\t *Parada Final:  " + paradaFin.getNomParada() + "\n" + "\t *Fecha y hora salida:       "
						+ sdf.format(fechaSeleccionada.getTime()) + "\t *Importe: " + importe + "\n"
						+ "\t *Fecha y hora llegada:      " + sdf.format(fechaHoraLlegada.getFechaHoraLlegada()));
		System.out.println(
				"\t\t\r ************************************************************************************* \n");
		
		double precioPagar = calcularImporte(paradaInicio, paradaFin, lineaSeleccionada);
		System.out.println(precioPagar);
	}
}