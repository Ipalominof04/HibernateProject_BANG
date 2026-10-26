package dao;

import classes.Partida;

public interface IPartidaDao extends IGenericDao<Partida, Integer> {

	public void LlistarJugadorsPartida(int idPartida);
	
	public void MostrarPartida(int idPartida);
	
	public int IniciarPartida();
	
	public void ComprobarVictoria(int idPartida);
	
	public String MostrarCarta();


}
