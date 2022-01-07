package com.example.matrix_v1;


public class GaussJordan {

    static int sizeMatriz = 4;
    static float[][] matriz = new float[sizeMatriz][sizeMatriz + 1];

    //public static void main(String args[]) {

        //lenarMatriz();
        //gaussjordan();

    //}

    public static void llenarMatriz() {
        //si queremos ver los valores de x agregamos un mas uno a contador
        for (int i = 0; i < sizeMatriz; i++) {
            for (int j = 0; j < sizeMatriz; j++) {
                String ell = "INTRODUZCA EL ELEMENTO : " + i + " " + j;

               //matriz[i][j] = Float.parseFloat(JOptionPane.showInputDialog(ell));

            }
        }

    }

    static void pivote(float matriz[][], int piv, int var) {
        float temp = 0;
        temp = matriz[piv][piv];
        for (int y = 0; y < (var + 1); y++) {

            matriz[piv][y] = matriz[piv][y] / temp;
        }
    }

    static void hacerceros(float matriz[][], int piv, int var) {
        for (int x = 0; x < var; x++) {
            if (x != piv) {
                float c = matriz[x][piv];
                for (int z = 0; z < (var + 1); z++) {
                    matriz[x][z] = ((-1 * c) * matriz[piv][z]) + matriz[x][z];
                }
            }
        }
    }

    //si queremos ver los valores de x agregamos un mas uno a contador

    static void muestramatriz(float matriz[][], int var) {
        for (int x = 0; x < var; x++) {
            for (int y = 0; y < (var); y++) {
               System.out.print(" " + matriz[x][y]);
            }

               System.out.println("");
        }

    }

    public static void gaussjordan() {
        int piv = 0;

        for (int a = 0; a < sizeMatriz; a++) {
            pivote(matriz, piv, sizeMatriz);
            hacerceros(matriz, piv, sizeMatriz);
            piv++;
            if (a == sizeMatriz - 2) {
               System.out.println("\tRenglon " + (a + 1) + " entre el pivote");
               System.out.println("\tHaciendo ceros");
               muestramatriz(matriz, sizeMatriz);
               System.out.println("");
            }
        }

    }

}
