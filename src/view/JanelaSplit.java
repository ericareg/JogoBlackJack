package view;

import model.FachadaModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class JanelaSplit extends JFrame {
    private List<Image> imagensCartas;
    private final PainelCartas painelCartas; 
    private final JLabel labelPontuacao; 

    //  padrão Singleton
    private JanelaSplit() {
        setTitle("Blackjack - Split");
        setSize(400, 300); 
        setResizable(false);
        setMaximumSize(new Dimension(1366, 768)); // Tamanho máximo permitido
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setAlwaysOnTop(true); // Mantém a JanelaJogador sempre acima
        setLocation(200, 200); // Coordenadas (x, y)

        imagensCartas = new ArrayList<>(); 
        painelCartas = new PainelCartas(); 
        labelPontuacao = new JLabel("Pontuação: 0"); 

        // Configurações do label da pontuação
        labelPontuacao.setFont(new Font("Arial", Font.BOLD, 16));
        labelPontuacao.setBounds(10, 220, 200, 30); 
        add(labelPontuacao); 
        add(painelCartas); 
    }

    private static JanelaSplit instance; 

   
    public static synchronized JanelaSplit getInstance() {
        if (instance == null) {
            instance = new JanelaSplit();
        }
        return instance;
    }

   
    public void atualizarCartas(FachadaModel fachada) {
        imagensCartas.clear(); 

        for (String nomeCarta : fachada.getCartasSplit()) { 
            String caminhoImagem = "/images/" + nomeCarta + ".gif"; // Construção do caminho
            try {
                Image imagem = new ImageIcon(getClass().getResource(caminhoImagem)).getImage();
                imagensCartas.add(imagem);
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem: " + caminhoImagem);
                imagensCartas.add(null); 
            }
        }

        // Atualiza a pontuação da mão dividida
        int pontuacaoSplit = fachada.getValorMaoSplit(); 
        labelPontuacao.setText("Pontuação: " + pontuacaoSplit);

        repaint(); 
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
}
