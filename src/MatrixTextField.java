public class MatrixTextField extends DoubleTextField {
    private int row, col;
    private double[][] matrix;

    public MatrixTextField(int row, int col, double[][] matrix) {
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

    public void update() {
        setText(Double.toString(matrix[row][col]));
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }
}
