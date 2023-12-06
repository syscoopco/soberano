package co.syscoop.soberano.enums;

public enum Stage
{
	CLOSED_NOT_COLLECTED(0),
	RESERVED1(1),
	ENABLED(2),
	ONGOING(3),
	RECORDED(4),
	CANCELED(5),
	CLOSED(6),
	HONORED(7),
	DISABLED(8),
	DISHONORED(9);
	
	Integer stageCode;

    Stage( Integer stageCode ) { this.stageCode = stageCode; }

    public Integer getStageCode() { return stageCode; }
}