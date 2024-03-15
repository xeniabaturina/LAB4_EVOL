package lab3;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TspMutation implements EvolutionaryOperator<TspSolution> {
    public List<TspSolution> apply(List<TspSolution> population, Random random) {
        for (TspSolution solution : population) {
            List<Integer> route = solution.getRoute();
            int size = route.size();

            int start = random.nextInt(size);
            int end = random.nextInt(size - start) + start;

            if (start != end && (end - start != size - 1)) {
                List<Integer> segment = new ArrayList<Integer>(route.subList(Math.min(start, end), Math.max(start, end) + 1));

                route.removeAll(segment);

                int newPosition = random.nextInt(route.size());
                route.addAll(newPosition, segment);
            }
        }

        return population;
    }
}
