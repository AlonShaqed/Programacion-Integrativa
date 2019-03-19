import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
 

public class Main {
 
	public static void main(String[] args) throws Exception {
	 
		//loading the XML document from a file
		DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance();
		builderfactory.setNamespaceAware(true);
		 
		DocumentBuilder builder = builderfactory.newDocumentBuilder();
		Document xmlDocument = builder.parse(
		new File(Main.class.getResource("books.xml").getFile().replace("%20", " ")));
		 
		XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xPath = factory.newXPath();

		String param="Horror";
		XPathExpression expression=xPath.compile("/catalog/book[genre='"+param+"']//author");
		String result= expression.evaluate(xmlDocument,XPathConstants.STRING).toString();
		System.out.print("Autor de la pelicula de Horror: "+result+"\n");

		param="Fantasy";
		expression=xPath.compile("/catalog/book[genre='"+param+"']//price");
		NodeList nodeList =  (NodeList) expression.evaluate(xmlDocument,XPathConstants.NODESET);
		float sum=0;
		for (int i=0; i<nodeList.getLength(); i++)
		{
			sum+=Float.parseFloat(nodeList.item(i).getTextContent());
		}
		System.out.println("Suma de los precios de libros de Fantasia: $"+sum);

		param="Microsoft";
		expression=xPath.compile("/catalog/book[contains(description,'"+param+"')]//title");
		NodeList list =  (NodeList) expression.evaluate(xmlDocument,XPathConstants.NODESET);
		String title="";
		System.out.println("Libros de Horror (Microsoft)");
		for(int i=0; i<list.getLength(); i++)
		{
			 title=list.item(i).getTextContent();
			System.out.println("Titulo: "+title);
		}
	}
}