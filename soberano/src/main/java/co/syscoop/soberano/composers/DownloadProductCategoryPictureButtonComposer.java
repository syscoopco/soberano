package co.syscoop.soberano.composers;

import org.apache.bcel.util.ByteSequence;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;

import co.syscoop.soberano.domain.tracked.ProductCategory;

@SuppressWarnings({ "serial", "rawtypes" })
public class DownloadProductCategoryPictureButtonComposer extends SelectorComposer {
	
	@Wire
	private A aDownload;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = a#aDownload")
    public void aDownload_onClick() throws Throwable {
		
		ProductCategory prod = (new ProductCategory(((Intbox) aDownload.query("#incDetails").getParent().query("#intId")).getValue()));
		prod.get();
		
		java.io.InputStream is = new ByteSequence(prod.getPicture());
		Filedownload.save(is, "", prod.getName());
	}
}