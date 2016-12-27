import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class Infection extends HttpServlet {

		private static Graph userBase;

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

    		JSONParser parser = new JSONParser();
    		double percentInfected = 0.0;
    		double percentBadUsers = 0.0;

    		try { 

    			JSONObject graphFile = (JSONObject) parser.parse(new FileReader("./src/main/java/TheGraph.json"));
    			JSONArray nodes = (JSONArray) graphFile.get("nodes");
    			JSONArray links = (JSONArray) graphFile.get("links");

    			Graph thisGraph = new Graph(nodes, links);

    			String infection = request.getParameter("infection");
    			String percent = request.getParameter("percentage");


    			if(infection != null && percent != null){

    			double prcnt = (Integer.parseInt(percent))/100.0;

    			

	    			if(infection.equals("Limited")){
	    				int numInfected = limitedInfection(thisGraph, "B", prcnt);
	    				percentInfected = ((double)numInfected/(double)thisGraph.getSize()) * 100.0;
	    				request.setAttribute("percentInfected",Double.toString(percentInfected));
	    				request.setAttribute("percentBadUsers","0");
	    			} else{
	    				int badUsers = exactInfection(thisGraph, "B", prcnt);
	    				percentBadUsers = ((double)badUsers/(double)thisGraph.getSize()) * 100.0;
	    				request.setAttribute("percentInfected",percent);
	    				request.setAttribute("percentBadUsers",Double.toString(percentBadUsers));
	    			}
    			}

    			request.setAttribute("graph", thisGraph.toJSONString());

    			// System.out.println("This is the entire GRAPHHHHH");
    			// System.out.println(thisGraph.toJSONString());

    		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    		

    		String infection = request.getParameter("infection");
    		String percent = request.getParameter("percentage");



    		System.out.println(infection);
    		System.out.println(percent);

    		request.setAttribute("percentage", percent);
    		request.setAttribute("infection", infection);

		    RequestDispatcher view=request.getRequestDispatcher("./index.jsp");
		    view.forward(request,response);

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
 	}

	//This method will infect the exact number of users requested, optimizing for the lowest number of edges between
	//infected and uninfected nodes
	public static int exactInfection(Graph graph, String version, double percentage){
		System.out.println("Inside exact infection");
		//execute the totally infect function and then see how many nodes are left, go through the nodes in graph sorted
		//by degree and infect all the ones with the lowest degree until we hit our exact percentage
		int infected = limitedInfection(graph, version, percentage);
		double numNodes = percentage * graph.getSize();
		double toInfect = numNodes - infected;


		//if we can get exactly what we want without halfway infecting components then lets do that
		if(toInfect <= 0.0){
			return 0;
		}

		HashMap<String, UserNode> allUsers = graph.getUsers();

		int queueSize = graph.getSize() - infected;

		Queue<UserNode> userPriorityQueue = new PriorityQueue(queueSize, userComparator);

		for(UserNode u : allUsers.values()){
			//if the user has not yet been infected then add them to the queue
			if(!u.getVersion().equals(version)){
				userPriorityQueue.add(u);
			}
		}		

		while(toInfect > 0){
			UserNode curr = userPriorityQueue.poll();
			curr.setVersion(version);
			allUsers.put(curr.getUsername(), curr);
			toInfect--;
		}

		int badUsers = 0;

		for(UserNode u : allUsers.values()){
			for(String s : u.getNeighbors()){
				UserNode thisNeighbor = allUsers.get(s);
				String thisVersion = thisNeighbor.getVersion();
				if(!u.getVersion().equals(thisVersion)){
					badUsers++;
				}
			}
		}

		//go through all the nodes in the graph and get the ones that are not yet infected, add them to priority queue and use a comparator
		//that sorts by number of neighbors, we want to optimize for the fewest number of neighbors
		System.out.println("This is how many nodes have a neighbor with different version: " + badUsers);
		return badUsers;
	}

	//Comparator to be used for sorting user nodes to find the one with the fewest connections
	public static Comparator<UserNode> userComparator = new Comparator<UserNode>(){
		@Override
		public int compare(UserNode u1, UserNode u2) {
            return (int) (u1.getNeighborCount() - u2.getNeighborCount());
        }
	};

//This method will get as close as possible to infecting the percentage of users provided but cannot execute
//any limited infections.
	public static int limitedInfection(Graph graph, String version, double percentage){

		System.out.println("Inside limited infection function");

		if(percentage < 0.0 || percentage > 1.0){
			System.out.println("Invalid percentage");
			return -1;
		}
		ArrayList<Component> components = graph.getComponents();

		double numNodes = percentage * graph.getSize();
		int[] componentSizes = new int[components.size()];

		for(int i = 0; i < components.size(); i++){
			componentSizes[i] = components.get(i).getNumberOfUsers();
		}

		HashMap<Integer, ArrayList<Component>> map = new HashMap<Integer, ArrayList<Component>>();

		allSums(components,map, 0, new ArrayList<Component>(), (int)numNodes);

		Set<Integer> allDistances = map.keySet();
		Integer min = Integer.MAX_VALUE;

		Iterator iter = allDistances.iterator();
		while(iter.hasNext()){
			Integer thisInt = (Integer)iter.next();
			if(thisInt < min){
				min = thisInt;
			}
		}

		ArrayList<Component> componentList = map.get(min);
		int totalNodes = 0;

		for(Component c : componentList){
			totalNodes += c.getNumberOfUsers();

			ArrayList<UserNode> componentUsers = c.getUsers();
			totalInfection(componentUsers.get(0), graph, version);
		}

		System.out.println("Just infected " + totalNodes + " of the required " + numNodes );

		return totalNodes;
	}

	public static void allSums(ArrayList<Component> components, HashMap<Integer, ArrayList<Component>> map, int index, ArrayList<Component> currSum, int value){
		int thisSum = 0;
		ArrayList<Component> nextSum = new ArrayList<Component>();

		if(index == components.size()){

			//Add the last sum array to the map
			for(Component c: currSum){
				thisSum += c.getNumberOfUsers();
				nextSum.add(c);
			}

			Integer distance = new Integer(Math.abs(thisSum - value));
			map.put(distance, currSum);

			return;
		}


		for(Component c: currSum){
			thisSum += c.getNumberOfUsers();
			nextSum.add(c);
		}

		//add the last value to nextSum
		nextSum.add(components.get(index));


		Integer distance = new Integer(Math.abs(thisSum - value));

		//put the distance value and the array of sums 

		map.put(distance, currSum);

		allSums(components, map, index + 1, currSum, value);
		allSums(components,map, index + 1, nextSum, value);

	}

	//Given a user infect all other users that they are connected to
	public static void totalInfection(UserNode user, Graph graph, String version){
		HashMap<String, UserNode> allUsers = graph.getUsers();
		LinkedList<UserNode> queue = new LinkedList<UserNode>();
		HashSet<String> visited = new HashSet<String>();

		queue.add(user);

		while(queue.peekFirst() != null){
			UserNode curr = queue.pollFirst();
			curr.setVersion(version);
			allUsers.put(curr.getUsername(), curr);

			if(!visited.contains(curr)){
				visited.add(curr.getUsername());
			}

			ArrayList<String> neighbors = curr.getNeighbors();
			for(String s : neighbors){
				if(!visited.contains(s)){
					UserNode neighbor = allUsers.get(s);
					queue.add(neighbor);
				}
			}

		}
	}

}