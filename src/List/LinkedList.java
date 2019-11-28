package List;

import Auxiliars.Node;

public class LinkedList<T> implements IList<T>{

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

