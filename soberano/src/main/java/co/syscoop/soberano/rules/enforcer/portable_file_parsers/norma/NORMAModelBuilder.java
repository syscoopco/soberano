package co.syscoop.soberano.rules.enforcer.portable_file_parsers.norma;

/**
 * @author Josue Portal
 *
 */
import java.lang.reflect.Method;
import java.util.Stack;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import co.syscoop.soberano.helper.xml.SimpleElement;

//Adapted from the class SAXModelBuilder defined in the book:
//
//Learning Java, 3rd Edition 
//By Jonathan Knudsen, Patrick Niemeyer 
//Publisher: O'Reilly
//Pub Date: May 2005
//ISBN: 0-596-00873-2
public class NORMAModelBuilder extends DefaultHandler {
	
	private Boolean elementMustBeProccessed = true;  
	
	Stack<SimpleElement> stack = new Stack<SimpleElement>();
	SimpleElement element;
	 
   public void startElement(String namespace, String localname, String qname, Attributes atts) throws SAXException {
      SimpleElement element = null;
    
      if (	qname.equals("orm:ModelErrors")) {
    	  elementMustBeProccessed = false;
      }
      
      if (elementMustBeProccessed) {
    	  try {
	    	  String localName = qname.substring(qname.indexOf(":") + 1);
	    	  String className = "co.syscoop.soberano.rules.enforcer.metamodel." + localName;
	    	  element = (SimpleElement) Class.forName(className).getDeclaredConstructor().newInstance();
	      } catch(Exception e) {}
	      if (element == null)
	         element = new SimpleElement();
	        for(int i=0; i < atts.getLength(); i++)
				try {
					element.setAttributeValue(atts.getQName(i), atts.getValue(i));
				} catch (Exception e) {
					e.fillInStackTrace();
				}
	      stack.push(element);
      }
   }
 
   public void endElement(String namespace, String localname, String qname) throws SAXException {
	   
	  if (elementMustBeProccessed) {
		  element = stack.pop();
	      if (!stack.empty())
	      try {
	    	 String localName = qname.substring(qname.indexOf(":") + 1);
	         setProperty(localName, stack.peek(), element);
	      } catch(Exception e) {
	    	  throw new SAXException("Error: "+e);
	    	}
	  }
	  
	  if (	qname.equals("orm:ModelErrors")) {
	    	  elementMustBeProccessed = true;
	      }
	  if (	qname.equals("orm:ORMModel")) {
    	  elementMustBeProccessed = false;
      }
   }
 
   public void characters(char[] ch, int start, int len) {
      String text = new String(ch, start, len);
      stack.peek().addText(text);
   }
 
   private void setProperty(String name, Object target, Object value) throws SAXException {
      Method method = null;
      try {
         method = target.getClass().getMethod("add"+name, value.getClass());
      } catch(NoSuchMethodException e) {}
      if (method == null) try {
         method = target.getClass().getMethod("set"+name, value.getClass());
      } catch(NoSuchMethodException e) {}
      if (method == null) try {
         value = ((SimpleElement)value).getText();
         method = target.getClass().getMethod("add"+name, String.class);
      } catch(NoSuchMethodException e) {}
      try {
         if (method == null)
            method = target.getClass().getMethod("set"+name, String.class);
         method.invoke(target, value);
      } catch(Exception e) {
    	  throw new SAXException(e.toString());
      	}
   }
 
   public SimpleElement getModel() {
	   return element;
   }
	   
   public void startDocument( ) throws SAXException {
	
   }
	   
   public void endDocument( ) throws SAXException {
	
   }
}

