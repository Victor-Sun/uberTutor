package com.gnomon.pdms.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.dao.ProgramDAO;
import com.gnomon.pdms.dao.VImsParticipationDAO;
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.entity.VImsParticipationEntity;

@Service
@Transactional
public class MyParticipationService {
	
	@Autowired
	private VImsParticipationDAO vImsParticipationDAO;

	@Autowired
	private ProgramDAO programDAO;
	
	public Page<VImsParticipationEntity> getMyParticipationList(String query, String searchChoice, int start, int end, String notifyFlg) 
			throws UnsupportedEncodingException {
		Page<VImsParticipationEntity> myparticipationList = null ;
		Page<VImsParticipationEntity> page = new Page<VImsParticipationEntity>(end);
		page.setPageNo(start);

		String userId = "";
		//登录用户ID取得
		userId = SessionData.getLoginUserId();

		String hql = "from VImsParticipationEntity WHERE memberUserid = ? ";
		//是否被知会
		if ("1".equals(notifyFlg)) {
			hql += "and roleCode = 'INFO_USER'";
		} else {
			hql += "and roleCode = 'SUBMIT_USER'";
		}
		if (PDMSCommon.isNotNull(query)) {
			String strQuery = new String(query.getBytes("ISO-8859-1"), "UTF-8");
			hql += "and " +  searchChoice + " like ?";
			hql += " order by rownum";
			myparticipationList =
					this.vImsParticipationDAO.findPage(page, hql, userId, "%" + strQuery + "%");
		} else {
			//String hql = "from VImsParticipationEntity WHERE memberUserid = ? ";
			hql += " order by rownum";
			myparticipationList =
					this.vImsParticipationDAO.findPage(page, hql, userId);
		}
        return myparticipationList;
    }
	//所属项目
	public ProgramEntity getProgram(String keyId) {

		ProgramEntity getProgram =
				this.programDAO.findUniqueBy("id", keyId);
		
        return getProgram;
    }

}