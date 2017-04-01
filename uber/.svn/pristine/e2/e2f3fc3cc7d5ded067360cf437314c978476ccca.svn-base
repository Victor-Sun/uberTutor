package com.gnomon.pdms.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.entity.GTIssueVO;
import com.gnomon.pdms.entity.ImsIssueVerificationVO;

/**
 * 调用PKG_IMS包中的存储过程
 */
@Service
@Transactional
public class PkgImsDBProcedureServcie extends AbstractDBProcedureService {
	/**
	 * 更新问题信息
	 */
	public Map<String, Object> updateIssue(final GTIssueVO gtIssueVO) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_IMS.UPDATE_IMS("
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, gtIssueVO.getId());
				statement.setString(2, gtIssueVO.getCreateBy());
				statement.setString(3, gtIssueVO.getAction());
				statement.setString(4, gtIssueVO.getOwnerComment());
				statement.setString(5, gtIssueVO.getReturnComment());
				statement.setString(6, gtIssueVO.getTitle());
//				statement.setString(7, gtIssueVO.getSeverityCode());
				statement.setString(7, gtIssueVO.getCode());
				statement.setString(8, gtIssueVO.getProjectId());
				statement.setString(9, gtIssueVO.getStageId());
				statement.setString(10, gtIssueVO.getIssueSourceId());
				statement.setString(11, gtIssueVO.getIssueTypeId());
				statement.setString(12, gtIssueVO.getIssueLevelCode());
				statement.setString(13, gtIssueVO.getIssueStatusCode());
				statement.setString(14, gtIssueVO.getDescription());
				statement.setString(15, gtIssueVO.getSubmitUser());
				statement.setString(16, gtIssueVO.getSubmitDeptId());
				if(gtIssueVO.getOpenDate() != null){
					statement.setDate(17, new java.sql.Date(gtIssueVO.getOpenDate().getTime()));
				}else{
					statement.setDate(17, null);
				}
				statement.setString(18, gtIssueVO.getOwner());
				statement.setString(19, gtIssueVO.getLineMgr());
				statement.setString(20, gtIssueVO.getDeptId());
				statement.setString(21, gtIssueVO.getRootCause());
				if(gtIssueVO.getRootCausePlanDate() != null){
					statement.setDate(22,  new java.sql.Date(gtIssueVO.getRootCausePlanDate().getTime()));
				}else{
					statement.setDate(22,  null);
				}
				if(gtIssueVO.getRootCauseActualDate() != null){
					statement.setDate(23, new java.sql.Date(gtIssueVO.getRootCauseActualDate().getTime()));
				}else{
					statement.setDate(23,  null);
				}
				
				statement.setString(24, gtIssueVO.getTempAction());
				statement.setString(25, gtIssueVO.getPermAction());
				if(gtIssueVO.getCloseReqActualDate() != null){
					statement.setDate(26, new java.sql.Date(gtIssueVO.getCloseReqActualDate().getTime()));
				}else{
					statement.setDate(26,  null);
				}
				
				statement.setString(27, gtIssueVO.getCloseNote());
				statement.setString(28, gtIssueVO.getMemo());
				if(gtIssueVO.getLineMgrApprDueDate() != null){
					statement.setDate(29, new java.sql.Date(gtIssueVO.getLineMgrApprDueDate().getTime()));
				}else{
					statement.setDate(29, null);
				}
				if(gtIssueVO.getSecMgrApprDueDate() != null){
					statement.setDate(30, new java.sql.Date(gtIssueVO.getSecMgrApprDueDate().getTime()));
				}else{
					statement.setDate(30, null);
				}
				
				statement.setString(31, gtIssueVO.getCreateBy());
				if(gtIssueVO.getCreateDate() != null){
					statement.setDate(32, new java.sql.Date(gtIssueVO.getCreateDate().getTime()));
				}else{
					statement.setDate(32, null);
				}
				statement.setString(33, gtIssueVO.getUpdateBy());
				if(gtIssueVO.getUpdateDate() != null){
					statement.setDate(34, new java.sql.Date(gtIssueVO.getUpdateDate().getTime()));
				}else{
					statement.setDate(34, null);
				}
				statement.setString(35, gtIssueVO.getIssueLifecycleCode());
				statement.setString(36, gtIssueVO.getSubProjectId());
				if(gtIssueVO.getOccurrenceDate() != null){
					statement.setDate(37, new java.sql.Date(gtIssueVO.getOccurrenceDate().getTime()));
				}else{
					statement.setDate(37, null);
				}
				statement.setString(38, gtIssueVO.getOccurrenceSite());
				statement.setString(39, gtIssueVO.getTestTypeId());
				statement.setString(40, gtIssueVO.getSampleNumber());
				statement.setString(41, gtIssueVO.getTroublePartMileage());
				statement.setString(42, gtIssueVO.getTestProgress());
				statement.setString(43, gtIssueVO.getDisposalMeasures());
				statement.setString(44, gtIssueVO.getFirstCauseAnalysis());
				statement.setString(45, gtIssueVO.getIsIssue());
				statement.setString(46, gtIssueVO.getIsIssueReason());
				statement.setString(47, gtIssueVO.getExpectedStageId());
				if(gtIssueVO.getExpectedDate() != null){
					statement.setDate(48, new java.sql.Date(gtIssueVO.getExpectedDate().getTime()));
				}else{
					statement.setDate(48, null);
				}
				statement.setString(49, gtIssueVO.getIsAction());
				statement.setString(50, gtIssueVO.getDeleteBy());
				if(gtIssueVO.getDeleteDate() != null){
					statement.setDate(51, new java.sql.Date(gtIssueVO.getDeleteDate().getTime()));
				}else{
					statement.setDate(51, null);
				}
				statement.setString(52, gtIssueVO.getRiskLevelCode());
				if(gtIssueVO.getPublishDate() != null){
					statement.setDate(53, new java.sql.Date(gtIssueVO.getPublishDate().getTime()));
				}else{
					statement.setDate(53, null);
				}
				statement.setString(54, gtIssueVO.getIssueNatureId());
				if(gtIssueVO.getReconfirmDate() != null){
					statement.setDate(55, new java.sql.Date(gtIssueVO.getReconfirmDate().getTime()));
				}else{
					statement.setDate(55, null);
				}
				if(gtIssueVO.getPlanClosedDate() != null){
					statement.setDate(56, new java.sql.Date(gtIssueVO.getPlanClosedDate().getTime()));
				}else{
					statement.setDate(56, null);
				}
				statement.setString(57, gtIssueVO.getIsUpdatedDfmea());
				statement.setString(58, gtIssueVO.getIsUpdatedTc());
				statement.setString(59, gtIssueVO.getEcoNo());
				statement.setString(60, gtIssueVO.getIsDp());
				statement.setString(61, gtIssueVO.getDfmeaNo());
				statement.setString(62, gtIssueVO.getTechnicalStandardNo());
				statement.setString(63, gtIssueVO.getOtherMeasures());
				statement.setString(64, gtIssueVO.getEffectConfirmation());
				statement.setString(65, gtIssueVO.getReason());
				statement.setString(66, null);
				if(gtIssueVO.getConfirmStartDate() != null){
					statement.setDate(67, new java.sql.Date(gtIssueVO.getConfirmStartDate().getTime()));
				}else{
					statement.setDate(67, null);
				}
				if(gtIssueVO.getConfirmFinishDate() != null){
					statement.setDate(68, new java.sql.Date(gtIssueVO.getConfirmFinishDate().getTime()));
				}else{
					statement.setDate(68, null);
				}
				if(gtIssueVO.getRootCauseStartDate() != null){
					statement.setDate(69, new java.sql.Date(gtIssueVO.getRootCauseStartDate().getTime()));
				}else{
					statement.setDate(69, null);
				}
				if(gtIssueVO.getRootCauseFinishDate() != null){
					statement.setDate(70, new java.sql.Date(gtIssueVO.getRootCauseFinishDate().getTime()));
				}else{
					statement.setDate(70, null);
				}
				if(gtIssueVO.getVerificationStartDate() != null){
					statement.setDate(71, new java.sql.Date(gtIssueVO.getVerificationStartDate().getTime()));
				}else{
					statement.setDate(71, null);
				}
				if(gtIssueVO.getVerificationFinishDate() != null){
					statement.setDate(72, new java.sql.Date(gtIssueVO.getVerificationFinishDate().getTime()));
				}else{
					statement.setDate(72, null);
				}
				statement.setString(73, gtIssueVO.getDeptMgr());
				statement.setString(74, gtIssueVO.getQm());
				statement.setString(75, gtIssueVO.getFm());
				if(gtIssueVO.getEffectConfirmationDate() != null){
					statement.setDate(76, new java.sql.Date(gtIssueVO.getEffectConfirmationDate().getTime()));
				}else{
					statement.setDate(76, null);
				}
				statement.setString(77, gtIssueVO.getNoActionReason());
				statement.setString(78, gtIssueVO.getIsOnlineStep_5());
				statement.setString(79, gtIssueVO.getIsOnlineStep_12());
				statement.registerOutParameter(80, Types.VARCHAR);
				statement.registerOutParameter(81, Types.VARCHAR);
				statement.registerOutParameter(1, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(80);
				returnMsg = statement.getString(81);
				returnMap.put("id", statement.getString(1));
			}
		});
	}
	
	/**
	 * 挂牌
	 */
	public Map<String, Object> listIssue(final String issueId,
			final String userId, final String comment) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_IMS.LIST_ISSUE(?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, issueId);
				statement.setString(2, userId);
				statement.setString(3, comment);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.executeUpdate();

				//设置错误代码和错误信息
				returnCode = statement.getString(4);
				returnMsg = statement.getString(5);
			}});
	}
	
	/**
	 * 摘牌
	 */
	public Map<String, Object> delistIssue(final String issueId, final String userId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_IMS.DELIST_ISSUE(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, issueId);
				statement.setString(2, userId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();

				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
	/**
	 * 取消挂牌
	 */
	public Map<String, Object> undoListIssue(final String issueId, final String userId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_IMS.UNDO_LIST_ISSUE(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, issueId);
				statement.setString(2, userId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();

				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}});
	}
	
	/**
	 * 更新问题验证信息
	 */
	public Map<String, Object> updateImsVerification(final ImsIssueVerificationVO imsIssueVerificationVO) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_IMS.UPDATE_IMS_VERIFICATION("
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, imsIssueVerificationVO.getId());
				statement.setString(2, imsIssueVerificationVO.getCreateBy());
				statement.setString(3, imsIssueVerificationVO.getAction());
				statement.setString(4, imsIssueVerificationVO.getOwnerComment());
				statement.setString(5, imsIssueVerificationVO.getReturnComment());
				statement.setString(6, imsIssueVerificationVO.getIssueId());
				statement.setString(7, imsIssueVerificationVO.getVerification());
				statement.setString(8, imsIssueVerificationVO.getPassRequest());
				statement.setString(9, imsIssueVerificationVO.getImplementVehicle());
				statement.setString(10, imsIssueVerificationVO.getDeptId());
				statement.setString(11, imsIssueVerificationVO.getVerificationEngineer());
				statement.setString(12, imsIssueVerificationVO.getVerificationResult());
				statement.setString(13, imsIssueVerificationVO.getEffectConfirmation());
				statement.setString(14, imsIssueVerificationVO.getIsUpdatedDfmea());
				statement.setString(15, imsIssueVerificationVO.getIsUpdatedTc());
				statement.setString(16, imsIssueVerificationVO.getEcoNo());
				statement.setString(17, imsIssueVerificationVO.getCreateBy());
				if(imsIssueVerificationVO.getCreateDate() != null){
					statement.setDate(18, new java.sql.Date(imsIssueVerificationVO.getCreateDate().getTime()));
				}else{
					statement.setDate(18, null);
				}
				statement.setString(19, imsIssueVerificationVO.getUpdateBy());
				if(imsIssueVerificationVO.getUpdateDate() != null){
					statement.setDate(20, new java.sql.Date(imsIssueVerificationVO.getUpdateDate().getTime()));
				}else{
					statement.setDate(20, null);
				}
				statement.setString(21, imsIssueVerificationVO.getReason());
				statement.setString(22, imsIssueVerificationVO.getSeq().toString());
				if(imsIssueVerificationVO.getTaskId() != null){
					statement.setString(23, imsIssueVerificationVO.getTaskId().toString());
				}else{
					statement.setString(23, null);
				}
				
				statement.setString(24, imsIssueVerificationVO.getSteps());
				
				
				statement.setString(25, imsIssueVerificationVO.getvFm());
				statement.setString(26, imsIssueVerificationVO.getvLineMgr());
				statement.setString(27, imsIssueVerificationVO.getStatusCode());
				if(imsIssueVerificationVO.getActualFinishDate() != null){
					statement.setDate(28, new java.sql.Date(imsIssueVerificationVO.getActualFinishDate().getTime()));
				}else{
					statement.setDate(28,  null);
				}
				
				statement.registerOutParameter(29, Types.VARCHAR);
				statement.registerOutParameter(30, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(29);
				returnMsg = statement.getString(30);
			}
		});
	}
	
	/**
	 * 新增成员
	 */
	public Map<String, Object> addMember(final String issueId,
			final String memberId) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_IMS.ADD_MEMBER(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, issueId);
				statement.setString(2, memberId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}
		});
	}
	
	/**
	 * 合并问题
	 */
	public Map<String, Object> mergeIssue(final String fromIssueId,
			final String toIssueId,final String userId,final String comment) {
		
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_IMS.MERGE_ISSUE(?, ?, ?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, fromIssueId);
				statement.setString(2, toIssueId);
				statement.setString(3, userId);
				statement.setString(4, comment);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(5);
				returnMsg = statement.getString(6);
			}
		});
	}
	
	/**
	 * 取消合并问题
	 */
	public Map<String, Object> cancelMergeIssue(final String issueId,
			final String userId) {
		return executeProcess(new DBProcedureWork() {
			@Override
			public void execute(Connection connection) throws SQLException {
				String procedure = "{call PKG_IMS.UNDO_MERGE_ISSUE(?, ?, ?, ?)}";
				CallableStatement statement = connection.prepareCall(procedure);
				statement.setString(1, issueId);
				statement.setString(2, userId);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.executeUpdate();
				//设置错误代码和错误信息
				returnCode = statement.getString(3);
				returnMsg = statement.getString(4);
			}
		});
	}
}
