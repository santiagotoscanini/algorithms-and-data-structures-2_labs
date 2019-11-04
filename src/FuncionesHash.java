public interface FuncionesHash<T> {
    boolean sonIguales(T t1, T t2);
    int key(T t1);
}