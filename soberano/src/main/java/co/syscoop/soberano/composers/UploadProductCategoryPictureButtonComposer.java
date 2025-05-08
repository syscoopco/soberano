package co.syscoop.soberano.composers;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.tracked.ProductCategory;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public class UploadProductCategoryPictureButtonComposer extends SelectorComposer {
	
	@Wire
	private Label lblNoPicture;
	
	@Wire
	private A aDownload;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnUpload")
    public void btnUpload_onClick() throws Throwable {
		
		try{
			Fileupload.get(1, new EventListener<UploadEvent>() {				
				public void onEvent(UploadEvent event) throws Exception {
				
					Media media = ((org.zkoss.zk.ui.event.UploadEvent) event).getMedia();					
					if (media.getContentType().contains("image") /*media instanceof org.zkoss.image.Image*/) {
						
						try {
							ProductCategory prod = (new ProductCategory(((Intbox) aDownload.query("#incDetails").getParent().query("#intId")).getValue()));
							prod.setPicture(media.getByteData());
							if (prod.uploadPicture().equals(-1)) {
								throw new NotEnoughRightsException();						
							}
							else {
								//update object's treeitem label
								lblNoPicture.setVisible(false);
								aDownload.setVisible(true);

								Messagebox.show(Labels.getLabel("message.notification.ChangesWereApplied"), 
					  					Labels.getLabel("messageBoxTitle.Information"), 
										0, 
										Messagebox.INFORMATION);
							}
						}
						catch(NotEnoughRightsException ex) {
							ExceptionTreatment.logAndShow(ex, 
														Labels.getLabel("message.permissions.NotEnoughRights"), 
														Labels.getLabel("messageBoxTitle.Warning"),
														Messagebox.EXCLAMATION);
						}
						catch(Exception ex)	{
							ExceptionTreatment.logAndShow(ex, 
														ex.getMessage(), 
														Labels.getLabel("messageBoxTitle.Error"),
														Messagebox.ERROR);
						}
					}
					else {
						Messagebox.show(Labels.getLabel("error.InvalidImageFileFormat"), Labels.getLabel("messageBoxTitle.Error"), 0, Messagebox.ERROR);
					}
				}
			});
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
	}
}