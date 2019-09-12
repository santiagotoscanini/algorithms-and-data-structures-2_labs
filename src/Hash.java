
public interface Hash<T> {
	public abstract void addElement(T t);
	public abstract void deleteElement(T t);
	public abstract boolean contains(T t);
	public abstract T get(T t);
	public abstract int h1(T t);
}
