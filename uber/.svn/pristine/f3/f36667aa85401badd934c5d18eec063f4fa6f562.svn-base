package com.gnomon.pdms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.page.GTJdbcTemplate;

@Service
@Transactional
public class PmPostService {
	
	@Autowired
	private GTJdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getForumList(String sourceId,String source){
		return jdbcTemplate.queryForList("select * from V_PM_FORUM_POST where SOURCE_ID = ? and SOURCE=?",sourceId,source);
	}

	public String hasNewNotify(String sourceId,String source,String userId,String objectTypeCode){
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM V_PM_FORUM_POST where SOURCE_ID = ? and SOURCE=? ORDER BY CREATE_DATE DESC ",sourceId,source);
		if(list == null || list.size() < 1){
			return "N";
		}else{
			Map<String, Object> map = list.get(0);
			Date createDate = (Date)map.get("CREATE_DATE");
			List<Map<String, Object>> accessLogList = jdbcTemplate.queryForList("select * from IMS_LAST_ACCESS_LOG where OBJECT_TYPE_CODE = ? and userId = ? and OBJECT_ID = ? ",objectTypeCode,userId,sourceId);
			if(accessLogList == null || accessLogList.size() < 1){
				return "Y";
			}else{
				Date lastAccessDate = (Date)(accessLogList.get(0).get("LAST_ACCESS_DATE"));
				if(lastAccessDate.before(createDate)){
					return "Y";
				}
			}
		}
		return "N";
	}
}
