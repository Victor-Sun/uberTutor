package com.gnomon.pdms.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.mpxj.Duration;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ProjectHeader;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;
import net.sf.mpxj.mspdi.MSPDIReader;
import net.sf.mpxj.reader.ProjectReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.CommonUtils;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.pdms.dao.Ext203ListDAO;
import com.gnomon.pdms.entity.Ext203ItemEntity;

@Service
@Transactional
public class Ext203ItemService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Ext203ListDAO ext203ListDAO;
	
	private Map<Task, List<String>> taskErrorList = new HashMap<Task, List<String>>();
	
	public List<Map<String, Object>> getExt203DependenceList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT203_PRE_TASK ");
        return list;
    }
	
	public List<Map<String, Object>> getExt203AssignmentList(Long projectId) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT203_WBS where EXT_PROJECT_ID=?",projectId);
        return list;
    }
	
	public List<Map<String, Object>> getExt203ResourcesList() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM SYS_USER ");
        return list;
    }
	
	public List<Map<String, Object>> getExt203TaskList(Long projectId,Long parentId) {
		List<Map<String, Object>> list = null;
		if(parentId == null){
			list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT203_WBS where EXT_PROJECT_ID=? and (PARENT_ID is null or PARENT_ID = 0) ",projectId);
		}else{
			list = jdbcTemplate.queryForList("SELECT * FROM PM_EXT203_WBS where EXT_PROJECT_ID=? and PARENT_ID=?",projectId,parentId);
		}
		
        return list;
    }

	public void addTaskList(List<Ext203ItemEntity> list){
		for(Ext203ItemEntity ext203ItemEntity:list){
			ext203ListDAO.save(ext203ItemEntity);
		}
	}
	
	public void save(Ext203ItemEntity entity){
		ext203ListDAO.save(entity);
	}
	
	public void updateTaskList(List<Ext203ItemEntity> list){
		for(Ext203ItemEntity ext203ItemEntity:list){
			ext203ListDAO.save(ext203ItemEntity);
		}
	}
	
	public Ext203ItemEntity get(Long id){
		return ext203ListDAO.get(id);
	}

	public void importMSProject(File projectFile,Long extProjectId,String userId)
			throws Exception {
		ProjectReader reader = null;
		ProjectFile file = null;
		try {
			reader = new MPPReader();// MPP
			FileInputStream inputstream = new FileInputStream(projectFile);
			file = reader.read(inputstream);
		} catch (Exception e) {
			try {
				reader = new MSPDIReader();// XML
				FileInputStream inputstream = new FileInputStream(projectFile);
				file = reader.read(inputstream);
			} catch (Exception e1) {
				reader = new MPXReader();// MPX
				FileInputStream inputstream = new FileInputStream(projectFile);
				file = reader.read(inputstream);
			}
		}

		// 校验Project任务
//		validateProject(file,programEntity);
		

		ProjectHeader header = file.getProjectHeader();
//		Date programPlanSDate = header.getStartDate();// 计划开始日期
		int gap = 0;


//		gap = calculateGap(file,programEntity);
		

//		GTProgram programEntity = new GTProgram();
		// 文件中的字段
//		programEntity.setCode(code);// 项目编码
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		programEntity.setShortName(programName.trim());// 项目名称
//		programEntity.setDescription(header.getComments());// 备注
//		programEntity.setCustomerId(customerId);
//		programEntity.setPartTypeId(partTypeId);
//		programEntity.setProductionSiteId(productionSiteId);
//		programEntity.setSopDate(sopDate);

		// 如果新开始日期不为空，计划开始日期=新开始日期
//		if (programEntity.getPlanStartDate() == null) {
//			programEntity.setPlanStartDate(header.getStartDate());
//		}
		// MPX无法获得FinishedDate，需要根据Duration计算
//		if (header.getFinishDate() == null) {
//			Date finishDate = getDate(header.getStartDate(), (int)header
//					.getDuration().getDuration());//TODO float -> int 会出现精度丢失问题
//			programEntity.setPlanEndDate(DateUtils.addDays(finishDate, gap));// 计划结束日期
//																				// ，按日期间隔向后推
//		} else {
//			programEntity.setPlanEndDate(DateUtils.addDays(
//					header.getFinishDate(), gap));// 计划结束日期，按日期间隔向后推
//		}
//		if (header.getCost() != null) {
//			programEntity.setBudget(header.getCost().doubleValue());// 项目预算
//		}
//		if (header.getCurrencyCode() != null) {
//			programEntity.setCurrency(header.getCurrencyCode());// 币种
//		}
//		// 设置必要的字段
//		programEntity.setOwner(programEntity.getOwner());// 负责人=当前用户
//		programEntity.setCreateBy(programEntity.getCreateBy());// 创建人=当前用户
//		programEntity.setCreateDate(new Date());// 创建日期=当前日期
//		programEntity.setType(GTProgram.TYPE_PROJECT);// 项目类型=项目
//		programEntity.setProjectState(GTProgram.PROJECT_STATE_NOTSTARTED);// 状态=未启动
//		programEntity.setCreateMode(GTProgram.CREATEMODE_PDMS);// 创建方式=PDMS
//		programEntity.setValidate(PdmsWebContants.PROGRAM_STATUS_INEFFECTIVE);// 是否有效=无效

		// 保存项目
//		saveGTProgram(programEntity);

		// 导入任务
		// Project文件的根任务节点不导入
//		AtomicInteger counter = new AtomicInteger(1);
		importProjectTask(file, null, extProjectId, userId, gap,null);
		// for(Task projectTask :file.getChildTasks()){
		// projectTask.setUniqueID(0);//设置根任务的parentId=0
		// }

		// 保存前置任务
//		savePretask(file);

//		return programEntity;
	}
	
	public void validateProject(ProjectFile file) throws Exception {
		clearValidateErrorMsg();
//		ProjectHeader header = file.getProjectHeader();
		// if(header.getProjectTitle() == null ||
		// header.getProjectTitle().equals("")){
		// errorMessage += "项目名称为空！";
		// return errorMessage;
		// }
		// if(header.getStartDate() == null){
		// errorMessage += "项目开始日期为空！";
		// return errorMessage;
		// }
		// if(header.getFinishDate() == null){
		// errorMessage += "项目完成日期为空！";
		// return errorMessage;
		// }

		for (Task task : file.getAllTasks()) {
			if (task.getOutlineLevel() == null
					|| task.getOutlineLevel().equals(0)) {
				continue;
			}
			
			//校验所有任务
			if (task.getName() == null || task.getName().equals("")) {
				addTaskValidateErrorMsg(task,"任务名称不能为空！");
			}
			if (task.getStart() == null) {
				addTaskValidateErrorMsg(task,"任务开始日期不能为空！");

			}
			if (task.getFinish() == null && task.getDuration() == null) {
				addTaskValidateErrorMsg(task,"任务完成日期不能为空！");
			}
			

		}

		if (!taskErrorList.isEmpty()) {
			throw new Exception(getTaskValidateErrorMsgString());
		}

	}
	
	private void clearValidateErrorMsg(){
		taskErrorList = new HashMap<Task, List<String>>();
	}
	private void addTaskValidateErrorMsg(Task task, String string) {
		if(!taskErrorList.containsKey(task)){
			taskErrorList.put(task, new ArrayList<String>());
		}
		List<String> errList = taskErrorList.get(task);
		errList.add(string);
	}
	
	private String getTaskValidateErrorMsgString(){
		StringBuffer sb = new StringBuffer();
		Set<Task> keySet = taskErrorList.keySet();
		for (Task task : keySet) {
			String taskName = task.getName();
			sb.append("["+taskName+"]任务导入失败：");
			List<String> errorList = taskErrorList.get(task);
			for (String error : errorList) {
				sb.append(error).append(";");
			}
			sb.append("<br>");
		}
		return sb.toString();
	}
	
	public void importProjectTask(ProjectFile file, Task parentTask,
			Long extProjectId, String createUserId, int gap,Long parentTaskId) {
		List<Task> childTasks = null;
		if (parentTask == null) {
			childTasks = file.getChildTasks();
			// 处理虚拟根节点的问题，Project导出XML时默认会有一个WBS为0的虚拟节点，需要跳过
			if (childTasks.size() >= 1) {
				for (Task rootTask : childTasks) {
					if ("0".equals(rootTask.getWBS())) {
						childTasks = rootTask.getChildTasks();
						break;
					}
				}
			}
		} else {
			childTasks = parentTask.getChildTasks();
		}
		for (int i = 0; i < childTasks.size(); i++) {
			Task childTask = childTasks.get(i);
			if (childTask.getName() == null) {
				continue;
			}
			Ext203ItemEntity task = new Ext203ItemEntity();
			task.setExtProjectId(extProjectId);
			if(parentTaskId == null){
				parentTaskId = 0L;
			}
			task.setParentId(parentTaskId);// 获得上级任务的数据库ID，使用UniqueID
//			task.setStatus("NOTSTARTED");// 状态
//			task.set(new Date()); // 创建日期
//			task.setCreateUser(createUserId); // 创建人
			task.setTaskName(childTask.getName().trim()); // 任务名称
			Duration duration = childTask.getDuration();
			task.setPlannedDuration(Double.valueOf(duration.getDuration()).longValue());// 工期
			task.setPlannedStartDate(DateUtils.addDays(childTask.getStart(), gap)); // 计划开始日期,按日期间隔向后推
			if (childTask.getFinish() == null) {
				Date finishDate = getDate(childTask.getStart(),
						(int)childTask.getDuration().getDuration());// 计划结束日期,按日期间隔向后推  //TODO float -> int 会出现精度丢失问题
				task.setPlannedFinishDate(DateUtils.addDays(finishDate, gap));
			} else {
				task.setPlannedFinishDate(DateUtils.addDays(childTask.getFinish(), gap)); // 计划结束日期,按日期间隔向后推
			}
//			String resourceId = getTaskOwnerUserid(childTask);
//			task.setResourceName(resourceId);// 资源
//			task.setOperatoruid(resourceId);// 资源,负责人
//			Long obsId = getTaskObsId(childTask);
//			task.setSwimlane1(obsId);

//			task.setSeq(counter.addAndGet(1));//counter 用于排序
			
			
			// 设置叶子节点（IS_LEAF)标志
			if (childTask.getChildTasks().size() > 0) {
				task.setIsLeaf("N");
			} else {
				task.setIsLeaf("Y");
			}
			
//			task.setUuid(CommonUtils.getUUID());
			
			// 保存任务
			this.save(task);
			// 保存任务的ID为Task的UniqueID
			Long taskId = task.getId();
//			setTaskId(childTask,taskId);
//			childTask.setUniqueID(taskId.intValue());
			
			// 导入子任务
			if (childTask.getChildTasks().size() > 0) {
				importProjectTask(file, childTask, extProjectId, createUserId, gap,taskId);
			}
		}
	}
	
	private Date getDate(Date startDate, Integer duration) {
		if (duration > 0) {
			// 结束日期=开始日期+(周期-1)
			return DateUtils.addDays(startDate, duration.intValue() - 1);
		} else {
			return startDate;
		}
	}
}
