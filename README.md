# Funcionalidades

## JugadorDao

**MostrarMaJugador(idJugador)**
Muestra en consola todas las cartas que tiene un jugador en su mano.

**DescartarCarta(idJugador, idCarta, idPartida)**
Permite que un jugador descarte una carta de su mano y se mueva al mazo de descartes de la partida.

**ComprobarEliminacio(idJugador, idPartida)**
Si un jugador pierde toda su vida, descarta automáticamente todas sus cartas.

**RobarCarta(idJugador, idPartida)**
Permite que un jugador robe la primera carta del mazo de la partida y la agregue a su mano.

**PassarTorn(idJugador, idPartida)**
Pasa el turno al siguiente jugador en orden, descartando cartas automáticamente si excede el límite de 4.

**EquiparCarta(idJugador, idCarta, idPartida)**
Permite que un jugador equipe un arma o equipamiento de su mano, con reglas de tipo y límite de equipamiento.

**UsarBang(idJugadorAtacant, idJugadorObjectiu, idPartida)**
Realiza la acción de atacar a otro jugador usando la carta BANG, considerando la distancia y posibles defensas.

**calcularDistancia(atacante, objetivo)**
Calcula la distancia entre dos jugadores según la disposición circular de la mesa.

**ComprovarDistanciaAtac(atacante, objetivo)**
Verifica si un objetivo está al alcance del atacante según su arma equipada.

## PartidaDao

**LlistarJugadorsPartida(idPartida)**
Muestra la lista de jugadores presentes en una partida.

**MostrarPartida(idPartida)**
Muestra información completa de la partida: vida de jugadores, cartas equipadas y rol.

**IniciarPartida()**
Crea una nueva partida, asigna roles, genera jugadores, reparte cartas y establece distancias circulares.

**ComprobarVictoria(idPartida)**
Determina si la partida ha finalizado y quién es el ganador según las reglas de roles (Sheriff, Malfactor, Renegado, Ayudante).

**MostrarCarta()**
Toma la primera carta del mazo de la partida y la agrega al mazo de descartes, mostrando su nombre.