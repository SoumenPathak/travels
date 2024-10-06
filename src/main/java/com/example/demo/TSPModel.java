package com.example.demo;



public class TSPModel {
    private int[][] distanceMatrix;
    private int numCities;

    // Constructors, Getters, Setters
    public TSPModel(int[][] distanceMatrix, int numCities) {
        this.distanceMatrix = distanceMatrix;
        this.numCities = numCities;
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public int getNumCities() {
        return numCities;
    }

    public void setNumCities(int numCities) {
        this.numCities = numCities;
    }
}
