
package kx2_4j.model.message;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.model.records.RecordUser;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class MessageModel {
	private long mid;		//短消息ID
	private long ctime;		//创建时间
	private String content;		//短消息内容
	private String uids;		//短消息涉及到的用户的uid
	private Attachment[] attachments;		//附件信息
	private int group;		//是否群发——1：群发，2：不是群发
	private int msg_num;		//单条短消息内的消息总数
	private int unread_num;		//未读消息数
	private int read_status;		//消息是否未读——0：已读，1：未读
	private RecordUser user;
	
    public MessageModel(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
        	try{
        		mid = json.getLong("mid");
				ctime = json.getLong("ctime");
				content = json.getString("content");
				uids = json.getString("uids");
				group = json.getInt("group");
				msg_num = json.getInt("msg_num");
				unread_num = json.getInt("unread_num");
				read_status = json.getInt("read_status");
				user = new RecordUser(json.getJSONObject("user"));
				attachments = Attachment.jsons2objs(json.getJSONArray("attachments"));
	        } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public MessageModel(JSONObject json) throws KxException {
        super();
        init(json);
    }
	
	public MessageModel() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MessageModel other = (MessageModel) obj;
		if (mid != other.mid) {
            return false;
        }
        return true;
    }
	
	@Override
	public String toString()
	{
		return '{'
				+ "mid=" + mid
				+ ", ctime=" + ctime
				+ ", content=" + content
				+ ", uids=" + uids
				+ ", attachments=" + attachments
				+ ", group=" + group
				+ ", msg_num=" + msg_num
				+ ", unread_num=" + unread_num
				+ ", read_status=" + read_status
				+ ", user=" + user
				+ '}';
	}
	
	
	public long getMid()
	{
		return mid;
	}
	
	public long getCtime()
	{
		return ctime;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public String getUids()
	{
		return uids;
	}
	
	public Attachment[] getAttachments()
	{
		return attachments;
	}
	
	public int getGroup()
	{
		return group;
	}
	
	public int getMsg_num()
	{
		return msg_num;
	}
	
	public int getUnread_num()
	{
		return unread_num;
	}
	
	public int getRead_status()
	{
		return read_status;
	}
	
	public RecordUser getUser()
	{
		return user;
	}
    
    public static MessageModel[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new MessageModel[0];
    	}
    	int len = jsons.length();
    	MessageModel[] objs = new MessageModel[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new MessageModel(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}