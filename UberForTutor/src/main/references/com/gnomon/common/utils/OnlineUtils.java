package com.gnomon.common.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springside.modules.utils.PropertiesUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.ubertutor.entity.UserEntity;

/**
 * 
 * Redis�����д������key��
 * 1.SID_PREFIX��ͷ����ŵ�½�û���SessionId��ClientUser��Json����
 * 2.UID_PREFIX��ͷ����ŵ�¼�û���UID��SessionId���ڵ�����
 * 3.VID_PREFIX��ͷ�����λ��ָ��ҳ���û������ݣ���Ajaxһ��ʹ�ã�����ʵ��ָ��ҳ��ͬʱ������������ƹ��ܣ�
 * 
 * @ClassName: OnlineUtils
 * @Description: �����б����������
 *
 */
public class OnlineUtils {
    
    //KEYֵ����SessionID����    
    private static final String SID_PREFIX = "online:sid:";
    private static final String UID_PREFIX = "online:uid:";
    private static final String VID_PREFIX = "online:vid:";
    private static final int OVERDATETIME = 30 * 60;
    private static final int BROADCAST_OVERDATETIME = 70;//Ajaxÿ60�뷢��һ�Σ�����BROADCAST_OVERDATETIMEʱ�䳤��δ�����ʾ�Ѿ��뿪��ҳ��
    
    
    public static boolean getIsUseRedis() {
		Properties p = null;
		try {
			p = PropertiesUtils.loadProperties("classpath:/redis.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "true".equals(p.getProperty("redis.isUse"));
	}

	public static void login(String sid,UserEntity user){
        
        Jedis jedis = RedisPoolUtils.getJedis();

        jedis.setex(SID_PREFIX+sid, OVERDATETIME, userToString(user));
        jedis.setex(UID_PREFIX+user.getId(), OVERDATETIME, sid);
        
        RedisPoolUtils.release(jedis);
    }
    
    public static void broadcast(String uid,String identify){
        
        if(uid==null||"".equals(uid)) //�쳣���ݣ���������µ�½�û��Żᷢ�������
            return ;
        
        Jedis jedis = RedisPoolUtils.getJedis();
        
        jedis.setex(VID_PREFIX+identify+":"+uid, BROADCAST_OVERDATETIME, uid);
        
        RedisPoolUtils.release(jedis);
    }
    
    
    private static String userToString(UserEntity user){
        JsonConfig  config = new JsonConfig();
        JSONObject obj = JSONObject.fromObject(user,config);

        return obj.toString();
    }
    
    /**
     * 
     * @Title: logout
     * @Description: �˳�
     * @param @param sessionId
     * @return void
     * @throws
     */
    public static void logout(String sid,String uid){
        
        Jedis jedis = RedisPoolUtils.getJedis();
        
        jedis.del(SID_PREFIX+sid);
        jedis.del(UID_PREFIX+uid);
        
        RedisPoolUtils.release(jedis);
    }
    
    /**
     * 
     * @Title: logout
     * @Description: �˳�
     * @param @param UserId  ʹָ���û�����
     * @return void
     * @throws
     */
    public static void logout(String uid){
        Jedis jedis = RedisPoolUtils.getJedis();
        
        //ɾ��sid
        jedis.del(SID_PREFIX+jedis.get(UID_PREFIX+uid));
        //ɾ��uid
        jedis.del(UID_PREFIX+uid);
        
        RedisPoolUtils.release(jedis);
    }
    
    public static String getClientUserBySessionId(String sid){
        
        Jedis jedis = RedisPoolUtils.getJedis();
        
        String user = jedis.get(SID_PREFIX+sid);
        
        RedisPoolUtils.release(jedis);
        
        return user;
    }
    
    public static String getClientUserByUid(String uid){
        Jedis jedis = RedisPoolUtils.getJedis();
        
        String user = jedis.get(SID_PREFIX+jedis.get(UID_PREFIX+uid));
        
        RedisPoolUtils.release(jedis);
        
        return user;
    }
    
    /**
     * 
     * @Title: online
     * @Description: ���е�key
     * @return List  
     * @throws
     */
    public static List online(){

        Jedis jedis = RedisPoolUtils.getJedis();
        
        Set online = jedis.keys(SID_PREFIX+"*");
        
        RedisPoolUtils.release(jedis);
        return new ArrayList(online);
    }
    
    /**
     * 
     * @Title: online
     * @Description: ��ҳ��ʾ�����б�
     * @return List  
     * @throws
     */
    public static List onlineByPage(int page,int pageSize) throws Exception{
        
        Jedis jedis = RedisPoolUtils.getJedis();
        
        Set onlineSet = jedis.keys(SID_PREFIX+"*");
        
        
        List onlines =new ArrayList(onlineSet);
        
        if(onlines.size() == 0){
            return null;
        }
        
        Pipeline pip = jedis.pipelined();
        for(Object key:onlines){
            pip.get(getKey(key));
        }
        List result = pip.syncAndReturnAll();
        RedisPoolUtils.release(jedis);
        
//        List<ClientUser> listUser=new ArrayList<ClientUser>();
//        for(int i=0;i<result.size();i++){
//            listUser.add(Constants.json2ClientUser((String)result.get(i)));
//        }
//        Collections.sort(listUser,new Comparator<ClientUser>(){
//            public int compare(ClientUser o1, ClientUser o2) {
//                return o2.getLastLoginTime().compareTo(o1.getLastLoginTime());
//            }
//        });
//        onlines=listUser;
        int start = (page - 1) * pageSize;
        int toIndex=(start+pageSize)>onlines.size()?onlines.size():start+pageSize;
        List list = onlines.subList(start, toIndex);
    
        return list;
    }
    
    private static String getKey(Object obj){
        
        String temp = String.valueOf(obj);
        String key[] = temp.split(":");

        return SID_PREFIX+key[key.length-1];
    }
    
    /**
     * 
     * @Title: onlineCount
     * @Description: ����������
     * @param @return
     * @return int
     * @throws
     */
    public static int onlineCount(){
        
        Jedis jedis = RedisPoolUtils.getJedis();
        
        Set online = jedis.keys(SID_PREFIX+"*");
        
        RedisPoolUtils.release(jedis);
        
        return online.size();
        
    }
    
    /**
     * ��ȡָ��ҳ��������������
     */
    public static int broadcastCount(String identify) {
        Jedis jedis = RedisPoolUtils.getJedis();
        
        Set online = jedis.keys(VID_PREFIX+identify+":*");
        
        

        RedisPoolUtils.release(jedis);
        
        return online.size();
    }
    
    /**
     * �Լ��Ƿ�����
     */
    public static boolean broadcastIsOnline(String identify,String uid) {
        
        Jedis jedis = RedisPoolUtils.getJedis();
        
        String online = jedis.get(VID_PREFIX+identify+":"+uid);
        
        RedisPoolUtils.release(jedis);
        
        return !StringUtils.isBlank(online);//��Ϊ�վʹ����Ѿ��ҵ������ˣ�Ҳ����������
    }
    
    /**
     * ��ȡָ��ҳ��������������
     */
    public static int broadcastCount() {
        Jedis jedis = RedisPoolUtils.getJedis();
        
        Set online = jedis.keys(VID_PREFIX+"*");
        
        RedisPoolUtils.release(jedis);
        
        return online.size();
    }
    
    
    /**
     * 
     * @Title: isOnline
     * @Description: ָ���˺��Ƿ��½
     * @param @param sessionId
     * @param @return
     * @return boolean 
     * @throws
     */
    public static boolean isOnline(String uid){
        
        Jedis jedis = RedisPoolUtils.getJedis();
        
        boolean isLogin = jedis.exists(UID_PREFIX+uid);
        
        RedisPoolUtils.release(jedis);
        
        return isLogin;
    }
    
    public static boolean isOnline(String uid,String sid){
        
        Jedis jedis = RedisPoolUtils.getJedis();
        
        String loginSid = jedis.get(UID_PREFIX+uid);
        
        RedisPoolUtils.release(jedis);
        
        return sid.equals(loginSid);
    }
    
    public static boolean isUseRedis(){
    	return getIsUseRedis();
    }
    
    public static void main(String[] args) {
		try {
			Jedis jedis = RedisPoolUtils.getJedis();
//			jedis.set("AAA", "AAAAA");
			System.out.print(jedis.get("AAA"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}