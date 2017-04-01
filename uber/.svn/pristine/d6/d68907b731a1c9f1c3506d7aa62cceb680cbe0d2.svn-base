package com.gnomon.pdms.action.pm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.Ext301CheckpointEntity;
import com.gnomon.pdms.procedure.PkgExt301DBProcedureServcie;
import com.gnomon.pdms.service.Ext301CheckpointService;

@Namespace("/pm")
public class Ext301CheckpointAction extends PDMSCrudActionSupport<Ext301CheckpointEntity> {

	private static final long serialVersionUID = 3137875303793582703L;

	@Autowired
	private Ext301CheckpointService ext301CheckpointService;
	
	@Autowired
	private PkgExt301DBProcedureServcie pkgExt301DBProcedureServcie;

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

	private Ext301CheckpointEntity entity;

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

	public void getExt301CheckpointList() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = ext301CheckpointService.getExt301CheckpointList(Long.valueOf(extProjectId));
			for(Map<String, Object> map : list){
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("id", map.get("ID"));
				data.put("title", map.get("TITLE"));
				data.put("code", map.get("CODE"));
				data.put("roleCode", map.get("ROLE_CODE"));
				if("ENG".equals(map.get("ROLE_CODE"))){
					data.put("roleName", "工程师");
				}else if("FM".equals(map.get("ROLE_CODE"))){
					data.put("roleName", "专业经理");
				}
				data.put("respFmGroup", map.get("RESP_FM_GROUP"));
				data.put("ownerName", map.get("USERNAME"));
				data.put("owner", map.get("OWNER"));
				resultList.add(data);
			}
			

			result.buildSuccessResult(resultList);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getTempStdObsList() {
		try {
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list = ext301CheckpointService.getTempStdObsList();
			for(Map<String, Object> map : list){
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("id", map.get("ID"));
				data.put("obsCode", map.get("OBS_CODE"));
				data.put("obsName", map.get("OBS_NAME"));
				
				resultList.add(data);
			}
			

			Struts2Utils.renderJson(resultList);
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
		ext301CheckpointService.save(entity);
		return null;
	}


	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void prepareModel() throws Exception {
		if(id == null){
			entity = new Ext301CheckpointEntity();
		}else{
			entity = ext301CheckpointService.get(id);
		}
		
	}

	@Override
	public Ext301CheckpointEntity getModel() {
		// TODO Auto-generated method stub
		return entity;
	}
	
	public void updateCheckpointOwner(){
		pkgExt301DBProcedureServcie.updateCheckpointOwner(owner, id);
	}

}
