package co.syscoop.soberano.util;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;

//import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

import com.fathzer.soft.javaluator.DoubleEvaluator;

public class Utils {

	public static String evaluate(String arithmeticExpression) /*throws ScriptException*/ {
	    
		DoubleEvaluator eval = new DoubleEvaluator();
		return eval.evaluate(arithmeticExpression).toString();   
    }
	
	public static String getValidURL(String invalidURLString){
	    try {
	    	URL url = new URL(URLDecoder.decode(invalidURLString, StandardCharsets.UTF_8.toString()));
	        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
	        return uri.toString();
	    } catch (URISyntaxException | UnsupportedEncodingException | MalformedURLException ignored) {
	        return null;
	    }
	}
	
	public void redirect(String urlStr) throws IOException {
		
		Execution exec = Executions.getCurrent();
		HttpServletRequest request = (HttpServletRequest) exec.getNativeRequest();
		HttpServletResponse response = (HttpServletResponse) exec.getNativeResponse();
		String referrer = request.getHeader("Referer");
		URL ref = new URL(referrer);
	   	
		try {
			response.sendRedirect(response.encodeRedirectURL(ref.getProtocol() + "://" + ref.getHost() + urlStr));
			exec.setVoided(true);
		}
		catch(Throwable ex) {
			return;
		}		
	}
	
	public static BufferedImage convertByteArrayToBufferedImage(byte[] byteArr, Integer width, Integer height) throws IOException {
        BufferedImage bufferedImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Raster raster = Raster.createRaster(bufferedImage.getSampleModel(), 
        									new DataBufferByte(byteArr, byteArr.length), 
        									new Point());
        bufferedImage.setData( raster);
        return bufferedImage;
    }
 
	
	static public BufferedImage createImageFromBytes(byte[] imageData, Integer width, Integer height) {	   
	    try {
	    	//return convertByteArrayToBufferedImage(imageData, width, height);
	    	
	    	return ImageIO.read(new ByteArrayInputStream(imageData));
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}

}
