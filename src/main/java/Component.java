import java.util.*;


public class Component{

	private ArrayList<UserNode> users;
	private int size;

	public Component(){
		this.users = new ArrayList<UserNode>();
	}

	public Component(ArrayList<UserNode> users){
		this.users = users;
	}

	public Component(Component c){
		this.users = c.getUsers();
		this.size = c.getNumberOfUsers();
	} 

	public boolean addUser(UserNode u){
		if(!this.users.contains(u)){
			this.users.add(u);
			size++;
			return true;
		}

		return false;
	}

	public boolean removeUser(UserNode u){
		if(this.users.contains(u)){
			this.users.remove(u);
			size--;
			return true;
		}

		return false;
	}

	public boolean containsUser(UserNode u){
		return this.users.contains(u);
	}

	public ArrayList<UserNode> getUsers(){
		return new ArrayList<UserNode>(this.users);
	}

	public int getNumberOfUsers(){
		return this.size;
	}

	public String toString(){
		String toReturn = "Component: ";

		for(UserNode u : this.users){
			toReturn = toReturn + "\n" + u.toString();
		}
		return toReturn;
	}
}