package eg.edu.alexu.csd.oop.db.backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Table {
	private String tableName;
	private String databaseName;
	private ArrayList<Column> columns;
	private DTD schema;
	//private String slach = System.getProperty("file.separator");
	
	
	public Table(){
	
		
	}
	
	public Table(String tableName2, String databaseName2) {
		this.tableName=tableName2;
		this.databaseName=databaseName2;
	}

	public void createSchema() throws IOException {

		schema = new DTD(columns, tableName,databaseName);
		schema.writeDTD();
	}
	public boolean createTable(String databaseName , String tableName,ArrayList<Column> columns){
		
		this.tableName=tableName;
		this.databaseName=databaseName;
		this.columns = columns;
		boolean success = false;
		if(!this.isExist()){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	//	dbFactory.setValidating(true);
		DocumentBuilder dBuilder;
		try {
			this.createSchema();
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element docElement = doc.createElement(tableName);
			doc.appendChild(docElement);		
		    Element tableColumns =doc.createElement("Columns");
		    docElement.appendChild(tableColumns);
		
		for (int i = 0; i < columns.size(); i++) {
			String tag=columns.get(i).getDataType();
			
			if(columns.get(i).getDataType().length()!=3){
			 tag=tag.substring(0, 7);
			}
			Element node = doc.createElement(tag);
			node.setTextContent(columns.get(i).getName());
			tableColumns.appendChild(node);			
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		 transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		DOMSource source = new DOMSource(doc);
		success=true;
		StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName + System.getProperty("file.separator")+ tableName +".xml"));
    //    transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, databaseName + "/" + tableName + ".dtd");
		transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			success= false;
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			success= false;
			e.printStackTrace();
		} catch (TransformerException e) {
			success= false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return success;
	}
	
	public boolean dropTable(String databaseName , String tableName){
		boolean success= false;
	   File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName + System.getProperty("file.separator") + tableName + ".xml");
		success=file.delete();
		   file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName + System.getProperty("file.separator") + tableName + ".dtd");
		   success=file.delete();
		return true;		
	}
	
	public boolean isExist(){
		
		File file= new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName);
		 File[] list = file.listFiles();
	        if(list!=null)
	        for (File fil : list){
	           if (tableName.equalsIgnoreCase(fil.getName().substring(0, fil.getName().length()-4))) {
	        	   return true;
	    
	            }
	        }
		return false;
	}
      


}
