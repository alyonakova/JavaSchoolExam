package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    private ArrayList<Integer> triangleNumbersCollection = new ArrayList<>();
    private ArrayList<Integer> columns = new ArrayList<>();

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        setColumnsWithNumbers();
        if (!isTriangleNumber(inputNumbers.size()) || inputNumbers.contains(null)) throw new CannotBuildPyramidException();
        int indexOfNumber = triangleNumbersCollection.indexOf(inputNumbers.size());
        int rowsCount = indexOfNumber + 2;
        int colsCount = columns.get(indexOfNumber);
        Collections.sort(inputNumbers);
        int[][] pyramid = new int[rowsCount][colsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                pyramid[i][j] = 0;
            }
        }

        int index = 0;
        for (int i = 0; i < rowsCount; i++) {
            int count = 0;
            int cell = indexOfNumber + 1;
            for (int j = 0; j < colsCount; j++) {
                pyramid[i][cell] = inputNumbers.get(index);
                index++;
                cell += 2;
                count++;
                if (i - count < 0) {
                    indexOfNumber--;
                    break;
                }
            }
        }
        return pyramid;
    }

    private ArrayList<Integer> getTriangleNumbersCollection() {
        int tmp = 0;
        for (int i = 1; i < 100; i++) {
            tmp = i + tmp;
            if (i == 1) continue;
            triangleNumbersCollection.add(tmp);
        }
        return triangleNumbersCollection;
    }

    private boolean isTriangleNumber(int number) {
        ArrayList<Integer> collection = getTriangleNumbersCollection();
        return (collection.contains(number));
    }

    private void setColumnsWithNumbers() {
        for (int i = 3; i < 100; i+=2) columns.add(i);
    }
}
