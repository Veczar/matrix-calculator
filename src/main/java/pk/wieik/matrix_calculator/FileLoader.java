package pk.wieik.matrix_calculator;

import java.io.*;
import java.util.List;

public class FileLoader {
    
    public static Matrix[] loadMatricesFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read the dimensions of the matrices from the first line
            String[] dimensions = br.readLine().split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            
            Matrix[] matrices = new Matrix[2]; // Assuming always two matrices in the file
            
            for (int k = 0; k < 2; k++) {
                br.readLine(); // empty separator line
                double[][] data = new double[rows+1][cols+1];
                
                // Read data for the current matrix
                for (int i = 0; i < rows; i++) {
                    String[] values = br.readLine().split(" ");
                    for (int j = 0; j < cols; j++) {
                        data[i][j] = Double.parseDouble(values[j]);
                    }
                }
                
                // Create the matrix object and store it in the array
                matrices[k] = new Matrix("File Matrix " + k, rows, cols, data);
            }
            
            return matrices;
        }
        catch (IOException e) {
            String msg = "error while loading matrices from file: " + filePath;
            throw new RuntimeException(msg, e);
        }
    }
    public static void generateReport(String filePath, Matrix matrixA, Matrix matrixB, Matrix resultMatrix, String operation, List<String> mismatches) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Operation: " + operation + "\n\n");
            writer.write(matrixA.toString() + "\n");

            writer.write(matrixB.toString() + "\n");

            writer.write(resultMatrix.toString() + "\n");


            if (mismatches.isEmpty()) {
                writer.write("All checksums are correct\n");
            } else {
                for (String mismatch : mismatches) {
                    writer.write(mismatch + "\n");
                }
            }

            System.out.println("Report generated successfully at: " + filePath);
        } catch (IOException e) {
            String msg = "error while generating report to file: " + filePath;
            throw new RuntimeException(msg, e);
        }
    }

}
