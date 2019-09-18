import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) throws FileNotFoundException {
//
//		PriorityQueueExtended<Integer> cola = new BinaryHeapExtended<Integer>(20, (Pair<Integer,Integer> p)-> {
//			return p.getV1();
//		});
//
//		cola.insert(12, 12);
//		cola.insert(2, 2);
//		cola.insert(22, 22);
//		cola.insert(233, 233);
//		cola.insert(1, 1);
//		cola.removeMin();
//		cola.delete(22);
//		cola.delete(2222222);
//		cola.changePriority(2, 4444);
//		System.out.println(cola);

//		Hash<Pair<String,Integer>> h = new ClosedHash<Pair<String,Integer>>(20, (Pair<String,Integer> p)->{
//			Integer hash= 0;
//			for (int j = 0; j < p.getV1().length(); j++) {
//				hash += p.getV1().charAt(j)*2;
//			}
//			return hash;
//		},true);
//		
//		h.addElement(new Pair<String,Integer>("foss",3));
//		h.addElement(new Pair<String,Integer>("foss",5));
//		System.out.println(h);
//		File f = new File("D:\\shared\\A2\\Obligatorio\\casos\\ejercicio1\\10000000.in.txt");
//		BufferedReader br= new BufferedReader(new FileReader(f));
//        AVL<Integer> tree = new AVL<Integer>();
		Scanner sc = new Scanner(System.in);
		Hash<Integer> h = new ClosedHash<Integer>(sc.nextInt(),(Integer i)->{
			return i;
		},false);

		while(sc.hasNextInt()){
			int n=sc.nextInt();
			h.addElement(n);
		}
		
		sc.nextInt();

		while (sc.hasNextInt()){
			int n=sc.nextInt();
			System.out.println(h.contains(n)?1:0);
		}


    }
}
