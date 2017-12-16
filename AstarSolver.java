import java.util.*;
import java.io.*;

public class AstarSolver implements Solver{

	//returns an object containing a map, which helps to reconstruct the path, and the final state of the path computed
    @Override
    public RetObj solve (State initial, State goal, List<State> Nodes, List<State> Taxis, int beam){
        Set<State> closedSet = new HashSet<>();
        PriorityQueue<State> openSet = new PriorityQueue<>();
        Map<State, State> cameFrom = new HashMap<>();

        openSet.add(initial);
        initial.set_heuristic(goal);

        /* Uncomment the following for testing purposes */
//        int max_size = openSet.size();
//        int steps = 0;

        while (!(openSet.isEmpty())){
        	/* Uncomment the following for testing purposes */
//        	steps++;
//        	if (openSet.size() > max_size){
//            	max_size = openSet.size();
//            }
            State cur = openSet.poll(); /*get the state with the highest priority*/

            if (cur.isFinal(goal)){
                RetObj myObj = new RetObj(cameFrom, cur);

                /*Uncomment the following for testing purposes*/
//                System.out.println("A* did "+steps+" steps with an actual maximum beam size of "+max_size);

                return myObj;
            }

            closedSet.add(cur); /*We are done with the current state*/
            for (Iterator iter = cur.get_neighbours().iterator(); iter.hasNext();){
                State neighbor = (State) iter.next();
                neighbor.set_heuristic(goal);

                if (!closedSet.contains(neighbor)){
                    if (!openSet.contains(neighbor)){
                        openSet.add(neighbor);
                    }
                    if (openSet.size() == beam + 1) openSet = removelast(openSet); /*keep size of openSet less or equal to beam*/


                    double tentativeDist = cur.get_distance() + neighbor.distance_from(cur);
                    if (tentativeDist >= neighbor.get_distance()) continue;

                    /*This is the best path to this state - record it & update distance*/
                    cameFrom.put(neighbor, cur);
                    neighbor.set_distance(tentativeDist);
                }
            }
        }
        return null; /*If it fails*/
    }

    /*A method to remove the last object (lowest priority) from a priority queue*/
    private PriorityQueue removelast(PriorityQueue<State> Set){
        PriorityQueue<State> newSet = new PriorityQueue<>();

        while (Set.size() > 1)  newSet.add(Set.poll());
        Set.clear();

        return newSet;
    }

}
