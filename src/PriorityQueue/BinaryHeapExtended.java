package BinaryHeap;

import Auxiliars.Pair;
import Hash.ClosedHash;
import Hash.IHash;

import java.lang.reflect.Array;
import java.util.function.Function;

public class BinaryHeapExtended<T> implements IPriorityQueueExtended<T> {

    // Attributes
    private Pair<T, Integer>[] table;
    private Integer limit;
    private Integer priority;
    private IHash<Pair<T, Integer>> IHashTable;

    // Constructor
    @SuppressWarnings("unchecked")
    public BinaryHeapExtended(Integer size, Function<T, Integer> f) {
        this.table = (Pair<T, Integer>[]) Array.newInstance(Pair.class, size + 1);
        this.limit = 0;
        this.priority = 0;
        Function<Pair<T,Integer>,Integer> function = (Pair<T,Integer> p) ->{
            return f.apply(p.getV1());
        };
        this.IHashTable = new ClosedHash<Pair<T, Integer>>(size, function, true);
    }

    // Operations
    // Private
    private boolean isInternNode(Integer pos) {
        if (pos * 2 > this.limit && pos * 2 + 1 > this.limit) {
            return false;
        } else {
            return (pos * 2 <= this.limit && this.table[pos * 2] != null)
                    || (pos * 2 + 1 <= this.limit && this.table[pos * 2 + 1] != null);
        }
    }

    private int leastSon(Integer pos) {
        int firstSon = -1, secondSon = -1;
        if (this.limit > pos * 2 && this.table[pos * 2] != null) {
            firstSon = pos * 2;
        }
        if (this.limit > pos * 2 + 1 && this.table[pos * 2 + 1] != null) {
            secondSon = pos * 2;
        }
        return Integer.min(firstSon, secondSon);
    }

    private int getParent(Integer pos) {
        return pos / 2;
    }

    private void sink(Integer pos) {
        if (this.isInternNode(pos)) {
            int lSon = this.leastSon(pos);
            if (lSon != -1 && this.table[lSon].getV2() < this.table[pos].getV2()) {// USE COMPARE
                this.swap(lSon, pos);
                this.sink(lSon);
            }
        }
    }

    private void mFloat(Integer pos) {
        if (pos > 1 && this.table[this.getParent(pos)].getV2() > this.table[pos].getV2()) {// USE COMPARE
            this.swap(this.getParent(pos), pos);
            this.mFloat(this.getParent(pos));
        }
    }

    private void swap(Integer a, Integer b) {
        Pair<T, Integer> aux = this.table[a];
        this.table[a] = this.table[b];
        this.table[b] = aux;

        this.IHashTable.addElement(new Pair<T, Integer>(this.table[a].getV1(), a));
        this.IHashTable.addElement(new Pair<T, Integer>(this.table[b].getV1(), b));

    }

    private boolean isFull() {
        return this.limit == this.table.length - 1;
    }

    // Public
    @Override
    public T getMin() {
        return this.table[1].getV1();
    }

    @Override
    public boolean removeMin() {
        if (this.limit != 0) {
            this.IHashTable.deleteElement(new Pair<T, Integer>(this.table[1].getV1(), 1));
            this.table[1] = this.table[this.limit];
            this.limit--;
            this.sink(1);
            return true;
        }
        return false;
    }

    @Override
    public void insert(T t, Integer priority) {
        if (this.isFull()) {
            @SuppressWarnings("unchecked")
            Pair<T, Integer>[] newTable = (Pair<T, Integer>[]) Array.newInstance(Pair.class, this.table.length * 3);
            for (int i = 0; i < this.table.length; i++) {
                newTable[i] = this.table[i];
            }
            this.table = newTable;
        }
        Pair<T, Integer> newData = new Pair<T, Integer>(t, priority + this.priority);
        this.limit++;
        this.table[this.limit] = newData;
        this.IHashTable.addElement(new Pair<T, Integer>(t, this.limit));
        this.mFloat(limit);
    }

    @Override
    public boolean isEmpty() {
        return this.limit==0;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 1; i <= this.limit; i++) {
            str += this.table[i].getV1().toString() + ":" + this.table[i].getV2() + "\n";
        }
        return str;
    }

    @Override
    public boolean changePriority(T elem, Integer newPriority) {
        if (this.IHashTable.contains(new Pair<T, Integer>(elem, -1))) {
            int elemPos = this.IHashTable.get(new Pair<T, Integer>(elem, 2)).getV2();
            this.table[elemPos].setV2(newPriority);
            this.mFloat(elemPos);

            elemPos = this.IHashTable.get(new Pair<T, Integer>(elem, 2)).getV2();
            this.sink(elemPos);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(T elem) {
        if (this.IHashTable.contains(new Pair<T, Integer>(elem, -1))) {
            int elemPos = this.IHashTable.get(new Pair<T, Integer>(elem, 2)).getV2();
            this.limit--;
            this.IHashTable.deleteElement(new Pair<T, Integer>(this.table[elemPos].getV1(), elemPos));
            this.table[elemPos] = this.table[this.limit];
            this.sink(elemPos);
            return true;
        }
        return false;
    }

    @Override
    public void changeAllPrioriy(Integer newPriority) {
        this.priority = newPriority;
    }

}
