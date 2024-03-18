package src;

import java.util.HashSet;

public class ScheduleState {
    HashSet<String> classes;

    public ScheduleState(HashSet<String> c) {
        this.classes = c;
    }

    @Override
    public boolean equals(Object o) {
        if (!( o instanceof ScheduleState)) return false;
        ScheduleState other = (ScheduleState) o;
        return (this.classes.equals(other.classes));
    }

    @Override
    public int hashCode() {
        // We need to override hashCode so that we can insert our states into HashSets and Hashmaps
        return this.classes.hashCode();
    }
}