import java.util.ArrayList;

public class UserNode {

	private ArrayList<String> neighbors;
	private String username;
	private String version;

	public UserNode(String username){
		this.username = username;
		this.neighbors = new ArrayList<String>();
		this.version = "A";
	}

	public void infect(){
		this.version = "B";
	}

	public void addNeighbor(String username){

		if(!this.neighbors.contains(username)){
			this.neighbors.add(username);
		}

		return;
	}

	public void addNeighbor(UserNode u){

		if(!this.neighbors.contains(u.getUsername())){
			this.neighbors.add(u.getUsername());
		}

		return;
	}

	public void deleteNeighbor(String username){

		if(this.neighbors.contains(username)){
			this.neighbors.remove(username);
		}

		return;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getVersion(){
		return this.version;
	}

	public String getNeighbor(int index){
		return this.neighbors.get(index);
	}

	public ArrayList<String> getNeighbors(){
		return new ArrayList<String>(this.neighbors);
	}

	public boolean containsNeighbor(String username){
		return this.neighbors.contains(username);
	}

	public int getNeighborCount(){
		return this.neighbors.size();
	}

	public String getUsername(){
		return this.username;
	}

	public String toString(){
		return "UserNode: " + this.username + " , Version: " + this.version;
	}
}