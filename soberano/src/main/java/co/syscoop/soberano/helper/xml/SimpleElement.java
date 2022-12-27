package co.syscoop.soberano.helper.xml;

//Taken from the book:
//
//Learning Java, 3rd Edition 
//By Jonathan Knudsen, Patrick Niemeyer 
//Publisher: O'Reilly
//Pub Date: May 2005
//ISBN: 0-596-00873-2
public class SimpleElement {
	
  StringBuffer text = new StringBuffer();
  public void addText(String s) {text.append(s);}
  public String getText() {return text.toString();}
  public void setAttributeValue(String name, String value) throws Exception {
      throw new Exception(getClass(  ) + ": No attributes allowed. Attribute name: " + name + ". Attribute value: " + value + ".");
  }
}
