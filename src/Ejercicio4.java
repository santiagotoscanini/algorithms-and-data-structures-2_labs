import java.lang.reflect.Array;
import java.util.Scanner;

public class Ejercicio4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph g = new Graph(sc,false);
        IList<Integer> sort = g.topologicOrder();

        while(!sort.isEmpty()){
            System.out.println(sort.getFirst());
            sort.deleteFirst();
        }
    }
    private interface IList<T> {
        public abstract boolean isEmpty();

        public abstract void addFirst(T t);

        public abstract void addLast(T t);

        public abstract T get(int pos);

        public abstract T get(T t);

        public abstract T getFirst();

        public abstract T getLast();

        public abstract boolean deleteFirst();

        public abstract boolean deleteLast();

        public abstract boolean contains(T t);

        public abstract boolean delete(T t);

        public abstract int size();
    }

    private static class LinkedList<T> implements IList<T>{

        // Attributes
        int size;
        Node<T> fst;
        Node<T> lst;

        // Constructors
        public LinkedList() {
            size = 0;
            fst = lst = null;
        }

        // Methods

        public boolean isEmpty() {
            return size == 0;
        }

        public void addFirst(T t) {
            Node<T> newNode;
            if (size == 0) {
                newNode = new Node<T>(null, t, null);
                this.lst = newNode;
            } else {
                newNode = new Node<T>(null, t, this.fst);
                this.fst.pre = newNode;
            }
            this.fst = newNode;
            this.size++;
        }

        public void addLast(T t) {
            Node<T> newNode;
            if (size == 0) {
                newNode = new Node<T>(null, t, null);
                this.fst = newNode;
            } else {
                newNode = new Node<T>(this.lst, t, null);
                this.lst.next = newNode;
            }
            this.lst = newNode;
            this.size++;
        }

        public T get(int pos) {
            Node<T> aux = this.fst;
            while (pos != 0) {
                aux = aux.next;
                pos--;
            }
            return aux.data;
        }

        public boolean contains(T element) {
            Node<T> iterNode = this.fst;
            while (iterNode != null) {
                if (iterNode.data.equals(element)) {
                    return true;
                }
                iterNode = iterNode.next;
            }
            return false;
        }

        public T get(T t) {
            Node<T> iterNode = this.fst;
            while (iterNode != null) {
                if (iterNode.data.equals(t)) {
                    return iterNode.data;
                }
                iterNode = iterNode.next;
            }
            return null;
        }

        public T getFirst() {
            if(this.fst==null){
                return null;
            }else {
                return this.fst.data;
            }
        }

        public T getLast() {
            if(this.lst==null){
                return null;
            }else {
                return this.lst.data;
            }
        }

        public boolean deleteFirst() {
            if(this.fst!=null){
                if(this.fst.next!=null){
                    this.fst=this.fst.next;
                    this.fst.pre=null;
                }else{
                    this.fst=this.lst=null;
                }

                this.size--;
                return true;
            }else{
                return false;
            }
        }

        public boolean deleteLast() {
            if(this.lst!=null){
                if(this.lst.pre!=null){
                    this.lst=this.lst.pre;
                    this.lst.next=null;
                }else{
                    this.lst=this.fst=null;
                }

                this.size--;
                return true;
            }else{
                return false;
            }
        }

        public boolean delete(T t) {
            if (this.contains(t)) {
                Node<T> nodeToDelete = this.fst;
                while (!nodeToDelete.data.equals(t)) {
                    nodeToDelete = nodeToDelete.next;
                }

                if (nodeToDelete.pre == null) {
                    this.deleteFirst();
                } else if (nodeToDelete.next == null) {
                    this.deleteLast();
                } else {
                    nodeToDelete.pre.next = nodeToDelete.next;
                    nodeToDelete.next.pre = nodeToDelete.pre;
                }
                this.size--;
                return true;
            }else{
                return false;
            }
        }

        public int size() {
            return this.size;
        }

        @Override
        public String toString() {
            String str = "";
            Node<T> node = this.fst;
            while (node != null) {
                str += node.data.toString() + "-";
                node = node.next;
            }
            if (str.length() == 1) {
                str = "";
            } else if (str.length() > 0) {
                str = str.substring(0, str.length() - 1);
            }
            return str;
        }
    }

    private static class Node<T> {
        public T data;
        public Node<T> next;
        public Node<T> pre;
        public int balance;

        public Node(Node<T> p, T d, Node<T> n) {
            this.data = d;
            this.pre = p;
            this.next = n;
        }

        public Node(Node<T> p, T d, Node<T> n, int b) {
            this.data = d;
            this.pre = p;
            this.next = n;
            this.balance = b;
        }
    }

    private static class Graph {
        private boolean weighted;
        private BinaryHeap<Pair<Integer,Integer>>[] adjacencyList;
        private Integer[] inDegree;
        private Integer[] backPath;

        public Graph(Scanner sc,boolean weighted){

            int v=sc.nextInt();
            int e = sc.nextInt();

            this.weighted = weighted;
            this.adjacencyList = (BinaryHeap<Pair<Integer,Integer>>[]) Array.newInstance(BinaryHeap.class,v+1);
            this.inDegree = new Integer[v+1];

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

        public void printPath( Integer v ) {

            if( backPath[v] != -1 ) {
                printPath( backPath[v] );
                System.out.print(" to ");
            }
            System.out.print(v);
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
            if(queue.isEmpty()){
                queue.addFirst(1);
            }
            while(!queue.isEmpty()){
                int v = queue.getFirst();
                queue.deleteFirst();
                nodeList.addLast(v);

                BinaryHeap<Pair<Integer,Integer>> auxList= new BinaryHeap<>(10);

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

    private static class BinaryHeap<T>{
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
        public T getMin() {
            return this.table[1].getV1();
        }

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

}
