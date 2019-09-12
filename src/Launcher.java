import java.util.Scanner;

public class Launcher {
	private static Scanner sc;

	public static void main(String[] args) {
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

		AVL<Integer> tree = new AVL<Integer>();
		tree.insert(23);
		tree.insert(2);
		tree.insert(21);
		tree.insert(4);
		System.out.println(tree.printASC());
		System.out.println("holaa");
	}
}
