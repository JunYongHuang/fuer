package kx2_4j.model.users;


import kx2_4j.http.KxException;
import kx2_4j.http.Response;
import kx2_4j.org.json.JSONArray;
import kx2_4j.org.json.JSONException;
import kx2_4j.org.json.JSONObject;

/**
 * A data class representing Basic user information element
 */
public class User{
    private long uid;		//开心网uid
    private String name;	//真实姓名
    private int gender;     //性别, 0:男    1:女
    private String hometown;//家乡
    private String logo50;	//50*50头像url地址
    private String city;	//现居住地
    private int status;		//状态    0:其它    1:学生    2:已工作
    private String logo120;	//头像120 x 120
    private String birthday;//生日	scope： user_birthday
    private String bodyform;//体型    scope： user_bodyform 0:保密    1:苗条    2:匀称    3:健壮    4:高大    5:小巧    6:丰满    7:高挑    8:较胖    9:较瘦    10:运动型
    private String blood;	//血型    scope： user_blood 0:没有填写    1:O型血    2:A型血    3:B型血    4:AB型血    5:稀有血型 
    private String marriage;//婚姻状态    scope： user_marriage 0:没有填写    1:单身    2:恋爱中    3:订婚    4:已婚    5:离异 
    private String trainwith;//希望结交    scope： user_intro
    private String interest;//兴趣爱好    scope： user_intro
    private String favbook;	//喜欢的书籍    scope： user_intro
    private String favmovie;//喜欢的电影    scope： user_intro
    private String favtv;	//喜欢的电视剧    scope： user_intro
    private String idol;	//偶像    scope： user_intro
    private String motto;	//座右铭    scope： user_intro
    private String wishlist;//愿望列表    scope： user_intro
    private String intro;	//介绍    scope： user_intro
    private Education[] education;	//教育经历	scope： user_education
    private Career[] career;			//工作经历	scope：user_career
    private int isStar; 	//是否公共主页    0:否    1:是
    private Pinyin pinyin;	//用户的姓名拼音
    private int online; 	//用户是否在线    0:不在线    1:在线 scope： user_online
    
    public User(Response res) throws KxException {
        super();
        init(res.asJSONObject());
    }

    private void init(JSONObject json) throws KxException {
        if (json != null) {
            try {
                uid = json.getLong("uid");
                name = json.getString("name");
                gender = json.getInt("gender");
                hometown = json.getString("hometown");
                logo50 = json.getString("logo50");
                city = json.getString("city");
                status = json.getInt("status");
                logo120 = json.getString("logo120");
                birthday = json.getString("birthday");
                bodyform = json.getString("bodyform");
                blood = json.getString("blood");
                marriage = json.getString("marriage");
                trainwith = json.getString("trainwith");
                interest = json.getString("interest");
                favbook = json.getString("favbook");
                favmovie = json.getString("favmovie");
                favtv = json.getString("favtv");
                idol = json.getString("idol");
                motto = json.getString("motto");
                wishlist = json.getString("wishlist");
                intro = json.getString("intro");
                isStar = json.getInt("isStar");
                online = json.getInt("online");
                if(json.getString("education").length() != 0)
                {
                    JSONArray educationJsonArr = json.getJSONArray("education");
                    int len = educationJsonArr.length();
                	education = new Education[len];
                	for (int i = 0; i < len; i++)
                    {
                		education[i] = new Education(educationJsonArr.getJSONObject(i));
                    }
                }
                if(json.getString("career").length() != 0)
                {
                    JSONArray careerJsonArr = json.getJSONArray("career");
                    int len = careerJsonArr.length();
                    career = new Career[len];
                    for (int i = 0; i < len; i++)
                    {
                    	career[i] = new Career(careerJsonArr.getJSONObject(i));
                    }
                }
                if(json.getString("pinyin").length() != 0)
                {
                	pinyin = new Pinyin(json.getJSONObject("pinyin"));
                }
            } catch (JSONException jsone) {
                throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }
    
    public User(JSONObject json) throws KxException {
        super();
        init(json);
    }
    
	public User() {
		// TODO Auto-generated constructor stub
	}
	
    /**
     * Returns the uid of the user
     *
     * @return the uid of the user
     */
    public long getUid() {
        return uid;
    }

    /**
     * Returns the name of the user
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the hometown of the user
     *
     * @return the hometown of the user
     */
    public String getHometown() {
        return hometown;
    }
    
    public String getMotto() {
        return motto;
    }

    public int getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBlood() {
        return blood;
    }

    public String getBodyform() {
        return bodyform;
    }

    public Career[] getCareer() {
        return career;
    }
    
    public Education[] getEducation() {
        return education;
    }

    public String getFavbook() {
        return favbook;
    }

    public String getFavmovie() {
        return favmovie;
    }

    public String getFavtv() {
        return favtv;
    }

    public String getIdol() {
        return idol;
    }

    public String getInterest() {
        return interest;
    }

    public String getIntro() {
        return intro;
    }

    public int getIsStar() {
        return isStar;
    }

    public int getOnline() {
        return online;
    }
    
    public int getStatus() {
        return status;
    }

    public String getLogo120() {
        return logo120;
    }

    public String getLogo50() {
        return logo50;
    }

    public String getMarriage() {
        return marriage;
    }
    
    public String getTrainwith() {
        return trainwith;
    }

    public String getWishlist() {
        return wishlist;
    }
    
    public Pinyin getPinyin(){
    	return pinyin;
    }
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (uid ^ (uid >>> 32));
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
        User other = (User) obj;
        if (uid != other.uid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() 
    {
    	StringBuilder educations = new StringBuilder("");
    	if(education != null){
	        int len = education.length;
	
	        
	        for (int i = 0; i < len; i++) {
	            educations.append(education[i]);
	
	            if (i < len - 1) {
	                educations.append(",");
	            }
	        }
    	}

        StringBuilder careers = new StringBuilder("");
    	if(career != null){
	        int len = career.length;
	        for (int i = 0; i < len; i++) {
	            careers.append(career[i]);
	
	            if (i < len - 1) {
	                careers.append(",");
	            }
	        }
    	}
        
        return "User{"
                + "uid=" + uid
                + ", name='" + name + '\''
                + ", hometown='" + hometown + '\''
                + ", logo50='" + logo50 + '\''
                + ", gender ='" + gender + '\''
                + ", logo120='" + logo120 + '\''
                + ", city='" + city + '\''
                + ", status='" + status + '\''
                + ", birthday='" + birthday + '\''
                + ", bodyform ='" + bodyform + '\''
                + ", blood='" + blood + '\''
                + ", marriage=" + marriage
                + ", trainwith=" + trainwith
                + ", interest=" + interest
                + ", favbook=" + favbook
                + ", favmovie='" + favmovie + '\''
                + ", favtv='" + favtv + '\''
                + ", idol=" + idol
                + ", motto=" + motto
                + ", wishlist=" + wishlist
                + ", intro=" + intro
                + ", educations[" + educations.toString()
                + "]"
                + ", careers[" + careers.toString()
                + "]"
                + ", isStar='" + isStar + '\''
                + (pinyin==null ? "" : ", pinyin='" + pinyin.toString() + '\'')
                + ", online='" + online + '\''
                + '}';
    }

    public class Education {

        private String schooltype;	//学校类型    大学：0，高中：1，技校：2，初中：3，小学：4
        private String school;		//学校
        private String strClass;	//班级
        private String year;		//入学年份

        public Education(JSONObject json) throws KxException {
            init(json);
        }

        private void init(JSONObject json) throws KxException {
            if (json != null) {
                try {
                    schooltype = json.getString("schooltype");
                    school = json.getString("school");
                    strClass = json.getString("class");
                    year = json.getString("year");
                } catch (JSONException jsone) {
                    throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
                }
            }
        }

        public String getSchool() {
            return school;
        }

        public String getSchooltype() {
            return schooltype;
        }

        public String getStrClass() {
            return strClass;
        }

        public String getYear() {
            return year;
        }

        public String toString() {
            return "{"
                    + "schooltype='" + schooltype + '\''
                    + ", school='" + school + '\''
                    + ", strClass='" + strClass + '\''
                    + ", year='" + year + '\''
                    + '}';
        }
    }
    
    public class Career {

        private String company;		//公司名称
        private String dept;		//所在部门
        private String beginyear;	//当前工作开始年份
        private String beginmonth;	//当前工作开始月份
        private String endyear;		//当前工作结束年份
        private String endmonth;	//当前工作结束月份

        public Career(JSONObject json) throws KxException {
            init(json);
        }

        private void init(JSONObject json) throws KxException {
            if (json != null) {
                try {
                    company = json.getString("company");
                    dept = json.getString("dept");
                    beginyear = json.getString("beginyear");
                    beginmonth = json.getString("beginmonth");
                    endyear = json.getString("endyear");
                    endmonth = json.getString("endmonth");
                } catch (JSONException jsone) {
                    throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
                }
            }
        }

        public String getBeginmonth() {
            return beginmonth;
        }

        public String getBeginyear() {
            return beginyear;
        }

        public String getCompany() {
            return company;
        }

        public String getDept() {
            return dept;
        }

        public String getEndmonth() {
            return endmonth;
        }

        public String getEndyear() {
            return endyear;
        }

        public String toString() {
            return "{"
                    + "company='" + company + '\''
                    + ", dept='" + dept + '\''
                    + ", beginyear='" + beginyear + '\''
                    + ", beginmonth='" + beginmonth + '\''
                    + ", endyear='" + endyear + '\''
                    + ", endmonth='" + endmonth + '\''
                    + '}';
        }
    }
    
    public class Pinyin{

        private String firststr;	//首字母拼音，如开心网：kxw
        private String pinyinstr;	//全拼，如kaixinwang

        public Pinyin(JSONObject json) throws KxException {
            init(json);
        }

        private void init(JSONObject json) throws KxException {
            if (json != null) {
                try {
                	firststr = json.getString("firststr");
                	pinyinstr = json.getString("pinyinstr");
                } catch (JSONException jsone) {
                    throw new KxException(jsone.getMessage() + ":" + json.toString(), jsone);
                }
            }
        }

        public String getFirststr() {
            return firststr;
        }

        public String getPinyinstr() {
            return pinyinstr;
        }
        
        public String toString() {
            return "{"
                    + "firststr='" + firststr + '\''
                    + ", pinyinstr='" + pinyinstr + '\''
                    + '}';
        }
    }

    
    public static User[] jsons2objs(JSONArray jsons) throws KxException, JSONException
    {
    	if(null == jsons){
    		return new User[0];
    	}
    	int len = jsons.length();
    	User[] objs = new User[len];
    	for(int i = 0; i < len; i++){
    		objs[i] = new User(jsons.getJSONObject(i));
    	}
    	return objs;
    }
}