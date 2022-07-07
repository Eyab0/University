package sample;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

public class BFS extends Algorithem{
    TreeMap<Vertex,Vertex> parent;
    public BFS(Graph g) {
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
        Queue<Vertex> que=new LinkedList<>();
        parent.put(start,null);
        Order.add(start);
        Expansion.add(start);
        que.add(start);
        while (!que.isEmpty()){
            Vertex cur=que.poll();
            if(Visited.contains(cur))continue;
            Order.add(cur);
            Visited.add(cur);
            if(cur.equals(dis))break;//for stopping on a specific node
            for (int i = 0; i < g.getNumOfVerticies(); i++) {
                g.getAdj_list()[i].sort(Comparator.comparing(Edge::getDestination));
            }
            for (Edge edge:g.getAdj_list()[g.getVertexIndex(cur.getName())]){
                if(Visited.contains(edge.getDestination())|| parent.containsKey(edge.getDestination())||edge.getCarDistance()<=0)continue;
                que.add(edge.getDestination());
                Expansion.add(edge.getDestination());
                parent.put(edge.getDestination(), edge.getSource());
            }
        }
    }
}
