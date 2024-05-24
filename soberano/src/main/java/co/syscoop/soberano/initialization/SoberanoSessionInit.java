package co.syscoop.soberano.initialization;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.SessionInit;

import co.syscoop.soberano.domain.tracked.Counter;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;

public class SoberanoSessionInit implements SessionInit {

	private ArrayList<String> colors = new ArrayList<String>();

	@Override
	public void init(Session sess, Object request) throws Exception {
		
		//assign a color to each counter
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
		
		try {
			List<DomainObject> counters = (new Counter()).getAll(false);			
			if (counters.size() > 0) {
				int counterCount = 0;
				int colorCount = 0;
				for(Object counter : counters) {
					sess.setAttribute("color_counter_" + ((DomainObject) counter).getId(), colors.get(colorCount));
					counterCount++;
					if (counterCount > counters.size()) {
						break;
					}
					else {
						colorCount++;
						if (colorCount > colors.size()) {
							colorCount = 0;
						}
					}
				}
			}			
		}
		catch(Exception ex) {
			ExceptionTreatment.log(ex);
		}
		
		sess.setAttribute("production_line_board_grid_order_column", "allocationId");
		sess.setAttribute("production_line_board_grid_sort_direction", "ASC");
	}	
}
