package Core.Support.General;

import Core.BaseEntities;
import Core.Support.RestAPI.StepsData;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportGenerator extends BaseEntities {
    public static void generateCucumberReport() throws Exception {
        File reportOutputDirectory = new File("target/test-classes/cucumber-reports/");

        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/test-classes/reports/result.json");

        String projectName = "WeCheer Automation";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);

        // do not make scenario failed when step has status SKIPPED
        configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));

        // additional metadata presented on main page
        String flatForm = System.getProperty("os.name");
        String browser = "Undefine";
        try {
            browser = getSeleniumBrowser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        configuration.addClassifications("OS", flatForm);
        configuration.addClassifications("Browser", browser);
        configuration.addClassifications("Mobile platform", BaseEntities.getDevicePlatform());
        configuration.addClassifications("App version", StepsData.getInstance().getAppVersion());
        /*
            // optionally specify qualifiers for each of the report json files - For PARALLEL later
                configuration.addPresentationModes(PresentationMode.PARALLEL_TESTING);
                configuration.setQualifier("cucumber-report-1", "First report");
                configuration.setQualifier("cucumber-report-2", "Second report");
         */

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        Reportable result = reportBuilder.generateReports();
    }
}
