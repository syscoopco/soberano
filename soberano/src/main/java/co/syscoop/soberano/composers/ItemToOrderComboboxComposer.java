package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Span;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.tracked.Product;
import co.syscoop.soberano.domain.tracked.ProductCategory;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.util.BarcodeValidator;
import co.syscoop.soberano.util.Utils;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings("serial")
public class ItemToOrderComboboxComposer extends ViewModelComposer {
	
	private static void additionButtonHandler(Button button) {
		
		if (button.getWidth().equals("100px")) {
			button.setWidth("97px");
			button.setHeight("97px");
			button.setStyle("border: 5px; border-style: inset; margin-left: 3px; margin-top: 3px;");
			button.setAttribute("checked", true);
		}
		else {
			button.setWidth("100px");
			button.setHeight("100px");
			button.setStyle("margin-left: 3px; margin-top: 3px;");
			button.setAttribute("checked", false);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void productButtonHandler(Integer orderId, Integer productId, String productName, Window spanProductsParentWindow) throws Exception {
		
		Window window = (Window) Executions.createComponents("./complete_addition.zul", spanProductsParentWindow, null);
		window.setAttribute("orderId", orderId);		
		window.setTitle(Labels.getLabel("pageOrder.howMany") + 
										" " + 
										productName + 
										" " + 
										Labels.getLabel("pageOrder.for") +
										" " +
									orderId);
		//window.setWidth("80%");
		//window.setHeight("80%");
		
		window.setWidth("100%");
		window.setHeight("100%");
		
		Product product = (Product) (new Product(productId)).getWithUnitForOrder().get(0);	
		Combobox cmbItemToOrder = (Combobox) window.query("combobox");
		Comboitem cmbItem = new Comboitem(productName);		
		cmbItem.setValue(product);
		cmbItemToOrder.getChildren().add(cmbItem);
		cmbItemToOrder.setSelectedIndex(0);
		Events.sendEvent(Events.ON_CHANGE, cmbItemToOrder, null);
		
		(window.query("#btnMakeFromAdditionsWindow")).setAttribute("orderId", orderId);		
		
		Span spanAdditions = (Span) window.query("#spanAdditions");
		for (Object doo : (new Product()).getAdditionsWithUnitsForOrder()) {					
			Button additionButton = new Button();
			additionButton.setStyle("margin-left: 3px; margin-top: 3px;");
			additionButton.setHeight("100px");
			additionButton.setWidth("100px");
			additionButton.setOrient("vertical");
			if (((Product) doo).getPicture() != null) {
				additionButton.setImageContent(Utils.createImageFromBytes(((Product) doo).getPicture(), 80, 80));
				additionButton.setTooltiptext(((Product) doo).getName());
			}
			else
				additionButton.setLabel(((DomainObject) doo).getName());
								
			additionButton.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					
					additionButtonHandler((Button) event.getTarget());
				}
			});
			
			additionButton.setAttribute("productId", ((DomainObject) doo).getId());
			additionButton.setAttribute("checked", false);
			spanAdditions.appendChild(additionButton);
		}
		Order order = new Order(orderId);
		((Textbox) window.query("#txtTicket")).setValue(Translator.translate(order.getReport()));
		
        window.doModal();	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void categoryButtonHandler(Integer categoryId, Integer orderId, Window spanProductsParentWindow) throws Exception {
		
		Span spanProducts = (Span) spanProductsParentWindow.query("#spanProducts");
		spanProducts.getChildren().clear();
		
		Product product = new Product();
		for (Object doo : product.getAll(categoryId)) {					
			Button prodButton = new Button();
			prodButton.setStyle("margin-left: 3px; margin-top: 3px;");
			prodButton.setHeight("100px");
			prodButton.setWidth("100px");
			prodButton.setOrient("vertical");
			if (((Product) doo).getPicture() != null) {
				prodButton.setImageContent(Utils.createImageFromBytes(((Product) doo).getPicture(), 80, 80));
				prodButton.setTooltiptext(((Product) doo).getName());
			}
			else
				prodButton.setLabel(((Product) doo).getName());
			
			prodButton.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					
					Integer productId = ((Product) doo).getId();
					Product product = new Product(productId);
					product.get();
					
					productButtonHandler(orderId, productId, product.getName() + " : " + product.getStringId(), spanProductsParentWindow);
				}
			});
			
			spanProducts.appendChild(prodButton);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public void openFastOrderingWindow(Window wndContentPanel, Integer orderId) {
		
		try {
			Window window = (Window) Executions.createComponents("./add_items.zul", wndContentPanel, null);
			window.setTitle(Labels.getLabel("pageOrder.addItemsToOrder") + " " + orderId);
			
			//window.setWidth("90%");
			//window.setHeight("90%");
			
			window.setWidth("100%");
			window.setHeight("100%");
			
			window.setAttribute("orderId", orderId);
							
			ProductCategory productCategory = new ProductCategory();
			Span spanCategories = (Span) window.query("#spanCategories");
			for (Object doo : productCategory.getAllWithPicture()) {					
				Button catButton = new Button();						
				catButton.setStyle("margin-left: 3px; margin-top: 3px;");
				catButton.setHeight("100px");
				catButton.setWidth("100px");
				catButton.setOrient("vertical");
				if (((ProductCategory) doo).getPicture() != null) {
					catButton.setImageContent(Utils.createImageFromBytes(((ProductCategory) doo).getPicture(), 80, 80));
					catButton.setTooltiptext(((ProductCategory) doo).getName());
				}
				else
					catButton.setLabel(((ProductCategory) doo).getName());
									
				catButton.addEventListener("onClick", new EventListener() {

					@Override
					public void onEvent(Event event) throws Exception {
						
						categoryButtonHandler(((ProductCategory) doo).getId(), orderId, window);
					}
				});
				
				spanCategories.appendChild(catButton);
			}
			
			window.addEventListener("onClose", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					
					Executions.sendRedirect("/order.zul?id=" + orderId);
				}
			});
	        window.doModal();
		}
		catch(Exception ex) {
			
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);        
        comp.addEventListener("onDoubleClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				try {
					Window wndContentPanel = (Window) comp.query("#wndContentPanel");
					Integer orderId = ((Intbox) wndContentPanel.query("#intObjectId")).getValue();
					openFastOrderingWindow(wndContentPanel, orderId);
				}
				catch(NullPointerException ex) {}				
			}
		});
	}
	
	@Listen("onItemToOrderProcessed = combobox#cmbItemToOrder")
	public void cmbItemToOrder_onItemToOrderProcessed(Event event) {
		
		((Combobox) event.getTarget()).invalidate();
	}
	
	@Listen("onChanging = combobox#cmbItemToOrder")
	public void cmbItemToOrder_onChanging(Event event) throws Exception {
	
		String barcode = ((InputEvent) event).getValue();
		
		if (BarcodeValidator.isAValidBarCode(barcode)) {
			
			Combobox cmbItemToOrder = (Combobox) event.getTarget();
			
			//check product exists
			Product product = new Product();
			product.setStringId(barcode);
			try {
				product.getFromStringId();
			} catch (Exception e) {
				ExceptionTreatment.logAndShow(e, 
						Labels.getLabel("message.permissions.NonExistentObjectOrNotEnoughRights"), 
						Labels.getLabel("messageBoxTitle.Warning"),
						Messagebox.EXCLAMATION);				
				Events.sendEvent("onItemToOrderProcessed", cmbItemToOrder, null);				
		        return;
			}
			
			product.setOneRunQuantity(new BigDecimal(1));
			Comboitem cmbIScannedProduct = new Comboitem();
			cmbIScannedProduct.setValue(product);
			cmbItemToOrder.getChildren().add(cmbIScannedProduct);
			
			cmbItemToOrder.setSelectedItem(cmbIScannedProduct);
	        Events.sendEvent(Events.ON_CHANGE, cmbItemToOrder, null);
        	
	        Textbox txtQuantityExpression = (Textbox) cmbItemToOrder.query("#txtQuantityExpression");
 	        if (txtQuantityExpression.getText().equals("0") || txtQuantityExpression.getText().isEmpty()) {
 	        	txtQuantityExpression.setText("1");
 	        }	   
 	        InputEvent inputEventQty = new InputEvent(Events.ON_CHANGE, txtQuantityExpression, txtQuantityExpression.getText(), null);
	        Events.sendEvent(inputEventQty);
 	        
	        Button btnMake = (Button) cmbItemToOrder.query("#btnMake");
	        Events.sendEvent(Events.ON_CLICK, btnMake, null);
	        
	        Clients.evalJavaScript("(new\n"
					        		+ "	Audio(\n"
					        		+ "	\"data:audio/wav;base64,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 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////VEFHAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAU291bmRib3kuZGUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMjAwNGh0dHA6Ly93d3cuc291bmRib3kuZGUAAAAAAAAAACU=\"\n"
					        		+ "	)).play();");
		}
	}
}