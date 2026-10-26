package classes;

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
import jakarta.persistence.OneToMany;

@Entity
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "objectiu")
	private String objectiu;

	@OneToMany(mappedBy = "rol", fetch = FetchType.EAGER)
	private List<Jugador> jugadors = new ArrayList<Jugador>();

	public Rol() {

	}


	public Rol(String objectiu) {
		super();
		this.objectiu = objectiu;
	}
	
	public void addJugador(Jugador jugador) {
		this.jugadors.add(jugador);
	}

	public String getObjectiu() {
		return objectiu;
	}

	public void setObjectiu(String objectiu) {
		this.objectiu = objectiu;
	}

	public int getId() {
		return id;
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
		Rol other = (Rol) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", objectiu=" + objectiu + "]";
	}

}
