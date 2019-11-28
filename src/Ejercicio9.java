import java.util.Scanner;

public class Ejercicio9 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        IList<Pair<Integer,Integer>> list = new LinkedList<>();
        int n = sc.nextInt();
        for (int j = 0; j < n; j++) {
            int i=sc.nextInt();
            int f=sc.nextInt();
            int h=sc.nextInt();

            list.addLast(new Pair<>(i,h));
            list.addLast(new Pair<>(f,0));
        }
        IList<Pair<Integer, Integer>> result = mergeSort(list);
        while (!result.isEmpty()){
            Pair<Integer, Integer> pair = result.getFirst();
            result.deleteFirst();
            System.out.println(pair.getV1()+" "+pair.getV2());
        }

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

    public interface IList<T> {
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

}
