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

WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/button_Apps'), 30)

'Open the Dashboard (apps)'
WebUI.click(findTestObject('Page_PCTE Portal/button_Apps'))

WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Content'), 30)

'Select the Content area (app)'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content'))

WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Content_TrainingPackages'), 30)

'Click on "Training Packages"'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content_TrainingPackages'))

WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/input_Content_TrainingPackageSearch'), 30)

'Set text to search for'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Content_TrainingPackageSearch'), packageName)

TestObject packageObject = findTestObject('Page_PCTE Portal/input_Content_TrainingPackageListSpecificTest', [('specificTestName') : packageName])

for (int i = 0; i < 5; i++) {
	if (WebUI.waitForElementClickable(packageObject)) {
		break
	}
}

'Get our test'
TestObject specificTest = findTestObject('Page_PCTE Portal/a_Content_TrainingPackageListSpecificTest', [('specificTestName') : packageName])

'Click "more"'
WebUI.click(findTestObject('Object Repository/Page_PCTE Portal/i_Content_TrainingPackagesMore'))

WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Content_TrainingPackageDelete'))

'Click delete'
WebUI.click(findTestObject('Object Repository/Page_PCTE Portal/span_Content_TrainingPackagesDelete'))

WebUI.waitForElementPresent(findTestObject('Page_PCTE POrtal/button_Content_TrainingPackagesConfirmYes'), 30)

'Confirm that the project should be deleted'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackagesConfirmYes'))


def setup() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Log into the portal'
    support.login(GlobalVariable.orgadmin_username, GlobalVariable.orgadmin_password)

    'Create a content module and training package to use'
    SharedTestData data = SharedTestData.getInstance()
    data.createContentModule('PCTE Test ' + testCode)
    TestSupport.delay(3)
    data.createTrainingPackage('PCTE Test ' + testCode)
    
    TestSupport.delay(3)
}

def teardown() 
{
   'Clean-up content module'
   SharedTestData data = SharedTestData.getInstance()
   data.deleteContentModule('PCTE Test ' + testCode)
      
   'Get the instance of the test support class'
   TestSupport support = TestSupport.getInstance()

   'Log out of the portal'
   support.logout()
}

