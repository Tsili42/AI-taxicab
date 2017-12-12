import java.util.Collection;

public interface State{
    public double get_x();
    public double get_y();
    public int get_id();
    public int get_heuristic();
    public int get_distance();
    public State get_previous();
   // public boolean isFinal();
    //public boolean isBad();
   // public State readState(String);
   // public int CompareTo(State S);
  //  public void change_id(int);
  //  public List<State> get_neighbours();
    //public State getPrevious();
   // public Collection<State> next();
}
