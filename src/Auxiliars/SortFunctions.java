package Auxiliars;


import List.IList;
import List.LinkedList;

public class SortFunctions<T extends Comparable<T>> {

    public IList<T> mergeSort(IList<T> list){
        if(list.size()>=2){
            Integer middle=list.size()/2;
            IList<T> l= new LinkedList<>();
            IList<T> r = new LinkedList<>();

            Integer i=0;
            while(!list.isEmpty()){
                if(i<middle){
                    l.addLast(list.getFirst());
                }else {
                    r.addLast(list.getFirst());
                }
                list.deleteFirst();
                i++;
            }

            l= mergeSort(l);
            r = mergeSort(r);
            return merge(l,r);
        }else{
            return list;
        }
    }

    private IList<T> merge(IList<T> l, IList<T> r) {
        IList<T> list = new LinkedList<>();

        while(!l.isEmpty() && !r.isEmpty()){
            if(l.getFirst().compareTo(r.getFirst())<=0){
                list.addLast(l.getFirst());
                l.deleteFirst();
            }else{
                list.addLast(r.getFirst());
                r.deleteFirst();
            }
        }

        IList<T> aux;
        if(l.isEmpty()){
            aux=r;
        }else{
            aux=l;
        }

        while(!aux.isEmpty()){
            list.addLast(aux.getFirst());
            aux.deleteFirst();
        }

        return list;
    }


}
