package com.gnomon.pdms.action.pm;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.utils.ObjectConverter;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.PMWorkGroupMemberEntity;
import com.gnomon.pdms.service.PmWorkGroupMemberService;

@Namespace("/pm")
public class PmWorkGroupMemberAction extends PDMSCrudActionSupport<PMWorkGroupMemberEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private PmWorkGroupMemberService pmWorkGroupMemberService;
	

	private Long workGroupId;
	private String extProjectId;
	
	private String programVehicleId;
	
	private String obsName;
	
	private String code;
	
	private String action;
	
	private Long stageId;
	
	private String obsId;
	
	private File upload;//导入文件
	
	private Long taskId;
	
	private String plannedFinishDate;
	
	private String actualFinishDate;
	
	private String model;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(Long workGroupId) {
		this.workGroupId = workGroupId;
	}

	public String getAction() {
		return action;
	}

	public String getObsName() {
		return obsName;
	}

	public void setObsName(String obsName) {
		this.obsName = obsName;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private Long id;
	
	private String owner;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setModel(String model) {
		this.model = model;
	}

	private Long revisionId;
	
	private String isCompleted;
	private String remark;
	
	public File getUpload() {
		return upload;
	}

	public Long getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(Long revisionId) {
		this.revisionId = revisionId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public String getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public String getObsId() {
		return obsId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getProgramVehicleId() {
		return programVehicleId;
	}

	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	private String uploadFileName;

	private PMWorkGroupMemberEntity entity;

	public void setExtProjectId(String extProjectId) {
		this.extProjectId = extProjectId;
	}
	
	public String getPlannedFinishDate() {
		return plannedFinishDate;
	}

	public void setPlannedFinishDate(String plannedFinishDate) {
		this.plannedFinishDate = plannedFinishDate;
	}

	public String getActualFinishDate() {
		return actualFinishDate;
	}

	public void setActualFinishDate(String actualFinishDate) {
		this.actualFinishDate = actualFinishDate;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public String getExtProjectId() {
		return extProjectId;
	}

	public void getWorkGroupMemberList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> page = pmWorkGroupMemberService.getWorkGroupMemberList(workGroupId,this.getPage(),this.getLimit());
			for(Map<String, Object> map : page.getItems()){
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("id", map.get("ID"));
				data.put("username", map.get("USERNAME"));
				data.put("createDate", ObjectConverter.convert2String(map.get("CREATE_DATE")));
				resultList.add(data);
			}
			

			result.buildSuccessResultForList(resultList,page.getItemCount());
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		try {
			pmWorkGroupMemberService.save(entity);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String delete() throws Exception {
		try {
			pmWorkGroupMemberService.delete(id);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			this.writeErrorResult(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}


	@Override
	protected void prepareModel() throws Exception {
		if(id == null){
			entity = new PMWorkGroupMemberEntity();
			entity.setIsActive("Y");
			entity.setCreateDate(new Date());
		}else{
			entity = pmWorkGroupMemberService.get(id);
		}
		
	}

	@Override
	public PMWorkGroupMemberEntity getModel() {
		// TODO Auto-generated method stub
		return entity;
	}
	
	

}