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

    public boolean canVisitBad(Function<StateType, Boolean> badProperty) {
        return this.canVisitBad_helper(badProperty, new HashSet<>());
    }

    private boolean canVisitBad_helper(
            Function<StateType, Boolean> badProperty, HashSet<Vertex<StateType>> visited) {
        // Will fill in during next lecture
        return false;
    }

    public boolean canLoop() {
        return this.canLoop_helper(new HashSet<>());
    }

    private boolean canLoop_helper(HashSet<StateType> visited) {
        // Will fill in during next lecture
        return false;
    }
}