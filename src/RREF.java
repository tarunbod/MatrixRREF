import java.util.Arrays;

public class RREF {
    private double[][] matrix;

    RREF(double[][] matrix) {
        this.matrix = matrix;
    }

    double[][] getMatrix() {
        return matrix;
    }

    void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    void solve() {
//        printMatrix(false);
        int pivotRow, pivotCol;
        for (pivotRow = 0; pivotRow < matrix.length; pivotRow++) {
            boolean found = false;
            for (pivotCol = 0; pivotCol < matrix[0].length; pivotCol++) {
                for (int i = pivotRow; i < matrix.length; i++) {
                    if (matrix[i][pivotCol] != 0) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (found) {
//                System.out.println("Using pivot column " + (pivotCol + 1));
                int swapRow = 0;
                for (int i = pivotRow; i < matrix.length; i++) {
                    if (matrix[i][pivotCol] != 0) {
                        swapRow = i;
                        break;
                    }
                }
                if (swapRow != pivotRow) {
//                    System.out.println("Swapping rows " + (swapRow + 1) + " and " + (pivotRow + 1));
                    double[] temp = Arrays.copyOf(matrix[pivotRow], matrix[0].length);
                    matrix[pivotRow] = matrix[swapRow];
                    matrix[swapRow] = temp;
//                    printMatrix(false);
                }
                for (int i = pivotRow + 1; i < matrix.length; i++) {
                    if (matrix[i][pivotCol] != 0) {
                        double factor = -1 * matrix[i][pivotCol] / matrix[pivotRow][pivotCol];
                        double[] pivotRowArr = Arrays.copyOf(matrix[pivotRow], matrix[0].length);
                        multiply(pivotRowArr, factor);
                        add(matrix[i], pivotRowArr);
//                        System.out.println("Multiplying row " + (pivotRow + 1) + " by " + factor + "; adding result to row " + (i + 1));
//                        printMatrix(false);
                    }
                }
            }
        }
//        System.out.println("NOW IN ROW ECHELON FORM");
        printMatrix(true);
        pivotCol = -1;
        for (pivotRow = matrix.length - 1; pivotRow >= 0; pivotRow--) {
            for (int i = 0; i < matrix[0].length; i++) {
                if (matrix[pivotRow][i] != 0) {
                    pivotCol = i;
                    break;
                }
            }
            if (pivotCol != -1) {
                double factor = 1 / matrix[pivotRow][pivotCol];
                multiply(matrix[pivotRow], factor);
//                System.out.println("Multiplying row " + (pivotRow + 1) + " by " + factor);
                printMatrix(false);
                for (int i = pivotRow - 1; i >= 0; i--) {
                    factor = -1 * matrix[i][pivotCol];
                    double[] pivotRowArr = Arrays.copyOf(matrix[pivotRow], matrix[0].length);
                    multiply(pivotRowArr, factor);
                    add(matrix[i], pivotRowArr);
//                    System.out.println("Multiplying row " + (pivotRow + 1) + " by " + factor + "; adding result to row " + (i + 1));
//                    printMatrix(false);
                }
            }
        }
//        System.out.println("NOW IN REDUCED ROW ECHELON FORM:");
//        printMatrix(true);
    }

    private void multiply(double[] arr, double factor) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= factor;
        }
    }

    private void add(double[] arr1, double[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] += arr2[i];
        }
    }

    void printMatrix(boolean fancy) {
        if (fancy) {
            for (int i = 0; i < matrix[0].length * 10 + 1; i++) {
                System.out.print("=");
            }
            System.out.println();
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (fancy) {
                    System.out.print("|| ");
                }
                System.out.printf("%6.2f ", matrix[i][j]);
            }
            if (fancy) {
                System.out.print("||");
            }
            System.out.println();
        }
        if (fancy) {
            for (int i = 0; i < matrix[0].length * 10 + 1; i++) {
                System.out.print("=");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        RREF r = new RREF(new double[][]{
//                {0, 0,  2, -4, -5,  2,  5},
//                {0, 1, -1,  1,  3,  1, -1},
//                {0, 6,  0, -6,  5, 16,  7}
                {1, 2, -1, 2, 1, 2},
                {-1, -2, 1, 2, 3, 6},
                {2, 4, -3, 2, 0, 3},
                {-3, -6, 2, 0, 3, 9}
        });
        r.solve();
        r.printMatrix(true);
    }
}