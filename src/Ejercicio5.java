import List.IList;
import List.LinkedList;
import PriorityQueue.BinaryHeap;
import PriorityQueue.IPriorityQueue;

import java.lang.reflect.Array;
import java.util.Scanner;

public class Ejercicio5 {

    public static Scanner sc;
    public static DijkstraShortestPath dij;

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        int src = sc.nextInt();
        int V = sc.nextInt();
        int E = sc.nextInt();

        dij = new DijkstraShortestPath(V);
        IList<Node>[] adj = (LinkedList<Node>[]) Array.newInstance(LinkedList.class, V + 1);

        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }

        for (int i = 1; i < E + 1; i++) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            int peso = sc.nextInt();
            adj[v].addLast(new Node(w, peso));
        }

        dij.dijkstra(adj, src);
        for (int i : dij.dist) {
            System.out.println(i);
        }
    }

    public static class DijkstraShortestPath {
        private int dist[];
        private boolean[] settled;
        private IPriorityQueue<Node> pq;
        private int V;
        IList<Node>[] adj;

        DijkstraShortestPath(int V) {
            this.V = V;
            dist = new int[V];
            settled = new boolean[V];
            pq = new BinaryHeap<>(V);
        }

        public void dijkstra(IList<Node>[] adj, int src) {
            this.adj = adj;
            for (int i = 0; i < V; i++) {
                dist[i] = Integer.MAX_VALUE;
            }
            pq.insert(new Node(src, 0), 0);
            dist[src] = 0;
            int visitados = 0;
            while (visitados != V) {
                int nodeNumber = pq.getMin().getNode();
                pq.removeMin();
                if (!settled[nodeNumber]) {
                    settled[nodeNumber] = true;
                    visitados++;
                }
                e_Neighbours(nodeNumber);
            }
        }

        private void e_Neighbours(int u) {
            int edgeDistance = -1;
            int newDistance = -1;

            for (int i = 0; i < adj[u].size(); i++) {
                Node v = adj[u].get(i);

                if (!settled[v.getNode()]) {
                    edgeDistance = v.getCost();
                    newDistance = dist[u] + edgeDistance;

                    if (newDistance < dist[v.getNode()])
                        dist[v.getNode()] = newDistance;

                    pq.insert(new Node(v.getNode(), dist[v.getNode()]), dist[v.getNode()]);
                }
            }
        }
    }

    public static class Node {
        private int node;
        private int cost;

        public Node(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        public int getNode() {
            return this.node;
        }

        public int getCost() {
            return this.cost;
        }
    }
}
