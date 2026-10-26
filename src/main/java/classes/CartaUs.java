package classes;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("US")
public class CartaUs extends Carta {

	@Enumerated(EnumType.STRING)
	@Column(name = "tipusUS")
	private TipusUs tipus;

	public CartaUs() {

	}

	public CartaUs(String nom, String descripcio, String coll, TipusUs tipus) {
		super(nom, descripcio,coll);
		this.tipus = tipus;
	}

	public TipusUs getTipus() {
		return tipus;
	}

	public void setTipus(TipusUs tipus) {
		this.tipus = tipus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tipus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartaUs other = (CartaUs) obj;
		return tipus == other.tipus;
	}

	@Override
	public String toString() {
		return "CartaUs [tipus=" + tipus + "]";
	}

}
