package pk.wieik.matrix_calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MatrixCalculatorTest {
    
    private Matrix matrixA;
    private Matrix matrixB;
    
    @BeforeEach
    public void setUp() {
        // Initialize matrixA and matrixB
        matrixA = new Matrix("A", 2, 2);
        matrixB = new Matrix("B", 2, 2);
        
        // 1 2
        // 3 4
        matrixA.get()[0][0] = 1;
        matrixA.get()[0][1] = 2;
        matrixA.get()[1][0] = 3;
        matrixA.get()[1][1] = 4;
        
        // 5 6
        // 7 8
        matrixB.get()[0][0] = 5;
        matrixB.get()[0][1] = 6;
        matrixB.get()[1][0] = 7;
        matrixB.get()[1][1] = 8;
    }
    
    @Test
    public void testAddMatrices() {
        Matrix expectedResult = new Matrix("result", 2, 2);
        // 6  8  14
        // 10 12 22
        // 16 20 72
        expectedResult.get()[0][0] = 6;
        expectedResult.get()[0][1] = 8;
        expectedResult.get()[1][0] = 10;
        expectedResult.get()[1][1] = 12;
        
        // Row sums
        expectedResult.get()[0][2] = 14; // 6 + 8
        expectedResult.get()[1][2] = 22; // 10 + 12
        
        // Column sums
        expectedResult.get()[2][0] = 16; // 6 + 10
        expectedResult.get()[2][1] = 20; // 8 + 12
        
        // Grand total
        expectedResult.get()[2][2] = 72; // 36 + 36 (row sums)
        
        Matrix result = Calculator.addMatrices(matrixA, matrixB);
        System.out.println(matrixA);
        System.out.println(matrixB);
        System.out.println(result);
        
        assertArrayEquals(expectedResult.get(), result.get(), "Matrices are not equal");
    }
    
    @Test
    public void testSubMatrices() {
        Matrix expectedResult = new Matrix("result", 2, 2);
        expectedResult.get()[0][0] = -4;
        expectedResult.get()[0][1] = -4;
        expectedResult.get()[1][0] = -4;
        expectedResult.get()[1][1] = -4;
        
        // Row sums
        expectedResult.get()[0][2] = -8; // -4 + -4
        expectedResult.get()[1][2] = -8; // -4 + -4
        
        // Column sums
        expectedResult.get()[2][0] = -8; // -4 + -4
        expectedResult.get()[2][1] = -8; // -4 + -4
        
        // Grand total
        expectedResult.get()[2][2] = -32; // -16 + -16 (row sums)
        
        Matrix result = Calculator.subMatrices(matrixA, matrixB);
        System.out.println(matrixA);
        System.out.println(matrixB);
        System.out.println(result);
        
        assertArrayEquals(expectedResult.get(), result.get(), "Matrices are not equal");
    }

    @Test
    public void testCheckCorrectnessAddition(){
        Matrix result = Calculator.addMatrices(matrixA, matrixB);
        boolean checkCorrectnessAdd = Calculator.checkCorrectnessAddition(matrixA, matrixB, result);
        assert checkCorrectnessAdd : "Addition checking does not work properly!";
    }

    @Test
    public void techCheckCorrectnessSubstraction() {
        Matrix result = Calculator.subMatrices(matrixA, matrixB);
        boolean checkCorrectnessSubstraction = Calculator.checkCorrectnessSubtraction(matrixA, matrixB, result);
        assert checkCorrectnessSubstraction : "Substraction checking does not work properly!";
    }
}
