package eg.edu.alexu.csd.oop.db.backend;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class MyDatabase {
	//private static final String SRC_FOLDER = "whatever we want";
	private static ArrayList<String> databaseList ;
	private boolean isLoaded=false;
	private String path;
	public String getPath() {
		return path;
	}

	public List<String> getDatabaseList() {
//		 if(!this.isLoaded()){
//   			this.loadList();
//   			}
		return databaseList;
	}

	public void setDatabaseList(ArrayList<String> databaseList) {
		this.databaseList = databaseList;
	}

	public MyDatabase(){
		databaseList = new ArrayList<String>();
	}
	
	public boolean createDatabase (String databaseName){
//		if(!this.isLoaded()){
//			this.loadList();
//			}
		 boolean success = true;
		 File  file=new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName);
		
		 if(!databaseList.contains(databaseName)){
		
		  success = file.mkdirs();
		  databaseList.add(databaseName);
		  }
		 if(databaseList.contains(databaseName)){
			 databaseList.set(databaseList.indexOf(databaseName), databaseList.get(databaseList.size()-1));
			 databaseList.set(databaseList.size()-1, databaseName);
		 }
			path=file.getAbsolutePath();
//		this.saveList();
		return success;
	}

	public boolean dropDatabase(String databaseName){
		boolean success = false;
		File directory = new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName);
		path=directory.getAbsolutePath();
		if(!directory.exists()){
	           System.out.println("Database dosen't exist");
	          success= false;
	        }else{
	           try{
	              success=( delete(directory));
	              if(success){
//	            	  if(!this.isLoaded()){
//	          			this.loadList();
//	          			}
	            	  System.err.println("Database name: " + databaseName);
	            	  databaseList.remove(databaseList.indexOf(databaseName));
//	            	  this.saveList();
	              }
	           }catch(IOException e){
	               e.printStackTrace();
	           }
	        }

		return success;
	}
	 private static boolean delete(File file)
		    	throws IOException{
        boolean success= false;
		    	if(file.isDirectory()){
		    		if(file.list().length==0){
		    		   file.delete();
		    		   success= true;
		    		   System.out.println("Directory is deleted :"+ file.getAbsolutePath());

		    		}else{

		    		   //list all the directory contents
		        	   String files[] = file.list();
		        	   for (String temp : files) {
		        		  
		        	      //construct the file structure
		        	      File fileDelete = new File(file, temp);

		        	      //recursive delete
		        	     success=delete(fileDelete);
		        	   }

		        	   //check the directory again, if empty then delete it
		        	   if(file.list().length==0){
		           	    success= file.delete();
		        	     System.out.println("Directory is deleted : "
		                                                  + file.getAbsolutePath());
		        	   }
		    		}
		    	
		    	}else{
		    		//if file, then delete it
		    		file.delete();
		    		System.out.println("File is deleted : " + file.getAbsolutePath());
		    	}
				return success;
		    }
//	 public boolean saveList() {
//			boolean success;
//			try {
//				XML xml = new XML(databaseList);
//				xml.serializeToXML();
//				success = true;
//			} catch (IOException e) {
//				success = false;
//				e.printStackTrace();
//			}
//			return success;
//		}

//		public boolean loadList() {
//			isLoaded = true;
//			boolean success;
//			try {
//				XML xml = new XML(databaseList);
//				databaseList = xml.deserializeFromXML();
//				success = true;
//			} catch (IOException e) {
//				success = false;
//				e.printStackTrace();
//			}
//			return success;
//
//		}
//
//		public boolean isLoaded() {
//			return isLoaded;
//		}

}
