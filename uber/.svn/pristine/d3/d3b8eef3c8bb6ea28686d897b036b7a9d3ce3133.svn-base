import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gnomon.pdms.service.BimDataSyncService;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration   
({"/applicationContext-test.xml"}) //加载配置文件  
public class BimServiceTest {

	@Autowired
	private BimDataSyncService bimDataSyncService;
	
	@Test
	public void syncOrgAndUserFromBim(){
		try{
			bimDataSyncService.syncOrgAndUserFromBim();
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
}
