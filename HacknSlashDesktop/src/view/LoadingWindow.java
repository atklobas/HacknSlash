package view;

import interaction.Event;
import interaction.LoadingEvent;
import interaction.Observer;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class LoadingWindow  extends JFrame implements Observer{
	JProgressBar bar=new JProgressBar(0,1000);
	JLabel info=new JLabel();
	public LoadingWindow(){
		super("Loading ...");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setLayout(new GridLayout(2,1));
		info.setText("Loading..");
		this.add(info);
		this.add(bar);
		this.pack();
		this.setLocation((screenSize.width-this.getWidth())/2, (screenSize.height-this.getHeight())/2);
		this.setVisible(true);
	}
	public void update(int progress, String description){
		bar.setValue(progress);
		if(description!=null){
			info.setText(description);
		}
		this.repaint();
	}
	@Override
	public void event(Event e) {
		if(e instanceof LoadingEvent){
			update(((LoadingEvent) e).getProgress(), ((LoadingEvent) e).getDescription());
		}
		
	}

}
