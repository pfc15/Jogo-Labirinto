package gerador_labirinto;
import java.util.*;



public class prim {
	private ArrayList<ArrayList<Integer>> labirinto;
	private ArrayList<ArrayList< HashMap<String, Integer>>> labirinto_pesos;
	private int tamanho;
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_RESET = "\u001B[0m";
	
	public prim(int tamanho) {
		this.tamanho = tamanho;
		labirinto = labirinto_vazio(tamanho);
		labirinto_pesos = set_pesos();
		
		printa_matriz();
	}
	
	public void printa_matriz() {
		
		for (int y=0;y<labirinto.size();y++) {
			System.out.print("|");
			for (int x=0; x<labirinto.size();x++) {
				if (labirinto.get(y).get(x) == 1) { // passagem aberta normal
					System.out.print(" "+ANSI_YELLOW+labirinto.get(y).get(x)+ANSI_RESET +" |");
				} else if (labirinto.get(y).get(x) == 2) { // passagem secreta
					System.out.print(" "+ ANSI_RED +labirinto.get(y).get(x)+ANSI_RESET +" |");
				}else if (labirinto.get(y).get(x) == 3) { // passagem com chave
					System.out.print(" "+ ANSI_CYAN +labirinto.get(y).get(x)+ANSI_RESET +" |");
				}
				else {
					System.out.print(" "+labirinto.get(y).get(x)+" |");
				}
				
			}
			System.out.println("");
		}
	}
	
	public ArrayList<ArrayList<Integer>> labirinto_vazio(int tamanho) {
		ArrayList< ArrayList< Integer> > matriz_vazia = new ArrayList<>();
		for (int i=0;i<tamanho+2; i++) {
			ArrayList<Integer> linha = new ArrayList<>();
			for (int e=0;e<tamanho+2;e++) {
				linha.add(0);
			}
			matriz_vazia.add(linha);
		}
		return matriz_vazia;
		
	}
	
	public ArrayList<ArrayList<HashMap<String, Integer>>> set_pesos(){
		ArrayList< ArrayList< HashMap<String, Integer>>> matriz = new ArrayList<>();
		Random random = new Random();
		for (int i=0;i<tamanho+2; i++) {
			ArrayList<HashMap<String, Integer>> linha = new ArrayList<>();
			for (int e=0;e<tamanho+2;e++) {
				HashMap<String, Integer> peso = new HashMap<>();
				peso.put("norte", -1);
				peso.put("sul", -1);
				peso.put("leste", -1);
				peso.put("oeste", -1);
				linha.add(peso);
			}
			matriz.add(linha);
		}
		
		for (int y=1;y<tamanho+1; y++) {
			for (int x=1;x<tamanho+1;x++) {
				HashMap<String, Integer> peso = matriz.get(y).get(x);
				// norte
				if (y-1>=0) {
					if (matriz.get(y-1).get(x).get("sul")<0) {
						peso.put("norte", random.nextInt(1000)); 
					} else {
						peso.put("norte" , matriz.get(y-1).get(x).get("sul"));
					}
				}
				
				
				// sul
				if (y+1<tamanho) {
					if (matriz.get(y+1).get(x).get("norte")<0) {
						peso.put("sul", random.nextInt(1000)); 
					} else {
						peso.put("sul" , matriz.get(y+1).get(x).get("norte"));
					}
				}
				
				
				// leste
				if (x+1<tamanho) {
					if (matriz.get(y).get(x+1).get("oeste")<0) {
						peso.put("leste", random.nextInt(1000)); 
					} else {
						peso.put("leste" , matriz.get(y).get(x+1).get("oeste"));
					}
				}
				
				
				// oeste
				if (x-1>=0) {
					if (matriz.get(y).get(x-1).get("leste")<0) {
						peso.put("oeste", random.nextInt(1000)); 
					} else {
						peso.put("oeste" , matriz.get(y).get(x-1).get("leste"));
					}
				}
				
				
				matriz.get(y).set(x, peso);
			}
		}
		
		return matriz;
	}
	
	
	public ArrayList<ArrayList<Integer>>  gerar_labirinto() {
		
		// labirinto maior
		ArrayList<ArrayList<Integer>> matriz = new ArrayList<>();
		for (int i=0;i<((tamanho+1)*2); i++) {
			ArrayList<Integer> linha = new ArrayList<>();
			for (int e=0;e<((tamanho+1)*2);e++) {
				linha.add(0);
			}
			matriz.add(linha);
		}
		
		int[] u = {1, 1};
		labirinto.get(1).set(1, 1);
		matriz.get(u[1]*2).set(u[0]*2,1);
		
		// priority queue
		PriorityQueue<Aresta> pq = new PriorityQueue<>(new ComparadorAresta());
		pq = add_arestas(u, pq);
		int contador = 1;
	
		
		// loop do algorítimo de PRIM
		while (contador <((tamanho)*(tamanho))) {
			// pega topo
			Aresta topo = pq.poll();
			
			
			// processa nó
			labirinto.get(topo.getPos()[1]).set(topo.getPos()[0], 1);
			matriz.get(topo.getPos()[1]*2).set(topo.getPos()[0]*2,1);
			
			
			if ( topo.getDirecao().equals("sul")) {
				matriz.get((topo.getPos()[1])*2+1).set(topo.getPos()[0]*2,1);
			}
			else if ( topo.getDirecao().equals("norte")) {
				matriz.get((topo.getPos()[1])*2-1).set(topo.getPos()[0]*2, 1);
			}
			else if ( topo.getDirecao().equals("leste")) {
				matriz.get(topo.getPos()[1]*2).set((topo.getPos()[0])*2+1, 1);
			}
			else if ( topo.getDirecao().equals("oeste")) {
				matriz.get(topo.getPos()[1]*2).set(topo.getPos()[0]*2-1, 1);
			}
			
			// add proximos nós
			pq = add_arestas(topo.getPos(), pq);
			
			
			contador++;
			
			// tirando arestas que causariam ciclos
			if (!pq.isEmpty()) {
				u = pq.peek().getPos();
				while (labirinto.get(u[1]).get(u[0]) != 0) {
					pq.poll();
					if (pq.isEmpty()) {
						break;
					} else {
						u = pq.peek().getPos();
					}
				}
			}
		}
		
		// criando algumas passagens secretas
		int secreta = 0;
		int chave = 0;
		Random random = new Random();
		while (secreta<tamanho && chave<tamanho) {
			int x = random.nextInt((tamanho*2-1))+2;
			int y = random.nextInt((tamanho*2-1))+2;
			
			if (matriz.get(y).get(x) == 0) {
				matriz.get(y).set(x, 2);
				secreta++;
			} else if (matriz.get(y).get(x) == 1) {
				matriz.get(y).set(x, 3);
				chave++;
			}
		}
		matriz.remove(0);
		for (int i=0;i<matriz.size();i++) {
			matriz.get(i).remove(0);
		}
		return matriz;
	}
	
	public PriorityQueue<Aresta> add_arestas(int[] pos, PriorityQueue<Aresta> pq){
		System.out.print("["+pos[0]+", "+pos[1]+"]");
		// norte
		if (pos[1]-1>0) {
			int[] norte_pos = {pos[0], pos[1]-1};
			if (labirinto.get(norte_pos[1]).get(norte_pos[0]) == 0) {
				Aresta n_aresta = new Aresta(norte_pos, 
				this.labirinto_pesos.get(norte_pos[1]).get(norte_pos[0]).get("norte"),"sul");
				pq.add(n_aresta);
				System.out.print(" nó norte");
			}
		}
		
		// sul
		if (pos[1]+1<=tamanho) {
			int[] sul_pos = {pos[0], pos[1]+1};
			if (labirinto.get(sul_pos[1]).get(sul_pos[0]) ==0) {
			
			Aresta s_aresta = new Aresta(sul_pos, this.labirinto_pesos.get(sul_pos[1]).get(sul_pos[0]).get("sul")
					,"norte");
			pq.add(s_aresta);
			System.out.print(" nó sul");
			}
		}
		
		
		// leste
		if (pos[0]+1<=tamanho) {
			int[] leste_pos = {pos[0]+1, pos[1]};
			if (labirinto.get(leste_pos[1]).get(leste_pos[0]) ==0) {
			Aresta l_aresta = new Aresta(leste_pos, this.labirinto_pesos.get(leste_pos[1]).get(leste_pos[0]).get("leste")
					, "oeste");
			pq.add(l_aresta);
			System.out.print(" nó leste");
			}
		}
		
		
		// oeste
		if (pos[0]-1>0) {
			int[] oeste_pos = {pos[0]-1, pos[1]};
			if (labirinto.get(oeste_pos[1]).get(oeste_pos[0]) ==0) {
			Aresta o_aresta = new Aresta(oeste_pos, this.labirinto_pesos.get(oeste_pos[1]).get(oeste_pos[0]).get("oeste")
					, "leste");
			pq.add(o_aresta);
			System.out.print(" nó oeste");
			}
		}
		System.out.println();
		
		return pq;
	}
	
	
	// getters and setters
	public ArrayList<ArrayList<Integer>> getLabirinto() {
		return labirinto;
	}

	public void setLabirinto(ArrayList<ArrayList<Integer>> labirinto) {
		this.labirinto = labirinto;
	}

	public ArrayList<ArrayList<HashMap<String, Integer>>> getLabirinto_pesos() {
		return labirinto_pesos;
	}

	public void setLabirinto_pesos(ArrayList<ArrayList<HashMap<String, Integer>>> labirinto_pesos) {
		this.labirinto_pesos = labirinto_pesos;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
}
