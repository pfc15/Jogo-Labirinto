package gerador_labirinto;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class main {
	public static void main(String[] args) {
		prim matriz = new prim(10);
		System.out.println("-=-=-=--=-=-=--=-=-==-=-=-=-==-");
		matriz.setLabirinto(matriz.gerar_labirinto());
		matriz.printa_matriz();
		
		
	}
}
