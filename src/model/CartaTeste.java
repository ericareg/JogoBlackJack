package model;

import static org.junit.Assert.*;

import model.Carta.Naipe;
import org.junit.Test;

public class CartaTeste {
	
	 @Test
	 public void testeGetValor() {
		 Carta cartaNumero = new Carta(Naipe.h, 8);
	     assertEquals(8, cartaNumero.getValor());
	        
	     Carta cartaValete = new Carta(Naipe.s, 11); //J
	     assertEquals(10, cartaValete.getValor());
	        
	     Carta cartaAs = new Carta(Naipe.d, 1); //A
	     assertEquals(1, cartaAs.getValor());
	 }

	 @Test
	 public void testeGetNaipe() {
		 Carta cartaCopas = new Carta(Naipe.h, 4);
	     assertEquals(Naipe.h, cartaCopas.getNaipe());
	        
	     Carta cartaEspadas = new Carta(Naipe.s, 10);
	     assertEquals(Naipe.s, cartaEspadas.getNaipe());
	 }

    @Test
    public void testeCartaComValorNumerico() {
        Carta carta1 = new Carta(Naipe.h, 7);
        assertEquals(7, carta1.getValor());
        assertEquals("7h", carta1.toString());
        
        Carta carta2 = new Carta(Naipe.c, 5);
        assertEquals(5, carta2.getValor());
        assertEquals("5c", carta2.toString());
        
        Carta carta3 = new Carta(Naipe.s, 2);
        assertEquals(2, carta3.getValor());
        assertEquals("2s", carta3.toString());
    }
   
    @Test
    public void testeCartaComValorJ() {
        Carta carta = new Carta(Naipe.d, 11);
        assertEquals(10, carta.getValor());
        assertEquals("Jd", carta.toString());
    }
    
    @Test
    public void testeCartaComValorQ() {
        Carta carta = new Carta(Naipe.s, 12);
        assertEquals(10, carta.getValor());
        assertEquals("Qs", carta.toString());
    }

    @Test
    public void testeCartaComValorK() {
        Carta carta = new Carta(Naipe.c, 13);
        assertEquals(10, carta.getValor());
        assertEquals("Kc", carta.toString());
    }

    @Test
    public void testeCartaComValorA() {
        Carta carta = new Carta(Naipe.s, 1);
        assertEquals(1, carta.getValor());
        assertEquals("As", carta.toString());
    }
    
    @Test
    public void testeCartaComValorSuperiorA10() {
        Carta carta = new Carta(Naipe.d, 15);
        assertEquals(10, carta.getValor());
        assertEquals("15d", carta.toString());
    }

    @Test
    public void testeCartaComNaipe() {
        Carta carta = new Carta(Naipe.c, 10);
        assertEquals(Naipe.c, carta.getNaipe());
        assertEquals("10c", carta.toString());
    }
}
