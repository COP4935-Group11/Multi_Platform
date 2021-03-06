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

'Wait for the user menu to become available'
WebUI.waitForElementClickable(findTestObject('Page_PCTE Portal/div_UserMenu'), 300)

'Click on the user menu'
WebUI.click(findTestObject('Page_PCTE Portal/div_UserMenu'))

'Wait for the Chat button to appear'
WebUI.waitForElementClickable(findTestObject('Page_PCTE Portal/span_Dashboard_Chat'), 300)

'Click on the Chat button'
WebUI.click(findTestObject('Page_PCTE Portal/span_Dashboard_Chat'))

'Wait a little bit for the page to load'
WebUI.delay(5)

'Switch to the new window/tab in which Mattermost opened'
WebUI.switchToWindowIndex(1)

'Wait for the Keycloak button to appear'
WebUI.waitForElementClickable(findTestObject('Page_Mattermost/span_Chat_Keycloak'), 30)

'Click on the Keycloak button'
WebUI.click(findTestObject('Page_Mattermost/span_Chat_Keycloak'))

'Check to see if the Mattermost tutorial has been displayed'
if (WebUI.waitForElementPresent(findTestObject('Page_Mattermost/span_Chat_SkipTutorial'), 10, FailureHandling.OPTIONAL)) {
    WebUI.click(findTestObject('Page_Mattermost/span_Chat_SkipTutorial'), FailureHandling.OPTIONAL)
}

'Wait for the chat text area to appear'
WebUI.waitForElementPresent(findTestObject('Page_Mattermost/textarea_Chat_Input'), 30)

def numIterations = (testRunTime / 60) * chatMessagesPerMinute

Random random = new Random()

def currTeam = random.nextInt(numEvents)

def message = chatMessages[random.nextInt(chatMessages.size())]

for (int i = 0; i < numIterations; i++) {
    'Wait for a while based on how frequently messages should be spammed'
    WebUI.delay(60 / chatMessagesPerMinute)

    def teamSelector = findTestObject('Page_Mattermost/div_Chat_DynamicTeamSelector', [('teamNum') : Integer.toString(currTeam + 1)])
    
    'Wait for the team button to appear'
    WebUI.waitForElementPresent(teamSelector, 10)
    
    'Click on the team'
    WebUI.click(teamSelector)
    
    'Wait for the text are to appear'
    WebUI.waitForElementPresent(findTestObject('Page_Mattermost/textarea_Chat_Input'), 300)

    'Enter some text into the text chat area'
    WebUI.setText(findTestObject('Page_Mattermost/textarea_Chat_Input'), message)
    
    'Delay for a second'
    WebUI.delay(1)

    'Hit the Enter key to send the chat message'
    WebUI.sendKeys(findTestObject('Page_Mattermost/textarea_Chat_Input'), Keys.chord(Keys.ENTER))
    
    'Move the current team to point to the next team'
    currTeam++
    currTeam = currTeam % numEvents
}

def setup() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Check to see if this test case is being run with a non-scalability user profile'
    if (GlobalVariable.metaClass.hasProperty(GlobalVariable, 'orgadmin_username') && GlobalVariable.orgadmin_username != null) {
       'This is a non-scalability user profile'
       support.login(GlobalVariable.orgadmin_username, GlobalVariable.orgadmin_password, GlobalVariable.orgadmin_key)
    }
    else {
       'This is a scalability user profile'
       support.staggeredLogin(GlobalVariable.username, GlobalVariable.password, GlobalVariable.name, userBatchSize, timeBetweenBatches, suiteSize)
    }
}

def loiterDelay() {
    'If a step failed in the test loiter in the portal for the rest of the test duration'
    WebUI.delay(testRunTime)
}

def teardown() {
    'Get an instance of the test support class'
    TestSupport testSupport = TestSupport.getInstance()

    'Switch back to the main portal tab'
    WebUI.switchToWindowIndex(0)

    'Log out of the portal'
    testSupport.logout()
}

