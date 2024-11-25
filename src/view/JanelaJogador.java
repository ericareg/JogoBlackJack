package view;

import model.FachadaModel;
import model.Jogo;
import controller.ControladorJogo;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class JanelaJogador extends JFrame {
	 private Jogo jogo;

	    public void setJogo(Jogo jogo) {
	        this.jogo = jogo;
	    }
    private List<Image> imagensCartas; 
    private final PainelCartas painelCartas;
    private final JLabel labelPontuacao;
	ControladorJogo controle = new ControladorJogo();


    // padrão Singleton
    private JanelaJogador() {
        setTitle("Blackjack - Jogador");
        setSize(400, 300);
        setPreferredSize(new Dimension(400, 300)); // Tamanho inicial
        setMaximumSize(new Dimension(1366, 768)); // Tamanho máximo permitido
        setSize(getPreferredSize()); // Ajusta o tamanho inicial
        setAlwaysOnTop(true); // Mantém a JanelaJogador sempre acima


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(80, 200); // Coordenadas (x, y)
        setResizable(false);
        imagensCartas = new ArrayList<>(); 
        painelCartas = new PainelCartas(); 
        labelPontuacao = new JLabel("Pontuação: 0"); 
        
     // Configurações do label da pontuação
        labelPontuacao.setFont(new Font("Arial", Font.BOLD, 16));
        labelPontuacao.setBounds(10, 220, 200, 30); 
        add(labelPontuacao); 

        add(painelCartas); 
    }

    private static JanelaJogador instance; 

   
    public static synchronized JanelaJogador getInstance() {
        if (instance == null) {
            instance = new JanelaJogador();
        }
        return instance;
    }

  
    public void atualizarCartas(FachadaModel fachada) {
        imagensCartas.clear();

        
        for (String nomeCarta : fachada.getCartasJogador()) {
            String caminhoImagem = "/images/" + nomeCarta + ".gif"; // Construção do caminho
            try {
                Image imagem = new ImageIcon(getClass().getResource(caminhoImagem)).getImage();
                imagensCartas.add(imagem);
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem: " + caminhoImagem);
                imagensCartas.add(null); 
            }
        }
        int pontuacaoJogador = fachada.getValorMaoJogador();
        labelPontuacao.setText("Pontuação: " + pontuacaoJogador);

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
    
    public List<String> getCartasJogador() {
        return controle.getCartasJogador();  
    }
   

}
