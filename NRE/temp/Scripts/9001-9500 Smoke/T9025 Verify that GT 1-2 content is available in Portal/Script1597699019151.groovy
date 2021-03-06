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
CucumberKW.runFeatureFile('Include/features/Portal-Event/verifyGT12Content.feature')


def setup() 
{
   // Store parameters for test
   SharedTestData data = SharedTestData.getInstance()
   data.addTestParam("testCode", test_code)
}


def teardown() 
{
   // Make sure we are logged out
   TestSupport support = TestSupport.getInstance()
   support.logout()
}
