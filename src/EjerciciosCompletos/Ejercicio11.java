package EjerciciosCompletos;

import java.util.Scanner;

public class Ejercicio11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int p = sc.nextInt();

        for (int i = 0; i < p; i++) {
            int n1 = sc.nextInt();
            int n2 = sc.nextInt();
            System.out.println(combination(n1,n2));
        }
    }

    static long combination(int n, int k) {
        long[] arr = new long[1];
        arr[0] = 1;
        for (int i = 1; i <= n; i++) {
            long[] arrAux = new long[i + 1];
            arrAux[0] = 1;
            arrAux[i] = 1;
            for (int j = 1; j < i; j++) {
                arrAux[j] = arr[j - 1] + arr[j];
            }
            arr = arrAux;
        }
        return arr[k];
    }
}
