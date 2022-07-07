package sample;

import java.util.*;
import java.util.function.Function;

public class UniformCost extends Algorithem {
    TreeMap<Vertex, Vertex> parent;
    Function<Edge, Integer> costFunction;

    public UniformCost(Graph g, Function<Edge, Integer> costFunction) {
        super(g);
        this.costFunction = costFunction;
        parent = new TreeMap<>();
    }

    @Override
    public void getPath(Vertex v) {
        Path.clear();
        while (v != null) {
            Path.add(v);
            v = parent.get(v);
        }
        distance = 0;
        for (int i = 1; i < Path.size(); i++) {
            distance += costFunction.apply(g.getAdj_mat().get(g.getVertexIndex(Path.get(i - 1).getName())).get(g.getVertexIndex(Path.get(i).getName())));
        }
    }

    @Override
    public void find(Vertex dis) {
        Expansion.clear();
        Order.clear();
        parent.clear();
        Visited.clear();
        TreeMap<Vertex, Long> cost = new TreeMap<>();
        ArrayList<Vertex> que = new ArrayList<>();
        parent.put(start, null);
        cost.put(start, 0L);
        Order.add(start);
        Expansion.add(start);
        que.add(start);
        while (!que.isEmpty()) {
            que.sort(Comparator.comparing(cost::get));
            Vertex cur = que.remove(0);
            if (Visited.contains(cur)) continue;
            Order.add(cur);
            Visited.add(cur);
            if (cur.equals(dis)) break;//for stopping on a specific node
            for (Edge edge : g.getAdj_list()[g.getVertexIndex(cur.getName())]) {
                if (Visited.contains(edge.getDestination()) || costFunction.apply(edge) <= 0)
                    continue;
                if(!cost.containsKey(edge.getDestination()) || cost.get(edge.getDestination()) > cost.get(edge.getSource())+costFunction.apply(edge)) {
                    que.add(edge.getDestination());
                    cost.put(edge.getDestination(), cost.get(edge.getSource())+costFunction.apply(edge));
                    Expansion.add(edge.getDestination());
                    parent.put(edge.getDestination(), edge.getSource());
                }

            }
        }
    }
}
