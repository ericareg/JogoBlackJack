package model;
import java.io.Serializable;

import javax.swing.JOptionPane;
public class Fichas implements Serializable  {
   private int quantidade; // Quantidade total de fichas em dólares
   private int fichas100;  // Quantidade de fichas de $100
   private int fichas50;   // Quantidade de fichas de $50
   private int fichas20;   // Quantidade de fichas de $20
   private int fichas10;   // Quantidade de fichas de $10
   // Construtor: inicializa com 2400 em fichas (10x100, 10x50, 20x20, 50x10)
   public Fichas() {
       this.fichas100 = 10;
       this.fichas50 = 10;
       this.fichas20 = 20;
       this.fichas10 = 50;
       this.quantidade = calcularQuantidadeTotal();
   }
   // Método para calcular a quantidade total em dólares com base nas fichas
   private int calcularQuantidadeTotal() {
       return (fichas100 * 100) + (fichas50 * 50) + (fichas20 * 20) + (fichas10 * 10);
   }
   // Método para apostar (subtrai fichas do saldo)
   public boolean apostar(int valor) {
       if (valor < 50) {
           System.out.println("A aposta mínima é de $50.");
           return false;
       }
       if (valor > quantidade) {
           System.out.println("Saldo insuficiente para apostar.");
           return false;
       }
      
       while (valor >= 100 && fichas100 > 0) {
           valor -= 100;
           fichas100--;
       }
       while (valor >= 50 && fichas50 > 0) {
           valor -= 50;
           fichas50--;
       }
       while (valor >= 20 && fichas20 > 0) {
           valor -= 20;
           fichas20--;
       }
       while (valor >= 10 && fichas10 > 0) {
           valor -= 10;
           fichas10--;
       }
      
       this.quantidade = calcularQuantidadeTotal();
       return true;
   }

   public void ganhar(int valor) {
       while (valor >= 100) {
           valor -= 100;
           fichas100++;
       }
       while (valor >= 50) {
           valor -= 50;
           fichas50++;
       }
       while (valor >= 20) {
           valor -= 20;
           fichas20++;
       }
       while (valor >= 10) {
           valor -= 10;
           fichas10++;
       }
      
       this.quantidade = calcularQuantidadeTotal();
   }
   public void perder(int valor) {
       // Esse método é apenas um marcador, pois a perda já foi gerenciada na aposta
       this.quantidade = calcularQuantidadeTotal();
   }
   public int getQuantidade() {
       return quantidade;
   }
   public boolean inserirAposta() {
       String valorStr = JOptionPane.showInputDialog("Digite o valor que deseja apostar (mínimo $50):");
       if (valorStr == null) {
           return false; // Jogador cancelou a operação
       }
       try {
           int valor = Integer.parseInt(valorStr);
           if (valor < 50) {
               System.out.println("A aposta mínima é de $50.");
               return false;
           }
           return apostar(valor);
       } catch (NumberFormatException e) {
           System.out.println("Valor inválido. Por favor, insira um número.");
           return false;
       }
   }
   // Método para exibir as fichas (para teste no console)
   public void exibirFichas() {
       System.out.println("Fichas de $100: " + fichas100);
       System.out.println("Fichas de $50: " + fichas50);
       System.out.println("Fichas de $20: " + fichas20);
       System.out.println("Fichas de $10: " + fichas10);
       System.out.println("Saldo total: $" + quantidade);
   }
 
}
