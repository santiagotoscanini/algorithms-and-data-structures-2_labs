import java.lang.reflect.Array;
import java.util.Scanner;

public class Ejercicio5 {

    private static Scanner sc;
    private static DijkstraShortestPath dij;

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        int V = sc.nextInt();
        int E = sc.nextInt();

        IList<Pair<Integer,Integer>>[] adj = (LinkedList<Pair<Integer,Integer>>[])
                Array.newInstance(LinkedList.class, V + 1);

        for (int i = 1; i <= V; i++) {
            adj[i] = new LinkedList<>();
        }

        for (int i = 0; i < E; i++) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            int peso = sc.nextInt();
            adj[v].addLast(new Pair<>(w, peso));
        }

        int nodes = sc.nextInt();

        for (int i = 0; i < nodes; i++) {
            int node=sc.nextInt();
            dij = new DijkstraShortestPath(V);
            dij.dijkstra(adj, node);
            for (int j=1;j<=V;j++) {
                if(j==node){
                    System.out.println(-1);
                }else{
                    System.out.println(dij.dist[j]==Integer.MAX_VALUE?-1:dij.dist[j]);
                }
            }
        }

    }

    public static class DijkstraShortestPath {
        private int dist[];
        private boolean[] settled;
        private IPriorityQueue<Pair<Integer,Integer>> pq;
        private int V;
        IList<Pair<Integer,Integer>>[] adj;

        DijkstraShortestPath(int V) {
            this.V = V;
            dist = new int[V+1];
            settled = new boolean[V+1];
            pq = new BinaryHeap<>(V);
        }

        public void dijkstra(IList<Pair<Integer,Integer>>[] adj, int src) {
            this.adj = adj;
            for (int i = 1; i <= V; i++) {
                dist[i] = Integer.MAX_VALUE;
                settled[i] = false;
            }

            dist[src] = 0;
            settled[src] = true;
            for (int i = 1; i <= V; i++) {
                pq.insert(new Pair<Integer,Integer>(i, dist[i]), dist[i]);
            }

            while (!pq.isEmpty()) {
                int nodeNumber = pq.getMin().getV1();
                pq.removeMin();
                settled[nodeNumber] = true;
                e_Neighbours(nodeNumber);
            }
        }

        private void e_Neighbours(int u) {
            int edgeDistance = -1;
            int newDistance = -1;
            IList<Pair<Integer,Integer>> copyList = adj[u].copyList();

            while(!copyList.isEmpty()){
                Pair<Integer,Integer> v = copyList.getFirst();

                if (!settled[v.getV1()]) {
                    edgeDistance = v.getV2();
                    newDistance = dist[u] + edgeDistance;

                    if (newDistance < dist[v.getV1()]){
                        dist[v.getV1()] = newDistance;
                        pq.insert(v, dist[v.getV1()]);
                    }
                }
                copyList.deleteFirst();
            }
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

    private interface IPriorityQueue<T> {
        public abstract T getMin();

        public abstract boolean removeMin();

        public abstract void insert(T t, Integer priority);

        public abstract boolean isEmpty();
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

        public abstract IList<T> copyList();
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

        public IList<T> copyList(){
            IList<T> c1 = new LinkedList<>();
            IList<T> c2 = new LinkedList<>();

            while (!this.isEmpty()){
                c1.addLast(this.getFirst());
                c2.addLast(this.getFirst());

                this.deleteFirst();
            }

            while(!c2.isEmpty()){
                this.addLast(c2.getFirst());
                c2.deleteFirst();
            }

            return c1;
        }

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

}
