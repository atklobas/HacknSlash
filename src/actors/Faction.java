package actors;

public class Faction {
	private String name;
	public Faction(String name){
		this.name=name;
	}
	public String toString(){
		return name;
	}
	public boolean equals(Object o){
		if(o instanceof Faction){
			return ((Faction)o).name.equals(name);
		}
		return false;
	}

}
