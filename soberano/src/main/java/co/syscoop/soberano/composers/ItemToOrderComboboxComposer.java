package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Span;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.tracked.Product;
import co.syscoop.soberano.domain.tracked.ProductCategory;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.util.Utils;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings("serial")
public class ItemToOrderComboboxComposer extends ViewModelComposer {
	
	private void additionButtonHandler(Button button) {
		
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
	private void productButtonHandler(Integer orderId, Integer productId, String productName, Window spanProductsParentWindow) throws Exception {
		
		Window window = (Window) Executions.createComponents("./complete_addition.zul", spanProductsParentWindow, null);
		window.setTitle(Labels.getLabel("pageOrder.howMany") + 
										" " + 
										productName + 
										" " + 
										Labels.getLabel("pageOrder.for") +
										" " +
										orderId);
		window.setWidth("80%");
		window.setHeight("80%");
		
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
				additionButton.setImageContent(Utils.createImageFromBytes(((Product) doo).getPicture()));
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
	private void categoryButtonHandler(Integer categoryId, Integer orderId, Window spanProductsParentWindow) throws Exception {
		
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
				prodButton.setImageContent(Utils.createImageFromBytes(((Product) doo).getPicture()));
				prodButton.setTooltiptext(((Product) doo).getName());
			}
			else
				prodButton.setLabel(((Product) doo).getName());
			
			prodButton.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					
					productButtonHandler(orderId, ((Product) doo).getId(), ((Product) doo).getName(), spanProductsParentWindow);
				}
			});
			
			spanProducts.appendChild(prodButton);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);        
        comp.addEventListener("onDoubleClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				Window wndContentPanel = (Window) comp.query("#wndContentPanel");
				
				try {
					Integer orderId = ((Intbox) wndContentPanel.query("#intObjectId")).getValue();
					Window window = (Window) Executions.createComponents("./add_items.zul", wndContentPanel, null);
					window.setTitle(Labels.getLabel("pageOrder.addItemsToOrder") + " " + orderId);
					window.setWidth("90%");
					window.setHeight("90%");
									
					ProductCategory productCategory = new ProductCategory();
					Span spanCategories = (Span) window.query("#spanCategories");
					for (Object doo : productCategory.getAllWithPicture()) {					
						Button catButton = new Button();						
						catButton.setStyle("margin-left: 3px; margin-top: 3px;");
						catButton.setHeight("100px");
						catButton.setWidth("100px");
						catButton.setOrient("vertical");
						if (((ProductCategory) doo).getPicture() != null) {
							catButton.setImageContent(Utils.createImageFromBytes(((ProductCategory) doo).getPicture()));
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
		});
	}
}