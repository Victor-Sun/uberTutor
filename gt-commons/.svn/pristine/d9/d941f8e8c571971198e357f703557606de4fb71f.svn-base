package com.gnomon.common.page;

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
 * ページング検索結果セットオブジェクト用モデルクラス。
 * <p>
 * <a href="FLResultSetExtractor.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class GTResultSetExtractor implements ResultSetExtractor {

	/* 結果セットオブジェクトより、リストを作成 */
	private List items;

	/* ページ番号 */
	private int pageNo;

	/* ページサイズ */
	private int pageSize;

	/* マッピングクラスオブジェクト */
	private final RowMapper rowMapper;

	/**
	 * 指定ページ番号とサイズにより、FLResultSetExtractorを構築します。
	 * <p><dl>
	 * <dt>実装機能：
	 * <dd>
	 * <ul>
	 * <li>{@link #setPageNo(int)}を呼出し、ページ番号を設定します。
	 * <li>{@link #setPageSize(int)}を呼出し、ページサイズを設定します。
	 * <li>マッピングクラスオブジェクトはNullを設定します。
	 * </ul>
	 * <dt>利用方法：
	 * <dd>ページ番号とページサイズを指定する場合、
	 *     このコンストラクターが呼出し、FLResultSetExtractor オブジェクトを作成します。
	 * </dl>
	 * 
	 * @param pageNo ページ番号
	 * @param pageSize ページサイズ
	 */
	public GTResultSetExtractor(int pageNo, int pageSize){
	  setPageNo(pageNo);
	  setPageSize(pageSize);
	  this.rowMapper=null;
	}

	/**
	 * 指定ページ番号、サイズとマッピングクラスオブジェクトにより、FLResultSetExtractorを構築します。
	 * <p><dl>
	 * <dt>実装機能：
	 * <dd>
	 * <ul>
	 * <li>{@link #setPageNo(int)}を呼出し、ページ番号を設定します。
	 * <li>{@link #setPageSize(int)}を呼出し、ページサイズを設定します。
	 * <li>マッピングクラスオブジェクトはrowMapperを設定します。
	 * </ul>
	 * <dt>利用方法：
	 * <dd>ページ番号、ページサイズとマッピングクラスオブジェクト指定する場合、
	 *     このコンストラクターが呼出し、FLResultSetExtractor オブジェクトを作成します。
	 * </dl>
	 * 
	 * @param pageNo ージ番号
	 * @param pageSize ページサイズ
	 * @param rowMapper マッピングクラスオブジェクト
	 */
	public GTResultSetExtractor(int pageNo, int pageSize, RowMapper rowMapper){
		  setPageNo(pageNo);
		  setPageSize(pageSize);
		  this.rowMapper = rowMapper;
	}

	/**
	 * ページング検索する結果セットオブジェクトリストを設定します。
	 * <dl>
	 * <dt>実装機能：
	 * <dd>ページング検索する結果セットオブジェクトリストを設定します。
	 * <dt>利用方法：
	 * <dd>このメソッドを呼出し、取得する結果セットオブジェクトリストを設定します。
	 * </dl>
	 *
	 * @param items ページング検索する結果セットオブジェクトリスト
	*/
	public void setItems(List items) {
		this.items = items;
	}

	/**
	 * ページング検索する結果セットオブジェクトリストを取得します。
	 * <dl>
	 * <dt>実装機能：
	 * <dd>ページング検索する結果セットオブジェクトリストを返す。
	 * <dt>利用方法：
	 * <dd>このメソッドを呼出し、取得する結果セットオブジェクトリストを取得します。
	 * </dl>
	 * 
	 * @return ページング検索する結果セットオブジェクトリスト
	*/
	public List getItems() {
		return items;
	}

	/**
	 * 当ページ番号を設定します。
	 * <dl>
	 * <dt>実装機能：
	 * <dd>このインスタンスのページ番号を設定します。
	 * <dt>利用方法：
	 * <dd>ページング検索取得するページ番号を利用して、
	 *     このメソッドを呼出し、FLResultSetExtractor オブジェクトのページ番号を設定します。
	 * </dl>
	 * 
	 * @param pageNo ページ番号
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * ページサイズを設定します。
	 * <dl>
	 * <dt>実装機能：
	 * <dd>このインスタンスのページサイズを設定します。
	 * <dt>利用方法：
	 * <dd>ページング検索取得するページサイズを利用して、
	 *     このメソッドを呼出し、
	 *     FLResultSetExtractor オブジェクトのページサイズを設定します。
	 * </dl>
	 * 
	 * @param pageSize ページサイズ
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 結果セットオブジェクトからリストを作成する。
	 * <p>
	 * <dl>
	 * <dt>実装機能：
	 * <dd>
	 * <ul>
	 * <li>結果セットのメタデータを取得します。
	 * <li>マッピングクラスオブジェクトがnull場合、
	 * 　　結果セットのメタデータから結果セットのコラム名を取得し、
	 *     これを利用して、マップにデータを格納します。
	 *     マップをリストを格納します
	 * <li>上記以外場合、rowMapperを利用して、マップにデータを格納します。
	 * 　　マップをリストを格納します
	 * <li>作成したリストを返す。
	 * </ul>
	 * <dt>利用方法：
	 * <dd>spring内部で該当メソッドを呼出し、結果セットから対象データを取り出します。
	 * </dl>
	 * 
	 * @param rs 結果セットオブジェクト
	 * @return ページング検索する結果セットオブジェクトリスト
	 */
	@SuppressWarnings("unchecked")
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {

		/* startRecordが0から数える 
		   ex) ページサイズ=10,ページ番号=5の場合は、startRecord=40
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