package pk.wieik.matrix_calculator;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;

@Getter
public class Matrix {

    private String name;
    private int rowsCount;
    private int colsCount;
    private double[][] data;

    public double[][] get() {
        return this.data;
    }

    public Matrix(String name, int rowsCount, int colsCount) {
        this.name = name;
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        this.data = new double[rowsCount][colsCount];
    }

    public Matrix(GridPane gridPane, String name) {
        this.name = name;
        this.rowsCount = gridPane.getRowCount();
        this.colsCount = gridPane.getColumnCount();
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
//        GridPane gridPane = new GridPane();
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
