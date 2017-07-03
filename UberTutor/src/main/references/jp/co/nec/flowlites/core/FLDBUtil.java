package jp.co.nec.flowlites.core;

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
 * DB��`�ƥ���ƥ����饹��
 * <p>
 * <a href="FLDBUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class FLDBUtil
{
  /**
   * �ǥե���ȥ��󥹥ȥ饯����
   * <p>
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�Τ⤷�ޤ���
   * <dt>���÷�����
   * <dd>���ä��ޤ���
   * </dl>
   */
  public FLDBUtil() {
  }
  
  /**
   * �Y�����åȥ��֥������Ȥ����Хǩ`���������򥭩`�Ȥ���HashMap�˸�{���롣
   * <p>
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�Y�����åȤΥ᥿�ǩ`����ȡ�ä��ޤ���
   * <li>�Y�����åȤΥ᥿�ǩ`������Y�����åȤΥ��������ȡ�ä�����������ä��ơ��ޥåפ˥ǩ`�����{���ޤ���
   * <li>���ɤ����ޥåפ򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�Ǥ��Υ᥽�åɤ���������Y�����åȤ���ޥåפޤǉ�Q���ޤ���
   * </dl>
   * 
   * @param resultSet �Y�����åȥ��֥�������
   * @return �Y�����åȥ��֥������Ȥ�����ɤ����ޥåץ��֥�������
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
   * Object�ؤΥޥåԥ󥰥��饹��ȡ�ä��ޤ���
   * <p>
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>ָ�����饹�����ä��ơ�Object�ؤΥޥåԥ󥰥��饹���֥������Ȥ����ɤ��ޤ���
   * <li>Object�ؤΥޥåԥ󥰥��饹���֥������Ȥ򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�Ǥ��Υ᥽�åɤ��������Object�ؤΥޥåԥ󥰥��饹���֥������Ȥ�ȡ�ä��ޤ���
   * </dl>
   * 
   * @param requiredType Object�Υ��饹
   * @return Object�ؤΥޥåԥ󥰥��饹
   */
  public static <T> ParameterizedRowMapper<T> initParameterizedRowMapper(
    final Class<T> requiredType) {
    ParameterizedRowMapper<T> rm = new ParameterizedRowMapper<T>()
    {
      public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T target = null;
        try {
          target = requiredType.newInstance();
          FLDBUtil.beanCopy(target, FLDBUtil.resultExtractor(rs));
        } catch (Exception e) {
          e.printStackTrace();
        }
        return target;
      }
    };
    return rm;
  }

  /**
   * Object�Υץ�ѥƥ����򥳥ԩ`���ޤ���
   * <p>
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�ޥåץ��֥������Ȥ��邎ȡ�ä��ơ�ָ�����饹���֥������Ȥ˸�{���ޤ���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�Ǥ��Υ᥽�åɤ��������ָ�����饹���֥������Ȥ˥ޥåפ���΂����O�����ޤ���
   * </dl>
   * 
   * @param dest ���ԩ`�Ȥ�Object
   * @param orig ���ԩ`Ԫ��Object
   * @throws Exception bject�Υץ�ѥƥ����򥳥ԩ`����I���Ф˥���`���k����������
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