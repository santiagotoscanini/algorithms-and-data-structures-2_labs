import java.util.Scanner;

public class Ejercicio12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int k = sc.nextInt();
        int[] values = new int[k + 1];

        values[0] = 0;
        for (int i = 1; i <= k; i++) {
            values[i] = sc.nextInt();
        }

        int m = sc.nextInt();
        int[][] matrix = new int[k + 1][m + 1];

        //Cargo la matriz
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= m; j++) {
                matrix[i][j] = 0;
            }
        }
        for (int i = 1; i <= k; i++) {
            for (int j = 0; j <= m; j++) {
                if (values[i] > j) {
                    matrix[i][j] = matrix[i - 1][j];
                } else {
                    matrix[i][j] = Integer.max(matrix[i - 1][j], matrix[i - 1][j - values[i]] + values[i]);
                }
            }
        }

        int p = sc.nextInt();
        while (p > 0) {
            int i = sc.nextInt();

            boolean exist = false;
            for (int j = 0; j <= k; j++) {
                if (matrix[j][i] == i) {
                    exist = true;
                }
            }
            p--;
            if(p==0){
                System.out.print(exist ? "1" : "0");
            }else{
                System.out.println(exist ? "1" : "0");
            }

        }


    }
}
