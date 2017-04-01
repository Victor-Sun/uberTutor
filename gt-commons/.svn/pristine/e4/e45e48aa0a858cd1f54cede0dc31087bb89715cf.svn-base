package com.gnomon.common.page;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * DBユーティリティクラス。
 * <p>
 * <a href="FLDBUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class GTDBUtil
{
  /**
   * デフォルトコンストラクタ。
   * <p>
   * <dl>
   * <dt>実装機能：
   * <dd>何もしません。
   * <dt>利用方法：
   * <dd>利用しません。
   * </dl>
   */
  public GTDBUtil() {
  }
  
  /**
   * 結果セットオブジェクトから行データは列名をキーとするHashMapに格納する。
   * <p>
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>結果セットのメタデータを取得します。
   * <li>結果セットのメタデータから結果セットのコラム名を取得し、これを利用して、マップにデータを格納します。
   * <li>作成したマップを返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでこのメソッドを呼出し、結果セットからマップまで変換します。
   * </dl>
   * 
   * @param resultSet 結果セットオブジェクト
   * @return 結果セットオブジェクトより作成したマップオブジェクト
   */
  public static HashMap resultExtractor(ResultSet resultSet)
    throws java.sql.SQLException {
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    int colCount = resultSetMetaData.getColumnCount();
    HashMap<String, Object> tmpHash = new HashMap<String, Object>();
    for (int i = 1; i <= colCount; i++) {
    	tmpHash.put(resultSetMetaData.getColumnName(i), JdbcUtils.getResultSetValue(resultSet, i));
    }
    return tmpHash;
  }

  /**
   * Objectへのマッピングクラスを取得します。
   * <p>
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>指定クラスを利用して、Objectへのマッピングクラスオブジェクトを作成します。
   * <li>Objectへのマッピングクラスオブジェクトを返す。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでこのメソッドを呼出し、Objectへのマッピングクラスオブジェクトを取得します。
   * </dl>
   * 
   * @param requiredType Objectのクラス
   * @return Objectへのマッピングクラス
   */
  public static <T> ParameterizedRowMapper<T> initParameterizedRowMapper(
    final Class<T> requiredType) {
    ParameterizedRowMapper<T> rm = new ParameterizedRowMapper<T>()
    {
      public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T target = null;
        try {
          target = requiredType.newInstance();
          GTDBUtil.beanCopy(target, GTDBUtil.resultExtractor(rs));
        } catch (Exception e) {
          e.printStackTrace();
        }
        return target;
      }
    };
    return rm;
  }

  /**
   * Objectのプロパティ値をコピーします。
   * <p>
   * <dl>
   * <dt>実装機能：
   * <dd>マップオブジェクトから値取得して、指定クラスオブジェクトに格納します。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでこのメソッドを呼出し、指定クラスオブジェクトにマップからの値を設定します。
   * </dl>
   * 
   * @param dest コピー先のObject
   * @param orig コピー元のObject
   * @throws Exception bjectのプロパティ値をコピーする処理中にエラーが発生した場合
   */
  @SuppressWarnings("unchecked")
  private static void beanCopy(Object dest, Map orig) throws Exception {
    PropertyDescriptor fields[] = PropertyUtils.getPropertyDescriptors(dest);
    String propName = "";
    Object value = null;
    for (int i = 0; i < fields.length; i++) {
      propName = fields[i].getName();
      if (orig.containsKey(propName.toUpperCase())) {
        value = orig.get(propName.toUpperCase());
        orig.remove(propName.toUpperCase());
        orig.put(propName, value);
      }
    }
    try {
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        beanUtilsBean.getConvertUtils().register(new BigDecimalConverter(null), BigDecimal.class);
      BeanUtils.copyProperties(dest, orig);
    } catch (Exception ex) {
      throw ex;
    }
  }
}
