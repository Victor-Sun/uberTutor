package com.gnomon.pdms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.ImsConstants;
import com.gnomon.pdms.common.PDMSCommon;


@Service
@Transactional
public class ImsReportService implements ImsConstants{
	public static final String KEY_TOTAL_COUNT="KEY_TOTAL_COUNT";//问题总数
	public static final String KEY_CONTINUE_COUNT="KEY_CONTINUE_COUNT";//继续跟进数
	public static final String KEY_PRECLOSE_COUNT="KEY_PRECLOSE_COUNT";//预关闭数
	public static final String KEY_CLOSE_COUNT="KEY_CLOSE_COUNT";//关闭数
	public static final String KEY_CLOSE_RATE="KEY_CLOSE_RATE";//关闭率
	
	public static final String KEY_ISSUE_STATUS_NAME="ISSUE_STATUS_NAME";//问题状态名称
	public static final String KEY_ISSUE_COUNT="ISSUE_COUNT";//问题数量
	
	public static final String KEY_OBS_NAME="OBS_NAME";//专业领域名称
	public static final String KEY_ISSUE_COUNT_60="ISSUE_COUNT_60";//0状态问题数
	public static final String KEY_ISSUE_COUNT_61="ISSUE_COUNT_61";//1状态问题数
	public static final String KEY_ISSUE_COUNT_62="ISSUE_COUNT_62";//2状态问题数
	public static final String KEY_ISSUE_COUNT_63="ISSUE_COUNT_63";//3状态问题数
	public static final String KEY_ISSUE_COUNT_64="ISSUE_COUNT_64";//4状态问题数
	public static final String KEY_ISSUE_COUNT_65="ISSUE_COUNT_65";//5状态问题数
	public static final String KEY_ISSUE_COUNT_66="ISSUE_COUNT_66";//6状态问题数
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * A/B类问题总体情况
	 * @param programId
	 * @return
	 */
	public Map<String,Object> getIssueTotalStatus(List<String> programIdList) {
		Map<String,Object> map = new HashMap<String,Object>();

		String sql="SELECT COUNT(1) FROM  V_IMS_ISSUE  "
				+ "WHERE ISSUE_LEVEL_CODE IN ('"+ISSUE_LEVEL_A+"','"+ISSUE_LEVEL_B+"') ";

		if(programIdList != null && !programIdList.isEmpty()){
			sql += " AND PROJECT_ID IN (";
			for (int i=0;i<programIdList.size();i++) {
				String programId  = programIdList.get(i);
				sql += "'"+programId+"'";
				if(i < programIdList.size()-1){
					sql +=",";
				}else{
					sql +=")";
				}
			}
		}
		
		String sql1 = sql + "AND ISSUE_STATUS_CODE IN (?, ?)";
		Integer continueCount = jdbcTemplate.queryForInt(sql1,
				ISSUE_STATUS_60,ISSUE_STATUS_61);
		sql1 = sql + "AND ISSUE_STATUS_CODE IN (? , ? , ? )";
		Integer precloseCount = jdbcTemplate.queryForInt(sql1,
				ISSUE_STATUS_62,ISSUE_STATUS_63,ISSUE_STATUS_64);
		
		sql1 = sql + "AND ISSUE_STATUS_CODE IN (?, ? )";
		Integer closeCount = jdbcTemplate.queryForInt(sql1,
				ISSUE_STATUS_65,ISSUE_STATUS_66);
		
		Integer totalCount = continueCount + precloseCount + closeCount;
		map.put(KEY_TOTAL_COUNT, totalCount);
		map.put(KEY_CONTINUE_COUNT, continueCount);
		map.put(KEY_PRECLOSE_COUNT, precloseCount);
		map.put(KEY_CLOSE_COUNT, closeCount);
//		BigDecimal r = new BigDecimal((double)closeCount/totalCount).setScale(2,RoundingMode.HALF_UP);
//		map.put(KEY_CLOSE_RATE, r);
		
		return map;
	}
	
	/**
	 * 质量问题仪表盘
	 */
	public List<Map<String,Object>> getIssueStatus(
			List<String> programIdList, String vehicleId, boolean judgePreviledge){
		String whereCond = "";
		if(programIdList != null && !programIdList.isEmpty()){
			whereCond += " AND PROJECT_ID IN (";
			for (int i=0;i<programIdList.size();i++) {
				String programId  = programIdList.get(i);
				whereCond += "'"+programId+"'";
				if(i < programIdList.size()-1){
					whereCond +=",";
				}else{
					whereCond +=")";
				}
			}
		}
		
		// 车型条件追加
		if (PDMSCommon.isNotNull(vehicleId)) {
			whereCond += " AND T2.SUB_PROJECT_ID = '" + vehicleId + "' ";
		}
		
		// 项目管理相关查询权限追加
	    /*String sql="SELECT T1.NAME ISSUE_STATUS_NAME,COUNT(1) ISSUE_COUNT FROM IMS_CODE_TABLE T1 "+
					"INNER JOIN V_IMS_ISSUE T2 ON T1.CODE = T2.ISSUE_STATUS_CODE "+whereCond+ 
					"WHERE T1.CODE IN(?,?,?,?,?) "+" GROUP BY T1.NAME "+
					"UNION "+
					"SELECT T1.NAME ISSUE_STATUS_NAME,0 ISSUE_COUNT FROM IMS_CODE_TABLE T1 "+
					"LEFT JOIN V_IMS_ISSUE T2 ON T1.CODE = T2.ISSUE_STATUS_CODE "+whereCond+ 
					"WHERE T1.CODE IN(?,?,?,?,?) AND T2.ISSUE_STATUS_CODE IS NULL GROUP BY T1.NAME "+
					"ORDER BY ISSUE_STATUS_NAME";*/

		String loginUser = SessionData.getLoginUserId();
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT");
		sql.append(" T1.NAME ISSUE_STATUS_NAME");
		sql.append(",COUNT(1) ISSUE_COUNT");
		sql.append(" FROM");
		sql.append(" IMS_CODE_TABLE T1");
		sql.append(" INNER JOIN V_IMS_INPROCESS_QUALITY_ISSUE T2 ON");
		sql.append(" T1.CODE = T2.ISSUE_STATUS_CODE");
		sql.append(whereCond);
		if (judgePreviledge) {
			sql.append(" AND PKG_PERMISSION.CAN_VIEW_PROGRAM_VEHICLE(?, T2.SUB_PROJECT_ID) = 1");
			params.add(loginUser);
		}
		sql.append(" WHERE");
		sql.append(" T1.CODE IN(?,?,?,?,?)");
		sql.append(" GROUP BY T1.NAME");
		params.add(ISSUE_STATUS_61);
		params.add(ISSUE_STATUS_62);
		params.add(ISSUE_STATUS_63);
		params.add(ISSUE_STATUS_64);
		params.add(ISSUE_STATUS_65);
		sql.append(" UNION");
		sql.append(" SELECT");
		sql.append(" T1.NAME ISSUE_STATUS_NAME");
		sql.append(",0 ISSUE_COUNT");
		sql.append(" FROM");
		sql.append(" IMS_CODE_TABLE T1");
		sql.append(" LEFT JOIN V_IMS_INPROCESS_QUALITY_ISSUE T2 ON");
		sql.append(" T1.CODE = T2.ISSUE_STATUS_CODE");
		sql.append(whereCond);
		if (judgePreviledge) {
			sql.append(" AND PKG_PERMISSION.CAN_VIEW_PROGRAM_VEHICLE(?, T2.SUB_PROJECT_ID) = 1");
			params.add(loginUser);
		}
		sql.append(" WHERE");
		sql.append(" T1.CODE IN(?,?,?,?,?)");
		sql.append(" AND T2.ISSUE_STATUS_CODE IS NULL");
		sql.append(" GROUP BY T1.NAME");
		sql.append(" ORDER BY ISSUE_STATUS_NAME");
		params.add(ISSUE_STATUS_61);
		params.add(ISSUE_STATUS_62);
		params.add(ISSUE_STATUS_63);
		params.add(ISSUE_STATUS_64);
		params.add(ISSUE_STATUS_65);
		
		return jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
	public List<Map<String,Object>> getIssueStatusOfObs(
			List<String> programIdList, String vehicleId, boolean judgePreviledge){
		String whereCond = "";
		if(programIdList != null && !programIdList.isEmpty()){
			whereCond += " AND PROJECT_ID IN (";
			for (int i=0;i<programIdList.size();i++) {
				String programId  = programIdList.get(i);
				whereCond += "'"+programId+"'";
				if(i < programIdList.size()-1){
					whereCond +=",";
				}else{
					whereCond +=")";
				}
			}
		}
		
		// 车型条件追加
		if (PDMSCommon.isNotNull(vehicleId)) {
			whereCond += " AND T1.SUB_PROJECT_ID = '" + vehicleId + "' ";
		}
		
		// 项目管理相关查询权限追加
		/*String sql="SELECT "+
					"   T3.OBS_NAME,                                                                      "+
					"   COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_61' THEN 1 ELSE NULL END) ISSUE_COUNT_61,   "+
					"   COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_62' THEN 1 ELSE NULL END) ISSUE_COUNT_62,   "+
					"   COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_63' THEN 1 ELSE NULL END) ISSUE_COUNT_63,   "+
					"   COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_64' THEN 1 ELSE NULL END) ISSUE_COUNT_64,   "+
					"   COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_65' THEN 1 ELSE NULL END) ISSUE_COUNT_65    "+
//					"   COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_66' THEN 1 ELSE NULL END) ISSUE_COUNT_66    "+
					" FROM V_IMS_ISSUE T1,IMS_CODE_TABLE T2,PM_OBS T3                                      "+
					" WHERE T1.ISSUE_STATUS_CODE=T2.CODE AND T1.DEPT_ID=T3.ID                             "+
					" AND T1.ISSUE_STATUS_CODE IN(?,?,?,?,?)"+  whereCond +
					" GROUP BY T3.OBS_NAME";*/

		String loginUser = SessionData.getLoginUserId();
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT");
		sql.append(" T3.NAME AS DEPT_NAME");
		sql.append(",COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_61' THEN 1 ELSE NULL END) ISSUE_COUNT_61");
		sql.append(",COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_62' THEN 1 ELSE NULL END) ISSUE_COUNT_62");
		sql.append(",COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_63' THEN 1 ELSE NULL END) ISSUE_COUNT_63");
		sql.append(",COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_64' THEN 1 ELSE NULL END) ISSUE_COUNT_64");
		sql.append(",COUNT(CASE T2.CODE WHEN 'ISSUE_STATUS_65' THEN 1 ELSE NULL END) ISSUE_COUNT_65");
		sql.append(" FROM");
		sql.append(" V_IMS_ISSUE T1");
		sql.append(",IMS_CODE_TABLE T2");
		sql.append(",SYS_DEPARTMENT T3");
		sql.append(" WHERE");
		sql.append(" T1.ISSUE_STATUS_CODE = T2.CODE");
		sql.append(" AND T1.DEPT_ID = T3.ID");
		sql.append(" AND T1.ISSUE_STATUS_CODE IN(?,?,?,?,?)");
		sql.append(" AND T1.ISSUE_NATURE_ID=?");
		sql.append(whereCond);
		params.add(ISSUE_STATUS_61);
		params.add(ISSUE_STATUS_62);
		params.add(ISSUE_STATUS_63);
		params.add(ISSUE_STATUS_64);
		params.add(ISSUE_STATUS_65);
		params.add(ISSUE_NATURE_1);//只查询质量问题
		if (judgePreviledge) {
			sql.append(" AND PKG_PERMISSION.CAN_VIEW_PROGRAM_VEHICLE(?, T1.SUB_PROJECT_ID) = 1");
			params.add(loginUser);
		}
		sql.append(" GROUP BY T3.NAME ");
		sql.append(" ORDER BY T3.NAME ");
		
		return jdbcTemplate.queryForList(sql.toString(), params.toArray());
	}
	
}