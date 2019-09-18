package Auxiliars;

public class Node<T> {
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