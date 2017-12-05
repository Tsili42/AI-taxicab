import java.util.*;
//import java.util.ArrayList;
//import java.util.Collection;

public class PointState implements State, Comparable<PointState>{

    private int heuristic;
    private int distFromRoot;
    private int x;
    private int y;
    private int id;
    private State previous;
    private List<State> neighbors;
    //private Vector<Vector<Character>> map;

    public PointState(string line){
        String[] point = line.split(",");
        this.x = point[0];
        this.y = point[1];
        this.id = point[2];
        return myState;
    }

    @Override
    public int get_x(){
        return this.x;
    }

    @Override
    public int get_y(){
        return this.y;
    }

    @Override
    public int get_id(){
        return this.id;
    }

    @Override
    public State get_previous(){
        return this.previous;
    }

    @Override
    public int get_heuristic(){
        return this.heuristic;
    }

    @Override
    public int get_distance(){
        return this.distFromRoot;
    }

    public void build_neighborhood(List a){
        this.neighbors  = a;
    }

    public int compareTo(PointState s){
        int one = Integer.compare(this.x, s.x);
        if (one != 0) return one;
        one = Integer.compare(this.y, s.y);
        return one;
    }



//    @Override
//    public boolean isFinal(){
//        return (this.map.elementAt(this.iPos).elementAt(this.jPos) == 'E');
//    }
//
//    @Override
//    public boolean isBad(){ //Check if we are out of bounds or we just hit an obstacle
//        return (this.iPos + 1 > this.map.size()) || (this.iPos < 0) || (this.jPos + 1 > this.map.elementAt(0).size()) || (this.jPos < 0) || (this.map.elementAt(this.iPos).elementAt(this.jPos) == 'X');
//    }
//
//    private void addState(State state, Collection<State> states){
//        if (!state.isBad()) states.add(state);
//    }
//
//    @Override
//    public Collection<State> next(){ //return neighbors
//        Collection<State> states = new ArrayList<>();
//
//        //Go up
//        addState(new LakiState(this.cost + 3, this.iPos - 1, this.jPos, 'U', this, this.map), states);
//
//        //Go down
//        addState(new LakiState(this.cost + 1, this.iPos + 1, this.jPos, 'D', this, this.map), states);
//
//        //Go right
//        addState(new LakiState(this.cost + 1, this.iPos, this.jPos + 1, 'R', this, this.map), states);
//
//        //Go left
//        addState(new LakiState(this.cost + 2, this.iPos, this.jPos - 1, 'L', this, this.map), states);
//
//        return states;
//    }
//
//    @Override
//    public String toString(){
//        final StringBuilder sb = new StringBuilder(Character.toString(this.prevMove));
//        return sb.toString();
//    }
//
//    public LakiState(int myCost, int i, int j, char move, State myPrevious, Vector<Vector<Character>> spaceMap){
//        this.cost = myCost;
//        this.iPos = i;
//        this.jPos = j;
//        this.prevMove = move;
//        this.previous = myPrevious;
//        this.map = spaceMap;
//    }
//
//    @Override
//    public boolean equals(Object o){
//        if (this == o) return true;
//        if (o == null || this.getClass() != o.getClass()) return false;
//
//        LakiState that = (LakiState) o;
//
//        if (!(this.iPos == that.iPos)) return false;
//        if (!(this.jPos == that.jPos)) return false;
//        return (this.cost == that.cost);
//    }
//
//    @Override
//    public int hashCode(){
//        Integer a = new Integer(this.iPos);
//        int result = a.hashCode();
//        Integer b = new Integer(this.jPos);
//        result = 31 * result + b.hashCode();
//        Integer c = new Integer(this.cost);
//        result = 31 * result + c.hashCode();
//        return result;
//    }
//
//    public int compareTo(LakiState s){
//        return Integer.compare(this.cost, s.cost);
//    }
}
