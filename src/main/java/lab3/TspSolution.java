package lab3;

import java.util.ArrayList;
import java.util.List;

public class TspSolution {
    private final List<Integer> route;

    public TspSolution(List<Integer> route) {
        this.route = new ArrayList<Integer>(route);
    }

    public List<Integer> getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return "Route: " + route;
    }
}
