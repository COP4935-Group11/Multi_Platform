import com.ucf.pcte.CucumberKW
import com.ucf.pcte.gold.WebUI as WebUI
import static com.ucf.pcte.gold.WebUI.findTestObject
import cucumber.api.java.en.*
import com.ucf.pcte.CSVData
import com.constants.CSVSeparator
import com.configuration.RunConfiguration
import internal.GlobalVariable
import com.constants.*
import hooks.TestSupport
import hooks.SharedTestData



// Run the feature file
CucumberKW.runFeatureFile('Include/features/Event-Outlook/scheduleLabEvent.feature')


def setup()
{
   // Store parameters for test
   SharedTestData data = SharedTestData.getInstance()
   data.addTestParam("testCode", test_code)
   data.addTestParam("labEventName", "PCTE Test " + test_code)
   data.addTestParam("contentModuleToUse", use_content_module_named)
   data.addTestParam("trainingPackageToUse", use_training_package_named)
   data.addTestParam("surveyToUse", use_survey_named)
  
   // Create support test data if running standalone (otherwise, a test suite that would make it)
   TestSupport support = TestSupport.getInstance()
   if (!support.isInTestSuite())
   {
      // Login as author
      support.login(GlobalVariable.author_username, GlobalVariable.author_password, GlobalVariable.author_key)

      // Make a content module, training package and survey to use
      data.createContentModule(use_content_module_named)
      data.createTrainingPackage(use_training_package_named, use_content_module_named)
      data.createSurveyModule(use_survey_named)
      
      // And finally logout
      support.logout()
   }
}


def teardown()
{
   // First, make sure we are logged out from any left over user
   TestSupport support = TestSupport.getInstance()
   support.logout()

   // Clean-up test data if running standalone (otherwise, a test suite will do it)
   if (!support.isInTestSuite())
   {
      // Login as author, remove training package & content module & survey, and logout
      support.login(GlobalVariable.author_username, GlobalVariable.author_password, GlobalVariable.author_key)
      SharedTestData data = SharedTestData.getInstance()
      data.deleteTrainingPackage(use_training_package_named)
      data.deleteContentModule(use_content_module_named)
      data.deleteSurveyModule(use_survey_named)
      support.logout()
   }
}
