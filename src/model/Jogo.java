package model;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Jogo  implements Serializable {
	
private static final long serialVersionUID = 1L;
private static Jogo instancia;
private final Baralho baralho;
private final Jogador jogador;
private final Dealer dealer;
private int aposta;
private double creditoJogador; // Valor do crédito do jogador
private boolean rodadaAtiva;
private String ultimoResultado;

	private Jogo() {
			baralho = new Baralho();
			jogador = new Jogador();
			dealer = new Dealer();
	       this.creditoJogador = 2400.0; // Definir apenas para novos jogos
			rodadaAtiva = false;
		}
	public static Jogo getInstancia() {
		if (instancia == null) {
			instancia = new Jogo();
		}
		return instancia;
	}
	
	public static void setInstancia(Jogo jogo) {
		   instancia = jogo;
		}
	
	
	public Jogador getJogador() {
	    return jogador;
	}
	public void iniciarNovaRodada(int apostaInicial) {
		if (rodadaAtiva) {
			throw new IllegalStateException("Rodada já está ativa!");
		}
		this.aposta = apostaInicial;
		rodadaAtiva = true;
		baralho.embaralhar();
		jogador.limparMao();
		dealer.limparMao();
		jogador.receberCarta(baralho.comprar());
		jogador.receberCarta(baralho.comprar());
		dealer.receberCarta(baralho.comprar());
		dealer.receberCarta(baralho.comprar());
	}
	
	public void jogadorPedirCarta() {
	    if (!rodadaAtiva) {
	        throw new IllegalStateException("Nenhuma rodada ativa!");
	    }
	    jogador.receberCarta(baralho.comprar());
	    if (jogador.estourou()) {
	    	 SwingUtilities.invokeLater(() -> {
	             JOptionPane.showMessageDialog(null, "Jogador estourou! Dealer vence!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
	         });
	         finalizarRodada("Jogador estourou! Dealer vence!");
	       
	       
	    }
	}
	public void jogadorPedirCartaSplit() {
		if (!rodadaAtiva) {
			throw new IllegalStateException("Nenhuma rodada ativa!");
		}
		jogador.receberCartaSplit(baralho.comprar());
		if (jogador.estourou()) {
			finalizarRodada("Jogador estourou! Dealer vence!");
			
		}
	}
	public void jogadorManter() {
		if (!rodadaAtiva) {
			throw new IllegalStateException("Nenhuma rodada ativa!");
		}
		while (dealer.devePedirCarta()) {
			dealer.receberCarta(baralho.comprar());
		}
		determinarVencedor();
	}
		public void jogadorDobrar() {
		    if (!rodadaAtiva) {
		        throw new IllegalStateException("Nenhuma rodada ativa!");
		    }
		    aposta *= 2;
		    jogador.receberCarta(baralho.comprar());
		    // Verifica se o jogador estourou
		    if (jogador.estourou()) {
		    	SwingUtilities.invokeLater(() -> {
		             JOptionPane.showMessageDialog(null, "Jogador estourou! Dealer vence!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
		         });
		        finalizarRodada("Jogador estourou! Dealer vence!");
		        determinarVencedor();
		    } else {
		        jogadorManter(); // Dealer joga normalmente
		    }
		}
	public void jogadorRender() {
		if (!rodadaAtiva) {
			throw new IllegalStateException("Nenhuma rodada ativa!");
		}
		finalizarRodada("Jogador se rendeu.");
	}

	private void determinarVencedor() {
	    if (dealer.estourou() || jogador.getValorMao() > dealer.getValorMao()) {
	        // Jogador venceu
	        creditoJogador += aposta * 2; // Recupera a aposta + prêmio equivalente
	        finalizarRodada("Jogador venceu!");
	        SwingUtilities.invokeLater(() -> {
	            JOptionPane.showMessageDialog(null, "Jogador vence!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
	        });
	    } else if (jogador.getValorMao() < dealer.getValorMao()) {
	        // Dealer venceu
	        finalizarRodada("Dealer venceu!");
	        SwingUtilities.invokeLater(() -> {
	            JOptionPane.showMessageDialog(null, "Dealer vence!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
	        });
	    } else { 
	        // Empate (Push)
	        creditoJogador += aposta; // Devolve a aposta ao jogador
	        finalizarRodada("Empate!");
	        SwingUtilities.invokeLater(() -> {
	            JOptionPane.showMessageDialog(null, "Empate!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
	        });
	    }

	    // Zera a aposta após a rodada
	    aposta = 0;
	}


	
	private void finalizarRodada(String resultado) {
		rodadaAtiva = false;
		ultimoResultado = resultado;
	}
	public boolean isRodadaAtiva() {
		return rodadaAtiva;
	}
	public String getUltimoResultado() {
		return ultimoResultado;
	}
	public int getValorMaoJogador() {
		return jogador.getValorMao();
	}
	public int getValorMaoDealer() {
		return dealer.getValorMao();
		}
	public int getValorMaoSplit() {
		return jogador.getValorMaoSplit();
	}
	public List<Carta> getCartasJogador() {
	   return new ArrayList<>(jogador.getCartas());
	}
	public List<Carta> getCartasDealer() {
	   return new ArrayList<>(dealer.getCartas());
	}
	public List<Carta> getCartasSplit() {
	   return new ArrayList<>(jogador.getCartasSplit());
	}
	public void jogadorSplit() {
	   if (!rodadaAtiva) {
	       throw new IllegalStateException("Nenhuma rodada ativa!");
	   }
	   jogador.realizarSplit();
	}
	public void atualizarCredito(double credito) {
	   this.creditoJogador = credito;
	}

	// Método para salvar o estado completo do jogo
	public static boolean salvarJogo(String caminhoArquivo) {
	   try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
	       oos.writeObject(instancia); // Salva a instância do jogo
	       System.out.println("Jogo carregado com crédito: " + instancia.creditoJogador);
	       return true;
	   } catch (IOException e) {
	       e.printStackTrace();
	       return false;
	   }
	}
	
	// Método para carregar o jogo depois de salvo
	public static Jogo carregarJogo(String caminhoArquivo) {
	   try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
	       instancia = (Jogo) ois.readObject(); // Carrega a instância do jogo
	       System.out.println("Jogo carregado com crédito: " + instancia.creditoJogador);
	       return instancia;
	   } catch (IOException | ClassNotFoundException e) {
	       e.printStackTrace();
	       return null;
	   }
	}
	public void setCredito(double credito) {
	   this.creditoJogador = credito;
	}
	public void setAposta(int aposta) {
	   this.aposta = aposta;
	}
	public double getAposta() {
	   return this.aposta;
	}
	public double getCredito() {
	   return this.creditoJogador;
	}
	public boolean isJogandoSplit() {
	   return jogador.isJogandoSplit();
	}
	
	public void encerrarRodada(String resultado) {
	    finalizarRodada(resultado); // Mantém o método privado
	    aposta = 0;
	}
	public void limparEstado() {
	    instancia = null;
	    rodadaAtiva = false;
	    ultimoResultado = null;
	}
}
