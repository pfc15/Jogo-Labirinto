package gerador_labirinto;
import java.util.*;
import java.util.Comparator;

public class Aresta {
    int [] pos;
    int peso;
    String direcao;
    
    
    public Aresta(int[] pos, int peso, String direcao) {
    	this.pos = new int[2];
    	this.pos[0] = pos[0];
    	this.pos[1] = pos[1];
    	this.peso = peso;
        this.direcao = direcao; 
    }

    public Integer getFirstElementAsInteger() {
        return peso;
    }

    @Override
    public String toString() {
        return "peso: " +peso+ 
        "; posição: ["+ pos[0] +", "+ pos[1]+"]; direção: " + direcao;
    }

	public int[] getPos() {
		return pos;
	}

	public void setPos(int[] pos) {
		this.pos = pos;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getDirecao() {
		return direcao;
	}

	public void setDirecao(String direcao) {
		this.direcao = direcao;
	}
}

