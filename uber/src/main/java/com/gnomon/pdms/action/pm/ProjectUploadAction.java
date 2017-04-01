package com.gnomon.pdms.action.pm;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ProgramEntity;
import com.gnomon.pdms.entity.VPmTempProgramEntity;
import com.gnomon.pdms.service.ProgramPlanImportManager;
import com.gnomon.pdms.service.ProjectUploadService;

@Namespace("/pm")
public class ProjectUploadAction extends PDMSCrudActionSupport<ProgramEntity> {

	private static final long serialVersionUID = 1L;

	private File upload;
	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	private String uploadFileName;
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
	private String programId;
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	private String programVehicleId;
	public void setProgramVehicleId(String programVehicleId) {
		this.programVehicleId = programVehicleId;
	}

	private String model;
	public void setModel(String model) {
		this.model = model;
	}
	
	@Autowired
	private ProgramPlanImportManager programPlanImportManager;
	
	@Autowired
	private ProjectUploadService projectUploadService;
	
	
	/**
	 * 项目计划导入（Excel）
	 */
	public void importProject(){
		try{
			// LoginUser取得
			String loginUserId = SessionData.getLoginUserId();
			programPlanImportManager.importProgramFromExcel(programId,
					uploadFileName, new FileInputStream(upload), loginUserId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 项目计划导入（模板）
	 */
	public void importProjectTemplate(){
		try{
			// JSON解析
			Map<String, String> model = this.convertJson(this.model);
//			// 模板导入
//			this.programPlanImportManager.importProgramFromTemplate(
//					programId, programVehicleId,
//					DateUtils.strToDate(model.get("sopDate")),
//					model.get("programTempId"));
			this.projectUploadService.importProgramFromTemplate(
					programVehicleId, model);
			this.writeSuccessResult(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	/**
	 * 项目模板一览取得
	 */
	public void getProgramTemp() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();

			List<VPmTempProgramEntity> list =
					this.projectUploadService.getProgramTempList(programId);
			for(VPmTempProgramEntity entity : list) {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				// 模板ID
				dataMap.put("id", entity.getId());
				// 模板名称
				dataMap.put("name", entity.getName());
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProgramEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
