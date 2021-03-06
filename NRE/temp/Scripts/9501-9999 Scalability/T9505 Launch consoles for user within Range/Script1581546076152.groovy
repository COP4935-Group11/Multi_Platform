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

'Wait for the Live Action Events tab to appear'
WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/span_Events_LiveActionEvents'), 600)

'Click on the Live Action Events tab'
WebUI.click(findTestObject('Page_PCTE Portal/span_Events_LiveActionEvents'))

'Wait for the page to load'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/input_Events_EventSearch'), 600)

'Search for the event that is to be entered'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Events_EventSearch'), GlobalVariable.range_name)

'Wait for the event to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/a_Events_DynamicEventItem', [('eventName') : GlobalVariable.range_name]), 
    600)

'Wait a few more seconds to make sure that click is not intercepted'
WebUI.delay(5)

'Enter the event'
WebUI.click(findTestObject('Page_PCTE Portal/a_Events_DynamicEventItem', [('eventName') : GlobalVariable.range_name]))

'Wait for the event page to load'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Events_VirtualMachines'), 600)

'Click on the Virtual Machine section'
WebUI.click(findTestObject('Page_PCTE Portal/span_Events_VirtualMachines'))

'Launch each of the consoles'
for (int currConsole = 0; currConsole < numConsoles; currConsole++) {
    'Switch back to the main window'
    WebUI.switchToWindowIndex(0)

    'Wait 5 seconds for the window to switch'
    WebUI.delay(5)

    'Repeatedly try to click on the Open Console button'
    for (int i = 0; i < 20; i++) {
		'Wait for the Virtual Machine section to load'
		WebUI.waitForElementClickable(findTestObject('Page_PCTE Portal/input_Events_VirtualMachineSearch'), 30, FailureHandling.OPTIONAL)
	
		'Clear the text in VM search box'
		WebUI.setText(findTestObject('Page_PCTE Portal/input_Events_VirtualMachineSearch'), '')
	
		'Wait for the search box to clear'
		WebUI.delay(5)
	
		'Enter the name of the desired VM'
		WebUI.setText(findTestObject('Page_PCTE Portal/input_Events_VirtualMachineSearch'), GlobalVariable.consoles[currConsole])
	
		'Fetch the Open Console button object to be clicked'
		TestObject openConsoleButton = findTestObject('Page_PCTE Portal/button_Events_DynamicVMOpenConsole', [('vmName') : GlobalVariable.consoles[
				currConsole]])
		
        'Wait for the VM to appear on the page'
        WebUI.waitForElementClickable(openConsoleButton, 30, FailureHandling.OPTIONAL)

        'Click on the Open Console button'
        if (WebUI.verifyElementClickable(openConsoleButton, FailureHandling.OPTIONAL)) {
            WebUI.click(openConsoleButton, FailureHandling.OPTIONAL)

            break
        }
    }
    
    'Wait for the console window to load'
    WebUI.delay(2)

    'Switch the newly launched console window'
    WebUI.switchToWindowIndex(currConsole + 1, FailureHandling.OPTIONAL)

    'Wait for the canvas element containing the VM console to appear.\r\nThis step is used to measure console load time'
    WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/canvas_Range_MainCanvas'), 600, FailureHandling.OPTIONAL)
}

'Delay to show the console. This can be changed to make the test last longer'
TestSupport.delay(testRunTime)

'Switch back to the main window'
WebUI.switchToWindowIndex(0)

def setup() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Check to see if this test case is being run with a non-scalability user profile'
    if (GlobalVariable.metaClass.hasProperty(GlobalVariable, 'orgadmin_username') && (GlobalVariable.orgadmin_username != 
    null)) {
        'This is a non-scalability user profile'
        support.login(GlobalVariable.orgadmin_username, GlobalVariable.orgadmin_password, GlobalVariable.orgadmin_key)
    } else {
        'This is a scalability user profile'
        support.staggeredLogin(GlobalVariable.username, GlobalVariable.password, GlobalVariable.name, userBatchSize, timeBetweenBatches, 
            suiteSize)
    }
}

def loiterDelay() {
    'If a step failed in the test loiter in the portal for the rest of the test duration'
    WebUI.delay(testRunTime)
}

def teardown() {
    'Get an instance of the test support class'
    TestSupport testSupport = TestSupport.getInstance()

    'Close each of the console windows that were launched'
    for (int currConsole = 0; currConsole < numConsoles; currConsole++) {
        'Close the next window. As windows are closed this automatically moves the next window down to the second index'
        WebUI.closeWindowIndex(1, FailureHandling.OPTIONAL)
    }
    
    'Log out of the portal'
    testSupport.logout()
}

