package kx2_4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class KxConfigApp{
	private static String filePath = "configApp.properties";

	private static Properties props = new Properties(); 
	static{
		try {
			InputStream file = KxConfigApp.class.getClassLoader().getResourceAsStream(filePath);
			//System.out.println(KxConfig.class.getClassLoader().getResource("/").getPath());
			props.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getValue(String key){
		return props.getProperty(key);
	}

    public static void updateProperties(String key,String value) {    
            props.setProperty(key, value); 
    } 
}