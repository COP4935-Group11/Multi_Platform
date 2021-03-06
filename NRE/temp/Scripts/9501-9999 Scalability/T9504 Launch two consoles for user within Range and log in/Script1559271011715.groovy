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

'Open the app selector, this doesn\'t require a delay because it was done in the test case'
WebUI.click(findTestObject('Page_PCTE Portal/button_Apps'))

'Wait for the range to be clickable or the test will fail in chrome'
TestSupport.delay(3, FailureHandling.STOP_ON_FAILURE)

'Select the range app from the app selector'
WebUI.click(findTestObject('Page_PCTE Portal/span_Apps_Content'))

'Click on the Ranges section of the Content app'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content_Ranges'))

'Wait for the desired network element to appear'
WebUI.waitForElementClickable(findTestObject('Page_PCTE Portal/a_Ranges_DynamicRangeLink', [('text') : GlobalVariable.range_name]), 
    20)

'Select a network, this is a placeholder and will change once there is an automated test network created test case'
WebUI.click(findTestObject('Page_PCTE Portal/a_Ranges_DynamicRangeLink', [('text') : GlobalVariable.range_name]))

'View the first console'
clickViewConsole(GlobalVariable.user1_console1)

'Switch the window with the console.\r\nThis is to ensure that key events are sent to the console canvas correctly'
WebUI.switchToWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.user1_console1)

'Wait for the canvas element containing the VM console to appear.\r\nThis step is used to measure console load time'
WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), 300)

'Wait 5 seconds for the console window to fully launch'
TestSupport.delay(5)

'Send CTRL+ALT+DELETE keys to start login process'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.CONTROL, Keys.ALT, Keys.DELETE))

'Wait 1 second for the login banner to appear'
TestSupport.delay(1)

'Send Enter key to acknowledge the login banner'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ENTER))

'Wait 3 seconds for the login prompt to appear'
TestSupport.delay(3)

'Enter the password for the console administrator'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), 'Simspace1!Simspace1!')

TestSupport.delay(1)

'Hit the enter key to enter the password and log in'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ENTER))

'Wait for the login process to complete'
TestSupport.delay(20)

'Click the Activate Later button'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ALT, 'l'))

TestSupport.delay(1)

'Click the OK button'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ALT, 'o'))

TestSupport.delay(10)

'Open the Start menu'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.CONTROL, Keys.ESCAPE))

TestSupport.delay(1)

'Type in command for command prompt'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), 'cmd')

TestSupport.delay(1)

'Open command prompt'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ENTER))

'Switch back to the main window'
WebUI.switchToWindowTitle('PCTE Portal')

'View the second console'
clickViewConsole(GlobalVariable.user1_console2)

'Switch the window with the console.\r\nThis is to ensure that key events are sent to the console canvas correctly'
WebUI.switchToWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.user1_console2)

'Wait 5 seconds for the console window to fully launch'
TestSupport.delay(5)

'Send CTRL+ALT+DELETE keys to start login process'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.CONTROL, Keys.ALT, Keys.DELETE))

'Wait 1 second for the login banner to appear'
TestSupport.delay(1)

'Send Enter key to acknowledge the login banner'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ENTER))

'Wait 3 seconds for the login prompt to appear'
TestSupport.delay(3)

'Enter the password for the console administrator'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), 'Simspace1!Simspace1!')

TestSupport.delay(1)

'Hit the enter key to enter the password and log in'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ENTER))

'Wait for the login process to complete'
TestSupport.delay(20)

'Click the Activate Later button'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ALT, 'l'))

TestSupport.delay(1)

'Click the OK button'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ALT, 'o'))

TestSupport.delay(10)

'Open the Start menu'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.CONTROL, Keys.ESCAPE))

TestSupport.delay(1)

'Type in command for command prompt'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), 'cmd')

TestSupport.delay(1)

'Open command prompt'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ENTER))

'Delay to keep the consoles logged in. Adjust this based on test topology'
TestSupport.delay(60)

'Open the Start Menu'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.CONTROL, Keys.ESCAPE))

TestSupport.delay(1)

'Type in command to log out'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), 'shutdown -L')

TestSupport.delay(1)

'Log out of the console'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ENTER))

'Switch the window with the console.\r\nThis is to ensure that key events are sent to the console canvas correctly'
WebUI.switchToWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.user1_console1)

'Open the Start Menu'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.CONTROL, Keys.ESCAPE))

TestSupport.delay(1)

'Type in command to log out'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), 'shutdown -L')

TestSupport.delay(1)

'Log out of the console'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), Keys.chord(Keys.ENTER))

'Close the first console window'
WebUI.closeWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.user1_console1)

'Close the second console window'
WebUI.closeWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.user1_console2)

TestSupport.delay(2)

'Switch back to the main window for teardown'
WebUI.switchToWindowTitle('PCTE Portal')

def setup() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Log into the portal'
    support.login(GlobalVariable.user1_username, GlobalVariable.user1_password)
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

