import java.util.Collection;

public interface State{
    public int get_x();
    public int get_y();
    public int get_id();
    public int get_heuristic();
    public int get_distance();
    public State get_previous();
    public boolean isFinal();
    public boolean isBad();
    public State readState(String);
    //public State getPrevious();
    public Collection<State> next();
}
