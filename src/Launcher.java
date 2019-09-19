import PriorityQueue.BinaryHeap;
import List.IList;
import List.LinkedList;
import PriorityQueue.IPriorityQueue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Launcher {
	private static Scanner sc= new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {

        Exercise3();
    }

    private static void Exercise3() throws FileNotFoundException {
//        BufferedReader bf = new BufferedReader(new FileReader(new File("C:\\Users\\Nahuel\\Documents\\Algorithms2Obl\\out\\production\\casos\\ejercicio3\\kmerge10.in.txt")));
//        sc = new Scanner(bf);
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
