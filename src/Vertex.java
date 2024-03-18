package src;

import javax.swing.plaf.nimbus.State;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

public class Vertex<StateType> {
    StateType label;
    // map from transition labels to the vertex that transition goes to
    HashMap<String, Vertex<StateType>> nextStates;
    // key - String that represents a transition like "add econ0110" or "timer"
    // value - Vertex that is the destination of the transition

    public Vertex(StateType l) {
        this.label = l;
        this.nextStates = new HashMap<>();
    }

    public Vertex<StateType> getNextState(String transition) {
        return this.nextStates.get(transition);
    }

    // For usage, see TrafficTest.java
    public boolean canVisitBad(Function<StateType, Boolean> badProperty) {
        return this.canVisitBad_helper(badProperty, new HashSet<>());
    }

    private boolean canVisitBad_helper(
            Function<StateType, Boolean> badProperty, HashSet<Vertex<StateType>> visited) {
        // If we're at the state we're looking for, we can reach it
        if (badProperty.apply(this.label)) return true;
        // if not, check all the neighbors, and see if they can reach it
        for (Vertex<StateType> neighbor : this.nextStates.values()) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                if (neighbor.canVisitBad_helper(badProperty, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    // For usage, see TrafficTest.java
    public boolean canLoop() {
        return this.canLoop_helper(new HashSet<>());
    }

    private boolean canLoop_helper(HashSet<StateType> visited) {
        if (visited.contains(this.label)) return true;
        // We have to make a copy of the visited set,
        // to prevent our neighbors from interfering with our loop calculation
        HashSet<StateType> copyOfVisited = new HashSet<>(visited);
        copyOfVisited.add(this.label);
        for (Vertex<StateType> nextState : this.nextStates.values()) {
            if (nextState.canLoop_helper(copyOfVisited)) return true;
        }
        return false;
    }
}