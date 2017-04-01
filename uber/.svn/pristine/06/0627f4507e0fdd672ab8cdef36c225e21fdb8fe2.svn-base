package com.gnomon.pdms.action.forum;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.PMPostEntity;
import com.gnomon.pdms.procedure.PkgPmPostDBProcedureServcie;
import com.gnomon.pdms.service.PmPostService;
import com.gnomon.pdms.service.ProjectOpenIssueService;
import com.gnomon.pdms.service.SysNoticeService;

@Namespace("/pm")
public class ForumAction extends PDMSCrudActionSupport<PMPostEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	private PMPostEntity pmPostEntity;

	@Autowired
	private PmPostService pmPostService;
	
	@Autowired
	private ProjectOpenIssueService projectOpenIssueService;
	
	@Autowired
	private PkgPmPostDBProcedureServcie pkgPmPostDBProcedureServcie;
	
	@Autowired
	private SysNoticeService sysNoticeService;
	
	@Override
	public PMPostEntity getModel() {
		return pmPostEntity;
	}
	
	private String method;

	private Long id;
	
	private String sourceId;
	
	private String source;
	
	private String title;
	
	private String content;
	
	private Long parentPostId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getParentPostId() {
		return parentPostId;
	}

	public void setParentPostId(Long parentPostId) {
		this.parentPostId = parentPostId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		Method m = this.getClass().getDeclaredMethod(method);
		m.invoke(this);
		return null;
	}

	@Override
	public String delete() throws Exception {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}
	
	public void getForumList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>>  list = pmPostService.getForumList(sourceId, source);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", map.get("ID"));
				dataMap.put("issueId", map.get("ISSUE_ID"));
				dataMap.put("title", map.get("TITLE"));
				dataMap.put("content", map.get("CONTENT"));	
				dataMap.put("parentId", map.get("PARENT_ID"));
				dataMap.put("postLevel", map.get("POST_LEVEL"));
				dataMap.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				dataMap.put("userName", map.get("USERNAME"));
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, data.size());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void newPost() {
		try{
			String userId = SessionData.getLoginUserId();
			pkgPmPostDBProcedureServcie.newPost(userId , source, sourceId, content, content);
			// 发表通知
			this.sysNoticeService.postNotice(userId, source, sourceId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void replyPost() {
		try{
			String userId = SessionData.getLoginUserId();
			pkgPmPostDBProcedureServcie.replyPost(userId, parentPostId, content);
			// 回复通知
			this.sysNoticeService.replyPostNotice(userId, parentPostId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
}
