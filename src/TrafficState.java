package src;

public class TrafficState {
    TrafficLight hope;
    TrafficLight waterman;

    public TrafficState(TrafficLight h, TrafficLight w) {
        this.hope = h;
        this.waterman = w;
    }

    @Override
    public boolean equals(Object o) {
        if (!( o instanceof TrafficState)) return false;
        TrafficState other = (TrafficState) o;
        return (this.hope == other.hope &&
                this.waterman == other.waterman);
    }

    @Override
    public int hashCode() {
        // We need to override hashCode so that we can insert our states into HashSets and Hashmaps
        return this.hope.hashCode() + this.waterman.hashCode();
    }
}
