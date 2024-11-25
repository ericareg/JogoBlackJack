package model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


class Jogador implements Serializable  {
    private final List<Carta> maosSplit = new ArrayList<>(); // Para armazenar as mãos após o split

    private final List<Carta> mao = new ArrayList<>();
    private boolean jogandoSplit = false; // Flag para indicar se o jogador está jogando a mão do split

    public boolean isJogandoSplit() {
        return jogandoSplit;
    }

    public void receberCarta(Carta carta) {
        mao.add(carta);
    }
    public void receberCartaSplit(Carta carta) {
        maosSplit.add(carta);
    }
    
 

    public int getValorMao() {
        int total = 0;
        int ases = 0;

        for (Carta carta : mao) {
            total += carta.getValor();
            if (carta.getValor() == 1) {
                ases++;
            }
        }

        while (ases > 0 && total + 10 <= 21) {
            total += 10;
            ases--;
        }

        return total;
    }

    
    public int getValorMaoSplit() {
        int total = 0;
        int ases = 0;

        for (Carta carta : maosSplit) {
            total += carta.getValor();
            if (carta.getValor() == 1) {
                ases++;
            }
        }

        while (ases > 0 && total + 10 <= 21) {
            total += 10;
            ases--;
        }

        return total;
    }

    public boolean estourou() {
        return getValorMao() > 21;
    }

    public void limparMao() {
        mao.clear();
    }
    
    public List<Carta> getCartas() {
    	
        return new ArrayList<>(mao); // Retorna cópia da mão do jogador
    }
    
   public List<Carta> getCartasSplit() {
    	
        return new ArrayList<>(maosSplit); // Retorna cópia da mão do split
    }
    
    public void realizarSplit() {
        if (mao.size() == 2 && mao.get(0).getValor() == mao.get(1).getValor()) {
            // Cria uma nova mão com a segunda carta e remove essa carta da mão original
            Carta cartaSplit = mao.remove(1);  // Remove a segunda carta da mão original

            List<Carta> novaMao = new ArrayList<>();
            maosSplit.add(mao.get(0));  // A primeira carta vai para a mão original
            mao.clear();
            mao.add(cartaSplit); // A segunda carta vai para a nova mão

            // impressoes para testes
            System.out.println("Split realizado com sucesso!");
        } else {
            System.out.println("Não é possível realizar Split, as cartas não são iguais.");
        }
    }
    
 // Alterna para jogar a mão do split
    public void alternarParaSplit() {
        if (!maosSplit.isEmpty()) {
            jogandoSplit = true;
        } else {
            System.out.println("Não há mão split para jogar.");
        }
    }

    // Alterna para jogar a mão principal
    public void alternarParaPrincipal() {
        jogandoSplit = false;
    }

}

class Dealer extends Jogador {
    public boolean devePedirCarta() {
        return getValorMao() < 17;
    }
}
