import java.util.*;

public interface Solver {
  // Returns the solution, or null if there is none.
  public State solve(State initial, State goal, List<State> Nodes, List<State> StartingPoints, int beam);
}
