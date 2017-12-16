import java.util.*;

public class PointState implements State, Comparable<PointState>{

    private double heuristic;
    private double distFromRoot;
    private double x;
    private double y;
    private int id;
    private State previous;
    private List<State> neighbors;

    public PointState(String line, double dist){
        String[] point = line.split(",");
        this.x = Double.parseDouble(point[0]);
        this.y = Double.parseDouble(point[1]);
        this.id = Integer.parseInt(point[2]);
        this.neighbors = new ArrayList<State>();
        this.distFromRoot = dist;
    }

    public PointState(){
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
    public double get_heuristic(){
        return this.heuristic;
    }

    @Override
    public void set_heuristic(State goal){
        this.heuristic = this.distance_from(goal);
    }

    @Override
    public double get_distance(){
        return this.distFromRoot;
    }

    public void build_neighborhood(State a){
        try {this.neighbors.add(a);
        }
        catch (NullPointerException e) {System.out.println("Hi");}
    }

    public int compareTo(PointState s){
        return Double.compare(this.heuristic + this.distFromRoot, s.heuristic + s.distFromRoot);
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        PointState that = (PointState) o;

        if (!(this.x == that.x)) return false;
        return (this.y == that.y);
    }

    public void change_id(int new_id){
        this.id = new_id;
    }

    @Override
    public List<State> get_neighbours(){
        return this.neighbors;
    }

    @Override
    public void set_distance(Double dist){
        this.distFromRoot = dist;
    }

    /*calculates distance of two nodes/taxis-node/PointState in general
    taken from: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi */
    @Override
    public double distance_from(State a){
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(this.y - a.get_y());
        double lonDistance = Math.toRadians(this.x - a.get_x());
        double b = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(a.get_y())) * Math.cos(Math.toRadians(this.y))* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(b), Math.sqrt(1 - b));
        double distance = R * c;

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    /*given a list of nodes, computes which node is closest to the state we are examining. That node is then added to the neighborhood of the state*/
    @Override
    public void nearest(List<State> nodes){
        double min = Double.MAX_VALUE;
        PointState target = new PointState();
        target = null;
        for (Iterator iter = nodes.iterator(); iter.hasNext();){
            PointState elem = (PointState) iter.next();
            if (!this.equals(elem)){
                if (this.distance_from(elem) < min){ min = this.distance_from(elem); target = (PointState) elem;}
            }
        }
        target.build_neighborhood(this);
        this.build_neighborhood(target);
    }

    /*decides if a State is final*/
    @Override
    public boolean isFinal(State client){
        return this.equals(client);
    }

    @Override
    public int hashCode(){
        Double a = new Double(this.x);
        int result = a.hashCode();
        Double b = new Double(this.y);
        result = 31 * result + b.hashCode();
        return result;
    }

    /*prints contents of a State. Just for debugging!*/
    @Override
    public void printMe(){
            System.out.println(this.x + "," + this.y);
    }
}
