package gerador_labirinto;

import java.util.Comparator;

public class ComparadorAresta implements Comparator<Aresta> {
    @Override
    public int compare(Aresta o1, Aresta o2) {
        return o1.getFirstElementAsInteger().compareTo(o2.getFirstElementAsInteger());
    }
}