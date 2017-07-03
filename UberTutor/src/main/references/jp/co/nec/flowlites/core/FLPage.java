package jp.co.nec.flowlites.core;

import java.io.Serializable;
import java.util.List;

/**
 * ページングモデルクラス。
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

  /* ページング検索する結果項目リスト */
  private List<T> items;

  /* ページ総数 */
  private int pageCount;

  /* 当ページの番号 */
  private int pageNo;
  
  /* 総件数 */
  private int itemCount;

  /* 検索結果制限件数 */
  private int limitCount;

  /* ページサイズ */
  private int pageSize;

  /* 上限値超フラグ */
  private boolean totalOverFlag;

  /**
   * @deprecated 推奨されない
   * デフォルトコンストラクタ。
   * <p><dl>
   * <dt>実装機能：
   * <dd>何もしません。
   * <dt>利用方法：
   * <dd>業務DAOでパラメータ指定しないの場合、このメソッドが呼出し、FLPage オブジェクトを作成します。
   * </dl>
   */
  public FLPage() {
  }

  /**
   * @deprecated 推奨されない
   * 指定されたページの項目リストとページ総数より、FLPageを構築します。
   * <p><dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>{@link #setPageCount(int)}を呼出し、ページ総数を設定します。
   * <li>{@link #setItems(List)}を呼出し、項目リストを設定します。
   * <li>{@link #setCurrentPageNo(int)}を呼出し、ページ番号を設定します。(デフォルト値は「1」です。)
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでページの項目リストとページ総数を指定する場合、このメソッドが呼出し、FLPage オブジェクトを作成します。
   * </dl>
   * 
   * @param items ページの項目リスト
   * @param pageCount ページ総数
   */
  public FLPage(List<T> items, int pageCount) {
    setPageCount(pageCount);
    setItems(items);
    setCurrentPageNo(1);
  }

  /**
   * @deprecated 推奨されない
   * 指定されたページの項目リストとページ総数より、FLPageを構築します。
   * <p><dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>{@link #setPageCount(int)}を呼出し、ページ総数を設定します。
   * <li>{@link #setItems(List)}を呼出し、項目リストを設定します。
   * <li>{@link #setCurrentPageNo(int)}を呼出し、ページ番号を設定します。(デフォルト値は「1」です。)
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでページの項目リストとページ総数を指定する場合、このメソッドが呼出し、FLPage オブジェクトを作成します。
   * </dl>
   * 
   * @param items ページの項目リスト
   * @param pageCount ページ総数
   */
  public FLPage(List<T> items,int itemCount, int pageCount) {
    setPageCount(pageCount);
    setItemCount(itemCount);
    setItems(items);
    setCurrentPageNo(1);
  }

  /**
   * @deprecated 推奨されない
   * 指定されたページの項目リスト、ページ総数とページの番号より、FLPageを構築します。
   * <p><dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>{@link #setPageCount(int)}を呼出し、ページ総数を設定します。
   * <li>{@link #setItems(List)}を呼出し、項目リストを設定します。
   * <li>{@link #setCurrentPageNo(int)}を呼出し、ページ番号を設定します。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでページの項目リスト、ページ総数とページの番号を指定する場合、このメソッドが呼出し、FLPage オブジェクトを作成します。
   * </dl>
   * 
   * @param items ページの項目リスト
   * @param pageCount ページ総数
   * @param pageNo ページの番号
   */
  public FLPage(List<T> items,int itemCount, int pageCount, int pageNo) {
    setPageCount(pageCount);
    setItemCount(itemCount);
    setItems(items);
    setCurrentPageNo(pageNo);
  }

  /**
   * 指定されたページの項目リスト、ページ総数、総件数、検索結果制限件数とページの番号より、FLPageを構築します。
   * <p><dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>{@link #setPageCount(int)}を呼出し、ページ総数を設定します。
   * <li>{@link #setItemCount(int)}を呼出し、総件数を設定します。
   * <li>{@link #setLimitCount(int)}を呼出し、検索結果制限件数を設定します。
   * <li>{@link #setItems(List)}を呼出し、項目リストを設定します。
   * <li>{@link #setCurrentPageNo(int)}を呼出し、ページ番号を設定します。
   * <li>{@link #setPageSize(int)}を呼出し、ページサイズを設定します。
   * <li>{@link #setLimitFlg(boolean)}を呼出し、上限値超フラグを設定します。
   * </ul>
   * <dt>利用方法：
   * <dd>業務DAOでページの項目リスト、ページ総数とページの番号を指定する場合、このメソッドが呼出し、FLPage オブジェクトを作成します。
   * </dl>
   * 
   * @param items ページの項目リスト
   * @param itemCount 総件数
   * @param limitCount 検索結果制限件数
   * @param pageCount ページ総数
   * @param pageNo ページの番号
   * @param pageSize ページのサイズ
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
   * ページング検索する結果項目リストを取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>ページング検索する結果項目リストを返す。
   * <dt>利用方法：
   * <dd>クライアント画面でこのメソッドを呼出し、取得する項目リストを表示します。
   * </dl>
   * 
   * @return ページング検索する結果項目リスト
   */
  public List<T> getItems() {
    return items;
  }

  /**
   * ページング検索する結果項目リストを設定します。
   * <dl>
   * <dt>実装機能：
   * <dd>このインスタンスのページング検索する結果項目リストを設定します。
   * <dt>利用方法：
   * <dd>ページング検索の結果リストを利用して、業務DAOでこのメソッドを呼出し、FLPage オブジェクトのページング検索する結果項目リストを設定します。
   * </dl>
   * 
   * @param items ページング検索する結果項目リスト
   */
  public void setItems(List<T> items) {
    this.items = items;
  }

  /**
   * ページ総数を取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>ページ総数を返す。
   * <dt>利用方法：
   * <dd>ページングタグライブラリ内部でこのメソッドを呼出し、取得するページ総数を利用して、クライアント画面で表示用のページングリンクを作成します。
   * </dl>
   * 
   * @return ページ総数
   */
  public int getPageCount() {
    return pageCount;
  }

  /**
   * ページ総数を設定します。
   * <dl>
   * <dt>実装機能：
   * <dd>このインスタンスのページ総数を設定します。
   * <dt>利用方法：
   * <dd>ページング検索取得するページ総数を利用して、業務DAOでこのメソッドを呼出し、FLPage オブジェクトのページ総数を設定します。
   * </dl>
   * 
   * @param pageCount ページ総数
   */
  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }
  
  /**
   * 次ページの有無チェックします。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>次ページ番号 ＞ 当ページ番号場合、true を返す。
   * <li>次ページ番号 ＜＝ 当ページ番号場合、false を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>ページングタグライブラリ内部でこのメソッドを呼出し、次ページの有無をチェックします。
   * </dl>
   * 
   * @return 次ページの有無 true 有り、false 無し
   */
  public boolean hasNext() {
    if (getNextIndex() > this.pageNo) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 次ページのベージ番号を取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>当ページ番号 ＜ ページ総数場合、次ページのベージ番号を返す。
   * <li>当ページ番号 ＝ ページ総数場合、当ページ番号を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>ページングタグライブラリ内部でこのメソッドを呼出し、次ページのベージ番号を取得します。
   * </dl>
   * 
   * @return 次ページのベージ番号
   */
  public int getNextIndex() {
    if (this.pageNo < this.pageCount) {
      return this.pageNo+1;
    } else {
      return pageNo;
    }
  }
  
  /**
   * 前ページの有無チェックします。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>前ページ番号 ＜ 当ページ番号場合、true を返す。
   * <li>前ページ番号 ＞＝ 当ページ番号場合、false を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>ページングタグライブラリ内部でこのメソッドを呼出し、前ページの有無をチェックします。
   * </dl>
   * 
   * @return 前ページの有無 true 有り、false 無し
   */
  public boolean hasPrevious() {
    if (getPreviousIndex() < this.pageNo) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 前ページのベージ番号を取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>
   * <ul>
   * <li>当ページ番号 ＞ 「0」場合、前ページのベージ番号を返す。
   * <li>当ページ番号 ＝ 「0」場合、当ページ番号を返す。
   * </ul>
   * <dt>利用方法：
   * <dd>ページングタグライブラリ内部でこのメソッドを呼出し、前ページのベージ番号を取得します。
   * </dl>
   * 
   * @return 前ページのベージ番号
   */
  public int getPreviousIndex() {
    if (this.pageNo > 0) {
      return this.pageNo - 1;
    } else {
      return this.pageNo;
    }
  }

  /**
   * 当ページ番号を取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>当ページ番号を返す。
   * <dt>利用方法：
   * <dd>ページングタグライブラリ内部でこのメソッドを呼出し、当ページ番号を取得します。
   * </dl>
   * 
   * @return 当ページ番号
   */
  public int getCurrentPageNo() {
    return pageNo;
  }

  /**
   * 当ページ番号を設定します。
   * <dl>
   * <dt>実装機能：
   * <dd>このインスタンスの当ページ番号を設定します。
   * <dt>利用方法：
   * <dd>ページング検索取得する当ページ番号を利用して、業務DAOでこのメソッドを呼出し、FLPage オブジェクトの当ページ番号を設定します。
   * </dl>
   * 
   * @param pageNo ページ番号
   */
  public void setCurrentPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  /**
   * 総件数を取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>総件数を返す。
   * <dt>利用方法：
   * <dd>ページングタグライブラリ内部でこのメソッドを呼出し、総件数を取得します。
   * </dl>
   * 
   * @return 総件数
   */
  public int getItemCount() {
    return itemCount;
  }

  /**
   * 総件数を設定します。
   * <dl>
   * <dt>実装機能：
   * <dd>このインスタンスの総件数を設定します。
   * <dt>利用方法：
   * <dd>ページング検索取得する総件数を利用して、業務DAOでこのメソッドを呼出し、FLPage オブジェクトの総件数を設定します。
   * </dl>
   * 
   * @param itemCount 総件数
   */
  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  /**
   * 検索結果制限件数を取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>検索結果制限件数を返す。
   * <dt>利用方法：
   * <dd>ページングタグライブラリ内部でこのメソッドを呼出し、検索結果制限件数を取得します。
   * </dl>
   * 
   * @return 検索結果制限件数
   */
  public int getLimitCount() {
  return limitCount;
  }

  /**
   * 検索結果制限件数を設定します。
   * <dl>
   * <dt>実装機能：
   * <dd>このインスタンスの検索結果制限件数を設定します。
   * <dt>利用方法：
   * <dd>ページング検索検索結果制限件数を利用して、業務DAOでこのメソッドを呼出し、FLPage オブジェクトの検索結果制限件数を設定します。
   * </dl>
   * 
   * @param limitCount 検索結果制限件数
   */
  public void setLimitCount(int limitCount) {
  this.limitCount = limitCount;
  }

  /**
   * 上限値超フラグを取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>上限値超フラグを返す。
   * <dt>利用方法：
   * <dd>ページングタグライブラリ内部でこのメソッドを呼出し、上限値超フラグを取得します。
   * </dl>
   * 
   * @return 上限値超フラグ
   */
  public boolean isTotalOverFlag() {
    return totalOverFlag;
  }

  /**
   * 上限値超フラグを設定します。
   * <dl>
   * <dt>実装機能：
   * <dd>このインスタンスの上限値超フラグを設定します。
   * <dt>利用方法：
   * <dd>ページング上限値超フラグを利用して、業務DAOでこのメソッドを呼出し、FLPage オブジェクトの上限値超フラグを設定します。
   * </dl>
   * 
   * @param totalOverFlag 上限値超フラグ
   */
  public void setTotalOverFlag(boolean totalOverFlag) {
    this.totalOverFlag = totalOverFlag;
  }

  /**
   * ページサイズを取得します。
   * <dl>
   * <dt>実装機能：
   * <dd>ページサイズを返す。
   * <dt>利用方法：
   * <dd>ページングタグライブラリ内部でこのメソッドを呼出し、ページサイズを取得します。
   * </dl>
   * 
   * @return ページサイズ
   */
  public int getPageSize() {
  return pageSize;
  }

  /**
   * ページサイズを設定します。
   * <dl>
   * <dt>実装機能：
   * <dd>このインスタンスのページサイズを設定します。
   * <dt>利用方法：
   * <dd>ページングページサイズを利用して、業務DAOでこのメソッドを呼出し、FLPage オブジェクトのページサイズを設定します。
   * </dl>
   * 
   * @param pageSize ページサイズ
   */
  public void setPageSize(int pageSize) {
  this.pageSize = pageSize;
  }

}