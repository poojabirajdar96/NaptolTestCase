package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Reports {
	public static ExtentReports createReport()
	{
		ExtentSparkReporter htmlReports = new ExtentSparkReporter("NaptolTestReports.html");
		ExtentReports reports = new ExtentReports();
		reports.attachReporter(htmlReports);
		reports.setSystemInfo("ProjectName","Naptol");
		reports.setSystemInfo("Owner", "Arise");
		return reports;
		
	}
}
