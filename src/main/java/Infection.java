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
    		double percentBadUsers = 0;

  			String infection = request.getParameter("infection");
  			String percent = request.getParameter("percentage");

    		try { 

    			JSONObject graphFile = (JSONObject) parser.parse(new FileReader("./src/main/java/TheGraph.json"));
    			JSONArray nodes = (JSONArray) graphFile.get("nodes");
    			JSONArray links = (JSONArray) graphFile.get("links");

    			Graph thisGraph = new Graph(nodes, links);

    			if(infection != null && percent != null && !percent.equals("")){

    				double prcnt = (Double.parseDouble(percent))/100.0;

	    			if(infection.equals("Limited")){
	    				int numInfected = limitedInfection(thisGraph, "B", prcnt);
	    				percentInfected = Math.round(((double)numInfected/(double)thisGraph.getSize()) * 100.0);
	    				percentBadUsers = Math.round(((double)thisGraph.upsetUsers()/(double)thisGraph.getSize()) * 100.0);
	    				request.setAttribute("percentInfected",(int)percentInfected);
	    				request.setAttribute("percentBadUsers",(int)percentBadUsers);
	    				request.setAttribute("limitedChecked", "selected");
	    				request.setAttribute("exactChecked", "");
	    			} else{
	    				int numInfected = exactInfection(thisGraph, "B", prcnt);
	    				percentInfected = Math.round(((double)numInfected/(double)thisGraph.getSize()) * 100.0);
	    				percentBadUsers = Math.round(((double)thisGraph.upsetUsers()/(double)thisGraph.getSize()) * 100.0);
	    				request.setAttribute("percentInfected",(int)percentInfected);
	    				request.setAttribute("percentBadUsers",(int)percentBadUsers);
	    				request.setAttribute("limitedChecked", "");
	    				request.setAttribute("exactChecked", "selected");
	    			}
    			}

    			request.setAttribute("graph", thisGraph.toJSONString());

    		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    		String title = "Enter a percentage to get started";
    		String results = "";


    		if(percent != null && infection != null && !percent.equals("")){
    			int percentInt = Integer.parseInt(percent);

    			if(percentInt > 100 || percentInt < 0){
    				title = "Please enter a valid percentage to get started";
    			} else {
	    			title = "" + infection + " Infection on " + percent + "% of users";
	    			results = "" + request.getAttribute("percentInfected") + "% of users have been infected, " + request.getAttribute("percentBadUsers") + "% of users at risk of bad UX.";
    			}
    		}


    		request.setAttribute("percentage", percent);
    		request.setAttribute("infection", infection);
    		request.setAttribute("title", title);
    		request.setAttribute("results", results);

		    RequestDispatcher view=request.getRequestDispatcher("./index.jsp");
		    view.forward(request,response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
 	}

	//This method will infect the exact number of users requested, optimizing for the lowest number of edges between
	//infected and uninfected nodes
	public static int exactInfection(Graph graph, String version, double percentage){

		if(percentage < 0.0 || percentage > 1.0){
			System.out.println("Invalid percentage");
			return -1;
		}

		int infected = 0; 

		ArrayList<Component> components = graph.getComponents();

		double numNodes = Math.round(percentage * graph.getSize());
		int[] componentSizes = new int[components.size()];

		for(int i = 0; i < components.size(); i++){
			componentSizes[i] = components.get(i).getNumberOfUsers();
		}

		HashMap<Integer, ArrayList<Component>> map = new HashMap<Integer, ArrayList<Component>>();

		//calculate all possible combinations of sums of components, see which combination gets us closest to our value

		allPositiveSums(components,map, 0, new ArrayList<Component>(), (int)numNodes);


		Set<Integer> allDistances = map.keySet();
		Integer min = Integer.MAX_VALUE;

		Iterator iter = allDistances.iterator();

		while(iter.hasNext()){
			Integer thisInt = (Integer)iter.next();
			if(thisInt > 0){
				if(thisInt < min){
					min = thisInt;
				}
			}
		}

		ArrayList<Component> componentList = map.get(min);

		for(Component c : componentList){
			infected += c.getNumberOfUsers();

			ArrayList<UserNode> componentUsers = c.getUsers();
			totalInfection(componentUsers.get(0), graph, version);
		}

		double toInfect = numNodes - infected;

		//if we have already reached our value then no need to continue
		if(toInfect == 0.0){
			return infected;
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
			infected++;
		}

		return infected;
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

		if(percentage < 0.0 || percentage > 1.0){
			System.out.println("Invalid percentage");
			return -1;
		}

		ArrayList<Component> components = graph.getComponents();

		double numNodes = Math.round(percentage * graph.getSize());
		int[] componentSizes = new int[components.size()];

		for(int i = 0; i < components.size(); i++){
			componentSizes[i] = components.get(i).getNumberOfUsers();
		}

		HashMap<Integer, ArrayList<Component>> map = new HashMap<Integer, ArrayList<Component>>();

		allSums(components,map, 0, new ArrayList<Component>(), (int)Math.round(numNodes));

		Set<Integer> allDistances = map.keySet();
		Integer min = Integer.MAX_VALUE;
		Integer minkey = new Integer(0);


		//using absolute value because we want to get as close as possible to value
		//we dont care if we have more or less infected nodes
		Iterator iter = allDistances.iterator();
		while(iter.hasNext()){
			Integer thisInt = (Integer)iter.next();
			if(Math.abs(thisInt) < min){
				min = Math.abs(thisInt);
				minkey = thisInt;
			}
		}

		ArrayList<Component> componentList = map.get(minkey);
		int totalNodes = 0;

		for(Component c : componentList){
			totalNodes += c.getNumberOfUsers();

			ArrayList<UserNode> componentUsers = c.getUsers();
			totalInfection(componentUsers.get(0), graph, version);
		}

		return totalNodes;
	}

	public static void allSums(ArrayList<Component> components, HashMap<Integer, ArrayList<Component>> map, int index, ArrayList<Component> currSum, int value){
		
		ArrayList<Component> nextSum = new ArrayList<Component>();

		if(index == components.size()){
			int thisSum = 0;
			//Add the sum array to the map
			for(Component c: currSum){
				thisSum += c.getNumberOfUsers();
			}

			Integer distance = new Integer(value - thisSum);
			map.put(distance, currSum);

			return;
		}

		//make next sum
		for(Component c: currSum){
			nextSum.add(c);
		}

		//add the next index value to next sum
		nextSum.add(components.get(index));

		allSums(components, map, index + 1, currSum, value);
		allSums(components,map, index + 1, nextSum, value);

	}

	//very similar to allSums but has better performance because we only want sums that are less than or equal to 
	//the value. This means we return out of recursive stacks when the sum exceeds our desired value
	public static void allPositiveSums(ArrayList<Component> components, HashMap<Integer, ArrayList<Component>> map, int index, ArrayList<Component> currSum, int value){
		
		ArrayList<Component> nextSum = new ArrayList<Component>();
		int thisSum = 0;

		if(index == components.size()){
			//Add the sum array to the map
			for(Component c: currSum){
				thisSum += c.getNumberOfUsers();
			}

			Integer distance = new Integer(value - thisSum);
			map.put(distance, currSum);

			return;
		}

		//make next sum
		for(Component c: currSum){
			nextSum.add(c);
			thisSum += c.getNumberOfUsers();
		}

		thisSum += components.get(index).getNumberOfUsers();

		if(thisSum > value){
			//if the sum of nodes in our component list is greater than the number of nodes we want to infect then stop this recursive stack
			return;
		}
		//add the next index value to next sum
		nextSum.add(components.get(index));

		allSums(components, map, index + 1, currSum, value);
		allSums(components,map, index + 1, nextSum, value);

	}

	//Given a user infect all other users that they are connected to
	//Essentially BFS
	public static void totalInfection(UserNode user, Graph graph, String version){
		HashMap<String, UserNode> allUsers = graph.getUsers();
		LinkedList<UserNode> queue = new LinkedList<UserNode>();
		HashSet<String> visited = new HashSet<String>();

		queue.add(user);

		while(queue.peekFirst() != null){
			UserNode curr = queue.pollFirst();
			curr.setVersion(version);
			allUsers.put(curr.getUsername(), curr);
			visited.add(curr.getUsername());

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