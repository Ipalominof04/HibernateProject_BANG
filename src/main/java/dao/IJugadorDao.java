package dao;

import classes.Jugador;

public interface IJugadorDao extends IGenericDao<Jugador, Integer> {

	public void MostrarMaJugador(int idJugador);

	public void DescartarCarta(int idKJugador, int idCarta, int idPartida);

	public void comprobarEliminacio(int idJugador, int idPartida);

	public void RobarCarta(int idJugador, int idPartida);

	public void PassarTorn(int idJugador, int idPartida);

	public void EquiparCarta(int idJugador, int idCarta, int idPartida);

	public void UsarBang(int idJugadorAtacant, int idJugadorObjectiu, int idPartida);

	public int calcularDistancia(Jugador atacante, Jugador objetivo);

	public boolean ComprovarDistanciaAtac(Jugador atacante, Jugador objetivo);

}
