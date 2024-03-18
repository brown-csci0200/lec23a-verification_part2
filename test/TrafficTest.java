package test;

import org.junit.Assert;
import org.junit.Test;
import static src.TrafficLight.*;
import src.*;

import java.util.*;

public class TrafficTest {

    private TransitionSystem<TrafficState> makeTrafficSystem(TrafficState start) {
        TransitionSystem ts = new TransitionSystem(start);
        ts.addTransition("timer",
                new TrafficState(GREEN, GREEN), new TrafficState(YELLOW, YELLOW));
        ts.addTransition("timer",
                new TrafficState(GREEN, YELLOW), new TrafficState(YELLOW, YELLOW));
        ts.addTransition("timer",
                new TrafficState(GREEN, RED), new TrafficState(YELLOW, RED));
        ts.addTransition("timer",
                new TrafficState(YELLOW, GREEN), new TrafficState(YELLOW, YELLOW));
        ts.addTransition("timer",
                new TrafficState(YELLOW, YELLOW), new TrafficState(RED, RED));
        ts.addTransition("timer",
                new TrafficState(YELLOW, RED), new TrafficState(RED, GREEN));
        ts.addTransition("timer",
                new TrafficState(RED, GREEN), new TrafficState(RED, YELLOW));
        ts.addTransition("timer",
                new TrafficState(RED, YELLOW), new TrafficState(GREEN, RED));
        ts.addTransition("timer",
                new TrafficState(RED, RED), new TrafficState(GREEN, GREEN));
        return ts;
    }

//    private TransitionSystem makeScheduleSystem(TrafficState start) {
//        TransitionSystem ts = new TransitionSystem(start);
//        ts.addTransition("add visa0100",
//                new ScheduleState(new HashSet<>()),
//                new ScheduleState(new HashSet<>(Set.of("visa0100"))));
//        ts.addTransition("add visa0100",
//                new ScheduleState(new HashSet<>(Set.of("econ0110"))),
//                new ScheduleState(new HashSet<>(Set.of("econ0110", "visa0100"))));
//        ts.addTransition("add econ0110",
//                new ScheduleState(new HashSet<>()),
//                new ScheduleState(new HashSet<>(Set.of("econ0110"))));
//        ts.addTransition("add econ0110",
//                new ScheduleState(new HashSet<>(Set.of("visa0100"))),
//                new ScheduleState(new HashSet<>(Set.of("econ0110", "visa0100"))));
//        return ts;
//    }

    private static boolean isTrafficHazard(TrafficState state) {
        // Version 1: a state is a traffic hazard if it is double-green
//        return state.equals(new TrafficState(GREEN, GREEN));

        // Version 2: a state is a traffic hazard if it doesn't have a red
        return !(state.hope == RED || state.waterman == RED);
    }

    @Test
    public void testGoodTrafficSafety() {
        TransitionSystem<TrafficState> goodSystem = makeTrafficSystem(new TrafficState(RED, GREEN));
        Assert.assertFalse(goodSystem.canBeBad(TrafficTest::isTrafficHazard));
    }

    @Test
    public void testBadTrafficSafety() {
        TransitionSystem<TrafficState> badSystem = makeTrafficSystem(new TrafficState(RED, RED));
        Assert.assertTrue(badSystem.canBeBad(TrafficTest::isTrafficHazard));
    }

    @Test
    public void testGoodTrafficLiveness() {
        TransitionSystem<TrafficState> goodSystem = makeTrafficSystem(new TrafficState(RED, GREEN));
        Assert.assertTrue(goodSystem.canLoop());
    }

    @Test
    public void testBadTrafficLiveness() {
        TransitionSystem<TrafficState> badSystem = makeTrafficSystem(new TrafficState(RED, RED));
        Assert.assertTrue(badSystem.canLoop());
    }
}
