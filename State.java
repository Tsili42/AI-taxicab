import java.util.*;

public interface State{
    public double get_x();
    public double get_y();
    public int get_id();
    public void set_heuristic(State goal);
    public void set_distance(Double dist);
    public double get_distance();
    public double distance_from(State a);
    public State get_previous();
    public void set_previous(State prev);
    public List<State> get_neighbours();
    public void nearest(List<State> nodes);
    public boolean isFinal(State goal);
    public void printMe();
    public double get_heuristic();
}
