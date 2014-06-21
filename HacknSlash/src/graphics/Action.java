package graphics;

public enum Action {
	DEFAULT,//will point to standing
	STANDING,
	MOVING,
	SWINGING,
	CASTING,
	DYING;

	public static Action fromString(String next) {
		switch(next){
		case "MOVING": return MOVING;
		case "STANDING": return STANDING;
		}
		return MOVING;
	}
}
