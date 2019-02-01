import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {

    private int rows = 3, cols = 4;

    private GridPane gridPane;

    private RREF rref = new RREF(new double[rows][cols]);

    private StackPane setupUI() {
        Text title = new Text("Matrix RREF Solver");
        title.setFont(Font.font(16));
        Text subtitle = new Text("by Tarun Boddupalli");
        subtitle.setFont(Font.font(12));
        VBox titleBox = new VBox(5, title, subtitle);
        titleBox.setAlignment(Pos.TOP_CENTER);

        IntegerTextField rowField = new IntegerTextField(rows);
        IntegerTextField colField = new IntegerTextField(cols);
        addRowsChangeListener(rowField);
        addColsChangeListener(colField);
        HBox rowColFieldContainer = new HBox(5, rowField, new Text("x"), colField);
        rowColFieldContainer.setAlignment(Pos.CENTER);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rref.getMatrix()[i][j] = 0;
                gridPane.add(new MatrixTextField(i, j, rref.getMatrix()), j, i);
            }
        }

        Button solveBtn = new Button("Solve");
        solveBtn.setOnAction(event -> {
            getCells().forEach(mtf -> rref.getMatrix()[mtf.getRow()][mtf.getCol()] = Double.parseDouble(mtf.getText()));
            rref.solve();
            getCells().forEach(MatrixTextField::update);
        });
        Button clearBtn = new Button("Clear");
        clearBtn.setOnAction(event -> getCells().forEach(MatrixTextField::zero));
        HBox btnBox = new HBox(5, solveBtn, clearBtn);
        btnBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(20, titleBox, rowColFieldContainer, gridPane, btnBox);
        vBox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(vBox);
        root.setPadding(new Insets(10));
        return root;
    }

    private Stream<MatrixTextField> getCells() {
        return gridPane.getChildrenUnmodifiable().stream().map(node -> (MatrixTextField) node);
    }

    private void addRowsChangeListener(IntegerTextField textField) {
        textField.textProperty().addListener((o, oldValue, newValue) -> {
            int val = Integer.parseInt(newValue);
            if (val > 0) {
                textField.setStyle("-fx-text-inner-color: black;");
                double[][] matrixCopy = Arrays.stream(rref.getMatrix()).map(double[]::clone).toArray(double[][]::new);
                rref.setMatrix(new double[val][cols]);
                getCells().forEach(mtf -> mtf.setMatrix(rref.getMatrix()));
                if (val > rows) {
                    for (int i = 0; i < val; i++) {
                        if (i < rows) {
                            rref.getMatrix()[i] = matrixCopy[i];
                        } else {
                            Arrays.fill(rref.getMatrix()[i], 0);
                        }
                    }
                    for (int i = rows; i < val; i++) {
                        MatrixTextField[] mtfs = new MatrixTextField[cols];
                        for (int j = 0; j < mtfs.length; j++) {
                            mtfs[j] = new MatrixTextField(i, j, rref.getMatrix());
                        }
                        gridPane.addRow(i, mtfs);
                    }
                } else if (val < rows) {
                    for (int i = 0; i < val; i++) {
                        rref.getMatrix()[i] = matrixCopy[i];
                    }
                    gridPane.getChildren().removeAll(
                            getCells().filter(mtf -> GridPane.getRowIndex(mtf) >= val).collect(Collectors.toList())
                    );
                }
                rows = val;
            } else {
                textField.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    private void addColsChangeListener(IntegerTextField textField) {
        textField.textProperty().addListener((o, oldValue, newValue) -> {
            int val = Integer.parseInt(newValue);
            if (val > 0) {
                textField.setStyle("-fx-text-inner-color: black;");
                double[][] matrixCopy = Arrays.stream(rref.getMatrix()).map(double[]::clone).toArray(double[][]::new);
                rref.setMatrix(new double[rows][val]);
                getCells().forEach(mtf -> mtf.setMatrix(rref.getMatrix()));
                if (val > cols) {
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < val; j++) {
                            if (j < cols) {
                                rref.getMatrix()[i][j] = matrixCopy[i][j];
                            } else {
                                rref.getMatrix()[i][j] = 0;
                            }
                        }
                    }
                    for (int j = cols; j < val; j++) {
                        MatrixTextField[] mtfs = new MatrixTextField[rows];
                        for (int i = 0; i < mtfs.length; i++) {
                            mtfs[i] = new MatrixTextField(i, j, rref.getMatrix());
                        }
                        gridPane.addColumn(j, mtfs);
                    }
                } else if (val < rows) {
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < val; j++) {
                            rref.getMatrix()[i][j] = matrixCopy[i][j];
                        }
                    }
                    gridPane.getChildren().removeAll(
                            getCells().filter(mtf -> GridPane.getColumnIndex(mtf) >= val).collect(Collectors.toList())
                    );
                }
                cols = val;
            } else {
                textField.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    @Override
    public void start(Stage stage) {

        StackPane root = setupUI();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Matrix RREF Solver");
        stage.setMinWidth(500);
        stage.setMinHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
