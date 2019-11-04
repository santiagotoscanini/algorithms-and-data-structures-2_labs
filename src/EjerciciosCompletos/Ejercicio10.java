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

            for (int j = 0; j < Math.max(operadores.size(), cuadrillas.size()); j++) {
                if (j >= cuadrillas.size()) {
                    if (Math.abs(cuadrillas.getFirst() - operadores.getFirst()) <= alcanceDelOperador) {
                        operadores.deleteFirst();
                        contador++;
                    }
                } else if (j >= operadores.size()) {
                    if (Math.abs(cuadrillas.getFirst() - operadores.getFirst()) <= alcanceDelOperador) {
                        cuadrillas.deleteFirst();
                        contador++;
                    }
                } else {
                    if (Math.abs(cuadrillas.getFirst() - operadores.getFirst()) <= alcanceDelOperador) {
                        contador++;
                        cuadrillas.deleteFirst();
                        operadores.deleteFirst();
                    } else if (cuadrillas.getFirst() > operadores.getFirst()) operadores.deleteFirst();
                    else cuadrillas.deleteFirst();
                }
            }
            System.out.println(contador);
        }
    }
}
