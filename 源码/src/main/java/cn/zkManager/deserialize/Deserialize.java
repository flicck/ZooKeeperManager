package cn.zkManager.deserialize;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserialize {

	public Object deserialize(byte[] bytes) throws ClassNotFoundException, IOException{
	      try {
	            ObjectInputStream inputStream = new TAObjectInputStream(new ByteArrayInputStream(bytes));
	            Object object = inputStream.readObject();
	            return object;
	        } catch (ClassNotFoundException e) {
	        	throw e;
	        } catch (IOException e) {
	        	throw e;
	        }
		
	}
	
}
