package src;

import javax.swing.plaf.nimbus.State;
import java.util.*;
import java.util.function.Function;

public class TransitionSystem <StateType> {
    StateType startState;
    HashMap<StateType, Vertex<StateType>> allVertices;

    public TransitionSystem(StateType start) {
        this.allVertices = new HashMap<>();
        this.addVertex(start);
        this.startState = start;
    }

    public void addVertex(StateType label) {
        if (!allVertices.containsKey(label)) {
            this.allVertices.put(label, new Vertex<StateType>(label));
        }
    }

    public void addTransition(String transitionLabel, StateType fromLabel, StateType toLabel) {
        this.addVertex(fromLabel);
        this.addVertex(toLabel);
        Vertex<StateType> startV = this.allVertices.get(fromLabel);
        Vertex<StateType> endV = this.allVertices.get(toLabel);
        startV.nextStates.put(transitionLabel, endV);
    }

    // Given a list of transitions the system should take (in order),
    // produce the *trace* of the system
    // (i.e., the list of states that the system visits, in order)
    public List<StateType> runSystem(ArrayList<String> transitions ) {
        Vertex<StateType> currentState = this.allVertices.get(this.startState);
        ArrayList<StateType> result = new ArrayList<>();
        result.add(this.startState);
        for (String transition : transitions) {
            currentState = currentState.getNextState(transition);
            result.add(currentState.label);
        }
        return result;
    }

    public boolean canBeBad(Function<StateType, Boolean> badProperty) {
        Vertex<StateType> start = this.allVertices.get(this.startState);
        return start.canVisitBad(badProperty);
    }

    public boolean canLoop() {
        Vertex<StateType> start = this.allVertices.get(this.startState);
        return start.canLoop();
    }
}
