package com.gnomon.pdms.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.dao.VIssueLastAccessDAO;
import com.gnomon.pdms.entity.VIssueLastAccessEntity;

@Service
@Transactional
public class LastAccessService {

	@Autowired
	private VIssueLastAccessDAO vIssueLastAccessDAO;
	
	public Page<VIssueLastAccessEntity> getLastAccessList(String query, String searchChoice, String accessChoice, int start, int end) throws UnsupportedEncodingException {
		Page<VIssueLastAccessEntity> lastAccessList = null ;
		Page<VIssueLastAccessEntity> page = new Page<VIssueLastAccessEntity>(end);
		page.setPageNo(start);

		String userId = "";
		// 登录用户ID取得
		userId = SessionData.getLoginUserId();
		List<String> params = new ArrayList<String>();

		String hql = "from VIssueLastAccessEntity WHERE userid = ? ";
		params.add(userId);
		if (PDMSCommon.isNotNull(query)) {
			String strQuery = new String(query.getBytes("ISO-8859-1"), "UTF-8");
			hql += "and " + searchChoice + " like ? ";
			params.add("%" + strQuery + "%");
		}
		hql += "order by " + accessChoice + " DESC";
		hql += " rownum";
		lastAccessList =
				this.vIssueLastAccessDAO.findPage(page, hql, params.toArray());
        return lastAccessList;
    }

}