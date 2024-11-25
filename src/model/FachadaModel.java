package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FachadaModel {
    private final Jogo jogo;


    public FachadaModel() {
        this.jogo = Jogo.getInstancia();
    }

    // Iniciar uma nova rodada com a aposta inicial
    public void iniciarNovaRodada(int aposta) {
        try {
            jogo.iniciarNovaRodada(aposta);
        } catch (IllegalStateException e) {
            System.out.println("Erro ao iniciar nova rodada: " + e.getMessage());
        }
    }

    // Jogador pede uma carta
    public void jogadorPedirCarta() {
        try {
            jogo.jogadorPedirCarta();
        } catch (IllegalStateException e) {
            System.out.println("Erro ao pedir carta: " + e.getMessage());
        }
    }
    
    public void jogadorPedirCartaSplit() {
        try {
            jogo.jogadorPedirCartaSplit();
        } catch (IllegalStateException e) {
            System.out.println("Erro ao pedir carta: " + e.getMessage());
        }
    }

    // Jogador decide manter sua mão
    public void jogadorManter() {
        try {
            jogo.jogadorManter();
        } catch (IllegalStateException e) {
            System.out.println("Erro ao manter a mão: " + e.getMessage());
        }
    }

    // Jogador dobra a aposta
    public void jogadorDobrar() {
        try {
            jogo.jogadorDobrar();
        } catch (IllegalStateException e) {
            System.out.println("Erro ao dobrar a aposta: " + e.getMessage());
        }
    }

    // Jogador se rende
    public void jogadorRender() {
        try {
            jogo.jogadorRender();
        } catch (IllegalStateException e) {
            System.out.println("Erro ao render: " + e.getMessage());
        }
    }

    // Retorna o valor da mão do jogador
    public int getValorMaoJogador() {
        return jogo.getValorMaoJogador();
    }

    // Retorna o valor da mão do dealer
    public int getValorMaoDealer() {
        return jogo.getValorMaoDealer();
    }
    
    // Retorna o valor da mão do split

    public int getValorMaoSplit() {
    	return jogo.getValorMaoSplit();
    }

    // Verifica se a rodada está ativa
    public boolean isRodadaAtiva() {
        return jogo.isRodadaAtiva();
    }

    // Obtém o último resultado da rodada
    public String getUltimoResultado() {
        return jogo.getUltimoResultado();
    }
    public List<String> getCartasJogador() {
        return jogo.getCartasJogador().stream()
                   .map(Carta::toString) 
                   .toList();
    }
    public List<String> getCartasDealer() {
        return jogo.getCartasDealer().stream()
                   .map(Carta::toString) 
                   .toList();
    }
    public List<String> getCartasSplit() {
        return jogo.getCartasSplit().stream()
                   .map(Carta::toString) 
                   .toList();
    }
    
    public String getCartasJogadorFormatadas() {
        return jogo.getCartasJogador().stream()
                   .map(carta -> carta.toString()) 
                   .collect(Collectors.joining(", "));
    }
    public String getCartasDealerFormatadas() {
        return jogo.getCartasDealer().stream()
                   .map(carta -> carta.toString()) 
                   .collect(Collectors.joining(", "));
    }
    
    public String getCartasSplitFormatadas() {
        return jogo.getCartasSplit().stream()
                   .map(carta -> carta.toString()) 
                   .collect(Collectors.joining(", "));
    }
    
    public boolean podeRealizarSplit() {
        // Verifica se o jogador tem exatamente duas cartas e se elas têm o mesmo valor para poder fazer o split
        List<Carta> cartasJogador = jogo.getCartasJogador();
        return cartasJogador.size() == 2 && cartasJogador.get(0).getValor() == cartasJogador.get(1).getValor();
    }
    
    public void jogadorSplit() {
        if (podeRealizarSplit()) {
            jogo.jogadorSplit();
        } else {
            System.out.println("Não é possível fazer Split. As cartas não são iguais.");
        }
    }
    
	public void exibirEstadoJogador() {
	    System.out.println("Valor atual da mão do jogador: " + jogo.getValorMaoJogador());
	    System.out.println("Cartas do jogador: " + getCartasJogadorFormatadas());
	}

	public void exibirEstadoDealer() {
		System.out.println("Valor da mão do dealer: " + jogo.getValorMaoDealer());
		System.out.println("Cartas do Dealer: " + getCartasDealerFormatadas());


	}

	public void exibirEstadoSplit() {
	    System.out.println("Valor atual da mão do split: " + jogo.getValorMaoSplit());
	    System.out.println("Cartas da mão do split: " + getCartasSplitFormatadas());
	}
	public void setCartasJogador(List<String> cartas) {
	    jogo.getCartasJogador(); 
	}
	public void setCartasDealer(List<String> cartas) {
	    jogo.getCartasDealer(); 
	}
	
	public int getPontuacaoDealer() {
        return jogo.getValorMaoDealer();
    }

	public int getPontuacaoJogador() {
        return jogo.getValorMaoJogador();
    }
	public void alternarMaoJogador() {
	    Jogador jogador = jogo.getJogador(); // Acessa o jogador
	    if (jogador.isJogandoSplit()) {
	        jogador.alternarParaPrincipal();
	        System.out.println("Agora jogando a mão principal.");
	    } else if (!jogador.getCartasSplit().isEmpty()) {
	        jogador.alternarParaSplit();
	        System.out.println("Agora jogando a mão do split.");
	    } else {
	        System.out.println("Não há mãos adicionais para alternar.");
	    }
	}
	  public boolean isJogandoSplit() {
	        return jogo.isJogandoSplit();
	    }

	  public double getAposta() {
		    return jogo.getAposta();
		}

		public double getCredito() {
		    return jogo.getCredito();
		}
		
		public void finalizarRodada(String resultado) {
		    jogo.encerrarRodada(resultado);
		}
		

}
