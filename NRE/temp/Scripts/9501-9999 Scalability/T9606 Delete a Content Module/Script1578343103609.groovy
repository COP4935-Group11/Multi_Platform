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

'Open the Dashboard (apps)'
WebUI.click(findTestObject('Page_PCTE Portal/button_Apps'))

WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/button_Apps'), 30)

'Select the Content area (app)'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content'))

'Click on "Content Modules"'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModules'))

'Set text to search for'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Content_ContentModuleSearch'), moduleName)

'Get our test'
TestObject specificTest = findTestObject('Page_PCTE Portal/a_Content_ContentModuleListSpecificTest', [('specificTestName') : 'PCTE Scalability ' + 
        GlobalVariable.username])

for (int i = 0; i < 5; i++) {
    if (WebUI.waitForElementClickable(findTestObject('Page_PCTE Portal/i_Content_ContentModulesMore'), 30, FailureHandling.OPTIONAL)) {
        break
    }
}

'Click "more"'
WebUI.click(findTestObject('Page_PCTE Portal/i_Content_ContentModulesMore'))

WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Content_ContentModulesDelete'), 30)

'Click delete'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModulesDelete'))

WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/button_Content_ContentModulesConfirmDelete'), 30)

'Confirm that the project should be deleted'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_ContentModulesConfirmDelete'))

'Open the Dashboard (apps)'
WebUI.click(findTestObject('Page_PCTE Portal/button_Apps'))

WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Content'), 30)

'Select the Content area (app)'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content'))

'Click on "Content Modules"'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModules'))

'Set text to search for'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Content_ContentModuleSearch'), moduleName)

'Wait for page to load'
not_run: TestSupport.delay(1)

'Verify module is gone now'
not_run: WebUI.verifyElementNotPresent(findTestObject('Page_PCTE Portal/a_Content_ContentModuleListSpecificTest', [('specificTestName') : 'PCTE Scalability ' + 
            GlobalVariable.username]), 3)

def setup() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Check to see if this test case is being run inside of a suite'
    if (!(support.isInTestSuite())) {
        'Log into the portal as admin'
        support.login(GlobalVariable.username, GlobalVariable.password)

        'Wait a second'
        TestSupport.delay(1)

        'Get the instance of the shared test data class'
        SharedTestData data = SharedTestData.getInstance()

        'Create a simple content module'
        data.createContentModule('PCTE Scalability ' + GlobalVariable.username)
    }
}

def teardown() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Check to see if this test case is being run inside of a suite'
    if (!(support.isInTestSuite())) {
        'Log out of the portal'
        support.logout()
    }
}

