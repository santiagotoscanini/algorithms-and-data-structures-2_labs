package EjerciciosCompletos;

import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int maxN = sc.nextInt();
        AVL<Integer> avl = new AVL<>();

        for (int i = 0; i < maxN; i++) {
            avl.insert(sc.nextInt());
        }

        System.out.println(avl.printASC());
    }
     static class Node<T> {
        T data;
        Node<T> next;
        Node<T> pre;
        int balance;

        Node(Node<T> p, T d, Node<T> n) {
            this.data = d;
            this.pre = p;
            this.next = n;
        }

        Node(Node<T> p, T d, Node<T> n, int b) {
            this.data = d;
            this.pre = p;
            this.next = n;
            this.balance = b;
        }
    }

    static class AVL<T extends Comparable<T>> {

        private Node<T> firstNode;
        private boolean h_variant;

        AVL() {
            this.h_variant = false;
            this.firstNode = null;
        }

        private Node<T> insertRecursive(Node<T> node, T elem) {
            Node<T> n1, n2;
            if (node == null) {
                node = new Node<T>(null, elem, null, 0);
                this.h_variant = true;
            } else if (node.data.compareTo(elem) >= 1) {
                node.pre = insertRecursive(node.pre, elem);
                if (this.h_variant) {
                    switch (node.balance) {
                        case 1:
                            node.balance = 0;
                            h_variant = false;
                            break;
                        case 0:
                            node.balance = -1;
                            break;
                        case -1:
                            n1 = node.pre;
                            if (n1.balance == -1) {
                                node.pre = n1.next;
                                n1.next = node;
                                node.balance = 0;
                                node = n1;
                            } else {
                                n2 = n1.next;
                                n1.next = n2.pre;
                                n2.pre = n1;
                                node.pre = n2.next;
                                n2.next = node;
                                node.balance = n2.balance == -1 ? 1 : 0;
                                n1.balance = n2.balance == 1 ? -1 : 0;
                                node = n2;
                            }
                            node.balance = 0;
                            this.h_variant = false;
                    }
                }
            } else if (node.data.compareTo(elem) <= -1) {
                node.next = insertRecursive(node.next, elem);
                if (this.h_variant) {
                    switch (node.balance) {
                        case -1:
                            node.balance = 0;
                            h_variant = false;
                            break;
                        case 0:
                            node.balance = 1;
                            break;
                        case 1:
                            n1 = node.next;
                            if (n1.balance == 1) {
                                node.next = n1.pre;
                                n1.pre = node;
                                node.balance = 0;
                                node = n1;
                            } else {
                                n2 = n1.pre;
                                n1.pre = n2.next;
                                n2.next = n1;
                                node.next = n2.pre;
                                n2.pre = node;
                                node.balance = n2.balance == 1 ? -1 : 0;
                                n1.balance = n2.balance == -1 ? 1 : 0;
                                node = n2;
                            }
                            node.balance = 0;
                            this.h_variant = false;
                    }
                }
            } else {
                this.h_variant = false;
                node.data = elem;
            }
            return node;
        }

        void insert(T elem) {
            this.firstNode = this.insertRecursive(this.firstNode, elem);
        }

        boolean delete(T elem) {
            return false;
        }

        T getElem(T elem) {
            return null;
        }

        private String printASCrec(Node<T> n) {
            if (n != null) {
                String str = "";
                str += printASCrec(n.pre);
                str += n.data + "\n";
                str += printASCrec(n.next);
                return str;
            } else {
                return "";
            }

        }

        String printASC() {
            return this.printASCrec(this.firstNode);
        }
    }

}

