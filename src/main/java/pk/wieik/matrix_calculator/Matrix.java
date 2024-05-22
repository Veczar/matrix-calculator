package pk.wieik.matrix_calculator;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;

public class Matrix {
    @Getter
    private String name;
    private final int rowsCount;
    private final int colsCount;
    private final double[][] data;

    public double[][] get() {
        return this.data;
    }
    
    // number of rows without the checksum rows
    public int getRowsCount() {
        return rowsCount - 1;
    }
    
    // number of cols without the checksum cols
    public int getColsCount() {
        return colsCount - 1;
    }
    
    public Matrix(String name, int rowsCnt, int colsCnt) {
        this.name = name;
        // +1 to account for checksums at the last position
        this.rowsCount = rowsCnt + 1;
        this.colsCount = colsCnt + 1;
        this.data = new double[rowsCount][colsCount];
    }

    public Matrix(GridPane gridPane, String name) {
        this.name = name;
        // +1 to account for checksums at the last position
        this.rowsCount = gridPane.getRowCount() + 1;
        this.colsCount = gridPane.getColumnCount() + 1;
        this.data = new double[rowsCount][colsCount];
        extractMatrixValues(gridPane);
    }

    private void extractMatrixValues(GridPane gridPane) {
        for (var node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                Integer row = GridPane.getRowIndex(node);
                Integer col = GridPane.getColumnIndex(node);

                if (row != null && col != null) {
                    TextField textField = (TextField) node;
                    String text = textField.getText();
                    data[row][col] = text.isEmpty() ? 0 : Double.parseDouble(text);
                }
            }
        }
    }

    public GridPane getAsGridPane(GridPane gridPane) {
        for (int row = 0; row < rowsCount; row++) {
            for (int col = 0; col < colsCount; col++) {
                TextField textField = new TextField(String.valueOf(data[row][col]));
                textField.setPrefWidth(50);
                gridPane.add(textField, col, row);
            }
        }
        return gridPane;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(":\n");
        for (double[] row : data) {
            for (double value : row) {
                sb.append(value).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
