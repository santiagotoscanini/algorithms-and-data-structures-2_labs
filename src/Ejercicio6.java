import java.lang.reflect.Array;
import java.util.Scanner;

public class Ejercicio6 {
    public static void main(String[] args) {
        Graph g = new Graph(new Scanner(System.in),true);
        System.out.print(g.Kruskal());
    }

    private static class Graph {
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

        int root(int x,Integer[] roots)
        {
            while(roots[x] != x)
            {
                roots[x] = roots[roots[x]];
                x = roots[x];
            }
            return x;
        }

        public Integer Kruskal(){
            Integer totalCost=0;
            IPriorityQueue<Pair<Pair<Integer,Integer>,Integer>> edgesOrdered = new BinaryHeap<>(10);
            Integer[]set = new Integer[this.v+1];
            for (int i = 1; i < set.length ; i++) {
                set[i]=i;
            }

            for (int i = 1; i < adjacencyList.length; i++) {
                IPriorityQueue<Pair<Integer,Integer>> auxList = new BinaryHeap<>(10);
                while(!adjacencyList[i].isEmpty()){
                    Pair<Integer,Integer> edge = this.adjacencyList[i].getMin();
                    auxList.insert(edge,edge.getV1());
                    this.adjacencyList[i].removeMin();
                    Pair<Pair<Integer,Integer>,Integer> newPair = new Pair<>(new Pair<>(i,edge.getV1()),edge.getV2());
                    edgesOrdered.insert(newPair,newPair.getV2());
                }
                adjacencyList[i]=auxList;
            }

            while(!edgesOrdered.isEmpty()){
                Pair<Pair<Integer,Integer>,Integer> edge = edgesOrdered.getMin();
                edgesOrdered.removeMin();
                Integer v1 =edge.getV1().getV1();
                Integer v2 = edge.getV1().getV2();
                if(root(v1,set) != root(v2,set)){
                    int p = root(v1,set);
                    int q = root(v2,set);
                    set[p] = set[q];
                    totalCost+=edge.getV2();
                }
            }

            return totalCost;
        }
    }

    private static class BinaryHeap<T> implements IPriorityQueue<T> {
        // Attributes
        private Pair<T, Integer>[] table;
        private Integer limit;

        // Constructor
        // unchecked
        public BinaryHeap(Integer size) {
            this.table = (Pair<T, Integer>[]) Array.newInstance(Pair.class, size + 1);
            this.limit = 0;
        }

        // Operations
        // Private
        private boolean isInternNode(Integer pos) {
            if (pos * 2 > this.limit && pos * 2 + 1 > this.limit) {
                return false;
            } else {
                return (pos * 2 <= this.limit && this.table[pos * 2] != null)
                        || (pos * 2 + 1 <= this.limit && this.table[pos * 2 + 1] != null);
            }
        }

        private int leastSon(Integer pos) {
            int firstSon = -1, secondSon = -1;
            if (this.limit >= pos * 2 && this.table[pos * 2] != null) {
                firstSon = pos * 2;
            }
            if (this.limit >= pos * 2 + 1 && this.table[pos * 2 + 1] != null) {
                secondSon = pos * 2 + 1;
            }
            if(firstSon==-1||secondSon==-1){
                return firstSon==-1?secondSon:firstSon;
            }else{
                if(this.table[firstSon].getV2()<this.table[secondSon].getV2()){
                    return firstSon;
                }else {
                    return secondSon;
                }
            }
        }

        private int getParent(Integer pos) {
            return pos / 2;
        }

        private void sink(Integer pos) {
            if (this.isInternNode(pos)) {
                int lSon = this.leastSon(pos);
                if (lSon != -1 && this.table[lSon].getV2() < this.table[pos].getV2()) {// USE COMPARE
                    this.swap(lSon, pos);
                    this.sink(lSon);
                }
            }
        }

        private void mFloat(Integer pos) {
            if (pos > 1 && this.table[this.getParent(pos)].getV2() > this.table[pos].getV2()) {// USE COMPARE
                this.swap(this.getParent(pos), pos);
                this.mFloat(this.getParent(pos));
            }
        }

        private void swap(Integer a, Integer b) {
            Pair<T, Integer> aux = this.table[a];
            this.table[a] = this.table[b];
            this.table[b] = aux;
        }

        private boolean isFull() {
            return this.limit == this.table.length - 1;
        }

        // Public
        @Override
        public T getMin() {
            return this.table[1].getV1();
        }

        @Override
        public boolean removeMin() {
            if (this.limit != 0) {
                this.table[1] = this.table[this.limit];
                this.sink(1);
                this.table[this.limit]=null;
                this.limit--;
                return true;
            }
            return false;
        }

        @Override
        public void insert(T t, Integer priority) {
            if (this.isFull()) {
                @SuppressWarnings("unchecked")
                Pair<T, Integer>[] newTable = (Pair<T, Integer>[]) Array.newInstance(Pair.class, this.table.length * 3);
                for (int i = 0; i < this.table.length; i++) {
                    newTable[i] = this.table[i];
                }
                this.table = newTable;
            }
            Pair<T, Integer> newData = new Pair<T, Integer>(t, priority);
            this.limit++;
            this.table[this.limit] = newData;
            this.mFloat(limit);
        }

        @Override
        public boolean isEmpty() {
            return this.limit==0;
        }

        @Override
        public String toString() {
            String str = "";
            for (int i = 1; i <= this.limit; i++) {
                str += this.table[i].getV1().toString() + ":" + this.table[i].getV2() + "\n";
            }
            return str;
        }

    }

    static interface IPriorityQueue<T> {
        public abstract T getMin();

        public abstract boolean removeMin();

        public abstract void insert(T t, Integer priority);

        public abstract boolean isEmpty();
    }

    private static class Pair<T1, T2> {

        // Attributes
        private T1 v1;
        private T2 v2;

        // Constructors
        public Pair(T1 v1, T2 v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        // Methods
        public T1 getV1() {
            return v1;
        }

        public T2 getV2() {
            return v2;
        }

        public void setV1(T1 value) {
            this.v1 = value;
        }

        public void setV2(T2 value) {
            this.v2 = value;
        }

        @Override
        public String toString() {
            return "(" + this.v1 + "," + this.v2 + ")";
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            Pair<T1, T2> p = (Pair<T1, T2>) obj;

            return p.getV1().equals(getV1());
        }

    }
}
