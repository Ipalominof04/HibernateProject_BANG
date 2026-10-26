package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import classes.Carta;
import classes.CartaArma;
import classes.CartaEquipament;
import classes.CartaUs;
import classes.Jugador;
import classes.Partida;
import classes.TipusUs;

public class JugadorDao extends GenericDao<Jugador, Integer> implements IJugadorDao {

	@Override
	public void MostrarMaJugador(int idJugador) {

		Jugador jugador = get(idJugador);

		List<Carta> ma = jugador.getMa();

		System.out.println("CARTAS DEL JUGADOR: ");
		for (Carta carta : ma) {
			System.out.println(
					"ID: " + carta.getId() + " NOM: " + carta.getNom() + " DESCRIPCION: " + carta.getDescripcio());
		}

	}

	@Override
	public void DescartarCarta(int idJugador, int idCarta, int idPartida) {
		Jugador jugador = null;
		PartidaDao pDAO = new PartidaDao();
		Partida p = pDAO.get(idPartida);

		Carta cartaEncontrada = null;
		List<Carta> cartasJugador = null;

		boolean encontrado = false;

		for (Jugador j : p.getJugadors()) {
			if (j.getId() == idJugador) {
				jugador = j;
				cartasJugador = new ArrayList<Carta>(jugador.getMa());
			}
		}

		for (Carta carta : cartasJugador) {
			if (carta.getId() == idCarta) {
				cartaEncontrada = carta;
				encontrado = true;
				break;
			}
		}
		if (!encontrado) {
			System.out.println("No tienes esa carta");
			System.out.println();
		} else {

			cartaEncontrada.setJugador(null);
			jugador.getMa().remove(cartaEncontrada);
			p.addCartaManoDesechable(cartaEncontrada);
			System.out.println("Se elimina la carta con el id: " + idCarta);

			this.saveOrUpdate(jugador);
		}
	}

	@Override
	public void comprobarEliminacio(int idJugador, int idPartida) {
		PartidaDao pDAO = new PartidaDao();

		Jugador jugador = null;

		Partida p = pDAO.get(idPartida);

		for (Jugador j : p.getJugadors()) {
			if (j.getId() == idJugador) {
				jugador = j;
			}

		}

		if (jugador.getVidaActual() <= 0) {
			List<Carta> cartasJugador = new ArrayList<Carta>(jugador.getMa());
			Iterator<Carta> iteratorCartas = cartasJugador.iterator();

			while (iteratorCartas.hasNext()) {
				Carta carta = iteratorCartas.next();

				carta.setJugador(null);
				jugador.getMa().remove(carta);
				p.addCartaManoDesechable(carta);
			}

		}

		pDAO.saveOrUpdate(p);

	}

	@Override
	public void RobarCarta(int idJugador, int idPartida) {
		PartidaDao pDAO = new PartidaDao();

		Jugador jugador = null;
		Partida p = pDAO.get(idPartida);

		for (Jugador j : p.getJugadors()) {
			if (j.getId() == idJugador) {
				jugador = j;
			}

		}

		if (jugador == null) {
			System.out.println("Jugador amb id " + idJugador + " no trobat a la partida.");
			return;
		}

		if (p.getCartas().isEmpty()) {
			System.out.println("No queden cartes per robar.");
			return;
		}

		Carta cartaRobada = p.getCartas().remove(0);
		jugador.getMa().add(cartaRobada);
		cartaRobada.setJugador(jugador);
		System.out.println("Jugador " + jugador.getNom() + " ha robat la carta: " + cartaRobada);
		this.saveOrUpdate(jugador);

	}

	@Override
	public void PassarTorn(int idJugador, int idPartida) {
		PartidaDao pDAO = new PartidaDao();

		Jugador jugador = null;
		;
		Partida partida = pDAO.get(idPartida);


		for (Jugador j : partida.getJugadors()) {
			if(j.getId() == idJugador) {
				jugador = j;
			}
			
		}

		if (jugador == null || partida == null) {
			System.out.println("Jugador o partida no trobats.");
			return;
		}

		int limitCartes = 4;

		if (jugador.getMa().size() > limitCartes) {
			System.out.println("Jugador " + jugador.getNom() + " té més de " + limitCartes
					+ " cartes. Es descartaran automàticament.");

			while (jugador.getMa().size() > limitCartes) {
				Carta cartaDescartada = jugador.getMa().remove(0);
				cartaDescartada.setJugador(null);
				jugador.getMa().remove(cartaDescartada);
				partida.addCartaManoDesechable(cartaDescartada);
				System.out.println("Carta descartada automàticament: " + cartaDescartada.getNom());
			}
		}

		List<Jugador> jugadors = partida.getJugadors();

		int indexActual = -1;
		for (int i = 0; i < jugadors.size(); i++) {
			if (jugadors.get(i).getId() == idJugador) {
				indexActual = i;
				break;
			}
		}

		if (indexActual == -1) {
			System.out.println("El jugador amb id " + idJugador + " no pertany a aquesta partida.");
			return;
		}

		int nouTorn = (indexActual + 1) % jugadors.size();
		partida.setTorn(nouTorn);

		Jugador seguent = jugadors.get(nouTorn);
		System.out.println("Torn passat. Ara juga: " + seguent.getNom());

		pDAO.saveOrUpdate(partida);
		this.saveOrUpdate(jugador);
	}

	@Override
	public void EquiparCarta(int idJugador, int idCarta, int idPartida) {
		PartidaDao pDAO = new PartidaDao();
		Jugador jugador = null;
		;
		Partida partida = pDAO.get(idPartida);

		Carta cartaEquipar = null;


		for (Jugador j : partida.getJugadors()) {
			if(j.getId() == idJugador) {
				jugador = j;
			}
			
		}

		for (Carta carta : jugador.getMa()) {
			if (carta.getId() == idCarta) {
				cartaEquipar = carta;
				break;
			}
		}

		if (cartaEquipar == null) {
			System.out.println("El jugador no tiene esa carta en la mano.");
			return;
		}

		if (cartaEquipar instanceof CartaArma) {
			CartaArma novaArma = (CartaArma) cartaEquipar;
			jugador.equiparArma(novaArma);
			jugador.getMa().remove(cartaEquipar);
			System.out.println(jugador.getNom() + " ha equipado el arma: " + novaArma.getNom());
		}

		else if (cartaEquipar instanceof CartaEquipament) {
			CartaEquipament nouEquip = (CartaEquipament) cartaEquipar;
			boolean yaTiene = false;

			for (CartaEquipament equip : jugador.getEquip()) {
				if (equip.getTipus() == nouEquip.getTipus()) {
					yaTiene = true;
					break;
				}
			}

			if (yaTiene) {
				System.out.println("Ya tienes un equipamiento del tipo " + nouEquip.getTipus());
				return;
			} else {
				jugador.getEquip().add(nouEquip);
				jugador.getMa().remove(cartaEquipar);
				System.out.println(jugador.getNom() + " ha equipado: " + nouEquip.getNom());
			}
		}

		else {
			System.out.println(" No se puede equipar este tipo de carta.");
			return;
		}
		pDAO.saveOrUpdate(partida);

	}

	@Override
	public void UsarBang(int idJugadorAtacant, int idJugadorObjectiu, int idPartida) {
		PartidaDao pDAO = new PartidaDao();
		Jugador atacante = null;
		Jugador objetivo = null;
		Partida partida = pDAO.get(idPartida);
		CartaUs bang = null;

		for (Jugador j_atacante : partida.getJugadors()) {
			atacante = j_atacante;
		}

		for (Jugador j_objetivo : partida.getJugadors()) {
			if (j_objetivo.getId() == idJugadorObjectiu)
				objetivo = j_objetivo;
		}

		for (Carta carta : atacante.getMa()) {
			if (carta instanceof CartaUs cu && cu.getTipus() == TipusUs.BANG) {
				bang = (CartaUs) cu;
				break;
			}
		}

		if (bang == null) {
			System.out.println(atacante.getNom() + " no tiene BANG!");
			return;
		}

		if (!ComprovarDistanciaAtac(atacante, objetivo)) {
			System.out.println("Objetivo fuera de rango!");
			return;
		}

		DescartarCarta(idJugadorAtacant, bang.getId(), idPartida);

		System.out.println(atacante.getNom() + " usa BANG contra " + objetivo.getNom());

		CartaUs fallaste = null;
		for (Carta carta : objetivo.getMa()) {
			if (carta instanceof CartaUs cu && cu.getTipus() == TipusUs.FALLASTE) {
				fallaste = cu;
				break;
			}
		}

		if (fallaste != null) {
			DescartarCarta(idJugadorObjectiu, fallaste.getId(), idPartida);
			System.out.println(objetivo.getNom() + " usa FALLASTE y evita el BANG!");
		} else {
			objetivo.setVidaActual(objetivo.getVidaActual() - 1);
			System.out.println("BANG impacta! " + objetivo.getNom() + " pierde 1 vida.");
		}
		pDAO.saveOrUpdate(partida);
	}

	@Override
	public int calcularDistancia(Jugador atacante, Jugador objetivo) {
		if (atacante.getId() == objetivo.getId())
			return 0;

		int indice = atacante.getDistancia().indexOf(objetivo);
		if (indice == -1)
			return Integer.MAX_VALUE;

		return Math.max(indice + 1 - atacante.getModificadorDistanciaOff() + objetivo.getModificadorDistanciaDef(), 1);
	}

	@Override
	public boolean ComprovarDistanciaAtac(Jugador atacante, Jugador objetivo) {
		return calcularDistancia(atacante,
				objetivo) <= (atacante.getArmaEquipada() != null ? atacante.getArmaEquipada().getDistancia() : 1);
	}

}
