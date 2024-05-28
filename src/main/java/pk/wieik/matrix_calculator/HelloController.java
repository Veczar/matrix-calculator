package pk.wieik.matrix_calculator;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

import java.util.List;
import java.util.Random;

public class HelloController {
    
    @FXML
    private Label correctnessLabel;

    @FXML
    private Spinner<Integer> matrixRows, matrixColumns;

    @FXML
    private GridPane matrixAGrid, matrixBGrid, matrixResultGrid;

    private Matrix matrixA;
    private Matrix matrixB;
    private Matrix resultMatrix;

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
    
    private void updateMatrixGridLocked(GridPane gridPane, int rows, int columns) {
        gridPane.getChildren().clear();
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                addTextFieldLocked(0,0, gridPane, col, row);
            }
            addTextFieldLocked(0, 10, gridPane, columns, row);
            addRowLine(gridPane, columns, row);
        }
        for (int col = 0; col < columns; col++) {
            addTextFieldLocked(10, 0, gridPane, col, rows);
            addColLine(gridPane, rows, col);
        }
        addTextFieldLocked(10, 10, gridPane, columns, rows);
    }
    
    private static void addTextFieldLocked(int top, int left, GridPane gridPane, int columns, int row) {
        TextField textField = new TextField();
        textField.setPrefWidth(50);
        textField.setEditable(false);
        GridPane.setMargin(textField, new Insets(top, 0, 0, left)); // Add margin around each row sum field
        gridPane.add(textField, columns, row);
    }
    
    // re-renders grid with new size
    private void updateMatrixGrid(GridPane gridPane, int rows, int columns) {
        gridPane.getChildren().clear();
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                addTextField(0,0, gridPane, col, row);
            }
            addTextField(0, 10, gridPane, columns, row);
            addRowLine(gridPane, columns, row);
        }
        for (int col = 0; col < columns; col++) {
            addTextField(10, 0, gridPane, col, rows);
            addColLine(gridPane, rows, col);
        }
        addTextField(10, 10, gridPane, columns, rows);
    }

    private static void addTextField(int top, int left, GridPane gridPane, int columns, int row) {
        TextField textField = new TextField();
        textField.setPrefWidth(50);
        GridPane.setMargin(textField, new Insets(top, 0, 0, left)); // Add margin around each row sum field
        gridPane.add(textField, columns, row);
    }

    private static void addColLine(GridPane gridPane, int rows, int col) {
        Line vLine = new Line(0, 0, 0, 5); // Adjust start and end points for better positioning
        vLine.setStrokeWidth(1);
        gridPane.add(vLine, col, rows);
        GridPane.setHalignment(vLine, HPos.CENTER);
        GridPane.setMargin(vLine, new Insets(0, 0, 30, 0)); // Adjust margin to move the line up
    }

    private static void addRowLine(GridPane gridPane, int columns, int row) {
        Line hLine = new Line(0, 0, 5, 0); // line width adjusted to match TextField width
        hLine.setStrokeWidth(1);
        gridPane.add(hLine, columns, row);
        GridPane.setValignment(hLine, VPos.CENTER);
    }

    @FXML
    private void extractMatrices() {
        matrixA = new Matrix("Matrix A", matrixAGrid);
        matrixB = new Matrix("Matrix B", matrixBGrid);

        System.out.println(matrixA);
        System.out.println(matrixB);
    }
    
    @FXML
    private void add() {
        matrixA = new Matrix("Matrix A", matrixAGrid);
        matrixB = new Matrix("Matrix B", matrixBGrid);

        resultMatrix = Calculator.addMatrices(matrixA, matrixB);
        List<Position> mismatchPositions = Calculator.checkCorrectnessAddition(matrixA, matrixB, resultMatrix);
        updateResultText(mismatchPositions.isEmpty());
        updateView(mismatchPositions);

        System.out.println(resultMatrix);
        System.out.println(matrixA);
        System.out.println(matrixB);
    }

    @FXML
    private void sub() {
        matrixA = new Matrix("Matrix A", matrixAGrid);
        matrixB = new Matrix("Matrix B", matrixBGrid);

        resultMatrix = Calculator.subMatrices(matrixA, matrixB);
        List<Position> mismatchPositions = Calculator.checkCorrectnessSubtraction(matrixA, matrixB, resultMatrix);
        updateResultText(mismatchPositions.isEmpty());
        updateView(mismatchPositions);

        System.out.println(resultMatrix);
        System.out.println(matrixA);
        System.out.println(matrixB);
    }

    private void updateResultText(boolean isvalid) {
        if (isvalid) {
            correctnessLabel.setText("All checksums are correct!");
            correctnessLabel.setStyle("-fx-text-fill: green;");
        }
        else {
            correctnessLabel.setText("Checksum mismatch!");
            correctnessLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void updateView(List<Position> mismatchPositions) {
        // Update checksums for input matrices
        matrixA.fillGridPane(matrixAGrid);
        matrixB.fillGridPane(matrixBGrid);

        // Show result matrix
        updateMatrixGridLocked(matrixResultGrid, matrixRows.getValue(), matrixColumns.getValue());
        resultMatrix.fillGridPane(matrixResultGrid);

        // Highlight the mismatch positions if there are any
        if (!mismatchPositions.isEmpty()) {
            for (Position pos : mismatchPositions) {
                for (Node node : matrixResultGrid.getChildren()) {
                    if (node instanceof TextField) {
                        Integer row = GridPane.getRowIndex(node);
                        Integer col = GridPane.getColumnIndex(node);

                        if (row != null && col != null && row == pos.row && col == pos.col) {
                            node.setStyle("-fx-background-color: red;");
                        }
                    }
                }
            }
        }
    }
    
    @FXML
    private void clearSums() {
        int rowSize = matrixA.getRowsCount();
        int colSize = matrixA.getColsCount();
        correctnessLabel.setText("");

        for (int i = 0; i < rowSize + 1; i++) {
            matrixA.get()[i][colSize] = 0;
            matrixB.get()[i][colSize] = 0;
        }

        for (int i = 0; i < colSize + 1; i++) {
            matrixA.get()[rowSize][i] = 0;
            matrixB.get()[rowSize][i] = 0;
        }

        matrixA.fillGridPane(matrixAGrid);
        matrixB.fillGridPane(matrixBGrid);

        for (var node : matrixAGrid.getChildren()) {
            if (node instanceof TextField) {
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);

                if (row == rowSize) {
                    ((TextField) node).clear();
                }
                if (col == colSize) {
                    ((TextField) node).clear();
                }
            }
        }

        for (var node : matrixBGrid.getChildren()) {
            if (node instanceof TextField) {
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);

                if (row == rowSize) {
                    ((TextField) node).clear();
                }
                if (col == colSize) {
                    ((TextField) node).clear();
                }
            }
        }

        System.out.println(matrixA.get()[rowSize][0]);
        System.out.println(matrixA);
        System.out.println(matrixB);
    }

    @FXML
    private void clearAll() {
        correctnessLabel.setText("");
        if (matrixA != null) matrixA.clear();
        if (matrixB != null) matrixB.clear();
//        if (resultMatrix != null) resultMatrix.clear();
        
        for (var node : matrixAGrid.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
        }
        for (var node : matrixBGrid.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
        }
    }

    @FXML
    private void fillBorderMatrices() {
        fillGridPaneBordersWithRandomValues(matrixAGrid);
        fillGridPaneBordersWithRandomValues(matrixBGrid);
    }

    @FXML
    private void fillNonBorderMatrices() {
        fillGridPaneNonBordersWithRandomValues(matrixAGrid);
        fillGridPaneNonBordersWithRandomValues(matrixBGrid);
    }

    private void fillGridPaneBordersWithRandomValues(GridPane gridPane) {
        Random random = new Random();
        int rows = matrixRows.getValue();
        int cols = matrixColumns.getValue();

        for (var node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                Integer row = GridPane.getRowIndex(node);
                Integer col = GridPane.getColumnIndex(node);

                if (row != null && col != null) {
                    if (row == rows || col == cols) {
                        TextField textField = (TextField) node;
                        double randomValue = Math.round(random.nextDouble() * 100.0) ;  // Random value between 0 and 100
                        textField.setText(String.format("%.2f", randomValue));
                    }
                }
            }
        }
    }

    private void fillGridPaneNonBordersWithRandomValues(GridPane gridPane) {
        Random random = new Random();
        int rows = matrixRows.getValue();
        int cols = matrixColumns.getValue();

        for (var node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                Integer row = GridPane.getRowIndex(node);
                Integer col = GridPane.getColumnIndex(node);

                if (row != null && col != null) {
                    if (row != rows && col != cols) {
                        TextField textField = (TextField) node;
                        double randomValue = Math.round(random.nextDouble() * 100.0);  // Random value between 0 and 100
                        textField.setText(String.format("%.2f", randomValue));
                    }
                }
            }
        }
    }
}
