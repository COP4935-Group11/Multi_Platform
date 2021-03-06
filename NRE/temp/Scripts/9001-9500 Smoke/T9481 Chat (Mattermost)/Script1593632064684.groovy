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
CucumberKW.runFeatureFile('Include\\features\\Ext-Services\\Mattermost.feature')


def setup()
{
   // Store test code
   SharedTestData.testCode = testCode
   
   // Login as admin
   TestSupport support = TestSupport.getInstance()
   support.login(GlobalVariable.author_username, GlobalVariable.author_password, GlobalVariable.author_key)
   
   // And finally logout
   support.logout()
}


def teardown()
{
   // First, make sure we are logged out from any left over user
   TestSupport support = TestSupport.getInstance()
   support.logout()
}
