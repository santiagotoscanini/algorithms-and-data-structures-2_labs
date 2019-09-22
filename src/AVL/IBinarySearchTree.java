package AVL;

public interface IBinarySearchTree<T> {
    public abstract void insert(T elem);

    public abstract boolean delete(T elem);

    public abstract T getElem(T elem);
}
