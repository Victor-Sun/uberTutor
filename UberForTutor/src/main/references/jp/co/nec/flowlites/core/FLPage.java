package jp.co.nec.flowlites.core;

import java.io.Serializable;
import java.util.List;

/**
 * �ک`���󥰥�ǥ륯�饹��
 * <p>
 * <a href="FLPage.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class FLPage<T> implements Serializable
{
  /**
   * serial Version UID
   */
  private static final long serialVersionUID = 1L;

  /* �ک`���󥰗�������Y���Ŀ�ꥹ�� */
  private List<T> items;

  /* �ک`���t�� */
  private int pageCount;

  /* ���ک`���η��� */
  private int pageNo;
  
  /* �t���� */
  private int itemCount;

  /* �����Y�����޼��� */
  private int limitCount;

  /* �ک`�������� */
  private int pageSize;

  /* ���ނ����ե饰 */
  private boolean totalOverFlag;

  /**
   * @deprecated �ƊX����ʤ�
   * �ǥե���ȥ��󥹥ȥ饯����
   * <p><dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�Τ⤷�ޤ���
   * <dt>���÷�����
   * <dd>�I��DAO�ǥѥ��`��ָ�����ʤ��Έ��ϡ����Υ᥽�åɤ���������FLPage ���֥������Ȥ����ɤ��ޤ���
   * </dl>
   */
  public FLPage() {
  }

  /**
   * @deprecated �ƊX����ʤ�
   * ָ�����줿�ک`�����Ŀ�ꥹ�Ȥȥک`���t����ꡢFLPage�򘋺B���ޤ���
   * <p><dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>{@link #setPageCount(int)}����������ک`���t�����O�����ޤ���
   * <li>{@link #setItems(List)}����������Ŀ�ꥹ�Ȥ��O�����ޤ���
   * <li>{@link #setCurrentPageNo(int)}����������ک`�����Ť��O�����ޤ���(�ǥե���Ȃ��ϡ�1���Ǥ���)
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥک`�����Ŀ�ꥹ�Ȥȥک`���t����ָ��������ϡ����Υ᥽�åɤ���������FLPage ���֥������Ȥ����ɤ��ޤ���
   * </dl>
   * 
   * @param items �ک`�����Ŀ�ꥹ��
   * @param pageCount �ک`���t��
   */
  public FLPage(List<T> items, int pageCount) {
    setPageCount(pageCount);
    setItems(items);
    setCurrentPageNo(1);
  }

  /**
   * @deprecated �ƊX����ʤ�
   * ָ�����줿�ک`�����Ŀ�ꥹ�Ȥȥک`���t����ꡢFLPage�򘋺B���ޤ���
   * <p><dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>{@link #setPageCount(int)}����������ک`���t�����O�����ޤ���
   * <li>{@link #setItems(List)}����������Ŀ�ꥹ�Ȥ��O�����ޤ���
   * <li>{@link #setCurrentPageNo(int)}����������ک`�����Ť��O�����ޤ���(�ǥե���Ȃ��ϡ�1���Ǥ���)
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥک`�����Ŀ�ꥹ�Ȥȥک`���t����ָ��������ϡ����Υ᥽�åɤ���������FLPage ���֥������Ȥ����ɤ��ޤ���
   * </dl>
   * 
   * @param items �ک`�����Ŀ�ꥹ��
   * @param pageCount �ک`���t��
   */
  public FLPage(List<T> items,int itemCount, int pageCount) {
    setPageCount(pageCount);
    setItemCount(itemCount);
    setItems(items);
    setCurrentPageNo(1);
  }

  /**
   * @deprecated �ƊX����ʤ�
   * ָ�����줿�ک`�����Ŀ�ꥹ�ȡ��ک`���t���ȥک`���η��Ť�ꡢFLPage�򘋺B���ޤ���
   * <p><dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>{@link #setPageCount(int)}����������ک`���t�����O�����ޤ���
   * <li>{@link #setItems(List)}����������Ŀ�ꥹ�Ȥ��O�����ޤ���
   * <li>{@link #setCurrentPageNo(int)}����������ک`�����Ť��O�����ޤ���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥک`�����Ŀ�ꥹ�ȡ��ک`���t���ȥک`���η��Ť�ָ��������ϡ����Υ᥽�åɤ���������FLPage ���֥������Ȥ����ɤ��ޤ���
   * </dl>
   * 
   * @param items �ک`�����Ŀ�ꥹ��
   * @param pageCount �ک`���t��
   * @param pageNo �ک`���η���
   */
  public FLPage(List<T> items,int itemCount, int pageCount, int pageNo) {
    setPageCount(pageCount);
    setItemCount(itemCount);
    setItems(items);
    setCurrentPageNo(pageNo);
  }

  /**
   * ָ�����줿�ک`�����Ŀ�ꥹ�ȡ��ک`���t�����t�����������Y�����޼����ȥک`���η��Ť�ꡢFLPage�򘋺B���ޤ���
   * <p><dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>{@link #setPageCount(int)}����������ک`���t�����O�����ޤ���
   * <li>{@link #setItemCount(int)}����������t�������O�����ޤ���
   * <li>{@link #setLimitCount(int)}��������������Y�����޼������O�����ޤ���
   * <li>{@link #setItems(List)}����������Ŀ�ꥹ�Ȥ��O�����ޤ���
   * <li>{@link #setCurrentPageNo(int)}����������ک`�����Ť��O�����ޤ���
   * <li>{@link #setPageSize(int)}����������ک`�����������O�����ޤ���
   * <li>{@link #setLimitFlg(boolean)}������������ނ����ե饰���O�����ޤ���
   * </ul>
   * <dt>���÷�����
   * <dd>�I��DAO�ǥک`�����Ŀ�ꥹ�ȡ��ک`���t���ȥک`���η��Ť�ָ��������ϡ����Υ᥽�åɤ���������FLPage ���֥������Ȥ����ɤ��ޤ���
   * </dl>
   * 
   * @param items �ک`�����Ŀ�ꥹ��
   * @param itemCount �t����
   * @param limitCount �����Y�����޼���
   * @param pageCount �ک`���t��
   * @param pageNo �ک`���η���
   * @param pageSize �ک`���Υ�����
   */
  public FLPage(List<T> items,int itemCount, int limitCount, 
      int pageCount, int pageNo, int pageSize) {
    setPageCount(pageCount);
    setItemCount(itemCount);
    setLimitCount(limitCount);
    setItems(items);
    setCurrentPageNo(pageNo);
    setPageSize(pageSize);
    setTotalOverFlag(itemCount > limitCount);
  }

  /**
   * �ک`���󥰗�������Y���Ŀ�ꥹ�Ȥ�ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�ک`���󥰗�������Y���Ŀ�ꥹ�Ȥ򷵤���
   * <dt>���÷�����
   * <dd>���饤����Ȼ���Ǥ��Υ᥽�åɤ��������ȡ�ä����Ŀ�ꥹ�Ȥ��ʾ���ޤ���
   * </dl>
   * 
   * @return �ک`���󥰗�������Y���Ŀ�ꥹ��
   */
  public List<T> getItems() {
    return items;
  }

  /**
   * �ک`���󥰗�������Y���Ŀ�ꥹ�Ȥ��O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤Υک`���󥰗�������Y���Ŀ�ꥹ�Ȥ��O�����ޤ���
   * <dt>���÷�����
   * <dd>�ک`���󥰗����νY���ꥹ�Ȥ����ä��ơ��I��DAO�Ǥ��Υ᥽�åɤ��������FLPage ���֥������ȤΥک`���󥰗�������Y���Ŀ�ꥹ�Ȥ��O�����ޤ���
   * </dl>
   * 
   * @param items �ک`���󥰗�������Y���Ŀ�ꥹ��
   */
  public void setItems(List<T> items) {
    this.items = items;
  }

  /**
   * �ک`���t����ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�ک`���t���򷵤���
   * <dt>���÷�����
   * <dd>�ک`���󥰥����饤�֥���ڲ��Ǥ��Υ᥽�åɤ��������ȡ�ä���ک`���t�������ä��ơ����饤����Ȼ���Ǳ�ʾ�äΥک`���󥰥�󥯤����ɤ��ޤ���
   * </dl>
   * 
   * @return �ک`���t��
   */
  public int getPageCount() {
    return pageCount;
  }

  /**
   * �ک`���t�����O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤Υک`���t�����O�����ޤ���
   * <dt>���÷�����
   * <dd>�ک`���󥰗���ȡ�ä���ک`���t�������ä��ơ��I��DAO�Ǥ��Υ᥽�åɤ��������FLPage ���֥������ȤΥک`���t�����O�����ޤ���
   * </dl>
   * 
   * @param pageCount �ک`���t��
   */
  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }
  
  /**
   * �Υک`�����Пo�����å����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>�Υک`������ �� ���ک`�����ň��ϡ�true �򷵤���
   * <li>�Υک`������ ���� ���ک`�����ň��ϡ�false �򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�ک`���󥰥����饤�֥���ڲ��Ǥ��Υ᥽�åɤ���������Υک`�����Пo������å����ޤ���
   * </dl>
   * 
   * @return �Υک`�����Пo true �Фꡢfalse �o��
   */
  public boolean hasNext() {
    if (getNextIndex() > this.pageNo) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * �Υک`���Υ٩`�����Ť�ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>���ک`������ �� �ک`���t�����ϡ��Υک`���Υ٩`�����Ť򷵤���
   * <li>���ک`������ �� �ک`���t�����ϡ����ک`�����Ť򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�ک`���󥰥����饤�֥���ڲ��Ǥ��Υ᥽�åɤ���������Υک`���Υ٩`�����Ť�ȡ�ä��ޤ���
   * </dl>
   * 
   * @return �Υک`���Υ٩`������
   */
  public int getNextIndex() {
    if (this.pageNo < this.pageCount) {
      return this.pageNo+1;
    } else {
      return pageNo;
    }
  }
  
  /**
   * ǰ�ک`�����Пo�����å����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>ǰ�ک`������ �� ���ک`�����ň��ϡ�true �򷵤���
   * <li>ǰ�ک`������ ���� ���ک`�����ň��ϡ�false �򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�ک`���󥰥����饤�֥���ڲ��Ǥ��Υ᥽�åɤ��������ǰ�ک`�����Пo������å����ޤ���
   * </dl>
   * 
   * @return ǰ�ک`�����Пo true �Фꡢfalse �o��
   */
  public boolean hasPrevious() {
    if (getPreviousIndex() < this.pageNo) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * ǰ�ک`���Υ٩`�����Ť�ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>
   * <ul>
   * <li>���ک`������ �� ��0�����ϡ�ǰ�ک`���Υ٩`�����Ť򷵤���
   * <li>���ک`������ �� ��0�����ϡ����ک`�����Ť򷵤���
   * </ul>
   * <dt>���÷�����
   * <dd>�ک`���󥰥����饤�֥���ڲ��Ǥ��Υ᥽�åɤ��������ǰ�ک`���Υ٩`�����Ť�ȡ�ä��ޤ���
   * </dl>
   * 
   * @return ǰ�ک`���Υ٩`������
   */
  public int getPreviousIndex() {
    if (this.pageNo > 0) {
      return this.pageNo - 1;
    } else {
      return this.pageNo;
    }
  }

  /**
   * ���ک`�����Ť�ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���ک`�����Ť򷵤���
   * <dt>���÷�����
   * <dd>�ک`���󥰥����饤�֥���ڲ��Ǥ��Υ᥽�åɤ�����������ک`�����Ť�ȡ�ä��ޤ���
   * </dl>
   * 
   * @return ���ک`������
   */
  public int getCurrentPageNo() {
    return pageNo;
  }

  /**
   * ���ک`�����Ť��O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤ε��ک`�����Ť��O�����ޤ���
   * <dt>���÷�����
   * <dd>�ک`���󥰗���ȡ�ä��뵱�ک`�����Ť����ä��ơ��I��DAO�Ǥ��Υ᥽�åɤ��������FLPage ���֥������Ȥε��ک`�����Ť��O�����ޤ���
   * </dl>
   * 
   * @param pageNo �ک`������
   */
  public void setCurrentPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  /**
   * �t������ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�t�����򷵤���
   * <dt>���÷�����
   * <dd>�ک`���󥰥����饤�֥���ڲ��Ǥ��Υ᥽�åɤ���������t������ȡ�ä��ޤ���
   * </dl>
   * 
   * @return �t����
   */
  public int getItemCount() {
    return itemCount;
  }

  /**
   * �t�������O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤ξt�������O�����ޤ���
   * <dt>���÷�����
   * <dd>�ک`���󥰗���ȡ�ä���t���������ä��ơ��I��DAO�Ǥ��Υ᥽�åɤ��������FLPage ���֥������Ȥξt�������O�����ޤ���
   * </dl>
   * 
   * @param itemCount �t����
   */
  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  /**
   * �����Y�����޼�����ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�����Y�����޼����򷵤���
   * <dt>���÷�����
   * <dd>�ک`���󥰥����饤�֥���ڲ��Ǥ��Υ᥽�åɤ�������������Y�����޼�����ȡ�ä��ޤ���
   * </dl>
   * 
   * @return �����Y�����޼���
   */
  public int getLimitCount() {
  return limitCount;
  }

  /**
   * �����Y�����޼������O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤Η����Y�����޼������O�����ޤ���
   * <dt>���÷�����
   * <dd>�ک`���󥰗��������Y�����޼��������ä��ơ��I��DAO�Ǥ��Υ᥽�åɤ��������FLPage ���֥������ȤΗ����Y�����޼������O�����ޤ���
   * </dl>
   * 
   * @param limitCount �����Y�����޼���
   */
  public void setLimitCount(int limitCount) {
  this.limitCount = limitCount;
  }

  /**
   * ���ނ����ե饰��ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���ނ����ե饰�򷵤���
   * <dt>���÷�����
   * <dd>�ک`���󥰥����饤�֥���ڲ��Ǥ��Υ᥽�åɤ�����������ނ����ե饰��ȡ�ä��ޤ���
   * </dl>
   * 
   * @return ���ނ����ե饰
   */
  public boolean isTotalOverFlag() {
    return totalOverFlag;
  }

  /**
   * ���ނ����ե饰���O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤����ނ����ե饰���O�����ޤ���
   * <dt>���÷�����
   * <dd>�ک`�������ނ����ե饰�����ä��ơ��I��DAO�Ǥ��Υ᥽�åɤ��������FLPage ���֥������Ȥ����ނ����ե饰���O�����ޤ���
   * </dl>
   * 
   * @param totalOverFlag ���ނ����ե饰
   */
  public void setTotalOverFlag(boolean totalOverFlag) {
    this.totalOverFlag = totalOverFlag;
  }

  /**
   * �ک`����������ȡ�ä��ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>�ک`���������򷵤���
   * <dt>���÷�����
   * <dd>�ک`���󥰥����饤�֥���ڲ��Ǥ��Υ᥽�åɤ���������ک`����������ȡ�ä��ޤ���
   * </dl>
   * 
   * @return �ک`��������
   */
  public int getPageSize() {
  return pageSize;
  }

  /**
   * �ک`�����������O�����ޤ���
   * <dl>
   * <dt>�gװ�C�ܣ�
   * <dd>���Υ��󥹥��󥹤Υک`�����������O�����ޤ���
   * <dt>���÷�����
   * <dd>�ک`���󥰥ک`�������������ä��ơ��I��DAO�Ǥ��Υ᥽�åɤ��������FLPage ���֥������ȤΥک`�����������O�����ޤ���
   * </dl>
   * 
   * @param pageSize �ک`��������
   */
  public void setPageSize(int pageSize) {
  this.pageSize = pageSize;
  }

}