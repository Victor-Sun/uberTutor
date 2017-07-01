package com.gnomon.common.constant;

public interface PDMSConstants {
	
	// 系统项目角色ID
	public static final String SYS_PROGRAM_PROFILE_DIRECTOR = "7692EC0BD4A344DDB763955858E8174A"; // 项目总监
	public static final String SYS_PROGRAM_PROFILE_PM = "AF361385A7F144A696CC5C98F7D1419F"; // 项目经理
	public static final String SYS_PROGRAM_PROFILE_FM = "FFD578E366BA46AEBA0DA21AA9CD6DAA"; // 专业经理
	public static final String SYS_PROGRAM_PROFILE_QM = "E57DB49C98DD41E9ADD79E0AD77B89AD"; // 质量经理
	
	//项目组织类型
	public static final String OBS_TYPE_RESP_DEPT = "OBS_TYPE_RESP_DEPT";//专业领域
	
	//任务类型
	public static final String TASK_TYPE_MAIN_NODE = "TASK_TYPE_MAIN_NODE";//主节点
	public static final String TASK_TYPE_SOP_NODE = "TASK_TYPE_SOP_NODE";//SOP节点
	public static final String TASK_TYPE_GATE = "TASK_TYPE_GATE";//质量阀
	public static final String TASK_TYPE_NODE = "TASK_TYPE_NODE";//节点
	public static final String TASK_TYPE_ACTIVITY = "TASK_TYPE_ACTIVITY";//虚拟活动
	
	//任务状态
	public static final String TASK_STATUS_NOT_START = "TASK_STATUS_NOT_START";
	public static final String TASK_STATUS_IN_PROCESS = "TASK_STATUS_IN_PROCESS";
	public static final String TASK_STATUS_CLOSED = "TASK_STATUS_CLOSED";
	public static final String TASK_STATUS_CANCELLED = "TASK_STATUS_CANCELLED";
	
	// 任务进展状态
	public static final String TASK_PROGRESS_STATUS_GREY = "TASK_PROGRESS_STATUS_GREY";// 未启动-灰
	public static final String TASK_PROGRESS_STATUS_G = "TASK_PROGRESS_STATUS_G";// 无风险-绿
	public static final String TASK_PROGRESS_STATUS_GY = "TASK_PROGRESS_STATUS_GY";// 小风险-绿黄
	public static final String TASK_PROGRESS_STATUS_Y = "TASK_PROGRESS_STATUS_Y";// 中风险-黄
	public static final String TASK_PROGRESS_STATUS_R = "TASK_PROGRESS_STATUS_R";// 大风险-红
	public static final String TASK_PROGRESS_STATUS_B = "TASK_PROGRESS_STATUS_B";// 无法判断-蓝
	
	//前置任务类型
	public static final String PRE_TASK_TYPE_FS = "PRETASK_TYPE_FS";//Finish Start 
	public static final String PRE_TASK_TYPE_FF = "PRETASK_TYPE_FF";//Finish Finish
	public static final String PRE_TASK_TYPE_SS = "PRETASK_TYPE_SS";//Start Start
	public static final String PRE_TASK_TYPE_SF = "PRETASK_TYPE_SF";//Start Finish
	
	// 流程Code
	public static final String PROCESS_CODE_DELIVERABLE = "DA001";// 交付物任务审批流程
	public static final String PROCESS_CODE_PROGRESS_STATUS = "RA001";// 交付物状态审批流程
	
	//项目计划状态
	public static final String PROGRAM_STATUS_INACTIVE = "INACTIVE";//未激活
	public static final String PROGRAM_STATUS_PLANNED = "PLANNED";//完成计划
	public static final String PROGRAM_STATUS_ACTIVE = "ACTIVE";//激活
	
	// 项目生命周期
	public static final String LIFECYCLE_STATUS_DRAFT = "LIFECYCLE_STATUS_DRAFT";//草稿
	public static final String LIFECYCLE_STATUS_IN_PROCESS = "LIFECYCLE_STATUS_IN_PROCESS";//进行中
	public static final String LIFECYCLE_STATUS_HALT = "LIFECYCLE_STATUS_HALT";//暂停
	public static final String LIFECYCLE_STATUS_CLOSED = "LIFECYCLE_STATUS_CLOSED";//关闭
	public static final String LIFECYCLE_STATUS_CANCELLED = "LIFECYCLE_STATUS_CANCELLED";//取消

	// 通知来源
	public static final String NOTICE_SOURCE_PM = "NOTICE_SOURCE_PM";//项目管理
	public static final String NOTICE_SOURCE_QIM = "NOTICE_SOURCE_QIM";//质量问题
	public static final String NOTICE_SOURCE_WC = "NOTICE_SOURCE_WC";//工作联系单
	
	// 通知类型
	public static final String NOTICE_TYPE_TASK = "NOTICE_TYPE_TASK";//任务类型通知
	public static final String NOTICE_TYPE_TASK_READONLY = "NOTICE_TYPE_TASK_READONLY";//任务类型通知（只读）
	public static final String NOTICE_TYPE_TASK_PROCESS = "NOTICE_TYPE_TASK_PROCESS";//任务类型通知（状态变更流程）
	public static final String NOTICE_TYPE_PLAN = "NOTICE_TYPE_PLAN";//计划类型通知
	public static final String NOTICE_TYPE_ISSUE = "NOTICE_TYPE_ISSUE";//质量问题类型通知
	public static final String NOTICE_TYPE_DEPT_ISSUE_CT = "NOTICE_TYPE_DEPT_ISSUE_CT";// 部门OpenIssue-协作通知
	public static final String NOTICE_TYPE_PM_ISSUE_CT = "NOTICE_TYPE_PM_ISSUE_CT";// 项目OpenIssue-协作通知
	public static final String NOTICE_TYPE_DEPT_ISSUE_CMT = "NOTICE_TYPE_DEPT_ISSUE_CMT";// 部门OpenIssue-评论通知
	public static final String NOTICE_TYPE_PM_ISSUE_CMT = "NOTICE_TYPE_PM_ISSUE_CMT";// 项目OpenIssue-评论通知
	public static final String NOTICE_TYPE_DEPT_ISSUE = "NOTICE_TYPE_DEPT_ISSUE";// 部门OpenIssue-处理通知
	public static final String NOTICE_TYPE_PM_ISSUE = "NOTICE_TYPE_PM_ISSUE";// 项目OpenIssue-处理通知
	
	// 对象类型（用于发布记录）
	public static final String OBJECT_TYPE_PROGRAM = "OBJECT_TYPE_PROGRAM";//项目
	
	// 项目计划级别
	public static final Long PROGRAM_PLAN_LEVEL_1 = 1L;// 一级计划
	public static final Long PROGRAM_PLAN_LEVEL_2 = 2L;// 二级计划	

	// Y/N状态
	public static final String STATUS_Y = "Y";// 是
	public static final String STATUS_N = "N";// 否
	
	// 任务优先级
	public static final String TASK_PRIORITY_1 = "TASK_PRIORITY_1";// 低
	public static final String TASK_PRIORITY_2 = "TASK_PRIORITY_2";// 中
	public static final String TASK_PRIORITY_3 = "TASK_PRIORITY_3";// 高
	
	// 流程处理状态
	public static final String PROCESS_STATUS_DRAFT = "DRAFT";// 草稿
	public static final String PROCESS_STATUS_OPEN = "OPEN";// 等待
	public static final String PROCESS_STATUS_PENDING = "PENDING";// 处理中
	public static final String PROCESS_STATUS_COMPLETE = "COMPLETE";// 完成
	public static final String PROCESS_STATUS_CANCELLED = "CANCELLED";// 取消
	
	// PM_DOCUMENT_INDEX来源类型
	public static final String SOURCE_TYPE_TASK_DELIVERBLE = "SOURCE_TYPE_TASK_DELIVERBLE";//任务交付物附件
	public static final String SOURCE_TYPE_TASK_ATTACHMENT = "SOURCE_TYPE_TASK_ATTACHMENT";//任务附件
	public static final String SOURCE_TYPE_TASK_EXTENSION_EVIDENCE = "SOURCE_TYPE_TASK_EXTENSION_EVIDENCE";//任务延期证据
	public static final String SOURCE_TYPE_PM_RECORD = "SOURCE_TYPE_PM_RECORD";//项目备案记录
	public static final String SOURCE_TYPE_PM_SCOPE = "SOURCE_TYPE_PM_SCOPE";//项目开发范围
	public static final String SOURCE_TYPE_PM_ATTACHMENT = "SOURCE_TYPE_PM_ATTACHMENT";//项目附件
	public static final String SOURCE_TYPE_PLAN_EVIDENCE = "SOURCE_TYPE_PLAN_EVIDENCE";//计划发布证据

	public static final String SOURCE_TYPE_OPEN_ISSUE_ATTACHMENT = "SOURCE_TYPE_OPEN_ISSUE_ATTACHMENT";//open issue 附件
	public static final String SOURCE_TYPE_WORK_ORDER_ATTACHMENT = "SOURCE_TYPE_WORK_ORDER_ATTACHMENT";//workorder 附件
	public static final String SOURCE_TYPE_DEPT_ISSUE_ATTACHMENT = "SOURCE_TYPE_DEPT_ISSUE_ATTACHMENT";//部门OpenIssue附件

	public static final String SOURCE_TYPE_CUSTOMIZATION = "SOURCE_TYPE_CUSTOMIZATION";//用户自定义文件夹类型
	public static final String SOURCE_TYPE_DEPARTMENT = "SOURCE_TYPE_DEPARTMENT";//部门文件夹类型
	
	public static final String SOURCE_TYPE_PRJECT_NOTE = "SOURCE_TYPE_PROJECT_NOTE";//项目笔记文件夹
	
	// IMS_DOCUMENT_INDEX来源类型
	public static final String SOURCE_TYPE_DES_ATTACHMENT = "SOURCE_TYPE_DES_ATTACHMENT";//附件-问题提交
	final String SOURCE_TYPE_ISSUE_REASON_ATTACHMENT = "SOURCE_TYPE_ISSUE_REASON_ATTACHMENT";//附件-问题确认理由
	public static final String SOURCE_TYPE_CAUSE_ATTACHMENT = "SOURCE_TYPE_CAUSE_ATTACHMENT";//附件-根本原因
	public static final String SOURCE_TYPE_ACTION_ATTACHMENT = "SOURCE_TYPE_ACTION_ATTACHMENT";//附件-是否有对策
	public static final String SOURCE_TYPE_PERMACTION_ATTACHMENT = "SOURCE_TYPE_PERMACTION_ATTACHMENT";//附件-恒久对策
	public static final String SOURCE_TYPE_RESULT_ATTACHMENT = "SOURCE_TYPE_RESULT_ATTACHMENT";//附件-验证效果
	public static final String SOURCE_TYPE_REASON_ATTACHMENT = "SOURCE_TYPE_REASON_ATTACHMENT";//附件-理由
	
	public static final String RETURN_CODE_SUCCESS = "S";
	
	//菜单权限定义
	public static final String PDMS_MENU_100="PDMS_MENU_100";//首页
	public static final String PDMS_MENU_101="PDMS_MENU_101";//常用项目组件
	public static final String PDMS_MENU_102="PDMS_MENU_102";//待办任务组件
	public static final String PDMS_MENU_103="PDMS_MENU_103";//多项目管理状态报告组件
	public static final String PDMS_MENU_104="PDMS_MENU_104";//部门质量问题完成情况组件
	public static final String PDMS_MENU_200="PDMS_MENU_200";//个人空间
	public static final String PDMS_MENU_201="PDMS_MENU_201";//待办任务
	public static final String PDMS_MENU_202="PDMS_MENU_202";//已办任务
	public static final String PDMS_MENU_203="PDMS_MENU_203";//通知提醒
	public static final String PDMS_MENU_300="PDMS_MENU_300";//部门空间
	public static final String PDMS_MENU_301="PDMS_MENU_301";//部门工作一览
	public static final String PDMS_MENU_302="PDMS_MENU_302";//部门任务管理
	public static final String PDMS_MENU_303="PDMS_MENU_303";//部门OpenIssue管理
	public static final String PDMS_MENU_400="PDMS_MENU_400";//多项目报表
	public static final String PDMS_MENU_401="PDMS_MENU_401";//状态报表
	public static final String PDMS_MENU_402="PDMS_MENU_402";//进展报表
	public static final String PDMS_MENU_403="PDMS_MENU_403";//质量问题报表
	public static final String PDMS_MENU_404="PDMS_MENU_404";//性能指标报表
	public static final String PDMS_MENU_500="PDMS_MENU_500";//项目管理
	public static final String PDMS_MENU_501="PDMS_MENU_501";//项目列表
	public static final String PDMS_MENU_502="PDMS_MENU_502";//项目知识库
	public static final String PDMS_MENU_503="PDMS_MENU_503";//项目模板库
	public static final String PDMS_MENU_600="PDMS_MENU_600";//质量问题管理
	public static final String PDMS_MENU_700="PDMS_MENU_700";//工作联系单管理
	public static final String PDMS_MENU_800="PDMS_MENU_800";//系统管理
	public static final String PDMS_MENU_801="PDMS_MENU_801"; //角色管理
	
	// MENU_ID
	public static final Integer PDMS_MENU_WIDGET_100 = 100; //首页组件
	public static final Integer PDMS_MENU_WIDGET_601 = 601; //质量问题管理首页组件
	public static final Integer PDMS_MENU_WIDGET_800601 = 800601; //质量问题管理首页组件
	
	// 阀门状态
	public static final String GATE_STATUS_GRAY="GATE_STATUS_GRAY"; //灰色
	public static final String GATE_STATUS_GREEN="GATE_STATUS_GREEN"; //绿色
	public static final String GATE_STATUS_YELLOW="GATE_STATUS_YELLOW"; //黄色
	public static final String GATE_STATUS_RED="GATE_STATUS_RED"; //红色
	
	// 时程表节点显示位置
	public static final String TITLE_DISP_LOCATION_UP="TITLE_DISP_LOCATION_UP"; // 上
	public static final String TITLE_DISP_LOCATION_DOWN="TITLE_DISP_LOCATION_DOWN"; // 下
	public static final String TITLE_DISP_LOCATION_LEFT="TITLE_DISP_LOCATION_LEFT"; // 左
	public static final String TITLE_DISP_LOCATION_RIGHT="TITLE_DISP_LOCATION_RIGHT"; // 右
	
	
	public static final String SYSTEM_USER_ID="SYS";

	//数据同步日志参数
	public static final String SYSTEM_NAME_BIM = "BIM";
	public static final String DIRECTION_INPUT = "I";
	public static final String DIRECTION_OUTPUT = "O";
	
	public static final String OPERATION_NAME_BIM="BimDataSyncService.syncOrgAndUserFromBim";
	
	// SYS_CODE_SEQUENCE类型
	public static final String SYS_CODE_TASK = "SYS_CODE_TASK";	// 任务节点
	
	// 文件夹权限
	public static final String FOLDER_PERMISSION_OWNER = "FOLDER_PERMISSION_OWNER";	// 管理
	public static final String FOLDER_PERMISSION_READ = "FOLDER_PERMISSION_READ";	// 读取
	public static final String FOLDER_PERMISSION_WRITE = "FOLDER_PERMISSION_WRITE";	// 写入（上传+删除）
	public static final String FOLDER_PERMISSION_UPLOAD = "FOLDER_PERMISSION_UPLOAD";	// 上传
	public static final String FOLDER_PERMISSION_DELETE = "FOLDER_PERMISSION_DELETE";	// 删除
	
	// 前置/后置关系类型
	public static final String PRE_TASK_TYPE_PRE = "PRE"; //前置
	public static final String PRE_TASK_TYPE_POST = "POST"; //后置
	
	// 问题状态
	public static final String ISSUE_LIFECYCLE_DRAFT = "ISSUE_LIFECYCLE_DRAFT"; //草稿
	public static final String ISSUE_LIFECYCLE_OPEN = "ISSUE_LIFECYCLE_OPEN"; //待解决
	public static final String ISSUE_LIFECYCLE_PENDING = "ISSUE_LIFECYCLE_PENDING"; //解决中
	public static final String ISSUE_LIFECYCLE_CLOSED = "ISSUE_LIFECYCLE_CLOSED"; //已关闭
	public static final String ISSUE_LIFECYCLE_CANCELED = "ISSUE_LIFECYCLE_CANCELED"; //已取消
//	public static final String ISSUE_LIFECYCLE_INEFFECTIVE = "ISSUE_LIFECYCLE_INEFFECTIVE"; //失效
	public static final String ISSUE_LIFECYCLE_ARCHIVED = "ISSUE_LIFECYCLE_ARCHIVED"; //已归档
	
	// 问题对象
	public static final String ROLE_CODE_SUBMIT_USER = "SUBMIT_USER"; // 我参与的问题
	public static final String ROLE_CODE_INFO_USER = "INFO_USER"; // 知会给我的问题
	
	// 挂牌状态
	//public static final String ISSUE_LIST_STATUS_0 = "ISSUE_LIST_STATUS_0"; //申请挂牌
	public static final String ISSUE_LIST_STATUS_1 = "ISSUE_LIST_STATUS_1"; //已挂牌
	public static final String ISSUE_LIST_STATUS_2 = "ISSUE_LIST_STATUS_2"; //已摘牌
	//public static final String ISSUE_LIST_STATUS_3 = "ISSUE_LIST_STATUS_3"; //已取消挂牌
	
	// 问题状态
	public static final String ISSUE_STATUS_50 = "ISSUE_STATUS_50"; // 草稿
	public static final String ISSUE_STATUS_60 = "ISSUE_STATUS_60"; // 0:问题提出
	public static final String ISSUE_STATUS_61 = "ISSUE_STATUS_61"; // 1:问题发布
	public static final String ISSUE_STATUS_62 = "ISSUE_STATUS_62"; // 2:对策决定
	public static final String ISSUE_STATUS_63 = "ISSUE_STATUS_63"; // 3:措施实施
	public static final String ISSUE_STATUS_64 = "ISSUE_STATUS_64"; // 4:效果验证
	public static final String ISSUE_STATUS_65 = "ISSUE_STATUS_65"; // 5:问题关闭
	public static final String ISSUE_STATUS_66 = "ISSUE_STATUS_66"; // 6:再发防止
	public static final String ISSUE_STATUS_70 = "ISSUE_STATUS_70"; // 合并的
	
	
	//问题来源
	public static final String ISSUE_SOURCE_DEI = "DEI"; // 整车项目试验问题
	public static final String ISSUE_SOURCE_EEI = "EEI"; // 整车项目试制问题
	public static final String ISSUE_SOURCE_FEI = "FEI"; // 主观评价问题
	public static final String ISSUE_SOURCE_GEI = "GEI"; // 设计分析问题
	public static final String ISSUE_SOURCE_HEI = "HEI"; // 售后市场问题
	
	public static final String CURRENT_STEP_PROCESS_TRACE = "41";
	
	// 风险级别
	public static final String RISK_LEVEL_1 = "RISK_LEVEL_1";
	public static final String RISK_LEVEL_2 = "RISK_LEVEL_2";
	public static final String RISK_LEVEL_3 = "RISK_LEVEL_3";
	
	// 问题等级
	public static final String ISSUE_LEVEL_A = "ISSUE_LEVEL_A"; // A
	public static final String ISSUE_LEVEL_B = "ISSUE_LEVEL_B"; // B
	public static final String ISSUE_LEVEL_C = "ISSUE_LEVEL_C"; // C
	
	// 部门文档-部门全员可见角色ID
	public static final String PRIVILEGE_DEPT_ALL = "PRIVILEGE_DEPT_ALL";
	
	// 基线类型
	public static final String BASELINE_TYPE_MAIN = "MAIN PLAN"; // 主计划
	public static final String BASELINE_TYPE_FN = "FN PLAN"; // 二级计划
	
	// 工作联系单类型
	public static final String WORKORDER_TYPE_INBOUND = "INBOUND"; // 接收工作联系单
	public static final String WORKORDER_TYPE_OUTBOUND = "OUTBOUND"; // 发出工作联系单

}
