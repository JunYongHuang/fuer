package kx2_4j;

import com.qq.connect.utils.QQConnectConfig;


public class KxConfig{
//	private static String filePath = "config.properties";
//
//	private static Properties props = new Properties(); 
//	static{
//		try {
//			InputStream file = KxConfig.class.getClassLoader().getResourceAsStream(filePath);
//			//System.out.println(KxConfig.class.getClassLoader().getResource("/").getPath());
//			props.load(file);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	public static String getValue(String key){
		//return props.getProperty(key);
		return QQConnectConfig.getValue("kaixin_"+key);
	}

    public static void updateProperties(String key,String value) {    
            //props.setProperty(key, value); 
        QQConnectConfig.updateProperties("kaixin_"+key, value);
    } 
}