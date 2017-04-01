package com.gnomon.pdms.action.pm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.entity.VPmTempGateEntity;
import com.gnomon.pdms.entity.VPmTempMainNodeEntity;
import com.gnomon.pdms.entity.VPmTempObsEntity;
import com.gnomon.pdms.entity.VPmTempProgramEntity;
import com.gnomon.pdms.service.ProjectTempService;

@SuppressWarnings("rawtypes")
@Namespace("/pm")
public class ProjectTempTreeAction extends PDMSCrudActionSupport {

	private static final long serialVersionUID = -8011468411300187421L;

	private static final String NODE_TYPE_ROOT = "root";
	private static final String NODE_TYPE_PROGRAM = "PROGRAM";//项目模板
	private static final String NODE_TYPE_ORG_ROOT = "ORG_ROOT";//组织架构模板-虚拟根节点（项目模板下）
	private static final String NODE_TYPE_ORG_NODE = "ORG_NODE";//组织结构模板-组织节点
	private static final String NODE_TYPE_MAINPLAN_ROOT = "MAINPLAN_ROOT";//主计划模板-虚拟根节点（项目模板下）
	private static final String NODE_TYPE_DEPTPLAN_ROOT = "DEPTPLAN_ROOT";//二级计划-虚拟根节点（项目模板下）
//	private static final String NODE_TYPE_DEPTPLAN_DEPT_ROOT = "DEPTPLAN_DEPT_ROOT";//二级计划-专业领域节点（二级计划下）
	private static final String NODE_TYPE_MAINNODE_ROOT = "MAINNODE_ROOT";//主节点-虚拟根节点(主计划模板下）
	private static final String NODE_TYPE_SWIMLANE_NODE = "SWIMLANE_NODE";//泳道节点（主计划和二级计划泳道）
	private static final String NODE_TYPE_DEPT_NODE = "DEPT_NODE";//二级计划下的专业领域节点
	private static final String NODE_TYPE_GATE_ROOT = "GATE_ROOT";//质量阀-虚拟根节点(主计划模板下）
	private static final String NODE_TYPE_SWIMLANE_ROOT = "SWIMLANE_ROOT";//泳道-虚拟根节点(主计划模板下）
	private static final String NODE_TYPE_SWIMLANE = "SWIMLANE";//泳道-虚拟根节点(二级计划模板下）
	private static final String NODE_TYPE_MAINDEPT_ROOT = "MAINDEPT_ROOT";//专业领域-虚拟根节点(主计划模板下）
	private static final String NODE_TYPE_MAJOR_NODE = "MAJOR_NODE";//项目管理专业组(二级计划模板下）

	private String node;//节点ID
	private String nodeType;//节点类型
	private String programTempId;//项目模板ID
	private Integer grade;//计划级别
	private String programTypeId;
	private String programObsId;

	@Autowired
	private ProjectTempService projectTempService;

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getProgramTempId() {
		return programTempId;
	}

	public void setProgramTempId(String programTempId) {
		this.programTempId = programTempId;
	}

	public String getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}

	public String getProgramObsId() {
		return programObsId;
	}

	public void setProgramObsId(String programObsId) {
		this.programObsId = programObsId;
	}

	public void getProjectTempTree() {
		try {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			String parentNodeId = node;
			
			if (NODE_TYPE_ROOT.equals(parentNodeId)) {
				//根节点下级节点（项目模板)
				data = getProgramTempData(programTypeId);
			} else if (NODE_TYPE_PROGRAM.equals(nodeType)) {
				//项目模板下级固定节点
				programTempId = parentNodeId;
				data = getProgramTempSubRootData(programTempId);
			} else if (NODE_TYPE_ORG_ROOT.equals(nodeType)) {
				//项目组织根节点
				data = getOrgTempData(programTempId,null);
			}else if (NODE_TYPE_ORG_NODE.equals(nodeType)) {
				//项目组织根节点下级节点数据
				data = getOrgTempData(programTempId,parentNodeId);
			}else if (NODE_TYPE_MAINPLAN_ROOT.equals(nodeType)) {
				//主计划模板下级的固定节点
				data = getMainPlanSubRootData(programTempId);
			}else if (NODE_TYPE_MAINDEPT_ROOT.equals(nodeType)) {
				//主计划模板下专业领域下节点
				data = getMainPlanMainDeptData(programTempId);
			}else if (NODE_TYPE_DEPTPLAN_ROOT.equals(nodeType)) {
				//二级计划模板下级的泳道（专业领域）节点
				//专业领域节点
				List<VPmTempObsEntity> deptRespListOfMainPlan = projectTempService.getDeptRespListOfMainPlan(programTempId);
				for (VPmTempObsEntity vPmTempObsEntity : deptRespListOfMainPlan) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("id", vPmTempObsEntity.getId()+"_DEPTPLAN");
					dataMap.put("text", vPmTempObsEntity.getObsName());
					dataMap.put("iconCls", "x-fa fa-group");
					dataMap.put("expanded", false);
					List<VPmTempObsEntity> swimlaneList = projectTempService.getSwimlaneListOfDeptPlan(programTempId,vPmTempObsEntity.getId());
					boolean isLeaf = (swimlaneList.size()>0)?false:true;
					dataMap.put("leaf", isLeaf);
					dataMap.put("type", NODE_TYPE_SWIMLANE_NODE);
					dataMap.put("programTempId", programTempId);
					dataMap.put("programObsId", vPmTempObsEntity.getId());
					data.add(dataMap);
				}
			}else if (NODE_TYPE_SWIMLANE_NODE.equals(nodeType)) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap = new HashMap<String, Object>();
					dataMap.put("id", programObsId+"_SWIMLANE");
					dataMap.put("text", "泳道");
					dataMap.put("iconCls", "x-fa fa-group");
					dataMap.put("expanded", false);
					dataMap.put("leaf", true);
					dataMap.put("type", NODE_TYPE_SWIMLANE);
					dataMap.put("programTempId", programTempId);
					dataMap.put("programObsId", programObsId);
					data.add(dataMap);

					dataMap = new HashMap<String, Object>();
					dataMap.put("id", programObsId+"_MAJOR");
					dataMap.put("text", "专业组");
					dataMap.put("iconCls", "x-fa fa-group");
					dataMap.put("expanded", false);
					dataMap.put("leaf", false);
					dataMap.put("type", NODE_TYPE_MAJOR_NODE);
					dataMap.put("programTempId", programTempId);
					dataMap.put("programObsId", programObsId);
					data.add(dataMap);
				}else if (NODE_TYPE_MAJOR_NODE.equals(nodeType)) {
					List<VPmTempObsEntity> swimlaneList = projectTempService.getSwimlaneListOfDeptPlan(programTempId,programObsId);
					for (VPmTempObsEntity vPmTempObsEntity : swimlaneList) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("id", vPmTempObsEntity.getId()+"_DEPTPLAN");
						dataMap.put("text", vPmTempObsEntity.getObsName());
						dataMap.put("iconCls", "x-fa fa-group");
						dataMap.put("expanded", false);
						dataMap.put("leaf", true);
						dataMap.put("type", NODE_TYPE_SWIMLANE_NODE);
						dataMap.put("programTempId", programTempId);
						dataMap.put("programObsId", vPmTempObsEntity.getId());
						data.add(dataMap);
					}
				}

			writeSuccessResult(data);
		} catch (Exception ex) {
			ex.printStackTrace();
			writeErrorResult(ex);
		}
	}

//	private List<Map<String, Object>> getMainNodeTempData(String programTempId) {
//		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
//		List<VPmTempMainNodeEntity> tempMainNodeList = projectTempService.getTempMainNodeList(programTempId);
//		for (VPmTempMainNodeEntity vPmTempMainNodeEntity : tempMainNodeList) {
//			Map<String, Object> dataMap = new HashMap<String, Object>();
//			dataMap.put("id", vPmTempMainNodeEntity.getId());
//			dataMap.put("text", vPmTempMainNodeEntity.getTaskName());
//			dataMap.put("iconCls", "x-fa fa-group");
//			dataMap.put("expanded", false);
//			dataMap.put("leaf", true);
//			String type= "";
//			if(vPmTempMainNodeEntity.getTaskTypeCode().equals(PDMSConstants.TASK_TYPE_MAIN_NODE)){
//				type =  NODE_TYPE_MAINNODE_NODE;
//			}else{
//				type = NODE_TYPE_MAINNODE_SOPNODE;
//			}
//			dataMap.put("type", type);
//			dataMap.put("programTempId", programTempId);
//			data.add(dataMap);
//		}
//		return data;
//	}

//	private List<Map<String, Object>> getGateTempData(String tempProgramId) {
//		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
//		
//		List<VPmTempGateEntity> tempGate = projectTempService.getTempGate(tempProgramId);
//		for (VPmTempGateEntity vPmTempGateEntity : tempGate) {
//			Map<String, Object> dataMap = new HashMap<String, Object>();
//			dataMap.put("id", vPmTempGateEntity.getId());
//			dataMap.put("text", vPmTempGateEntity.getTaskName());
//			dataMap.put("iconCls", "x-fa fa-group");
//			dataMap.put("expanded", false);
//			dataMap.put("leaf", true);
//			dataMap.put("type", NODE_TYPE_GATE_NODE);
//			dataMap.put("programTempId", tempProgramId);
//			data.add(dataMap);
//		}
//		
//		return data;
//	}

	private List<Map<String, Object>> getOrgTempData(String programTempId,String parentNodeId) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<VPmTempObsEntity> tempProgramObsList = projectTempService.getTempProgramObsList(programTempId,parentNodeId);
		for (VPmTempObsEntity vPmTempObsEntity : tempProgramObsList) {
			List<VPmTempObsEntity> children = projectTempService.getTempProgramObsList(programTempId,vPmTempObsEntity.getParentId());
			Map<String, Object> dataMap = new HashMap<String, Object>();
			boolean isLeaf = false;
			if(children.size() > 0){
				isLeaf = false;
			}else{
				isLeaf = true;
			}
			dataMap.put("leaf", isLeaf);
			dataMap.put("id", vPmTempObsEntity.getId());
			dataMap.put("text", vPmTempObsEntity.getObsName());
			dataMap.put("iconCls", "x-fa fa-group");
			dataMap.put("expanded", false);
			dataMap.put("type", NODE_TYPE_ORG_NODE);
			dataMap.put("programTempId", programTempId);

			data.add(dataMap);
		}
		return data;
	}

	/**
	 * 查询主计划下级节点
	 * @param programTempId
	 * @return
	 */
	private List<Map<String, Object>> getMainPlanSubRootData(String programTempId) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		dataMap = new HashMap<String, Object>();
		dataMap.put("id", programTempId+"_SWIMLANE_ROOT");
		dataMap.put("text", "泳道");
		dataMap.put("iconCls", "x-fa fa-group");
		dataMap.put("expanded", false);
		dataMap.put("leaf", true);
		dataMap.put("type", NODE_TYPE_SWIMLANE_ROOT);
		dataMap.put("programTempId", programTempId);
		data.add(dataMap);

		dataMap = new HashMap<String, Object>();
		dataMap.put("id", programTempId+"_MAINNODE_ROOT");
		dataMap.put("text", "主节点");
		dataMap.put("iconCls", "x-fa fa-group");
		dataMap.put("expanded", false);
		dataMap.put("leaf", true);
		dataMap.put("type", NODE_TYPE_MAINNODE_ROOT);
		dataMap.put("programTempId", programTempId);
		data.add(dataMap);
		
		dataMap = new HashMap<String, Object>();
		dataMap.put("id", programTempId+"_GATE_ROOT");
		dataMap.put("text", "质量阀");
		dataMap.put("iconCls", "x-fa fa-group");
		dataMap.put("expanded", false);
		dataMap.put("leaf", true);
		dataMap.put("type", NODE_TYPE_GATE_ROOT);
		dataMap.put("programTempId", programTempId);
		data.add(dataMap);

		dataMap = new HashMap<String, Object>();
		dataMap.put("id", programTempId+"_MAINDEPT_ROOT");
		dataMap.put("text", "专业领域");
		dataMap.put("iconCls", "x-fa fa-group");
		dataMap.put("expanded", false);
		dataMap.put("leaf", false);
		dataMap.put("type", NODE_TYPE_MAINDEPT_ROOT);
		dataMap.put("programTempId", programTempId);
		data.add(dataMap);

		return data;
	}

	private List<Map<String, Object>> getMainPlanMainDeptData(String programTempId) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<VPmTempObsEntity> deptRespListOfMainPlan = projectTempService.getDeptRespListOfMainPlan(programTempId);
		for (VPmTempObsEntity vPmTempObsEntity : deptRespListOfMainPlan) {
			dataMap = new HashMap<String, Object>();
			dataMap.put("id", vPmTempObsEntity.getId()+"_MAMINPLAN");
			dataMap.put("text", vPmTempObsEntity.getObsName());
			dataMap.put("iconCls", "x-fa fa-group");
			dataMap.put("expanded", false);
			dataMap.put("leaf", true);
			dataMap.put("type", NODE_TYPE_SWIMLANE_NODE);
			dataMap.put("programTempId", programTempId);
			dataMap.put("programObsId", vPmTempObsEntity.getId());
			data.add(dataMap);
		}
		return data;
	}

	private List<Map<String, Object>> getProgramTempSubRootData(String programTempId) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id", programTempId+"_ORG_ROOT");
		dataMap.put("text", "组织架构模板");
		dataMap.put("iconCls", "x-fa fa-group");
		dataMap.put("expanded", false);
		dataMap.put("leaf", false);
		dataMap.put("type", NODE_TYPE_ORG_ROOT);
		dataMap.put("programTempId", programTempId);
		data.add(dataMap);
		
		dataMap = new HashMap<String, Object>();
		dataMap.put("id", programTempId+"_MAINPLAN_ROOT");
		dataMap.put("text", "主计划模板");
		dataMap.put("iconCls", "x-fa fa-group");
		dataMap.put("expanded", false);
		dataMap.put("leaf", false);
		dataMap.put("type", NODE_TYPE_MAINPLAN_ROOT);
		dataMap.put("programTempId", programTempId);
		data.add(dataMap);
		
		dataMap = new HashMap<String, Object>();
		dataMap.put("id", programTempId+"_DEPTPLAN_ROOT");
		dataMap.put("text", "二级计划模板");
		dataMap.put("iconCls", "x-fa fa-group");
		dataMap.put("expanded", false);
		dataMap.put("leaf", false);
		dataMap.put("type", NODE_TYPE_DEPTPLAN_ROOT);
		dataMap.put("programTempId", programTempId);
		data.add(dataMap);
		
		return data;
	}

	private List<Map<String, Object>> getProgramTempData(String programTypeId) {
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		List<VPmTempProgramEntity> tempProgramList = projectTempService.getTempProgramListByProgramTypeId(programTypeId);
		for (VPmTempProgramEntity vPmTempProgramEntity : tempProgramList) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			String id = vPmTempProgramEntity.getId();
			String name = vPmTempProgramEntity.getName();
			dataMap.put("id", id);
			dataMap.put("text", name);
			dataMap.put("iconCls", "x-fa fa-group");
			dataMap.put("expanded", false);
			dataMap.put("leaf", false);
			dataMap.put("type", NODE_TYPE_PROGRAM);
			data.add(dataMap);
		}
		return data;
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

}
