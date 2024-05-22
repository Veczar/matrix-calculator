package pk.wieik.matrix_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HelloController {

    @FXML
    private Spinner<Integer> matrixRows, matrixColumns;

    @FXML
    private GridPane matrixAGrid, matrixBGrid, matrixResultGrid;

    private Matrix matrixA;
    private Matrix matrixB;

    @FXML
    private void initialize() {
        updateMatrixGrid(matrixAGrid, matrixRows.getValue(), matrixColumns.getValue());
        updateMatrixGrid(matrixBGrid, matrixRows.getValue(), matrixColumns.getValue());

        // matrix A
        matrixRows.valueProperty().addListener(
                (obs, oldVal, newVal) -> updateMatrixGrid(matrixAGrid, newVal, matrixColumns.getValue())
        );
        matrixColumns.valueProperty().addListener(
                (obs, oldVal, newVal) -> updateMatrixGrid(matrixAGrid, matrixRows.getValue(), newVal)
        );

        // matrix B
        matrixRows.valueProperty().addListener(
                (obs, oldVal, newVal) -> updateMatrixGrid(matrixBGrid, newVal, matrixColumns.getValue())
        );
        matrixColumns.valueProperty().addListener(
                (obs, oldVal, newVal) -> updateMatrixGrid(matrixBGrid, matrixRows.getValue(), newVal)
        );
    }
    
    // re-renders grid with new size
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
        matrixA = new Matrix(matrixAGrid, "Matrix A");
        matrixB = new Matrix(matrixBGrid, "Matrix B");

        System.out.println(matrixA);
        System.out.println(matrixB);
    }

    @FXML
    private void add() {
        matrixA = new Matrix(matrixAGrid, "Matrix A");
        matrixB = new Matrix(matrixBGrid, "Matrix B");

        Matrix resultMatrix = Calculator.addMatrices(matrixA, matrixB);
        Calculator.checkCorrectness(matrixA, matrixB, resultMatrix);
        matrixResultGrid = resultMatrix.getAsGridPane(matrixResultGrid);
        
        System.out.println(resultMatrix);
        System.out.println(matrixA);
        System.out.println(matrixB);
    }
    
    @FXML
    private void sub() {
        matrixA = new Matrix(matrixAGrid, "Matrix A");
        matrixB = new Matrix(matrixBGrid, "Matrix B");
        
        Matrix resultMatrix = Calculator.subMatrices(matrixA, matrixB);
        Calculator.checkCorrectness(matrixA, matrixB, resultMatrix);
        matrixResultGrid = resultMatrix.getAsGridPane(matrixResultGrid);
        
        System.out.println(resultMatrix);
        System.out.println(matrixA);
        System.out.println(matrixB);
    }
}