package com.gnomontech.pdms.redis;
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
 * Redis缓存中存放两组key：
 * 1.SID_PREFIX开头，存放登陆用户的SessionId与ClientUser的Json数据
 * 2.UID_PREFIX开头，存放登录用户的UID与SessionId对于的数据
 * 3.VID_PREFIX开头，存放位于指定页面用户的数据（与Ajax一起使用，用于实现指定页面同时浏览人数的限制功能）
 * 
 * @ClassName: OnlineUtils
 * @Description: 在线列表操作工具类
 *
 */
public class OnlineUtils {
    
    //KEY值根据SessionID生成    
    private static final String SID_PREFIX = "online:sid:";
    private static final String UID_PREFIX = "online:uid:";
    private static final String VID_PREFIX = "online:vid:";
    private static final int OVERDATETIME = 30 * 60;
    private static final int BROADCAST_OVERDATETIME = 70;//Ajax每60秒发起一次，超过BROADCAST_OVERDATETIME时间长度未发起表示已经离开该页面
    
    
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
        
        if(uid==null||"".equals(uid)) //异常数据，正常情况下登陆用户才会发起该请求
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
     * @Description: 退出
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
     * @Description: 退出
     * @param @param UserId  使指定用户下线
     * @return void
     * @throws
     */
    public static void logout(String uid){
        Jedis jedis = RedisPoolUtils.getJedis();
        
        //删除sid
        jedis.del(SID_PREFIX+jedis.get(UID_PREFIX+uid));
        //删除uid
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
     * @Description: 所有的key
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
     * @Description: 分页显示在线列表
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
     * @Description: 总在线人数
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
     * 获取指定页面在线人数总数
     */
    public static int broadcastCount(String identify) {
        Jedis jedis = RedisPoolUtils.getJedis();
        
        Set online = jedis.keys(VID_PREFIX+identify+":*");
        
        

        RedisPoolUtils.release(jedis);
        
        return online.size();
    }
    
    /**
     * 自己是否在线
     */
    public static boolean broadcastIsOnline(String identify,String uid) {
        
        Jedis jedis = RedisPoolUtils.getJedis();
        
        String online = jedis.get(VID_PREFIX+identify+":"+uid);
        
        RedisPoolUtils.release(jedis);
        
        return !StringUtils.isBlank(online);//不为空就代表已经找到数据了，也就是上线了
    }
    
    /**
     * 获取指定页面在线人数总数
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
     * @Description: 指定账号是否登陆
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