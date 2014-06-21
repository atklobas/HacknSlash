package graphics;

public enum Direction {
	NORTH,
	NORTH_EAST,
	EAST,
	SOUTH_EAST,
	SOUTH,
	SOUTH_WEST,
	WEST,
	NORTH_WEST;
	public static Direction fromString(String s){
		switch(s){
		case "NORTH":return NORTH;
		case "NORTH_EAST":return NORTH_EAST;
		case "EAST":return EAST;
		case "SOUTH_EAST":return SOUTH_EAST;
		case "SOUTH":return SOUTH;
		case "SOUTH_WEST":return SOUTH_WEST;
		case "WEST":return WEST;
		case "NORTH_WEST":return NORTH_WEST;
		}
		return SOUTH;
	}
}
