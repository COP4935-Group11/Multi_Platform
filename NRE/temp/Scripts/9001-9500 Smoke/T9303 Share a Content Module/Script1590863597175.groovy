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
CucumberKW.runFeatureFile('Include/features/Content-Content-Authoring/shareAContentModule.feature')


def setup()
{
   // Store parameters for test
   SharedTestData data = SharedTestData.getInstance()
   data.addTestParam("testCode", test_code)
   data.addTestParam("contentModuleName", "PCTE Test " + test_code)
   
   // Login as author
   TestSupport support = TestSupport.getInstance()
   support.login(GlobalVariable.author_username, GlobalVariable.author_password, GlobalVariable.author_key)

   // Make a content module and training package to use
   data.createContentModule("PCTE Test " + test_code)
   
   // And finally logout
   support.logout()
}


def teardown()
{
   // First, make sure we are logged out from any left over user
   TestSupport support = TestSupport.getInstance()
   support.logout()
  
   // Login as author
   support.login(GlobalVariable.author_username, GlobalVariable.author_password, GlobalVariable.author_key)

   // Clean-up data we made
   SharedTestData data = SharedTestData.getInstance()
   data.deleteContentModule("PCTE Test " + test_code)
   
   // And logout
   support.logout()
}
