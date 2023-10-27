package co.syscoop.soberano.renderers;

public enum ActionRequested
{
	NONE(0),
	DISABLE(1),
	RECORD(2),
	CANCEL(3),
	CLOSE(4),
	SOME(5);
	
    @SuppressWarnings("unused")
	private int actionCode;
    ActionRequested(int actionCode) {this.actionCode = actionCode;}
}