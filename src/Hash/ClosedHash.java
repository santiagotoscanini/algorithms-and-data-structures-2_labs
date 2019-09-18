package Hash;

import Auxiliars.Pair;

import java.lang.reflect.Array;
import java.util.function.Function;

public class ClosedHash<T> implements IHash<T> {

    // Attributes
    enum State {
        FREE, OFF, USED;
    }

    private Pair<State, T>[] hashTable;
    private Function<T, Integer> toNat;
    private Integer size;
    private Integer inUse;
    private boolean update;

    // Methods
    // Privates

    private boolean spaceLeft() {
        return ((double) (this.inUse) / (double) this.size) > 0.6;
    }

    private boolean IsPrime(int number) {
        if (number == 2 || number == 3)
            return true;

        if (number % 2 == 0 || number % 3 == 0)
            return false;

        int divisor = 6;
        while (divisor * divisor - 2 * divisor + 1 <= number) {
            if (number % (divisor - 1) == 0)
                return false;

            if (number % (divisor + 1) == 0)
                return false;

            divisor += 6;

        }
        return true;
    }

    private int NextPrime(int a) {
        while (!IsPrime(++a)) {
        }
        return a;
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        this.size = this.NextPrime(this.size * 2);
        this.inUse = 0;
        Pair<State, T>[] auxTable = (Pair<State, T>[]) Array.newInstance(Pair.class, this.hashTable.length);
        System.arraycopy(this.hashTable, 0, auxTable, 0, this.hashTable.length);
        this.hashTable = (Pair<State, T>[]) Array.newInstance(Pair.class, this.size);
        for (int i = 0; i < hashTable.length; i++) {
            this.hashTable[i] = new Pair<State, T>(State.FREE, null);
        }
        for (int i = 0; i < auxTable.length; i++) {
            if (auxTable[i] != null && auxTable[i].getV1() == State.USED) {
                this.addElement(auxTable[i].getV2());
            }
        }
    }

    private int search(T t) {
        int pos = h1(t);
        int firstPosDown = -1; // Have not occurred yet

        for (int i = 0; !(this.hashTable[pos].getV1() == State.FREE
                || (this.hashTable[pos].getV1() == State.USED && this.hashTable[pos].getV2().equals(t))); i++) {
            if (this.hashTable[pos].getV1() == State.OFF && firstPosDown < 0) {
                firstPosDown = pos;
            }
            if (pos == h1(t) && i != 0) {
                return h1(t);
            }
            pos = (pos + h2(t)) % this.size;
        }

        if (this.hashTable[pos].getV1() == State.FREE && firstPosDown >= 0) {
            pos = firstPosDown;
        }
        return pos;
    }

    // PUBLICs
    @SuppressWarnings("unchecked")
    public ClosedHash(int size, Function<T, Integer> toNat, boolean update) {
        this.size = size;
        this.inUse = 0;
        this.hashTable = (Pair<State, T>[]) Array.newInstance(Pair.class, size);
        for (int i = 0; i < hashTable.length; i++) {
            this.hashTable[i] = new Pair<State, T>(State.FREE, null);
        }
        this.toNat = toNat;
        this.update = update;
    }

    @Override
    public int h1(T t) {
//		System.out.println(toNat.apply(t) % this.size);

        return toNat.apply(t) % this.size;
    }

    public int h2(T t) { // Steps
        return toNat.apply(t) % (size - 2) + 1;
    }

    @Override
    public void addElement(T t) {
        if (this.spaceLeft()) {
//			System.out.println("holaaa");
            this.expand();
        }
        int pos = this.search(t);
//		System.out.println(pos);
        if (this.hashTable[pos].getV1() == State.FREE) {
            this.hashTable[pos].setV2(t);
            this.hashTable[pos].setV1(State.USED);
            this.inUse++;
        } else if (this.hashTable[pos].getV1() == State.USED && this.update) {
            this.hashTable[pos].setV2(t);
            this.hashTable[pos].setV1(State.USED);
        } else if (this.hashTable[pos].getV1() == State.OFF) {
            this.hashTable[pos].setV1(State.USED);
            this.hashTable[pos].setV2(t);
        }
    }

    @Override
    public void deleteElement(T t) {
        int pos = search(t);
        if (this.hashTable[pos].getV1() == State.USED && this.hashTable[pos].getV2().equals(t)) {
            this.hashTable[pos].setV1(State.OFF);
        }
    }

    @Override
    public boolean contains(T t) {
        return this.hashTable[this.search(t)].getV1() == State.USED && this.hashTable[this.search(t)].getV2().equals(t);
    }

    @Override
    public T get(T t) {
        return this.hashTable[this.search(t)].getV2();
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
