import Auxiliars.Pair;
import Auxiliars.SortFunctions;
import Graph.Graph;
import List.IList;
import List.LinkedList;
import PriorityQueue.BinaryHeap;
import PriorityQueue.IPriorityQueue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Launcher {
	private static Scanner sc;

    public static void main(String[] args) throws FileNotFoundException {
        sc = new Scanner(System.in);
        //Exercise3();
        //Exercise4();
        Exercise9();

    }

    private static void Exercise4() {
        Graph g = new Graph(sc,false);
        IList<Integer>sort = g.topologicOrder();
        System.out.println(sort);
    }

    public static IList<Pair<Integer,Integer>> mergeSort(IList<Pair<Integer,Integer>> list){
        if(list.size()>=2){
            Integer middle=list.size()/2;
            IList<Pair<Integer,Integer>> l= new LinkedList<>();
            IList<Pair<Integer,Integer>> r = new LinkedList<>();

            Integer i=0;
            while(!list.isEmpty()){
                if(i<middle){
                    l.addLast(list.getFirst());
                }else {
                    r.addLast(list.getFirst());
                }
                list.deleteFirst();
                i++;
            }

            l= mergeSort(l);
            r = mergeSort(r);
            return merge(l,r);
        }else{
            return list;
        }
    }

    private static IList<Pair<Integer,Integer>> merge(IList<Pair<Integer,Integer>> l, IList<Pair<Integer,Integer>> r) {
        IList<Pair<Integer,Integer>> list = new LinkedList<>();
        Integer h1=0,h2=0,lh=0;

        while(!l.isEmpty() && !r.isEmpty()){
            Pair<Integer,Integer> p1 = l.getFirst();
            Pair<Integer,Integer> p2 = r.getFirst();

            if(p1.getV1()<=p2.getV1()){
                h1 = p1.getV2();
                Integer max = Integer.max(h1,h2);
                if(max!=lh){
                    p1.setV2(max);
                    list.addLast(p1);
                }
                lh = max;

                l.deleteFirst();
            }else{
                h2 = p2.getV2();
                Integer max = Integer.max(h1,h2);
                if(max!=lh){
                    p2.setV2(max);
                    list.addLast(p2);
                }
                lh = max;
                r.deleteFirst();
            }
        }

        IList<Pair<Integer,Integer>> aux;
        if(l.isEmpty()){
            aux=r;
        }else{
            aux=l;
        }

        while(!aux.isEmpty()){
            list.addLast(aux.getFirst());
            aux.deleteFirst();
        }

        return list;
    }

    private static void Exercise9() {
        IList<Pair<Integer,Integer>>list = new LinkedList<>();
        int n = sc.nextInt();
        for (int j = 0; j < n; j++) {
            int i=sc.nextInt();
            int f=sc.nextInt();
            int h=sc.nextInt();

            list.addLast(new Pair<>(i,h));
            list.addLast(new Pair<>(f,0));
        }

        System.out.println("aloha");
        System.out.println(mergeSort(list));
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
    }
}
