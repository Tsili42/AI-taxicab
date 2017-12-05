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
				String line = input.readLine();		//insert if here
				while ((line = input.readLine()) != null){
					State taxi = new PointState(line);
					List<State> TaxisList = new List<State>;
					TaxisList.add(taxi);
				}
			}catch (IOException e) {
            System.out.println("No input for taxis!");
			}


        }
    }
}
