import java.util.*;

public class PointState implements State, Comparable<PointState>{

    private double heuristic;
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

   
    public double get_heuristic(List<State> taxis){
        double min = Double.MAX_VALUE;
        for (Iterator iter = taxis.iterator(); iter.hasNext();){
            PointState elem = (PointState) iter.next();
            double dist = Math.sqrt(Math.pow((this.x - elem.get_x()),2.0) + Math.pow((this.y - elem.get_y()),2.0));
            if (dist < min){min = dist;}
        }
        this.heuristic = min;
        return min;
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


    public void change_id(int new_id){
        this.id = new_id;
    }

    public List<State> get_neighbours(){    //prepei na dhlwthei ws List<State> h ws List sketo?
        return this.neighbors;
    }

    //calculates distance of two nodes/taxis-node/PointState in general
    //taken from: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
    public double distance_from (State a){
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(this.y - a.get_y());
        double lonDistance = Math.toRadians(this.x - a.get_x());
        double b = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(a.get_y())) * Math.cos(Math.toRadians(this.y))* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(b), Math.sqrt(1 - b));
        double distance = R * c;

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    //given a list of nodes, computes which node is closest to the state we are examining
    public State nearest(List<State> nodes){
        double min = Double.MAX_VALUE;
        PointState target = new PointState();
        target = null;
        for (Iterator iter = nodes.iterator(); iter.hasNext();){
            PointState elem = (PointState) iter.next();
            if (!this.equals(elem)){
                if (this.distance_from(elem) < min){ min = this.distance_from(elem); target = (PointState) elem;}
            }
        }
        return target;
    }

    //decides if a State is final
    public boolean isFinal (PointState client, List<State> nodes){
        if (this.equals(client.nearest(nodes))){    //if our state is the nearest state for our client then it's a final state
            return true;
        }
        return false;
    }

}
