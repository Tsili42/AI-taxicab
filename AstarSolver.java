import java.util.*;
import java.io.*;

public class AstarSolver implements Solver{

    @Override
    public State solve (State initial, State goal, List<State> Nodes, List<State> Taxis, int beam){
        Set<State> closedSet = new HashSet<>();
        PriorityQueue<State> openSet = new PriorityQueue<>();

        openSet.add(initial);
        initial.set_heuristic(goal);

//        System.out.println("!");
        initial.printMe();
//        System.out.println("!");
        while (!(openSet.isEmpty())){
            State cur = openSet.poll();

            //cur.printMe();
            //System.out.println(cur.get_heuristic());
            if (cur.isFinal(goal)) return cur;

            closedSet.add(cur);

            for (Iterator iter = cur.get_neighbours().iterator(); iter.hasNext();){
                State neighbor = (State) iter.next();
                neighbor.set_heuristic(goal);

                if (!closedSet.contains(neighbor)){ //Questionmark cuz of our equals function
                    if (!openSet.contains(neighbor)){
                        openSet.add(neighbor);
                    }
                    if (openSet.size() == beam + 1) openSet = removelast(openSet);

                    //neighbor.printMe();
                    double tentativeDist = cur.get_distance() + neighbor.distance_from(cur);
                    if (tentativeDist >= neighbor.get_distance()) continue;

                    neighbor.set_previous(cur);
                    neighbor.set_distance(tentativeDist);
                }
            }

        }

        return null;
    }

    private PriorityQueue removelast(PriorityQueue<State> Set){
        PriorityQueue<State> newSet = new PriorityQueue<>();

        while (Set.size() > 1)  newSet.add(Set.poll());
        Set.clear();

        return newSet;
    }

}
