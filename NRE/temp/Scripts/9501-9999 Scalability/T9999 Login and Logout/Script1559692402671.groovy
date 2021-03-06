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

def clickViewConsole(String consoleName) {
    'Create a dynamic test object and find the HTML element with the correct name'
    TestObject consoleHeading = new TestObject()

    'Form the xpath for a generic "View Console" button'
    String viewConsoleButtonPath = '//div[@class = \'overview\' and .//h2[(text() = \'%s\')]]//span[(text() = \'View Console\')]'

    'Form the full xpath with the name of the desired console'
    String buttonXPath = String.format(viewConsoleButtonPath, consoleName)

    'Set the newly-formed xpath as a property of the dynamic test object'
    consoleHeading.addProperty('xpath', ConditionType.EQUALS, buttonXPath)

    'For now, this is a hard limit on the amount of scrolling that will be done, because there is no way to detect that the end of the page has been reached'
    def i = 0

    def maxScroll = 100

    'Keep scrolling until the desired element appears or the maximum amount of scrolling is reached'
    while (!(WebUI.verifyElementPresent(consoleHeading, 1, FailureHandling.OPTIONAL)) && (i < maxScroll)) {
        'Use a robot to scroll the page. This robot is used because of known issues in Katalon with lazy loading'
        Robot robot = new Robot()

        robot.mouseWheel(8)

        i++
    }
    
    'Click on the "View Console" button'
    WebUI.click(consoleHeading)
}

