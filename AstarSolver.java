import java.util.*;
import java.io.*;

public class AstarSolver implements Solver{

	//returns a RetObj containing a map, which helps to reconstruct the path, and the final state of the path computed
    @Override
    public RetObj solve (State initial, State goal, List<State> Nodes, List<State> Taxis, int beam){
        Set<State> closedSet = new HashSet<>();
        PriorityQueue<State> openSet = new PriorityQueue<>();
        Map<State, State> cameFrom = new HashMap<>();

        openSet.add(initial);
        initial.set_heuristic(goal);
        int max_size = openSet.size();
        int steps = 0;
        while (!(openSet.isEmpty())){
        	steps++;
        	if (openSet.size() > max_size){
            	max_size = openSet.size();
            }
            State cur = openSet.poll();
            
            
            if (cur.isFinal(goal)){
                RetObj myObj = new RetObj(cameFrom, cur);
                System.out.println("A* did "+steps+" steps with an actual maximum beam size of "+max_size);
                return myObj;
            }
            closedSet.add(cur);
            for (Iterator iter = cur.get_neighbours().iterator(); iter.hasNext();){
                State neighbor = (State) iter.next();
                neighbor.set_heuristic(goal);

                if (!closedSet.contains(neighbor)){ 
                    if (!openSet.contains(neighbor)){
                        openSet.add(neighbor);
                    }
                    if (openSet.size() == beam + 1) openSet = removelast(openSet);


                    double tentativeDist = cur.get_distance() + neighbor.distance_from(cur);
                    if (tentativeDist >= neighbor.get_distance()) continue;

                    cameFrom.put(neighbor, cur);

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