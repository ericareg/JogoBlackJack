package view;
import model.*; // Importar a classe Jogo do pacote model
import java.util.List;  // Importação correta
import javax.swing.*;
import controller.ControladorJogo;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;

public class JanelaBanca extends JFrame {
	    
	public void setJogo(Jogo jogo) {
	        this.jogo = jogo;
	    }
	
	ControladorJogo controle = new ControladorJogo();
	FachadaModel fachada = new FachadaModel();
	JanelaJogador janelaJogador = JanelaJogador.getInstance();
	JanelaDealer janelaDealer = JanelaDealer.getInstance();
	JanelaSplit janelaSplit = JanelaSplit.getInstance();
	private Image imagemMesa;
	private Rectangle ficha1, ficha5, ficha25, ficha100, ficha500;
	private Rectangle botaoHit, botaoStand, botaoDouble, botaoSunder, botaoExit, botaoDeal, botaoClear, botaoSplit;
	private Rectangle botaoSalvar; // Área clicável para salvar o jogo
	private JLabel labelAposta, labelCredito;
	private double aposta = fachada.getAposta(); // Valor atual da aposta
	private double credito = fachada.getCredito(); // Valor inicial do crédito
	private boolean mostrarImagens = false; // Controla se as cartas devem ser exibidas
	private Jogo jogo; 
	public JanelaBanca() {
	   setTitle("Blackjack - Banca");
	   setSize(800, 600);
       setResizable(false);
       setMaximumSize(new Dimension(1366, 768)); // Tamanho máximo permitido


	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setLocationRelativeTo(null);
	   try {
	       imagemMesa = ImageIO.read(getClass().getResource("/images/Mesa.png"));
	   } catch (IOException e) {
	       e.printStackTrace();
	   }
	   jogo = Jogo.getInstancia();
	   // Configuração das áreas clicáveis
	   configurarAreas();
	   // Painel da mesa
	   PainelMesa painelMesa = new PainelMesa();
	   add(painelMesa);
	   // Labels para aposta e crédito
	   labelAposta = new JLabel("Aposta: $0.0");
	   labelAposta.setFont(new Font("SansSerif", Font.BOLD, 18));
	   labelAposta.setForeground(Color.BLACK);
	   labelAposta.setOpaque(true);
	   labelAposta.setBackground(Color.WHITE);
	   add(labelAposta, BorderLayout.NORTH);
	   labelCredito = new JLabel(String.format("Crédito: $%.2f", credito));
	   labelCredito.setFont(new Font("SansSerif", Font.BOLD, 18));
	   labelCredito.setForeground(Color.BLACK);
	   labelCredito.setOpaque(true);
	   labelCredito.setBackground(Color.WHITE);
	   add(labelCredito, BorderLayout.SOUTH);
	   
	   // Detectar cliques
	   painelMesa.addMouseListener(new MouseAdapter() {
	       @Override
	       public void mouseClicked(MouseEvent e) {
	           Point pontoClique = e.getPoint();
	           if (ficha1.contains(pontoClique)) {
	        	   adicionarAposta(1);
	           }
	           else if (ficha5.contains(pontoClique)) {
	        	   adicionarAposta(5);
	           }
	           else if (ficha25.contains(pontoClique)) {
	        	   adicionarAposta(25);
	           }
	           else if (ficha100.contains(pontoClique)) {
	        	   adicionarAposta(100);
	           }
	           else if (ficha500.contains(pontoClique)) {
	        	   adicionarAposta(500);
	           }
	           else if (botaoDeal.contains(pontoClique)) {
	        	   iniciarJogo();
	           }
	           else if (botaoHit.contains(pontoClique)) {
	        	   System.out.println("Botão Hit clicado!");
	        	   	controle.jogadorPedirCarta();
	   	        	janelaJogador.atualizarCartas(fachada); // Atualiza com as cartas reais do jogador
	           }
	           else if (botaoStand.contains(pontoClique)) {
	        	   System.out.println("Botão Stand clicado!");
	        	   controle.jogadorManter();
	        	   janelaDealer.atualizarCartas(fachada); // Atualiza com as cartas reais do dealer
	           }
	           else if (botaoDouble.contains(pontoClique)) {
	        	   System.out.println("Botão Double clicado!");
	        	   controle.jogadorDobrar();
	        	   aposta *= 2; // Duplica a aposta
	        	   credito-=aposta/2;
	        	   atualizarLabels(); // Atualiza os labels com o novo valor da aposta
	        	   janelaJogador.atualizarCartas(fachada); // Atualiza com as cartas reais do jogador
	        	   janelaDealer.atualizarCartas(fachada); // Atualiza com as cartas reais do dealer
	           }
	           else if (botaoSplit.contains(pontoClique)) {
	        	   System.out.println("Botão split clicado!");
	        	   if (controle.podeRealizarSplit()) {
	        		if (credito >= aposta) { // Verifica se há crédito suficiente para dobrar a aposta
	                 aposta *= 2; // Duplica a aposta
	                 credito -= aposta / 2; // Reduz o crédito pelo valor adicional da aposta
	                 atualizarLabels(); // Atualiza os labels com os novos valores
	                 controle.jogadorSplit(); // Realiza o split no modelo
	                 // Comprar uma nova carta para cada mão
	                 controle.jogadorPedirCarta(); // Nova carta para a mão original
	                 controle.jogadorPedirCartaSplit(); // Nova carta para a mão dividida
	                 // Atualizar ambas as janelas
	                 janelaJogador.atualizarCartas(fachada);
	                 janelaSplit.atualizarCartas(fachada);
	                 janelaSplit.setVisible(true);
	             } else {
	                 JOptionPane.showMessageDialog(JanelaBanca.this, "Crédito insuficiente para realizar o split!", "Erro", JOptionPane.ERROR_MESSAGE);
	             }
	        	   }
	           }
	           else if (botaoSunder.contains(pontoClique)) surrender();
	           else if (botaoExit.contains(pontoClique)) System.exit(0);
	           else if (botaoClear.contains(pontoClique)) limparAposta();
	           else if (botaoSalvar.contains(pontoClique)) {
	        	    JFileChooser fileChooser = new JFileChooser();
	        	    int retorno = fileChooser.showSaveDialog(null);
	        	    if (retorno == JFileChooser.APPROVE_OPTION) {
	        	        File arquivo = fileChooser.getSelectedFile();
	        	        jogo.atualizarCredito(credito); // Atualiza o crédito antes de salvar
	        	        jogo.salvarJogo(arquivo.getAbsolutePath()); // Chama o método do controlador
	        	    }
	        	}
	       }
	   });
	}
	private void configurarAreas() {
	   ficha1 = new Rectangle(250, 10, 20, 60);
	   ficha5 = new Rectangle(275, 10, 45, 60);
	   ficha25 = new Rectangle(322, 10, 20, 60);
	   ficha100 = new Rectangle(345, 10, 20, 60);
	   ficha500 = new Rectangle(370, 10, 40, 60);
	   botaoHit = new Rectangle(663, 390, 100, 35);
	   botaoStand = new Rectangle(663, 435, 100, 30);
	   botaoDouble = new Rectangle(170, 472, 100, 30);
	   botaoSunder = new Rectangle(663, 472, 100, 32);
	   botaoExit = new Rectangle(25, 400, 100, 30);
	   botaoClear = new Rectangle(405, 472, 100, 32);
	   botaoDeal = new Rectangle(520, 472, 100, 30);
	   botaoSplit = new Rectangle(285, 472, 100, 30);
	   botaoSalvar = new Rectangle(663, 350, 100, 30); // Posição e tamanho do botão
	}
	private void adicionarAposta(double valor) {
	   if (credito >= valor) {
	       aposta += valor;
	       credito -= valor;
	       atualizarLabels();
	     
	   } else {
	       JOptionPane.showMessageDialog(this, "Crédito insuficiente para essa aposta!", "Erro", JOptionPane.ERROR_MESSAGE);
	   }
	}
	private void limparAposta() {
	   credito += aposta;
	   aposta = 0.0;
	   atualizarLabels();
	}
	public void atualizarLabels() {
	   labelAposta.setText(String.format("Aposta: $%.2f", aposta));
	   labelCredito.setText(String.format("Crédito: $%.2f", credito));
	}
	private void iniciarJogo() {
		    if (aposta < 50) {
		        JOptionPane.showMessageDialog(this, "A aposta mínima é de $50!", "Erro", JOptionPane.WARNING_MESSAGE);
		    } else {
		        mostrarImagens = true; // Simula a exibição das cartas
		        repaint();
		        // Inicializar nova rodada no jogo
		        FachadaModel fachada = new FachadaModel();
		        fachada.iniciarNovaRodada((int) aposta);
		        // Criar e configurar a janela do jogador
		   
		        janelaJogador.atualizarCartas(fachada); // Atualiza com as cartas reais do jogador
		        janelaJogador.setVisible(true); // Exibe a janela do jogador
		        // Criar e configurar a janela do dealer
		        janelaDealer.atualizarCartas(fachada); // Atualiza com as cartas reais do dealer
		        janelaDealer.setVisible(true); // Exibe a janela do dealer
		    }
		}
	
		private void surrender() {
				    // Verifica se o jogador está nas duas primeiras cartas
				    if (jogo.getCartasJogador().size() > 2) {
				        JOptionPane.showMessageDialog(this, "Rendição só é permitida nas duas primeiras cartas!", "Erro", JOptionPane.WARNING_MESSAGE);
				        return;
				    }
		
				    // Verifica se o dealer tem um Blackjack
				    if (jogo.getValorMaoDealer() == 21 && jogo.getCartasDealer().size() == 2) {
				        JOptionPane.showMessageDialog(this, "Rendição não permitida porque o dealer tem um Blackjack!", "Erro", JOptionPane.WARNING_MESSAGE);
				        return;
				    }
		
				    // Confirmação do jogador para a rendição
				    int resposta = JOptionPane.showConfirmDialog(this, "Você realmente deseja desistir? Você perderá metade da aposta.", "Confirmação", JOptionPane.YES_NO_OPTION);
				    if (resposta == JOptionPane.YES_OPTION) {
				        // Calcula o crédito após perder metade da aposta
				        credito += aposta / 2; // Recupera metade da aposta
				        aposta = aposta/2; // Zera a aposta da rodada
				        controle.finalizarRodada("Jogador se rendeu."); // Finaliza a rodada com a rendição
				        atualizarLabels(); // Atualiza os valores na interface

				    }
		}
	
	
	
		class PainelMesa extends JPanel {
			  @Override
			  protected void paintComponent(Graphics g) {
			      super.paintComponent(g);
			      Graphics2D g2d = (Graphics2D) g;
			      // Desenhar a mesa
			      if (imagemMesa != null) {
			          g2d.drawImage(imagemMesa, 0, 0, getWidth(), getHeight(), null);
			      }
			      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			      g2d.setColor(new Color(0, 0, 0, 50)); // Sombra preta semitransparente
			      g2d.fillRoundRect(botaoSalvar.x + 3, botaoSalvar.y + 3, botaoSalvar.width, botaoSalvar.height, 15, 15);
			   // Desenhar botão com bordas arredondadas
			      g2d.setColor(new Color(150, 150, 150)); // Cor do botão
			      g2d.fillRoundRect(botaoSalvar.x, botaoSalvar.y, botaoSalvar.width, botaoSalvar.height, 15, 15);
			      // Adicionar borda ao botão
			      g2d.setColor(Color.BLACK);
			      g2d.setStroke(new BasicStroke(2)); // Espessura da borda
			      g2d.drawRoundRect(botaoSalvar.x, botaoSalvar.y, botaoSalvar.width, botaoSalvar.height, 15, 15);
			      g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
			      FontMetrics fm = g2d.getFontMetrics();
			      String texto = "Salvar";
			      int textWidth = fm.stringWidth(texto);
			      int textHeight = fm.getAscent();
			      int textX = botaoSalvar.x + (botaoSalvar.width - textWidth) / 2;
			      int textY = botaoSalvar.y + (botaoSalvar.height + textHeight) / 2 - 2;
			      g2d.setColor(Color.BLACK);
			      g2d.drawString(texto, textX, textY);
			  }
			}
	
}
