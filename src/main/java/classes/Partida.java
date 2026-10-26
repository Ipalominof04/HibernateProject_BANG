package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Partida {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "estat", nullable = true)
	private String estat;

	@Column(name = "torn")
	private int torn;

	@Column(name = "dataInici")
	LocalDate dataInici;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "partida_jugador", joinColumns = @JoinColumn(name = "partida_id"), inverseJoinColumns = @JoinColumn(name = "jugador_id"))
	private List<Jugador> jugadors = new ArrayList<Jugador>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "partida_carta", joinColumns = @JoinColumn(name = "partida_id"), inverseJoinColumns = @JoinColumn(name = "carta_id"))
	private List<Carta> cartas = new ArrayList<Carta>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "partida_carta_descartada", joinColumns = @JoinColumn(name = "partida_id"), inverseJoinColumns = @JoinColumn(name = "carta_id"))
	private List<Carta> cartasDescartadas = new ArrayList<Carta>();

	public Partida() {

	}

	public Partida(String estat, int torn, LocalDate dataInici) {
		super();
		this.estat = estat;
		this.torn = torn;
		this.dataInici = dataInici;
	}

	public void addCartaMano(Carta carta) {
		this.cartas.add(carta);
	}

	public void addCartaManoDesechable(Carta carta) {
		this.cartasDescartadas.add(carta);
	}

	public void addJugador(Jugador jugador) {
		this.jugadors.add(jugador);
		jugador.getPartidas().add(this);
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public int getTorn() {
		return torn;
	}

	public void setTorn(int torn) {
		this.torn = torn;
	}

	public LocalDate getDataInici() {
		return dataInici;
	}

	public void setDataInici(LocalDate dataInici) {
		this.dataInici = dataInici;
	}

	public int getId() {
		return id;
	}

	public List<Jugador> getJugadors() {
		return jugadors;
	}

	public void setJugadors(List<Jugador> jugadors) {
		this.jugadors = jugadors;
	}

	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	public List<Carta> getCartasDescartadas() {
		return cartasDescartadas;
	}

	public void setCartasDescartadas(List<Carta> cartasDescartadas) {
		this.cartasDescartadas = cartasDescartadas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partida other = (Partida) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Partida [id=" + id + ", estat=" + estat + ", torn=" + torn + ", dataInici=" + dataInici + "]";
	}

}
