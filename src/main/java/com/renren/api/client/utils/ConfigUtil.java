package com.renren.api.client.utils;

import com.qq.connect.utils.QQConnectConfig;

public class ConfigUtil {
//    private static URL filePath=Thread.currentThread().getContextClassLoader().getResource("");
//    private static String fileName = "apiconfig.properties";
    public ConfigUtil(){}
//    private static Properties props = new Properties(); 
//    static{
//        try {
//            props.load(new FileInputStream(filePath.getPath()+File.separator+fileName));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static String getValue(String key){
        //return props.getProperty(key);
    	return QQConnectConfig.getValue(key);
    }

    public static void updateProperties(String key,String value) {    
            //props.setProperty(key, value); 
    	QQConnectConfig.updateProperties(key, value);
    } 
}
