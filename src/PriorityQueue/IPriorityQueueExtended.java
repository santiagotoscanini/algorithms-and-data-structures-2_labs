package PriorityQueue;

public interface IPriorityQueueExtended<T> extends IPriorityQueue<T> {
    public abstract boolean changePriority(T elem, Integer newPriority);

    public abstract boolean delete(T elem);

    public abstract void changeAllPrioriy(Integer newPriority);

}
