package classes;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("EQUIPAMENT")
public class CartaEquipament extends Carta {

	@Enumerated(EnumType.STRING)
	@Column(name = "tipusEquipament")
	private TipusEquipament tipus;

	@Column(name = "modificadorDistancia")
	private int modificadorDistancia;

	@ManyToOne
	@JoinColumn(name = "jugadorEquip_id")
	public Jugador jugadorEquip;

	public CartaEquipament() {

	}

	public CartaEquipament(String nom, String descripcio, String coll, TipusEquipament tipus, int modificadorDistancia) {
		super(nom, descripcio,coll);
		this.tipus = tipus;
		this.modificadorDistancia = modificadorDistancia;
	}
	

	public TipusEquipament getTipus() {
		return tipus;
	}

	public void setTipus(TipusEquipament tipus) {
		this.tipus = tipus;
	}

	public int getModificadorDistancia() {
		return modificadorDistancia;
	}

	public void setModificadorDistancia(int modificadorDistancia) {
		this.modificadorDistancia = modificadorDistancia;
	}

//	public Jugador getJugadorEquip() {
//		return jugadorEquip;
//	}
//
//	public void setJugadorEquip(Jugador jugadorEquip) {
//		this.jugadorEquip = jugadorEquip;
//	}

	@Override
	public String toString() {
		return "CartaEquipament [tipus=" + tipus + ", modificadorDistancia=" + modificadorDistancia + "]";
	}

}
