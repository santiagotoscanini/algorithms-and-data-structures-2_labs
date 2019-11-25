import java.util.Scanner;

public class Ejercicio10 {
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        Exercise10();
    }

    private static void Exercise10() {
        int cantidadEdificios = sc.nextInt();
        int contador;
        for (int i = 0; i < cantidadEdificios; i++) {
            contador = 0;
            int alcanceDelOperador = sc.nextInt();
            int pisosDelEdificio = sc.nextInt();

            IList<Integer> operadores = new LinkedList<>();
            IList<Integer> cuadrillas = new LinkedList<>();
            //sc.nextLine();
            String[] piso = new String[pisosDelEdificio];

            for (int j = 0; j < pisosDelEdificio; j++) {
                piso[j] = sc.next();
            }
            for (int j = 0; j < pisosDelEdificio; j++) {
                char c = piso[j].charAt(0);
                if (c == 'C') {
                    cuadrillas.addLast(j);
                } else {
                    operadores.addLast(j);
                }
            }
            while (operadores.size() > 0 && cuadrillas.size() > 0) {
                int posOperador = operadores.getFirst();
                int posCuadrilla = cuadrillas.getFirst();

                if (Math.abs(posOperador - posCuadrilla) <= alcanceDelOperador) {
                    cuadrillas.deleteFirst();
                    operadores.deleteFirst();
                    contador++;
                } else {
                    if (operadores.getFirst() < cuadrillas.getFirst()) {
                        operadores.deleteFirst();
                    } else {
                        cuadrillas.deleteFirst();
                    }
                }
            }
            System.out.println(contador);
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

    private static class LinkedList<T> implements IList<T> {

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

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
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

        @Override
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

        @Override
        public T get(int pos) {
            Node<T> aux = this.fst;
            while (pos != 0) {
                aux = aux.next;
                pos--;
            }
            return aux.data;
        }

        @Override
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

        @Override
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

        @Override
        public T getFirst() {
            if(this.fst==null){
                return null;
            }else {
                return this.fst.data;
            }
        }

        @Override
        public T getLast() {
            if(this.lst==null){
                return null;
            }else {
                return this.lst.data;
            }
        }

        @Override
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

        @Override
        public boolean deleteLast() {
            if(this.fst!=null){
                if(this.lst.pre!=null){
                    this.lst=this.fst.pre;
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
        @Override
        public boolean delete(T t) {
            if (this.contains(t)) {
                Node<T> nodeToDelete = this.fst;
                while (!nodeToDelete.data.equals(t)) {
                    nodeToDelete = nodeToDelete.next;
                }

                if (nodeToDelete.pre == null) {
                    this.fst = this.fst.next;
                    this.fst.pre = null;
                } else if (nodeToDelete.next == null) {
                    this.lst = this.lst.pre;
                    this.lst.next = null;
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

        @Override
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
