interface Comparador<T>{
    boolean comparar(T a,T b);
}
public class Heap<P,T> {
     class Asociacion <P,T>{
        P prio;
        T dato;
        Asociacion(P prio, T dato){
            this.prio = prio;
            this.dato = dato;
        }
    } 
    private Asociacion<P,T> heap[];
    private int tope;
    private int posRaiz=1;
    private Comparador<P> comparador;

    public Heap(int largo,Comparador<P> comparador) {
        this.heap = new Asociacion[largo+1];
        this.tope = 0;
        this.posRaiz = 1;
        this.comparador = comparador;
    }

    public void insertar(P p, T t){
        if(!estaLLeno()){
            heap[++tope]=new Asociacion<P,T>(p,t);
            flotar(tope);
        }
    }

    public T getMin(){
        return heap[posRaiz].dato;
    }

    public void borrarMin(){
        assert(!esVacio());
        heap[posRaiz]=heap[tope];
        heap[tope]=null;
        tope--;
        hundir(posRaiz);
    }

    private boolean estaLLeno(){
        return tope>heap.length-1;
    }
    private boolean esVacio(){
        return tope==0;
    }

    private int posPadre(int n){
        return n/2;
    }
    private int posHijoIzq(int n){
        return n*2;
    }
    private int posHijoDer(int n){
        return n*2+1;
    }

    private void intercambiar(int pos1,int pos2){
        Asociacion<P,T> aux= heap[pos1];
        heap[pos1]=heap[pos2];
        heap[pos2]=aux;
    }
    private boolean buenPadre(int pospadre,int poshijo){
        if(heap[poshijo]==null)return true;
        return comparador.comparar(heap[pospadre].prio,heap[poshijo].prio);
    }

    private void flotar(int pos){
        if(pos>posRaiz){
            if(!buenPadre(posPadre(pos),pos)){
                intercambiar(posPadre(pos),pos);
                flotar(posPadre(pos));
            }
        }
    }

    private int posMenorHijo(int pos){
        int posI= posHijoIzq(pos);
        int posD = posHijoDer(pos);
        if(posD>tope)return posI;
        if(comparador.comparar(heap[posI].prio,heap[posD].prio)) return posI;
        else return posD;
    }

    private boolean esNodoInterno(int pos){
        return posHijoIzq(pos)<=this.tope;
    }

    private void hundir(int pos){
        if(esNodoInterno(pos)){
            int posMenor= posMenorHijo(pos);
            if(!buenPadre(pos,posMenor)){
                intercambiar(pos,posMenor);
                hundir(posMenor);
            }
        }
    }
}
