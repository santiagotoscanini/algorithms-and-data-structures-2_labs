import Auxiliars.Pair;
import Auxiliars.SortFunctions;
import Graph.Graph;
import List.IList;
import List.LinkedList;
import List.*;
import PriorityQueue.BinaryHeap;
import PriorityQueue.IPriorityQueue;

import java.lang.reflect.Array;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Launcher {

    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        Exercise12();
    }

    private static void Exercise6() {
        Graph g = new Graph(sc, true);
        System.out.println(g.Kruskal());
    }

    private static void Exercise5() {
        int V = sc.nextInt();
        int E = sc.nextInt();
        LinkedList<Pair<Integer, Integer>> arr[] = (LinkedList<Pair<Integer, Integer>>[]) Array.newInstance(LinkedList.class, V + 1);

        for (int i = 1; i < V + 1; i++) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            int peso = sc.nextInt();
            arr[v].addLast(new Pair<>(w, peso));
        }

        System.out.println("xd");


    }

    private static void Exercise4() {
        Graph g = new Graph(sc, false);
        IList<Integer> sort = g.topologicOrder();
        System.out.println(sort);
    }

    public static IList<Pair<Integer, Integer>> mergeSort(IList<Pair<Integer, Integer>> list) {
        if (list.size() >= 2) {
            Integer middle = list.size() / 2;
            IList<Pair<Integer, Integer>> l = new LinkedList<>();
            IList<Pair<Integer, Integer>> r = new LinkedList<>();

            Integer i = 0;
            while (!list.isEmpty()) {
                if (i < middle) {
                    l.addLast(list.getFirst());
                } else {
                    r.addLast(list.getFirst());
                }
                list.deleteFirst();
                i++;
            }

            l = mergeSort(l);
            r = mergeSort(r);
            return merge(l, r);
        } else {
            return list;
        }
    }

    private static IList<Pair<Integer, Integer>> merge(IList<Pair<Integer, Integer>> l, IList<Pair<Integer, Integer>> r) {
        IList<Pair<Integer, Integer>> list = new LinkedList<>();
        Integer h1 = 0, h2 = 0, lh = 0;

        while (!l.isEmpty() && !r.isEmpty()) {
            Pair<Integer, Integer> p1 = l.getFirst();
            Pair<Integer, Integer> p2 = r.getFirst();

            if (p1.getV1() <= p2.getV1()) {
                h1 = p1.getV2();
                Integer max = Integer.max(h1, h2);
                if (max != lh) {
                    p1.setV2(max);
                    list.addLast(p1);
                }
                lh = max;

                l.deleteFirst();
            } else {
                h2 = p2.getV2();
                Integer max = Integer.max(h1, h2);
                if (max != lh) {
                    p2.setV2(max);
                    list.addLast(p2);
                }
                lh = max;
                r.deleteFirst();
            }
        }

        IList<Pair<Integer, Integer>> aux;
        if (l.isEmpty()) {
            aux = r;
        } else {
            aux = l;
        }

        while (!aux.isEmpty()) {
            list.addLast(aux.getFirst());
            aux.deleteFirst();
        }

        return list;
    }

    private static void Exercise9() {
        IList<Pair<Integer, Integer>> list = new LinkedList<>();
        int n = sc.nextInt();
        for (int j = 0; j < n; j++) {
            int i = sc.nextInt();
            int f = sc.nextInt();
            int h = sc.nextInt();

            list.addLast(new Pair<>(i, h));
            list.addLast(new Pair<>(f, 0));
        }

        System.out.println("aloha");
        System.out.println(mergeSort(list));
    }

    private static void Exercise3() {
        int k = sc.nextInt();
        IPriorityQueue<IList<Integer>> queue = new BinaryHeap<IList<Integer>>(k);

        for (int i = 0; i < k; i++) {
            int elem_cant = sc.nextInt();
            IList<Integer> list = new LinkedList<Integer>();
            while (elem_cant != 0) {
                elem_cant--;
                list.addLast(sc.nextInt());
            }
            queue.insert(list, list.getFirst());
        }

        IList<Integer> list = new LinkedList<Integer>();

        while (!queue.isEmpty()) {
            IList<Integer> listInQueue = queue.getMin();
            list.addLast(listInQueue.getFirst());
            queue.removeMin();
            listInQueue.deleteFirst();
            if (!listInQueue.isEmpty()) {
                queue.insert(listInQueue, listInQueue.getFirst());
            }
        }
        String result = "";
        while (!list.isEmpty()) {
            if (list.size() != 1) {
                result += list.getFirst() + "\n";
            } else {
                result += list.getFirst();
            }
            list.deleteFirst();
        }
        System.out.println(result);


    }

    Integer[] copyArr(Integer[] arr) {
        Integer[] arrCopy = new Integer[arr.length];

        for (int i = 0; i < arr.length; i++) {
            arrCopy[i] = arr[i];
        }

        return arrCopy;
    }

    static Integer combination(Integer n, Integer k) {
        Integer iterativeN = 0;
        Integer[] arr = new Integer[1];
        arr[0] = 1;
        for (int i = 1; i <= n; i++) {
            Integer[] arrAux = new Integer[i + 1];
            arrAux[0] = 1;
            arrAux[i] = 1;
            for (int j = 1; j < i; j++) {
                arrAux[j] = arr[j - 1] + arr[j];
            }
            arr = arrAux;
        }
        return arr[k];
    }

    private static void Exercise12() {
        int k = sc.nextInt();
        int[] values = new int[k + 1];

        values[0] = 0;
        for (int i = 1; i <= k; i++) {
            values[i] = sc.nextInt();
        }

        int m = sc.nextInt();
        int[][] matrix = new int[k + 1][m + 1];

        //Cargo la matriz
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= m; j++) {
                matrix[i][j] = 0;
            }
        }
        for (int i = 1; i <= k; i++) {
            for (int j = 0; j <= m; j++) {
                if (values[i] > j) {
                    matrix[i][j] = matrix[i - 1][j];
                } else {
                    matrix[i][j] = Integer.max(matrix[i - 1][j], matrix[i - 1][j - values[i]] + values[i]);
                }
            }
        }

        int p = sc.nextInt();
        while (p > 0) {
            int i = sc.nextInt();

            boolean exist = false;
            for (int j = 0; j <= k; j++) {
                if (matrix[j][i] == i) {
                    exist = true;
                }
            }
            System.out.println(exist ? "1" : "0");
            p--;
        }

    }
}
