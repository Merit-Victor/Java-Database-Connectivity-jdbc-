package eg.edu.alexu.csd.oop.db.strategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import eg.edu.alexu.csd.oop.db.backend.Column;


public class InsertOperation implements Strategy{
	private String name;
	private ArrayList<Column> columns;
	boolean withoutCol ;
	public InsertOperation(String name, ArrayList<Column> columns,boolean withoutCol) {
		this.name=name;
		this.columns=columns;
		this.withoutCol=withoutCol;
	}
	
	
	@Override
	public Result doOperation(String databaseName) {
		//System.out.println(databaseName);
		Result resultMap = new Result();
		boolean exist = false;
		InputStream inputStream;
		Reader reader = null;
		String path=System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName + System.getProperty("file.separator") + name+".xml";
		
			File file= new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName);
			 File[] list = file.listFiles();
		        if(list!=null)
		        for (File fil : list){
		           if (name.equalsIgnoreCase(fil.getName().substring(0, fil.getName().length()-4))) {
		        	  exist= true;
		    
		            }
		        }
			
		      if(exist){  
		        try {
					inputStream = new FileInputStream(path);
					reader = new InputStreamReader(inputStream, "ISO-8859-1");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		try{	
		InputSource is = new InputSource(reader);
		is.setEncoding("ISO-8859-1");
		
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(is);
	     //    dbFactory.setValidating(true);
	         doc.getDocumentElement().normalize();
	         Element documentElement= doc.getDocumentElement();
	         Node node= doc.createElement("Row");
	         documentElement.appendChild(node);
	         Node Col = doc.getElementsByTagName("Columns").item(0);
 	    	 Element eElement = (Element) Col;
 	    	 NodeList nl = eElement.getElementsByTagName("*");
 	    	 if(withoutCol){
 	    		 for(int i=0;i<nl.getLength();i++){
 	    			 columns.get(i).setName(nl.item(i).getTextContent());
 	    		 }
 	    	 }
	         for(int i=0;i<nl.getLength();i++){
	        	 if(contains(nl.item(i).getTextContent())){
	        	 Node childNode= doc.createElement(columns.get(i).getName());
	        	 childNode.setTextContent(columns.get(i).getValue().toString().replace("\'", " ").replace("\"", " ").trim());
	        	
		        node.appendChild(childNode);
	        	 }else{
	        		 Node childNode= doc.createElement(nl.item(i).getTextContent());
		        	 childNode.setTextContent("null");
			        node.appendChild(childNode);
	        	 }
	         }
	        
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        Transformer transformer = transformerFactory.newTransformer();
		        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		   //     transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, databaseName + "/" + name + ".dtd");
		        DOMSource source = new DOMSource(doc);
		        StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName + System.getProperty("file.separator") + name+".xml"));
		        transformer.transform(source, result);
		} catch (Exception e) {
	         e.printStackTrace();
		      }
		resultMap.setNumber(1);
		      }else{
		    	  System.out.println("the table doesn't exist");
		    	  resultMap.setNumber(0);
		      }
		return resultMap;
	}
	private boolean contains(String nodeName) {
		boolean flag = false;
		Iterator<Column> itr= columns.iterator();
		while(itr.hasNext()){
			if(itr.next().getName().equals(nodeName)){
				flag=true;
			}
		}
		return flag;
	}

}
