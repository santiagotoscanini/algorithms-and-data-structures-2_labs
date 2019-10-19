import Auxiliars.Pair;
import Graph.Graph;
import List.IList;
import List.*;
import PriorityQueue.BinaryHeap;
import PriorityQueue.IPriorityQueue;
import java.lang.reflect.Array;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Launcher {

	private static Scanner sc;

    public static void main(String[] args) throws FileNotFoundException {
        sc = new Scanner(System.in);
        Exercise5();
    }

    private static void Exercise5(){
        int V = sc.nextInt();
        int E = sc.nextInt();
        LinkedList<Pair<Integer,Integer>> arr[] = (LinkedList<Pair<Integer,Integer>>[]) Array.newInstance(LinkedList.class,V+1);

        for(int i =1; i<V+1;i++){
            int v = sc.nextInt();
            int w = sc.nextInt();
            int peso = sc.nextInt();
            arr[v].addLast(new Pair<>(w, peso));
        }

        System.out.println("xd");


    }
/*
    private static void Exercise4() {
        Graph g = new Graph(sc,false);
        IList<Integer>sort = g.topologicOrder();
        System.out.println(sort);
    }

    private static void Exercise3()   {
        int k = sc.nextInt();
        IPriorityQueue<IList<Integer>> queue = new BinaryHeap<IList<Integer>>(k);

        for (int i=0;i<k;i++){
            int elem_cant=sc.nextInt();
            IList<Integer> list = new LinkedList<Integer>();
            while (elem_cant!=0){
                elem_cant--;
                list.addLast(sc.nextInt());
            }
            queue.insert(list, list.getFirst());
        }

        IList<Integer> list = new LinkedList<Integer>();

        while(!queue.isEmpty()){
            IList<Integer> listInQueue = queue.getMin();
            list.addLast(listInQueue.getFirst());
            queue.removeMin();
            listInQueue.deleteFirst();
            if(!listInQueue.isEmpty()) {
                queue.insert(listInQueue, listInQueue.getFirst());
            }
        }
        String result = "";
        while(!list.isEmpty()){
            if(list.size()!=1){
                result+=list.getFirst()+"\n";
            }else{
                result+=list.getFirst();
            }
            list.deleteFirst();
        }
        System.out.println(result);


    }*/


}
