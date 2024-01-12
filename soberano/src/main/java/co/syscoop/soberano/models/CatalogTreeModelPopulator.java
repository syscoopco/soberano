package co.syscoop.soberano.models;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeansException;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;

import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.util.CatalogEntryRowData;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.domain.tracked.CatalogEntry;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.renderers.CatalogTreeNodeRenderer;

public class CatalogTreeModelPopulator {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static TreeModel populateTreeModel() throws BeansException, SQLException {
		
		List<Object> catalogEntries = (new CatalogEntry()).getAll();		
		String currentCategory = "";
		TreeNode categoryNode = null;
		TreeNode rootNode = new DefaultTreeNode(null, (TreeNode[]) null);
		
		for (Object catalogEntryObject : catalogEntries) {			
			CatalogEntryRowData catalogEntry = (CatalogEntryRowData) catalogEntryObject;			
			String itemName = catalogEntry.getItemName(); 
			TreeNode itemNode = new DefaultTreeNode(new NodeData(itemName, catalogEntry));
			if (!currentCategory.equals(catalogEntry.getCategoryName())) {
				currentCategory = catalogEntry.getCategoryName();
				categoryNode = new DefaultTreeNode(new NodeData(currentCategory, null), (TreeNode[]) null);
				rootNode.add(categoryNode);
			}
			categoryNode.add(itemNode);
		} return new DefaultTreeModel(rootNode);
	};
	
	public static void rerenderCatalogTree(Tree treeCatalog) {
		
		try{
			treeCatalog.setModel(CatalogTreeModelPopulator.populateTreeModel());
			treeCatalog.setItemRenderer(new CatalogTreeNodeRenderer());
		}
		catch(Exception ex){
			try {
				ExceptionTreatment.logAndShow(ex, 
						ex.getMessage(), 
						Labels.getLabel("messageBoxTitle.Error"),
						Messagebox.ERROR);
			} catch (SoberanoException e) {
				e.printStackTrace();
				e.fillInStackTrace();
			}
  		}
	}
}
