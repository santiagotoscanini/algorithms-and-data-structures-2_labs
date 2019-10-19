package Graph;

import Auxiliars.Pair;
import List.IList;
import List.LinkedList;
import PriorityQueue.BinaryHeap;
import PriorityQueue.IPriorityQueue;

import java.lang.reflect.Array;
import java.util.Scanner;

public class Graph {
    private boolean weighted;
    private IPriorityQueue<Pair<Integer,Integer>>[] adjacencyList;
    private Integer[] inDegree;

    public Graph(Scanner sc,boolean weighted){
        int v=sc.nextInt();
        this.weighted = weighted;
        this.adjacencyList = (IPriorityQueue<Pair<Integer,Integer>>[]) Array.newInstance(IPriorityQueue.class,v+1);
        this.inDegree = new Integer[v+1];

        sc.nextInt();
        for (int i = 1; i<this.adjacencyList.length; i++){
            this.adjacencyList[i] = new BinaryHeap<>(10);
            this.inDegree[i]=0;
        }

        while(sc.hasNextInt()){
            int vertex=sc.nextInt();
            int edge=sc.nextInt();
            int weight = this.weighted?sc.nextInt():0;
            this.adjacencyList[vertex].insert(new Pair<>(edge, weight),edge);
            this.inDegree[edge]++;
        }
    }

    public void dijkstra(){

    }

    public IList<Integer> topologicOrder(){
        IList<Integer> nodeList = new LinkedList<Integer>();
        IList<Integer> queue = new LinkedList<Integer>();
        Integer[] inDegree = this.inDegree.clone();

        for (int i = 1; i < this.inDegree.length; i++) {
            if(this.inDegree[i] == 0){
                queue.addLast(i);
            }
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
