package Graph;

import Auxiliars.Pair;
import List.IList;
import List.LinkedList;
import PriorityQueue.BinaryHeap;
import PriorityQueue.IPriorityQueue;
import com.sun.org.apache.xpath.internal.operations.Bool;
import sun.awt.image.ImageWatched;

import java.lang.reflect.Array;
import java.util.Scanner;

public class Graph {
    private boolean weighted;
    private IPriorityQueue<Pair<Integer,Integer>>[] adjacencyList;
    private Integer[] inDegree;
    private Integer[] distance;
    private Boolean[] known;
    private Integer[] backPath;
    private Integer v;
    private Integer e;

    public Graph(Scanner sc,boolean weighted){

        int v=sc.nextInt();
        int e = sc.nextInt();
        this.e = e;
        this.v = v;

        this.weighted = weighted;
        this.adjacencyList = (IPriorityQueue<Pair<Integer,Integer>>[]) Array.newInstance(IPriorityQueue.class,v+1);
        this.inDegree = new Integer[v+1];

        distance = new Integer[v+1];
        known = new Boolean[v+1];
        backPath = new Integer[v+1];

        for (int i = 1; i<this.adjacencyList.length; i++){
            this.adjacencyList[i] = new BinaryHeap<>(10);
            this.inDegree[i]=0;
        }

        while(e>0){
            int vertex=sc.nextInt();
            int edge=sc.nextInt();
            int weight = this.weighted?sc.nextInt():0;
            this.adjacencyList[vertex].insert(new Pair<>(edge, weight),edge);
            this.inDegree[edge]++;
            e--;
        }
    }
    //Dijkstra implementado con cola FIFO
    public void dijkstra(Integer vertex){
        for(int i=1;i<distance.length;i++){
            distance[i] = Integer.MAX_VALUE;
            known[i] = false;
            backPath[i]=-1;
        }
        IList<Integer>q= new LinkedList<>();

        distance[vertex] = 0;
        q.addLast(vertex);

        while(!q.isEmpty()) {
            Integer v = q.getFirst();
            q.deleteFirst();
            known[v] = true;
            IPriorityQueue<Pair<Integer, Integer>> auxList = new BinaryHeap<>(10);
            while (!adjacencyList[v].isEmpty()) {
                Pair<Integer, Integer> edge = adjacencyList[v].getMin();
                adjacencyList[v].removeMin();
                auxList.insert(edge, edge.getV1());
                Integer w = edge.getV1();

                if(!known[w]){
                    Integer cvw = edge.getV2();
                    if (distance[v] + cvw < distance[w]) { // Update w
                        distance[w] = distance[v] + cvw;
                        backPath[w] = v;
                    }
                    q.addLast(w);
                }
            }
            adjacencyList[v] = auxList;
        }

    }

    public void printPath( Integer v ) {

        if( backPath[v] != -1 ) {
            printPath( backPath[v] );
            System.out.print(" to ");
        }
        System.out.print(v);
    }
/*
    vector<Edge> kruskal( vector<Edge> edges, int numVertices ) {
        DisjSets ds {
            numVertices
        } ;
        priority_queue pq {
            edges
        } ;
        vector<Edge> mst;
        while (mst.size() != numVertices - 1) {
            Edge e = pq.pop(); // Edge e = (u, v)
            SetType uset = ds.find( e.getu( ) );
            SetType vset = ds.find( e.getv( ) );

            if (uset != vset) { // Accept the edge
                mst.push_back( e );
                ds.union( uset, vset );
            }
        }
        return mst;
    }
*/
    public IList<Integer> topologicOrder(){
        IList<Integer> nodeList = new LinkedList<Integer>();
        IList<Integer> queue = new LinkedList<Integer>();
        Integer[] inDegree = this.inDegree.clone();

        for (int i = 1; i < this.inDegree.length; i++) {
            if(this.inDegree[i] == 0){
                queue.addLast(i);
            }
        }
        if(queue.isEmpty()){
            queue.addFirst(1);
        }
        while(!queue.isEmpty()){
            int v = queue.getFirst();
            queue.deleteFirst();
            nodeList.addLast(v);

            IPriorityQueue<Pair<Integer,Integer>> auxList= new BinaryHeap<>(10);

            while(!this.adjacencyList[v].isEmpty()) {
                Pair<Integer,Integer> edge =this.adjacencyList[v].getMin();
                auxList.insert(edge,edge.getV1());
                this.adjacencyList[v].removeMin();

                if(--inDegree[edge.getV1()] == 0){
                    queue.addLast(edge.getV1());
                }
            }

            this.adjacencyList[v] = auxList;
        }

        return nodeList;
    }
}
