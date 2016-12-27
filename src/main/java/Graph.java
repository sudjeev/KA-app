import java.util.*;
import org.json.simple.*;


public class Graph{
	
	private HashMap<String, UserNode> users;

	public Graph(){
		this.users = new HashMap<String, UserNode>();
	}

	public Graph(ArrayList<UserNode> allUsers){
		this.users = new HashMap<String, UserNode>();

		for(UserNode u: allUsers){
			this.users.put(u.getUsername(), u);
		}
	}

	public Graph(JSONArray nodes, JSONArray links){
		this.users = new HashMap<String, UserNode>();

		Iterator one = nodes.iterator();

		while(one.hasNext()){
			JSONObject thisObj = (JSONObject) one.next();
			UserNode thisUser = new UserNode((String)thisObj.get("id"));
			this.users.put(thisUser.getUsername(), thisUser);
		}

		Iterator two = links.iterator();

		while(two.hasNext()){
			JSONObject thisLink = (JSONObject) two.next();

			UserNode userOne = this.users.get((String)thisLink.get("source"));
			UserNode userTwo = this.users.get((String)thisLink.get("target"));

			if(userOne != null && userTwo != null){
				userOne.addNeighbor(userTwo);
				userTwo.addNeighbor(userOne);
				this.users.put(userOne.getUsername(), userOne);
				this.users.put(userTwo.getUsername(), userTwo);
			}
		}
	}

	public String toString(){
		String toPrint = "Graph:  ";

		for(UserNode u : users.values()){
			toPrint = toPrint + "\n" + u.toString();
		}

		return toPrint;
	}

	public int getSize(){
		return this.users.size();
	}

	//function that returns the number of users who are 'upset' because one or many of their neighbors has a version
	//of the site that they do not
	public int upsetUsers(){

		HashSet<String> counted = new HashSet<String>();
		int upset = 0;

		for(UserNode u : users.values()){
			String thisVersion = u.getVersion();

			for(String n : u.getNeighbors()){
				UserNode neighbor = users.get(n);
				String neighborVersion = neighbor.getVersion();

				String firstHash = u.getUsername() + n;
				String secondHash = n + u.getUsername();

				if(!thisVersion.equals(neighborVersion)){
					if(!counted.contains(firstHash) && !counted.contains(secondHash)){
						upset++;
						counted.add(firstHash);
						counted.add(secondHash);
					}
				}
			}
		}

		return upset;
	}

	public ArrayList<Component> getComponents(){
		//take all the user nodes and do BFS to find out each component and how many in that component, put it in a tuple
		HashSet<String> visited = new HashSet<String>();

		ArrayList<Component> allComponents = new ArrayList<Component>();

		for(UserNode u: users.values()){

			if(!visited.contains(u.getUsername())){

				Component nextComponent = new Component();

				LinkedList<UserNode> queue = new LinkedList<UserNode>();
				queue.add(u);

				//while there are elements in the queue
				while(queue.peekFirst() != null){
					//get the first element
					UserNode currUser = queue.pollFirst();

					//if we havnt visited it yet then add it to visited and add it to the component obj
					if(!visited.contains(currUser.getUsername())){
						nextComponent.addUser(currUser);
						visited.add(currUser.getUsername());
					}

					//get all the neighbors for the node
					ArrayList<String> neighbors = currUser.getNeighbors();

					for(String s : neighbors){
						//for each neighbor if we havnt visited it yet then add it to the queue
						if(!visited.contains(s)){
							queue.add(this.users.get(s));
						}
					}

				}

				allComponents.add(nextComponent);
			}
		}

		return allComponents;
	}

	public String toJSONString(){
		//nodes: {"id": "1", "group": 2}
		//links: {"source": "1", "target": "2"}
		//{nodes: [], links: []}
		JSONArray nodes = new JSONArray();
		JSONArray links = new JSONArray();

		for(UserNode u : users.values()){
			JSONObject thisUser = new JSONObject();
			String username = u.getUsername();
			thisUser.put("id", username);
			thisUser.put("group", u.getVersion());
			nodes.add(thisUser);

			for(String n : u.getNeighbors()){
				JSONObject thisLink = new JSONObject();
				thisLink.put("source", username);
				thisLink.put("target", n);
				links.add(thisLink);
			}

		}

		JSONObject thisGraph = new JSONObject();
		thisGraph.put("nodes", nodes);
		thisGraph.put("links", links);
		return thisGraph.toJSONString();
	}

	// //This function takes a node and 'infects' all other nodes it is connected to. The process of infecting a node is essentially just
	// //changing the node's version to be B
	// public Component totallyInfect(UserNode u){

	// }

	public HashMap<String, UserNode> getUsers(){
		return new HashMap<String, UserNode>(this.users);
	}

	public boolean hasUser(String username){
		return this.users.containsKey(username);
	}

}