package co.syscoop.soberano.rules.enforcer.generator;

/**
 * @author Josue Portal
 *
 * Just an array of query (script) strings to be intendedly executed on a generated logical model, in the order they are stored.
 * It's thought to be executed after a logical model is generated, as a post generation task. For example, to implement the
 * derived fact types, to populate the metamodel logical implementation, to populate the model of a Soberano instance (case),
 * and so forth.
 */
public class LogicalQueriesBatch {
 
	private String[] batch = null;

	public String[] getBatch() {
		return batch;
	}

	public void setBatch(String[] batch) {
		this.batch = batch;
	}
}
