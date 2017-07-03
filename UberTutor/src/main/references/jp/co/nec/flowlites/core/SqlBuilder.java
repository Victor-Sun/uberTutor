package jp.co.nec.flowlites.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.gnomon.common.constant.GTConstants;
import com.gnomon.common.utils.CommonUtils;

/**
 * SQL文生成ユーティリティクラス。
 * <p>
 * <a href="FLSqlBuilder.java.html"><i>View Source</i></a>
 * </p>
 *
 */
public final class SqlBuilder
{
  /**
   * 検索の結果の総数を限定されたSQL文を組み立てます。
   * <p>
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>データベースの種類名が{@link Constants#DATABASE_ORACLE}場合、Oracleデータベースで検索用SQL文を作成します。
   * <li>データベースの種類名が{@link Constants#DATABASE_SQL_SERVER}場合、SQL
   * Serverデータベースで検索用SQL文を作成します。
   * <li>作成したSQL文を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでこのメソッドを呼出し、検索の結果の総数を限定されたSQL文を組み立てます。
   * </dl>
   *
   * @param sql 普通SQL文
   * @param searchLimit 限定された総数
   * @param dbname データベース情報
   * @return 限定されたSQL文
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
   * 検索ページ化の場合、項目総数のSQL文を作成します。
   * <p>
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>データベースの種類名が{@link Constants#DATABASE_ORACLE}場合、Oracleデータベースのレコード数を取得する用SQL文を作成します。
   * <li>データベースの種類名が{@link Constants#DATABASE_SQL_SERVER}場合、SQL
   * Serverデータベースのレコード数を取得する用SQL文を作成します。
   * <li>作成したSQL文を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでこのメソッドを呼出し、検索の結果の総数を限定されたSQL文を組み立てます。
   * </dl>
   *
   * @param sql 一覧検索用SQL文
   * @param dbname データベース情報 del by dhc 2011/10/21 DB2対応
   * @param useOrderByFlg OrderBy使用フラグ（true ： OrderByを残す　false ： OrderByを残さない） add by dhc 2011/10/21 DB2対応
   *
   * @return 件数取得SQL文
   */
  //public static String getCountSql(String sql, String dbname) {
  public static String getCountSql(String sql, boolean useOrderByFlg) {
    // del by dhc 2011/10/21 DB2対応
    //String dbinfo = filterDBInfo(dbname);
    StringBuffer limit_sql = new StringBuffer();
    // upd by dhc 2011/10/21 DB2対応 -- start
    //if (dbinfo.trim().equalsIgnoreCase(Constants.DATABASE_ORACLE)) {
    if (useOrderByFlg) {
    // upd by dhc 2011/10/21 DB2対応 -- end
      limit_sql.append(" select count(1) as count from ( ");
      limit_sql.append(sql);
      limit_sql.append(" ) temptable");
    // upd by dhc 2011/10/21 DB2対応 -- start
    //} else if (dbinfo.trim().equalsIgnoreCase(Constants.DATABASE_MSSQL)) {
    } else {
    // upd by dhc 2011/10/21 DB2対応 -- end
      /* 修正者:GuoDecai 原因:OrderByの後の物を削除 時間:2008/09/23
      Pattern pattern = Pattern.compile(
    		  "\\s+order\\s+by\\s+\\w+\\.?\\w*\\s*(asc|desc)*(\\s*,\\s*\\w+\\.?\\w*\\s*(asc|desc)*)*",
        Pattern.CASE_INSENSITIVE);
      */
     Pattern pattern = Pattern
                    .compile(
                            // upd by dhc 2010/03/30 障害単票_岩永_20100323_01 order by に「[、]」が含む場合の対応
                            //"\\s+order\\s+by\\s+(\\w*[^\\x00-\\xff]*)*+\\.?(\\w*[^\\x00-\\xff]*)*\\s*(asc|desc)*(\\s*,\\s*(\\w*[^\\x00-\\xff]*)*+\\.?(\\w*[^\\x00-\\xff]*)*\\s*(asc|desc)*)*",
                            // upd by dhc 2010/03/30 障害単票_大坪_20100406_01 order by のテーブル名に「[、]」が含む場合の対応
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
   * 検索ページ化の場合、検索SQL文を作成します。
   * <p>
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>データベースの種類名が{@link Constants#DATABASE_ORACLE}場合、Oracleデータベースのページング検索用SQL文を作成します。
   * <li>データベースの種類名が{@link Constants#DATABASE_SQL_SERVER}場合、SQL
   * Serverデータベースのページング検索用SQL文を作成します。
   * <li>作成したSQL文を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでこのメソッドを呼出し、ページング検索用SQL文を組み立てます。
   * </dl>
   *
   * @param sql 普通SQL文
   * @param pageNo 開始インデックス
   * @param pageSize 毎ページに項目数
   * @param dbname データベース情報
   * @return ページ化の検索SQL文
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
   * 空項目に対するDB別差異SQL文を取得
   * add by dhc 2014/10/13 社員限定
   *
   * @param colName     対象カラム
   * @param dbType      DB種別
   *
   * @return 空項目に対するDB別差異SQL文
   */
  public static String createSql_IsNotNull(String colName, String dbType) {
      StringBuffer sb = new StringBuffer();
      sb.append(" ");
      sb.append(colName + " IS NOT NULL ");
      // MSSSの場合、「''」がチェックする必要
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

