package eg.edu.alexu.csd.oop.db.strategy;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
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
import eg.edu.alexu.csd.oop.db.backend.Condition;

public class UpdateOperation implements Strategy{

	private String name; 
	private ArrayList<Column> columns;
	private Condition condition= null;
	
	public UpdateOperation(String name, ArrayList<Column> columns) {
		this.columns=columns;
		this.name=name;
	}
	public UpdateOperation(String name, ArrayList<Column> columns, Condition condition) {
		this.columns=columns;
		this.name=name;
		this.condition=condition;
		
	}
	@Override
	public Result doOperation(String databaseName) throws SQLException {
		 Result resultMap = new Result();
		int counter=0;
		boolean exist = false;
		File file= new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName);
		 File[] list = file.listFiles();
	        if(list!=null)
	        for (File fil : list){
	           if (name.equalsIgnoreCase(fil.getName().substring(0, fil.getName().length()-4))) {
	        	  exist= true;
	    
	            }
	        }
		
	      if(exist){  
		InputStream inputStream;
		Reader reader = null;
		try {
			inputStream = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName+System.getProperty("file.separator")+name+".xml");
			try {
				reader = new InputStreamReader(inputStream, "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		InputSource is = new InputSource(reader);
		is.setEncoding("ISO-8859-1");
	      try {
	         
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	   //      dbFactory.setValidating(true);
	         Document doc = dBuilder.parse(is);
	         doc.getDocumentElement().normalize();
	         
	      
	         NodeList nList = doc.getElementsByTagName("Row");
	         //System.out.println("----------------------------");
	       // Node columnNode=  doc.getElementsByTagName("Columns").item(0); 
	         //Element columnElement = (Element) columnNode;
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	           // System.out.println("\nCurrent Element :" + nNode.getNodeName());
	            Element eElement = (Element) nNode;
	            if (condition != null){
	           String key= eElement.getElementsByTagName(condition.getFirstOperand().replace("\'", " ").replace("\"", " ").trim()).item(0).getTextContent();
	       
	           switch (condition.getOperator()) {
				case '=':
					//System.out.println(condition.getSecondOperand().toLowerCase().replace("\'", " ").replace("\"", " ").trim().compareTo(key));
					if(condition.getSecondOperand().toLowerCase().replace("\'", " ").replace("\"", " ").trim().compareTo(key)==0){
						//System.out.println(key);
						counter++;
						for(int i=0;i<columns.size();i++){
						eElement.getElementsByTagName(columns.get(i).getName()).item(0).setTextContent(columns.get(i).getValue().toString().replace("\'", " ").replace("\"", " ").trim());
					}
					}
//					if (columnElement.getElementsByTagName(condition.getFirstOperand()).item(0).getTextContent().equalsIgnoreCase("varchar")&&condition.getSecondOperand().equalsIgnoreCase(key)) {
//					counter++;
//						for(int i=0;i<columns.size();i++){
//						eElement.getElementsByTagName(columns.get(i).getName()).item(0).setTextContent(columns.get(i).getValue().toString());
//					}
//					} else if (columnElement.getElementsByTagName(condition.getFirstOperand()).item(0).getTextContent().equalsIgnoreCase("int")&&Integer.parseInt(condition
//									.getSecondOperand()) ==  Integer.parseInt(key)) {
//						counter++;
//						for(int i=0;i<columns.size();i++){
//							//System.out.println(columns.get(i).getName());
//							eElement.getElementsByTagName(columns.get(i).getName()).item(0).setTextContent(columns.get(i).getValue().toString());
//						//System.out.println(eElement.getElementsByTagName(columns.get(i).getName()).item(0).getTextContent());
//						}
//					}
					break;
				case '<':
					if(condition.getSecondOperand().replace("\'", " ").replace("\"", " ").trim().compareTo(key)>0){
						counter++;
						for(int i=0;i<columns.size();i++){
						eElement.getElementsByTagName(columns.get(i).getName()).item(0).setTextContent(columns.get(i).getValue().toString().replace("\'", " ").replace("\"", " ").trim());
					}
					}
//					if (Integer.parseInt(condition.getSecondOperand()) > (Integer.parseInt(key))) {
//						counter++;
//						for(int i=0;i<columns.size();i++){
//						//	System.out.println(columns.get(i).getName());
//							eElement.getElementsByTagName(columns.get(i).getName()).item(0).setTextContent(columns.get(i).getValue().toString());
//						//System.out.println(eElement.getElementsByTagName(columns.get(i).getName()).item(0).getTextContent());
//						}
//					}
					break;
				case '>':
					if(condition.getSecondOperand().replace("\'", " ").replace("\"", " ").trim().compareTo(key)<0){
						counter++;
						for(int i=0;i<columns.size();i++){
						eElement.getElementsByTagName(columns.get(i).getName()).item(0).setTextContent(columns.get(i).getValue().toString().replace("\'", " ").replace("\"", " ").trim());
					}
					}
//					if (Integer.parseInt(condition.getSecondOperand()) < (Integer.parseInt(key))) {
//						counter++;
//						for(int i=0;i<columns.size();i++){
//							System.out.println(columns.get(i).getName());
//							eElement.getElementsByTagName(columns.get(i).getName()).item(0).setTextContent(columns.get(i).getValue().toString());
//						System.out.println(eElement.getElementsByTagName(columns.get(i).getName()).item(0).getTextContent());
//						}
//					}
					break;
				default:
					break;

	           }
	            }
	            else if (condition == null){
                        counter++;
	            	for(int i=0;i<columns.size();i++){
						eElement.getElementsByTagName(columns.get(i).getName()).item(0).setTextContent(columns.get(i).getValue().toString().replace("\'", " ").replace("\"", " ").trim());
					}
	            }
	         }
	           
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        Transformer transformer = transformerFactory.newTransformer();
		        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		 //       transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, databaseName + "/" + name + ".dtd");
		        DOMSource source = new DOMSource(doc);
		        StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName+System.getProperty("file.separator")+name+".xml"));
		        transformer.transform(source, result);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	    
	      if (counter==0){
	    	 // throw new SQLException();
	    	  System.out.println("that column doesn't exist or the condition can't be met");
	      }
	     resultMap.setNumber(counter);
	      }else{
	    	  throw new SQLException();
	    	//  System.out.println("the table doesn't exist");
	    	//  resultMap.setNumber(0);
	      }
		return resultMap ;
	}

}
