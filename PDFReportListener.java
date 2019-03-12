package com.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.DocumentException;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class PDFReportListener implements Reporter, Formatter {

    private PDFReport pdfReport;

    private static String reportFileName;
    private static String reportTextFileName;
    private static File reportTextFile;
    private static String screenshotName = "";
    private static ThreadLocal<LinkedList<Step>> stepListThreadLocal = new InheritableThreadLocal<>();
    private static ThreadLocal<StringBuilder> logReport = new InheritableThreadLocal<>();
    private static String scenarioName = "";

    public PDFReportListener() {
        pdfReport = new PDFReport();
        stepListThreadLocal.set(new LinkedList<Step>());
        logReport.set(new StringBuilder());

        createReportFile();
    }

    private void createReportFile() {

        reportFileName = "AutomationPDFReport" + "_" + getCurrentDateAndTime();
        try {
            reportTextFileName = Constants.TextReportPath + reportFileName + ".txt";
            reportTextFile = new File(reportTextFileName);

            if (reportTextFile.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String doGetScreenshotName() {
        Constants.screenshotName = Constants.ScreenshotsPath + getCurrentDateAndTime() + ".jpg";
        return Constants.screenshotName;
    }

    private void doCaptureScreenShot(String fileName) {
        try {
            // Take screenshot and store as a file format
            File src = ((TakesScreenshot) Browser.driver.get()).getScreenshotAs(OutputType.FILE);

            // now copy the screenshot to desired location using copyFile method //
            FileUtils.copyFile(src, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendTestReportFile(String logInfo) {
        // System.out.println("testStepStatus : " + testStepStatus);
        screenshotName = doGetScreenshotName();
        doCaptureScreenShot(screenshotName);
        logInfo = logInfo + Constants.DELIMITER + screenshotName;
        FileWriter writer = null;
        try {
            writer = new FileWriter(reportTextFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(Objects.requireNonNull(writer));
        PrintWriter pw = new PrintWriter(bw);
        pw.println(logInfo);
        pw.close();
    }

    private String getCurrentDateAndTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(cal.getTime());
    }

    @Override
    public void background(Background arg0) {
    }

    @Override
    public void close() {
    }

    @Override
    public void done() {
        String pdfReportFileName = Constants.PdfReportPath + reportFileName + ".pdf";
        try {
            pdfReport.generatePDFReport(pdfReportFileName, reportTextFileName);
        } catch (DocumentException | ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario arg0) {
        logReport.set(new StringBuilder());
    }

    @Override
    public void eof() {
    }

    @Override
    public void examples(Examples arg0) {
    }

    @Override
    public void feature(Feature feature) {
    }

    @Override
    public void scenario(Scenario scenario) {
    }

    @Override
    public void scenarioOutline(ScenarioOutline arg0) {
    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        scenarioName = scenario.getName();
        logReport.get().append(scenarioName);
    }

    @Override
    public void step(Step step) {
        stepListThreadLocal.get().add(step);
//        screenshotName = doGetScreenshotName();
//        doCaptureScreenShot(screenshotName);
    }

    @Override
    public void syntaxError(String arg0, String arg1, List<String> arg2, String arg3, Integer arg4) {
    }

    @Override
    public void uri(String arg0) {
    }

    @Override
    public void after(Match arg0, Result arg1) {
    }

    @Override
    public void before(Match arg0, Result arg1) {
    }

    @Override
    public void embedding(String arg0, byte[] arg1) {
    }

    @Override
    public void match(Match arg0) {
    }

    @Override
    public void result(Result result) {
        Step step = stepListThreadLocal.get().poll();
        String stepName = step.getName();
        StringBuilder testData = new StringBuilder();
        if (stepName.contains("\"")) {
            Matcher m = Pattern.compile("(\"[^\"]+\")").matcher(stepName);
            while (m.find()) {
                testData.append(m.group());
            }
        } else {
            testData = new StringBuilder("NO DATA");
        }
        logReport.get().append(Constants.DELIMITER + Constants.BROWSER_NAME + Constants.DELIMITER).append
                (getCurrentDateAndTime()).append(Constants.DELIMITER).append(stepName).append(Constants.DELIMITER)
                .append(testData).append(Constants.DELIMITER);

        if (Result.PASSED.equals(result.getStatus())) {
            appendTestReportFile(new String(logReport.get().append(Constants.stepPass)));
        } else if (Result.FAILED.equals(result.getStatus())) {
            appendTestReportFile(new String(logReport.get().append(Constants.stepFail)));
        }

        logReport.set(new StringBuilder(scenarioName));
    }

    @Override
    public void write(String arg0) {
    }
}
