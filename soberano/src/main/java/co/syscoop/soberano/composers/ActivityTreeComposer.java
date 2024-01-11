package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import co.syscoop.soberano.models.ActivityTreeModelPopulator;

@SuppressWarnings("serial")
public class ActivityTreeComposer extends GenericForwardComposer<Window> {
	
	private Tree treeActivity;
	
	@Override
	public void doAfterCompose(Window div) throws Exception{
		
		super.doAfterCompose(div);		
		ActivityTreeModelPopulator.rerenderActivityTree(treeActivity);
	}
}
