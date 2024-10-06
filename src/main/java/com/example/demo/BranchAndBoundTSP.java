package com.example.demo;





import java.util.Arrays;

public class BranchAndBoundTSP {

    private int[][] costMatrix;
    private int numCities;
    private int[] bestPath;
    private int bestCost = Integer.MAX_VALUE;

    public BranchAndBoundTSP(int[][] costMatrix) {
        this.costMatrix = costMatrix;
        this.numCities = costMatrix.length;
        this.bestPath = new int[numCities + 1];
    }

    public int[] solve() {
        int[] currentPath = new int[numCities + 1];
        boolean[] visited = new boolean[numCities];

        currentPath[0] = 0;  // Start from city 0
        visited[0] = true;
        solveRecursively(0, currentPath, visited, 1, 0);
        return bestPath;
    }

    private void solveRecursively(int currentCity, int[] currentPath, boolean[] visited, int count, int currentCost) {
        if (count == numCities && costMatrix[currentCity][0] > 0) {
            int totalCost = currentCost + costMatrix[currentCity][0];
            if (totalCost < bestCost) {
                bestCost = totalCost;
                System.arraycopy(currentPath, 0, bestPath, 0, numCities);
                bestPath[numCities] = 0;  // Return to the starting city
            }
            return;
        }

        for (int nextCity = 0; nextCity < numCities; nextCity++) {
            if (!visited[nextCity] && costMatrix[currentCity][nextCity] > 0) {
                visited[nextCity] = true;
                currentPath[count] = nextCity;

                solveRecursively(nextCity, currentPath, visited, count + 1, currentCost + costMatrix[currentCity][nextCity]);

                visited[nextCity] = false;
            }
        }
    }

    public int getCost() {
        return bestCost;
    }
}
