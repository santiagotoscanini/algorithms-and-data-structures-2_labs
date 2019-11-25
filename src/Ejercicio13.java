import java.util.Scanner;

public class Ejercicio13 {
    static Scanner sc;

    static int Xf;
    static int Yf;
    static int K;
    static int mejorSolucion = Integer.MAX_VALUE;

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        K = sc.nextInt();
        ElementoMatriz[][] matriz = new ElementoMatriz[M][N];

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                matriz[i][j] = new ElementoMatriz(false, sc.nextInt());
            }
        }
        int numeroDeCasosDePrueba = sc.nextInt();
        while (numeroDeCasosDePrueba != 0) {
            int Yi = sc.nextInt() - 1;
            int Xi = sc.nextInt() - 1;
            Yf = sc.nextInt() - 1;
            Xf = sc.nextInt() - 1;

            laberinto(Xi, Yi, matriz, 0);
            resetearMatriz(matriz);
            if (mejorSolucion == Integer.MAX_VALUE) {
                System.out.println(0);
            } else {
                System.out.println(mejorSolucion);
            }
            numeroDeCasosDePrueba--;
        }
    }

    private static void resetearMatriz(ElementoMatriz[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                mat[i][j].setVisitado(false);
            }
        }
    }

    private static void laberinto(int Xi, int Yi, ElementoMatriz[][] mat, int costoActual) {
        if (!esValido(Xi, Yi, mat)) {
            return;
        }

        int nuevoValorActual = costoActual + mat[Xi][Yi].getValor();
        if (nuevoValorActual > K) return;

        if (esMejorSolucion(Xi, Yi, nuevoValorActual)) {
            mejorSolucion = nuevoValorActual;
        }

        mat[Xi][Yi].setVisitado(true);

        laberinto(Xi - 1, Yi, mat, nuevoValorActual);
        laberinto(Xi + 1, Yi, mat, nuevoValorActual);
        laberinto(Xi, Yi - 1, mat, nuevoValorActual);
        laberinto(Xi, Yi + 1, mat, nuevoValorActual);

        mat[Xi][Yi].setVisitado(false);
    }

    private static boolean esMejorSolucion(int x, int y, int valorActual) {
        return x == Xf && y == Yf && valorActual < mejorSolucion;
    }

    private static boolean esValido(int x, int y, ElementoMatriz[][] mat) {
        return x >= 0 &&
                y >= 0 &&
                x < mat.length &&
                y < mat[0].length &&
                mat[x][y].getValor() != 0;
    }

    private static class ElementoMatriz {

        private boolean visitado;
        private int valor;

        ElementoMatriz(boolean visitado, int valor) {
            this.visitado = visitado;
            this.valor = valor;
        }

        public boolean isVisitado() {
            return visitado;
        }

        public void setVisitado(boolean visitado) {
            this.visitado = visitado;
        }

        public int getValor() {
            return valor;
        }

        public void setValor(int valor) {
            this.valor = valor;
        }

    }
}

