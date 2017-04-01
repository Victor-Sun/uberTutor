package com.gnomon.pdms.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.system.service.PrivilegeService;
import com.gnomon.common.utils.DateUtils;
import com.gnomon.common.web.SessionData;
import com.gnomon.dms.api.CheckinRevisionType;
import com.gnomon.dms.api.DMSConstants;
import com.gnomon.dms.api.DMSDocument;
import com.gnomon.dms.api.DMSDocumentAPI;
import com.gnomon.dms.api.DMSDocumentRevision;
import com.gnomon.dms.api.DMSFolder;
import com.gnomon.dms.api.DMSFolderAPI;
import com.gnomon.dms.api.DmsAPIFactory;
import com.gnomon.pdms.entity.GTDocumentVO;

@Service
@Transactional
public class PMDocumentIndexManager {
//	
//	/**
//	 * 
//	 */
//	private static final String SOURCE_TYPE_PDC = "PDC";
//	public static final String SOURCETYPE_GATECHECKITEMDELIVERABLE = "GATE_CHECKITEM_DELIVERABLE";//检查项交付物
//	public static final String SOURCETYPE_BO = "SOURCETYPE_BO";//检查项交付物
//	public static final String SOURCETYPE_TMP_GATECHECKITEM = "TMP_GATECHECKITEM";//门径模板交付物
//	
//	public static final String SYSTEM_CODE_PDMS="PDMS";
//	public static final String SYSTEM_CODE_WORKSPACE="WORKSPACE";
//	public static final String SYSTEM_CODE_DEFAULT="DEFAULT";
//	
//	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	
//	@Autowired
//	private GTDocumentIndexDao gtDocumentIndexDao;
//	
//
//	@Autowired
//	private PrivilegeService privilegeService;
//	
//	
//	
//	
//	/**
//	 * 删除项目文档
//	 * @param programId
//	 */
//	public void deleteProgramDocument(String programId){
//		List<PMDocumentIndex> docIndexs = gtDocumentIndexDao.find(new Criterion[]{Restrictions.eq("gtProgramId", programId)});
//		for(PMDocumentIndex docIndex:docIndexs){
//			deleteGTDocumentIndex(docIndex.getId());
//		}
//
//	}
//	
//	/**
//	 * 删除附件
//	 * @param id documentIndexId
//	 */
//	public void deleteGTDocumentIndex(String id) {
//		PMDocumentIndex gtDocumentIndex = gtDocumentIndexDao.get(id);
//		Long gtDocumentId = gtDocumentIndex.getGtDocumentId();
//		String sourceType = gtDocumentIndex.getSourceType();
//		String sourceId = gtDocumentIndex.getSourceId();
//		//查询该文档是否被其他引用
//		List<PMDocumentIndex> docIndexs = gtDocumentIndexDao.find(new Criterion[]{Restrictions.eq("gtDocumentId", gtDocumentId),Restrictions.ne("sourceId", sourceId)});
//		List<Long> versions = new ArrayList<Long>();
//		for (PMDocumentIndex gtDocumentIndex2 : docIndexs) {
//			versions.add(gtDocumentIndex2.getGtDocumentVersionId());
//		}
//		if(docIndexs.size() > 0){
//			//有则只删除index记录和对应文档版本
//			List<PMDocumentIndex> currDocIndexs = gtDocumentIndexDao.find(new Criterion[]{Restrictions.eq("gtDocumentId", gtDocumentId),Restrictions.eq("sourceType", sourceType),Restrictions.eq("sourceId", sourceId)});
//			for (PMDocumentIndex index : currDocIndexs) {
//				if(!versions.contains(index.getGtDocumentVersionId())){
//					getDMSDocumentAPI().deleteRevision(index.getGtDocumentVersionId(),getCurrentUserId());
//				}
//				gtDocumentIndexDao.delete(index);
//			}
//		}else{
//			//否则删除实际的文档
//			try{
//				getDMSDocumentAPI().deleteDocument(gtDocumentId, getCurrentUserId());
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			gtDocumentIndexDao.delete(id);
//		}
//	}
//	
////	/**
////	 * 删除附件最新版本
////	 * @param id documentIndexId
////	 */
////	public void deleteGTDocumentIndexLastVersion(String id) {
////		PMDocumentIndex gtDocumentIndex = gtDocumentIndexDao.get(id);
////		Long gtDocumentId = gtDocumentIndex.getGtDocumentId();
////
//////		documentService.deleteLastestRevision(String.valueOf(gtDocumentId));
//////		GTDocumentRevision gtDocumentVersion = documentService.getLastestRevision(String.valueOf(gtDocumentId));
////		List<DMSDocumentRevision> list = getDMSDocumentAPI().getAllRevisions(gtDocumentId);
//////		List<GTDocumentRevision> list = documentService.getAllRevisions(String.valueOf(gtDocumentId));
////		if(list.size() == 1){
////			gtDocumentIndexDao.delete(gtDocumentIndex);
////		}
////		getDMSDocumentAPI().deleteLastRevision(gtDocumentId, getCurentUserId());
////	}
//	
//	private String getCurentUserId() {
//		return getCurrentUserId();
//	}
//	private DMSDocumentAPI getDMSDocumentAPI(){
//		return DmsAPIFactory.getDocumentAPI();
//	}
//	private DMSFolderAPI getDMSFolderAPI(){
//		return DmsAPIFactory.getFolderAPI();
//	}
//
//	/**
//	 * 查询项目下的所有文件
//	 * @param progId
//	 * @return
//	 * @throws RuntimeException
//	 */
//	public List<GTDocumentVO> getAllGTDocumentByProgId(Long progId) throws RuntimeException{
//		List<PMDocumentIndex> docIndexs = gtDocumentIndexDao.find(new Criterion[]{Restrictions.eq("gtProgramId", progId)});
//		List<GTDocumentVO> vos = gtDocumentPoToVo(docIndexs);
//		return vos; 
//	}
//	
//	/**
//	 * 使用属性过滤条件查询项目.
//	 */
//	@Transactional(readOnly = true)
//	public Page<GTDocumentVO> searchPage(final Page<PMDocumentIndex> page,
//			long progId, String shortName,String stageid) {
//		
//		Map<String, Object> parameter = new HashMap<String, Object>();
//		parameter.put("progId", progId);
//		StringBuffer hqlstr = new StringBuffer();
//		hqlstr.append("select distinct gtDocumentId from PMDocumentIndex where gtProgramId = :progId ");
//		if (shortName != null && shortName != "") {
//			List<DMSDocument> docs = getDMSDocumentAPI().searchDocumentByName(shortName);
//			String docIds = "";
//			for (DMSDocument dmsDocument : docs) {
//				docIds = docIds + dmsDocument.getId() +",";
//			}
//			if(org.apache.commons.lang.StringUtils.isNotBlank(docIds)){
//				docIds = docIds.substring(0, docIds.length()-1);
//			}
//			hqlstr.append(" and gtDocumentId in ("+docIds+") ");
//		}
//		if (stageid != null && !"".equals(stageid)) {
//			hqlstr.append("and gtProcessId in (select id from GTProcess where stageid ='"+stageid+"')");
//		}
//		//hqlstr.append(" order by planStartDate asc");
//		List<Long> docIds = gtDocumentIndexDao.findDocIdsPage(page, hqlstr.toString(), parameter);
//		
//		Page<GTDocumentVO> documentPage = new Page<GTDocumentVO>();
//		List<GTDocumentVO> vos = new ArrayList<GTDocumentVO>();
//		for (Long docId : docIds) {
//			DMSDocument document = getDMSDocumentAPI().getDocument(docId);
//			GTDocumentVO vo = new GTDocumentVO();
//			DMSDocumentRevision lastOrXXVersion = getDMSDocumentAPI().getLastRevision(docId);
//			
//			
//			String documentURL = "<a href='"+Struts2Utils.getRequest().getContextPath()+"/common/document/dms!downloadLastVersion.action?documentId="+document.getId()+"'>"+document.getName()+"</a>";
//			vo.setDocumentPath(documentURL);
//			vo.setShortName(document.getName());
//			vo.setUploadBy(lastOrXXVersion.getCreateUser());
//			vo.setUploadDate(lastOrXXVersion.getCreateDate());
//			vo.setVersion(lastOrXXVersion.getRevision());
////			String docStage = gtDocumentIndexDao.findDocStage(String.valueOf(docId));
////			vo.setStage(docStage);
//			vo.setGtDocumentId(docId);
//			vos.add(vo);
//		}
//		
//		documentPage.setResult(vos);
//		documentPage.setPageSize(page.getPageSize());
//		//获取当前页数据
//		documentPage.setAutoCount(false);
//		documentPage.setTotalCount(page.getTotalCount());
//		documentPage.setPageNo(page.getPageNo());
//		return documentPage;
//	}
//	
//	/**
//	 * 使用属性过滤条件查询项目.
//	 */
//	@Transactional(readOnly = true)
//	public Page<GTDocumentVO> searchPage(final Page<PMDocumentIndex> page,
//			long progId,long processId, String shortName) {
//		Map<String, Object> parameter = new HashMap<String, Object>();
//		parameter.put("progId", progId);
//		parameter.put("processId", processId);
//		StringBuffer hqlstr = new StringBuffer();
//		hqlstr.append("from PMDocumentIndex where gtProgramId = :progId and gtProcessId =:processId ");
//		if (shortName != null && shortName != "") {
//			hqlstr.append(" and gtDocumentId in (select id from GTDocument where name like '"+shortName+"%' ) ");
//		}
////		hqlstr.append(" order by planStartDate asc");
//		Page<PMDocumentIndex> pageIndexs = gtDocumentIndexDao.findPage(page, hqlstr.toString(), parameter);
//		Page<GTDocumentVO> documentVo = new Page<GTDocumentVO>();
//		List<PMDocumentIndex> docIndexs = pageIndexs.getResult();
//		List<GTDocumentVO> vos = gtDocumentPoToVo(docIndexs);
//		documentVo.setResult(vos);
//		documentVo.setTotalCount(pageIndexs.getTotalCount());
//		documentVo.setPageNo(pageIndexs.getPageNo());
//		return documentVo;
//	}
//	
//	/**
//	 * 检入文档
//	 * @param fileName
//	 * @param progId
//	 * @param localFileNam
//	 * @param file
//	 * @return
//	 */
//	private String checkinDocument(String fileName,String sourceType,String sourceId,String localFileNam,File file){
//		DMSDocumentRevision documentRevision = new DMSDocumentRevision();
//		DMSDocument document = new DMSDocument();
//		document.setName(fileName);
//		document.setCreateDate(new Date());
//		document.setCreateUser(getCurrentUserId());
//		document.setFolderId(Long.parseLong(getDefaultFolderId(sourceType,sourceId)));
//		documentRevision.setDocument(document);
//		documentRevision.setComment("Upload from DMS Component");
//		documentRevision.setCreateUser(getCurrentUserId());
//		documentRevision.setCreateDate(new Date());
//		documentRevision.setLocalName(localFileNam);
//		documentRevision.setDocument(document);
//		Long revisionId = null;
//		try {
//			revisionId = getDMSDocumentAPI().checkin(documentRevision,new FileInputStream(file),CheckinRevisionType.MINOR_REVISION,getCurentUserId());
////			DMSDocumentRevision dmsDocumentRevision = getDMSDocumentAPI().getRevision(revisionId);
////			documentId = dmsDocumentRevision.getDocument().getId().toString();
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException(e.getMessage(),e);
//		}
//		
//		return ""+revisionId;
//	}
//	
//	public void saveGTDocumentIndex(String progId, String sourceType, String sourceID, 
//			String createUser,String documentName,String localFileName, File file) throws RuntimeException {
//		String documentId = checkinDocument(documentName, sourceType, sourceID, localFileName, file);
//		saveGTDocumentIndex(progId,sourceType,sourceID,createUser,Long.parseLong(documentId));
//	}
//	
//	private void saveGTDocumentIndex(String progId, String sourceType, String sourceID, 
//			String createUser, long revisionId) throws RuntimeException {
//
//		try {
//			DMSDocumentRevision dmsDocumentRevision = getDMSDocumentAPI().getRevision(revisionId);
//			Long documentId = dmsDocumentRevision.getDocument().getId();
//			//更新“是否最后记录”的标志为false
//			PMDocumentIndex oldDocIndex = gtDocumentIndexDao.findUnique(new Criterion[] {
//					Restrictions.eq("sourceType", sourceType), 
//					Restrictions.eq("sourceId", sourceID),
//					Restrictions.eq("gtDocumentId", documentId), 
//					Restrictions.eq("isLast", true) });
//			if(oldDocIndex != null){				
//				oldDocIndex.setLast(false);
//				gtDocumentIndexDao.save(oldDocIndex);
//			}
//			//插入一条新记录
//			PMDocumentIndex lastDocIndex = new PMDocumentIndex();
//			lastDocIndex.setGtProgramId(progId);
//			lastDocIndex.setSourceType(sourceType);
//			lastDocIndex.setSourceId(sourceID);
//			lastDocIndex.setGtDocumentId(documentId);
//
//			DMSDocumentRevision lastRevision = getDMSDocumentAPI().getLastRevision(documentId);
//			lastDocIndex.setGtDocumentVersionId(lastRevision.getId());
//
//			lastDocIndex.setLast(true);
//			gtDocumentIndexDao.save(lastDocIndex);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//	
//
//	
//
//	public Page<GTDocumentVO> findGTDocumentPage(Page<GTDocumentVO> page, String sourceType, String sourceId) {
//		Page<PMDocumentIndex> indexPage = new Page<PMDocumentIndex>();
//		indexPage.setPageSize(page.getPageSize());
//		indexPage.setPageNo(page.getPageNo());
//		indexPage.setOrderBy("id");
//		indexPage.setOrder("DESC");
//		gtDocumentIndexDao.findPage(indexPage ,new Criterion[]{Restrictions.eq("sourceType", sourceType),Restrictions.eq("sourceId", sourceId),Restrictions.eq("isLast", true)});
//		page.setResult(gtDocumentPoToVo(indexPage.getResult()));
//		page.setTotalCount(indexPage.getTotalCount());
//		return page;
//	}
//
//	public List<GTDocumentVO> findGTDocument(String sourceType, String sourceId) {
//		List<PMDocumentIndex> docIndexs = gtDocumentIndexDao.find(new Criterion[]{Restrictions.eq("sourceType", sourceType),Restrictions.eq("sourceId", sourceId),Restrictions.eq("isLast", true)});
//		List<GTDocumentVO> vos = gtDocumentPoToVo(docIndexs);
//		return vos; 
//	}
//	
//	
//	private List<GTDocumentVO> gtDocumentPoToVo(List<PMDocumentIndex> result) {
//		List<GTDocumentVO> vos = new ArrayList<GTDocumentVO>();
////		String uid = "";
////		String sessionId = "";
////		try{
////			uid = Struts2Utils.getSessionAttribute("aws-uid").toString();
////			sessionId = Struts2Utils.getSessionAttribute("aws-sid").toString();
////		}catch (Exception e) {
////			//TODO webservice 调用导致取不到uid
////		}
//		for (PMDocumentIndex index : result) {
//			GTDocumentVO vo = new GTDocumentVO(); 
//			vo.setId(index.getId());
//			vo.setSourceID(index.getSourceId());
//			vo.setSourceType(index.getSourceType());
//			Long documentId = index.getGtDocumentId();
//			String strDocumentId = String.valueOf(documentId);
//			long begintime = System.currentTimeMillis();
////			System.out.println(begintime);
////			GTDocument document = documentService.getDocument(strDocumentId);
////			System.out.println("==========="+(System.currentTimeMillis()-begintime));
////			GTDocumentRevision lastOrXXVersion = documentService.getLastestRevision(strDocumentId);
//			DMSDocumentRevision lastOrXXVersion = getDMSDocumentAPI().getLastRevision(Long.parseLong(strDocumentId));
//
////			System.out.println("==========="+(System.currentTimeMillis()-begintime));
//			
//			DMSDocument document = lastOrXXVersion.getDocument();
//			vo.setState(lastOrXXVersion.getState());
//			vo.setStateCode(lastOrXXVersion.getStateCode());
////			System.out.println("==========="+(System.currentTimeMillis()-begintime));
////			Long verId = index.getGtDocumentVersionId();
////			List<GTDocumentRevision> versionList = documentService.getAllRevisions(strDocumentId);
////			for (GTDocumentRevision gtDocumentVersion : versionList) {
////				if(Long.valueOf(gtDocumentVersion.getId()) == verId.longValue()){
////					lastOrXXVersion = gtDocumentVersion;
////				}
////			}
////			if(!uid.equals("") && lastOrXXVersion != null){				
////				String documentURL = documentService.getDocumentVersionDownloadLink(lastOrXXVersion,uid,sessionId);
////				vo.setDocumentPath(documentURL);
////			} 
//			try{
//				String documentURL = "<a href='"+Struts2Utils.getRequest().getContextPath()+"/common/document/dms!downloadLastVersion.action?documentId="+document.getId()+"'>"+document.getName()+"</a>";
//				vo.setDocumentPath(documentURL);
//			}catch (Exception e) {
//				//TODO webservice 调用导致取不到uid
//			}			
//			vo.setShortName(document.getName());
//			
//			if(lastOrXXVersion != null){	
//				vo.setUploadBy(lastOrXXVersion.getCreateUser());
//				vo.setUploadDate(lastOrXXVersion.getCreateDate());
//				vo.setVersion(lastOrXXVersion.getRevision());
//			}
//			vo.setGtDocumentId(documentId);
//			vos.add(vo);
//		}
//		return vos;
//	}
//	
//	public List<GTDocumentVO> getAllDocumentByProgId(Long progId,String filter){
////		long begin = System.currentTimeMillis();
////		long allBegin = begin;
//		
//		//项目下的所有附件
//		List<GTDocumentVO> allDocuments = new ArrayList<GTDocumentVO>();
//		
//		//查询所有上传到GTDocument的文件,包括项目关键信息,阶段关键信息,门径检查项,门径交付物,过程关键信息
//		List<GTDocumentVO> gtDocuments = getAllGTDocumentByProgId(progId);
//		
////		System.out.println("Time index:"+(System.currentTimeMillis()-begin));
////		begin = System.currentTimeMillis();
//		
////		Map<String, GTWorkflowMethodology> wfDefines = gtIssueManager.getIssueWfDefines();
////		
////		//查询问题流程中上传的附件
////		List<GTDocumentVO> issuesWorkflowAttaches = new ArrayList<GTDocumentVO>();
////		List<Issue> issuesList = gtIssueManager.findAllGTIssueByProid(progId);
////		for (Issue tmpIssue : issuesList) {
////			issuesWorkflowAttaches.addAll(gtIssueManager.getWorkflowAttaches(tmpIssue.getId(),Struts2Utils.getRequest().getSession().getAttribute("aws-sid").toString(), filter,wfDefines));		
////		}
////		System.out.println("Time 问题管理 总用时:"+(System.currentTimeMillis()-begin));
////		begin = System.currentTimeMillis();
//		
//		//查询变更流程中上传的附件
////		long beginIssue = System.currentTimeMillis();
//		
////		GTWorkflowMethodology cmWfDefine = gtChangeManagemnetManager.getCmWfDefine();
////		
////		List<GTDocumentVO> cmWorkflowAttaches = new ArrayList<GTDocumentVO>();
////		List<GTChangeManagement> cmList = gtChangeManagemnetManager.findAllGTChangeManagementByProgIdNoAppendInfo(progId);
//////		System.out.println("Time 变更管理 Query 总用时:"+(System.currentTimeMillis()-beginIssue));
//////		beginIssue = System.currentTimeMillis();
////		for (GTChangeManagement tmpCm : cmList) {
////			cmWorkflowAttaches.addAll(gtChangeManagemnetManager.getWorkflowAttaches(tmpCm.getId(),Struts2Utils.getRequest().getSession().getAttribute("aws-sid").toString(), filter, cmWfDefine));		
////		}
//////		System.out.println("Time 变更管理 loop 总用时:"+(System.currentTimeMillis()-beginIssue));
////		
////		//合并所有文件
////		allDocuments.addAll(gtDocuments);
////		allDocuments.addAll(issuesWorkflowAttaches);
////		allDocuments.addAll(cmWorkflowAttaches);
//////		System.out.println("Time 变更管理 总用时:"+(System.currentTimeMillis()-begin));
//////		begin = System.currentTimeMillis();
////		//根据过滤条件过滤
////		List<GTDocumentVO> filterDocuments = new ArrayList<GTDocumentVO>();
////		for (GTDocumentVO gtDocumentVO : allDocuments) {
////			if(gtDocumentVO.getShortName().indexOf(filter) >= 0){
////				filterDocuments.add(gtDocumentVO);
////			}
////		}
////		allDocuments.clear();
////		System.out.println("Time5:"+(System.currentTimeMillis()-begin));
////		System.out.println("总用时:"+(System.currentTimeMillis()-allBegin));		
//		return null;
//	}
//
//
//	/**
//	 * @param fileType
//	 * @param valueOf
//	 * @param documentId
//	 * @return
//	 */
//	public PMDocumentIndex getDocumentIndex(String sourceType, String sourceId, long documentId) {
//		List<PMDocumentIndex> docIndexs = gtDocumentIndexDao.find(new Criterion[]{Restrictions.eq("sourceType", sourceType),Restrictions.eq("sourceId", sourceId),Restrictions.eq("gtDocumentId", documentId)});
//		if(docIndexs.size() > 0 ){
//			return docIndexs.get(0);
//		}else{			
//			return null;
//		}
//	}
//
//
//	/**
//	 * 查询文件版本来源信息
//	 * @param versionId
//	 * @return GTDocumentIndex
//	 */
//	public PMDocumentIndex findGTDocumentIndexByVersionId(Long versionId) {
//		List<PMDocumentIndex> docIndexs = gtDocumentIndexDao.findBy("gtDocumentVersionId", versionId);
//		if(docIndexs != null && docIndexs.size() > 0){
//			return docIndexs.get(0);
//		}else{
//			throw new RuntimeException("文件来源信息丢失!");
//		}
//		
//	}
//	
//	String[] PDMS_SOURCETYPES = new String[]{
//			SOURCETYPE_GATECHECKITEMDELIVERABLE,
//			SOURCETYPE_BO,
//	};
//	
//	
//	private String getApplicationCodeBySourceType(String sourceType){
//		for(int i=0;i<PDMS_SOURCETYPES.length;i++){
//			if(sourceType.equals(PDMS_SOURCETYPES[i])){
//				return SYSTEM_CODE_PDMS;
//			}
//		}
//		return SYSTEM_CODE_DEFAULT;
//		
//	}
//
//	/**
//	 * 根据项目ID获得保存默认的文件夹ID
//	 * @param progId
//	 * @return
//	 */
//	private String getDefaultFolderId(String sourceType,String sourceId) {
//		
//		String applicationCode = getApplicationCodeBySourceType(sourceType);
//
//		Long applicationFolderId = null;
//		String applicationFolderName = "$"+applicationCode;
//		Long folderId = null;
//		String folderName=sourceType+"_"+sourceId;
//		
//		List<DMSFolder> childFolders = getDMSFolderAPI().getChildFolderList(null);
//		for(DMSFolder folder:childFolders){
//			if(folder.getName().equals(applicationFolderName)){
//				applicationFolderId = folder.getId();
//				break;
//			}
//		}
//		if(applicationFolderId == null){
//			DMSFolder folder = new DMSFolder();
//			folder.setName(applicationFolderName);
//			folder.setCreateUser(getCurentUserId());
//			folder.setCreateDate(new Date());
//			applicationFolderId = getDMSFolderAPI().saveFolder(folder);
//		}
//		
//		childFolders = getDMSFolderAPI().getChildFolderList(applicationFolderId);
//		for(DMSFolder folder:childFolders){
//			if(folder.getName().equals(folderName)){
//				folderId = folder.getId();
//				break;
//			}
//		}
//		if(folderId == null){
//			DMSFolder folder = new DMSFolder();
//			folder.setName(folderName);
//			folder.setParentId(applicationFolderId);
//			folder.setCreateUser(getCurentUserId());
//			folder.setCreateDate(new Date());
//			folderId = getDMSFolderAPI().saveFolder(folder);
//			
//		}
//		
//		return ""+folderId;
//	}
//	
//
//	public PMDocumentIndex getGTDocumentIndex(String id) {
//		PMDocumentIndex gtDocumentIndex = gtDocumentIndexDao.get(id);
//			return gtDocumentIndex;
//	}
//	
//	public void saveGTDocumentIndex(PMDocumentIndex gtDocumentIndex) {
//		gtDocumentIndexDao.save(gtDocumentIndex);
//	}
//	
//	public List<Map<String, Object>> getProgramDocumentList(String programId,String query,String userid,int pagenum){
//		List<Object> parameters = new ArrayList<Object>();
//		int fromNum = (pagenum-1)*50+1;
//		int toNum = pagenum * 50;
//		parameters.add(programId);
//		String sql = "";
//		if(org.apache.commons.lang.StringUtils.isEmpty(query)){
//			sql = "SELECT * FROM (SELECT ROW_NUMBER() Over(ORDER BY LATEST_UPLOAD_DATE) AS ROW_NUM, *,dbo.[fnGetUserObjectFunctions]('DOCUMENT',DOCUMENT_ID,'"+userid+"') AS FUNCTIONS FROM vProgramDocumentList where PROGRAM_ID=? ) AS T WHERE ROW_NUM BETWEEN "+fromNum+"  AND "+toNum+"";
//		}else{
//			sql = "SELECT * FROM (SELECT ROW_NUMBER() Over(ORDER BY LATEST_UPLOAD_DATE) AS ROW_NUM,*,dbo.[fnGetUserObjectFunctions]('DOCUMENT',DOCUMENT_ID,'"+userid+"') AS FUNCTIONS FROM vProgramDocumentList where PROGRAM_ID=? and (DOCUMENT_NAME like ? or STAGE_NAME like ? or DOCUMENT_OWNER like ?)) AS T WHERE ROW_NUM BETWEEN "+fromNum+"  AND "+toNum+"";
//			parameters.add("%"+query+"%");
//			parameters.add("%"+query+"%");
//			parameters.add("%"+query+"%");
//		}
//		
//		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,parameters.toArray());
//		for(Map<String, Object> map:list){
//			map.put("LATEST_UPLOAD_DATE", DateUtils.formate((Date)map.get("LATEST_UPLOAD_DATE")));
//			String functions = (String)map.get("FUNCTIONS");
////			boolean isProgramAdmin = gTProgMemberDao.validateProgramAdmin("PROJECT ADMIN", userid, programId);
//			boolean isProgramAdmin = privilegeService.hasProgramPrivilege(userid, programId, PrivilegeService.P0001);
//			if(map.get("DOCUMENT_OWNER").toString().startsWith(userid)){
//				isProgramAdmin = true;
//			}
//			map.put("isProgramAdmin", isProgramAdmin);
//			if(functions != null && functions.indexOf(DMSConstants.FUNCTION_CODE_DOWNLOAD) != -1){
//				map.put("DOCUMENT_NAME", "<a href='/6dPPM_Web/common/document/dms!downloadLastVersion.action?documentId="+map.get("DOCUMENT_ID")+"'>"+map.get("DOCUMENT_NAME")+"</a>");
//			}else{
//				if(!isProgramAdmin){
//					map.put("DOCUMENT_NAME", map.get("DOCUMENT_NAME"));
//				}else{
//					map.put("DOCUMENT_NAME", "<a href='/6dPPM_Web/common/document/dms!downloadLastVersion.action?documentId="+map.get("DOCUMENT_ID")+"'>"+map.get("DOCUMENT_NAME")+"</a>");
//				}
//				
//			}
////			boolean isProgramAdmin = gTProgMemberDao.validateProgramAdmin("PROJECT ADMIN", userid, programId);
//			map.put("isProgramAdmin", isProgramAdmin);
//			
//		}
//		return list;
//	}
//	
//	public int getProgramDocumentCount(Long programId,String query,String userid){
//		List<Object> parameters = new ArrayList<Object>();
//		parameters.add(programId);
//		String sql = "";
//		if(org.apache.commons.lang.StringUtils.isEmpty(query)){
//			sql = "SELECT count(*) FROM vProgramDocumentList where PROGRAM_ID=? ";
//		}else{
//			sql = "SELECT count(*) FROM vProgramDocumentList where PROGRAM_ID=? and (DOCUMENT_NAME like ? or STAGE_NAME like ? or DOCUMENT_OWNER like ?)";
//			parameters.add("%"+query+"%");
//			parameters.add("%"+query+"%");
//			parameters.add("%"+query+"%");
//		}
//		
//		int total = jdbcTemplate.queryForInt(sql,parameters.toArray());
//		return total;
//	}
//	
//	public List<Map<String, Object>> getProgramDocumentListByTagId(String programId,String userid,Long tagId){
//		String sql = "SELECT T1.*,dbo.[fnGetUserObjectFunctions]('DOCUMENT',T1.DOCUMENT_ID,'" + userid
//				+ "') AS FUNCTIONS FROM vProgramDocumentList T1,GT_PROGRAM_DOCUMENT_TAG T2 where T1.DOCUMENT_ID = T2.DOCUMENT_ID and t2.TAG_LIST_ID="+tagId+" and PROGRAM_ID = " + programId;
//		
//		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//		for(Map<String, Object> map:list){
//			map.put("LATEST_UPLOAD_DATE", DateUtils.formate((Date)map.get("LATEST_UPLOAD_DATE")));
//			String functions = (String)map.get("FUNCTIONS");
////			boolean isProgramAdmin = gTProgMemberDao.validateProgramAdmin("PROJECT ADMIN", userid, programId);
//			boolean isProgramAdmin = privilegeService.hasProgramPrivilege(userid, programId, PrivilegeService.P0001);
//			if(map.get("DOCUMENT_OWNER").toString().startsWith(userid)){
//				isProgramAdmin = true;
//			}
//			map.put("isProgramAdmin", isProgramAdmin);
//			if(functions != null && functions.indexOf(DMSConstants.FUNCTION_CODE_DOWNLOAD) != -1){
//				map.put("DOCUMENT_NAME", "<a href='/6dPPM_Web/common/document/dms!downloadLastVersion.action?documentId="+map.get("DOCUMENT_ID")+"'>"+map.get("DOCUMENT_NAME")+"</a>");
//			}else{
//				if(!isProgramAdmin){
//					map.put("DOCUMENT_NAME", map.get("DOCUMENT_NAME"));
//				}else{
//					map.put("DOCUMENT_NAME", "<a href='/6dPPM_Web/common/document/dms!downloadLastVersion.action?documentId="+map.get("DOCUMENT_ID")+"'>"+map.get("DOCUMENT_NAME")+"</a>");
//				}
//				
//			}
////			boolean isProgramAdmin = gTProgMemberDao.validateProgramAdmin("PROJECT ADMIN", userid, programId);
//			map.put("isProgramAdmin", isProgramAdmin);
//			
//		}
//		return list;
//	}
//	
//	public List<PMDocumentIndex> getDocumentIndexListByTaskId(Long taskId){
//		List<PMDocumentIndex> list = new ArrayList<PMDocumentIndex>();
//		
//		return list;
//	}
//	
//	private String getCurrentUserId(){
//		return SessionData.getLoginUserId();
//	}
//	
}

