package interaction;

public class LoadingEvent implements Event{
	int progress;
	String desc;
	public LoadingEvent(int progress, String desc){
		this.progress=progress;
		this.desc=desc;
	}
	public int getProgress(){
		return this.progress;
	}
	public String getDescription(){
		return desc;
	}
}
