package resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import external.resources.TextResource;

public class PC_TextResource extends TextResource{
	File f;
	String loc;
	BufferedReader reader;
	public PC_TextResource(String loc) throws FileNotFoundException {
		f=new File(loc);
		this.loc=loc;
		reader=new BufferedReader(new FileReader(f));
	}

	@Override
	public BufferedReader getReader() {
		
		return reader;
	}

	@Override
	public String getLocation() {
		// TODO Auto-generated method stub
		return loc;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "text";
	}

}
