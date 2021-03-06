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

'Wait for the Apps button to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/button_Apps'), 30)

'Click on the Apps button'
WebUI.click(findTestObject('Page_PCTE Portal/button_Apps'))

'Wait for the Events app to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Events'), 30)

'Select the Events area (app)'
WebUI.click(findTestObject('Page_PCTE Portal/span_Events'))

'Wait for the Schedule New Event button to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/a_Events_ScheduleNewEvent'), 30)

'Click "Schedule New Event"'
WebUI.click(findTestObject('Page_PCTE Portal/a_Events_ScheduleNewEvent'))

'Wait for the Event Name field to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/input_Events_NewEventName'), 30)

'Set the name'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Events_NewEventName'), eventName)

'Focus on the training package selector'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/input_Events_NewEventSelectTrainingPackage'), packageName)

'Delay for a second'
TestSupport.delay(1)

'Hit enter'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/input_Events_NewEventSelectTrainingPackage'), Keys.chord(Keys.ENTER))

'Set the description'
WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Events_NewEventDescription'), eventName + ' Description')

'Wait for Enable Leaderboard checkbox to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/i_Events_NewEventEnableLeaderboard'), 30)

'Enable using the leaderboard'
WebUI.click(findTestObject('Page_PCTE Portal/i_Events_NewEventEnableLeaderboard'))

'Enable revealing answers'
WebUI.click(findTestObject('Page_PCTE Portal/i_Events_NewEventEnableRevealingAnswers'))

'Delay for a second'
TestSupport.delay(1)

'Switch to participants pane'
WebUI.click(findTestObject('Page_PCTE Portal/div_Events_EventParticipants'))

'Add PCTE org'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Events_NewEventParticipant'), 'PCTE')

'Hit enter'
WebUI.sendKeys(findTestObject('Page_PCTE Portal/input_Events_NewEventParticipant'), Keys.chord(Keys.ENTER))

'Delay for a second'
TestSupport.delay(1)

'Go through the scalability users and add them as participants'
CSVData userData = findTestData('Scalability User Logins')

'Select all the participants for the event'
for (def index : (participantNumStart..participantNumEnd)) {
    WebUI.click(findTestObject('Page_PCTE Portal/label_Events_NewEventParticipantDynamicItem', [('participantName') : userData.getValue(
                    'Name', index)]))
}

'Add the users to the event'
WebUI.click(findTestObject('Page_PCTE Portal/button_Events_NewEventsAddParticipant'))

'Switch to schedule pane'
WebUI.click(findTestObject('Page_PCTE Portal/div_Events_Schedule'))

'Delay for a second'
TestSupport.delay(1)

'Enter the event duration to be 24 hours'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Events_NewEventDuration'), '24h')

'Confirm the event duration'
WebUI.click(findTestObject('Page_PCTE Portal/i_Events_NewEventEventDurationConfirm'))

'Set the start time based on parameter'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Events_NewEventStartTime'), startTime)

'Confirm the start time'
WebUI.click(findTestObject('Page_PCTE Portal/i_Events_NewEventStartTimeConfirm'))

'Click on today'
WebUI.click(findTestObject('Page_PCTE Portal/div_Events_ScheduleToday'))

'Delay for a second'
TestSupport.delay(1)

'Save the schedule'
WebUI.click(findTestObject('Page_PCTE Portal/button_Events_ScheduleSave'))

'Delay for a second'
TestSupport.delay(60)

'Create the event!'
WebUI.click(findTestObject('Page_PCTE Portal/button_Events_CreateEvent'))

def setup() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Check to see if the test case is being run in a test suite'
    if (!(support.isInTestSuite())) {
        'Log into the portal'
        support.login(GlobalVariable.username, GlobalVariable.password)

        'Create a survey module to use'
        SharedTestData data = SharedTestData.getInstance()

        TestSupport.delay(3)

        'Create a training package to use'
        data.createContentModule(moduleName)

        TestSupport.delay(3)

        data.createTrainingPackage(packageName, moduleName)

        TestSupport.delay(3)
    }
}

def teardown() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Check to see if the test case is running in a suite'
    if (!(support.isInTestSuite())) {
        'Delete survey module we made'
        SharedTestData data = SharedTestData.getInstance()

        'Delete the lab event we tested'
        data.deleteLabEvent(eventName)

        TestSupport.delay(1)

        'Delete the training package we made'
        data.deleteTrainingPackage(packageName)

        TestSupport.delay(1)

        'Delete the survey module we made'
        data.deleteContentModule(moduleName)

        TestSupport.delay(1)

        'Log out of the portal'
        support.logout()
    }
}

