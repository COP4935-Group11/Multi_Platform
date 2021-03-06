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

WebUI.click(findTestObject('Page_PCTE Portal/button_Apps'))

WebUI.waitForElementPresent(findTestObject('Page_PCTE portal/span_Events'), 30)

WebUI.click(findTestObject('Page_PCTE Portal/span_Events'))

'Search for the event'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Events_EventSearch'), eventName)

TestObject eventObject = findTestObject('Page_PCTE Portal/a_Events_EventListSpecificTest', [('specificTestName'): eventName])

'Wait for the event to appear'
for (int i = 0; i < 10; i++) {
	if (WebUI.verifyElementClickable(eventObject, FailureHandling.OPTIONAL)) {
		break
	}
}

'Open the event'
WebUI.click(eventObject)

WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/button_Events_EnterEvent'), 30)

'Enter the event'
WebUI.click(findTestObject('Page_PCTE Portal/button_Events_EnterEvent'))

for (int i = 0; i < numIterations; i++) {
	'Wait for some time and... think'
	TestSupport.delay(answerDelay)
	
	'Answer the first question'
	WebUI.setText(findTestObject('Page_PCTE Portal/input_Events_EventShortAnswer'), 'Persistent Cyber Training Environment')
	
	TestSupport.delay(1)
	
	'Click Submit'
	WebUI.click(findTestObject('Page_PCTE Portal/button_Event_AnswerSubmit'))
	
	'Think about answer'
	TestSupport.delay(answerDelay)
	
	'Answer the second question'
	WebUI.click(findTestObject('Page_PCTE Portal/div_Events_MultipleChoiceDynamicItem', [('answer'): 'C']))
	
	TestSupport.delay(1)
	
	'Click Submit'
	WebUI.click(findTestObject('Page_PCTE Portal/button_Event_AnswerSubmit'))
	
	'Think about answer'
	TestSupport.delay(answerDelay)
	
	'Answer the third question'
	WebUI.setText(findTestObject('Page_PCTE Portal/input_Events_EventShortAnswer'), 'Knightro')
	
	TestSupport.delay(1)
	
	'Click Submit'
	WebUI.click(findTestObject('Page_PCTE Portal/button_Event_AnswerSubmit'))
}


def setup() {
	'Get the instance of the test support class'
	TestSupport support = TestSupport.getInstance()

	support.login(GlobalVariable.username, GlobalVariable.password)
}

def teardown() {
	'Get the instance of the test support class'
	TestSupport support = TestSupport.getInstance()

	support.logout()
}
