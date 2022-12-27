package co.syscoop.soberano.initialization;

import java.util.ArrayList;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.SessionInit;

public class SoberanoSessionInit implements SessionInit {

	private ArrayList<String> colors = new ArrayList<String>();

	@Override
	public void init(Session sess, Object request) throws Exception {
		
		//assign a color to each desk
		colors.add("aqua");
		colors.add("black");
		colors.add("blue");
		colors.add("fuchsia");
		colors.add("gray");
		colors.add("green");
		colors.add("lime");
		colors.add("maroon");
		colors.add("navy");
		colors.add("olive");
		colors.add("purple");
		colors.add("red");
		colors.add("silver");
		colors.add("teal");
		colors.add("white");
		colors.add("orange");
		
		//TODO: session initialization
	}	
}
