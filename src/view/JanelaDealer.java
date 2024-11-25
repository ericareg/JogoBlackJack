package view;

import model.FachadaModel;
import model.Jogo;

import javax.swing.*;

import controller.ControladorJogo;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class JanelaDealer extends JFrame {
	 private Jogo jogo;

	    public void setJogo(Jogo jogo) {
	        this.jogo = jogo;
	    }
    private List<Image> imagensCartas; // Lista de imagens das cartas do dealer
    private final PainelCartas painelCartas; // Painel para desenhar as cartas
    private final JLabel labelPontuacao; // Label para exibir a pontuação do dealer
	ControladorJogo controle = new ControladorJogo();



    // padrão Singleton
    private JanelaDealer() {
        setTitle("Blackjack - Dealer");
        setSize(400, 300); 
        setResizable(false);
        setMaximumSize(new Dimension(1366, 768)); // Tamanho máximo permitido
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null); 
        setAlwaysOnTop(true); // Mantém a JanelaJogador sempre acima
        setLocation(1100, 200); // Coordenadas (x, y)


        imagensCartas = new ArrayList<>(); 
        painelCartas = new PainelCartas(); 
        labelPontuacao = new JLabel("Pontuação: 0"); 
        
        // Configurações do label da pontuação
           labelPontuacao.setFont(new Font("Arial", Font.BOLD, 16));
           labelPontuacao.setBounds(10, 220, 200, 30);
           add(labelPontuacao); 
           add(painelCartas);
    }

    private static JanelaDealer instance; // Instância única para o padrão Singleton

   
    public static synchronized JanelaDealer getInstance() {
        if (instance == null) {
            instance = new JanelaDealer();
        }
        return instance;
    }

   
    public void atualizarCartas(FachadaModel fachada) {
        imagensCartas.clear(); 

        for (String nomeCarta : fachada.getCartasDealer()) {
            String caminhoImagem = "/images/" + nomeCarta + ".gif"; // Construção do caminho
            try {
                Image imagem = new ImageIcon(getClass().getResource(caminhoImagem)).getImage();
                imagensCartas.add(imagem);
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem: " + caminhoImagem);
                imagensCartas.add(null); 
            }
        }
        int pontuacaoDealer = fachada.getValorMaoDealer();
        labelPontuacao.setText("Pontuação: " + pontuacaoDealer);


        repaint(); // Atualiza a interface gráfica
    }

 
    private class PainelCartas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int x = 10, y = 10; 

            for (Image carta : imagensCartas) {
                if (carta != null) {
                    g.drawImage(carta, x, y, 50, 70, this); 
                } else {
                    g.drawString("Erro ao carregar imagem", x, y + 50); 
                }
                x += 60; 
            }
        }
    }
    


    public List<String> getCartasDealer() {
        return controle.getCartasDealer();  
    }

}
