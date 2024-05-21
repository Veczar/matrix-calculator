package pk.wieik.matrix_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HelloController {

    @FXML
    private Spinner<Integer> matrixRows, matrixColumns;
    @FXML
    private GridPane matrixAGrid, matrixBGrid;

    @FXML
    private void initialize() {
        updateMatrixGrid(matrixAGrid, matrixRows.getValue(), matrixColumns.getValue());
        updateMatrixGrid(matrixBGrid, matrixRows.getValue(), matrixColumns.getValue());

        // matrix A
        matrixRows.valueProperty().addListener((obs, oldVal, newVal) ->
                updateMatrixGrid(matrixAGrid, newVal, matrixColumns.getValue()));
        matrixColumns.valueProperty().addListener((obs, oldVal, newVal) ->
                updateMatrixGrid(matrixAGrid, matrixRows.getValue(), newVal));

        // matrix B
        matrixRows.valueProperty().addListener((obs, oldVal, newVal) ->
                updateMatrixGrid(matrixBGrid, newVal, matrixColumns.getValue()));
        matrixColumns.valueProperty().addListener((obs, oldVal, newVal) ->
                updateMatrixGrid(matrixBGrid, matrixRows.getValue(), newVal));
    }

    private void updateMatrixGrid(GridPane gridPane, int rows, int columns) {
        gridPane.getChildren().clear();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                TextField textField = new TextField();
                textField.setPrefWidth(50);
                gridPane.add(textField, col, row);
            }
        }
    }

    @FXML
    private void extractMatrices() {
        double[][] matrixA = extractMatrixValues(matrixAGrid);
        double[][] matrixB = extractMatrixValues(matrixBGrid);

        printMatrix(matrixA, "Matrix A");
        printMatrix(matrixB, "Matrix B");
    }

    private double[][] extractMatrixValues(GridPane gridPane) {
        int rows = gridPane.getRowCount();
        int columns = gridPane.getColumnCount();
        double[][] matrix = new double[rows][columns];

        for (var node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                Integer row = GridPane.getRowIndex(node);
                Integer col = GridPane.getColumnIndex(node);

                if (row != null && col != null) {
                    TextField textField = (TextField) node;
                    String text = textField.getText();
                    matrix[row][col] = text.isEmpty() ? 0 : Double.parseDouble(text);
                }
            }
        }

        return matrix;
    }

    private void printMatrix(double[][] matrix, String matrixName) {
        System.out.println(matrixName + ":");
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}