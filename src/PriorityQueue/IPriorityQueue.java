package PriorityQueue;

public interface IPriorityQueue<T> {
    public abstract T getMin();

    public abstract boolean removeMin();

    public abstract void insert(T t, Integer priority);

    public abstract boolean isEmpty();
}
