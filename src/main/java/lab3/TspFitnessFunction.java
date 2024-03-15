package lab3;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TspFitnessFunction implements FitnessEvaluator<TspSolution> {
    private double[][] distances;

    public TspFitnessFunction(String problem) {
        loadProblemData(problem);
    }

    private void loadProblemData(String problem) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(problem + ".tsp"));
            List<double[]> coordinates = new ArrayList<double[]>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.matches("\\d+\\s+\\d+\\s+\\d+")) {
                    String[] parts = line.split("\\s+");
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    coordinates.add(new double[]{x, y});
                }
            }

            int numCities = coordinates.size();
            distances = new double[numCities][numCities];

            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    double[] city1 = coordinates.get(i);
                    double[] city2 = coordinates.get(j);

                    distances[i][j] = calculateDistance(city1[0], city1[1], city2[0], city2[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public double getFitness(TspSolution solution, List<? extends TspSolution> list) {
        List<Integer> route = solution.getRoute();
        double fitness = 0.0;

        for (int i = 0; i < route.size() - 1; i++) {
            int city1 = route.get(i);
            int city2 = route.get(i + 1);
            fitness += distances[city1 - 1][city2 - 1];
        }

        fitness += distances[route.get(route.size() - 1) - 1][route.get(0) - 1];

        return fitness;
    }

    public boolean isNatural() {
        return false;
    }
}
