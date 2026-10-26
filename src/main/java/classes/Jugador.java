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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String nom;

    @Column(name = "vidaActual")
    private int vidaActual;

    @Column(name = "vidaMaxima")
    private int vidaMaxima;

    @Column(name = "modificadorDistanciaDef")
    public int modificadorDistanciaDef;

    @Column(name = "modificadorDistanciaOff")
    public int modificadorDistanciaOff;

    @OneToMany(mappedBy = "jugador", fetch = FetchType.EAGER)
    public List<Carta> ma = new ArrayList<>();

    @ManyToMany(mappedBy = "jugadors", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Partida> partidas = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rol_id")
    public Rol rol;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartaArma_id")
    public CartaArma armaEquipada;

    @OneToMany(mappedBy = "jugadorEquip")
    public List<CartaEquipament> equip = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "jugador_distancia",
        joinColumns = @JoinColumn(name = "jugador_id"),
        inverseJoinColumns = @JoinColumn(name = "jugador_destino_id")
    )
    private List<Jugador> distancia = new ArrayList<>();


    public Jugador() {}

    public Jugador(String nom, int vidaActual, int vidaMaxima, int modificadorDistanciaDef, int modificadorDistanciaOff) {
        this.nom = nom;
        this.vidaActual = vidaActual;
        this.vidaMaxima = vidaMaxima;
        this.modificadorDistanciaDef = modificadorDistanciaDef;
        this.modificadorDistanciaOff = modificadorDistanciaOff;
    }

    public void equiparArma(CartaArma arma) {
        this.armaEquipada = arma;
        arma.setJugador(this);
    }

    public void addJugadorDistancia(Jugador jugador) {
        if (jugador != null && !this.distancia.contains(jugador)) {
            this.distancia.add(jugador);
        }
    }

    public void removeJugadorDistancia(Jugador jugador) {
        if (jugador != null) {
            this.distancia.remove(jugador);
        }
    }

    public void addCarta(Carta carta) {
        this.ma.add(carta);
        carta.setJugador(this);
    }

    public void addEquip(CartaEquipament carta) {
        this.equip.add(carta);
        carta.setJugador(this);
    }

    // Getters & Setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public int getModificadorDistanciaDef() {
        return modificadorDistanciaDef;
    }

    public void setModificadorDistanciaDef(int modificadorDistanciaDef) {
        this.modificadorDistanciaDef = modificadorDistanciaDef;
    }

    public int getModificadorDistanciaOff() {
        return modificadorDistanciaOff;
    }

    public void setModificadorDistanciaOff(int modificadorDistanciaOff) {
        this.modificadorDistanciaOff = modificadorDistanciaOff;
    }

    public List<CartaEquipament> getEquip() {
        return equip;
    }

    public void setEquip(List<CartaEquipament> equip) {
        this.equip = equip;
    }

    public List<Jugador> getDistancia() {
        return distancia;
    }

    public void setDistancia(List<Jugador> distancia) {
        this.distancia = distancia;
    }

    public int getId() {
        return id;
    }

    public List<Carta> getMa() {
        return ma;
    }

    public void setMa(List<Carta> ma) {
        this.ma = ma;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
        rol.addJugador(this);
    }

    public CartaArma getArmaEquipada() {
        return armaEquipada;
    }

    public void setArmaEquipada(CartaArma armaEquipada) {
        this.armaEquipada = armaEquipada;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Jugador other = (Jugador) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Jugador{id=").append(id)
          .append(", nom='").append(nom).append("'")
          .append(", vida=").append(vidaActual).append("/")
          .append(vidaMaxima).append(", distancia=[");
        for (Jugador j : distancia) {
            sb.append(j.getNom()).append(", ");
        }
        if (!distancia.isEmpty()) sb.setLength(sb.length() - 2);
        sb.append("]}");
        return sb.toString();
    }
}
