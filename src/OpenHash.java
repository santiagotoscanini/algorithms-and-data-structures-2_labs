import java.util.function.Function;

public class OpenHash<T> implements Hash<T> {
	// Attributes
	private ListAbstract<T>[] hashTable;
	private Function<T, Integer> toNat;
	private Integer size;
	private boolean update;

	// Methods
	@SuppressWarnings("unchecked")
	public OpenHash(int size, Function<T, Integer> toNat, boolean update) {
		this.size = size;
		this.hashTable = new NodeList[size];
		for (int i = 0; i < hashTable.length; i++) {
			this.hashTable[i] = new NodeList<T>();
		}
		this.toNat = toNat;
		this.update = update;
	}

	@Override
	public int h1(T elem) {
		return toNat.apply(elem) % this.size;
	}

	@Override
	public void addElement(T t) {
		int pos = h1(t);
		assert (pos >= 0);
		assert (pos < this.size);

		if (this.hashTable[pos].contains(t)) {
			if (update) {
				this.deleteElement(this.hashTable[pos].get(t));
				this.addElement(t);
			}
		} else {
			this.hashTable[pos].addLast(t);
		}
	}

	@Override
	public void deleteElement(T t) {
		if (this.contains(t)) {
			this.hashTable[h1(t)].delete(t);
		}
	}

	@Override
	public boolean contains(T t) {
		return this.hashTable[h1(t)].contains(t);
	}

	@Override
	public T get(T t) {
		if (this.contains(t)) {
			return this.hashTable[h1(t)].get(t);
		}
		return null;
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
