package List;

import Auxiliars.Node;

public class LinkedList<T> implements IList<T> {

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
    public void delete(T t) {
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
