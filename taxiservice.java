import java.util.*;
import java.io.*;

public class taxiservice{

    public static void main(String args[]) throws IOException{
        Solver solver = new AstarSolver();

        //read input files & build the map
        State client = readClient(args[0]);
        List<State> Taxis = readTaxis(args[1]);
        List<State> Nodes = readNodes(args[2]);

//        System.out.println("Size of nodes: " + Nodes.size());
//        //Printing block
//        for (Iterator iter = Nodes.iterator(); iter.hasNext();){
//            PointState elem = (PointState) iter.next();
//            System.out.println("(" + elem.get_x() + ", " + elem.get_y() + "):");
//            List<State> gotMilk = elem.get_neighbours();
//            for (Iterator j = gotMilk.iterator(); j.hasNext();){
//                PointState new_elem = (PointState) j.next();
//                try { System.out.println("(" + new_elem.get_x() + ", " + new_elem.get_y() + ")");
//                }
//                catch (NullPointerException e) {System.out.println("Hi2");}
//            }
//        }
        
        // NEW way of creating neighborhoods!
        Map<Integer,State> seen = new HashMap<>();
        for (Iterator iter = Nodes.iterator(); iter.hasNext();){
        	PointState elem = (PointState) iter.next();
        	if(!seen.containsValue(elem)){
        		seen.put(elem.hashCode(),elem);
        		elem.change_id(-1);
        	}
        	else{
        		//System.out.println("Found a duplicate!");
        		PointState node = (PointState) seen.get(elem.hashCode());
        		List<State> neighbours = new ArrayList<>();
        		neighbours = elem.get_neighbours();
        		for(Iterator i = neighbours.iterator(); i.hasNext();){
        			PointState crossneighbour = (PointState) i.next();
        			node.build_neighborhood(crossneighbour);
        		}        		
        	}
        }
        for(Iterator iter = Nodes.iterator(); iter.hasNext();){
        	PointState elem = (PointState) iter.next();
        	if (elem.get_id() != -1){
        		iter.remove();
        	}
        }

//         Set<State> seen = new HashSet<>();
//         Set<State> crossRoads = new HashSet<State>();
//         for (Iterator iter = Nodes.iterator(); iter.hasNext();){
//             PointState elem = (PointState) iter.next();

//             if (!seen.add(elem)){
//                 crossRoads.add(elem);
//             }
//         }

//         System.out.println(crossRoads.size());

//         for (Iterator iter = Nodes.iterator(); iter.hasNext();){
//             PointState node = (PointState) iter.next();
//             for (Iterator crossiter = crossRoads.iterator(); crossiter.hasNext();){
//                 PointState crossnode = (PointState) crossiter.next();
//                 List<State> neighbours = new ArrayList<>();
//                 if (node.equals(crossnode) && (node.get_id() != crossnode.get_id())){
//                     //System.out.println(node.get_id() + " ** " + crossnode.get_id());
//                     crossnode.change_id(-1);				//to state pou einai stavrodromi ki exei tous swstous geitones exei id == -1
//                     neighbours = node.get_neighbours();
//                     for (Iterator i = neighbours.iterator(); i.hasNext();){
//                         PointState crossneighbour = (PointState) i.next();
//                         crossnode.build_neighborhood(crossneighbour);		//mono to state pou vrisketai sto crossRoads exei tous swstous geitones
//                     }
//                     //remove the state that doesn't have the correct neighbours from NodeList
//                     if (node.get_id() != -1){
//                         iter.remove();
//                     }
//                 }
//             }
//         }

        for (Iterator iter = Taxis.iterator(); iter.hasNext();){
            State curTaxi = ((State) iter.next()).nearest(Nodes);
            curTaxi.set_distance(0.0);
            State result = solver.solve(curTaxi, client, Nodes, Taxis, Integer.parseInt(args[3]));
            if (result == null) continue;
            System.out.println(result.get_x());
            System.out.println(result.get_y());
        }
    }

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
        try {
            File inFile2 = new File(path);
            PointState prev = new PointState(); //erwthmatiko no1
            prev = null;
            BufferedReader input = new BufferedReader(new FileReader(inFile2));
            List<State> NodeList = new ArrayList<State>();
            String line = input.readLine();		//insert if here
            while ((line = input.readLine()) != null){
                PointState node = new PointState(line, Double.MAX_VALUE);
                //System.out.println("LOL");
                try{
                    if (node.get_id() == prev.get_id()){
                        prev.build_neighborhood(node); // erwthmatiko no2
                        node.build_neighborhood(prev);
                    }
                }
                catch (NullPointerException e) {System.out.println("Hey");}
                prev = node;
                NodeList.add(node);
            }
            return NodeList;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
