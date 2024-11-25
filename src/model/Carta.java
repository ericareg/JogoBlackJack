package model;
import java.io.Serializable;

class Carta implements Serializable {
   public enum Naipe {
       h, d, s, c
   }
   private final Naipe naipe;
   private final int valor;
   public Carta(Naipe naipe, int valor) {
       this.naipe = naipe;
       this.valor = valor;
   }
   public int getValor() {
       return Math.min(valor, 10); // J, Q, K 
   }
   public Naipe getNaipe() {
       return naipe;
   }
   
   @Override
   public String toString() {
       String valorStr;

       switch (valor) {
           case 11:
               valorStr = "J"; // Valete
               break;
           case 12:
               valorStr = "Q"; // Rainha
               break;
           case 13:
               valorStr = "K"; // Rei
               break;
           case 1:
               valorStr = "A"; // Ás
               break;
           default:
               valorStr = String.valueOf(valor); // Números de 2 a 10
       }

       return valorStr + naipe; // Formato: 1h, Jh, Qh, Kh, etc.
   }
}
