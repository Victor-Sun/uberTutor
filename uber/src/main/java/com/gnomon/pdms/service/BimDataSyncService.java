package com.gnomon.pdms.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.pdms.constant.PDMSConstants;

/**
 * 数据同步服务类
 * @author Frank
 *
 */
@Service
@Transactional
public class BimDataSyncService {
	Log log = LogFactory.getLog(getClass());
	
	
	private static final String DEFAULT_ROLE_ID = "EMPLOYEE";//默认导入的用户的角色是工程师
	private static final String DEFAULT_USER_PASSWORD = "6QA/RqOU/pVcoL6ZK3sIRjwT0LbogBdw";//用户默认密码，1
	private String bimServerUrl;
	private String bimSystemCode;
	private String bimUsername;
	private String bimPassword;
	private String restfulTargetOrganizationServiceFindBySystemCode;
	private String restfulTargetAccountServiceFindXAttrBySystemCode;
	private String restfulTargetAccountServiceFindBy;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
//	@Autowired
//	private SysDataSyncLogServcie sysDataSyncLogServcie;

	@Value("#{application['bim.server.url']}")
	public void setBimServerUrl(String bimServerUrl) {
		this.bimServerUrl = bimServerUrl;
	}

	@Value("#{application['bim.systemCode']}")
	public void setBimSystemCode(String bimSystemCode) {
		this.bimSystemCode = bimSystemCode;
	}
	
	@Value("#{application['bim.restful.TargetOrganizationService.findBySystemCode']}")
	public void setRestfulTargetOrganizationServiceFindBySystemCode(
			String restfulTargetOrganizationServiceFindBySystemCode) {
		this.restfulTargetOrganizationServiceFindBySystemCode = restfulTargetOrganizationServiceFindBySystemCode;
	}

	@Value("#{application['bim.restful.TargetAccountService.findXAttrBySystemCode']}")
	public void setRestfulTargetAccountServiceFindXAttrBySystemCode(
			String restfulTargetAccountServiceFindXAttrBySystemCode) {
		this.restfulTargetAccountServiceFindXAttrBySystemCode = restfulTargetAccountServiceFindXAttrBySystemCode;
	}

	@Value("#{application['bim.restful.TargetAccountService.findBy']}")
	public void setRestfulTargetAccountServiceFindBy(String restfulTargetAccountServiceFindBy) {
		this.restfulTargetAccountServiceFindBy = restfulTargetAccountServiceFindBy;
	}

	
	@Value("#{application['bim.restful.username']}")	
	public void setBimUsername(String bimUsername) {
		this.bimUsername = bimUsername;
	}

	@Value("#{application['bim.restful.password']}")	
	public void setBimPassword(String bimPassword) {
		this.bimPassword = bimPassword;
	}

	public void syncOrgAndUserFromBim(){
		try{
			log.info("BimSyncJob.syncOrgAndUserFromBim Start.");
			syncOrgFromBim();
			syncUserFromBim();
			log.info("BimSyncJob.syncOrgAndUserFromBim Finished.");
		}catch(Exception e){
			log.error("BimSyncJob.syncOrgAndUserFromBim throw exception.",e);
			throw new RuntimeException(e);
		}
	}
	
//	public String beginSync(){
//		return sysDataSyncLogServcie.beginSync(PDMSConstants.SYSTEM_NAME_BIM,PDMSConstants.DIRECTION_INPUT,"BimDataSyncService.syncOrgAndUserFromBim");
//	}
//	
//	public void finishSyncWithSuccess(String syncLogId,String message){
//		sysDataSyncLogServcie.finisheSync(syncLogId, true, message);
//	}
//	
//	public void finishSyncWithException(String syncLogId,Exception e){
//		sysDataSyncLogServcie.finishSyncWithException(syncLogId, e);
//	}
//	

	
	/**
	 * 从BIM同步组织信息
	 */
	public void syncOrgFromBim(){
		//获取组织机构全量同步数据
		if(StringUtils.isNotEmpty(bimServerUrl)){
			String url=bimServerUrl+restfulTargetOrganizationServiceFindBySystemCode;
			Map<String,String> params = new HashMap<String,String>();
			params.put("systemCode", bimSystemCode);
			params.put("useDefaultFilters", "false");{
				
			}
			String data = "";
			if(!StringUtils.isEmpty(url)){
				data = getDataFromServer(url,params);
				JSONArray ja = JSONArray.fromObject(data);
				for(int i=0;i<ja.size();i++){
					JSONObject jo =(JSONObject)ja.get(i);
					importOrgData(jo);
				}
			}
		}
	
		 
	}
	
	private void importOrgData(JSONObject jo) {
		System.out.println(jo.toString());
		//读取一条组织信息
		String id = jo.getString("id");
		String guid = jo.getString("guid");
		String parentId = null;
		if(jo.containsKey("parentId")){
			parentId = jo.getString("parentId");
		}
		
		String code = null;
		if(!jo.containsKey("code")){
			System.err.println("组织数据错误，没有CODE，忽略！\n data="+jo.toString());
			return;//忽略没有code的组织
		}
		code = jo.getString("code");
		String name = jo.getString("name");
		String isDeleted = jo.getString("isDeleted");
		String deleteBy = null;
		Date deleteDate = null;
		if("true".equals(isDeleted)){
			deleteBy = PDMSConstants.SYSTEM_USER_ID;
			deleteDate = new Date();
		}
		
		//查看数据库是否存在
		String sql = "SELECT count(1) FROM SYS_DEPARTMENT WHERE ID=?";
		int c = jdbcTemplate.queryForInt(sql,id);
		
		//如果不存在，则插入记录
		if(c == 0 ){
			sql="INSERT INTO SYS_DEPARTMENT(ID,CODE,NAME,PARENT_ID,CREATE_DATE,CREATE_BY,DELETE_DATE,DELETE_BY,GUID) VALUES(?,?,?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql,id,code,name,parentId,new Date(),PDMSConstants.SYSTEM_USER_ID,deleteDate,deleteBy,guid);
		}
		//如果存在,则更新记录
		else{
			sql="UPDATE SYS_DEPARTMENT SET CODE=?,NAME=?,PARENT_ID=?,UPDATE_DATE=?,UPDATE_BY=?,DELETE_DATE=?,DELETE_BY=? WHERE ID=?";
			jdbcTemplate.update(sql,code,name,parentId,new Date(),PDMSConstants.SYSTEM_USER_ID,deleteDate,deleteBy,id);
		}
	}

	/**
	 * 从BIM同步用户信息
	 */
	public void syncUserFromBim(){
		//获取组织机构全量同步数据
		if(StringUtils.isNotEmpty(bimServerUrl)){
			String url=bimServerUrl+restfulTargetAccountServiceFindXAttrBySystemCode;
			Map<String,String> params = new HashMap<String,String>();
			params.put("systemCode", bimSystemCode);
			params.put("includeIsDisabled", "true");
			params.put("includeIsDeleted", "true");
			String data = getDataFromServer(url,params);
	//		try {
	//			FileWriter fw = new FileWriter("/tmp/userdata.json");
	//			fw.write(data);
	//			fw.close();
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
			Map<String, String> positonRoleIdMap = getPositonRoleIdMap();
			JSONArray ja = JSONArray.fromObject(data);
			for(int i=0;i<ja.size();i++){
				JSONObject jo =(JSONObject)ja.get(i);
				importUserData(jo,positonRoleIdMap);
			}
		}
	}
	
	
	private void importUserData(JSONObject jo,Map<String, String> positonRoleIdMap) {
		System.out.println(jo.toString());
		//读取一条组织信息
		String id = jo.getString("id");
		String guid = jo.getString("guid");
		String organizationId = jo.getString("organizationId");
		String username = jo.getString("username");//账号名
		String fullname = jo.getString("fullname");//员工全名
		String isDeleted = jo.getString("isDeleted");
		String isDisabled = jo.getString("isDisabled");
		if("true".equals(isDisabled)){
			isDisabled = "Y";
		}else{
			isDisabled = "N";
		}
		
		JSONObject data = jo.getJSONObject("data");
		String mobile = null;
		if(data.containsKey("telnumber")){
			mobile = data.getString("telnumber");
		}
		String number = null;
		if(data.containsKey("Code")){
			number = data.getString("Code");
		}
		
		String email = null;
		if(data.containsKey("email")){
			email = data.getString("email");
		}
		
		String post = null;
		if(data.containsKey("post")){
			post = data.getString("post").trim();
		}
		
		//过滤劳务工
		if(StringUtils.isNotEmpty(number)){
			if(number.startsWith("8")){
				System.out.println("过滤劳务工，员工编号："+number);
				return;
			}
		}
		
		//岗位信息映射到角色信息
		String roleId = DEFAULT_ROLE_ID;
		if(positonRoleIdMap.containsKey(post)){
			roleId = positonRoleIdMap.get(post);
		}
		System.out.println("Post mapping to role. post='"+post+"'\troleId='"+roleId+"'");
		
		String deleteBy = null;
		Date deleteDate = null;
		if("true".equals(isDeleted)){
			deleteBy = PDMSConstants.SYSTEM_USER_ID;
			deleteDate = new Date();
		}
		
		//查看数据库是否存在（根据GUID判断）
		String sql = "SELECT count(1) FROM SYS_USER WHERE GUID=?";
		int c = jdbcTemplate.queryForInt(sql,guid);
		
		//如果不存在，则插入记录
		if(c == 0 ){
			sql="INSERT INTO SYS_USER(ID,USERID,USERNAME,EMAIL,MOBILE,EMPLOYEE_NO,CREATE_DATE,CREATE_BY,DELETE_DATE,DELETE_BY,IS_DISABLED,DEPARTMENT_ID,PASSWORD,GUID,POST) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql,id,username,fullname,email,mobile,number,new Date(),PDMSConstants.SYSTEM_USER_ID,deleteDate,deleteBy,isDisabled,organizationId,DEFAULT_USER_PASSWORD,guid,post);
		}
		//如果存在,则更新记录
		else{
			sql="UPDATE SYS_USER SET USERID=?,USERNAME=?,EMAIL=?,MOBILE=?,EMPLOYEE_NO=?,UPDATE_DATE=?,UPDATE_BY=?,DELETE_DATE=?,DELETE_BY=?,IS_DISABLED=?,DEPARTMENT_ID=?,POST=? WHERE ID=?";
			jdbcTemplate.update(sql,username,fullname,email,mobile,number,new Date(),PDMSConstants.SYSTEM_USER_ID,deleteDate,deleteBy,isDisabled,organizationId,post,id);
		}
		
		//更新用户HR角色表
	    sql = "SELECT count(1) FROM SYS_POSITION WHERE USER_ID=?";
		c = jdbcTemplate.queryForInt(sql, username);
		//如果不存在，则插入记录
		if(c == 0 ){
			sql="INSERT INTO SYS_POSITION(ID, USER_ID, NAME, CREATE_DATE, CREATE_BY) VALUES(sys_position_seq.Nextval, ?, ?, SYSDATE, ?)";
			jdbcTemplate.update(sql, username, post, PDMSConstants.SYSTEM_USER_ID);
		}
		//如果存在,则更新记录
		else{
			sql="UPDATE SYS_POSITION SET NAME=? WHERE USER_ID = ?";
			jdbcTemplate.update(sql, post, username);
		}
		
		//更新用户部门角色表
	    sql = "SELECT count(1) FROM SYS_USER_ROLE WHERE USER_ID=?";
		c = jdbcTemplate.queryForInt(sql,username);
		//如果不存在，则插入记录
		if(c == 0 ){
			sql="INSERT INTO SYS_USER_ROLE(ID,USER_ID,ROLE_ID,DEPARTMENT_ID) VALUES(sys_user_role_seq.Nextval,?,?,?)";
			jdbcTemplate.update(sql,username,roleId,organizationId);
		}
		//如果存在,则更新记录（更新部门和角色）
		else{
			sql="UPDATE SYS_USER_ROLE SET DEPARTMENT_ID=?,ROLE_ID=? WHERE USER_ID=?";
			jdbcTemplate.update(sql,organizationId,roleId,username);
		}
	}
	
	
//	private String fetchData(String url,Map<String,String>params){
//		String data = null;
//		  // 创建默认的httpClient实例.    
//      CloseableHttpClient httpclient = HttpClients.createDefault();  
//      // 创建httppost    
//      HttpPost httppost = new HttpPost(url);  
//      // 创建参数队列    
//      List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();  
//      for(String key:params.keySet()){
//    	  String value=params.get(key);
//    	  formparams.add(new BasicNameValuePair(key, value));  
//      }
//      
//	  String authString = bimUsername + ":" + bimPassword;
//	  String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
//	  formparams.add(new BasicNameValuePair("Authorization", "Basic " + authStringEnc));
//	  
//      UrlEncodedFormEntity uefEntity;  
//      try {  
//          uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
//          httppost.setEntity(uefEntity);  
//          System.out.println("executing request " + httppost.getURI());  
//          CloseableHttpResponse response = httpclient.execute(httppost);  
//          try {  
//              HttpEntity entity = response.getEntity();  
//              if (entity != null) {  
//            	  data = EntityUtils.toString(entity, "UTF-8");
//              }  
//          } finally {  
//              response.close();  
//          }  
//      } catch (Exception e) {  
//    	  
//          e.printStackTrace();  
//          throw new RuntimeException(e);
//      } finally {  
//          // 关闭连接,释放资源    
//          try {  
//              httpclient.close();  
//          } catch (IOException e) {  
//              e.printStackTrace();  
//          }  
//      }  
//      return data;
//	}
	

	
	public String getDataFromServer(String sUrl,Map<String,String>params) {
		StringBuilder sb = new StringBuilder();
		try {
			int i=1;
			for(String key:params.keySet()){
				if(i==1){
					sUrl+="?";
				}
				sUrl += key+"="+params.get(key);
				if(i<params.size()){
					sUrl+="&";
				}
				i++;
			}
//			log.info("URL="+sUrl);
			URL url = new URL(sUrl);
			URLConnection urlConnection = setUsernamePassword(url);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();

			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private URLConnection setUsernamePassword(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();
		String authString = bimUsername + ":" + bimPassword;
		String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		return urlConnection;
	}
	
	private Map<String,String> getPositonRoleIdMap(){
		Map<String,String> positonRoleIdMap = new HashMap<String,String> ();
		List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT T2.ROLE_ID, T1.ROLE_NAME,T1.POSITION_NAME FROM SYS_ROLE_POSITION T1, SYS_ROLE T2 WHERE T1.ROLE_NAME=T2.TITLE");
		for(Map<String,Object> m : list){
			String roleId = ObjectConverter.convert2String(m.get("ROLE_ID"));
			String positionNames = ObjectConverter.convert2String(m.get("POSITION_NAME"));
			if(positionNames != null){
				String[] split = positionNames.split(",");
				for(String positionName: split){
					positonRoleIdMap.put(positionName.trim(), roleId);
					System.out.println("positionName="+positionName+"\troleId="+roleId);
				}
			}
		}
		return positonRoleIdMap;
	}
	
	public static void main(String[] args) {
		String authString = "bbcservice" + ":" + "password";
		String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
		System.out.println(authStringEnc);
	}
}