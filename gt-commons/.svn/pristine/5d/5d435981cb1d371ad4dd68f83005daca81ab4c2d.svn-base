package com.gnomon.common.page;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.gnomon.common.GTConstants;

/**
 * 共通JDBCテンプレート基底クラス。<br />
 * このクラスでは、ステートメントの生成、 実行のようなコアJDBCのワークフローを実行し、
 * アプリケーションコードからSQLの受け渡しと結果の抽出を分離する。
 * <p>
 * <a href="FLJdbcTemplate.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class GTJdbcTemplate extends JdbcTemplate
{
  /* 命名JDBCテンプレート */
  private NamedParameterJdbcTemplate namedJdbcTempleate;

  /* 検索結果の制限数 */
  private int searchLimit = 0;

  /* データベース情報 */
  private String dbProductName = GTConstants.DATABASE_ORACLE;
  
  /**
   * OrderByを残すか否かのフラグ
   * add by dhc 2011/10/18 DB2対応
   */
  private boolean useOrderByFlg = false;
  /**
   * デフォルトコンストラクタ。
   * <p>
   * <dl>
   * <dt>実装機能：
   * <dd>何もしません。
   * <dt>利用方法：
   * <dd>業務サービスでパラメータ指定しないの場合、このメソッドが呼出し、JDBCテンプレートオブジェクトを作成します。
   * </dl>
   */
  public GTJdbcTemplate() {

  }

  /**
   * 指定されたデータベースより、FLJdbcTemplateを構築します。
   * <p>
   * <dl>
   * <dt>実装機能：
   * <dd>指定されたデータベースを利用して、スーパークラスのコンストラクタを呼び出す。
   * <dt>利用方法：
   * <dd>{@link jp.co.nec.flowlites.core.dao.impl.FLJdbcBaseDAOImpl#createFLJdbcTemplate(DataSource)}内部で指定されたデータベースを利用して、このメソッドが呼出し、JDBCテンプレートオブジェクトを作成します。
   * </dl>
   * 
   * @param dataSource データソース
   */
  public GTJdbcTemplate(DataSource dataSource) {
    super(dataSource);
  }

  /**
   * データベースの種類名の対応定数を取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>このインスタンス設定したデータベースの種類名の対応定数({@link jp.co.nec.flowlites.core.common.GTConstants#DATABASE_ORACLE}または{@link jp.co.nec.flowlites.core.common.GTConstants#DATABASE_MSSQL})を返す。
   * <dt>利用方法：
   * <dd>結果件数制限検索とページング検索時にこのメッソドを呼出された取得した戻り値よりSQL文を作成します。
   * </dl>
   * 
   * @return このインスタンス設定したデータベースの種類名の対応定数({@link jp.co.nec.flowlites.core.common.GTConstants#DATABASE_ORACLE}または{@link jp.co.nec.flowlites.core.common.GTConstants#DATABASE_MSSQL})
   */
  public String getDbProductName() {
    return dbProductName;
  }

  /**
   * データベースの種類名の対応定数を設定します。
   * <dl>
   * <dt>実装機能：
   * <dd>このインスタンスのデータベースの種類名の対応定数を設定します。
   * <dt>利用方法：
   * <dd>{@link jp.co.nec.flowlites.core.dao.impl.FLJdbcBaseDAOImpl#setDatabaseProductName(String)}内部で指定されたデータベースの種類名の対応定数を利用して、このメソッドが呼出し、データベースの種類名の対応定数を設定します。
   * </dl>
   * 
   * @param dbProductName アプリケーションコンテキストのXMLファイルにて設定したデータベースの種類名の対応定数({@link jp.co.nec.flowlites.core.common.GTConstants#DATABASE_ORACLE}または{@link jp.co.nec.flowlites.core.common.GTConstants#DATABASE_MSSQL})
   */
  public void setDbProductName(String dbProductName) {
    this.dbProductName = dbProductName;
  }

  /**
   * 検索結果制限件数を取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>内部の検索結果制限件数を返す。
   * <dt>利用方法：
   * <dd>データを検索する場合、検索結果件数制限必要時に、このメッソドを呼出された取得した検索結果制限件数より、処理を行う。
   * </dl>
   * 
   * @return 設定した検索結果制限件数
   */
  public int getSearchLimit() {
    return searchLimit;
  }

  /**
   * 検索結果制限件数を設定します。
   * <dl>
   * <dt>実装機能：
   * <dd>このインスタンスの検索結果制限件数を設定します。
   * <dt>利用方法：
   * <dd>{@link jp.co.nec.flowlites.core.dao.impl.FLJdbcBaseDAOImpl#setSearchLimit(int)}内部で指定された検索結果制限件数を利用して、このメソッドが呼出し、検索結果制限件数を設定します。
   * </dl>
   * 
   * @param searchLimit 検索結果制限件数
   */
  public void setSearchLimit(int searchLimit) {
    this.searchLimit = searchLimit;
  }

  /**
   * カスタマイズドライバクラスを設定します.
   *
   * @param useOrderByFlg OrderByを残すか否かのフラグ
   * add by dhc 2011/10/18 DB2対応
   */
  public void setUseOrderByFlg(boolean useOrderByFlg) {
    this.useOrderByFlg = useOrderByFlg;
  }

  /**
   * 検索用結果制限数までのSQL文を生成する。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>検索結果制限件数が「0」の場合、SQL文を返す。
   * <li>検索結果制限件数が「0」でないの場合、{@link SqlBuilder#generateLimitSql(String, int, String)}を呼出され、処理後のSQL文を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>検索結果件数制限ことがある場合、このメソッドを呼出し、検索件数制限SQL文を作成する。
   * </dl>
   * 
   * @param sql 処理前のSQL文
   * @return 処理後のSQL文
   */
  private String getLimitSql(String sql) {
	  return sql;
  }

  /**
   * ページングのSQL文を生成する。
   * <dl>
   * <dt>実装機能：
   * <dd>{@link jp.co.nec.flowlites.core.jdbc.util.SqlBuilder#getPaginationSql(String, int, int, String)}を呼出し、ページング用のSQL文を作成します。
   * <dt>利用方法：
   * <dd>検索結果件数制限ことがある場合、このメソッドを呼出し、検索件数制限SQL文を作成する。
   * </dl>
   * 
   * @param sql 処理前のSQL文
   * @param pageNo ページ番号
   * @param pageSize 毎ページに項目数
   * @return 処理後のSQL文
   */
  private String getPageSearchSql(String sql, int pageNo, int pageSize) {
    return SqlBuilder.getPaginationSql(sql, pageNo, pageSize,
      getDbProductName());
  }

  /**
   * 検索結果の総ページ数を取得する。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>ページング検索用のSQL文を取得して、それを利用して、検索結果のレコード数を取得します。
   * <li>取得したレコード数より、検索結果の総ページ数を取得します。
   * </ul>
   * <dt>利用方法：
   * <dd>検索結果件数制限ことがある場合、このメソッドを呼出し、検索件数制限SQL文を作成する。
   * </dl>
   * 
   * @param sql SQL文
   * @param pageSize 毎ページに項目数
   * @return 総ページ数
   */
  private int getTotalCount(String sql) {
      // upd by dhc 2011/10/21 -- start
      //return queryForInt(SqlBuilder.getCountSql(sql, getDbProductName()));
      boolean orderByFlg = false;
      if (GTConstants.DATABASE_ORACLE.equals(this.dbProductName)) {
          // フラグにtrueを格納する
          orderByFlg = true;
      } else if (GTConstants.DATABASE_MSSQL.equals(this.dbProductName)) {
          // フラグにfalseを格納する
          orderByFlg = false;
      } else {
          // useOrderByFlgの値をフラグに格納する
          orderByFlg = this.useOrderByFlg;
      }
      // 件数取得SQL作成
      String sqlForQuery = SqlBuilder.getCountSql(sql, orderByFlg);
      // 件数取得
      return queryForInt(sqlForQuery);
      // upd by dhc 2011/10/21 -- end
  }

  /**
   * 検索結果の総ページ数を取得する。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>ページング検索用のSQL文を取得して、それを利用して、検索結果のレコード数を取得します。
   * <li>取得したレコード数より、検索結果の総ページ数を取得します。
   * </ul>
   * <dt>利用方法：
   * <dd>検索結果件数制限ことがある場合、このメソッドを呼出し、検索件数制限SQL文を作成する。
   * </dl>
   * 
   * @param sql SQL文
   * @param args パラメータ
   * @param pageSize 毎ページに項目数
   * @return 総ページ数
   */
  private int getTotalCount(String sql, Object[] args) {
      // upd by dhc 2011/10/21 -- start
      //return this.queryForInt(SqlBuilder.getCountSql(sql, getDbProductName()),
      //  args);
      boolean orderByFlg = false;
      if (GTConstants.DATABASE_ORACLE.equals(this.dbProductName)) {
          // フラグにtrueを格納する
          orderByFlg = true;
      } else if (GTConstants.DATABASE_MSSQL.equals(this.dbProductName)) {
          // フラグにfalseを格納する
          orderByFlg = false;
      } else {
          // useOrderByFlgの値をフラグに格納する
          orderByFlg = this.useOrderByFlg;
      }
      // 件数取得SQL作成
      String sqlForQuery = SqlBuilder.getCountSql(sql, orderByFlg);
      // 件数取得
      return this.queryForInt(sqlForQuery, args);
      // upd by dhc 2011/10/21 -- end
  }

  /**
   * 検索結果の総ページ数を取得する。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>ページング検索用のSQL文を取得して、それを利用して、検索結果のレコード数を取得します。
   * <li>取得したレコード数より、検索結果の総ページ数を取得します。
   * </ul>
   * <dt>利用方法：
   * <dd>検索結果件数制限ことがある場合、このメソッドを呼出し、検索件数制限SQL文を作成する。
   * </dl>
   * 
   * @param sql SQL文
   * @param paraMap パラメータマップ
   * @param pageSize 毎ページに項目数
   * @return 総ページ数
   */
  private int getTotalCount(String sql, Map paraMap) {
      // upd by dhc 2011/10/21 -- start
      //return this.queryForInt(SqlBuilder.getCountSql(sql, getDbProductName()),
      //  paraMap);
      boolean orderByFlg = false;
      if (GTConstants.DATABASE_ORACLE.equals(this.dbProductName)) {
          // フラグにtrueを格納する
          orderByFlg = true;
      } else if (GTConstants.DATABASE_MSSQL.equals(this.dbProductName)) {
          // フラグにfalseを格納する
          orderByFlg = false;
      } else {
          // useOrderByFlgの値をフラグに格納する
          orderByFlg = this.useOrderByFlg;
      }
      // 件数取得SQL作成
      String sqlForQuery = SqlBuilder.getCountSql(sql, orderByFlg);
      // 件数取得
      return this.queryForInt(sqlForQuery, paraMap);
      // upd by dhc 2011/10/21 -- end
  }

  /**
   * 検索結果の総ページ数を取得する。
   * <dl>
   * <dt>実装機能：
   * <dd>取得したレコード数より、検索結果の総ページ数を取得します。
   * <dt>利用方法：
   * <dd>検索結果件数制限ことがある場合、このメソッドを呼出し、検索件数制限SQL文を作成する。
   * </dl>
   * 
   * @param count 検索結果の総件数
   * @param pageSize 毎ページに項目数
   * @return 総ページ数
   */
  private int getPageTotalCount(int count, int pageSize) {
	if (pageSize == 0) return 0;

    if (count % pageSize == 0) {
      return count / pageSize;
    } else {
      return (count / pageSize) + 1;
    }
  }

  /**
   * パラメータ行オブジェクトを取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>{@link jp.GTDBUtil.nec.flowlites.core.jdbc.util.FLDBUtil#initParameterizedRowMapper(Class)}を呼出し、結果を返す。
   * <dt>利用方法：
   * <dd>このメソッドは内部のみでの利用。
   * </dl>
   * 
   * @param requiredType テイプ
   * @return パラメータ行オブジェクト
   */
  private <T> ParameterizedRowMapper<T> getParameterizedRowMapper(
    Class<T> requiredType) {
    return GTDBUtil.initParameterizedRowMapper(requiredType);
  }

  /**
   * データを一括更新します。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>更新用の値が存在するチェックします。
   * <li>存在する場合、<code>org.springframework.jdbc.core.BatchPreparedStatementSetter</code>オブジェクトを作成し、
   * <code>org.springframework.jdbc.core.JdbcTemplate</code>の<code>batchUpdate(java.lang.String, org.springframework.jdbc.core.BatchPreparedStatementSetter)</code>を呼出す。
   * <li>存在しない場合、<code>org.springframework.jdbc.core.JdbcTemplate</code>の<code>batchUpdate(java.lang.String[])</code>を呼出す。
   * <li> 更新結果を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでデータを一括更新する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql SQL文
   * @param types テイプ
   * @param values 値
   * @return 処理結果({0,1,0}:{失敗,成功,失敗})
   */
  public int[] batchUpdate(String sql, int[] types, Object... values) {
    final int[] updateTypes = types;
    final Object[] updateValues = values;
    if (values != null && values.length > 0) {
      BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter()
      {
        public void setValues(PreparedStatement ps, int index)
          throws SQLException {
          int startIndex = index * updateTypes.length;
          for (int i = 0; i < updateTypes.length; i++) {
            // ps.setObject(i+1,updateValues[startIndex+i],updateTypes[i]);
            StatementCreatorUtils.setParameterValue(ps, i + 1, updateTypes[i],
              updateValues[startIndex + i]);
          }
        }

        public int getBatchSize() {
          return updateValues.length / updateTypes.length;
        }
      };
      return super.batchUpdate(sql, setter);
    } else {
      return super.batchUpdate(new String[] { sql });
    }
  }

  /**
   * 結果Objectへのマッピングと無制限パラメーターで検索し、リスト中にマッピングの型結果を持ちます。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>検索結果制限件数より検索用のSQL文を生成します。
   * <li><code>org.springframework.jdbc.core.simple.SimpleJdbcTemplate</code>の<code>query(java.lang.String, org.springframework.jdbc.core.simple.ParameterizedRowMapper, java.lang.Object[])</code>を呼出し、検索結果を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでデータリストを検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param rm 結果Objectへのマッピング
   * @param args 無制限パラメーター
   * @return マッピングの型結果を持つリスト
   */
  @SuppressWarnings("unchecked")
  public <T> List<T> query(String sql, ParameterizedRowMapper<T> rm,
    Object... args) {
    String limitSql = getLimitSql(sql);
    return super.query(limitSql, args, rm);
  }

  /**
   * 結果Objectへのマッピングと無制限パラメーターで検索し、リスト中に指定Object型結果を持ちます。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>検索結果制限件数より検索用のSQL文を生成します。
   * <li>{@link #query(String, org.springframework.jdbc.core.simple.ParameterizedRowMapper, Object[])}を呼出し、検索結果を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでデータリストを検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param requiredType 結果Objectのクラス
   * @param args 無制限パラメーター
   * @return マッピングの型結果を持つリスト
   */
//  public <T> List<T> query(String sql, Class<T> requiredType, Object... args) {
//    return query(sql, getParameterizedRowMapper(requiredType), args);
//  }

  /**
   * パラメーターで検索し、リスト中にマップ結果を持ちます。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>無制限パラメーターがある場合、スーパークラスの<code>queryForInt(String, Object)</code>を呼出し、検索結果を返す。
   * <li>無制限パラメーターがない場合、スーパークラスの<code>queryForInt(String)</code>を呼出し、検索結果を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでデータリストを検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param arg パラメーター
   * @return マップ結果を持つリスト
   */
  public int queryForInt(String sql, Object arg) {
    if (arg == null) {
      return super.queryForInt(sql);
    } else {
      return super.queryForInt(sql, new Object[] { arg });
    }
  }

  /**
   * マップ型結果を単件検索します。
   * <dl>
   * <dt>実装機能：
   * <dd><code>org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate</code>の<code>queryForInt(java.lang.String, java.util.Map)</code>を呼出し、検索結果を返す。
   * <dt>利用方法：
   * <dd>業務DAOでマップ結果を検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param paraMap パラメーターマップ
   * @return マップ結果
   */
  public int queryForInt(String sql, Map paraMap) {
    return getNamedJdbcTempleate().queryForInt(sql, paraMap);
  }

  /**
   * 結果Objectへのマッピングを無制限パラメーターで単件を検索します。
   * <dl>
   * <dt>実装機能：
   * <dd><code>org.springframework.jdbc.core.simple.SimpleJdbcTemplate</code>の<code>queryForObject(java.lang.String, org.springframework.jdbc.core.simple.ParameterizedRowMapper, java.lang.Object[])</code>を呼出し、検索結果を返す。
   * <dt>利用方法：
   * <dd>業務DAOで結果Objectへのマッピングを検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param rm 結果Objectへのマッピング
   * @param args 無制限パラメーター
   * @return マッピングのObject
   */
  @SuppressWarnings("unchecked")
  public <T> T queryForObject(String sql, ParameterizedRowMapper<T> rm,
    Object... args) {
    return (T)super.queryForObject(sql, args, rm);
  }

  /**
   * 指定結果クラスと無制限パラメーターで単件データを検索します。
   * <dl>
   * <dt>実装機能：
   * <dd>{@link #queryForObject(String, org.springframework.jdbc.core.simple.ParameterizedRowMapper, Object[])}を呼出し、検索結果を返す。
   * <dt>利用方法：
   * <dd>業務DAOで指定結果クラスを検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param requiredType 指定Objectクラス
   * @param args 無制限パラメーター
   * @return 指定結果クラスオブジェクト
   */
//  public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) {
//    return queryForObject(sql, getParameterizedRowMapper(requiredType), args);
//  }

  /**
   * パラメーターで検索し、リスト中にマップ結果を持ちます。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>検索結果制限件数より検索用のSQL文を生成します。
   * <li><code>org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate</code>の<code>queryForList(java.lang.String, java.util.Map)</code>を呼出し、検索結果を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでデータリストを検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param paramMap パラメーターマップ
   * @return マップ結果を持つリスト
   */
  @SuppressWarnings("unchecked")
  public List<Map<String, Object>> queryForList(String sql,
    Map<String, Object> paramMap) {
    return getNamedJdbcTempleate().queryForList(getLimitSql(sql), paramMap);

  }

  /**
   * 結果Objectへのマッピングとパラメーターで検索し、リスト中に指定クラスのオブジェクトを持ちます。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>検索結果制限件数より検索用のSQL文を生成します。
   * <li><code>org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate</code>の<code>query(java.lang.String, java.util.Map, org.springframework.jdbc.core.RowMapper)</code>を呼出し、検索結果を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでデータリストを検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param paramMap パラメーターマップ
   * @param rm 結果Objectへのマッピング
   * @return 指定結果クラスを持つリスト
   */
  @SuppressWarnings("unchecked")
  public <T> List<T> queryForList(String sql, Map<String, Object> paramMap,
    ParameterizedRowMapper<T> rm) {
    return getNamedJdbcTempleate().query(getLimitSql(sql), paramMap, rm);
  }

  /**
   * 結果Objectのクラスとパラメーターで検索し、リスト中に指定クラスのオブジェクトを持ちます。
   * <dl>
   * <dt>実装機能：
   * <dd>{@link #queryForList(String, java.util.Map, org.springframework.jdbc.core.simple.ParameterizedRowMapper)}を呼出し、検索結果を返す。
   * <dt>利用方法：
   * <dd>業務DAOでデータリストを検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param paramMap パラメーターマップ
   * @param requiredType 結果Objectのクラス
   * @return 指定結果クラスを持つリスト
   */
//  public <T> List<T> queryForList(String sql, Map<String, Object> paramMap,
//    Class<T> requiredType) {
//    return queryForList(sql, paramMap, getParameterizedRowMapper(requiredType));
//  }

  /**
   * 結果Objectへのマッピングをパラメーターで単件を検索します。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>検索結果制限件数より検索用のSQL文を生成します。
   * <li><code>org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate</code>の<code>queryForObject(java.lang.String, java.util.Map, org.springframework.jdbc.core.RowMapper)</code>を呼出し、検索結果を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOで結果Objectへのマッピングを検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param paramMap パラメーターマップ
   * @param rm 結果Objectへのマッピング
   * @return 指定クラスのObject
   */
  @SuppressWarnings("unchecked")
  public <T> T queryForObject(String sql, Map<String, Object> paramMap,
    ParameterizedRowMapper<T> rm) {
    return (T)getNamedJdbcTempleate().queryForObject(getLimitSql(sql),
      paramMap, rm);
  }

  /**
   * 結果Objectへのマッピングをパラメーターで単件を検索します。
   * <dl>
   * <dt>実装機能：
   * <dd>{@link #queryForObject(String, Map, org.springframework.jdbc.core.simple.ParameterizedRowMapper)}を呼出し、検索結果を返す。
   * <dt>利用方法：
   * <dd>業務DAOで結果Objectへのマッピングを検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param paramMap パラメーターマップ
   * @param requiredType 結果Objectのクラス
   * @return 指定クラスのObject
   */
  public <T> T queryForObject(String sql, Map<String, Object> paramMap,
    Class<T> requiredType) {
    return queryForObject(sql, paramMap,
      getParameterizedRowMapper(requiredType));
  }

  /**
   * 結果Objectへのマッピングと無制限パラメーターでページング検索します。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>{@link #getPageTotalCount(String, int)}を呼出し、検索結果の総ページ数を取得します。
   * <li>{@link #getPageSearchSql(String, int, int)}を呼出し、ページング検索用のSQL文を生成します。
   * <li>{@link #query(String, ParameterizedRowMapper, Object...)}を呼出し、検索結果を取得します。
   * <li>{@link jp.co.nec.flowlites.core.jdbc.object.GTPage}オブジェクトを作成し、結果を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでページング検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param pageNo ページ番号
   * @param pageSize 毎ページのレコード数
   * @param rm 結果Objectへのマッピング
   * @param args 無制限パラメーター
   * @return ページングオブジェクト
   */
  @SuppressWarnings("unchecked")
  public <T> GTPage<T> queryPagination(String sql, int pageNo, int pageSize,
    ParameterizedRowMapper<T> rm, Object... args) {
    int totalCount = getTotalCount(sql, args);
    int limitCount = totalCount;
    if (this.searchLimit > 0 && this.searchLimit < totalCount) {
    	limitCount = this.searchLimit;
    }
    int pageTotalCount = getPageTotalCount(limitCount, pageSize);
    GTResultSetExtractor rse =  new GTResultSetExtractor(pageNo,pageSize,rm);
    super.query(sql, args,  rse);
    List<T> items = rse.getItems();
    GTPage<T> ps = new GTPage<T>(items, totalCount, 
    		limitCount, pageTotalCount, pageNo, pageSize);
    return ps;
  }

  /**
   * 結果Objectへのマッピングと無制限パラメーターでページング検索します。
   * <dl>
   * <dt>実装機能：
   * <dd>{@link #queryPagination(String, int, int, ParameterizedRowMapper, Object[])}を呼出し、検索結果を返す。
   * <dt>利用方法：
   * <dd>業務DAOでページング検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param pageNo ページ番号
   * @param pageSize 毎ページのレコード数
   * @param requiredType 結果Objectクラス
   * @param args 無制限パラメーター
   * @return ページングオブジェクト
   */
  public <T> GTPage<T> queryPagination(String sql, int pageNo, int pageSize,
    Class<T> requiredType, Object... args) {
    return queryPagination(sql, pageNo, pageSize,
      getParameterizedRowMapper(requiredType), args);
  }

  /**
   * 結果Objectへのマッピングと無制限パラメーターでページング検索します。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>{@link #getPageTotalCount(String, int)}を呼出し、検索結果の総ページ数を取得します。
   * <li>{@link #getPageSearchSql(String, int, int)}を呼出し、ページング検索用のSQL文を生成します。
   * <li>{@link #queryForList(String, Map, ParameterizedRowMapper)}を呼出し、検索結果を取得します。
   * <li>{@link jp.co.nec.flowlites.core.jdbc.object.GTPage}オブジェクトを作成し、結果を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでページング検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param pageNo ページ番号
   * @param pageSize 毎ページのレコード数
   * @param rm 結果Objectへのマッピング
   * @param paramMap パラメーター
   * @return ページングオブジェクト
   */
  @SuppressWarnings("unchecked")
  public <T> GTPage<T> queryPagination(String sql, int pageNo, int pageSize,
    ParameterizedRowMapper<T> rm, Map<String, Object> paramMap) {
    int totalCount = getTotalCount(sql, paramMap);
    int limitCount = totalCount;
    if (this.searchLimit > 0 && this.searchLimit < totalCount) {
    	limitCount = this.searchLimit;
    }
    int pageTotalCount = getPageTotalCount(limitCount, pageSize);
    GTResultSetExtractor rse =  new GTResultSetExtractor(pageNo,pageSize,rm);
    List<T> items = (List<T>)getNamedJdbcTempleate().query(sql, paramMap, rse);
    GTPage<T> ps = new GTPage<T>(items, totalCount, limitCount, pageTotalCount, pageNo, pageSize);
    return ps;
  }

  /**
   * 結果Objectへのマッピングと無制限パラメーターでページング検索します。
   * <dl>
   * <dt>実装機能：
   * <dd>{@link #queryPagination(String, int, int, ParameterizedRowMapper, Map)}を呼出し、検索結果を返す。
   * <dt>利用方法：
   * <dd>業務DAOでページング検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param pageNo ページ番号
   * @param pageSize 毎ページのレコード数
   * @param requiredType 結果Objectクラス
   * @param paramMap パラメーター
   * @return ページングオブジェクト
   */
  public <T> GTPage<T> queryPagination(String sql, int pageNo, int pageSize,
    Class<T> requiredType, Map<String, Object> paramMap) {
    return queryPagination(sql, pageNo, pageSize,
      getParameterizedRowMapper(requiredType), paramMap);
  }

  /**
   * ページング検索します。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>{@link #getPageTotalCount(String, int)}を呼出し、検索結果の総ページ数を取得します。
   * <li>{@link #getPageSearchSql(String, int, int)}を呼出し、ページング検索用のSQL文を生成します。
   * <li><code>org.springframework.jdbc.core.simple.SimpleJdbcTemplate</code>の<code>queryForList(java.lang.String, java.lang.Object[])</code>を呼出し、検索結果を取得します。
   * <li>{@link jp.co.nec.flowlites.core.jdbc.object.GTPage}オブジェクトを作成し、結果を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでページング検索する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 検索用のSQL文
   * @param pageNo ページ番号
   * @param pageSize 毎ページのレコード数
   * @return ページングオブジェクト
   */
  @SuppressWarnings("unchecked")
  public <T> GTPage<Map<String, Object>> queryPagination(String sql,
    int pageNo, int pageSize) {
    int totalCount = getTotalCount(sql);
    int limitCount = totalCount;
    if (this.searchLimit > 0 && this.searchLimit < totalCount) {
    	limitCount = this.searchLimit;
    }
    int pageTotalCount = getPageTotalCount(limitCount, pageSize);
    GTResultSetExtractor rse =  new GTResultSetExtractor(pageNo,pageSize);
    super.query(sql, rse);
    List items = rse.getItems();
    GTPage<Map<String, Object>> ps = new GTPage<Map<String, Object>>
      (items, totalCount, limitCount, pageTotalCount, pageNo, pageSize);
    return ps;
  }
  
  @SuppressWarnings("unchecked")
  public <T> GTPage<Map<String, Object>> queryPagination(String sql,
    int pageNo, int pageSize,Object... args) {
    int totalCount = getTotalCount(sql,args);
    int limitCount = totalCount;
    if (this.searchLimit > 0 && this.searchLimit < totalCount) {
    	limitCount = this.searchLimit;
    }
    int pageTotalCount = getPageTotalCount(limitCount, pageSize);
    List<Map<String, Object>> list = super.queryForList(getPageSearchSql(sql, pageNo,pageSize),args);
    GTPage<Map<String, Object>> ps = new GTPage<Map<String, Object>>(list, totalCount, limitCount, pageTotalCount, pageNo, pageSize);
    return ps;
  }

  /**
   * データを更新処理します。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>パラメーターの有無がチェックします。
   * <li>無しの場合、スーパークラスの<code>update(String)</code>を呼出し、データを更新処理します。
   * <li>有りの場合、<code>org.springframework.jdbc.core.simple.SimpleJdbcTemplate</code>の<code>update(java.lang.String, java.lang.Object)</code>を呼出し、データを更新処理します。
   * <li>更新結果件数を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでデータを更新する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 更新用のSQL文
   * @param arg パラメーター
   * @return 更新件数
   */
  public int update(String sql, Object arg) {
    if (arg == null) {
      return super.update(sql);
    } else {
      return super.update(sql, new Object[] { arg });
    }
  }

  /**
   * データを更新処理します。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li><code>org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate</code>の<code>update(java.lang.String, java.util.Map)</code>を呼出し、データを更新処理します。
   * <li>更新結果件数を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでデータを更新する時にこのメソッドを呼出す。
   * </dl>
   * 
   * @param sql 更新用のSQL文
   * @param paraMap パラメーターマップ
   * @return 更新件数
   */
  public int update(String sql, Map paraMap) {
    return getNamedJdbcTempleate().update(sql, paraMap);
  }

  /**
   * このインスタンス内部の命名JDBCテンプレートオブジェクトを取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>内部の命名JDBCテンプレートオブジェクトを返す。
   * <ul>
   * <li>内部の命名JDBCテンプレートオブジェクトがNull場合、検索結果件数制限つきのクラシックJDBCテンプレートオブジェクトを作成します。
   * <li>生成されたクラシックJDBCテンプレートを利用して作成された命名テンプレートオブジェクトを返す。
   * <li>内部の命名JDBCテンプレートオブジェクトがNullでない場合、内部の命名JDBCテンプレートオブジェクトを返す。
   * </ul>
   * <dt>利用方法：
   * <dd>データベースのデータを検索処理する時、 このメッソドが呼出された取得した命名JDBCテンプレートオブジェクトを利用して、データを検索します。
   * </dl>
   * 
   * @return 有効な命名JDBCテンプレートオブジェクト
   */
  protected NamedParameterJdbcTemplate getNamedJdbcTempleate() {
    if (namedJdbcTempleate == null) {
      JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
      jdbcTemplate.setMaxRows(getSearchLimit());
      return new NamedParameterJdbcTemplate(jdbcTemplate);
    } else
      return namedJdbcTempleate;
  }

  /**
   * このインスタンス内部の命名JDBCテンプレートオブジェクトを設定します。
   * <dl>
   * <dt>実装機能：
   * <dd>内部の命名JDBCテンプレートオブジェクトを設定します。
   * <dt>利用方法：
   * <dd>業務サービスで既存の命名JDBCテンプレートオブジェクトを利用する場合、このメソッドを呼出す。
   * </dl>
   * 
   * @param namedJdbcTempleate 有効な命名JDBCテンプレートオブジェクト
   */
  protected void setNamedJdbcTempleate(
    NamedParameterJdbcTemplate namedJdbcTempleate) {
    this.namedJdbcTempleate = namedJdbcTempleate;
  }

  // add - start - by qnes '09.07.06
  public <T> GTPage<T> queryPaginationEx(
		  String countSql
		  , List<Object> countArgs
		  , String sql
		  , ParameterizedRowMapper<T> rm
		  , List<Object> args
		  , int pageNo
		  , int pageSize
  ) {
	  //-----------------------------------------------------------------------
	  // 件数取得SQLの発行
	  //-----------------------------------------------------------------------
	  int totalCount = getTotalCountByCountSql( countSql, countArgs );
	  //-----------------------------------------------------------------------
	  // 最大件数を設定
	  // 　最大件数がsearchLimitよりも大きな値の場合、searchLimitを最大件数
	  // 　とする。
	  //-----------------------------------------------------------------------
	  int limitCount = totalCount;
	  if (this.searchLimit > 0 && this.searchLimit < totalCount) {
		  limitCount = this.searchLimit;
	  }
	  //-----------------------------------------------------------------------
	  // ページ数取得
	  //-----------------------------------------------------------------------
	  int pageTotalCount = getPageTotalCount(limitCount, pageSize);
	  //-----------------------------------------------------------------------
	  // ページ数取得
	  //-----------------------------------------------------------------------
	  GTResultSetExtractor rse =  new GTResultSetExtractor(pageNo,pageSize,rm);
	  super.query(sql, args.toArray(),  rse);
	  //-----------------------------------------------------------------------
	  // 結果取り出し
	  //-----------------------------------------------------------------------
	  List<T> items = rse.getItems();
	  GTPage<T> ps = new GTPage<T>(items, totalCount, limitCount, pageTotalCount, pageNo, pageSize);

	  return ps;
  }

  private int getTotalCountByCountSql(String countSql, List<Object> countArgs) {
	    return queryForInt( countSql, countArgs.toArray() );
  }
  // add - end - by qnes '09.07.06


}
