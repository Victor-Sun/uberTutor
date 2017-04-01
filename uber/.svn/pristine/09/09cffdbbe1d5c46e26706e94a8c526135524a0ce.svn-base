

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bios.report.api.exception.ReportException;
import bios.report.api.manager.ReportBean;
import bios.report.api.manager.ReportManager;
import bios.report.api.model.ReportTemplet;
import bios.report.core.model.docket.Parameter;
import bios.report.core.model.docket.Variable;

/**
 * ����API����ɴ������?���㱨�?��������ȹ��ܵ����ӡ�
 * һ��Ӧ���ڱ����Ԥ����Ͷ�ʱ����ȡ�
 * @author Bijetsoft.com
 */
public class Sample {
	
	public static void main(String[] args) {
		try {			
			File dir = new File("d:/temp");
			if(!dir.exists() || !dir.isDirectory())
				dir.mkdir();
				
			//����������������ģ�����
			FileInputStream in = new FileInputStream("D:\\work\\广汽研究院\\PDMS\\1开发库\\101代码\\10101广汽PDMS\\gaei\\report\\report\\WEB-INF\\reports\\pm\\partControlRpt.brt");
			ReportTemplet template = new ReportTemplet(in);
			in.close();
			
			FileOutputStream out;
			
			//����ģ�����ָ�������
//			out = new FileOutputStream("d:/temp/test.brt");
//			template.save(out);
//			out.close();
			
			//���ò���ͱ���
			Map params = new HashMap();
			List<Parameter> paramList = template.getParameters();
			for (Parameter param : paramList) {
				params.put(param.getName(), ""); //TODO �û����л�ȡ����ֵ
			}
			Map vars = new HashMap();
			List<Variable> varList = template.getVariables();
			for (Variable var : varList) {
				vars.put(var.getName(), ""); //TODO �û����л�ȡ����ֵ
			}
			
			//������������ӿ�
			ReportManager manager = new ReportManager(template, params, vars);
			
			//������ݿ�����
			//�ڷ�����������ʱ�����Զ����ϵͳ���û�ȡ���Դ���ɲ��ý�������
			manager.setConnection(getConnection());
			
			ReportBean reportBean = null;
			//�������㷽��
			try {
				reportBean = manager.calc();
			} catch (ReportException e) {
				System.out.println("�����������!\n [" + e.getMessage() + "]");
				return;
			}
			
			//����excel
			out = new FileOutputStream("d:/temp/test.xls");
			reportBean.toExcel(out);
			out.close();

			//����pdf
			out = new FileOutputStream("d:/temp/test.pdf");
			reportBean.toPdf(out);
			out.close();
			
			//��ҳ���html
			for (int i = 1; i <= reportBean.getPageCount(); i++) {
				FileWriter fw = new FileWriter("d:/temp/test_page" + i + ".html");
				fw.write(reportBean.toHtml(i, null));
				fw.close();
			}
			
			//����txt
			FileWriter fw = new FileWriter("d:/temp/test.txt");
			String txt = reportBean.toTxt();
			fw.write(txt, 0, txt.length());
			fw.close();
			
			//����ͼƬ
			out = new FileOutputStream("d:/temp/test.jpg");
			reportBean.toImage(out, false);
			out.close();
			
			
			//���������ɵı������
			out = new FileOutputStream("d:/temp/test.brf");
			reportBean.toBrf(out);
			out.close();
			
			//�ӱ���ı�����󴴽�
			in = new FileInputStream("d:/temp/test.brf");
			reportBean = new ReportBean(in);
			in.close();
			
			//�ٴε���excel
			out = new FileOutputStream("d:/temp/test_2.xls");
			reportBean.toPagedExcel(out);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static Connection getConnection(){
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@demo.gnomontech.com:1521:orcl";
		String user = "pdms";
		String password = "pdms";
		try{
			Class.forName(driver).newInstance();
			return DriverManager.getConnection(url, user, password);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
