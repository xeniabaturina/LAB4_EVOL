package lab3;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TspCrossover extends AbstractCrossover<TspSolution> {
    protected TspCrossover() {
        super(1);
    }

    protected List<TspSolution> mate(TspSolution p1, TspSolution p2, int i, Random random) {
        int size = p1.getRoute().size();
        int crossoverPoint1 = random.nextInt(size);
        int crossoverPoint2 = random.nextInt(size);

        while (crossoverPoint1 == crossoverPoint2) {
            crossoverPoint2 = random.nextInt(size);
        }

        int startPoint = Math.min(crossoverPoint1, crossoverPoint2);
        int endPoint = Math.max(crossoverPoint1, crossoverPoint2);

        List<Integer> child1 = new ArrayList<Integer>(p1.getRoute().subList(startPoint, endPoint + 1));
        List<Integer> child2 = new ArrayList<Integer>(p2.getRoute().subList(startPoint, endPoint + 1));

        for (int j = 0; j < size; j++) {
            if (!child1.contains(p2.getRoute().get(j))) {
                child1.add(p2.getRoute().get(j));
            }

            if (!child2.contains(p1.getRoute().get(j))) {
                child2.add(p1.getRoute().get(j));
            }
        }

        List<TspSolution> children = new ArrayList<TspSolution>();
        children.add(new TspSolution(child1));
        children.add(new TspSolution(child2));
        return children;
    }
}
