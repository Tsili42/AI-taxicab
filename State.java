import java.util.*;

public interface State{
    public double get_x();
    public double get_y();
    public int get_id();
    public void set_heuristic(List<State> Taxis, List<State> Nodes);
    public void set_distance(Double dist);
    public double get_distance();
    public double distance_from(State a);
    public State get_previous();
    public List<State> get_neighbours();
    public State nearest(List<State> nodes);
    public boolean isFinal(State goal, List<State> Nodes);
}
