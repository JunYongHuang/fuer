
package kx2_4j.apis;

import java.util.ArrayList;

import kx2_4j.http.KxException;
import kx2_4j.http.PostParameter;
import kx2_4j.http.Response;

public class Emotion extends KxAPIcaller {

    public Emotion(String token)
    {
    	super(token);
    }
    
	/**
	 * 获取开心网所有表情
	 * @param emotion_type state或者face,默认为face。state表情：用于记录与记录转发；face表情：用于短消息、日记和评论
	 * @return 
	 * @throws KxException
	 * @scope basic
	 */
	public Response getEmotion(String emotion_type) throws KxException
	{
		ArrayList<PostParameter> params = new ArrayList<PostParameter>(); 
	    params.add(new PostParameter("emotion_type", emotion_type));
	    return get( "emotion/show", params);
	}
}