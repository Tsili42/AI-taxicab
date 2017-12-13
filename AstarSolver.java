import java.util.*;

public class AstarSolver implements Solver{

    @Override
    public State solve (State initial, int beam){
        Set<State> closedSet = new HashSet<>();
        PriorityQueue<State> openSet = new PriorityQueue<>();

        openSet.add(initial);

        while (!(openSet.isEmpty())){
            State cur = openSet.remove();

            if (cur.isFinal()) return cur;

            closedSet.add(cur);

            for (Iterator iter = cur.get_neighbours().iterator(); iter.hasNext();){
                PointState neighbor = (PointState) iter.next();

                if (!closedSet.contains(neighbor)){ //Questionmark cuz of our equals function
                    if ((!openSet.contains(neighbor)) && (openSet.size() < beam)){
                        openSet.add(neighbor);
                    }

                    tentativeDist = cur.get_distance() + neighbor.calcDist(cur);
                    if (tentativeDist >= neighbor.get_distance()) continue;

                    neighbor.set_distance(tentativeDist);


                }
            }

        }

        return null;
    }

}
