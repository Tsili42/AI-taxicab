import java.util.*;
import java.io.*;

public class taxiservice{

    public static void main(String args[] throws IOException){
        try {
            Solver solver = new AstarSolver();
			FILE inFile = new File(args[0]);	//read client position
			try (BufferedReader input = new BufferedReader(new FileReader(inFile))){
				String line = input.readLine();
				line = input.readLine();
				State client = new PointState(line);
			} catch (IOException e) {
                System.out.println("No input for client!");
			}

			FILE inFile1 = new File(args[1]);	//read taxis' positions
			try (BufferedReader input = new BufferedReader(new FileReader(inFile1))){
			    List<State> TaxisList = new List<>();
				String line = input.readLine();		//insert if here
				while ((line = input.readLine()) != null){
					State taxi = new PointState(line);
					TaxisList.add(taxi);
				}
			}catch (IOException e) {
                System.out.println("No input for taxis!");
			}

			FILE inFile2 = new File(args[2]);
			State prev = new PointState(); //erwthmatiko no1
			prev = null;
			try (BufferedReader input = new BufferedReader(new FileReader(inFile1))){
			    List<State> NodeList = new List<>();
				String line = input.readLine();		//insert if here
				while ((line = input.readLine()) != null){
					State node = new PointState(line);
					try{
					    prev.build_neighborhood(node); // erwthmatiko no2
					}
					catch (NullPointerEception e) {}
					node.build_neighborhood(prev);
					prev = node;
					NodeList.add(node);
				}
			}catch (IOException e) {
                System.out.println("No input for nodes!");
			}

			Set<State> seen = new HashSet<>();
			List<State> crossRoads = new List<>();
			for (Iterator iter = NodeList.iterator(); iter.hasNext();){
                PointState elem = (PointState) iter.next();

                if (!seen.add(elem)){
                    crossRoads.add(elem);
                }
			}
			

			//NEW_STUFF!
			for (Iterator iter = NodeList.iterator(); iter.hasNext();){
				for (Iterator crossiter = crossRoads.iterator(); crossiter.hasNext();){
					PointState node = (PointState) iter.next();
					PointState crossnode = (PointState) crossiter.next();
					List<State> neighbours = new List<>();
					if (node.compareTo(crossnode) == 0){
						crossnode.change_id(-1);				//to state pou einai stavrodromi ki exei tous swstous geitones exei id == -1
						neighbours = node.get_neighbours();
						for (Iterator i = neighbours.iterator(); i.hasNext();){
							PointState crossneighbour = (PointState) i.next();
							crossnode.build_neighborhood(crossneighbour);		//mono to state pou vrisketai sto crossRoads exei tous swstous geitones
						}
						//remove the state that doesn't have the correct neighbours from NodeList
						if (node.get_id() != -1){
							NodeList.remove(node);
						}
					}
				}
       		 }
		}
    }
}
