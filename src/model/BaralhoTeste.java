
package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class BaralhoTeste {

    @Test
    public void testInicializacaoBaralho() {
        Baralho baralho = new Baralho();

        int contador = 0;
        while (true) {
            try {
                baralho.comprar();
                contador++;
            } catch (IllegalStateException e) {
                break;
            }
        }

        assertEquals(416, contador);
    }

    //método embaralhar não é testado já que apenas usa a função já existente 'shuffle'.

    @Test
    public void testComprarCarta() {
        Baralho baralho = new Baralho();

        Carta cartaComprada = baralho.comprar();
        assertNotNull(cartaComprada);

        for (int i = 1; i < 416; i++) {
            baralho.comprar();
        }

        try {
            baralho.comprar();
            fail("Comprar de um baralho vazio deveria lançar IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("O baralho está vazio!", e.getMessage());
        }
    }
}
