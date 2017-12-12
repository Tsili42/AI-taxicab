import java.util.*;

public class PointState implements State, Comparable<PointState>{

    private int heuristic;
    private int distFromRoot;
    private double x;
    private double y;
    private int id;
    private State previous;
    private List<State> neighbors;

    public PointState(){

    }
    public PointState(String line){
        String[] point = line.split(",");
        this.x = Double.parseDouble(point[0]);
        this.y = Double.parseDouble(point[1]);
        this.id = Integer.parseInt(point[2]);
        this.neighbors = new ArrayList<State>();
    }

    @Override
    public double get_x(){
        return this.x;
    }

    @Override
    public double get_y(){
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

    public void build_neighborhood(State a){
        try{
            this.neighbors.add(a);
        }
        catch (NullPointerException e) {System.out.println("Gamw to nako patokorfa!");}
    }

    public int compareTo(PointState s){
        int one = Double.compare(this.x, s.x);
        if (one != 0) return one;
        one = Double.compare(this.y, s.y);
        return one;
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        PointState that = (PointState) o;

        if (!(this.x == that.x)) return false;
        return (this.y == that.y);
    }

    @Override
    public int hashCode(){
        Double a = new Double(this.x);
        int result = a.hashCode();
        Double b = new Double(this.y);
        result = 31 * result + b.hashCode();
        return result;
    }

//NEW_STUFF!!
    public void change_id(int new_id){
        this.id = new_id;
    }

    public List<State> get_neighbours(){    //prepei na dhlwthei ws List<State> h ws List sketo?
        return this.neighbors;
    }
}
