package com.gnomon.common.webservice;
import java.io.IOException;  
  
import javax.security.auth.callback.Callback;  
import javax.security.auth.callback.CallbackHandler;  
import javax.security.auth.callback.UnsupportedCallbackException;  
  
import org.apache.ws.security.WSPasswordCallback;  
  
public class ServerPasswordCallback implements CallbackHandler {  
  
    public void handle(Callback[] callbacks) throws IOException,  
            UnsupportedCallbackException {  
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];  
        String pw = pc.getPassword();  
        String idf = pc.getIdentifier();  
        System.out.println("password:"+pw);  
        System.out.println("identifier:"+idf);  
        if (pw.equals("123456") && idf.equals("PDMS")) {  
            // 验证通过  
        } else {  
            throw new SecurityException("验证失败");  
        }  
    }  
  
}  