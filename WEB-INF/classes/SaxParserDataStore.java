
/*********


http://www.saxproject.org/

SAX is the Simple API for XML, originally a Java-only API. 
SAX was the first widely adopted API for XML in Java, and is a e facto standard. 
The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java. 

The following URL from Oracle is the JAVA documentation for the API

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html


*********/
import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

////////////////////////////////////////////////////////////

/**************
 * 
 * SAX parser use callback function to notify client object of the XML document
 * structure. You should extend DefaultHandler and override the method when
 * parsin the XML document
 * 
 ***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
	Console console;
	Game game;
	Tablet tablet;
	Accessory accessory;
	static HashMap<String, Console> consoles;
	static HashMap<String, Game> games;
	static HashMap<String, Tablet> tablets;
	static HashMap<String, Accessory> accessories;
	String consoleXmlFileName;
	HashMap<String, String> accessoryHashMap;
	String elementValueRead;
	String currentElement = "";

	public SaxParserDataStore() {
	}

	public SaxParserDataStore(String consoleXmlFileName) {

		if(!funcIsDiskHashmapFound()){
			this.consoleXmlFileName = consoleXmlFileName;
			consoles = new HashMap<String, Console>();
			games = new HashMap<String, Game>();
			tablets = new HashMap<String, Tablet>();
			accessories = new HashMap<String, Accessory>();
			accessoryHashMap = new HashMap<String, String>();
			parseDocument();
			funcStoreCurentHashmaptoDisk();
			System.out.print("------------- Printing Consoles Hasmap");
			System.out.println(consoles + "---------------");
		}
		
	}
	public Boolean funcIsDiskHashmapFound() {

		String TOMCAT_HOME = System.getProperty("catalina.home");
		File tempFile = new File(TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapA.txt");
		boolean exists = tempFile.exists();

		if (!exists) {
			return exists;
		}


		Boolean fileFound = false;

		HashMap<String, Console> ProductHashMapA = new HashMap<String, Console>();
		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File(TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapA.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			ProductHashMapA = (HashMap) objectInputStream.readObject();
			consoles = ProductHashMapA;
			fileFound = true;
		} catch (Exception e) {
			System.out.println("exceptionnnnnnnn SaxParser reading hashmap" + ProductHashMapA + "from disk");

		}

		HashMap<String, Game> ProductHashMapB = new HashMap<String, Game>();
		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File(TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapB.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			ProductHashMapB = (HashMap) objectInputStream.readObject();
			games = ProductHashMapB;
			fileFound = true;

		} catch (Exception e) {
			System.out.println("exceptionnnnnnnn SaxParser reading hashmap" + ProductHashMapB + "from disk");

		}
		HashMap<String, Tablet> ProductHashMapC = new HashMap<String, Tablet>();
		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File(TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapC.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			ProductHashMapC = (HashMap) objectInputStream.readObject();
			tablets = ProductHashMapC;
			fileFound = true;

		} catch (Exception e) {
			System.out.println("exceptionnnnnnnn SaxParser reading hashmap" + ProductHashMapC + "from disk");

		}
		HashMap<String, Accessory> ProductHashMapD = new HashMap<String, Accessory>();
		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File(TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapD.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			ProductHashMapD = (HashMap) objectInputStream.readObject();
			accessories = ProductHashMapD;
			fileFound = true;

		} catch (Exception e) {
			System.out.println("exceptionnnnnnnn SaxParser reading hashmap" + ProductHashMapD + "from disk");

		}
		
		HashMap<String, String> ProductHashMapE = new HashMap<String, String>();
		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File(TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapE.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			ProductHashMapE = (HashMap) objectInputStream.readObject();
			accessoryHashMap = ProductHashMapE;
			fileFound = true;

		} catch (Exception e) {
			System.out.println("exceptionnnnnnnn SaxParser reading hashmap" + ProductHashMapE + "from disk");

		}

		// HashMap<String, VOICEASSISTANT> ProductHashMapE = new HashMap<String, VOICEASSISTANT>();
		// try {
		// 	FileInputStream fileInputStream = new FileInputStream(
		// 			new File(TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapF.txt"));
		// 	ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		// 	ProductHashMapF = (HashMap) objectInputStream.readObject();
		// 	VOICEASSISTANT = ProductHashMapF;
		// fileFound = true;

		// } catch (Exception e) {
		// 	System.out.println("exceptionnnnnnnn SaxParser reading hashmap" + ProductHashMapF + "from disk");

		// }
		System.out.println("Could load hashmap from disk"+fileFound);
		return fileFound;
	}


	public void funcStoreCurentHashmaptoDisk() {
		String TOMCAT_HOME = System.getProperty("catalina.home");

		funcStoreProductHashmapToDisk(consoles, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapA.txt"));
		funcStoreProductHashmapToDisk(games, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapB.txt"));
		funcStoreProductHashmapToDisk(tablets, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapC.txt"));
		funcStoreProductHashmapToDisk(accessories, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapD.txt"));
		funcStoreProductHashmapToDisk(accessoryHashMap, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapE.txt"));
		// funcStoreProductHashmapToDisk(VOICEASSISTANT, (TOMCAT_HOME + "/webapps/Assignment_webapp/ProductHashMapF.txt"));
	}

	public void funcStoreProductHashmapToDisk(HashMap hm,String path) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(hm);
			objectOutputStream.flush();
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception e) {
			System.out.println("exceptionnnnnnnn SaxParser storing hashmap" + hm + "at path" + path);
		}

	}
	// parse the xml using sax parser to get the data

	private void parseDocument() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(consoleXmlFileName, this);
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfig error");
		} catch (SAXException e) {
			System.out.println("SAXException : xml not well formed");
		} catch (IOException e) {
			System.out.println("IO error");
		}
	}

	////////////////////////////////////////////////////////////

	/*************
	 * 
	 * There are a number of methods to override in SAX handler when parsing your
	 * XML document :
	 * 
	 * Group 1. startDocument() and endDocument() : Methods that are called at the
	 * start and end of an XML document. Group 2. startElement() and endElement() :
	 * Methods that are called at the start and end of a document element. Group 3.
	 * characters() : Method that is called with the text content in between the
	 * start and end tags of an XML document element.
	 * 
	 * 
	 * There are few other methods that you could use for notification for different
	 * purposes, check the API at the following URL:
	 * 
	 * https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
	 * 
	 ***************/

	////////////////////////////////////////////////////////////

	// when xml start element is parsed store the id into respective hashmap for
	// console,games etc
	@Override
	public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

		if (elementName.equalsIgnoreCase("product")) {
			currentElement = "product";
			console = new Console();
			console.setId(attributes.getValue("id"));
		}
		if (elementName.equalsIgnoreCase("Laptop")) {
			currentElement = "Laptop";
			tablet = new Tablet();
			tablet.setId(attributes.getValue("id"));
		}
		if (elementName.equalsIgnoreCase("Phone")) {
			currentElement = "Phone";
			game = new Game();
			game.setId(attributes.getValue("id"));
		}
		if (elementName.equals("accessory") && !currentElement.equals("product")) {
			currentElement = "accessory";
			accessory = new Accessory();
			accessory.setId(attributes.getValue("id"));
		}

	}

	// when xml end element is parsed store the data into respective hashmap for
	// console,games etc respectively
	@Override
	public void endElement(String str1, String str2, String element) throws SAXException {

		if (element.equals("product")) {
			consoles.put(console.getId(), console);
			return;
		}

		if (element.equals("Laptop")) {
			tablets.put(tablet.getId(), tablet);
			return;
		}
		if (element.equals("Phone")) {
			games.put(game.getId(), game);
			return;
		}
		if (element.equals("accessory") && currentElement.equals("accessory")) {
			accessories.put(accessory.getId(), accessory);
			return;
		}
		if (element.equals("accessory") && currentElement.equals("product")) {
			accessoryHashMap.put(elementValueRead, elementValueRead);
		}
		if (element.equalsIgnoreCase("accessories") && currentElement.equals("product")) {
			System.out.println("accesssssoorryy" + accessoryHashMap);
			console.setAccessories(accessoryHashMap);
			accessoryHashMap = new HashMap<String, String>();
			return;
		}
		if (element.equalsIgnoreCase("image")) {
			if (currentElement.equals("product"))
				console.setImage(elementValueRead);
			if (currentElement.equals("Phone"))
				game.setImage(elementValueRead);
			if (currentElement.equals("Laptop"))
				tablet.setImage(elementValueRead);
			if (currentElement.equals("accessory"))
				accessory.setImage(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("discount")) {
			if (currentElement.equals("product"))
				console.setDiscount(Double.parseDouble(elementValueRead));
			if (currentElement.equals("Phone"))
				game.setDiscount(Double.parseDouble(elementValueRead));
			if (currentElement.equals("Laptop"))
				tablet.setDiscount(Double.parseDouble(elementValueRead));
			if (currentElement.equals("accessory"))
				accessory.setDiscount(Double.parseDouble(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("condition")) {
			if (currentElement.equals("product"))
				console.setCondition(elementValueRead);
			if (currentElement.equals("Phone"))
				game.setCondition(elementValueRead);
			if (currentElement.equals("Laptop"))
				tablet.setCondition(elementValueRead);
			if (currentElement.equals("accessory"))
				accessory.setCondition(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("manufacturer")) {
			if (currentElement.equals("product"))
				console.setRetailer(elementValueRead);
			if (currentElement.equals("Phone"))
				game.setRetailer(elementValueRead);
			if (currentElement.equals("Laptop"))
				tablet.setRetailer(elementValueRead);
			if (currentElement.equals("accessory"))
				accessory.setRetailer(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("name")) {
			if (currentElement.equals("product"))
				console.setName(elementValueRead);
			if (currentElement.equals("Phone"))
				game.setName(elementValueRead);
			if (currentElement.equals("Laptop"))
				tablet.setName(elementValueRead);
			if (currentElement.equals("accessory"))
				accessory.setName(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("price")) {
			if (currentElement.equals("product"))
				console.setPrice(Double.parseDouble(elementValueRead));
			if (currentElement.equals("Phone"))
				game.setPrice(Double.parseDouble(elementValueRead));
			if (currentElement.equals("Laptop"))
				tablet.setPrice(Double.parseDouble(elementValueRead));
			if (currentElement.equals("accessory"))
				accessory.setPrice(Double.parseDouble(elementValueRead));
			return;
		}

	}

	// get each element in xml tag
	@Override
	public void characters(char[] content, int begin, int end) throws SAXException {
		elementValueRead = new String(content, begin, end);
	}

	/////////////////////////////////////////
	// Kick-Start SAX in main //
	////////////////////////////////////////

	// call the constructor to parse the xml and get product details
	public static void addHashmap(String path) {
		// System.out.println("Hi SPLessons");
		new SaxParserDataStore(path);

	}
}
