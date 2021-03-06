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
CucumberKW.runFeatureFile('Include/features/Event-Outlook/editLabEvent.feature')


def setup()
{
   // Store parameters for test
   SharedTestData data = SharedTestData.getInstance()
   data.addTestParam("testCode", test_code)
   data.addTestParam("labEventToUse", use_lab_event_named)
   data.addTestParam("contentModuleToUse", use_content_module_named)
   data.addTestParam("trainingPackageToUse", use_training_package_named)
   data.addTestParam("surveyToUse", use_survey_named)
  
   // Create support test data if running standalone (otherwise, a test suite that would make it)
   TestSupport support = TestSupport.getInstance()
   if (!support.isInTestSuite())
   {
      // Login as author
      support.login(GlobalVariable.author_username, GlobalVariable.author_password, GlobalVariable.author_key)

      // Make a training package (with content module)
      data.createContentModule(use_content_module_named)
      data.createTrainingPackage(use_training_package_named, use_content_module_named)
      
      // Make a survey module that the lab event will use
      data.createSurveyModule(use_survey_named)

      // Switch to training manager to make event
      support.logout()
      support.login(GlobalVariable.manager_username, GlobalVariable.manager_password, GlobalVariable.manager_key)

      // Make a lab event
      data.createLabEvent(use_lab_event_named, use_training_package_named, use_survey_named, 10)

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
      // Login as manager, remove lab event (also, try to remove old name in case test failed)
      support.login(GlobalVariable.manager_username, GlobalVariable.manager_password, GlobalVariable.manager_key)
      SharedTestData data = SharedTestData.getInstance()
      data.deleteLabEvent(use_lab_event_named + ' [UPDATED]')
      data.deleteLabEvent(use_lab_event_named)
      support.logout()

      // Login as author, remove training package & content module & survey, and logout
      support.login(GlobalVariable.author_username, GlobalVariable.author_password, GlobalVariable.author_key)
      data.deleteTrainingPackage(use_training_package_named)
      data.deleteContentModule(use_content_module_named)
      data.deleteSurveyModule(use_survey_named)
      support.logout()
   }
}
