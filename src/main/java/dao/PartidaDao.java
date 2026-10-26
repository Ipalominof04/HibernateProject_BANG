package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import classes.Carta;
import classes.CartaArma;
import classes.CartaEquipament;
import classes.CartaUs;
import classes.Jugador;
import classes.Partida;
import classes.Rol;
import classes.TipusEquipament;
import classes.TipusUs;

public class PartidaDao extends GenericDao<Partida, Integer> implements IPartidaDao {

	@Override
	public void LlistarJugadorsPartida(int idPartida) {
		Partida partida = get(idPartida);

		List<Jugador> jugadors = partida.getJugadors();

		System.out.println("LLISTA DE JUGADORS PER PARTIDA: ");
		for (Jugador j : jugadors) {
			System.out.println("ID: " + j.getId() + " NOM: " + j.getNom());
		}

	}

	@Override
	public void MostrarPartida(int idPartida) {
		Partida partida = get(idPartida);

		List<Jugador> jugadors = partida.getJugadors();

		for (Jugador j : jugadors) {
			if (j.getVidaActual() > 0)
				System.out.println("NOM: " + j.getNom());
			System.out.println("VIDA: " + j.getVidaActual());
			System.out.println("CARTAS EQUIPADES: " + j.getArmaEquipada());
			Rol rol = j.getRol();
			if (rol.getObjectiu().equals("Sheriff")) {
				System.out.println("ROL: " + j.getRol());
			}
		}

	}

	@Override
	public int IniciarPartida() {

		JugadorDao jugadorDAO = new JugadorDao();
		Partida partida = new Partida("en joc", 0, LocalDate.now());

		System.out.println("CREO ROLES");
		Rol sheriff = new Rol("Sheriff");
		Rol renegat = new Rol("Renegat");
		Rol malfactor = new Rol("malfactor");
		Rol ajudant = new Rol("ajudant");

		System.out.println("CREO JUGADORES");
		Jugador jugador1 = new Jugador("Karlos", 10, 10, 1, 2);
		Jugador jugador2 = new Jugador("Sergio", 10, 10, 2, 3);
		Jugador jugador3 = new Jugador("Samir", 10, 10, 2, 2);
		Jugador jugador4 = new Jugador("Thiago", 10, 10, 1, 1);

		jugador1.setRol(sheriff);
		jugador2.setRol(renegat);
		jugador3.setRol(malfactor);
		jugador4.setRol(ajudant);

		// Agregar jugadores a la partida
		partida.addJugador(jugador1);
		partida.addJugador(jugador2);
		partida.addJugador(jugador3);
		partida.addJugador(jugador4);

		this.saveOrUpdate(partida);

		  // 💡 Establecer distancias entre jugadores (mesa circular)
	    List<Jugador> jugadores2 = List.of(jugador1, jugador2, jugador3, jugador4);

	    for (int i = 0; i < jugadores2.size(); i++) {
	        Jugador actual = jugadores2.get(i);
	        for (int d = 1; d < jugadores2.size(); d++) {
	            // usar módulo para que la lista sea circular
	            Jugador vecino = jugadores2.get((i + d) % jugadores2.size());
	            actual.addJugadorDistancia(vecino);
	        }
	    }

	    // Guardar relación de distancias en BD
	    for (Jugador j : jugadores2) {
	        jugadorDAO.saveOrUpdate(j);
	    }

	    System.out.println("✅ Distancias entre jugadores asignadas (mesa circular).");

	    this.saveOrUpdate(partida);
	    
		System.out.println("CREO CARTAS");

		// Crear cartas de uso
		List<CartaUs> cartasUs = List.of(
			    new CartaUs("BANG", "carta us", "PICA", TipusUs.BANG),
			    new CartaUs("BANG", "carta us", "CORAZON", TipusUs.BANG),
			    new CartaUs("BANG", "carta us", "TREBOL", TipusUs.BANG),
			    new CartaUs("BANG", "carta us", "DIAMANTE", TipusUs.BANG),

			    new CartaUs("BIRRA", "carta us", "PICA", TipusUs.BIRRA),
			    new CartaUs("BIRRA", "carta us", "CORAZON", TipusUs.BIRRA),
			    new CartaUs("BIRRA", "carta us", "TREBOL", TipusUs.BIRRA),
			    new CartaUs("BIRRA", "carta us", "DIAMANTE", TipusUs.BIRRA),

			    new CartaUs("FALLASTE", "carta us", "PICA", TipusUs.FALLASTE),
			    new CartaUs("FALLASTE", "carta us", "CORAZON", TipusUs.FALLASTE),
			    new CartaUs("FALLASTE", "carta us", "TREBOL", TipusUs.FALLASTE),
			    new CartaUs("FALLASTE", "carta us", "DIAMANTE", TipusUs.FALLASTE)
			);



		// Crear cartas de equipamiento
		List<CartaEquipament> cartasDefensa = List.of(
			    new CartaEquipament("BARRIL", "carta defensa", "TREBOL", TipusEquipament.BARRIL, 0),
			    new CartaEquipament("BARRIL", "carta defensa", "PICA", TipusEquipament.BARRIL, 0),
			    new CartaEquipament("CAVALL", "carta defensa", "CORAZON", TipusEquipament.CAVALL, 1),
			    new CartaEquipament("CAVALL", "carta defensa", "DIAMANTE", TipusEquipament.CAVALL, 1),
			    new CartaEquipament("MIRA TELESCOPICA", "carta defensa", "TREBOL", TipusEquipament.MIRA_TELESCOPICA, 1),
			    new CartaEquipament("MIRA TELESCOPICA", "carta defensa", "CORAZON", TipusEquipament.MIRA_TELESCOPICA, 1)
			);


		// Crear cartas de arma
		CartaArma default1 = new CartaArma("Colt.45", "arma", "TREBOL", 1);
		CartaArma default2 = new CartaArma("Colt.45", "arma", "PICA", 1);
		CartaArma default3 = new CartaArma("Colt.45", "arma", "DIAMANTE", 1);
		CartaArma default4 = new CartaArma("Colt.45", "arma", "CORAZON", 1);

		CartaArma arma1 = new CartaArma("Volcanic", "arma", "CORAZON", 1);
		CartaArma arma2 = new CartaArma("Schofield", "arma", "PICA", 2);
		CartaArma arma3 = new CartaArma("Winchester", "arma", "DIAMANTE", 5);
		CartaArma arma4 = new CartaArma("Winchester prueba", "arma", "DIAMANTE", 5);



		// Añadir todas las cartas a la partida
		cartasUs.forEach(partida::addCartaMano);
		cartasDefensa.forEach(partida::addCartaMano);
		partida.addCartaMano(arma1);
		partida.addCartaMano(arma2);
		partida.addCartaMano(arma3);
		partida.addCartaMano(arma4);

		this.saveOrUpdate(partida);

		// Equipar arma por defecto
		jugador1.equiparArma(default1);
		jugador1.addCarta(arma4);
		jugador2.equiparArma(default2);
		jugador3.equiparArma(default3);
		jugador4.equiparArma(default4);

		// Repartir cartas
		List<Jugador> jugadores = List.of(jugador1, jugador2, jugador3, jugador4);
		List<Carta> manoJuego = new ArrayList<>(partida.getCartas());
		Collections.shuffle(manoJuego);

		int index = 0;
		for (Jugador j : jugadores) {
			for (int i = 0; i < 4 && index < manoJuego.size(); i++) {
				Carta carta = manoJuego.get(index++);
				carta.setJugador(j);
				j.addCarta(carta);
			}
			jugadorDAO.saveOrUpdate(j);
		}

		System.out.println("✅ Se han repartido 4 cartas a cada jugador.");

		this.saveOrUpdate(partida);
	    return partida.getId();

	}

	@Override
	public void ComprobarVictoria(int idPartida) {
		Partida partida = get(idPartida);

		List<Jugador> jugadors = partida.getJugadors();

		boolean sheriffViu = false;
		boolean malfactorsVius = false;
		boolean renegatViu = false;
		String guanyador;
		boolean partidaFinalitzada;

		for (Jugador j : jugadors) {
			if (j.getVidaActual() > 0) {
				switch (j.getRol().getObjectiu()) {
				case "Sheriff":
					sheriffViu = true;
					break;
				case "Malfactor":
					malfactorsVius = true;
					break;
				case "Renegat":
					renegatViu = true;
					break;
				}
			}
		}
		int count = 0;
		for (Jugador j : jugadors) {
			if (j.getVidaActual() > 0)
				count++;
		}

		if (!sheriffViu && malfactorsVius) {

			guanyador = "Malfactors";
			partidaFinalitzada = true;
			System.out.println("Guanyen els Malfactors!");
		} else if (!malfactorsVius && !renegatViu && sheriffViu) {

			guanyador = "Sheriff i ajudants";
			partidaFinalitzada = true;
			System.out.println("Guanya el Sheriff i els seus ajudants!");
		} else if (sheriffViu && !malfactorsVius && renegatViu && count == 2) {

			guanyador = "Renegat";
			partidaFinalitzada = true;
			System.out.println("Guanya el Renegat!");
		} else {
			guanyador = null;
		}
		this.saveOrUpdate(partida);
	}
	
	@Override
	public String MostrarCarta() {
		Partida partida = this.get(1);
		
		Carta carta = partida.getCartas().remove(0);
			
		partida.getCartasDescartadas().add(carta);
		
		return "Nom carta: " + carta.getColl();
	}

}
