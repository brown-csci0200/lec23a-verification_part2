package test;

import org.junit.Assert;
import org.junit.Test;
import static src.TrafficLight.*;
import src.*;

import java.util.*;

public class TrafficTest {

    private TransitionSystem makeTrafficSystem(TrafficState start) {
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

    @Test
    public void testGoodTrafficSafety() {
        TransitionSystem goodSystem = makeTrafficSystem(new TrafficState(RED, GREEN));
        Assert.assertFalse(goodSystem.canBeBad(
                (state) -> state.equals(new TrafficState(GREEN, GREEN))
        ));
    }

    @Test
    public void testBadTrafficSafety() {
        TransitionSystem badSystem = makeTrafficSystem(new TrafficState(RED, RED));
        Assert.assertTrue(badSystem.canBeBad(
                (state) -> state.equals(new TrafficState(GREEN, GREEN))
        ));
    }

    @Test
    public void testGoodTrafficLiveness() {
        TransitionSystem goodSystem = makeTrafficSystem(new TrafficState(RED, GREEN));
        Assert.assertTrue(goodSystem.canLoop());
    }

    @Test
    public void testBadTrafficLiveness() {
        TransitionSystem badSystem = makeTrafficSystem(new TrafficState(RED, RED));
        Assert.assertTrue(badSystem.canLoop());
    }
}
