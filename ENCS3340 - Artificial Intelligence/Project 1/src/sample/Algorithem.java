package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeSet;

public abstract class Algorithem {
    Graph g;
    Vertex start;
    ArrayList<Vertex> Path;
    TreeSet<Vertex> Visited;
    ArrayList<Vertex> Expansion;
    ArrayList<Vertex> Order;
    int distance = 0;
    public abstract void getPath(Vertex v);
    public abstract void find( Vertex dis);

    public Algorithem(Graph g) {
        this.g = g;
        Path=new ArrayList<>();
        Visited=new TreeSet<>();
        Expansion=new ArrayList<>();
        Order=new ArrayList<>();
    }
    public void setStart(Vertex start){
        this.start=start;
    }
}
