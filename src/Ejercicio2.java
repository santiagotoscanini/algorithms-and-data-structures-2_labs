import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.function.Function;

public class Ejercicio2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int maxN = sc.nextInt();
        sc.nextLine();

        IHash<String> hash = new OpenHash<String>(maxN*3,(String src)->{
            int nat = 0;

            for (int i = 0; i < src.length(); i++) {
                nat+= (int) Math.round(src.charAt(i)*0.2);
            }

            return nat%Integer.MAX_VALUE;
        });

        for (int i = 0; i < maxN; i++) {
            hash.addElement(sc.nextLine());
        }
        int maxM = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < maxM; i++) {
            String line =sc.nextLine();
            System.out.println(hash.contains(line)?"1":"0");
            //hash.deleteElement(line);
        }
    }

    private static interface IHash<T> {
        public abstract void addElement(T t);

        public abstract void deleteElement(T t);

        public abstract boolean contains(T t);

        public abstract T get(T t);

        public abstract int h1(T t);
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

    private static class OpenHash<T> implements IHash<T> {
        // Attributes
        private IList<T>[] hashTable;
        private Function<T, Integer> toNat;
        private Integer size;

        // Methods
        @SuppressWarnings("unchecked")
        public OpenHash(int size, Function<T, Integer> toNat) {
            this.size = size;
            this.hashTable = new LinkedList[size];
            for (int i = 0; i < hashTable.length; i++) {
                this.hashTable[i] = new LinkedList<T>();
            }
            this.toNat = toNat;
        }

        @Override
        public int h1(T elem) {
            return toNat.apply(elem) % this.size;
        }

        @Override
        public void addElement(T t) {
            int pos = h1(t);
            assert (pos >= 0);
            assert (pos < this.size);

            this.hashTable[pos].addLast(t);
        }

        @Override
        public void deleteElement(T t) {
            if (this.contains(t)) {
                this.hashTable[h1(t)].delete(t);
            }
        }

        @Override
        public boolean contains(T t) {
            return this.hashTable[h1(t)].contains(t);
        }

        @Override
        public T get(T t) {
            if (this.contains(t)) {
                return this.hashTable[h1(t)].get(t);
            }
            return null;
        }

        @Override
        public String toString() {
            String str = "";
            for (int i = 0; i < hashTable.length; i++) {
                str += i + ": " + this.hashTable[i].toString() + "\n";
            }
            return str;
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


}
