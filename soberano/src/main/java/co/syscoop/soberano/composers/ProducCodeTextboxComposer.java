package co.syscoop.soberano.composers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.SpringUtility;

import co.syscoop.soberano.util.GTINDatabaseHelper;
import co.syscoop.soberano.util.GoUPCScraper;

@SuppressWarnings({ "serial", "rawtypes" })
public class ProducCodeTextboxComposer extends SelectorComposer {
	
	@Wire
	private Textbox txtCode;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }

	@Listen("onChanging = textbox#txtCode")
	public void txtCode_onChanging(Event event) throws Exception {
		
		
		
		String typedOrScannedCode = ((InputEvent) event).getValue();
		if (typedOrScannedCode.length() >= 8) {
			
			String productName = null;
			
			try {
				productName = GoUPCScraper.getProductDetails(typedOrScannedCode);
			}
			catch(Exception ex) {
				productName = null;
			} 
			
			if (productName == null) {
				
				String curDir = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath());
				String GTINDBFileFolderAbsolutePathInJavaPropertiesFile = (String) Executions.getCurrent().
																							getDesktop().
																							getWebApp().
																							getAttribute("gtin_database_file_path");
				
				String databaseFilePath = GTINDBFileFolderAbsolutePathInJavaPropertiesFile.isEmpty() ? 
						curDir + "../../gtin.db" : 
							GTINDBFileFolderAbsolutePathInJavaPropertiesFile + "gtin.db";
				
				try (Connection conn = GTINDatabaseHelper.getConnection(databaseFilePath)) {
			        if (conn == null) {
			            // No database file – skip lookup, user must type manually
			            return;
			        }
			        
			        String sql = "SELECT * FROM main.products WHERE gtin = ?;";
			        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			            stmt.setString(1, typedOrScannedCode.trim());
			            ResultSet rs = stmt.executeQuery();
			            
			            if (rs.next()) {
			            	productName = rs.getString("name");
			            }
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
				catch (Exception e) {
			        e.printStackTrace();
			    }
			}
			((Textbox) txtCode.query("#txtName")).setValue(productName == null ? "" : productName);
		}
	}
}
