package pk.wieik.matrix_calculator;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

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

            TextField rowSumField = new TextField();
            rowSumField.setPrefWidth(50);
            rowSumField.setEditable(false);
            GridPane.setMargin(rowSumField, new Insets(0, 0, 0, 10)); // Add margin around each row sum field
            gridPane.add(rowSumField, columns, row);

            Line hLine = new Line(0, 0, 5, 0); // line width adjusted to match TextField width
            hLine.setStrokeWidth(1);
            gridPane.add(hLine, columns, row);
            GridPane.setValignment(hLine, VPos.CENTER);

        }
        for (int col = 0; col < columns; col++) {
            TextField colSumField = new TextField();
            colSumField.setPrefWidth(50);
            colSumField.setEditable(false);
            GridPane.setMargin(colSumField, new Insets(10,0,0,0 ));
            gridPane.add(colSumField, col, rows);

            Line vLine = new Line(0, 0, 0, 5); // Adjust start and end points for better positioning
            vLine.setStrokeWidth(1);
            gridPane.add(vLine, col, rows);
            GridPane.setHalignment(vLine, HPos.CENTER);
            GridPane.setMargin(vLine, new Insets(0, 0, 30, 0)); // Adjust margin to move the line up
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