package com.gnomon.pdms.action.pm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.PMTempTaskVMEntity;
import com.gnomon.pdms.entity.VPmTempDeliverableEntity;
import com.gnomon.pdms.entity.VPmTempGateEntity;
import com.gnomon.pdms.entity.VPmTempMainNodeEntity;
import com.gnomon.pdms.entity.VPmTempObsEntity;
import com.gnomon.pdms.entity.VPmTempProgramEntity;
import com.gnomon.pdms.service.ProgramPlanTempImportManager;
import com.gnomon.pdms.service.ProjectTempService;

@SuppressWarnings("rawtypes")
@Namespace("/pm")
public class ProjectTempAction extends PDMSCrudActionSupport{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProgramPlanTempImportManager programPlanTempImportManager;

	@Autowired
	private ProjectTempService projectTempService;

	private String programName;//导入模板名称
	private String programCode;//导入模板编号
	private File upload;//导入文件
	private String programTypeId;//导入类型
	
	private String templateName;//添加模板名称
	private String templateNumber;//添加模板编号
	private String templateDefault;//是否默认(必须有一个项目为默认)
	
	private String id;//节点id
	private String obsId;//组织交付物id
	private String taskId;//质量阀交付物id
	private String programId;//模板导入Id 组织节点id
	private Integer grade;//计划级别
	
	private String uploadFileName;

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setObsId(String obsId) {
		this.obsId = obsId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}
	public void setTemplateDefault(String templateDefault) {
		this.templateDefault = templateDefault;
	}
	
	private String programTempId;
	public void setProgramTempId(String programTempId) {
		this.programTempId = programTempId;
	}
	
	private String programTempName;
	public void setProgramTempName(String programTempName) {
		this.programTempName = programTempName;
	}

	/*
	 * 导入
	 */
	public void importProjectTemplate() {
		try{
			// LoginUser取得
			String loginUserId = SessionData.getLoginUserId();
			programPlanTempImportManager.createProgramFromExcel(programTypeId,
					programName, programCode, uploadFileName, new FileInputStream(upload), loginUserId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}

	/*
	 * tree右键导入模板
	 */
	public void importProjectTemplateInfo() {
		try{
			// LoginUser取得
			String loginUserId = SessionData.getLoginUserId();
			programPlanTempImportManager.importProgramFromExcel(programId,
					 uploadFileName, new FileInputStream(upload), loginUserId);
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			if(upload != null){
				upload.delete();
			}
			this.writeErrorResult(ex.getMessage());
		}
	}

	/*
	 * 删除模板信息
	 */
	public void DeleteTemplate() {
		try {
			this.programPlanTempImportManager.deleteProgramTemp(programId);
			this.writeSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			this.writeErrorResult(e.getMessage());
		}
	}

	/*
	 * 模板信息取得
	 */
	 public void getProjectTemplateForm() {
		try {
			JsonResult result = new JsonResult();
			VPmTempProgramEntity entity = this.projectTempService
					.getProjectTemplateForm(id);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			//编号
			dataMap.put("code", entity.getCode());
			//名称
			dataMap.put("name", entity.getName());
			//类型
			dataMap.put("programTypeName", entity.getProgramTypeName());
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	 }

	/*
	 * 组织信息取得
	 */
	public void getProjectTemplateOrgNode() {
		try {
			JsonResult result = new JsonResult();
			VPmTempObsEntity entity = this.projectTempService
					.getProjectTemplateOrgNode(id);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			//编号
			dataMap.put("obsCode", entity.getObsCode());
			//名称
			dataMap.put("obsName", entity.getObsName());
			//类型
			dataMap.put("obsTypeName", entity.getObsTypeName());
			result.buildSuccessResult(dataMap);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 组织交付物信息取得
	 */
	public void getTemplateOrgNodeDeliverable() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<PMTempTaskVMEntity> list =
					this.projectTempService.getTemplateOrgNodeDeliverable(obsId);
			for(PMTempTaskVMEntity entity : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//节点名称
				dataMap.put("taskName", entity.getTaskName());
				//节点类型
				dataMap.put("taskTypeName", entity.getTaskTypeName());
				//完成周期(天)
				dataMap.put("finishDaysToSop", entity.getFinishDaysToSop());
				//是否显示在时程表
				dataMap.put("isShow", entity.getIsShow());
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 节点信息取得
	 */
	public void getProjectTemplateMainNode() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<VPmTempMainNodeEntity> list = this.projectTempService
					.getProjectTemplateMainNode(programId);
			for(VPmTempMainNodeEntity entity : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				//名称
				dataMap.put("taskName", entity.getTaskName());
				//完成周期(天)
				dataMap.put("finishDaysToSop", entity.getFinishDaysToSop());
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 阀门信息取得
	 */
	public void getProjectTemplateGateNode() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<VPmTempGateEntity> list = this.projectTempService
					.getProjectTemplateGateNode(programId);
			for(VPmTempGateEntity entity : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				//ID
				dataMap.put("id", entity.getId());
				//名称
				dataMap.put("taskName", entity.getTaskName());
				//完成周期(天)
				dataMap.put("finishDaysToSop", entity.getFinishDaysToSop());
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 阀门交付物信息取得
	 */
	public void getTemplateGateNodeDeliverable() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<VPmTempDeliverableEntity> list =
					this.projectTempService.getTemplateGateNodeDeliverable(taskId);
			for(VPmTempDeliverableEntity entity : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//阀门名称
				dataMap.put("taskName", entity.getTaskName());
				//交付物编号
				dataMap.put("code", entity.getCode());
				//交付物名称
				dataMap.put("name", entity.getName());
				//检查项目
				dataMap.put("checkitemName", entity.getCheckitemName());
				//单项通过要求及验收办法
				dataMap.put("checkRequirement", entity.getCheckRequirement());
				//负责主体
				dataMap.put("obsName", entity.getObsName());
				//是否关键交付物
				dataMap.put("isKey", entity.getIsKey());
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 专业领域信息取得
	 */
	public void getProjectTemplateMainDept() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<PMTempTaskVMEntity> list = this.projectTempService
					.getProjectTemplateMainDept(programId);
			for(PMTempTaskVMEntity entity : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				//ID
				dataMap.put("id", entity.getId());
				//名称
				dataMap.put("taskName", entity.getTaskName());
				//节点类型
				dataMap.put("taskTypeName", entity.getTaskTypeName());
				//是否显示时程表
				dataMap.put("isShow", entity.getIsShow());
				//完成周期(天)
				dataMap.put("finishDaysToSop", entity.getFinishDaysToSop());
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 专业领域交付物信息取得
	 */
	public void getTemplateMainDeptDeliverable() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list =
					this.projectTempService.getTemplateMainDeptDeliverable(taskId);
			for(Map<String, Object> map : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//阀门名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				//交付物编号
				dataMap.put("code", map.get("CODE"));
				//交付物名称
				dataMap.put("name", map.get("NAME"));
				//检查项目
				dataMap.put("checkitemName", map.get("CHECKITEM_NAME"));
				//单项通过要求及验收办法
				dataMap.put("checkRequirement", map.get("CHECK_REQUIREMENT"));
				//负责主体
				dataMap.put("obsName", map.get("OBS_NAME"));
				//是否关键交付物
				dataMap.put("isKey", map.get("IS_KEY"));
				
				data.add(dataMap);
			}
			// 结果返回
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 泳道信息取得
	 */
	public void getProjectTemplateSwimLane() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list = this.projectTempService
					.getProjectTemplateSwimLane(programId, grade);
			for(Map<String, Object> map : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				//ID
				dataMap.put("id", map.get("FUNCTION_ID"));
				//泳道名称
				dataMap.put("displayName", map.get("DISPLAY_NAME"));
				//关联组织
				dataMap.put("childObsName", map.get("CHILD_OBS_NAME"));
				//显示顺序
				dataMap.put("seqNo", map.get("SEQ_NO"));
				data.add(dataMap);
			}
			result.buildSuccessResultForList(data, 1);
			Struts2Utils.renderJson(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 泳道交付物信息取得
	 */
	public void getTemplateSwimLaneDeliverable() {
		try {
			JsonResult result = new JsonResult();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list =
					this.projectTempService.getTemplateSwimLaneDeliverable(taskId);
			for(Map<String, Object> map : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				//节点名称
				dataMap.put("taskName", map.get("TASK_NAME"));
				//开始日期
				dataMap.put("plannedStartDate", map.get("START_DAYS_TO_SOP"));
				//结束日期
				dataMap.put("plannedFinishDate", map.get("FINISH_DAYS_TO_SOP"));
				
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
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
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
	
	public void downloadTemplate() {
		try {
			InputStream is = this.getClass().getResourceAsStream("/template/programTemplate.xls");
			HttpServletResponse response = Struts2Utils.getResponse();
			ServletOutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			// filename = "attachment; filename=" + filename + ".xls";
			response.setHeader("Content-disposition","attachment; filename="+java.net.URLEncoder.encode("项目模板.xls", "UTF-8"));// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			try {
				int l = -1;
				byte[] tmp = new byte[1024];
				while ((l = is.read(tmp)) != -1) {
					os.write(tmp, 0, l);
					// 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
				}
				os.flush();
				os.close();
			} finally {
				// 关闭低层流。
				is.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出模板Excel
	 */
	public void exportTemplate2Excel() {
		try {
			HttpServletResponse response = Struts2Utils.getResponse();
			ServletOutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			String filename = programTempName + ".xls";
			response.setHeader("Content-disposition","attachment; filename=" + filename);// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			this.programPlanTempImportManager.export2Excel(programTempId, os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
