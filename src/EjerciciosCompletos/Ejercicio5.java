package EjerciciosCompletos;
import java.util.*;

public class Ejercicio5 {

    public static Scanner sc;
    public static DijkstraShortestPath dij;

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        int src = sc.nextInt();
        int V = sc.nextInt();
        int E = sc.nextInt();

        dij = new DijkstraShortestPath(V);
        List<List<Node>> adj = new ArrayList<List<Node>>(V);

        for (int i =1; i<V+1;i++){
            adj.add(new ArrayList<Node>());
        }

        for(int i =1; i<E+1;i++){
            int v = sc.nextInt();
            int w = sc.nextInt();
            int peso = sc.nextInt();
            adj.get(v).add(new Node(w,peso));
        }

        dij.dijkstra(adj,src);
        for(int i: dij.dist){
            System.out.println(i);
        }
    }

    //TODO: cambiar los Tads por mi implementacion
    public static class DijkstraShortestPath{
        private int dist[];
        private Set<Integer> settled;
        private PriorityQueue<Node> pq;
        private int V;
        List<List<Node>> adj;

        DijkstraShortestPath(int V){
            this.V = V;
            dist = new int[V];
            settled = new HashSet<Integer>();
            pq = new PriorityQueue<Node>(V, new Node());
        }

        public void dijkstra(List<List<Node>> adj, int src){
            this.adj = adj;
            for(int i = 0; i < V; i++){
                dist[i] = Integer.MAX_VALUE;
            }
            pq.add(new Node(src, 0));
            dist[src]=0;

            while(settled.size()!=V){
                int u = pq.remove().node;
                settled.add(u);
                e_Neighbours(u);
            }
        }

        private void e_Neighbours(int u)
        {
            int edgeDistance = -1;
            int newDistance = -1;

            // All the neighbors of v
            for (int i = 0; i < adj.get(u).size(); i++) {
                Node v = adj.get(u).get(i);

                // If current node hasn't already been processed
                if (!settled.contains(v.node)) {
                    edgeDistance = v.cost;
                    newDistance = dist[u] + edgeDistance;

                    // If new distance is cheaper in cost
                    if (newDistance < dist[v.node])
                        dist[v.node] = newDistance;

                    // Add the current node to the queue
                    pq.add(new Node(v.node, dist[v.node]));
                }
            }
        }
    }

    public static class Node implements Comparator<Node> {
        public int node;
        public int cost;

        public Node()
        {
        }

        public Node(int node, int cost)
        {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compare(Node node1, Node node2)
        {
            if (node1.cost < node2.cost)
                return -1;
            if (node1.cost > node2.cost)
                return 1;
            return 0;
        }
    }

}
