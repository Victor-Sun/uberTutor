
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.service.ProgramPlanImportManager;
import com.gnomon.pdms.service.ProgramPlanTempImportManager;

public class ProgramPlanTempImportManagerTest extends BaseJunit4Test {
	@Autowired
	private ProgramPlanTempImportManager programPlanTempImportManager;

	
	@Test	// 标明是测试方法
	@Transactional	// 标明此方法需使用事务
	@Rollback(false)	// 标明使用完此方法后事务不回滚,true时为回滚
	public void testImport1() throws IOException {
		String userId = "1525AC437E094080918D1A9DFBDA0B0E";//admin
//		File testFile = new File("files/test.txt");
//		FileWriter fw = new FileWriter(testFile);
//		fw.write("aaaaaaaaaaaaaaaaa");
//		fw.close();
		
		String programTypeId="35B3586847103E35E050A8C09901619C";//整车开发项目
		
		File excelFile = new File("D:\\当前项目\\GAEI_PDMS\\3 系统设计\\功能设计\\从模板导入\\一级计划模板导入.xls");
		try {
			String programName = "测试模板1";
			String programCode = "test Temp 1";
			programPlanTempImportManager.createProgramFromExcel(programTypeId,programName,programCode,excelFile.getName(),new FileInputStream(excelFile),userId);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
//	@Test	// 标明是测试方法
//	@Transactional	// 标明此方法需使用事务
//	@Rollback(false)	// 标明使用完此方法后事务不回滚,true时为回滚
	public void testImport2() throws IOException {
		String userId = "1525AC437E094080918D1A9DFBDA0B0E";//admin
//		File testFile = new File("files/test.txt");
//		FileWriter fw = new FileWriter(testFile);
//		fw.write("aaaaaaaaaaaaaaaaa");
//		fw.close();
		
		String programTypeId="35B3586847103E35E050A8C09901619C";//整车开发项目
		
		File excelFile = new File("D:\\当前项目\\GAEI_PDMS\\3 系统设计\\功能设计\\从模板导入\\二级计划模板导入.xls");
		try {
			String programName = "测试模板2";
			String programCode = "test Temp 2";
			programPlanTempImportManager.createProgramFromExcel(programTypeId,programName,programCode,excelFile.getName(),new FileInputStream(excelFile),userId);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	public static void main(String[] args) {
	}
}
