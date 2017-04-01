package com.gnomon.pdms.action.ims;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.gnomon.common.page.GTPage;
import com.gnomon.common.utils.JsonResult;
import com.gnomon.common.web.SessionData;
import com.gnomon.pdms.common.PDMSCrudActionSupport;
import com.gnomon.pdms.entity.ImsPartEntity;
import com.gnomon.pdms.entity.MyTaskInfo;
import com.gnomon.pdms.service.ImsPartService;

@Namespace("/ims")
public class ImsPartAction extends PDMSCrudActionSupport<ImsPartEntity> {

	private static final long serialVersionUID = 1L;
	private ImsPartEntity imsPartEntity;
	
	@Override
	public ImsPartEntity getModel() {
		return imsPartEntity;
	}
	@Autowired
	private ImsPartService imsPartService;
	

	private File upload;//导入文件
	
	private String uploadFileName;
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
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
	
	public void getPartList() {

		try{
			JsonResult result = new JsonResult();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			GTPage<Map<String, Object>> pageResult = this.imsPartService.getImsPartList(this.getPage(), this.getLimit());
			for (Map<String, Object> map : pageResult.getItems()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("code", map.get("CODE"));
				dataMap.put("name", map.get("NAME"));
				dataMap.put("createBy", map.get("CREATE_BY"));
				dataMap.put("createDate", map.get("CREATE_DATE"));
				dataMap.put("updateBy", map.get("UPDATE_BY"));
				dataMap.put("updateDate", map.get("UPDATE_DATE"));
				dataMap.put("deleteBy", map.get("DELETE_BY"));
				dataMap.put("deleteDate", map.get("DELETE_DATE"));
				dataMap.put("groupNo", map.get("GROUP_NO"));
				dataMap.put("systemNo", map.get("SYSTEM_NO"));
				dataMap.put("systemName", map.get("SYSTEM_NAME"));
				dataMap.put("groupName", map.get("GROUP_NAME"));
				
				data.add(dataMap);
			}
			
			// 结果返回
			result.buildSuccessResultForList(data, pageResult.getItemCount());
			Struts2Utils.renderJson(result);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void importFromExcel() {
		try{
			imsPartService.importFromExcel(uploadFileName, new FileInputStream(upload));
			this.writeSuccessResult(null);
		}catch (Exception ex) {
			ex.printStackTrace();
			this.writeErrorResult(ex.getMessage());
		}
	}
	
	public void downloadExtTemplate() {
		try {
			InputStream is = this.getClass().getResourceAsStream("/template/IMS_PART.xlsx");
			HttpServletResponse response = Struts2Utils.getResponse();
			ServletOutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			// filename = "attachment; filename=" + filename + ".xls";
			response.setHeader("Content-disposition","attachment; filename=IMS_PART.xlsx");// 设定输出文件头
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
}
