package co.syscoop.soberano.renderers;

public enum ActionRequested
{
	NONE(0),
	DISABLE(1);
	
    @SuppressWarnings("unused")
	private int actionCode;
    ActionRequested(int actionCode) {this.actionCode = actionCode;}
}