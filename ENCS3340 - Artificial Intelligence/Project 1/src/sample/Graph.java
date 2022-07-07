package sample;

import java.util.*;

public class Graph {
	private Vertex[] VList; //array of vertex
	private LinkedList<Edge> []adj_list;
	private ArrayList<ArrayList<Edge>> adj_mat;
	private final int NumOfVerticies;
	private int i = 0;
	private final HashMap<String,Integer> map;
	public Graph(int v) {
		super();
		NumOfVerticies = v;
		VList = new Vertex[NumOfVerticies];
		adj_list = new LinkedList[NumOfVerticies];
		adj_mat=new ArrayList<>(NumOfVerticies);
		for (int j = 0; j < NumOfVerticies; j++) {
			adj_mat.add(new ArrayList<>(NumOfVerticies));
			for (int k = 0; k < NumOfVerticies; k++) {
				adj_mat.get(j).add(null);
			}
		}
		map=new HashMap<>();
	}

	public void addVertex(Vertex v) {
		VList[i] = v;
		map.put(v.getName(),i);
		adj_list[i] = new LinkedList<>();
		i++;
	}
	public int getVertexIndex(String x){
		return map.get(x);
	}
	public void addEdge(Edge e) {  //ramallah -> nablus      nablus -> ramallah
		adj_list[getVertexIndex(e.getSource().getName())].addLast(e);
		adj_mat.get(getVertexIndex(e.getSource().getName())).set(getVertexIndex(e.getDestination().getName()),e);
	}

	public int getNumOfVerticies() {
		return NumOfVerticies;
	}

	public LinkedList<Edge>[] getAdj_list() {
		return adj_list;
	}

	public void setAdj_list(LinkedList<Edge>[] adj_list) {
		this.adj_list = adj_list;
	}

	public ArrayList<ArrayList<Edge>> getAdj_mat() {
		return adj_mat;
	}

	public void setAdj_mat(ArrayList<ArrayList<Edge>> adj_mat) {
		this.adj_mat = adj_mat;
	}

	public Vertex[] getVList() {
		return VList;
	}

	public void setVList(Vertex[] vList) {
		VList = vList;
	}
}
