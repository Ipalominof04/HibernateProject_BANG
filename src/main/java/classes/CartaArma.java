package classes;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("ARMA")
public class CartaArma extends Carta {

	@Column(name = "distancia")
	private int distancia;

	@OneToOne(mappedBy = "armaEquipada")
	public Jugador jugador;

	public CartaArma() {

	}

	public CartaArma(String nom, String descripcio,String coll, int distancia) {
		super(nom, descripcio,coll);
		this.distancia = distancia;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

//	public Jugador getJugador() {
//		return jugador;
//	}
//
//	public void setJugador(Jugador jugador) {
//		this.jugador = jugador;
//	}

	@Override
	public String toString() {
		return "CartaArma [distancia=" + distancia + "]";
	}

}
