import java.util.*;
import java.io.*;

public class taxiservice{

    public static void main(String args[]) throws IOException{
        Solver solver = new AstarSolver();

        //read input files & build the map
        State client = readClient(args[0]);
        List<State> Taxis = readTaxis(args[1]);
        List<State> Nodes = readNodes(args[2]);

//        client.nearest(Nodes).printMe();
        double minDistance = Double.MAX_VALUE;
        int minID = -1;
        List<State> Results = new ArrayList<State>();
        for (Iterator iter = Taxis.iterator(); iter.hasNext();){
            State curTaxi = ((State) iter.next());
            //curTaxi.printMe();
            curTaxi.set_previous(null);
            curTaxi.nearest(Nodes);
            curTaxi.set_distance(0.0);
            client.nearest(Nodes);
            RetObj result = solver.solve(curTaxi, client, Nodes, Taxis, Integer.parseInt(args[3]));
            if (result == null) continue;
            //Results.add(result);
            //System.out.println(result.get_distance() + " with taxi id = " + curTaxi.get_id());
//            printSteps(result);
            //if (result.get_distance() < minDistance)    minID = curTaxi.get_id();
            //KML_writer(Results, minID);

        }
    }

//    private static void reConstruct(Map<State, State>,  State s){
//        if (state.get_previous() != null){
//            printSteps(state.get_previous());
//            state.printMe();
//        }
//    }

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

    private static List<State> readNodes(String path){
//        Map<Integer, PointState> map = new HashMap<Integer, PointState>();
//		BufferedReader in = null;
//		List<State> NodeList = new ArrayList<State>();
//        try {
//            in = new BufferedReader(new FileReader(path));
//            String line;
//            int previd = -1;
//            PointState prevnode = null, node = null;
//            //Point point;
//            in.readLine();
//
//            while ((line = in.readLine()) != null) {
//            	node = new PointState(line, Double.MAX_VALUE);
//				int id = node.get_id();
//                PointState intersection = (PointState) map.get(node.hashCode());
//				if (intersection != null) {
//					for (State neigh : node.get_neighbours()) intersection.build_neighborhood(neigh);
//					node = intersection;
//				}
//				if (id == previd) {
//					node.build_neighborhood(prevnode);
//					prevnode.build_neighborhood(node);
//				}
//           		map.put(node.hashCode(), node);
//
//            	previd = id;
//            	prevnode = node;
//            	NodeList.add(node);
//            }
//
//            return NodeList;
//        }
//        catch (IOException e) {
//        	System.out.println("Wrong Nodes Input File");
//        	System.exit(-1);
//        }
//        finally {
//        	try {
//            	if (in != null) in.close();
//            } catch (IOException e) {}
//        }
//
//        return null;
//	}

        try {
            File inFile2 = new File(path);
            PointState prev = new PointState(); //erwthmatiko no1
            prev = null;
            Map<Integer, PointState> seen = new HashMap<Integer, PointState>();
            BufferedReader input = new BufferedReader(new FileReader(inFile2));
            List<State> NodeList = new ArrayList<State>();
            int flag = 0;
            String line = input.readLine();		//insert if here
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
                        ret.build_neighborhood(node); // erwthmatiko no2
                        node.build_neighborhood(ret);
                    }

                }
                catch (NullPointerException e) {System.out.println("Hey");}
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
