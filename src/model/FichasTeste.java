package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FichasTeste {

    private Fichas fichas;

    @Before
    public void setUp() {
        fichas = new Fichas();
    }

    @Test
    public void testeSaldoInicial() {
        assertEquals(2400, fichas.getQuantidade());
    }

    @Test
    public void testeApostaValida() {
        boolean resultado = fichas.apostar(150);
        assertTrue(resultado);
        assertEquals(2250, fichas.getQuantidade());
    }
    
    @Test
    public void testegetQuantidadeSucesso() {
        boolean resultado = fichas.apostar(200);
        assertTrue(resultado);
        assertEquals(2200, fichas.getQuantidade());
    }
    
    @Test
    public void testegetQuantidadeFalha() {
        boolean resultado = fichas.apostar(200);
        assertTrue(resultado);
        assertNotEquals(2300, fichas.getQuantidade());
    }

    @Test
    public void testeApostaInvalidaMenorQueMinimo() {
        boolean resultado = fichas.apostar(30);
        assertFalse(resultado);
    }

    @Test
    public void testeApostaInvalidaSaldoInsuficiente() {
        boolean resultado = fichas.apostar(2500);
        assertFalse(resultado);
    }


    @Test
    public void testeGanharFichas() {
        fichas.ganhar(200);
        assertEquals(2600, fichas.getQuantidade());
    }

    @Test
    public void testeApostaEsgotaFichas() {
        assertTrue(fichas.apostar(2400));
        assertEquals(0, fichas.getQuantidade());
    }

    @Test
    public void testeApostarSemSaldo() {
        fichas.apostar(2400);
        assertFalse(fichas.apostar(10));
    }
}
