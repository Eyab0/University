package sample;

import javafx.scene.Parent;

import java.util.*;

public class DFS extends Algorithem{
    TreeMap<Vertex,Vertex> parent;
    public DFS(Graph g) {
        super(g);
        parent=new TreeMap<>();
    }

    @Override
    public void getPath(Vertex v) {
        Path.clear();
        while(v!=null){
            Path.add(v);
            v=parent.get(v);
        }
        distance=0;
        for (int i = 1; i < Path.size(); i++) {
            distance+=g.getAdj_mat().get(g.getVertexIndex(Path.get(i-1).getName())).get(g.getVertexIndex(Path.get(i).getName())).getCarDistance();
        }

    }

    @Override
    public void find(Vertex dis) {
        Expansion.clear();
        Order.clear();
        parent.clear();
        Visited.clear();
        Stack<Vertex> stk=new Stack<>();
        parent.put(start,null);
        Order.add(start);
        Expansion.add(start);
        stk.add(start);
        while (!stk.empty()){
            Vertex cur=stk.pop();
            if(Visited.contains(cur))continue;
            Order.add(cur);
            Visited.add(cur);
            if(cur.equals(dis))break;//for stopping on a specific node
            for (int i = 0; i < g.getNumOfVerticies(); i++) {
                g.getAdj_list()[i].sort(Comparator.comparing(Edge::getDestination).reversed());
            }
            for (Edge edge:g.getAdj_list()[g.getVertexIndex(cur.getName())]){
                if(Visited.contains(edge.getDestination())|| parent.containsKey(edge.getDestination())||edge.getCarDistance()<=0)continue;
                stk.add(edge.getDestination());
                Expansion.add(edge.getDestination());
                parent.put(edge.getDestination(), edge.getSource());
            }
        }
    }
}
