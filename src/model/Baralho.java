package model;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Baralho implements Serializable  {
    private final List<Carta> cartas = new ArrayList<>();

    public Baralho() {
        for (int i = 0; i < 8; i++) { // 8 baralhos
            for (Carta.Naipe naipe : Carta.Naipe.values()) {
                for (int valor = 1; valor <= 13; valor++) {
                    cartas.add(new Carta(naipe, valor));
                }
            }
        }
    }

    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    public Carta comprar() {
        if (cartas.isEmpty()) {
            throw new IllegalStateException("O baralho está vazio!");
        }
        return cartas.remove(0);
    }
}
