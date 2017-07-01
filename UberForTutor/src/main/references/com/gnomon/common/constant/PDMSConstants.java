package com.gnomon.common.constant;

public interface PDMSConstants {
	
	// ϵͳ��Ŀ��ɫID
	public static final String SYS_PROGRAM_PROFILE_DIRECTOR = "7692EC0BD4A344DDB763955858E8174A"; // ��Ŀ�ܼ�
	public static final String SYS_PROGRAM_PROFILE_PM = "AF361385A7F144A696CC5C98F7D1419F"; // ��Ŀ����
	public static final String SYS_PROGRAM_PROFILE_FM = "FFD578E366BA46AEBA0DA21AA9CD6DAA"; // רҵ����
	public static final String SYS_PROGRAM_PROFILE_QM = "E57DB49C98DD41E9ADD79E0AD77B89AD"; // ��������
	
	//��Ŀ��֯����
	public static final String OBS_TYPE_RESP_DEPT = "OBS_TYPE_RESP_DEPT";//רҵ����
	
	//��������
	public static final String TASK_TYPE_MAIN_NODE = "TASK_TYPE_MAIN_NODE";//���ڵ�
	public static final String TASK_TYPE_SOP_NODE = "TASK_TYPE_SOP_NODE";//SOP�ڵ�
	public static final String TASK_TYPE_GATE = "TASK_TYPE_GATE";//������
	public static final String TASK_TYPE_NODE = "TASK_TYPE_NODE";//�ڵ�
	public static final String TASK_TYPE_ACTIVITY = "TASK_TYPE_ACTIVITY";//����
	
	//����״̬
	public static final String TASK_STATUS_NOT_START = "TASK_STATUS_NOT_START";
	public static final String TASK_STATUS_IN_PROCESS = "TASK_STATUS_IN_PROCESS";
	public static final String TASK_STATUS_CLOSED = "TASK_STATUS_CLOSED";
	public static final String TASK_STATUS_CANCELLED = "TASK_STATUS_CANCELLED";
	
	// �����չ״̬
	public static final String TASK_PROGRESS_STATUS_GREY = "TASK_PROGRESS_STATUS_GREY";// δ����-��
	public static final String TASK_PROGRESS_STATUS_G = "TASK_PROGRESS_STATUS_G";// �޷���-��
	public static final String TASK_PROGRESS_STATUS_GY = "TASK_PROGRESS_STATUS_GY";// С����-�̻�
	public static final String TASK_PROGRESS_STATUS_Y = "TASK_PROGRESS_STATUS_Y";// �з���-��
	public static final String TASK_PROGRESS_STATUS_R = "TASK_PROGRESS_STATUS_R";// �����-��
	public static final String TASK_PROGRESS_STATUS_B = "TASK_PROGRESS_STATUS_B";// �޷��ж�-��
	
	//ǰ����������
	public static final String PRE_TASK_TYPE_FS = "PRETASK_TYPE_FS";//Finish Start 
	public static final String PRE_TASK_TYPE_FF = "PRETASK_TYPE_FF";//Finish Finish
	public static final String PRE_TASK_TYPE_SS = "PRETASK_TYPE_SS";//Start Start
	public static final String PRE_TASK_TYPE_SF = "PRETASK_TYPE_SF";//Start Finish
	
	// ����Code
	public static final String PROCESS_CODE_DELIVERABLE = "DA001";// ������������������
	public static final String PROCESS_CODE_PROGRESS_STATUS = "RA001";// ������״̬��������
	
	//��Ŀ�ƻ�״̬
	public static final String PROGRAM_STATUS_INACTIVE = "INACTIVE";//δ����
	public static final String PROGRAM_STATUS_PLANNED = "PLANNED";//��ɼƻ�
	public static final String PROGRAM_STATUS_ACTIVE = "ACTIVE";//����
	
	// ��Ŀ��������
	public static final String LIFECYCLE_STATUS_DRAFT = "LIFECYCLE_STATUS_DRAFT";//�ݸ�
	public static final String LIFECYCLE_STATUS_IN_PROCESS = "LIFECYCLE_STATUS_IN_PROCESS";//������
	public static final String LIFECYCLE_STATUS_HALT = "LIFECYCLE_STATUS_HALT";//��ͣ
	public static final String LIFECYCLE_STATUS_CLOSED = "LIFECYCLE_STATUS_CLOSED";//�ر�
	public static final String LIFECYCLE_STATUS_CANCELLED = "LIFECYCLE_STATUS_CANCELLED";//ȡ��

	// ֪ͨ��Դ
	public static final String NOTICE_SOURCE_PM = "NOTICE_SOURCE_PM";//��Ŀ����
	public static final String NOTICE_SOURCE_QIM = "NOTICE_SOURCE_QIM";//��������
	public static final String NOTICE_SOURCE_WC = "NOTICE_SOURCE_WC";//������ϵ��
	
	// ֪ͨ����
	public static final String NOTICE_TYPE_TASK = "NOTICE_TYPE_TASK";//��������֪ͨ
	public static final String NOTICE_TYPE_TASK_READONLY = "NOTICE_TYPE_TASK_READONLY";//��������֪ͨ��ֻ����
	public static final String NOTICE_TYPE_TASK_PROCESS = "NOTICE_TYPE_TASK_PROCESS";//��������֪ͨ��״̬������̣�
	public static final String NOTICE_TYPE_PLAN = "NOTICE_TYPE_PLAN";//�ƻ�����֪ͨ
	public static final String NOTICE_TYPE_ISSUE = "NOTICE_TYPE_ISSUE";//������������֪ͨ
	public static final String NOTICE_TYPE_DEPT_ISSUE_CT = "NOTICE_TYPE_DEPT_ISSUE_CT";// ����OpenIssue-Э��֪ͨ
	public static final String NOTICE_TYPE_PM_ISSUE_CT = "NOTICE_TYPE_PM_ISSUE_CT";// ��ĿOpenIssue-Э��֪ͨ
	public static final String NOTICE_TYPE_DEPT_ISSUE_CMT = "NOTICE_TYPE_DEPT_ISSUE_CMT";// ����OpenIssue-����֪ͨ
	public static final String NOTICE_TYPE_PM_ISSUE_CMT = "NOTICE_TYPE_PM_ISSUE_CMT";// ��ĿOpenIssue-����֪ͨ
	public static final String NOTICE_TYPE_DEPT_ISSUE = "NOTICE_TYPE_DEPT_ISSUE";// ����OpenIssue-����֪ͨ
	public static final String NOTICE_TYPE_PM_ISSUE = "NOTICE_TYPE_PM_ISSUE";// ��ĿOpenIssue-����֪ͨ
	
	// �������ͣ����ڷ�����¼��
	public static final String OBJECT_TYPE_PROGRAM = "OBJECT_TYPE_PROGRAM";//��Ŀ
	
	// ��Ŀ�ƻ�����
	public static final Long PROGRAM_PLAN_LEVEL_1 = 1L;// һ���ƻ�
	public static final Long PROGRAM_PLAN_LEVEL_2 = 2L;// �����ƻ�	

	// Y/N״̬
	public static final String STATUS_Y = "Y";// ��
	public static final String STATUS_N = "N";// ��
	
	// �������ȼ�
	public static final String TASK_PRIORITY_1 = "TASK_PRIORITY_1";// ��
	public static final String TASK_PRIORITY_2 = "TASK_PRIORITY_2";// ��
	public static final String TASK_PRIORITY_3 = "TASK_PRIORITY_3";// ��
	
	// ���̴���״̬
	public static final String PROCESS_STATUS_DRAFT = "DRAFT";// �ݸ�
	public static final String PROCESS_STATUS_OPEN = "OPEN";// �ȴ�
	public static final String PROCESS_STATUS_PENDING = "PENDING";// ������
	public static final String PROCESS_STATUS_COMPLETE = "COMPLETE";// ���
	public static final String PROCESS_STATUS_CANCELLED = "CANCELLED";// ȡ��
	
	// PM_DOCUMENT_INDEX��Դ����
	public static final String SOURCE_TYPE_TASK_DELIVERBLE = "SOURCE_TYPE_TASK_DELIVERBLE";//���񽻸��︽��
	public static final String SOURCE_TYPE_TASK_ATTACHMENT = "SOURCE_TYPE_TASK_ATTACHMENT";//���񸽼�
	public static final String SOURCE_TYPE_TASK_EXTENSION_EVIDENCE = "SOURCE_TYPE_TASK_EXTENSION_EVIDENCE";//��������֤��
	public static final String SOURCE_TYPE_PM_RECORD = "SOURCE_TYPE_PM_RECORD";//��Ŀ������¼
	public static final String SOURCE_TYPE_PM_SCOPE = "SOURCE_TYPE_PM_SCOPE";//��Ŀ������Χ
	public static final String SOURCE_TYPE_PM_ATTACHMENT = "SOURCE_TYPE_PM_ATTACHMENT";//��Ŀ����
	public static final String SOURCE_TYPE_PLAN_EVIDENCE = "SOURCE_TYPE_PLAN_EVIDENCE";//�ƻ�����֤��

	public static final String SOURCE_TYPE_OPEN_ISSUE_ATTACHMENT = "SOURCE_TYPE_OPEN_ISSUE_ATTACHMENT";//open issue ����
	public static final String SOURCE_TYPE_WORK_ORDER_ATTACHMENT = "SOURCE_TYPE_WORK_ORDER_ATTACHMENT";//workorder ����
	public static final String SOURCE_TYPE_DEPT_ISSUE_ATTACHMENT = "SOURCE_TYPE_DEPT_ISSUE_ATTACHMENT";//����OpenIssue����

	public static final String SOURCE_TYPE_CUSTOMIZATION = "SOURCE_TYPE_CUSTOMIZATION";//�û��Զ����ļ�������
	public static final String SOURCE_TYPE_DEPARTMENT = "SOURCE_TYPE_DEPARTMENT";//�����ļ�������
	
	public static final String SOURCE_TYPE_PRJECT_NOTE = "SOURCE_TYPE_PROJECT_NOTE";//��Ŀ�ʼ��ļ���
	
	// IMS_DOCUMENT_INDEX��Դ����
	public static final String SOURCE_TYPE_DES_ATTACHMENT = "SOURCE_TYPE_DES_ATTACHMENT";//����-�����ύ
	final String SOURCE_TYPE_ISSUE_REASON_ATTACHMENT = "SOURCE_TYPE_ISSUE_REASON_ATTACHMENT";//����-����ȷ������
	public static final String SOURCE_TYPE_CAUSE_ATTACHMENT = "SOURCE_TYPE_CAUSE_ATTACHMENT";//����-����ԭ��
	public static final String SOURCE_TYPE_ACTION_ATTACHMENT = "SOURCE_TYPE_ACTION_ATTACHMENT";//����-�Ƿ��жԲ�
	public static final String SOURCE_TYPE_PERMACTION_ATTACHMENT = "SOURCE_TYPE_PERMACTION_ATTACHMENT";//����-��öԲ�
	public static final String SOURCE_TYPE_RESULT_ATTACHMENT = "SOURCE_TYPE_RESULT_ATTACHMENT";//����-��֤Ч��
	public static final String SOURCE_TYPE_REASON_ATTACHMENT = "SOURCE_TYPE_REASON_ATTACHMENT";//����-����
	
	public static final String RETURN_CODE_SUCCESS = "S";
	
	//�˵�Ȩ�޶���
	public static final String PDMS_MENU_100="PDMS_MENU_100";//��ҳ
	public static final String PDMS_MENU_101="PDMS_MENU_101";//������Ŀ���
	public static final String PDMS_MENU_102="PDMS_MENU_102";//�����������
	public static final String PDMS_MENU_103="PDMS_MENU_103";//����Ŀ����״̬�������
	public static final String PDMS_MENU_104="PDMS_MENU_104";//���������������������
	public static final String PDMS_MENU_200="PDMS_MENU_200";//���˿ռ�
	public static final String PDMS_MENU_201="PDMS_MENU_201";//��������
	public static final String PDMS_MENU_202="PDMS_MENU_202";//�Ѱ�����
	public static final String PDMS_MENU_203="PDMS_MENU_203";//֪ͨ����
	public static final String PDMS_MENU_300="PDMS_MENU_300";//���ſռ�
	public static final String PDMS_MENU_301="PDMS_MENU_301";//���Ź���һ��
	public static final String PDMS_MENU_302="PDMS_MENU_302";//�����������
	public static final String PDMS_MENU_303="PDMS_MENU_303";//����OpenIssue����
	public static final String PDMS_MENU_400="PDMS_MENU_400";//����Ŀ����
	public static final String PDMS_MENU_401="PDMS_MENU_401";//״̬����
	public static final String PDMS_MENU_402="PDMS_MENU_402";//��չ����
	public static final String PDMS_MENU_403="PDMS_MENU_403";//�������ⱨ��
	public static final String PDMS_MENU_404="PDMS_MENU_404";//����ָ�걨��
	public static final String PDMS_MENU_500="PDMS_MENU_500";//��Ŀ����
	public static final String PDMS_MENU_501="PDMS_MENU_501";//��Ŀ�б�
	public static final String PDMS_MENU_502="PDMS_MENU_502";//��Ŀ֪ʶ��
	public static final String PDMS_MENU_503="PDMS_MENU_503";//��Ŀģ���
	public static final String PDMS_MENU_600="PDMS_MENU_600";//�����������
	public static final String PDMS_MENU_700="PDMS_MENU_700";//������ϵ������
	public static final String PDMS_MENU_800="PDMS_MENU_800";//ϵͳ����
	public static final String PDMS_MENU_801="PDMS_MENU_801"; //��ɫ����
	
	// MENU_ID
	public static final Integer PDMS_MENU_WIDGET_100 = 100; //��ҳ���
	public static final Integer PDMS_MENU_WIDGET_601 = 601; //�������������ҳ���
	public static final Integer PDMS_MENU_WIDGET_800601 = 800601; //�������������ҳ���
	
	// ����״̬
	public static final String GATE_STATUS_GRAY="GATE_STATUS_GRAY"; //��ɫ
	public static final String GATE_STATUS_GREEN="GATE_STATUS_GREEN"; //��ɫ
	public static final String GATE_STATUS_YELLOW="GATE_STATUS_YELLOW"; //��ɫ
	public static final String GATE_STATUS_RED="GATE_STATUS_RED"; //��ɫ
	
	// ʱ�̱�ڵ���ʾλ��
	public static final String TITLE_DISP_LOCATION_UP="TITLE_DISP_LOCATION_UP"; // ��
	public static final String TITLE_DISP_LOCATION_DOWN="TITLE_DISP_LOCATION_DOWN"; // ��
	public static final String TITLE_DISP_LOCATION_LEFT="TITLE_DISP_LOCATION_LEFT"; // ��
	public static final String TITLE_DISP_LOCATION_RIGHT="TITLE_DISP_LOCATION_RIGHT"; // ��
	
	
	public static final String SYSTEM_USER_ID="SYS";

	//����ͬ����־����
	public static final String SYSTEM_NAME_BIM = "BIM";
	public static final String DIRECTION_INPUT = "I";
	public static final String DIRECTION_OUTPUT = "O";
	
	public static final String OPERATION_NAME_BIM="BimDataSyncService.syncOrgAndUserFromBim";
	
	// SYS_CODE_SEQUENCE����
	public static final String SYS_CODE_TASK = "SYS_CODE_TASK";	// ����ڵ�
	
	// �ļ���Ȩ��
	public static final String FOLDER_PERMISSION_OWNER = "FOLDER_PERMISSION_OWNER";	// ����
	public static final String FOLDER_PERMISSION_READ = "FOLDER_PERMISSION_READ";	// ��ȡ
	public static final String FOLDER_PERMISSION_WRITE = "FOLDER_PERMISSION_WRITE";	// д�루�ϴ�+ɾ����
	public static final String FOLDER_PERMISSION_UPLOAD = "FOLDER_PERMISSION_UPLOAD";	// �ϴ�
	public static final String FOLDER_PERMISSION_DELETE = "FOLDER_PERMISSION_DELETE";	// ɾ��
	
	// ǰ��/���ù�ϵ����
	public static final String PRE_TASK_TYPE_PRE = "PRE"; //ǰ��
	public static final String PRE_TASK_TYPE_POST = "POST"; //����
	
	// ����״̬
	public static final String ISSUE_LIFECYCLE_DRAFT = "ISSUE_LIFECYCLE_DRAFT"; //�ݸ�
	public static final String ISSUE_LIFECYCLE_OPEN = "ISSUE_LIFECYCLE_OPEN"; //�����
	public static final String ISSUE_LIFECYCLE_PENDING = "ISSUE_LIFECYCLE_PENDING"; //�����
	public static final String ISSUE_LIFECYCLE_CLOSED = "ISSUE_LIFECYCLE_CLOSED"; //�ѹر�
	public static final String ISSUE_LIFECYCLE_CANCELED = "ISSUE_LIFECYCLE_CANCELED"; //��ȡ��
//	public static final String ISSUE_LIFECYCLE_INEFFECTIVE = "ISSUE_LIFECYCLE_INEFFECTIVE"; //ʧЧ
	public static final String ISSUE_LIFECYCLE_ARCHIVED = "ISSUE_LIFECYCLE_ARCHIVED"; //�ѹ鵵
	
	// �������
	public static final String ROLE_CODE_SUBMIT_USER = "SUBMIT_USER"; // �Ҳ��������
	public static final String ROLE_CODE_INFO_USER = "INFO_USER"; // ֪����ҵ�����
	
	// ����״̬
	//public static final String ISSUE_LIST_STATUS_0 = "ISSUE_LIST_STATUS_0"; //�������
	public static final String ISSUE_LIST_STATUS_1 = "ISSUE_LIST_STATUS_1"; //�ѹ���
	public static final String ISSUE_LIST_STATUS_2 = "ISSUE_LIST_STATUS_2"; //��ժ��
	//public static final String ISSUE_LIST_STATUS_3 = "ISSUE_LIST_STATUS_3"; //��ȡ������
	
	// ����״̬
	public static final String ISSUE_STATUS_50 = "ISSUE_STATUS_50"; // �ݸ�
	public static final String ISSUE_STATUS_60 = "ISSUE_STATUS_60"; // 0:�������
	public static final String ISSUE_STATUS_61 = "ISSUE_STATUS_61"; // 1:���ⷢ��
	public static final String ISSUE_STATUS_62 = "ISSUE_STATUS_62"; // 2:�Բ߾���
	public static final String ISSUE_STATUS_63 = "ISSUE_STATUS_63"; // 3:��ʩʵʩ
	public static final String ISSUE_STATUS_64 = "ISSUE_STATUS_64"; // 4:Ч����֤
	public static final String ISSUE_STATUS_65 = "ISSUE_STATUS_65"; // 5:����ر�
	public static final String ISSUE_STATUS_66 = "ISSUE_STATUS_66"; // 6:�ٷ���ֹ
	public static final String ISSUE_STATUS_70 = "ISSUE_STATUS_70"; // �ϲ���
	
	
	//������Դ
	public static final String ISSUE_SOURCE_DEI = "DEI"; // ������Ŀ��������
	public static final String ISSUE_SOURCE_EEI = "EEI"; // ������Ŀ��������
	public static final String ISSUE_SOURCE_FEI = "FEI"; // ������������
	public static final String ISSUE_SOURCE_GEI = "GEI"; // ��Ʒ�������
	public static final String ISSUE_SOURCE_HEI = "HEI"; // �ۺ��г�����
	
	public static final String CURRENT_STEP_PROCESS_TRACE = "41";
	
	// ���ռ���
	public static final String RISK_LEVEL_1 = "RISK_LEVEL_1";
	public static final String RISK_LEVEL_2 = "RISK_LEVEL_2";
	public static final String RISK_LEVEL_3 = "RISK_LEVEL_3";
	
	// ����ȼ�
	public static final String ISSUE_LEVEL_A = "ISSUE_LEVEL_A"; // A
	public static final String ISSUE_LEVEL_B = "ISSUE_LEVEL_B"; // B
	public static final String ISSUE_LEVEL_C = "ISSUE_LEVEL_C"; // C
	
	// �����ĵ�-����ȫԱ�ɼ���ɫID
	public static final String PRIVILEGE_DEPT_ALL = "PRIVILEGE_DEPT_ALL";
	
	// ��������
	public static final String BASELINE_TYPE_MAIN = "MAIN PLAN"; // ���ƻ�
	public static final String BASELINE_TYPE_FN = "FN PLAN"; // �����ƻ�
	
	// ������ϵ������
	public static final String WORKORDER_TYPE_INBOUND = "INBOUND"; // ���չ�����ϵ��
	public static final String WORKORDER_TYPE_OUTBOUND = "OUTBOUND"; // ����������ϵ��

}
