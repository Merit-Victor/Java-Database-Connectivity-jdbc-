package eg.edu.alexu.csd.oop.db.backend;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Target
 *
 */
public class XML {
	ArrayList<String> databaseInfo;
	String path ="databaseInfo";
	
	/**
	 * .
	 */
	public XML(ArrayList<String> databaseInfo) {
		this.databaseInfo = databaseInfo;
	}
	

	/**
	 * .
	 * 
	 * @param path.
	 * @param myShapes.
	 * @throws IOException.
	 */
	public void serializeToXML() throws IOException {
		FileOutputStream fos = new FileOutputStream(path+".xml");
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		XMLEncoder encoder = new XMLEncoder(bos);
		encoder.writeObject(databaseInfo);
		encoder.close();
		fos.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> deserializeFromXML() throws IOException {
		FileInputStream fis = new FileInputStream(path+".xml");
		BufferedInputStream bos = new BufferedInputStream(fis);
		XMLDecoder decoder = new XMLDecoder(bos);
		ArrayList<String> loadedTable = (ArrayList<String>) decoder.readObject();
		decoder.close();
		fis.close();
		return loadedTable;
	}

	
	



}