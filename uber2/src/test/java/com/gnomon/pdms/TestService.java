package com.gnomon.pdms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.ObjectConverter;

@Service
@Transactional
public class TestService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> getExt301ItemList(String programVehicleId){
		List<Map<String,Object>> itemList = new ArrayList<Map<String,Object>>();
		
		String sql="select * from v_ext301_item_cp_list where program_vehicle_id=? order by item_id,seq_no";
		List<Map<String, Object>> itemCpList = jdbcTemplate.queryForList(sql,programVehicleId);
		String lastItemId = null;
		Map<String,Object> itemMap = null;
		for(Map<String,Object>itemCp : itemCpList){
			String itemId = ObjectConverter.convert2String(itemCp.get("ITEM_ID"));
			if(!itemId.equals(lastItemId)){
				//添加零件信息
				lastItemId = itemId;
				itemMap = new HashMap<String,Object>();
				itemList.add(itemMap);
				itemMap.put("ITEM_ID", itemId);
				itemMap.put("ITEM_NO", ObjectConverter.convert2String(itemCp.get("ITEM_NO")));
				itemMap.put("ITEM_NAME", ObjectConverter.convert2String(itemCp.get("ITEM_NAME")));
				itemMap.put("OBS_NAME", ObjectConverter.convert2String(itemCp.get("OBS_NAME")));
				itemMap.put("ITEM_OWNER", ObjectConverter.convert2String(itemCp.get("ITEM_OWNER")));
				itemMap.put("SOURCING_TYPE", ObjectConverter.convert2String(itemCp.get("SOURCING_TYPE")));
				itemMap.put("SEVERITY_LEVEL", ObjectConverter.convert2String(itemCp.get("SEVERITY_LEVEL")));
				itemMap.put("IS_KCRP", ObjectConverter.convert2String(itemCp.get("IS_KCRP")));
				itemMap.put("PROGRESS", ObjectConverter.convert2String(itemCp.get("PROGRESS")));
				itemMap.put("PROGRESS_STATUS", ObjectConverter.convert2String(itemCp.get("PROGRESS_STATUS")));
			}
			//添加节点
			String taskCode = ObjectConverter.convert2String(itemCp.get("CODE"));
			Date planFinishDate = ObjectConverter.convert2Date(itemCp.get("PLANNED_FINISH_DATE"));
			Date actualFinishDate = ObjectConverter.convert2Date(itemCp.get("ACTUAL_FINISH_DATE"));
			itemMap.put(taskCode+"-PF", planFinishDate);
			itemMap.put(taskCode+"-AF", actualFinishDate);
			
		}
		
		return itemList;
	}
	
}
