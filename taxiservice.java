import java.util.*;
import java.io.*;

public class taxiservice{

    public static void main(String args[]) throws IOException{
        try {
            Solver solver = new AstarSolver();
			File inFile = new File(args[0]);	//read client position
			BufferedReader input = new BufferedReader(new FileReader(inFile));
			String line = input.readLine();
			line = input.readLine();
			PointState client = new PointState(line);
			File inFile1 = new File(args[1]);	//read taxis' positions
			input = new BufferedReader(new FileReader(inFile1));
			List<State> TaxisList = new ArrayList<State>();
			line = input.readLine();		//insert if here
			while ((line = input.readLine()) != null){
				State taxi = new PointState(line);
				TaxisList.add(taxi);
			}
			

			File inFile2 = new File(args[2]);
			PointState prev = new PointState(); //erwthmatiko no1
			prev = null;
			input = new BufferedReader(new FileReader(inFile2));
			    List<State> NodeList = new ArrayList<State>();
				line = input.readLine();		//insert if here
				while ((line = input.readLine()) != null){
					PointState node = new PointState(line);
					//System.out.println("Gamw to nako");
					try{
						if (node.get_id() == prev.get_id()){
					    	prev.build_neighborhood(node); // erwthmatiko no2
					    	node.build_neighborhood(prev);
					    }
					}
					catch (NullPointerException e) {}
					prev = node;
					NodeList.add(node);
				}
				// for (Iterator iter = NodeList.iterator(); iter.hasNext();){
				// 	PointState elem = (PointState) iter.next();
				// 	List<State> gotmilk = elem.get_neighbours();
				// 	System.out.println(elem.get_x()+" my neighbours:");
				// 	for (Iterator i = gotmilk.iterator(); i.hasNext();){
				// 		PointState point  = (PointState) i.next();
				// 		try{
				// 			System.out.println(point.get_x());
				// 		}
				// 		catch(NullPointerException e){System.out.println("Gamw to nako !");}
				// 	}
				// }

			// System.out.println(".|.");
			Set<State> seen = new HashSet<>();
			List<State> crossRoads = new ArrayList<>();
			//Set<State> crossRoads = new HashSet<>();	//mallon me set pio grhgoro
			for (Iterator iter = NodeList.iterator(); iter.hasNext();){
                PointState elem = (PointState) iter.next();

                if (!seen.add(elem)){
                	if (!crossRoads.contains(elem)){crossRoads.add(elem);}   
                	//crossRoads.add(elem);               
                    //System.out.println("ok");
                }
			}


			// for (Iterator iter = crossRoads.iterator(); iter.hasNext();){
			// 	PointState elem = (PointState) iter.next();
			// 	//List<State> gotmilk = elem.get_neighbours();
			// 	System.out.println(elem.get_y());
			// }

			System.out.println(crossRoads.size());

			for (Iterator iter = NodeList.iterator(); iter.hasNext();){
				PointState node = (PointState) iter.next();
				for (Iterator crossiter = crossRoads.iterator(); crossiter.hasNext();){
					
					PointState crossnode = (PointState) crossiter.next();
					List<State> neighbours = new ArrayList<>();
					if ((node.compareTo(crossnode) == 0) && (node.get_id() != crossnode.get_id())){
						//System.out.println(node.get_x());
						//System.out.println("MPIKA");
						crossnode.change_id(-1);				//to state pou einai stavrodromi ki exei tous swstous geitones exei id == -1
						neighbours = node.get_neighbours();
						for (Iterator i = neighbours.iterator(); i.hasNext();){
							PointState crossneighbour = (PointState) i.next();
							crossnode.build_neighborhood(crossneighbour);		//mono to state pou vrisketai sto crossRoads exei tous swstous geitones
						}
						//remove the state that doesn't have the correct neighbours from NodeList
						if (node.get_id() != -1){
							iter.remove();
						}
					}
				}
       		 }
        		for (Iterator iter = NodeList.iterator(); iter.hasNext();){
					PointState elem = (PointState) iter.next();
					List<State> gotmilk = elem.get_neighbours();
					System.out.println(elem.get_x()+" my neighbours:");
					for (Iterator i = gotmilk.iterator(); i.hasNext();){
						PointState point  = (PointState) i.next();
						try{
							System.out.println(point.get_x());
						}
						catch(NullPointerException e){System.out.println("Gamw to nako !");}
					}
				}

			//  for (Iterator iter = NodeList.iterator(); iter.hasNext();){
			//  	PointState elem = (PointState) iter.next();
			//  	for (Iterator tax = TaxisList.iterator(); tax.hasNext();){
			// 		PointState taxi = (PointState) tax.next();
			// 		double h = elem.distance_from(taxi);
			// 		System.out.println(elem.get_x() + " has distance from taxi " + h);
			// 	}
			// }
				

			//  for (Iterator iter = NodeList.iterator(); iter.hasNext();){
			// 		PointState elem = (PointState) iter.next();
			// 		if (elem.isFinal(client,NodeList)){
			// 			System.out.println (elem.get_x() + " " + elem.get_y());
			// 		}
			// }
		
		}catch(IOException e){
			e.printStackTrace();
		}
    }
}
