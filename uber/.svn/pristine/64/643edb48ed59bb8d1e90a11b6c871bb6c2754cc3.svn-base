package com.gnomon.pdms.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.web.SessionData;
import com.gnomon.dms.api.CheckinRevisionType;
import com.gnomon.dms.api.DMSDocument;
import com.gnomon.dms.api.DMSDocumentAPI;
import com.gnomon.dms.api.DMSDocumentRevision;
import com.gnomon.dms.api.DMSFolder;
import com.gnomon.dms.api.DMSFolderAPI;
import com.gnomon.dms.api.DmsAPIFactory;
import com.gnomon.pdms.common.PDMSCommon;
import com.gnomon.pdms.constant.PDMSConstants;
import com.gnomon.pdms.dao.DocumentIndexDao;
import com.gnomon.pdms.dao.DocumentIndexVMDAO;
import com.gnomon.pdms.entity.DocumentIndexVMEntity;
import com.gnomon.pdms.entity.PMDocumentIndexEntity;

@Service
@Transactional
public class DocumentService {

	public static final String SYSTEM_CODE_PM="PM";
	public static final String SYSTEM_CODE_IMS="IMS";
	public static final String SYSTEM_CODE_DEPT="DEPT";
	public static final String SYSTEM_CODE_DEFAULT="DEFAULT";
	
	String[] PM_SOURCETYPES = new String[]{
			PDMSConstants.SOURCE_TYPE_TASK_DELIVERBLE,
			PDMSConstants.SOURCE_TYPE_TASK_ATTACHMENT,
			PDMSConstants.SOURCE_TYPE_PM_RECORD,
			PDMSConstants.SOURCE_TYPE_PM_SCOPE,
			PDMSConstants.SOURCE_TYPE_PM_ATTACHMENT,
			PDMSConstants.SOURCE_TYPE_PLAN_EVIDENCE,
			PDMSConstants.SOURCE_TYPE_PRJECT_NOTE,
			PDMSConstants.SOURCE_TYPE_CUSTOMIZATION

	};
	
	String[] IMS_SOURCETYPES = new String[]{
			PDMSConstants.SOURCE_TYPE_DES_ATTACHMENT,
			PDMSConstants.SOURCE_TYPE_CAUSE_ATTACHMENT,
			PDMSConstants.SOURCE_TYPE_ACTION_ATTACHMENT,
			PDMSConstants.SOURCE_TYPE_PERMACTION_ATTACHMENT,
			PDMSConstants.SOURCE_TYPE_RESULT_ATTACHMENT,
			PDMSConstants.SOURCE_TYPE_REASON_ATTACHMENT
	};
	
	String[] DEPT_SOURCETYPES = new String[]{
			PDMSConstants.SOURCE_TYPE_DEPARTMENT
	};
	
	@Autowired
	private DocumentIndexVMDAO documentIndexVMDAO;
	
	@Autowired
	private DocumentIndexDao documentIndexDao;

	/**
	 * 文件一览取得
	 */
	public List<DocumentIndexVMEntity> getDocumentInfo(
			String sourceType, String sourceId) {
		String hql = "FROM DocumentIndexVMEntity WHERE sourceType = ? and sourceId = ? ";
		List<DocumentIndexVMEntity> result = this.documentIndexVMDAO.find(
				hql, sourceType, sourceId);
		return result;
	}
	
	/**
	 * 文件信息取得
	 */
	public DocumentIndexVMEntity getDocumentIdxInfo(String documentIdxId) {
		return this.documentIndexVMDAO.findUniqueBy("id", documentIdxId);
	}
	
	/**
	 * 文件上传
	 */
	public void saveGTDocumentIndex(String programId, String sourceType,
			String sourceId, String folderId, String documentName,
			String departmentId, File file,String revisionId)
					throws RuntimeException {
		if(StringUtils.isNotEmpty(revisionId)){
			this.saveRevision(programId, sourceType, sourceId, folderId, documentName, departmentId, file, Long.valueOf(revisionId));
		}else{
			// 上传文件
			String documentId = checkinDocument(
					documentName, sourceType, sourceId, folderId, file);
			// 登录数据
			saveGTDocumentIndex(programId, sourceType, sourceId, documentName,
					departmentId, Long.parseLong(documentId));
		}
		
	}
	
	/**
	 * 上传新版本 
	 */
	public void saveRevision(String programId, String sourceType,
			String sourceId, String folderId, String documentName,
			String departmentId, File file,Long revisionId)
					throws RuntimeException {
		// 上传文件
		String newRevisionId = this.checkinRevision(documentName, file, revisionId);
		// 登录数据
		saveGTDocumentIndex(programId, sourceType, sourceId, documentName,
				departmentId, Long.valueOf(newRevisionId));
	}
	
	/**
	 * 文件删除
	 */
	public void deleteGTDocumentIndex(String id) {
		String loginUserId = SessionData.getLoginUserId();
		PMDocumentIndexEntity gtDocumentIndex = documentIndexDao.get(id);
		String documentId = gtDocumentIndex.getDocumentId();
		String sourceType = gtDocumentIndex.getSourceType();
		String sourceId = gtDocumentIndex.getSourceId();
		//查询该文档是否被其他引用
		List<PMDocumentIndexEntity> docIndexs = documentIndexDao.find(
				new Criterion[]{
					Restrictions.eq("documentId", documentId),
					Restrictions.ne("sourceId", sourceId)
				});
		List<String> versions = new ArrayList<String>();
		for (PMDocumentIndexEntity gtDocumentIndex2 : docIndexs) {
			versions.add(gtDocumentIndex2.getDocumentVersionId());
		}
		if (docIndexs.size() > 0) {
			//有则只删除index记录和对应文档版本
			List<PMDocumentIndexEntity> currDocIndexs = documentIndexDao.find(
					new Criterion[]{
							Restrictions.eq("documentId", documentId),
							Restrictions.eq("sourceType", sourceType),
							Restrictions.eq("sourceId", sourceId)
					});
			for (PMDocumentIndexEntity index : currDocIndexs) {
				if (! versions.contains(index.getDocumentVersionId())) {
					getDMSDocumentAPI().deleteRevision(
							Long.parseLong(index.getDocumentVersionId()), loginUserId);
				}
				//documentIndexDao.delete(index);
				// 物理删除
				index.setDeleteBy(loginUserId);
				index.setDeleteDate(new Date());
				documentIndexDao.save(index);
			}
		} else {
			//否则删除实际的文档
			try{
				getDMSDocumentAPI().deleteDocument(
						Long.parseLong(documentId), loginUserId);
			}catch(Exception e){
				e.printStackTrace();
			}
			documentIndexDao.delete(id);
		}
	}
	
	/**
	 * 检入文档
	 */
	private String checkinDocument(String fileName, String sourceType,
			String sourceId, String folderId, File file) {
		String loginUserId = SessionData.getLoginUserId();
		DMSDocumentRevision documentRevision = new DMSDocumentRevision();
		DMSDocument document = new DMSDocument();
		document.setName(fileName);
		document.setCreateDate(new Date());
		document.setCreateUser(loginUserId);
		if (PDMSCommon.isNull(folderId)) {
			document.setFolderId(Long.parseLong(getDefaultFolderId(sourceType, sourceId)));
		} else {
			document.setFolderId(Long.parseLong(folderId));
		}
		documentRevision.setDocument(document);
		documentRevision.setComment("Upload from DMS Component");
		documentRevision.setCreateUser(loginUserId);
		documentRevision.setCreateDate(new Date());
		documentRevision.setLocalName(fileName);
		documentRevision.setDocument(document);
		Long revisionId = null;
		try {
			revisionId = getDMSDocumentAPI().checkin(
					documentRevision, new FileInputStream(file),
					CheckinRevisionType.MINOR_REVISION,loginUserId);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return "" + revisionId;
	}
	
	
	private String checkinRevision(String fileName,File file,Long revisionId) {
		String loginUserId = SessionData.getLoginUserId();
		DMSDocumentRevision documentRevision = new DMSDocumentRevision();
		DMSDocument document = getDMSDocumentAPI().getRevision(revisionId).getDocument();
		
		documentRevision.setDocument(document);
		documentRevision.setComment("Upload from DMS Component");
		documentRevision.setCreateUser(loginUserId);
		documentRevision.setCreateDate(new Date());
		documentRevision.setLocalName(fileName);
		documentRevision.setDocument(document);
		Long newRevisionId = null;
		try {
			newRevisionId = getDMSDocumentAPI().checkin(
					documentRevision, new FileInputStream(file),
					CheckinRevisionType.MINOR_REVISION,loginUserId);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return "" + newRevisionId;
	}
	
	/**
	 * 保存数据
	 */
	private void saveGTDocumentIndex(String programId, String sourceType,
			String sourceID, String documentName, String departmentId,
			Long revisionId) throws RuntimeException {
		String loginUserId = SessionData.getLoginUserId();
		try {
			DMSDocumentRevision dmsDocumentRevision =
					getDMSDocumentAPI().getRevision(revisionId);
			String documentId =
					PDMSCommon.nvl(dmsDocumentRevision.getDocument().getId());
			//更新“是否最后记录”的标志为false
			PMDocumentIndexEntity oldDocIndex = documentIndexDao.findUnique(new Criterion[] {
					Restrictions.eq("sourceType", sourceType), 
					Restrictions.eq("sourceId", sourceID),
					Restrictions.eq("documentId", documentId),
					Restrictions.eq("isLast", PDMSConstants.STATUS_Y) });
			if (oldDocIndex != null) {				
				oldDocIndex.setIsLast(PDMSConstants.STATUS_N);
				oldDocIndex.setUpdateBy(loginUserId);
				oldDocIndex.setUpdateDate(new Date());
				documentIndexDao.save(oldDocIndex);
			}
			//插入一条新记录
			PMDocumentIndexEntity lastDocIndex = new PMDocumentIndexEntity();
			lastDocIndex.setId(com.gnomon.pdms.common.PDMSCommon.generateUUID());
			lastDocIndex.setProgramId(programId);
			lastDocIndex.setSourceType(sourceType);
			lastDocIndex.setSourceId(sourceID);
			lastDocIndex.setDocumentId(documentId);
			DMSDocumentRevision lastRevision =
					getDMSDocumentAPI().getLastRevision(Long.parseLong(documentId));
			lastDocIndex.setDocumentVersionId(PDMSCommon.nvl(lastRevision.getId()));
			lastDocIndex.setIsLast(PDMSConstants.STATUS_Y);
			lastDocIndex.setDocumentName(documentName);
			lastDocIndex.setCreateBy(loginUserId);
			lastDocIndex.setCreateDate(new Date());
			lastDocIndex.setDepartmentId(departmentId);
			this.documentIndexDao.save(lastDocIndex);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据项目ID获得保存默认的文件夹ID
	 */
	private String getDefaultFolderId(String sourceType,String sourceId) {
		String loginUserId = SessionData.getLoginUserId();
		String applicationCode = getApplicationCodeBySourceType(sourceType);

		Long applicationFolderId = null;
		String applicationFolderName = "$"+applicationCode;
		Long folderId = null;
		String folderName = sourceType+"_"+sourceId;
		
		List<DMSFolder> childFolders = getDMSFolderAPI().getChildFolderList(null);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(applicationFolderName)){
				applicationFolderId = folder.getId();
				break;
			}
		}
		if(applicationFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(applicationFolderName);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			applicationFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		childFolders = getDMSFolderAPI().getChildFolderList(applicationFolderId);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(folderName)){
				folderId = folder.getId();
				break;
			}
		}
		if(folderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(folderName);
			folder.setParentId(applicationFolderId);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			folderId = getDMSFolderAPI().saveFolder(folder);
			
		}
		
		return "" + folderId;
	}
	
	/**
	 * 根据项目ID获得用户自定义的文件夹ID
	 */
	public Long getCusomizeFolderId(String programId) {
		String loginUserId = SessionData.getLoginUserId();
		
		String applicationCode = getApplicationCodeBySourceType(
				PDMSConstants.SOURCE_TYPE_CUSTOMIZATION);
		String applicationFolderName = "$"+applicationCode;

		Long applicationFolderId = null;
		List<DMSFolder> childFolders = getDMSFolderAPI().getChildFolderList(null);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(applicationFolderName)){
				applicationFolderId = folder.getId();
				break;
			}
		}
		if(applicationFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(applicationFolderName);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			applicationFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		Long customizeFolderId = null;
		childFolders = getDMSFolderAPI().getChildFolderList(applicationFolderId);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(PDMSConstants.SOURCE_TYPE_CUSTOMIZATION)){
				customizeFolderId = folder.getId();
				break;
			}
		}
		if(customizeFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(PDMSConstants.SOURCE_TYPE_CUSTOMIZATION);
			folder.setParentId(applicationFolderId);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			customizeFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		Long programFolderId = null;
		childFolders = getDMSFolderAPI().getChildFolderList(customizeFolderId);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(programId)){
				programFolderId = folder.getId();
				break;
			}
		}
		if(programFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(programId);
			folder.setParentId(customizeFolderId);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			programFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		return programFolderId;
	}
	
	/**
	 * 根据项目ID获得Note文件夹ID
	 */
	public Long getProjectNoteFolderId(String programId) {
		String loginUserId = SessionData.getLoginUserId();
		
		String applicationCode = getApplicationCodeBySourceType(
				PDMSConstants.SOURCE_TYPE_PRJECT_NOTE);
		String applicationFolderName = "$"+applicationCode;

		Long applicationFolderId = null;
		List<DMSFolder> childFolders = getDMSFolderAPI().getChildFolderList(null);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(applicationFolderName)){
				applicationFolderId = folder.getId();
				break;
			}
		}
		if(applicationFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(applicationFolderName);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			applicationFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		Long projectNoteFolderId = null;
		childFolders = getDMSFolderAPI().getChildFolderList(applicationFolderId);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(PDMSConstants.SOURCE_TYPE_PRJECT_NOTE)){
				projectNoteFolderId = folder.getId();
				break;
			}
		}
		if(projectNoteFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(PDMSConstants.SOURCE_TYPE_PRJECT_NOTE);
			folder.setParentId(applicationFolderId);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			projectNoteFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		Long programFolderId = null;
		childFolders = getDMSFolderAPI().getChildFolderList(projectNoteFolderId);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(programId)){
				programFolderId = folder.getId();
				break;
			}
		}
		if(programFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(programId);
			folder.setParentId(projectNoteFolderId);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			programFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		return programFolderId;
	}
	
	/**
	 * 根据部门ID获得部门文件夹ID
	 */
	public Long getDepartmentFolderId(String departmentId) {
		String loginUserId = SessionData.getLoginUserId();

		String applicationCode = getApplicationCodeBySourceType(
				PDMSConstants.SOURCE_TYPE_DEPARTMENT);
		String applicationFolderName = "$"+applicationCode;

		Long applicationFolderId = null;
		List<DMSFolder> childFolders = getDMSFolderAPI().getChildFolderList(null);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(applicationFolderName)){
				applicationFolderId = folder.getId();
				break;
			}
		}
		if(applicationFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(applicationFolderName);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			applicationFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		Long deptDocFolderId = null;
		childFolders = getDMSFolderAPI().getChildFolderList(applicationFolderId);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(PDMSConstants.SOURCE_TYPE_DEPARTMENT)){
				deptDocFolderId = folder.getId();
				break;
			}
		}
		if(deptDocFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(PDMSConstants.SOURCE_TYPE_DEPARTMENT);
			folder.setParentId(applicationFolderId);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			deptDocFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		Long departmentFolderId = null;
		childFolders = getDMSFolderAPI().getChildFolderList(deptDocFolderId);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(departmentId)){
				departmentFolderId = folder.getId();
				break;
			}
		}
		if(departmentFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(departmentId);
			folder.setParentId(applicationFolderId);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			departmentFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		return departmentFolderId;
	}
	
	/**
	 * 新建文件夹
	 */
	public Long createFolder(Long parentId, String folderName) {
		String loginUserId = SessionData.getLoginUserId();
		
		// 新建文件夹
		DMSFolder folder = new DMSFolder();
		folder.setName(folderName);
		folder.setParentId(parentId);
		folder.setCreateUser(loginUserId);
		folder.setCreateDate(new Date());
		return getDMSFolderAPI().saveFolder(folder);
	}
	
	/**
	 * 删除文件夹
	 */
	public void deleteFolder(Long folderId) throws Exception {
		// 查询子文件夹
		List<DMSFolder> childFolders = getDMSFolderAPI().getChildFolderList(folderId);
		if (childFolders.size() > 0) {
			throw new Exception("该文件夹存在子文件夹，不允许删除!");
		}
		// 查询文件夹下的文件
		String hql = "from PMDocumentIndexEntity where sourceId = ? and deleteBy is null";
		List<PMDocumentIndexEntity> documentList = this.documentIndexDao.find(
				hql, String.valueOf(folderId));
		if (documentList.size() > 0) {
			throw new Exception("该文件夹中存在文件，不允许删除!");
		}
		// 删除文件夹
		getDMSFolderAPI().deleteFolder(folderId);
	}
	
	/**
	 * 修改文件夹
	 */
	public void updateFolder(Long folderId, String folderName) {
		// 修改文件夹名称
		DMSFolder folder = getDMSFolderAPI().getFolderById(folderId);
		folder.setName(folderName);
		getDMSFolderAPI().updateFolder(folder);
	}
	
	/**
	 * 取得文件夹信息
	 */
	public DMSFolder getFolderById(Long folderId) {
		DMSFolder folder = getDMSFolderAPI().getFolderById(folderId);
		if (folder != null) {
			return folder;
		}
		return new DMSFolder();
	}
	
	private String getExtFolderId() {
		String loginUserId = SessionData.getLoginUserId();

		Long applicationFolderId = null;
		String applicationFolderName = "$ext";
		
		List<DMSFolder> childFolders = getDMSFolderAPI().getChildFolderList(null);
		for(DMSFolder folder:childFolders){
			if(folder.getName().equals(applicationFolderName)){
				applicationFolderId = folder.getId();
				break;
			}
		}
		if(applicationFolderId == null){
			DMSFolder folder = new DMSFolder();
			folder.setName(applicationFolderName);
			folder.setCreateUser(loginUserId);
			folder.setCreateDate(new Date());
			applicationFolderId = getDMSFolderAPI().saveFolder(folder);
		}
		
		return "" + applicationFolderId;
	}
	
	/**
	 * 根目录名称取得
	 */
	private String getApplicationCodeBySourceType(String sourceType){
		for (int i = 0; i < PM_SOURCETYPES.length; i++){
			if (sourceType.equals(PM_SOURCETYPES[i])) {
				return SYSTEM_CODE_PM;
			}
		}
		for (int i = 0; i < IMS_SOURCETYPES.length; i++){
			if (sourceType.equals(IMS_SOURCETYPES[i])) {
				return SYSTEM_CODE_IMS;
			}
		}
		for (int i = 0; i < DEPT_SOURCETYPES.length; i++){
			if (sourceType.equals(DEPT_SOURCETYPES[i])) {
				return SYSTEM_CODE_DEPT;
			}
		}
		return SYSTEM_CODE_DEFAULT;
	}
	
	/**
	 * 取得DMSDocumentAPI
	 */
	private DMSDocumentAPI getDMSDocumentAPI(){
		return DmsAPIFactory.getDocumentAPI();
	}
	
	/**
	 * 取得DMSFolderAPI
	 */
	private DMSFolderAPI getDMSFolderAPI(){
		return DmsAPIFactory.getFolderAPI();
	}
	
	public String checkinExtDocument(String fileName, File file) {
		String loginUserId = SessionData.getLoginUserId();
		DMSDocumentRevision documentRevision = new DMSDocumentRevision();
		DMSDocument document = new DMSDocument();
		document.setName(fileName);
		document.setCreateDate(new Date());
		document.setCreateUser(loginUserId);
		document.setFolderId(Long.parseLong(getExtFolderId()));
		documentRevision.setDocument(document);
		documentRevision.setComment("Upload from DMS Component");
		documentRevision.setCreateUser(loginUserId);
		documentRevision.setCreateDate(new Date());
		documentRevision.setLocalName(fileName);
		documentRevision.setDocument(document);
		Long revisionId = null;
		try {
			revisionId = getDMSDocumentAPI().checkin(
					documentRevision, new FileInputStream(file),
					CheckinRevisionType.MINOR_REVISION,loginUserId);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return "" + revisionId;
	}
	
	public DMSDocumentRevision getDocument(Long revisionId){
		return getDMSDocumentAPI().getRevision(revisionId);
	}
	
	public List<DMSDocumentRevision> getHistoryRevisionList(Long documentId){
		return getDMSDocumentAPI().getAllRevisions(documentId);
	}
}
