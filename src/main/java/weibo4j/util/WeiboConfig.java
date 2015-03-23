package weibo4j.util;

import com.qq.connect.utils.QQConnectConfig;

public class WeiboConfig {
	public WeiboConfig(){}
//	private static Properties props = new Properties(); 
//	static{
//		try {
//			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	public static String getValue(String key){
		//return props.getProperty(key);
		return QQConnectConfig.getValue("sina_"+key);
	}

    public static void updateProperties(String key,String value) {    
           //props.setProperty(key, value); 
            QQConnectConfig.updateProperties("sina_"+key, value);
    } 
}
