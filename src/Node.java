
public class Node<T> {
	T data;
	Node<T> next;
	Node<T> pre;
	int balance;

	Node(T d) {
		this.data = d;
	}

	Node(Node<T> p, T d, Node<T> n) {
		this.data = d;
		this.pre = p;
		this.next = n;
	}
	Node(Node<T> p, T d, Node<T> n,int b) {
		this.data = d;
		this.pre = p;
		this.next = n;
		this.balance=b;
	}
}