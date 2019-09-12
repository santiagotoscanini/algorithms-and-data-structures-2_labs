
public interface PriorityQueueExtended<T> extends PriorityQueue<T>{

	public abstract boolean changePriority(T elem,Integer newPriority);
	public abstract boolean delete(T elem);
	public abstract void changeAllPrioriy(Integer newPriority);

}
