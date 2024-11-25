package view;

import javax.swing.*;

import model.FachadaModel;
import model.Jogo;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class JanelaInicial extends JFrame {
    private Image imagemCartas; // Imagem das cartas
    private Rectangle botaoNovoJogo, botaoJogoSalvo; // área dos botões

    public JanelaInicial() {
        setTitle("Blackjack - Inicial");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMaximumSize(new Dimension(1366, 768)); // Tamanho máximo permitido
        setResizable(false);



        // Carregar a imagem das cartas
        try {
            imagemCartas = ImageIO.read(getClass().getResource("/images/cartasTelaInicial.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Definir as áreas dos botões
        botaoNovoJogo = new Rectangle(150, 250, 200, 50);
        botaoJogoSalvo = new Rectangle(450, 250, 200, 50);

        // Painel para desenhar os elementos
        PainelInicial painelInicial = new PainelInicial();
        add(painelInicial);

        // Detectar clique 
        painelInicial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point pontoClique = e.getPoint();
                if (botaoNovoJogo.contains(pontoClique)) {
                    abrirJanelas(); // Inicia um novo jogo
                } else if (botaoJogoSalvo.contains(pontoClique)) {
                    carregarJogoSalvo(); // Carrega um jogo salvo
                }
            }
        });
    }

    // Painel para desenhar a tela inicial
    class PainelInicial extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Fundo cinza claro
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Desenhar imagem das cartas
            if (imagemCartas != null) {
                g2d.drawImage(imagemCartas, getWidth() / 2 - 50, 50, 100, 100, null);
            }

            // Desenhar o texto 
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 36));
            g2d.drawString("Black Jack", getWidth() / 2 - 100, 200);

            // Desenhar os botões como retângulos
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(botaoNovoJogo.x, botaoNovoJogo.y, botaoNovoJogo.width, botaoNovoJogo.height);
            g2d.fillRect(botaoJogoSalvo.x, botaoJogoSalvo.y, botaoJogoSalvo.width, botaoJogoSalvo.height);

            // Desenhar os textos dos botões
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
            g2d.drawString("Novo Jogo", botaoNovoJogo.x + 40, botaoNovoJogo.y + 30);
            g2d.drawString("Jogo Salvo", botaoJogoSalvo.x + 40, botaoJogoSalvo.y + 30);
        }
    }

    private void abrirJanelas() {
        JanelaBanca janelaBanca = new JanelaBanca();
        janelaBanca.setVisible(true);
        this.dispose(); // Fecha a janela inicial
    }

    private void carregarJogoSalvo() {
        JFileChooser fileChooser = new JFileChooser();
        int retorno = fileChooser.showOpenDialog(null);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            File arquivoSelecionado = fileChooser.getSelectedFile();
            Jogo jogoCarregado = Jogo.carregarJogo(arquivoSelecionado.getAbsolutePath());

            if (jogoCarregado != null) {
                System.out.println("Jogo salvo carregado com sucesso!");

                // Atualiza a instância do jogo para a carregada
                Jogo.setInstancia(jogoCarregado);

                // Criar as janelas e passar o estado do jogo carregado
                JanelaBanca janelaBanca = new JanelaBanca();
                JanelaJogador janelaJogador = JanelaJogador.getInstance();
                JanelaDealer janelaDealer = JanelaDealer.getInstance();

                janelaBanca.setJogo(jogoCarregado);
                janelaJogador.setJogo(jogoCarregado);
                janelaDealer.setJogo(jogoCarregado);

                // Atualizar os componentes visuais
                janelaBanca.atualizarLabels();
                janelaJogador.atualizarCartas(new FachadaModel());
                janelaDealer.atualizarCartas(new FachadaModel());

                // Exibir as janelas
                janelaBanca.setVisible(true);
                janelaJogador.setVisible(true);
                janelaDealer.setVisible(true);

                // Fechar a janela inicial
                JanelaInicial.this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao carregar o jogo. Tente novamente.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        
    }

    // metodo main
    public static void main(String[] args) {
        JanelaInicial janelaInicial = new JanelaInicial();
        janelaInicial.setVisible(true);
    }
    
    
}
