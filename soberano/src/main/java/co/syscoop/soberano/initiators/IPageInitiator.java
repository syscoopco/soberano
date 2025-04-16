package co.syscoop.soberano.initiators;

import org.zkoss.zk.ui.Page;

public interface IPageInitiator {

	//Guarantee that the correct page is loaded, depending of the type of device (desktop or mobile).
	public abstract void goToCorrectSite(Page arg0);
}
