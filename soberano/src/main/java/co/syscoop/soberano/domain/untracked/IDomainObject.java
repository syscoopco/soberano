package co.syscoop.soberano.domain.untracked;

import java.sql.SQLException;
import java.util.List;

public interface IDomainObject {

	abstract public List<DomainObject> getAll(Boolean stringId) throws SQLException;
}
