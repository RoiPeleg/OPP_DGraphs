package dataStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DGraph implements graph{
    private HashMap<Integer , node_data> hashMap;
    private int MC;
    public DGraph()
    {
        hashMap = new HashMap<>();
        MC =0;
    }
	@Override
	public node_data getNode(int key) {
		return (node_data) hashMap.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
            Node n =(Node)(hashMap.get(src));
		return n.getEdge(dest);
	}

	@Override
	public void addNode(node_data n) {
        hashMap.put(n.getKey(),n);
        MC++;
	}

	@Override
	public void connect(int src, int dest, double w) {
    	Edge e = new Edge(src,dest,w);
    	Node n = (Node)hashMap.get(src);
    	n.addEdge(e);
    	MC++;
	}

	@Override
	public Collection<node_data> getV() {
		ArrayList<node_data> list = new ArrayList<node_data>(hashMap.values());
		return (Collection<node_data>) list;
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		Node node = (Node) hashMap.get(node_id);
		ArrayList<edge_data> list = new ArrayList<edge_data>(node.getEdges().values());
		return (Collection<edge_data>)list;
	}

	@Override
	public node_data removeNode(int key) {
		MC++;
		return hashMap.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
    	Node n = (Node)hashMap.get(src);
    	MC++;
		return n.removeEdge(dest);
	}

	@Override
	public int nodeSize() {
        return hashMap.size();
	}

	@Override
	public int edgeSize() {
        return Node.getNumofEdges();
	}

	@Override
	public int getMC() {
		return MC;
	}

}
