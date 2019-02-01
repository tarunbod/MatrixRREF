public class MatrixTextField extends DoubleTextField {
    private int row, col;
    private double[][] matrix;

    MatrixTextField(int row, int col, double[][] matrix) {
        super();
        this.row = row;
        this.col = col;
        this.matrix = matrix;

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                matrix[row][col] = Double.parseDouble(newValue);
            }
        });
    }

    void update() {
        setText(Double.toString(matrix[row][col]));
    }

    void zero() {
        setText("0");
    }

    int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }
}
