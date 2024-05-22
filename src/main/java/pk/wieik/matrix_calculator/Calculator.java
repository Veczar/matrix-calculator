package pk.wieik.matrix_calculator;

public class Calculator {
    private Calculator() {}

    public static Matrix addMatrices(Matrix matrixA, Matrix matrixB) {
        int rowsCnt = matrixA.getRowsCount();
        int colsCnt = matrixA.getColsCount();
        Matrix resultMat = new Matrix("result", rowsCnt, colsCnt);
        
        for (int i = 0; i < rowsCnt; i++) {
            for (int j = 0; j < colsCnt; j++) {
                double sum = matrixA.get()[i][j] + matrixB.get()[i][j];
                resultMat.get()[i][j] = sum;
                
                // Row sum
                resultMat.get()[i][colsCnt] += sum;
                matrixA.get()[i][colsCnt] += matrixA.get()[i][j];
                matrixB.get()[i][colsCnt] += matrixB.get()[i][j];
                
                // Column sum
                resultMat.get()[rowsCnt][j] += sum;
                matrixA.get()[rowsCnt][j] += matrixA.get()[i][j];
                matrixB.get()[rowsCnt][j] += matrixB.get()[i][j];
            }
        }
        
        // Calculate the grand total sum for the bottom-right corner
        for (int i = 0; i < rowsCnt; i++) {
            resultMat.get()[rowsCnt][colsCnt] += resultMat.get()[i][colsCnt];
            matrixA.get()[rowsCnt][colsCnt] += matrixA.get()[i][colsCnt];
            matrixB.get()[rowsCnt][colsCnt] += matrixB.get()[i][colsCnt];
        }
        
//        checkCorrectness(matrixA, matrixB, resultMat);
        return resultMat;
    }
    
    public static Matrix subMatrices(Matrix matrixA, Matrix matrixB) {
        int rowsCnt = matrixA.getRowsCount();
        int colsCnt = matrixA.getColsCount();
        Matrix resultMat = new Matrix("result", rowsCnt, colsCnt);
        
        for (int i = 0; i < rowsCnt; i++) {
            for (int j = 0; j < colsCnt; j++) {
                double sum = matrixA.get()[i][j] - matrixB.get()[i][j];
                resultMat.get()[i][j] = sum;
                
                // Row sum
                resultMat.get()[i][colsCnt] -= sum;
                matrixA.get()[i][colsCnt] -= matrixA.get()[i][j];
                matrixB.get()[i][colsCnt] -= matrixB.get()[i][j];
                
                // Column sum
                resultMat.get()[rowsCnt][j] -= sum;
                resultMat.get()[rowsCnt][j] -= matrixA.get()[i][j];
                resultMat.get()[rowsCnt][j] -= matrixA.get()[i][j];
            }
        }
        
//        checkCorrectness(matrixA, matrixB, resultMat);
        return resultMat;
    }
    
    public static void checkCorrectness(Matrix matrixA, Matrix matrixB, Matrix resultMat) {
        int rowsCnt = matrixA.getRowsCount();
        int colsCnt = matrixA.getColsCount();
        
        // Iterate through each row to check row sums
        for (int i = 0; i < rowsCnt; i++) {
            double rowSumA = matrixA.get()[i][colsCnt]; // Last column of matrixA's row
            double rowSumB = matrixB.get()[i][colsCnt]; // Last column of matrixB's row
            double expectedRowSum = rowSumA + rowSumB;
            double resultRowSum = resultMat.get()[i][colsCnt];
            assert expectedRowSum == resultRowSum : "Row sum mismatch at row " + i;
        }
        
        // Iterate through each column to check column sums
        for (int j = 0; j < colsCnt; j++) {
            double colSumA = matrixA.get()[rowsCnt][j]; // Last row of matrixA's column
            double colSumB = matrixB.get()[rowsCnt][j]; // Last row of matrixB's column
            double expectedColSum = colSumA + colSumB;
            double resultColSum = resultMat.get()[rowsCnt][j];
            assert expectedColSum == resultColSum : "Column sum mismatch at column " + j;
        }
        
        // check grand total
        double resultTotal = resultMat.get()[rowsCnt][colsCnt];
        double expectedTotal = matrixA.get()[rowsCnt][colsCnt] + matrixB.get()[rowsCnt][colsCnt];
        assert expectedTotal == resultTotal : "Grand total checksum mismatch";
        
        System.out.println("All checksums are correct!");
    }
    
    
    // ------------------------------- RAW ADD/SUB WITHOUT CHECKSUMS  ---------------------------------
    
    public static Matrix addNoChecksum(Matrix matrixA, Matrix matrixB) {
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
    
    public static Matrix subNoChecksum(Matrix matrixA, Matrix matrixB) {
        int rowsCnt = matrixA.getRowsCount();
        int colsCnt = matrixA.getRowsCount();
        Matrix resultMat = new Matrix("result", rowsCnt, colsCnt);
        
        for (int i = 0; i < rowsCnt; i++) {
            for (int j = 0; j < colsCnt; j++) {
                resultMat.get()[i][j] = matrixA.get()[i][j] - matrixB.get()[i][j];
            }
        }
        return resultMat;
    }
}