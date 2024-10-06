package com.example.demo;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TSPController {

    @GetMapping("/")
    public String index() {
        return "index";  // Maps to src/main/resources/templates/index.html
    }

    @PostMapping("/solveTSP")
    public String solveTSP(@RequestParam("cityName") String[] cityNames, 
                           @RequestParam("distance") String[] distances, 
                           Model model) {
        try {
            int numCities = cityNames.length;
            int[][] distanceMatrix = new int[numCities][numCities];

            // Construct the distance matrix from input
            int distanceIndex = 0;
            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    if (i != j) {
                        distanceMatrix[i][j] = Integer.parseInt(distances[distanceIndex++]);
                    } else {
                        distanceMatrix[i][j] = 0; // Distance to itself is 0
                    }
                }
            }

            // Calculate total distance without applying the algorithm
            int totalDistanceWithoutAlgorithm = 0;
            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    if (i != j) {
                        totalDistanceWithoutAlgorithm += distanceMatrix[i][j];
                    }
                }
            }
            totalDistanceWithoutAlgorithm /= 2; // Each distance is counted twice

            // Apply the TSP algorithm
            BranchAndBoundTSP solver = new BranchAndBoundTSP(distanceMatrix);
            int[] bestPath = solver.solve();
            int bestCost = solver.getCost();

            // Convert bestPath array to a readable String
            StringBuilder pathString = new StringBuilder();
            for (int i = 0; i < bestPath.length; i++) {
                pathString.append(cityNames[bestPath[i]]);
                if (i < bestPath.length - 1) {
                    pathString.append(" -> ");
                }
            }

            model.addAttribute("bestPath", pathString.toString());
            model.addAttribute("bestCost", bestCost);
            model.addAttribute("totalDistance", totalDistanceWithoutAlgorithm);
            return "result";  // Maps to src/main/resources/templates/result.html
        } catch (Exception e) {
            model.addAttribute("error", "Invalid input. Please ensure the distance matrix is correct.");
            return "index";  // Back to form with an error message
        }
    }


    private int[][] parseDistanceMatrix(String distanceMatrixStr, int numCities) throws Exception {
        int[][] distanceMatrix = new int[numCities][numCities];
        String[] rows = distanceMatrixStr.split("\n");

        if (rows.length != numCities) {
            throw new Exception("Number of rows doesn't match the number of cities.");
        }

        for (int i = 0; i < numCities; i++) {
            String[] elements = rows[i].split(",");

            if (elements.length != numCities) {
                throw new Exception("Row " + (i + 1) + " doesn't have the correct number of columns.");
            }

            for (int j = 0; j < numCities; j++) {
                distanceMatrix[i][j] = Integer.parseInt(elements[j].trim());
            }
        }
        return distanceMatrix;
    }
}
