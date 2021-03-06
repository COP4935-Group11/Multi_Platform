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

for (int i = 0; i < numIterations; i++) {
	TestSupport.delay(tabDelay)
	
	WebUI.click(findTestObject('Page_PCTE Portal/div_Events_EventStandingsTab'))
	
	WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/div_Events_EventStandingsDynamicEntry', [('userName') : GlobalVariable.name]), 120)
	
	TestSupport.delay(tabDelay)
	
	WebUI.click(findTestObject('Page_PCTE Portal/div_Events_CurrentStatusTab'))
	
	WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/div_Events_ScoreGrid'), 120)
	
	TestSupport.delay(tabDelay)
	
	WebUI.click(findTestObject('Page_PCTE Portal/div_Events_ParticipantsTab'))
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
