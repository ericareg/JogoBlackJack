package model;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
public class JogoTeste {
   private Jogo jogo;
   @Before
   public void setUp() {
       jogo = Jogo.getInstancia();
       jogo.limparEstado();
   }
   @Test
   public void testeIniciarNovaRodada() {
       jogo.iniciarNovaRodada(100);
       assertTrue(jogo.isRodadaAtiva());
       assertEquals(2, jogo.getCartasJogador().size());
       assertEquals(2, jogo.getCartasDealer().size());
   }
   @Test(expected = IllegalStateException.class)
   public void testeIniciarNovaRodadaQuandoJaAtiva() {
       jogo.iniciarNovaRodada(100);
       jogo.iniciarNovaRodada(50);
   }
   @Test
   public void testeJogadorPedirCarta() {
       jogo.iniciarNovaRodada(100);
       int tamanhoInicial = jogo.getCartasJogador().size();
       jogo.jogadorPedirCarta();
       assertEquals(tamanhoInicial + 1, jogo.getCartasJogador().size());
   }
   @Test(expected = IllegalStateException.class)
   public void testeJogadorPedirCartaSemRodadaAtiva() {
       jogo.jogadorPedirCarta();
   }
   @Test
   public void testeJogadorDobrar() {
       jogo.iniciarNovaRodada(100);
       jogo.jogadorDobrar();
       assertFalse(jogo.isRodadaAtiva());
   }
   @Test
   public void testeJogadorManter() {
       jogo.iniciarNovaRodada(100);
       jogo.jogadorManter();
       assertFalse(jogo.isRodadaAtiva());
       assertNotNull(jogo.getUltimoResultado());
   }
   @Test
   public void testeJogadorRender() {
       jogo.iniciarNovaRodada(100);
       jogo.jogadorRender();
       assertFalse(jogo.isRodadaAtiva());
       assertEquals("Jogador se rendeu. Metade da aposta foi devolvida.", jogo.getUltimoResultado());
   }
   @Test
   public void testeJogadorSplit() {
       jogo.iniciarNovaRodada(100);
       jogo.jogadorSplit();
       //System.out.println(jogo.getCartasJogador().size());
       //System.out.println(jogo.getCartasSplit().size());
       assertEquals(1, jogo.getCartasJogador().size());
       assertEquals(1, jogo.getCartasSplit().size());
   }
   @Test
   public void testeDeterminarVencedorJogadorVence() {
       jogo.iniciarNovaRodada(100);
       jogo.getCartasJogador().clear();
       jogo.getCartasDealer().clear();
       jogo.getCartasJogador().add(new Carta(Carta.Naipe.h, 10));
       jogo.getCartasJogador().add(new Carta(Carta.Naipe.s, 9));
       jogo.jogadorManter();
       assertEquals("Jogador venceu!", jogo.getUltimoResultado());
   }
   @Test
   public void testeFinalizarRodada() {
       jogo.iniciarNovaRodada(100);
       jogo.jogadorRender();
       assertFalse(jogo.isRodadaAtiva());
   }
   @Test
   public void testeGetValorMaoJogador() {
       jogo.iniciarNovaRodada(100);
       assertTrue(jogo.getValorMaoJogador() >= 2);
   }
}

