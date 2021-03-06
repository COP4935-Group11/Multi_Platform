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


def setup()
{
   // Store parameters for test
   SharedTestData data = SharedTestData.getInstance()
   data.addTestParam("testCode", test_code)
   data.addTestParam("contentModuleToUse", use_content_module_named)
   String trainingPackageName = "PCTE Test " + test_code
   data.addTestParam("trainingPackageName", trainingPackageName)

   // Login as author
   TestSupport support = TestSupport.getInstance()
   support.login(GlobalVariable.author_username, GlobalVariable.author_password, GlobalVariable.author_key)

   // Create support test data if running standalone (otherwise, a test suite would make it)
   if (!support.isInTestSuite())
   {
      // Make a content module to use
      data.createContentModule(use_content_module_named)
   }

   data.createTrainingPackage(trainingPackageName, use_content_module_named)
   
   // And finally logout
   support.logout()
}


def teardown()
{
   // First, make sure we are logged out from any left over user
   TestSupport support = TestSupport.getInstance()
   support.logout()
  
   support.login(GlobalVariable.god_username, GlobalVariable.god_password, GlobalVariable.god_key)

   // Clean-up training package we made
   SharedTestData data = SharedTestData.getInstance()
   String trainingPackageName = "PCTE Test " + test_code
   data.deleteTrainingPackage(trainingPackageName)
   
   // Done with portal admin 
   support.logout()

   // Clean-up test data if running standalone (otherwise, a test suite will do it)
   if (!support.isInTestSuite())
   {
      // Login as admin
      support.login(GlobalVariable.author_username, GlobalVariable.author_password, GlobalVariable.author_key)

      // Clean-up data we made
      data.deleteContentModule(use_content_module_named)
   
      // And logout
      support.logout()
   }
}
