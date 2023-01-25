package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.List;

import co.syscoop.soberano.domain.untracked.DomainObject;

public interface ITrackedObject {

	abstract public Integer record() throws SQLException, Exception;
	abstract public void get() throws SQLException;
	abstract public Integer modify() throws SQLException, Exception;	
	abstract public Integer makeDecision(String decision) throws SQLException; //it's passed the name of the decision
	abstract public Integer disable() throws SQLException, Exception;
	abstract public Integer print() throws SQLException;
	
	//limit and offset are useful for windowed lists, as in paged lists / grids / scrollable trees / etc.
	//the semantics is the typical, for sql standard.
	//passing negatives values discard them.
	abstract public List<TrackedObject> getSet(String criteria, Integer limit, Integer offset) throws SQLException;
	abstract public List<DomainObject> getAll(Boolean stringId) throws SQLException;
}
