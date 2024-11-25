package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class JogadorTeste {

    private Jogador jogador;

    @Before
    public void setUp() {
        jogador = new Jogador();
    }

    @Test
    public void testeReceberCarta() {
        jogador.receberCarta(new Carta(Carta.Naipe.h, 5));
        jogador.receberCarta(new Carta(Carta.Naipe.d, 10));
        
        List<Carta> cartas = jogador.getCartas();
        assertEquals(2, cartas.size());
        assertEquals("5h", cartas.get(0).toString());
        assertEquals("10d", cartas.get(1).toString());
    }

    @Test
    public void testeGetValorMaoSemAs() {
        jogador.receberCarta(new Carta(Carta.Naipe.c, 9));
        jogador.receberCarta(new Carta(Carta.Naipe.s, 8));
        
        assertEquals(17, jogador.getValorMao());
    }

    @Test
    public void testeGetValorMaoComAs() {
        jogador.receberCarta(new Carta(Carta.Naipe.h, 1));
        jogador.receberCarta(new Carta(Carta.Naipe.d, 6));
        
        assertEquals(17, jogador.getValorMao());
    }

    @Test
    public void testeGetValorMaoComAsEstouro() {
        jogador.receberCarta(new Carta(Carta.Naipe.h, 1));
        jogador.receberCarta(new Carta(Carta.Naipe.d, 10));
        jogador.receberCarta(new Carta(Carta.Naipe.s, 10));
        
        assertEquals(21, jogador.getValorMao());
    }

    @Test
    public void testeReceberCartaSplit() {
        jogador.receberCartaSplit(new Carta(Carta.Naipe.h, 4));
        jogador.receberCartaSplit(new Carta(Carta.Naipe.d, 7));
        
        List<Carta> cartasSplit = jogador.getCartasSplit();
        assertEquals(2, cartasSplit.size());
        assertEquals("4h", cartasSplit.get(0).toString());
        assertEquals("7d", cartasSplit.get(1).toString());
    }

    @Test
    public void testeGetValorMaoSplitSemAs() {
        jogador.receberCartaSplit(new Carta(Carta.Naipe.c, 9));
        jogador.receberCartaSplit(new Carta(Carta.Naipe.s, 6));
        
        assertEquals(15, jogador.getValorMaoSplit());
    }

    @Test
    public void testeGetValorMaoSplitComAs() {
        jogador.receberCartaSplit(new Carta(Carta.Naipe.h, 1));
        jogador.receberCartaSplit(new Carta(Carta.Naipe.d, 5));
        
        assertEquals(16, jogador.getValorMaoSplit());
    }

    @Test
    public void testeGetValorMaoSplitComAsEstouro() {
        jogador.receberCartaSplit(new Carta(Carta.Naipe.h, 1));
        jogador.receberCartaSplit(new Carta(Carta.Naipe.d, 10));
        jogador.receberCartaSplit(new Carta(Carta.Naipe.s, 5));
        
        assertEquals(16, jogador.getValorMaoSplit());
    }
    
    @Test
    public void testeEstourou() {
        jogador.receberCarta(new Carta(Carta.Naipe.c, 10));
        jogador.receberCarta(new Carta(Carta.Naipe.s, 5));
        jogador.receberCarta(new Carta(Carta.Naipe.h, 7));
        
        assertTrue(jogador.estourou());
    }

    @Test
    public void testeRealizarSplit() {
        jogador.receberCarta(new Carta(Carta.Naipe.h, 8));
        jogador.receberCarta(new Carta(Carta.Naipe.d, 8));
        
        jogador.realizarSplit();
        
        List<Carta> maoOriginal = jogador.getCartas();
        List<Carta> maoSplit = jogador.getCartasSplit();
        
        assertEquals(1, maoOriginal.size());
        assertEquals(1, maoSplit.size());
        assertEquals("8d", maoOriginal.get(0).toString());
        assertEquals("8h", maoSplit.get(0).toString());
    }

    @Test
    public void testeSplitInvalido() {
        jogador.receberCarta(new Carta(Carta.Naipe.h, 7));
        jogador.receberCarta(new Carta(Carta.Naipe.d, 6));
        
        jogador.realizarSplit();
        
        assertEquals(2, jogador.getCartas().size());
        assertTrue(jogador.getCartasSplit().isEmpty());
    }
    
    @Test
    public void testeLimparMao() {
        jogador.receberCarta(new Carta(Carta.Naipe.c, 10));
        jogador.receberCarta(new Carta(Carta.Naipe.d, 3));
        
        assertEquals(2, jogador.getCartas().size());
        
        jogador.limparMao();
        assertTrue(jogador.getCartas().isEmpty());
    }
}