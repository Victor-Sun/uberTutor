import com.actionsoft.awf.services.WSDKException;
import com.gnomon.intergration.aws.AwsApi;


public class testEmail {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			int result = AwsApi.getInstance().getIM().sendMail("aws-test", "thomas.ma@gnomontech.com", "aaaaa", "hello");
			System.out.print("result"+result);
		} catch (WSDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
