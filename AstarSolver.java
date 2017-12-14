import java.util.*;

public class AstarSolver implements Solver{

    @Override
    public State solve (State initial, State goal, List<State> Nodes, List<State> Taxis, int beam){
        Set<State> closedSet = new HashSet<>();
        PriorityQueue<State> openSet = new PriorityQueue<>();

        openSet.add(initial);
        initial.set_heuristic(Taxis, Nodes);

        while (!(openSet.isEmpty())){
            State cur = openSet.remove();

            if (cur.isFinal(goal, Nodes)) return cur;

            closedSet.add(cur);

            for (Iterator iter = cur.get_neighbours().iterator(); iter.hasNext();){
                State neighbor = (State) iter.next();
                neighbor.set_heuristic(Taxis, Nodes);

                if (!closedSet.contains(neighbor)){ //Questionmark cuz of our equals function
                    if ((!openSet.contains(neighbor)) && (openSet.size() < beam)){
                        openSet.add(neighbor);
                    }
                    else if (openSet.size() >= beam) continue;

                    double tentativeDist = cur.get_distance() + neighbor.distance_from(cur);
                    if (tentativeDist >= neighbor.get_distance()) continue;

                    neighbor.set_distance(tentativeDist);

                }
            }

        }

        return null;
    }

}
