package co.syscoop.soberano.beans;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.*;

public class SoberanoDatasource {

	private BasicDataSource dataSource;
	private PlatformTransactionManager transactionManager = null;
	private TransactionDefinition transactionDefinition = null;
	
	public SoberanoDatasource(BasicDataSource dataSource,
			PlatformTransactionManager transactionManager,
			TransactionDefinition transactionDefinition) {
		this.dataSource = dataSource;
		this.transactionManager = transactionManager;
		this.transactionDefinition = transactionDefinition;
		((DataSourceTransactionManager) transactionManager).setDataSource(dataSource);
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
		
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public TransactionDefinition getTransactionDefinition() {
		return transactionDefinition;
	}

	public void setTransactionDefinition(TransactionDefinition transactionDefinition) {
		this.transactionDefinition = transactionDefinition;
	}
}
