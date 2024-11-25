package controller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import model.*;

public class ControladorJogo {
	private final Jogo jogo;
	FachadaModel fachada = new FachadaModel();
	public ControladorJogo() {
		this.jogo = Jogo.getInstancia();
	}
	
	// metodo para iniciar partida
	public void iniciarNovaRodada(int aposta) {
	   if (aposta < 50) {
	       System.out.println("Aposta mínima é de $50. Tente novamente.");
	       throw new IllegalArgumentException("Aposta inválida."); // Interrompe a execução
	   }
	   try {
	       jogo.iniciarNovaRodada(aposta);
	       System.out.println("Nova rodada iniciada. Boa sorte!\n");
	   } catch (IllegalStateException e) {
	       System.out.println("Erro: " + e.getMessage());
	   }
	}
		
	// metodo para pedir carta
	public void jogadorPedirCarta() {
		try {
			jogo.jogadorPedirCarta();
			exibirEstadoJogador();
			exibirEstadoDealer();
			if (!jogo.isRodadaAtiva()) {
				System.out.println(jogo.getUltimoResultado());
			}
		} catch (IllegalStateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public void jogadorPedirCartaSplit() {
		try {
			jogo.jogadorPedirCartaSplit();
			
			if (!jogo.isRodadaAtiva()) {
				System.out.println(jogo.getUltimoResultado());
			}
		} catch (IllegalStateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	// metodo para quando o jogador escolher manter
	public void jogadorManter() {
		try {
		jogo.jogadorManter();
		exibirEstadoJogador();
		exibirEstadoDealer();
			System.out.println(jogo.getUltimoResultado());
		} catch (IllegalStateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	// metodo para quando o jogador escolher double
	public void jogadorDobrar() {
	    try {
	        jogo.jogadorDobrar();
	        exibirEstadoJogador(); // Exibe o estado do jogador após dobrar
	        exibirEstadoDealer();
	        if (!jogo.isRodadaAtiva()) {
	            System.out.println(jogo.getUltimoResultado()); // Mostra o resultado final
	        }
	    } catch (IllegalStateException e) {
	        System.out.println("Erro: " + e.getMessage());
	    }
	}
	// metodo para quando o jogador escolher surrender
	public void jogadorRender() {
		try {
			jogo.jogadorRender();
			System.out.println(jogo.getUltimoResultado());
		} catch (IllegalStateException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
 
	public void exibirEstadoJogador() {
	    fachada.exibirEstadoJogador();
	}
	public void exibirEstadoDealer() {
		fachada.exibirEstadoDealer();
	}
	
	public void exibirEstadoSplit() {
	    fachada.exibirEstadoSplit();
	}
	
	public boolean isRodadaAtiva() {
		return jogo.isRodadaAtiva();
	}
	
	public void jogadorSplit() {
	    if (fachada.podeRealizarSplit()) {
	        // Lógica para dividir as cartas e criar duas mãos separadas
	    	fachada.jogadorSplit();
	        System.out.println("Você pode dividir suas cartas!\n");
	    } else {
	        System.out.println("Você não pode dividir suas cartas.\n");
	    }
	}
	public boolean podeRealizarSplit() {
	    return fachada.podeRealizarSplit();
	}
	
	
	
	public List<String> getCartasJogador() {
        return fachada.getCartasJogador();

    }
    public List<String> getCartasDealer() {
        return fachada.getCartasDealer();
                   
    }
    
 
    public void alternarMao() {
    	fachada.alternarMaoJogador();
    }
    
  
    
    public String getCartasDealerFormatadas() {
        return fachada.getCartasDealerFormatadas();
    }
    
    public String getCartasSplitFormatadas() {
        return getCartasSplitFormatadas();
    }
    public String getCartasJogadorFormatadas() {
        return fachada.getCartasJogadorFormatadas();
    }
    
    // Retorna o valor da mão do jogador
    public int getValorMaoJogador() {
        return fachada.getValorMaoJogador();
    }

    // Retorna o valor da mão do dealer
    public int getValorMaoDealer() {
        return fachada.getValorMaoDealer();
    }
    
    // Retorna o valor da mão do split

    public int getValorMaoSplit() {
    	return fachada.getValorMaoSplit();
    }
    public boolean isJogandoSplit() {
        return fachada.isJogandoSplit();
    }
    public double getAposta() {
	    return fachada.getAposta();
	}

	public double getCredito() {
	    return fachada.getCredito();
	}
    
	public void finalizarRodada(String resultado) {
	    fachada.finalizarRodada(resultado);
	}
 

}
