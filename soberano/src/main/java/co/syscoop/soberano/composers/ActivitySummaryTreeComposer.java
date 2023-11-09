package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import co.syscoop.soberano.models.ActivitySummaryTreeModelPopulator;

@SuppressWarnings("serial")
public class ActivitySummaryTreeComposer extends GenericForwardComposer<Window> {
	
	private Tree treeActivitySummary;
	
	@Override
	public void doAfterCompose(Window div) throws Exception{
		
		super.doAfterCompose(div);		
		ActivitySummaryTreeModelPopulator.rerenderActivityTree(treeActivitySummary);
	}
}
