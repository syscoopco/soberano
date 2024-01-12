package co.syscoop.soberano.renderers;

import java.math.BigDecimal;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import co.syscoop.soberano.domain.tracked.Product;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.CatalogEntryRowData;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

public class CatalogTreeNodeRenderer implements TreeitemRenderer<DefaultTreeNode<NodeData>> {

	@SuppressWarnings({"unchecked", "rawtypes" })
	@Override
	public void render(Treeitem item, DefaultTreeNode<NodeData> data, int index) throws Exception {
		
		Treerow tr = new Treerow();
		item.appendChild(tr);
		
		//category node
		if (data.getData().getValue() == null) {			
			tr.appendChild(new Treecell(data.getData().getLabel()));
		}
		//item node
		else {
			CatalogEntryRowData catalogEntryRowData = (CatalogEntryRowData) (data.getData().getValue());
			Treecell tc = new Treecell();
			Checkbox chkItemEnabled = new Checkbox(catalogEntryRowData.getItemName());
			chkItemEnabled.setChecked(catalogEntryRowData.getItemEnabled());
			tc.appendChild(chkItemEnabled);
			
			//item price
			Decimalbox decPrice = new Decimalbox(catalogEntryRowData.getItemPrice());
			decPrice.setId("decPrice" + catalogEntryRowData.getItemId().toString());
			decPrice.setConstraint("no negative, no empty");
			decPrice.addEventListener("onChange", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					
					try{
						Decimalbox decPrice = (Decimalbox) event.getTarget();
						
						//update the reference price
						Decimalbox decRefPrice = (Decimalbox) decPrice.query("decRefPrice" + catalogEntryRowData.getItemId().toString());
						
						//update the price of the item
						BigDecimal refPrice = (new Product(catalogEntryRowData.getItemId())).setItemPrice(decPrice.getValue());						
						if (refPrice.compareTo(new BigDecimal(-1)) == 0) {
							throw new NotEnoughRightsException();
						}
						else
							decRefPrice.setValue(refPrice);
					}
					catch(NotEnoughRightsException ex) {
						ExceptionTreatment.logAndShow(ex, 
								Labels.getLabel("message.permissions.NotEnoughRights"), 
								Labels.getLabel("messageBoxTitle.Warning"),
								Messagebox.EXCLAMATION);
					}
					catch(Exception ex) {
						ExceptionTreatment.logAndShow(ex, 
								ex.getMessage(), 
								Labels.getLabel("messageBoxTitle.Error"),
								Messagebox.ERROR);
					}
				}			
			});
			tc.appendChild(decPrice);
			
			//reference price
			tc.appendChild(new Separator("vertical"));
			tc.appendChild(new Label(catalogEntryRowData.getRefCurrency().toString()));		
			Decimalbox decRefPrice = new Decimalbox(catalogEntryRowData.getItemReferencePrice());
			decRefPrice.setConstraint("no negative, no empty");
			decRefPrice.addEventListener("onChange", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					
					try{
						Decimalbox decRefPrice = (Decimalbox) event.getTarget();
						
						//update the official price
						Decimalbox decPrice = (Decimalbox) decRefPrice.query("decPrice" + catalogEntryRowData.getItemId().toString());
												
						//update the price of the item
						BigDecimal itemPrice = (new Product(catalogEntryRowData.getItemId())).setItemReferencePrice(decRefPrice.getValue());	
						if (itemPrice.compareTo(new BigDecimal(-1)) == 0) {
							throw new NotEnoughRightsException();
						}
						else
							decPrice.setValue(itemPrice);
					}
					catch(NotEnoughRightsException ex) {
						ExceptionTreatment.logAndShow(ex, 
								Labels.getLabel("message.permissions.NotEnoughRights"), 
								Labels.getLabel("messageBoxTitle.Warning"),
								Messagebox.EXCLAMATION);
					}
					catch(Exception ex) {
						ExceptionTreatment.logAndShow(ex, 
								ex.getMessage(), 
								Labels.getLabel("messageBoxTitle.Error"),
								Messagebox.ERROR);
					}
				}			
			});
			tc.appendChild(decRefPrice);
			
			//enable / disable item
			tr.appendChild(tc);
			chkItemEnabled.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					
					try {
						Checkbox chkItemEnabled = (Checkbox) event.getTarget();
						if (chkItemEnabled.isChecked()) {
							Integer result = (new Product(catalogEntryRowData.getItemId())).showInCatalog();					
							if (result == -1) {
								throw new NotEnoughRightsException();
							}
						}
						else {
							Integer result = (new Product(catalogEntryRowData.getItemId())).hideInCatalog();				
							if (result == -1) {
								throw new NotEnoughRightsException();
							}
						}
					}
					catch(NotEnoughRightsException ex) {
						ExceptionTreatment.logAndShow(ex, 
								Labels.getLabel("message.permissions.NotEnoughRights"), 
								Labels.getLabel("messageBoxTitle.Warning"),
								Messagebox.EXCLAMATION);
					}
					catch(Exception ex) {
						ExceptionTreatment.logAndShow(ex, 
								ex.getMessage(), 
								Labels.getLabel("messageBoxTitle.Error"),
								Messagebox.ERROR);
					}
				}
			});
		}
	}
}
