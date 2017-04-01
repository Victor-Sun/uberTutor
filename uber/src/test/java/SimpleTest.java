import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.common.utils.JsonResult;
import com.gnomon.pdms.TestService;


public class SimpleTest extends BaseJunit4Test{

	@Autowired
	TestService testService;
	
	@Test	// 标明是测试方法
	@Transactional	// 标明此方法需使用事务
	@Rollback(false)	// 标明使用完此方法后事务不回滚,true时为回滚
	public void test(){
		String programVehicleId="156A4507AD4F4791B5645688FE59C584";
		List<Map<String, Object>> ext301ItemList = testService.getExt301ItemList(programVehicleId);
		JsonResult result = new JsonResult();
		result.buildSuccessResult(ext301ItemList);
		System.out.println(JSONObject.fromObject(result).toString());
	}
}
