package linearalgebra;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;  
import static org.junit.Assert.assertFalse;  
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit testing for our Matrix class.
 */
public class MatrixTest {

    private double delta = 0.00001; // to compare floating-point numbers

    @Test
    public void testConstructor() {
        double[][] entries = {
            {1, 2},
            {2, 1}
        };
        Matrix m = new Matrix(entries);
        assertArrayEquals(entries, m.getEntries());
    }
    
    @Test
    public void testCopyConstructor() {
        double[][] entries = {
            {1, 2},
            {2, 1}
        };
        Matrix m = new Matrix(entries);
        // Make a copy of m using copy constructor
        Matrix mcopy = new Matrix(m);
        // Are m and mcopy equal as they should be?65r
        assertArrayEquals(m.getEntries(), mcopy.getEntries());
    }

    @Test
    public void testGetEntries() {
        double[][] entries = {
            {1, 0},
            {0, 1}
        };
        Matrix m = new Matrix(entries);
        // getEntries() should return correct output
        assertArrayEquals(entries, m.getEntries());
    }

    @Test
    public void testMinorMatrix() {
        double[][] entries = {
            {1, 0, 2},
            {0, 1, 0},
            {0, 0, 2}
        };
        Matrix m = new Matrix(entries);
        // Drop the 0th row and 0th column
        Matrix mnew = m.minorMatrix(0,0);
        // Call to minorMatrix() should return correct smaller matrix
        assertArrayEquals(new double [][] {{1,0}, {0,2}}, mnew.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinorMatrixInvalidRow() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);

        Matrix result = Matrix.minorMatrix(matrix, 3, 1);  // This line should throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinorMatrixInvalidColumn() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);

        Matrix result = Matrix.minorMatrix(matrix, 1, 3);  // This line should throw an exception
    }

    @Test
    public void testFromColumnVectors() {
        Vector c1 = new Vector(1,2,3);
        Vector c2 = new Vector(4,5,6);
        Vector c3 = new Vector(7,8,9);
        Matrix m = Matrix.fromColumnVectors(c1,c2,c3);
        assertArrayEquals(new double[][] {{1,4,7}, {2,5,8}, {3,6,9}}, m.getEntries());
    }

    @Test
    public void testFromRowVectors() {
        Vector r1 = new Vector(1,2,3);
        Vector r2 = new Vector(4,5,6);
        Vector r3 = new Vector(7,8,9);
        Matrix m = Matrix.fromRowVectors(r1,r2,r3);
        assertArrayEquals(new double[][] {{1,2,3}, {4,5,6}, {7,8,9}}, m.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRowVectorsMismatchedLength() {
        Vector vector1 = new Vector(1, 2, 3);
        Vector vector2 = new Vector(4, 5, 6);
        Vector vector3 = new Vector(7, 8, 9);
        Vector vector4 = new Vector(10, 11, 12, 13);  // This vector has a different length
        Matrix result = Matrix.fromRowVectors(vector1, vector2, vector3, vector4);  // This line should throw an exception
    }

    @Test
    public void testDropColumn() {
        double[][] entries = {
            {1, 0},
            {0, 1}
        };
        Matrix m = new Matrix(entries);
        Matrix mnew = m.dropColumn(1);
        // Column should be correctly dropped
        assertArrayEquals(new double[][] {{1}, {0}}, mnew.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDropColumnOutOfRange() {
        double[][] entries = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        int col = 5;  // This column index is out of range
        Matrix result = Matrix.dropColumn(matrix, col);  // This line should throw an exception
    }

    @Test
    public void testDropRow() {
        double[][] entries = {
            {1, 0},
            {0, 1}
        };
        Matrix m = new Matrix(entries);
        Matrix mnew = m.dropRow(1);
        // Row should be correctly dropped
        assertArrayEquals(new double[][] {{1,0}}, mnew.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDropRowOutOfRange() {
        double[][] entries = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        int row = 3;  // This row index is out of range
        Matrix result = Matrix.dropRow(matrix, row);  // This line should throw an exception
    }

    @Test
    public void testGetColumn() {
        double[][] entries = {
            {1, 0},
            {0, 1}
        };
        Matrix m = new Matrix(entries);
        Vector colm = m.getColumn(1);
        assertArrayEquals(new double[] {0,1}, colm.getEntries(), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetColumnOutOfRange() {
        double[][] entries = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        int invalidColumn = 5;  // Choose a column index that is out of range
        Vector result = Matrix.getColumn(matrix, invalidColumn);  // This line should throw an exception
    }

    @Test
    public void testGetRow() {
        double[][] entries = {
            {1, 0},
            {0, 1}
        };
        Matrix m = new Matrix(entries);
        Vector row = m.getRow(1);
        assertArrayEquals(new double[] {0,1}, row.getEntries(), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRowInvalidRow() {
        double[][] entries = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        int invalidRow = 5;  // Choose an invalid row index that exceeds the matrix dimensions
        Vector result = Matrix.getRow(matrix, invalidRow);  // This line should throw an exception
    }

    @Test
    public void testGetEntry() {
        double[][] entries = {
            {1, 2},
            {2, 1}
        };
        Matrix m = new Matrix(entries);
        assertEquals(1, m.getEntry(1, 1), delta); 
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEntryInvalidRow() {
        double[][] entries = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        double result = Matrix.getEntry(matrix, 3, 1);  //  This row index is invalid, should throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEntryInvalidCol() {
        double[][] entries = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        double result = Matrix.getEntry(matrix, 1, 3);  // This column index is invalid, throw an exception
    }

    @Test
    public void testGetNumColumns() {
        double[][] entries = {
            {1, 2},
            {2, 1}
        };
        Matrix m = new Matrix(entries);
        assertEquals(2, m.getNumColumns());
    }

    @Test
    public void testGetNumRows() {
        double[][] entries = {
            {1, 2},
            {2, 1}
        };
        Matrix m = new Matrix(entries);
        assertEquals(2, m.getNumRows());
    }

    @Test
    public void testIdentityMatrix() {
        Matrix i = Matrix.identityMatrix(2);
        assertArrayEquals(new double[][] {{1,0}, {0,1}}, i.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIdentityMatrixInvalidSize() {
        int invalidSize = 0;  // Provide an invalid size (less than 1)
        Matrix result = Matrix.identityMatrix(invalidSize);  // This line should throw an exception
    }

    @Test
    public void testSetColumn_Vector() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        Vector vector = new Vector(new double[]{10, 11, 12});
        Matrix result = matrix.setColumn(1, vector);
        double[][] expected = {
                {1, 10, 3},
                {4, 11, 6},
                {7, 12, 9}
        };
        assertArrayEquals(expected, result.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColumn_Vector_DiffColmLen() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        Vector colm = new Vector(new double[]{10, 11});
        matrix.setColumn(1, colm); // colm size does not match, throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColumn_Vector_IllegalInput() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        Vector colm = new Vector(new double[]{10, 11, 12});
        matrix.setColumn(5, colm); // there is no 5th column to set, throw exception
    }

    @Test
    public void testSetColumn_Array() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        double[] colm = {10, 11, 12};
        Matrix result = matrix.setColumn(1, colm);
        double[][] expected = {
                {1, 10, 3},
                {4, 11, 6},
                {7, 12, 9}
        };
        assertArrayEquals(expected, result.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColumn_Array_DiffColmLen() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        double[] colm = {10, 11};
        matrix.setColumn(1, colm); // colm size does not match, throw exception
    }

    // Test cases for setRow method

    @Test
    public void testSetRow_Vector() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        Vector row = new Vector(new double[]{10, 11, 12});
        Matrix result = matrix.setRow(1, row);
        double[][] expected = {
                {1, 2, 3},
                {10, 11, 12},
                {7, 8, 9}
        };
        assertArrayEquals(expected, result.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRow_Vector_DiffRowLen() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        Vector row = new Vector(new double[]{10, 11});

        matrix.setRow(1, row);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRow_Vector_IllegalInput() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        Vector row = new Vector(new double[]{10, 11, 12});
        matrix.setRow(5, row); // there is no 5th row to set, throw exception
    }

    @Test
    public void testSetRow_Array() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        double[] row = {10, 11, 12};
        Matrix result = matrix.setRow(1, row);

        double[][] expected = {
                {1, 2, 3},
                {10, 11, 12},
                {7, 8, 9}
        };
        assertArrayEquals(expected, result.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRow_Array_DiffRowLen() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        double[] row = {10, 11};
        matrix.setRow(1, row);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRow_Array_IllegalInput() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        double[] row = {10, 11, 12};
        matrix.setRow(5, row); // there is no 5th row to set, throw exception
    }

    // Test case for setEntry method

    @Test
    public void testSetEntry() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        Matrix result = Matrix.setEntry(matrix, 99, 1, 1);

        double[][] expected = {
                {1, 2, 3},
                {4, 99, 6},
                {7, 8, 9}
        };
        assertArrayEquals(expected, result.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEntry_InvalidRow() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);

        Matrix.setEntry(matrix, 99, 3, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEntry_InvalidColumn() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);

        Matrix.setEntry(matrix, 99, 1, 3);
    }

    // Test case for toColumnVectors method

    @Test
    public void testToColumnVectors() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        Vector[] result = matrix.toColumnVectors();

        Vector[] expected = {
                new Vector(new double[]{1, 4, 7}),
                new Vector(new double[]{2, 5, 8}),
                new Vector(new double[]{3, 6, 9})
        };

        for(int i=0; i<result.length; i++) {
            assertArrayEquals(expected[i].getEntries(), result[i].getEntries(), delta);
        }
    }

    // Test case for toRowVectors method

    @Test
    public void testToRowVectors() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        Vector[] result = matrix.toRowVectors();

        Vector[] expected = {
                new Vector(new double[]{1, 2, 3}),
                new Vector(new double[]{4, 5, 6}),
                new Vector(new double[]{7, 8, 9})
        };

        for(int i=0; i<result.length; i++) {
            assertArrayEquals(expected[i].getEntries(), result[i].getEntries(), delta);
        }
    }

    // Test case for toString method

    @Test
    public void testToString() {
        double[][] entries = {
                {1.2, 3.4, 8.9},
                {9.7, 7.5, 3.1}
        };
        Matrix matrix = new Matrix(entries);
        String result = matrix.toString();
        String expected = "[[1.2, 3.4, 8.9],\n [9.7, 7.5, 3.1]]";
        assertEquals(expected, result);
    }

    // Test cases for determinant method

    @Test
    public void testDeterminant_Case1() {
        double[][] entries1 = {
            {5}
        };
        Matrix matrix1 = new Matrix(entries1);
        assertEquals(5.0, matrix1.determinant(), delta);
    }

    @Test
    public void testDeterminant_Case2() {
        double[][] entries2 = {
            {2, 3},
            {4, 5}
        };
        Matrix matrix2 = new Matrix(entries2);
        assertEquals(-2.0, matrix2.determinant(), delta);
    }

    @Test
    public void testDeterminant_Case3() {
        double[][] entries3 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Matrix matrix3 = new Matrix(entries3);
        assertEquals(0.0, matrix3.determinant(), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeterminant_Exception() {
        double[][] entries = {
            {1, 2, 3, 4},
            {4, 5, 6, 5},
            {7, 8, 9, 7}
        };
        Matrix matrix = new Matrix(entries);
        matrix.determinant(); // non-square matrix, throw exception
    }

    @Test
    public void testIsDiagonal_Case1() {
        double[][] entries1 = {
                {1, 0, 0},
                {0, 2, 0},
                {0, 0, 3}
        };
        Matrix matrix1 = new Matrix(entries1);
        assertTrue(matrix1.isDiagonal());
    }

    @Test
    public void testIsDiagonal_Case2() {
        double[][] entries2 = {
                {1, 0, 0},
                {0, 2, 1},
                {0, 0, 3}
        };
        Matrix matrix2 = new Matrix(entries2);
        assertFalse(matrix2.isDiagonal());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsDiagonal_Case3() {
        double[][] entries3 = {
                {1, 0},
                {0, 1},
                {0, 0}
        };
        Matrix matrix3 = new Matrix(entries3);
        matrix3.isDiagonal();
    }
    
    @Test
    public void testIsLowerTriangular_Case1() {
        double[][] entries = {
                {1, 0, 0},
                {4, 5, 0},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        assertFalse(matrix.isLowerTriangular());
    }

    @Test
    public void testIsLowerTriangular_Case2() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        assertFalse(matrix.isLowerTriangular());
    }

    @Test
    public void testIsLowerTriangular_Case3() {
        double[][] entries = {
                {1, 2, 3},
                {0, 5, 6},
                {0, 0, 9}
        };
        Matrix matrix = new Matrix(entries);
        assertTrue(matrix.isLowerTriangular());
    }

    @Test
    public void testIsPermutationMatrix_Case1() {
        double[][] entries1 = {
                {0, 1, 0},
                {1, 0, 0},
                {0, 0, 1}
        };
        Matrix matrix1 = new Matrix(entries1);
        assertTrue(matrix1.isPermutationMatrix());
    }

    @Test
    public void testIsPermutationMatrix_Case2() {
        double[][] entries2 = {
                {1, 1, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        Matrix matrix2 = new Matrix(entries2);
        assertFalse(matrix2.isPermutationMatrix());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsPermutationMatrix_Case3() {
        double[][] entries3 = {
                {1, 0},
                {0, 1},
                {0, 0}
        };
        Matrix matrix3 = new Matrix(entries3);
        matrix3.isPermutationMatrix();
    }

    @Test
    public void testIsSparse_Case1() {
        double[][] entries1 = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        Matrix matrix1 = new Matrix(entries1);
        assertTrue(matrix1.isSparse());
    }

    @Test
    public void testIsSparse_Case2() {
        double[][] entries2 = {
                {0, 0},
                {0, 5}
        };
        Matrix matrix2 = new Matrix(entries2);
        assertTrue(matrix2.isSparse());
    }

    @Test
    public void testIsSparse_Case3() {
        double[][] entries3 = {
                {1, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 4}
        };
        Matrix matrix3 = new Matrix(entries3);
        assertTrue(matrix3.isSparse());
    }

    @Test
    public void testIsSparse_Case4() {
        double[][] entries4 = {
                {0.1, 0.2, 0.3},
                {0.4, 0.5, 0.6},
                {0.7, 0.8, 0.9}
        };
        Matrix matrix4 = new Matrix(entries4);
        assertFalse(matrix4.isSparse(0.5));
    }

    @Test
    public void testIsSparse_Case5() {
        double[][] entries5 = {
                {0.1, 0.2},
                {0.3, 0.4}
        };
        Matrix matrix5 = new Matrix(entries5);
        assertFalse(matrix5.isSparse(0.2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsSparse_Case6() {
        double[][] entries6 = {
                {0.1, 0.2},
                {0.3, 0.4}
        };
        Matrix matrix6 = new Matrix(entries6);
        matrix6.isSparse(1.5);
    }

    @Test
    public void testIsSquare_Case1() {
        double[][] entries1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix1 = new Matrix(entries1);
        assertTrue(matrix1.isSquare());
    }

    @Test
    public void testIsSquare_Case2() {
        double[][] entries2 = {
                {1, 2, 3},
                {4, 5, 6}
        };
        Matrix matrix2 = new Matrix(entries2);
        assertFalse(matrix2.isSquare());
    }

    @Test
    public void testIsUpperTriangular_Case1() {
        double[][] entries = {
                {1, 2, 3},
                {0, 4, 5},
                {0, 0, 6}
        };
        Matrix matrix = new Matrix(entries);
        assertFalse(matrix.isUpperTriangular());
    }

    @Test
    public void testIsUpperTriangular_Case2() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        assertFalse(matrix.isUpperTriangular());
    }

    @Test
    public void testIsUpperTriangular_Case3() {
        double[][] entries = {
                {1, 0, 0},
                {4, 5, 0},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        assertTrue(matrix.isUpperTriangular());
    }

    @Test
    public void testShape_Case1() {
        double[][] entries1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix1 = new Matrix(entries1);
        int[] expectedShape1 = {3, 3};
        assertArrayEquals(expectedShape1, Matrix.shape(matrix1));
    }

    @Test
    public void testShape_Case2() {
        double[][] entries2 = {{1, 2}};
        Matrix matrix2 = new Matrix(entries2);
        int[] expectedShape2 = {1, 2};
        assertArrayEquals(expectedShape2, Matrix.shape(matrix2));
    }

    @Test
    public void testShape_Case3() {
        double[][] entries3 = {
                {1, 2, 3},
                {4, 5, 6}
        };
        Matrix matrix3 = new Matrix(entries3);
        int[] expectedShape3 = {2, 3};
        assertArrayEquals(expectedShape3, Matrix.shape(matrix3));
    }

    @Test
    public void testTrace_Case1() {
        double[][] entries1 = {
                {7}
        };
        Matrix matrix1 = new Matrix(entries1);
        assertEquals(7.0, matrix1.trace(), delta);
    }

    @Test
    public void testTrace_Case2() {
        double[][] entries2 = {
                {1, 0, 0},
                {0, 5, 0},
                {0, 0, 9}
        };
        Matrix matrix2 = new Matrix(entries2);
        assertEquals(15.0, matrix2.trace(), delta);
    }

    @Test
    public void testTrace_Case3() {
        double[][] entries3 = {
                {-1, 0, 0},
                {0, -5, 0},
                {0, 0, -9}
        };
        Matrix matrix3 = new Matrix(entries3);
        assertEquals(-15.0, matrix3.trace(), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTrace_Case4() {
        double[][] entries4 = {
                {1, 2, 3},
                {4, 5, 6}
        };
        Matrix matrix4 = new Matrix(entries4);
        matrix4.trace();  // matrix is non-square, throw exception
    }

    @Test
    public void testTranspose_Case1() {
        double[][] entries1 = {
            {5}
        };
        Matrix matrix1 = new Matrix(entries1);
        Matrix transposed1 = matrix1.transpose();
        double[][] expected1 = {
            {5}
        };
        assertArrayEquals(expected1, transposed1.getEntries());
    }

    @Test
    public void testTranspose_Case2() {
        double[][] entries2 = {
            {2, 3, 4},
            {5, 6, 7}
        };
        Matrix matrix2 = new Matrix(entries2);
        Matrix transposed2 = matrix2.transpose();
        double[][] expected2 = {
            {2, 5},
            {3, 6},
            {4, 7}
        };
        assertArrayEquals(expected2, transposed2.getEntries());
    }

    @Test
    public void testTranspose_Case3() {
        double[][] entries3 = {
            {1, 2},
            {3, 4},
            {5, 6}
        };
        Matrix matrix3 = new Matrix(entries3);
        Matrix transposed3 = matrix3.transpose();
        double[][] expected3 = {
            {1, 3, 5},
            {2, 4, 6}
        };
        assertArrayEquals(expected3, transposed3.getEntries());
    }

    @Test
    public void testTranspose_Case4() {
        double[][] entries4 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        Matrix matrix4 = new Matrix(entries4);
        Matrix transposed4 = matrix4.transpose();
        assertArrayEquals(entries4, transposed4.getEntries()); // transpose = matrix itself
    }

    @Test
    public void testSwapColumns_Case1() {
        double[][] entries1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix1 = new Matrix(entries1);
        Matrix result1 = matrix1.swapColumns(0, 2);
        double[][] expected1 = {
                {3, 2, 1},
                {6, 5, 4},
                {9, 8, 7}
        };
        assertArrayEquals(expected1, result1.getEntries());
    }

    @Test
    public void testSwapColumns_Case2() {
        double[][] entries2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        Matrix matrix2 = new Matrix(entries2);
        Matrix result2 = matrix2.swapColumns(0, 2);
        double[][] expected2 = {
                {3, 2, 1},
                {6, 5, 4},
                {9, 8, 7},
                {12, 11, 10}
        };
        assertArrayEquals(expected2, result2.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSwapColumns_Case3() {
        double[][] entries3 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        Matrix matrix3 = new Matrix(entries3);
        matrix3.swapColumns(1, 3);  // no 3th column, throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSwapColumns_Case4() {
        double[][] entries4 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        Matrix matrix4 = new Matrix(entries4);
        matrix4.swapColumns(3, 1);  // no 3th column, throw exception
    }

    @Test
    public void testSwapRows_Case1() {
        double[][] entries1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix1 = new Matrix(entries1);
        Matrix result1 = matrix1.swapRows(0, 2);
        double[][] expected1 = {
                {7, 8, 9},
                {4, 5, 6},
                {1, 2, 3}
        };
        assertArrayEquals(expected1, result1.getEntries());
    }

    @Test
    public void testSwapRows_Case2() {
        double[][] entries2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        Matrix matrix2 = new Matrix(entries2);
        Matrix result2 = matrix2.swapRows(0, 2);
        double[][] expected2 = {
                {7, 8, 9},
                {4, 5, 6},
                {1, 2, 3},
                {10, 11, 12}
        };
        assertArrayEquals(expected2, result2.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSwapRows_Case3() {
        double[][] entries3 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        Matrix matrix3 = new Matrix(entries3);
        matrix3.swapRows(1, 4);  // no 4th row, throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSwapRows_Case4() {
        double[][] entries4 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        Matrix matrix4 = new Matrix(entries4);
        matrix4.swapRows(1, 4);  // no 4th row, throw exception
    }

    // Test cases for add method

    @Test
    public void testAdd() {
        double[][] entries1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        double[][] entries2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        Matrix matrix1 = new Matrix(entries1);
        Matrix matrix2 = new Matrix(entries2);
        Matrix result = matrix1.add(matrix2);
        double[][] expected = {
                {10, 10, 10},
                {10, 10, 10},
                {10, 10, 10}
        };
        assertArrayEquals(expected, result.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdd_Exception() {
        double[][] entries1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        double[][] entries2 = {
                {9, 8, 7, 8},
                {6, 5, 4, 5},
                {3, 2, 1, 2}
        };
        Matrix matrix1 = new Matrix(entries1);
        Matrix matrix2 = new Matrix(entries2);
        Matrix result = matrix1.add(matrix2);  // matrix size don't match, throw exception
    }

    // Test cases for subtract method

    @Test
    public void testSubtract() {
        double[][] entries1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        double[][] entries2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        Matrix matrix1 = new Matrix(entries1);
        Matrix matrix2 = new Matrix(entries2);
        Matrix result = matrix1.subtract(matrix2);
        double[][] expected = {
                {-8, -6, -4},
                {-2, 0, 2},
                {4, 6, 8}
        };
        assertArrayEquals(expected, result.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtract_Exception() {
        double[][] entries1 = {
                {1, 2, 3},
                {4, 5, 6}
        };
        double[][] entries2 = {
                {7, 8},
                {9, 10}
        };
        Matrix matrix1 = new Matrix(entries1);
        Matrix matrix2 = new Matrix(entries2);
        matrix1.subtract(matrix2); // matrix size don't match, throw exception
    }

    // Test cases for multiply methods

    @Test
    public void testMultiplyByScalar() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix = new Matrix(entries);
        Matrix result = matrix.multiply(2);
        double[][] expected = {
                {2, 4, 6},
                {8, 10, 12},
                {14, 16, 18}
        };
        assertArrayEquals(expected, result.getEntries());
    }

    @Test
    public void testMultiplyByVector() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
        };
        Matrix matrix = new Matrix(entries);
        Vector vector = new Vector(new double[]{1, 1, 1}); 
        Vector result = matrix.multiply(vector);
        assertArrayEquals(new double[] {6.0, 15.0}, result.getEntries(), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplyByVector_Exception() {
        double[][] entries = {
                {1, 2, 3},
                {4, 5, 6},
        };
        Matrix matrix = new Matrix(entries);
        Vector vector = new Vector(new double[]{1, 1});  // Incompatible shapes
        Vector result = matrix.multiply(vector);  // This line should throw an exception
    }

    @Test
    public void testMultiplyByMatrix() {
        double[][] entries1 = {
                {1, 2},
                {3, 4},
        };
        double[][] entries2 = {
                {5, 6},
                {7, 8},
        };
        Matrix matrix1 = new Matrix(entries1);
        Matrix matrix2 = new Matrix(entries2);
        Matrix result = matrix1.multiply(matrix2);
        double[][] expected = {
                {19, 22},
                {43, 50}
        };
        assertArrayEquals(expected, result.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplyByMatrix_Exception() {
        double[][] entries1 = {
                {1, 2},
                {3, 4}
        };
        double[][] entries2 = {
                {5, 6},
                {7, 8},
                {1, 2}
        };
        Matrix matrix1 = new Matrix(entries1);
        Matrix matrix2 = new Matrix(entries2);
        Matrix result = matrix1.multiply(matrix2); // Incompatible shapes, throw exception
    }

    @Test
    public void testAddVectorToColumn_Case1() {
        double[][] entries1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix1 = new Matrix(entries1);
        Vector vector1 = new Vector(new double[]{10, 11, 12});
        Matrix result1 = matrix1.addVectorToColumn(vector1, 1);
        double[][] expected1 = {
                {1, 12, 3},
                {4, 16, 6},
                {7, 20, 9}
        };
        assertArrayEquals(expected1, result1.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddVectorToColumn_Case2() {
        double[][] entries2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix2 = new Matrix(entries2);
        Vector vector2 = new Vector(new double[]{10, 11});
        matrix2.addVectorToColumn(vector2, 1); // vector length does not match column length, throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddVectorToColumn_Case3() {
        double[][] entries3 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix3 = new Matrix(entries3);
        Vector vector3 = new Vector(new double[]{10, 11, 12});
        matrix3.addVectorToColumn(vector3, 3); // no 3th column, throw exception
    }

    @Test
    public void testAddVectorToRow_Case1() {
        double[][] entries1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix1 = new Matrix(entries1);
        Vector vector1 = new Vector(new double[] {10,11,12});
        Matrix result1 = matrix1.addVectorToRow(vector1, 1);
        double[][] expected1 = {
                {1, 2, 3},
                {14, 16, 18},
                {7, 8, 9}
        };
        assertArrayEquals(expected1, result1.getEntries());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddVectorToRow_Case2() {
        double[][] entries2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix2 = new Matrix(entries2);
        Vector vector2 = new Vector(new double[] {10,11});
        matrix2.addVectorToRow(vector2, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddVectorToRow_Case3() {
        double[][] entries3 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix matrix3 = new Matrix(entries3);
        Vector vector3 = new Vector(new double[] {10,11,12});
        matrix3.addVectorToRow(vector3, 3); // no 3th row, throw exception
    }
}
