package kx2_4j.model.users;

import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

public class Nverifyinfo {

	/**
	 * 是否做过实名认证
		1：已认证
		0：未认证
	 */
	private int idchecked;
	/**
	 * 是否满18岁
		1：认证通过满18岁
		0：认证年龄不满18 或者未实名认证
	 */
	private int agechecked;
	
    public Nverifyinfo(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    protected void init(JSONObject json) throws KxException {
        if (json != null) {
            try {
            	idchecked = json.getInt("idchecked");
            	agechecked = json.getInt("agechecked");
            } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public Nverifyinfo(JSONObject json) throws KxException {
        super();
        init(json);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idchecked ^ (idchecked >>> 32))
        		+ (int) (agechecked ^ (agechecked >>> 32));
        return result;
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
        Nverifyinfo other = (Nverifyinfo) obj;
        if (idchecked != other.idchecked) {
            return false;
        }
        else if (agechecked != other.agechecked) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{"
                + "idchecked=" + idchecked
                + ", agechecked=" + agechecked
                + '}';
    }
}