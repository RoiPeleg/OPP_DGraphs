package algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

import dataStructure.*;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 *
 * @author
 */

public class Graph_Algo implements graph_algorithms {
    private graph graph;

    public Graph_Algo(graph g) {
        init(g);
    }

    @Override
    public void init(graph g) {
        this.graph = new DGraph((DGraph) g);
    }

    @Override
    public graph copy() {
        return new DGraph((DGraph) this.graph);

    }

    @Override
    public void init(String file_name) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(String file_name) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isConnected() {
        ArrayList<node_data> nodes = (ArrayList<node_data>) this.graph.getV();
        if (nodes.size() <= 1)
            return true;
        node_data src = nodes.get(0);
        nodes.remove(0);
        for (node_data n:nodes) {
            double go = shortestPathDist(src.getKey(),n.getKey());
            double back = shortestPathDist(n.getKey(),src.getKey());
            if(go==Integer.MAX_VALUE||back==Integer.MAX_VALUE)
                return false;
        }
        return true;
    }

    private int minDistance(double dist[], boolean used[]) {

        double min = Integer.MAX_VALUE;
        int min_index = -1;
        int V = this.graph.nodeSize();
        for (int v = 0; v < V; v++)
            if (used[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        // TODO Auto-generated method stub
        PriorityQueue pq = new PriorityQueue<Node>();
        int V = this.graph.nodeSize();
        double dist[] = new double[V];
        boolean used[] = new boolean[V];
        ArrayList<node_data> nodes = (ArrayList<node_data>) this.graph.getV();
        //initiate values
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            used[i] = false;
        }
        dist[nodes.indexOf(graph.getNode(src))] = 0;
        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, used); //index of u in the array
            used[u] = true;
            HashMap<Integer, edge_data> edges = ((Node) nodes.get(u)).getEdges();
            for (Map.Entry<Integer, edge_data> edge : edges.entrySet()) {
                int adjindex = nodes.indexOf(graph.getNode(edge.getValue().getDest()));
                if (dist[u] + edge.getValue().getWeight() < dist[adjindex]) {
                    dist[adjindex] = dist[u] + edge.getValue().getWeight();
                }
            }
        }
        return dist[nodes.indexOf(graph.getNode(dest))];

    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        PriorityQueue pq = new PriorityQueue<Node>();
        int V = this.graph.nodeSize();
        double dist[] = new double[V];
        int N[] = new int[V];
        boolean used[] = new boolean[V];
        ArrayList<node_data> nodes = (ArrayList<node_data>) this.graph.getV();
        //initiate values
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            used[i] = false;
            N[i] = -1;
        }
        dist[nodes.indexOf(graph.getNode(src))] = 0;
        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, used); //index of u in the array
            used[u] = true;
            HashMap<Integer, edge_data> edges = ((Node) nodes.get(u)).getEdges();
            for (Map.Entry<Integer, edge_data> edge : edges.entrySet()) {
                int adjindex = nodes.indexOf(graph.getNode(edge.getValue().getDest()));
                if (dist[u] + edge.getValue().getWeight() < dist[adjindex]) {
                    dist[adjindex] = dist[u] + edge.getValue().getWeight();
                    N[adjindex] = u;
                }
            }
        }

        List<node_data> list1 = new LinkedList<>();
        int f = nodes.indexOf(graph.getNode(dest));
        if (dist[f] != Integer.MAX_VALUE) {
            while (N[f] != -1) {
                list1.add(nodes.get(f));
                f = N[f];
            }

            list1.add(nodes.get(f));

            List<node_data> list2 = new LinkedList<>();
            for (int i = list1.size() - 1; i >= 0; i--) {
                list2.add(list1.get(i));
            }
            for (node_data n : list2) {
                System.out.print(n.getKey() + " ");
            }
            System.out.println();
            System.out.println(dist[nodes.indexOf(graph.getNode(dest))]);
            return list2;
        }
        System.out.println("Path doesn't exist");
        return null;
    }

    @Override
    public List<node_data> TSP(List<Integer> targets) {
        // TODO Auto-generated method stub
        return null;
    }
}