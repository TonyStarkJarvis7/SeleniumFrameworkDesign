package tonystarkindustries.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {//for an idea of how Hashmap works to read a string file.
						//Actual fnction which is being used is in BaseTest.java
	
	public List<HashMap<String, String>> getJasonDataToMap() throws IOException {
		String jsonContent=FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\tonystarkindustries\\data\\PurchaseOrder.json"),StandardCharsets.UTF_8);
		
		//String to HashMAp -> using DataBind for ObjectMaper class
		ObjectMapper mapper =new ObjectMapper();
		List<HashMap<String,String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){		
		});
		return data;
		//{map,map}
	}
}
