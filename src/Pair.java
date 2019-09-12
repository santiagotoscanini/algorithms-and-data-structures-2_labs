import java.util.Comparator;

public class Pair<T1, T2>{

	// Attributes
	private T1 v1;
	private T2 v2;

	// Constructors
	public Pair(T1 v1, T2 v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	// Methods
	public T1 getV1() {
		return v1;
	}

	public T2 getV2() {
		return v2;
	}

	public void setV1(T1 value) {
		this.v1 = value;
	}

	public void setV2(T2 value) {
		this.v2 = value;
	}

	@Override
	public String toString() {
		return "(" + this.v1 + "," + this.v2 + ")";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		Pair<T1, T2> p = (Pair<T1, T2>) obj;
		
		return p.getV1().equals(getV1());
	}

}
