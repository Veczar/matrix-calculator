package pk.wieik.matrix_calculator;

public class Calculator {
    private Calculator() {}

    public static Matrix addMatrices(Matrix matrixA, Matrix matrixB) {
        int rowsCnt = matrixA.getRowsCount();
        int colsCnt = matrixA.getRowsCount();
        Matrix resultMat = new Matrix("result", rowsCnt, colsCnt);

        for (int i = 0; i < rowsCnt; i++) {
            for (int j = 0; j < colsCnt; j++) {
                resultMat.get()[i][j] = matrixA.get()[i][j] + matrixB.get()[i][j];
            }
        }
        
        return resultMat;
    }
}
