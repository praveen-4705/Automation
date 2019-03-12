package com.utilities;

public class Constants {
	
		
	public static final String CASURL = "";
	
	public static String userDir= System.getProperty("user.dir");
	
	//Browser Driver Paths
	public static final String FirefoxDriverPath = userDir + "Broswers/geckodriver.exe";
	public static final String IEDriverPath = userDir + "/Broswers/IEDriverServer.exe";
	public static final String ChromeDriverPath = userDir + "/Broswers/chromedriver_2_35.exe";
	
	//Constants for the step status
	public static final String stepPass = "PASS";
	public static final String stepFail = "FAIL";
	public static final String stepWarn = "WARN";
	public static final String stepInfo = "INFO";
	public static final String stepError = "ERROR";
	
	
	//Timeout interval constants
	public static final int MaxWaitTimeInSec = 20;
	public static final int maxWaitTime = 20; 
	public static final int intervalWaitTime = 5;
	
	public static final String DELIMITER="@@@@@";
    public static final String BROWSER_NAME="Chrome";
	
    //Logo
	public static final String Logo = System.getProperty("user.dir") + "/Reports/Logo/Logo.png";
	
	//File locations constants
	public static final String InputFileLocation = userDir + "/TestData/InputData.xlsx";
	public static final String ExecuteFileLocation = userDir + "/TestData/ExecutionFile.xlsx";
	public static final String ExecuteFileSheet = "ExecutionSheet";

	//Test reports location details
	public static final String TextReportPath = userDir + "/Reports/TextReports/";
	public static final String HtmlReportPath = userDir + "/Reports/HtmlReports/";
	public  static final String PdfReportPath = userDir + "/Reports/PdfReports/";
	public  static final String ExtentReportPath = userDir + "/Reports/ExtentReports/";
	public static final String ScreenshotsPath = userDir + "/Reports/Screenshots/";
	public static final String ApplitoolsScreenshotsPath = userDir + "/Reports/Screenshots/Applitools/";
	public static  String screenshotName = "";
	

	//Screen shot preferences
	public static final boolean ScreenshotsToAllStepsInExtentReport=false;
	public static final boolean ScreenshotsToAllStepsInPdfReport=true;
	public static final boolean ScreenshotsToAllApplitoolsReport=false;
	
	
	//Auto It File Path
	public static final String AutoITUploadFilePath = userDir + "/TestFiles/AutoFileUpload.exe";
	public static final String AutoITFilePathLeavePage = userDir + "/TestFiles/AutoClickOnLeaveThisPage.exe";
	public static final String AutoITSaveBtnClick = userDir + "/TestFiles/Click_Save_Btn.exe";
	public static final String AutoITSaveAsBtnClick = userDir + "/TestFiles/Click_SaveAS_Btn.exe";
	public static final String AutoITImportFileChrome = userDir + "/TestFiles/ImportExcelSheet.exe";
	public static final String AutoITImportFileIE = userDir + "/TestFiles/ImportExcelSheetIE.exe";
	public static final String AutoITImportRosterFileChrome = userDir + "/TestFiles/ImportRosterFileForChrome.exe";
	public static final String AutoITImportRosterFileIE = userDir + "/TestFiles/ImportRosterFileForIE.exe";
	public static final String AutoITClickYes = userDir + "/TestFiles/AutoClickOnYes.exe";
	public static final String AutoITImportCustomerFileChrome = userDir + "/TestFiles/ImportCustomerFileForChrome.exe";
	public static final String AutoITImportCustomerFileIE = userDir + "/TestFiles/ImportCustomerFileForIE.exe";	
	public static final String AutoITImportCMAProductChrome = userDir + "/TestFiles/Import_CMAProductFile_Chrome.exe";
	public static final String AutoITImportCMAProductIE = userDir + "/TestFiles/Import_CMA_ProductFile_IE.exe";	
	public static final String AutoITImportCMAExcludeChrome = userDir + "/TestFiles/Import_CMAExcludes_Chrome.exe";
	public static final String AutoITImportCMAExcludeIE = userDir + "/TestFiles/Import_CMAExcludes_IE.exe";	
	public static final String AutoITImportCMAHierExcludeChrome = userDir + "/TestFiles/Import_CMAHierarchyExcludes_Chrome.exe";
	public static final String AutoITImportCMAHierExcludeIE = userDir + "/TestFiles/Import_CMAHierarchyExcludes_IE.exe";
	
	public static final String AutoITImportCMAProductErrorChrome = userDir + "/TestFiles/Import_CMAExcludes_Error_Chrome.exe";
	public static final String AutoITImportCMAProductErrorIE = userDir + "/TestFiles/Import_CMA_ProductFile_Error_IE.exe";	
	public static final String AutoITImportCMAHierExcludeErrorChrome = userDir + "/TestFiles/Import_CMAHierarchyExcludes_Error_Chrome.exe";
	public static final String AutoITImportCMAHierExcludeErrorIE = userDir + "/TestFiles/Import_CMA_Hier_ExcludeFile_Eror_IE.exe";
	public static final String AutoITImportCMAExcludeErrorChrome = userDir + "/TestFiles/Import_CMAExcludes_Error_Chrome.exe";
	public static final String AutoITImportCMAExcludeErrorIE = userDir + "/TestFiles/Import_CMA_ExcludeFile_Eror_IE.exe";	
	
	public static final String AutoITImportCustomerDataFileChrome = userDir + "/TestFiles/ImportCustomerDataFileForChrome.exe";
	public static final String AutoITImportCustomerDataFileIE = userDir + "/TestFiles/ImportCustomerDataFileForIE.exe";	

	
	
	
	//Sikuli image File path
	public static final String SikuliImageFilePath = userDir + "/TestFiles/Leave.PNG";
	public static final String SikuliYesImageFilePath = userDir + "/TestFiles/YesBtn.PNG";
	public static final String SikuliLeavePageImageFilePath = userDir + "/TestFiles/LeavePage.PNG";
	
//	//Download File Path
//	public static final String RosterColumnMappingFilePath = "C:\\Users\\EJ86026\\Desktop\\Project\\CASIS\\DataExportFromCAS\\Roster_Column_Mapping.xlsx";
//	public static final String RosterColumnMappingFileLocation = "C:\\Users\\EJ86026\\Desktop\\Project\\CASIS\\DataExportFromCAS";
//	public static final String RosterImportDataFilePath = "C:\\Users\\EJ86026\\Desktop\\Project\\CASIS\\DataExportFromCAS\\Roster_Import_Data_JAY-NOVATION_"+ Common.getCurrentDate() +".xlsx";
//	public static final String RosterErrorFilePath = "C:\\Users\\EJ86026\\Desktop\\Project\\CASIS\\DataExportFromCAS\\ROSTER_ERROR_JAY-NOVATION_"+ Common.getCurrentDate() +".xlsx";
//	public static final String RosterACDTFilePath = "C:\\Users\\EJ86026\\Desktop\\Project\\CASIS\\DataExportFromCAS\\ROSTER_ACD_JAY-NOVATION_"+ Common.getCurrentDate() +".xlsx";
//	public static final String CustomersFilePath = "C:\\Users\\EJ86026\\Desktop\\Project\\CASIS\\DataExportFromCAS\\CUSTOMERS.xlsx";
//	
//	public static final String CMAProductsFilePath = "C:\\Users\\EJ86026\\Desktop\\Project\\CASIS\\DataExportFromCAS\\RC_CMA_PROD_"+ Common.getCurrentDate() +".xlsx";
//	public static final String CMAHierarchyExcludesFilePath = "C:\\Users\\EJ86026\\Desktop\\Project\\CASIS\\DataExportFromCAS\\RC_CMA_HRCHY_EXCL_"+ Common.getCurrentDate() +".xlsx";
//	public static final String CMAExcludesFilePath = "C:\\Users\\EJ86026\\Desktop\\Project\\CASIS\\DataExportFromCAS\\RC_CMA_DIV_EXCL_"+ Common.getCurrentDate() +".xlsx";
//	public static final String MarginProgramCodeMaintenaceFilePath = "C:\\Users\\EJ86026\\Desktop\\Project\\CASIS\\DataExportFromCAS\\Margin_Program_Code_Maintenace.xlsx";
//	//Attachment Files
//	public static final String RosterColumnMappingImportFilePath = userDir + "/TestData/Roster_Column_Mapping_Import.xlsx";
//	
		
	//Email
	public static final String userName = "rakesh.yeshwant@usfoods.com";	 
	public static final String password = "Saicas17";

	
	
			
}
