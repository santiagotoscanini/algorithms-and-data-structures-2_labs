package EjerciciosCompletos;


import List.IList;
import List.LinkedList;

import java.util.*;
import java.lang.reflect.Array;

public class Ejercicio5 {

    public static Scanner sc;
    public static DijkstraShortestPath dij;

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        int src = sc.nextInt();
        int V = sc.nextInt();
        int E = sc.nextInt();

        dij = new DijkstraShortestPath(V);
        IList<Node>[] adj = (LinkedList<Node>[]) Array.newInstance(IList.class, V+1);

        for (int i = 1; i <= V; i++) {
            adj[i] = new ArrayList<Node>();
        }

        for (int i = 1; i < E + 1; i++) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            int peso = sc.nextInt();
            adj[i].addLast(new Node(w, peso));
        }

        dij.dijkstra(adj, src);
        for (int i : dij.dist) {
            System.out.println(i);
        }
    }

    //TODO: cambiar los Tads por mi implementacion
    public static class DijkstraShortestPath {
        private int dist[];
        private Set<Integer> settled;
        private PriorityQueue<Node> pq;
        private int V;
        List<Node>[] adj;

        DijkstraShortestPath(int V) {
            this.V = V;
            dist = new int[V];
            settled = new HashSet<Integer>();
            pq = new PriorityQueue<Node>(V, new Node());
        }

        public void dijkstra(List<Node>[] adj, int src) {
            this.adj = adj;
            for (int i = 0; i < V; i++) {
                dist[i] = Integer.MAX_VALUE;
            }
            pq.add(new Node(src, 0));
            dist[src] = 0;

            while (settled.size() != V) {
                int u = pq.remove().node;
                settled.add(u);
                e_Neighbours(u);
            }
        }

        private void e_Neighbours(int u) {
            int edgeDistance = -1;
            int newDistance = -1;
            for (int i = 0; i < adj[u].size(); i++) {
                Node v = adj[u].get(i);
                if (!settled.contains(v.node)) {
                    edgeDistance = v.cost;
                    newDistance = dist[u] + edgeDistance;
                    if (newDistance < dist[v.node])
                        dist[v.node] = newDistance;
                    pq.add(new Node(v.node, dist[v.node]));
                }
            }
        }
    }

    public static class Node implements Comparator<Node> {
        public int node;
        public int cost;

        public Node() {
        }

        public Node(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compare(Node node1, Node node2) {
            if (node1.cost < node2.cost)
                return -1;
            if (node1.cost > node2.cost)
                return 1;
            return 0;
        }
    }

}
