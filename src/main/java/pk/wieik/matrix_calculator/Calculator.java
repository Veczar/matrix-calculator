package pk.wieik.matrix_calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private static final String reportPath = "src/main/resources/report.txt";
    private Calculator() {
    }

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
            //rows
            resultMat.get()[rowsCnt][colsCnt] += resultMat.get()[i][colsCnt];
            matrixA.get()[rowsCnt][colsCnt] += matrixA.get()[i][colsCnt];
            matrixB.get()[rowsCnt][colsCnt] += matrixB.get()[i][colsCnt];
        }
        for (int j = 0; j < colsCnt; j++) {
            //cols
            resultMat.get()[rowsCnt][colsCnt] += resultMat.get()[rowsCnt][j];
            matrixA.get()[rowsCnt][colsCnt] += matrixA.get()[rowsCnt][j];
            matrixB.get()[rowsCnt][colsCnt] += matrixB.get()[rowsCnt][j];
        }
        
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
        for (int j = 0; j < colsCnt; j++) {
            resultMat.get()[rowsCnt][colsCnt] += resultMat.get()[rowsCnt][j];
            matrixA.get()[rowsCnt][colsCnt] += matrixA.get()[rowsCnt][j];
            matrixB.get()[rowsCnt][colsCnt] += matrixB.get()[rowsCnt][j];
        }

        return resultMat;
    }

    public static List<Position> checkCorrectnessAddition(Matrix matrixA, Matrix matrixB, Matrix resultMat) {
        List<Position> mismatches = new ArrayList<>();
        List<String> allMismatches = new ArrayList<>();

        int rowsCnt = matrixA.getRowsCount();
        int colsCnt = matrixA.getColsCount();
        
        // Iterate through each row to check row sums
        for (int i = 0; i < rowsCnt; i++) {
            double rowSumA = matrixA.get()[i][colsCnt]; // Last column of matrixA's row
            double rowSumB = matrixB.get()[i][colsCnt]; // Last column of matrixB's row
            double expectedRowSum = rowSumA + rowSumB;
            double resultRowSum = resultMat.get()[i][colsCnt];
            if (expectedRowSum != resultRowSum) {
                String mismatch = "Row sum mismatch at row " + i;
                System.out.println(mismatch);
                allMismatches.add(mismatch);
                mismatches.add(new Position(i, colsCnt));
            }
        }
        
        // Iterate through each column to check column sums
        for (int j = 0; j < colsCnt; j++) {
            double colSumA = matrixA.get()[rowsCnt][j]; // Last row of matrixA's column
            double colSumB = matrixB.get()[rowsCnt][j]; // Last row of matrixB's column
            double expectedColSum = colSumA + colSumB;
            double resultColSum = resultMat.get()[rowsCnt][j];
            if (expectedColSum != resultColSum) {
                String mismatch = "Column sum mismatch at column " + j;
                System.out.println(mismatch);
                allMismatches.add(mismatch);
                mismatches.add(new Position(rowsCnt, j));
            }
        }
        
        // Check grand total
        double resultTotal = resultMat.get()[rowsCnt][colsCnt];
        double expectedTotal = matrixA.get()[rowsCnt][colsCnt] + matrixB.get()[rowsCnt][colsCnt];
        if (expectedTotal != resultTotal) {
            System.out.println("Grand total checksum mismatch");
            mismatches.add(new Position(rowsCnt, colsCnt));
        }

        if (mismatches.isEmpty()) {
            System.out.println("All checksums are correct!");
        }
        FileLoader.generateReport(reportPath, matrixA, matrixB, resultMat, "addition", allMismatches);
        return mismatches;
    }
    
    
    public static List<Position> checkCorrectnessSubtraction(Matrix matrixA, Matrix matrixB, Matrix resultMat) {
        List<String> allMismatches = new ArrayList<>();
        List<Position> mismatches = new ArrayList<>();

        int rowsCnt = matrixA.getRowsCount();
        int colsCnt = matrixA.getColsCount();
        
        // Iterate through each row to check row sums
        for (int i = 0; i < rowsCnt; i++) {
            double rowSumA = matrixA.get()[i][colsCnt]; // Last column of matrixA's row
            double rowSumB = matrixB.get()[i][colsCnt]; // Last column of matrixB's row
            double expectedRowSum = rowSumA - rowSumB;
            double resultRowSum = resultMat.get()[i][colsCnt];
            if (expectedRowSum != resultRowSum) {
                String mismatch = "Row sum mismatch at row " + i;
                System.out.println(mismatch);
                mismatches.add(new Position(i, colsCnt));
                allMismatches.add(mismatch);
            }
        }
        
        // Iterate through each column to check column sums
        for (int j = 0; j < colsCnt; j++) {
            double colSumA = matrixA.get()[rowsCnt][j]; // Last row of matrixA's column
            double colSumB = matrixB.get()[rowsCnt][j]; // Last row of matrixB's column
            double expectedColSum = colSumA - colSumB;
            double resultColSum = resultMat.get()[rowsCnt][j];
            if (expectedColSum != resultColSum) {
                String mismatch = "Column sum mismatch at column " + j;
                System.out.println(mismatch);
                allMismatches.add(mismatch);
                mismatches.add(new Position(rowsCnt, j));
            }
        }
        
        // Check grand total
        double resultTotal = resultMat.get()[rowsCnt][colsCnt];
        double expectedTotal = matrixA.get()[rowsCnt][colsCnt] - matrixB.get()[rowsCnt][colsCnt];
        if (expectedTotal != resultTotal) {
            System.out.println("Grand total checksum mismatch");
            mismatches.add(new Position(rowsCnt, colsCnt));
        }

        if (mismatches.isEmpty()) {
            System.out.println("All checksums are correct!");
        }
        FileLoader.generateReport(reportPath, matrixA, matrixB, resultMat, "subtraction", allMismatches);
        return mismatches;
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
