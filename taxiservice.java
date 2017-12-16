import java.util.*;
import java.io.*;

public class taxiservice{

    public static void main(String args[]) throws IOException{
        Solver solver = new AstarSolver();

        /*read input files & build the map*/
        State client = readClient(args[0]);
        List<State> Taxis = readTaxis(args[1]);
        List<State> Nodes = readNodes(args[2]);

        /*Run A* for each taxi*/
        runMultipleSolvers(client, Taxis, Nodes, solver, args[3]);
    }

    private static void runMultipleSolvers(State client, List<State> Taxis, List<State> Nodes, Solver solver, String path){
        double minDistance = Double.MAX_VALUE;
        int minID = -1;
        List<RetObj> Results = new ArrayList<RetObj>();

        for (Iterator iter = Taxis.iterator(); iter.hasNext();){	/*run A* for every taxi available*/
            State curTaxi = ((State) iter.next());
            curTaxi.nearest(Nodes);
            client.nearest(Nodes);
            for (Iterator i = Nodes.iterator(); i.hasNext();){		/*for every execution of A* we must set all other nodes' distances from root equal to infinity*/
                State curNode = ((State) i.next());
                curNode.set_distance(Double.MAX_VALUE);
            }
            client.set_distance(Double.MAX_VALUE);					/*same with clients distance from root*/
            RetObj result = solver.solve(curTaxi, client, Nodes, Taxis, Integer.parseInt(path));
            if (result == null) continue;
            Results.add(result);
            if ((result.get_final()).get_distance() < minDistance){		/*find the taxi_id of the vehicle traveling the minimum distance*/
                 minID = curTaxi.get_id();
                 minDistance = (result.get_final()).get_distance();
            }
        }

        /*Uncomment the following for testing purposes*/
//        System.out.println("The selected (minimum) distance is "+minDistance +" traveled by taxi with id = "+minID);

        KML_writer("Taxi_Routes_little.kml",Results,minID);

    }

    /*creates the KML file in order to visually represent the routes of our taxis*/
     private static void KML_writer(String filename, List<RetObj> results, int min_id){
        FileWriter fileWriter = null;
        BufferedWriter Writer = null;
    	try{
            fileWriter = new FileWriter(filename);
            Writer = new BufferedWriter(fileWriter);
            String opening = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
							"<kml xmlns=\"http://earth.google.com/kml/2.1\">\n"+
							"<Document>\n"+
							"<name>Taxi Routes_Max_beam_little</name>\n"+
							"<Style id=\"green\">\n"+
							"<LineStyle>\n"+
							"<color>ff009900</color>\n"+
							"<width>4</width>\n"+
							"</LineStyle>\n"+
							"</Style>\n"+
							"<Style id=\"red\">\n"+
							"<LineStyle>\n"+
							"<color>ff0000ff</color>\n"+
							"<width>4</width>\n"+
							"</LineStyle>\n"+
							"</Style>\n";
            Writer.write(opening);
            for (Iterator iter = results.iterator(); iter.hasNext();){
            	RetObj curr = ((RetObj) iter.next());
            	State current = (State) curr.get_final();
            	Map<State,State> result = (Map<State,State>) curr.get_Map();
            	String path = current.get_x() + "," +current.get_y()+"\n";
    			String Taxi;
        		while (result.containsKey(current)){				//reconstruct the path from client to taxi
        			current = result.get(current);
        			path = path + current.get_x()+","+current.get_y()+"\n";
        		}
                if (current.get_id() == min_id){
                    Taxi = "<Placemark>\n"+
                            "<name>Taxi "+ current.get_id()+"</name>\n"+
                            "<styleUrl>#green</styleUrl>\n"+
                            "<LineString>\n"+
                            "<altitudeMode>relative</altitudeMode>\n"+
                            "<coordinates>\n"+
                            path+
                            "</coordinates>\n"+
                            "</LineString>\n"+
                            "</Placemark>\n";
                }
                else{
                    Taxi = "<Placemark>\n"+
                            "<name>Taxi "+current.get_id()+"</name>\n"+
                            "<styleUrl>#red</styleUrl>\n"+
                            "<LineString>\n"+
                            "<altitudeMode>relative</altitudeMode>\n"+
                            "<coordinates>\n"+
                            path+
                            "</coordinates>\n"+
                            "</LineString>\n"+
                            "</Placemark>\n";
                }
               Writer.write(Taxi);
            }
            String closing = "</Document>\n"+
                            "</kml>";
            Writer.write(closing);
            Writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /*reads info for client and creates the state that represents them*/
    private static State readClient(String path){
        try {
            File inFile = new File(path);	//read client position
            BufferedReader input = new BufferedReader(new FileReader(inFile));
            String line = input.readLine();
            line = input.readLine();
            State client = new PointState(line, Double.MAX_VALUE);
            return client;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*reads info for the taxis and creates a list where every element is a state describing a taxi*/
    private static List<State> readTaxis(String path){
        try {
            File inFile1 = new File(path);	//read taxis' positions
            BufferedReader input = new BufferedReader(new FileReader(inFile1));
            List<State> TaxisList = new ArrayList<State>();
            String line = input.readLine();		//insert if here
            while ((line = input.readLine()) != null){
                State taxi = new PointState(line, 0);
                TaxisList.add(taxi);
            }
            return TaxisList;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*reads info about the nodes of the map. Creates a list where every element corresponds to one node*/
    private static List<State> readNodes(String path){
        try {
            File inFile2 = new File(path);
            PointState prev = new PointState();
            prev = null;
            Map<Integer, PointState> seen = new HashMap<Integer, PointState>();
            BufferedReader input = new BufferedReader(new FileReader(inFile2));
            List<State> NodeList = new ArrayList<State>();
            int flag = 0;
            String line = input.readLine();
            while ((line = input.readLine()) != null){
                PointState node = new PointState(line, Double.MAX_VALUE);

                PointState temp = node;

                try{
                    flag = 0;
                    if(seen.containsKey(node.hashCode())){
                        flag = 1;
                        node = seen.get(temp.hashCode());
                    }
                    else{
                        seen.put(node.hashCode(),node);
                    }

                    if (temp.get_id() == prev.get_id()){
                        PointState ret = seen.get(prev.hashCode());
                        ret.build_neighborhood(node);
                        node.build_neighborhood(ret);
                    }

                }
                catch (NullPointerException e) {}
                prev = temp;
                if (flag == 0) NodeList.add(node);
            }
            return NodeList;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
