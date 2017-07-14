package jp.co.nec.flowlites.core;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * �ک`���󥰗����Y�����åȥ��֥��������å�ǥ륯�饹��
 * <p>
 * <a href="FLResultSetExtractor.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class FLResultSetExtractor implements ResultSetExtractor {

  /* �Y�����åȥ��֥������Ȥ�ꡢ�ꥹ�Ȥ����� */
  private List items;

  /* �ک`������ */
  private int pageNo;

  /* �ک`�������� */
  private int pageSize;

  /* �ޥåԥ󥰥��饹���֥������� */
  private final RowMapper rowMapper;

  /**
   * ָ���ک`�����Ťȥ������ˤ�ꡢFLResultSetExtractor�򘋺B���ޤ���
   * <p><dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>{@link #setPageNo(int)}����������ک`�����Ť��O�����ޤ���
   * <li>{@link #setPageSize(int)}����������ک`�����������O�����ޤ���
   * <li>�ޥåԥ󥰥��饹���֥������Ȥ�Null���O�����ޤ���
   * </ul>
   * <dt>���÷�����
   * <dd>�ک`�����Ťȥک`����������ָ��������ϡ�
   *     ���Υ��󥹥ȥ饯���`����������FLResultSetExtractor ���֥������Ȥ����ɤ��ޤ���
   * </dl>
   * 
   * @param pageNo �ک`������
   * @param pageSize �ک`��������
   */
  public FLResultSetExtractor(int pageNo, int pageSize){
    setPageNo(pageNo);
    setPageSize(pageSize);
    this.rowMapper=null;
  }

  /**
   * ָ���ک`�����š��������ȥޥåԥ󥰥��饹���֥������Ȥˤ�ꡢFLResultSetExtractor�򘋺B���ޤ���
   * <p><dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>{@link #setPageNo(int)}����������ک`�����Ť��O�����ޤ���
   * <li>{@link #setPageSize(int)}����������ک`�����������O�����ޤ���
   * <li>�ޥåԥ󥰥��饹���֥������Ȥ�rowMapper���O�����ޤ���
   * </ul>
   * <dt>���÷�����
   * <dd>�ک`�����š��ک`���������ȥޥåԥ󥰥��饹���֥�������ָ��������ϡ�
   *     ���Υ��󥹥ȥ饯���`����������FLResultSetExtractor ���֥������Ȥ����ɤ��ޤ���
   * </dl>
   * 
   * @param pageNo �`������
   * @param pageSize �ک`��������
   * @param rowMapper �ޥåԥ󥰥��饹���֥�������
   */
  public FLResultSetExtractor(int pageNo, int pageSize, RowMapper rowMapper){
      setPageNo(pageNo);
      setPageSize(pageSize);
      this.rowMapper = rowMapper;
  }

  /**
   * �ک`���󥰗�������Y�����åȥ��֥������ȥꥹ�Ȥ��O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�ک`���󥰗�������Y�����åȥ��֥������ȥꥹ�Ȥ��O�����ޤ���
   * <dt>���÷�����
   * <dd>���Υ᥽�åɤ��������ȡ�ä���Y�����åȥ��֥������ȥꥹ�Ȥ��O�����ޤ���
   * </dl>
   *
   * @param items �ک`���󥰗�������Y�����åȥ��֥������ȥꥹ��
  */
  public void setItems(List items) {
    this.items = items;
  }

  /**
   * �ک`���󥰗�������Y�����åȥ��֥������ȥꥹ�Ȥ�ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�ک`���󥰗�������Y�����åȥ��֥������ȥꥹ�Ȥ򷵤���
   * <dt>���÷�����
   * <dd>���Υ᥽�åɤ��������ȡ�ä���Y�����åȥ��֥������ȥꥹ�Ȥ�ȡ�ä��ޤ���
   * </dl>
   * 
   * @return �ک`���󥰗�������Y�����åȥ��֥������ȥꥹ��
  */
  public List getItems() {
    return items;
  }

  /**
   * ���ک`�����Ť��O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤Υک`�����Ť��O�����ޤ���
   * <dt>���÷�����
   * <dd>�ک`���󥰗���ȡ�ä���ک`�����Ť����ä��ơ�
   *     ���Υ᥽�åɤ��������FLResultSetExtractor ���֥������ȤΥک`�����Ť��O�����ޤ���
   * </dl>
   * 
   * @param pageNo �ک`������
   */
  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  /**
   * �ک`�����������O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤Υک`�����������O�����ޤ���
   * <dt>���÷�����
   * <dd>�ک`���󥰗���ȡ�ä���ک`�������������ä��ơ�
   *     ���Υ᥽�åɤ��������
   *     FLResultSetExtractor ���֥������ȤΥک`�����������O�����ޤ���
   * </dl>
   * 
   * @param pageSize �ک`��������
   */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
  
  /**
   * �Y�����åȥ��֥������Ȥ���ꥹ�Ȥ����ɤ��롣
   * <p>
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�Y�����åȤΥ᥿�ǩ`����ȡ�ä��ޤ���
   * <li>�ޥåԥ󥰥��饹���֥������Ȥ�null���ϡ�
   * �����Y�����åȤΥ᥿�ǩ`������Y�����åȤΥ��������ȡ�ä���
   *     ��������ä��ơ��ޥåפ˥ǩ`�����{���ޤ���
   *     �ޥåפ�ꥹ�Ȥ��{���ޤ�
   * <li>��ӛ������ϡ�rowMapper�����ä��ơ��ޥåפ˥ǩ`�����{���ޤ���
   * �����ޥåפ�ꥹ�Ȥ��{���ޤ�
   * <li>���ɤ����ꥹ�Ȥ򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>spring�ڲ���ԓ���᥽�åɤ���������Y�����åȤ��錝��ǩ`����ȡ������ޤ���
   * </dl>
   * 
   * @param rs �Y�����åȥ��֥�������
   * @return �ک`���󥰗�������Y�����åȥ��֥������ȥꥹ��
   */
  @SuppressWarnings("unchecked")
  public Object extractData(ResultSet rs) throws SQLException,
      DataAccessException {

    /* startRecord��0���������� 
       ex) �ک`��������=10,�ک`������=5�Έ��Ϥϡ�startRecord=40
     */
    int startRecord = (pageNo - 1) * pageSize;
    int pageSize = this.pageSize;

    ResultSetMetaData rsmd = rs.getMetaData();

      int colsCount = rsmd.getColumnCount();
      this.items = new ArrayList();

      int rowNum = 0;
      while (rs.next()) {

        if (0 == pageSize) {
          break;
        }

        if (rowNum >= startRecord) {
          if (null == this.rowMapper) {
            Map mapOfColValues = null;

            mapOfColValues = new LinkedHashMap(colsCount);

            for (int i = 1; i <= colsCount; i++) {
              mapOfColValues.put(rsmd.getColumnName(i).toUpperCase(), JdbcUtils.getResultSetValue(rs, i));
            }
            items.add(mapOfColValues);
          } else {
            items.add(this.rowMapper.mapRow(rs, rowNum));
          }
          pageSize--;
        }
        rowNum++;
      }
      return items;
  }
}