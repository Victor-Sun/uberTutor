package jp.co.nec.flowlites.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.gnomon.common.constant.GTConstants;
import com.gnomon.common.utils.CommonUtils;

/**
 * SQL�����ɥ�`�ƥ���ƥ����饹��
 * <p>
 * <a href="FLSqlBuilder.java.html"><i>View Source</i></a>
 * </p>
 *
 */
public final class SqlBuilder
{
  /**
   * �����νY���ξt�����޶����줿SQL�Ĥ�M�����Ƥޤ���
   * <p>
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�ǩ`���٩`���ηN�����{@link Constants#DATABASE_ORACLE}���ϡ�Oracle�ǩ`���٩`���Ǘ�����SQL�Ĥ����ɤ��ޤ���
   * <li>�ǩ`���٩`���ηN�����{@link Constants#DATABASE_SQL_SERVER}���ϡ�SQL
   * Server�ǩ`���٩`���Ǘ�����SQL�Ĥ����ɤ��ޤ���
   * <li>���ɤ���SQL�Ĥ򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�Ǥ��Υ᥽�åɤ�������������νY���ξt�����޶����줿SQL�Ĥ�M�����Ƥޤ���
   * </dl>
   *
   * @param sql ��ͨSQL��
   * @param searchLimit �޶����줿�t��
   * @param dbname �ǩ`���٩`�����
   * @return �޶����줿SQL��
   */
  public static String getLimitSql(String sql, int searchLimit, String dbname) {
    String dbinfo = filterDBInfo(dbname);
    StringBuffer limit_sql = new StringBuffer();
    if (dbinfo.trim().equalsIgnoreCase(GTConstants.DATABASE_ORACLE)) {
      limit_sql.append(" select * from ( ");
      limit_sql.append(sql);
      limit_sql.append(" ) where rownum <=");
      limit_sql.append(searchLimit);
    } else if (dbinfo.trim().equalsIgnoreCase(GTConstants.DATABASE_MSSQL)) {
      Pattern pattern = Pattern.compile(
        "order\\s*by\\s*\\w*\\s*(asc|desc)*(\\s*,\\s*\\w*\\s*(asc|desc)*)*",
        Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(sql);
      String orderBy = "";
      StringBuffer sbSql = new StringBuffer();
      int cnt = 0;
      int startIndex = 0;
      while (matcher.find()) {
        orderBy = matcher.group();
        startIndex = matcher.start();
        cnt++;
      }
      if (cnt > 0) {
        matcher.reset();
        matcher.find(startIndex);
        matcher.appendReplacement(sbSql, "");
        matcher.appendTail(sbSql);
        sql = sbSql.toString();
      }
      limit_sql.append("select top(" + searchLimit + ") * from (");
      limit_sql.append(sql);
      limit_sql.append(") as temp ");
      limit_sql.append(orderBy);
    }
    return limit_sql.toString();
  }

  /**
   * �����ک`�����Έ��ϡ��Ŀ�t����SQL�Ĥ����ɤ��ޤ���
   * <p>
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�ǩ`���٩`���ηN�����{@link Constants#DATABASE_ORACLE}���ϡ�Oracle�ǩ`���٩`���Υ쥳�`������ȡ�ä�����SQL�Ĥ����ɤ��ޤ���
   * <li>�ǩ`���٩`���ηN�����{@link Constants#DATABASE_SQL_SERVER}���ϡ�SQL
   * Server�ǩ`���٩`���Υ쥳�`������ȡ�ä�����SQL�Ĥ����ɤ��ޤ���
   * <li>���ɤ���SQL�Ĥ򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�Ǥ��Υ᥽�åɤ�������������νY���ξt�����޶����줿SQL�Ĥ�M�����Ƥޤ���
   * </dl>
   *
   * @param sql һ�E������SQL��
   * @param dbname �ǩ`���٩`����� del by dhc 2011/10/21 DB2����
   * @param useOrderByFlg OrderByʹ�åե饰��true �� OrderBy��Ф���false �� OrderBy��Ф��ʤ��� add by dhc 2011/10/21 DB2����
   *
   * @return ����ȡ��SQL��
   */
  //public static String getCountSql(String sql, String dbname) {
  public static String getCountSql(String sql, boolean useOrderByFlg) {
    // del by dhc 2011/10/21 DB2����
    //String dbinfo = filterDBInfo(dbname);
    StringBuffer limit_sql = new StringBuffer();
    // upd by dhc 2011/10/21 DB2���� -- start
    //if (dbinfo.trim().equalsIgnoreCase(Constants.DATABASE_ORACLE)) {
    if (useOrderByFlg) {
    // upd by dhc 2011/10/21 DB2���� -- end
      limit_sql.append(" select count(1) as count from ( ");
      limit_sql.append(sql);
      limit_sql.append(" ) temptable");
    // upd by dhc 2011/10/21 DB2���� -- start
    //} else if (dbinfo.trim().equalsIgnoreCase(Constants.DATABASE_MSSQL)) {
    } else {
    // upd by dhc 2011/10/21 DB2���� -- end
      /* ������:GuoDecai ԭ��:OrderBy������������ �r�g:2008/09/23
      Pattern pattern = Pattern.compile(
    		  "\\s+order\\s+by\\s+\\w+\\.?\\w*\\s*(asc|desc)*(\\s*,\\s*\\w+\\.?\\w*\\s*(asc|desc)*)*",
        Pattern.CASE_INSENSITIVE);
      */
     Pattern pattern = Pattern
                    .compile(
                            // upd by dhc 2010/03/30 �Ϻ��gƱ_����_20100323_01 order by �ˡ�[��]�����������ϤΌ���
                            //"\\s+order\\s+by\\s+(\\w*[^\\x00-\\xff]*)*+\\.?(\\w*[^\\x00-\\xff]*)*\\s*(asc|desc)*(\\s*,\\s*(\\w*[^\\x00-\\xff]*)*+\\.?(\\w*[^\\x00-\\xff]*)*\\s*(asc|desc)*)*",
                            // upd by dhc 2010/03/30 �Ϻ��gƱ_��ƺ_20100406_01 order by �ΥƩ`�֥����ˡ�[��]�����������ϤΌ���
                            //"\\s+order\\s+by\\s+(\\w*[^\\x00-\\xff]*)*+\\.?(\\w*[^\\x00-\\xff]*(\\x5b|\\x5d)?)*\\s*(asc|desc)*(\\s*,\\s*(\\w*[^\\x00-\\xff]*)*+\\.?(\\w*[^\\x00-\\xff]*(\\x5b|\\x5d)?)*\\s*(asc|desc)*)*",
                            "\\s+order\\s+by\\s+(\\w*[^\\x00-\\xff]*(\\x5b|\\x5d)?)*+\\.?(\\w*[^\\x00-\\xff]*(\\x5b|\\x5d)?)*\\s*(asc|desc)*(\\s*,\\s*(\\w*[^\\x00-\\xff]*(\\x5b|\\x5d)?)*+\\.?(\\w*[^\\x00-\\xff]*(\\x5b|\\x5d)?)*\\s*(asc|desc)*)*",
                            Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(sql);
      sql = matcher.replaceAll(" ");
      limit_sql.append(" select count(1) as count from ( ");
      limit_sql.append(sql);
      limit_sql.append(" ) as temptable");
    }
    return limit_sql.toString();
  }

  /**
   * �����ک`�����Έ��ϡ�����SQL�Ĥ����ɤ��ޤ���
   * <p>
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�ǩ`���٩`���ηN�����{@link Constants#DATABASE_ORACLE}���ϡ�Oracle�ǩ`���٩`���Υک`���󥰗�����SQL�Ĥ����ɤ��ޤ���
   * <li>�ǩ`���٩`���ηN�����{@link Constants#DATABASE_SQL_SERVER}���ϡ�SQL
   * Server�ǩ`���٩`���Υک`���󥰗�����SQL�Ĥ����ɤ��ޤ���
   * <li>���ɤ���SQL�Ĥ򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�Ǥ��Υ᥽�åɤ���������ک`���󥰗�����SQL�Ĥ�M�����Ƥޤ���
   * </dl>
   *
   * @param sql ��ͨSQL��
   * @param pageNo �_ʼ����ǥå���
   * @param pageSize ���ک`�����Ŀ��
   * @param dbname �ǩ`���٩`�����
   * @return �ک`�����Η���SQL��
   */
  public static String getPaginationSql(String sql, int pageNo, int pageSize,
    String dbname) {
    String dbinfo = filterDBInfo(dbname);
    StringBuffer limit_sql = new StringBuffer();
    int last = (pageNo * pageSize);
    if (dbinfo.trim().equalsIgnoreCase(GTConstants.DATABASE_ORACLE)) {
      limit_sql.append(" select * from ( ");
      limit_sql.append(" select temptable.* ,rownum row_num from ( ");
      limit_sql.append(sql);
      limit_sql.append(" ) temptable where rownum <= ");
      limit_sql.append(last);
      limit_sql.append(" ) where row_num >= ");
      limit_sql.append(((pageNo - 1) * pageSize) + 1);
    } else if (dbinfo.trim().equalsIgnoreCase(GTConstants.DATABASE_MSSQL)) {
      Pattern pattern = Pattern.compile(
        "order\\s*by\\s*\\w*\\s*(asc|desc)*(\\s*,\\s*\\w*\\s*(asc|desc)*)*",
        Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(sql);
      String orderBy = "";
      StringBuffer sbSql = new StringBuffer();
      int cnt = 0;
      int startIndex = 0;
      while (matcher.find()) {
        orderBy = matcher.group();
        startIndex = matcher.start();
        cnt++;
      }
      if (cnt > 0) {
        matcher.reset();
        matcher.find(startIndex);
        matcher.appendReplacement(sbSql, "");
        matcher.appendTail(sbSql);
        sql = sbSql.toString();
      } else {
        orderBy = "order by CURRENT_TIMESTAMP";
      }

      limit_sql.append("select * from (");
      limit_sql.append("  select row_number() over (");
      limit_sql.append(orderBy);
      limit_sql.append("    ) as paging_rownum,* from (");
      limit_sql.append(sql);
      limit_sql.append("  ) as tempA");
      limit_sql.append(") as temp ");
      limit_sql.append("where paging_rownum between ");
      limit_sql.append((pageNo - 1) * pageSize + 1);
      limit_sql.append(" and ");
      limit_sql.append(last).append(" ");
      if (!"order by CURRENT_TIMESTAMP".equals(orderBy)) {
        limit_sql.append(orderBy);
      }
    }
    return limit_sql.toString();

  }

  private static String filterDBInfo(String dbname) {
    if (StringUtils.isEmpty(dbname)) {
      return GTConstants.DATABASE_ORACLE;
    } else {
      return dbname;
    }
  }

  /**
   * ���Ŀ�ˌ�����DB�e�SQL�Ĥ�ȡ��
   * add by dhc 2014/10/13 ��T�޶�
   *
   * @param colName     ���󥫥��
   * @param dbType      DB�N�e
   *
   * @return ���Ŀ�ˌ�����DB�e�SQL��
   */
  public static String createSql_IsNotNull(String colName, String dbType) {
      StringBuffer sb = new StringBuffer();
      sb.append(" ");
      sb.append(colName + " IS NOT NULL ");
      // MSSS�Έ��ϡ���''���������å������Ҫ
      if (GTConstants.DATABASE_TYPE_SQLSERVER.equals(dbType)) {
          sb.append(" AND " + colName + " <> '' ");
      }
      return sb.toString();
    }
  
	public static String getFilterSql(String filter){
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotEmpty(filter)){
			JSONArray jsonArray = JSONArray.fromObject(filter); 
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String operator = jsonObject.getString("operator");
				String value = jsonObject.getString("value");
				String property = jsonObject.getString("property");
				property = CommonUtils.camelToUnderline(property);
				if (StringUtils.isNotEmpty(operator)) {
					if("like".equals(operator)){
						sql.append(" AND "+property+" "+operator+" '%" +value+"%'");
					}else{
						sql.append(" AND "+property+" = '" +value+"'");
					}
					
				}
			}
		}
		return sql.toString();
	}
	
	public static String getSortSql(String sort,String defaultSort,String defaultDesc){
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotEmpty(sort)){
			sql.append(" ORDER BY ");
			JSONArray jsonArray = JSONArray.fromObject(sort); 
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String property = jsonObject.getString("property");
				property = CommonUtils.camelToUnderline(property);
				String direction = jsonObject.getString("direction");
				if (StringUtils.isNotEmpty(property)) {
					sql.append(property+" "+direction);
				}
			}
		}else{
			sql.append(" ORDER BY "+defaultSort+" "+defaultDesc);
		}
		return sql.toString();
	}
}

