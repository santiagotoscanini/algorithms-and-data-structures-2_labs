public class AVL<T extends Comparable<T>> implements BinarySearchTree<T>{

    private Node<T> firstNode;
    private boolean h_variant;

   public AVL(){
        this.h_variant=false;
        this.firstNode= null;
    }

    private Node<T> insertRecursive(Node<T> node,T elem){
       Node<T> n1,n2;
        if(node==null){
            node= new Node<T>(null,elem,null,0);
            this.h_variant=true;
        }else if(node.data.compareTo(elem) > 1){
            node.pre = insertRecursive(node.pre,elem);
            if(this.h_variant){
                switch (node.balance){
                    case 1:
                        node.balance=0;
                        h_variant=false;
                        break;
                    case 0:
                        node.balance=-1;
                        break;
                    case -1:
                        n1=node.pre;
                        if(n1.balance == -1){
                            node.pre=n1.next;
                            n1.next=node;
                            node.balance=0;
                            node=n1;
                        }else {
                            n2=n1.next;
                            n1.next=n2.pre;
                            n2.pre=n1;
                            node.pre=n2.next;
                            n2.next=node;
                            node.balance=n2.balance==-1?1:0;
                            n1.balance=n2.balance==1?-1:0;
                            node=n2;
                        }
                        node.balance=0;
                        this.h_variant = false;
                }
            }
        }else if(node.data.compareTo(elem) < -1){
            node.next = insertRecursive(node.next,elem);
            if(this.h_variant){
                switch (node.balance){
                    case -1:
                        node.balance=0;
                        h_variant=false;
                        break;
                    case 0:
                        node.balance=1;
                        break;
                    case 1:
                        n1=node.next;
                        if(n1.balance == 1){
                            node.next=n1.pre;
                            n1.next=node;
                            node.balance=0;
                            node=n1;
                        }else {
                            n2=n1.pre;
                            n1.next=n2.pre;
                            n2.next=n1;
                            node.next=n2.pre;
                            n2.pre=node;
                            node.balance=n2.balance==1?-1:0;
                            n1.balance=n2.balance==-1?-1:0;
                            node=n2;
                        }
                        node.balance=0;
                        this.h_variant = false;
                }
            }
        }else{
            this.h_variant=false;
            node.data = elem;
        }
        return node;
    }
    @Override
    public void insert(T elem) {
           this.firstNode = this.insertRecursive(this.firstNode, elem);
    }

    @Override
    public boolean delete(T elem) {
        return false;
    }

    @Override
    public T getElem(T elem) {
        return null;
    }

    private String printASCrec(Node<T> n){
       if(n!=null) {
               return ""+printASCrec(n.pre) + n.data.toString() + printASCrec(n.next);
       }else{
           return "";
       }
    }

    public String printASC(){
       return this.printASCrec(this.firstNode);
    }
}
