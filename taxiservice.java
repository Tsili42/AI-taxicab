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
			State prev = new PointState(null); //erwthmatiko no1
			try (BufferedReader input = new BufferedReader(new FileReader(inFile1))){
			    List<State> NodeList = new List<>();
				String line = input.readLine();		//insert if here
				while ((line = input.readLine()) != null){
					State node = new PointState(line);
					prev.build_neighborhood(node); // erwthmatiko no2
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




        }
    }
}
