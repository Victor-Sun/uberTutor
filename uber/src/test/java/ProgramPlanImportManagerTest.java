
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.pdms.service.ProgramPlanImportManager;

public class ProgramPlanImportManagerTest extends BaseJunit4Test {
	@Autowired
	private ProgramPlanImportManager programPlanImportManager;


	String programId="35B3586847193E35E050A8C09901619C";
	
//	@Test	// 标明是测试方法
	@Transactional	// 标明此方法需使用事务
	@Rollback(false)	// 标明使用完此方法后事务不回滚,true时为回滚
	public void testImport() throws IOException {
		String userid = "admin";
//		File testFile = new File("files/test.txt");
//		FileWriter fw = new FileWriter(testFile);
//		fw.write("aaaaaaaaaaaaaaaaa");
//		fw.close();
		
		File excelFile = new File("files/一级计划导入.xls");
		try {
			programPlanImportManager.importProgramFromExcel(programId,excelFile.getName(),new FileInputStream(excelFile),userid);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test	// 标明是测试方法
	@Transactional	// 标明此方法需使用事务
	@Rollback(false)	// 标明使用完此方法后事务不回滚,true时为回滚
	public void testImportProgramPlanFromTemplate() throws FileNotFoundException{
//		List<Integer> partNumberList = new ArrayList<Integer>();
//		partNumberList.add(1);
//		partNumberList.add(2);
//		partNumberList.add(3);
//		pkgExt301DBProcedureServcie.processSchedule("","",partNumberList);
		File testFile = new File("d:/tmp/节点管理表.xls");
		FileInputStream fis = new FileInputStream(testFile);
		String programVehicleId = "156A4507AD4F4791B5645688FE59C584";
		String functionGroupId = "390403042CD94AA28538D6EE5CA9CD7E";
		programPlanImportManager.importProgramNodeFromExcel(programVehicleId, functionGroupId, fis);
		
	}
	public static void main(String[] args) {
	}
}
