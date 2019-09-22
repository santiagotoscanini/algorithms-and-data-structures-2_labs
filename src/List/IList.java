package List;

public interface IList<T> {
    public abstract boolean isEmpty();

    public abstract void addFirst(T t);

    public abstract void addLast(T t);

    public abstract T get(int pos);

    public abstract T get(T t);

    public abstract T getFirst();

    public abstract T getLast();

    public abstract boolean deleteFirst();

    public abstract boolean deleteLast();

    public abstract boolean contains(T t);

    public abstract boolean delete(T t);

    public abstract int size();
}
