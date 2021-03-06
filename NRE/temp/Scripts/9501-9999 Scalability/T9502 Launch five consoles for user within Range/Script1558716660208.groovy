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

'Open the app selector'
WebUI.click(findTestObject('Page_PCTE Portal/button_Apps'))

'Wait for the app dashboard to open'
TestSupport.delay(3)

'Select the range app from the app selector'
WebUI.click(findTestObject('Page_PCTE Portal/span_Apps_Content'))

'Wait for the page to load'
TestSupport.delay(3)

'Open the network selector'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content_Ranges'))

'Wait for the page to load'
TestSupport.delay(1)

'Input the name of the deployment into the range filter'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Content_RangeFilter'), GlobalVariable.range_name)

'Wait for the desired range to become available'
WebUI.waitForElementClickable(findTestObject('Page_PCTE Portal/a_Ranges_DynamicRangeLink', [('text') : GlobalVariable.range_name]), 
    20)

'Select the range based on the execution profile'
WebUI.click(findTestObject('Page_PCTE Portal/a_Ranges_DynamicRangeLink', [('text') : GlobalVariable.range_name]))

'Wait for the range to load'
TestSupport.delay(3)

'Enter the first console name into the VM filter'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Ranges_VMFilter'), GlobalVariable.console1)

'Wait 5 seconds for the filtering to finish'
TestSupport.delay(5)

'View the first console'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_DynamicRangeViewConsole', [('consoleName') : GlobalVariable.console1]))

'Wait for the console window to load'
TestSupport.delay(2)

'Switch the newly launched console window'
WebUI.switchToWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.console1)

'Wait for the canvas element containing the VM console to appear.\r\nThis step is used to measure console load time'
WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), consoleWaitTime, FailureHandling.OPTIONAL)

'Switch back to the main window'
WebUI.switchToWindowTitle('PCTE Portal')

'Enter the second console name into the VM filter'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Ranges_VMFilter'), GlobalVariable.console2)

'Wait 5 seconds for the filtering to finish'
TestSupport.delay(5)

'View the second console'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_DynamicRangeViewConsole', [('consoleName') : GlobalVariable.console2]))

'Wait for the console window to load'
TestSupport.delay(2)

'Switch the newly launched console window'
WebUI.switchToWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.console2)

'Wait for the canvas element containing the VM console to appear.\r\nThis step is used to measure console load time'
WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), consoleWaitTime, FailureHandling.OPTIONAL)

'Switch back to the main window'
WebUI.switchToWindowTitle('PCTE Portal')

'Enter the third console name into the VM filter'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Ranges_VMFilter'), GlobalVariable.console3)

'Wait 5 seconds for the filtering to finish'
TestSupport.delay(5)

'View the third console'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_DynamicRangeViewConsole', [('consoleName') : GlobalVariable.console3]))

'Wait for the console window to load'
TestSupport.delay(2)

'Switch the newly launched console window'
WebUI.switchToWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.console3)

'Wait for the canvas element containing the VM console to appear.\r\nThis step is used to measure console load time'
WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), consoleWaitTime, FailureHandling.OPTIONAL)

'Switch back to the main window'
WebUI.switchToWindowTitle('PCTE Portal')

'Enter the fourth console name into the VM filter'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Ranges_VMFilter'), GlobalVariable.console4)

'Wait 5 seconds for the filtering to finish'
TestSupport.delay(5)

'View the fourth console'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_DynamicRangeViewConsole', [('consoleName') : GlobalVariable.console4]))

'Wait for the console window to load'
TestSupport.delay(2)

'Switch the newly launched console window'
WebUI.switchToWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.console4)

'Wait for the canvas element containing the VM console to appear.\r\nThis step is used to measure console load time'
WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), consoleWaitTime, FailureHandling.OPTIONAL)

'Switch back to the main window'
WebUI.switchToWindowTitle('PCTE Portal')

'Enter the fifth console name into the VM filter'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Ranges_VMFilter'), GlobalVariable.console5)

'Wait 5 seconds for the filtering to finish'
TestSupport.delay(5)

'View the fifth console'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_DynamicRangeViewConsole', [('consoleName') : GlobalVariable.console5]))

'Wait for the console window to load'
TestSupport.delay(2)

'Switch the newly launched console window'
WebUI.switchToWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.console5)

'Wait for the canvas element containing the VM console to appear.\r\nThis step is used to measure console load time'
WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), consoleWaitTime, FailureHandling.OPTIONAL)

'Delay to show the console. This can be changed to make the test last longer'
TestSupport.delay(testRunTime)

'Close the first console window'
WebUI.closeWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.console1)

'Close the second console window'
WebUI.closeWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.console2)

'Close the third console window'
WebUI.closeWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.console3)

'Close the fourth console window'
WebUI.closeWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.console4)

'Close the fifth console window'
WebUI.closeWindowTitle((('PCTE Portal (Console): ' + GlobalVariable.range_name) + ', ') + GlobalVariable.console5)

'Switch back to the main window'
WebUI.switchToWindowTitle('PCTE Portal')

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

