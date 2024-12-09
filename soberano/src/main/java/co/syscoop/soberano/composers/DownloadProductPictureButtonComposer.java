package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;

import com.sun.org.apache.bcel.internal.util.ByteSequence;
import co.syscoop.soberano.domain.tracked.Product;

@SuppressWarnings({ "serial", "rawtypes", "restriction" })
public class DownloadProductPictureButtonComposer extends SelectorComposer {
	
	@Wire
	private A aDownload;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = a#aDownload")
    public void aDownload_onClick() throws Throwable {
		
		Product prod = (new Product(((Intbox) aDownload.query("#incDetails").getParent().query("#intId")).getValue()));
		prod.get();
		
		java.io.InputStream is = new ByteSequence(prod.getPicture());
		Filedownload.save(is, "", prod.getName());
	}
}