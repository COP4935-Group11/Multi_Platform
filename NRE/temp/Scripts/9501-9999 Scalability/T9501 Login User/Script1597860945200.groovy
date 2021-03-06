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

'Wait for the apps button to appear'
WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/button_Apps'), 5)

def setup() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Log into the portal'
    support.login(GlobalVariable.username, GlobalVariable.password)
}

def teardown() {
    'Get an instance of the test support class'
    TestSupport testSupport = TestSupport.getInstance()

    'Log out of the portal'
    testSupport.logout()
}

