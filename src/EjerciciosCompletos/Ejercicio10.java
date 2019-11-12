package EjerciciosCompletos;

import List.IList;
import List.LinkedList;

import java.util.Scanner;

public class Ejercicio10 {
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        Exercise10();
    }

    private static void Exercise10() {
        int cantidadEdificios = sc.nextInt();
        int contador;
        for (int i = 0; i < cantidadEdificios; i++) {
            contador = 0;
            int alcanceDelOperador = sc.nextInt();
            int pisosDelEdificio = sc.nextInt();

            IList<Integer> operadores = new LinkedList<>();
            IList<Integer> cuadrillas = new LinkedList<>();
            sc.nextLine();
            String[] piso = new String[pisosDelEdificio];

            for (int j = 0; j < pisosDelEdificio; j++) {
                piso[j] = sc.nextLine();
            }
            for (int j = 0; j < pisosDelEdificio; j++) {
                char c = piso[j].charAt(0);
                if (c == 'C') {
                    cuadrillas.addLast(j);
                } else {
                    operadores.addLast(j);
                }
            }
            while (operadores.size() > 0 && cuadrillas.size() > 0) {
                int posOperador = operadores.getFirst();
                int posCuadrilla = cuadrillas.getFirst();

                if (Math.abs(posOperador - posCuadrilla) <= alcanceDelOperador) {
                    cuadrillas.deleteFirst();
                    contador++;
                } else {
                    if (operadores.getFirst() < cuadrillas.getFirst()) {
                        operadores.deleteFirst();
                    } else {
                        cuadrillas.deleteFirst();
                    }
                }
            }
            System.out.println(contador);
        }
    }
}
