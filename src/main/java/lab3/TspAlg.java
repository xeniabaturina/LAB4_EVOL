package lab3;

import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TspAlg {

    public static void main(String[] args) {
        String problem = "XQF131"; // name of problem or path to input file

        int populationSize = 100; // size of population
        int generations = 20000; // number of generations

        Random random = new Random(); // random

        CandidateFactory<TspSolution> factory = new TspFactory(); // generation of solutions

        ArrayList<EvolutionaryOperator<TspSolution>> operators = new ArrayList<EvolutionaryOperator<TspSolution>>();
        operators.add(new TspCrossover()); // Crossover
        operators.add(new TspMutation()); // Mutation
        EvolutionPipeline<TspSolution> pipeline = new EvolutionPipeline<TspSolution>(operators);

        SelectionStrategy<Object> selection = new RouletteWheelSelection(); // Selection operator

        FitnessEvaluator<TspSolution> evaluator = new TspFitnessFunction(problem); // Fitness function

        EvolutionEngine<TspSolution> algorithm = new SteadyStateEvolutionEngine<TspSolution>(
                factory, pipeline, evaluator, selection, populationSize, false, random);

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            private List<Double> fitnessHistory = new ArrayList<Double>();

            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                fitnessHistory.add(bestFit);


                System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                TspSolution best = (TspSolution) populationData.getBestCandidate();
                System.out.println("\tBest solution = " + best.toString());
                if (populationData.getGenerationNumber() == generations - 1) {
                    printBest(fitnessHistory);
                    plotGraph(fitnessHistory);
                }
            }
        });

        TerminationCondition terminate = new GenerationCount(generations);
        algorithm.evolve(populationSize, 1, terminate);
    }

    private static void plotGraph(List<Double> fitnessHistory) {
        XYChart chart = new XYChartBuilder().width(800).height(600).title("Fitness History").xAxisTitle("Generation").yAxisTitle("Fitness").build();
        XYSeries series = chart.addSeries("Best Fitness", null, fitnessHistory);
        series.setMarker(SeriesMarkers.CIRCLE);
        new SwingWrapper(chart).displayChart();
    }

    private static void printBest(List<Double> fitnessHistory) {
        for (int i = fitnessHistory.size() - 1; i > 0; i--) {
            if (!fitnessHistory.get(i).equals(fitnessHistory.get(i - 1))) {
                System.out.println("Best is " + i + "'th generation, " + fitnessHistory.get(i));
                break;
            }
        }
    }
}
