package main;

import java.util.Scanner;

import org.hibernate.HibernateException;

import dao.JugadorDao;
import dao.PartidaDao;

public class TestBang {

	static PartidaDao partidaDAO = new PartidaDao();
	static JugadorDao jugadorDAO = new JugadorDao();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		try {
			int idPartida = partidaDAO.IniciarPartida();
			System.out.println("\n✅ Partida iniciada con ID: " + idPartida);

	partidaDAO.LlistarJugadorsPartida(idPartida);

			System.out.println("\n---- SELECCIONA TU JUGADOR ----");
			System.out.print("Ingresa el ID del jugador: ");
			int jugadorActual = sc.nextInt();

			boolean seguirJugando = true;

			while (seguirJugando) {
				mostrarMenu();
				int opcion = sc.nextInt();

				switch (opcion) {
				case 1 -> mostrarMano(jugadorActual);
				case 2 -> mostrarPartida(idPartida);
				case 3 -> equiparCarta(jugadorActual, idPartida);
				case 4 -> usarBang(jugadorActual, idPartida);
				case 5 -> descartarCarta(jugadorActual, idPartida);
				case 6 -> robarCarta(jugadorActual, idPartida);
				case 7 -> comprobarEliminacion(jugadorActual, idPartida);
				case 8 -> pasarTurno(jugadorActual, idPartida);
				case 9 -> comprobarVictoria(idPartida);
				case 0 -> seguirJugando = false;
				default -> System.out.println("Opción no válida. Intenta nuevamente.");
				}
			}

			System.out.println("Gracias por jugar. ¡Hasta la próxima!");

		} catch (HibernateException e) {
			System.err.println("Error durante la ejecución del juego: " + e.getMessage());
		}
	}

	private static void mostrarMenu() {
		System.out.println("\n---- MENÚ DE ACCIONES ----");
		System.out.println("1. Mostrar mano del jugador");
		System.out.println("2. Mostrar partida");
		System.out.println("3. Equipar carta");
		System.out.println("4. Usar BANG");
		System.out.println("5. Descartar carta");
		System.out.println("6. Robar carta");
		System.out.println("7. Comprobar eliminación");
		System.out.println("8. Pasar turno");
		System.out.println("9. Comprobar victoria");
		System.out.println("0. Salir");
		System.out.print("Selecciona una opción: ");
	}

	private static void mostrarMano(int jugadorActual) {
		System.out.println("\n---- MANO DEL JUGADOR ----");
		jugadorDAO.MostrarMaJugador(jugadorActual);
	}

	private static void mostrarPartida(int idPartida) {
		System.out.println("\n---- ESTADO DE LA PARTIDA ----");
		partidaDAO.MostrarPartida(idPartida);
	}

	private static void equiparCarta(int jugadorActual, int idPartida) {
		mostrarMano(jugadorActual);
		System.out.print("Ingrese el ID de la carta que desea equipar: ");
		int idCarta = sc.nextInt();
		jugadorDAO.EquiparCarta(jugadorActual, idCarta, idPartida);
	}

	private static void usarBang(int jugadorActual, int idPartida) {
		System.out.print("Ingrese el ID del jugador a atacar: ");
		int jObjetivo = sc.nextInt();
		jugadorDAO.UsarBang(jugadorActual, jObjetivo, idPartida);
	}

	private static void descartarCarta(int jugadorActual, int idPartida) {
		mostrarMano(jugadorActual);
		System.out.print("Ingrese el ID de la carta a descartar: ");
		int idCarta = sc.nextInt();
		jugadorDAO.DescartarCarta(jugadorActual, idCarta, idPartida);
	}

	private static void robarCarta(int jugadorActual, int idPartida) {
		jugadorDAO.RobarCarta(jugadorActual, idPartida);
		mostrarMano(jugadorActual);
	}

	private static void comprobarEliminacion(int jugadorActual, int idPartida) {
		jugadorDAO.comprobarEliminacio(jugadorActual, idPartida);
	}

	private static void pasarTurno(int jugadorActual, int idPartida) {
		jugadorDAO.PassarTorn(jugadorActual, idPartida);
	}

	private static void comprobarVictoria(int idPartida) {
		partidaDAO.ComprobarVictoria(idPartida);
	}
}
