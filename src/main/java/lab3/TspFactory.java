package lab3;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TspFactory extends AbstractCandidateFactory<TspSolution> {
    private int numberOfCities = 131;

    public TspFactory() {
    }

    public TspSolution generateRandomCandidate(Random random) {
        List<Integer> route = new ArrayList<Integer>();
        for (int i = 1; i <= numberOfCities; i++) {
            route.add(i);
        }

        Collections.shuffle(route, random);

        return new TspSolution(route);
    }
}
