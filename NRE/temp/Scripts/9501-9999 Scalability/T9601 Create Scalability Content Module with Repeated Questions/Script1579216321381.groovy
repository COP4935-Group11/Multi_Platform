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

'Open the Dashboard (apps)'
WebUI.click(findTestObject('Page_PCTE Portal/button_Apps'))

'Wait for the Contents app button to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Content'), 30)

'Select the Content area (app)'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content'))

'Wait for the Content Modules button to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Content_ContentModules'), 30)

'Click on "Content Modules"'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModules'))

'Wait for the New Content Module button to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/button_Content_NewContentModule'), 30)

'Click on "New Content Module"'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_NewContentModule'))

'Enter name for Content Module'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Content_ContentModuleName'), moduleName)

'Enter description for Content Module'
WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleDescription'), 'Create a content module with one task.')

'Set duration to 1 day'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Content_ContentModuleDuration'), '1')

'Select Tasks tab'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModuleTasks'))

'Add a number of question sets according to the parameter'
for (int i = 0; i < numQuestionSets; i++) {
    'Create a new chain'
    WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModuleCreateNewChain'))

    'Enter a question'
    WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleTaskName'), 'What does PCTE stand for?')

    'Enter the correct answer'
    WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleShortAnswer1'), 'Persistent Cyber Training Environment')

    'Click to add a task to the chain'
    WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModuleCreateNewChain'))

    'Set the task name'
    WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleTaskName'), 'Which of the following letters is in "PCTE?"')

    WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/i_Content_ContentModuleResponseTypeArrow'), 30)

    'Select "Short Answer" to open drop down list'
    WebUI.click(findTestObject('Page_PCTE Portal/i_Content_ContentModuleResponseTypeArrow'))

    'Click the "Multiple Choice" item'
    WebUI.click(findTestObject('Page_PCTE Portal/div_Content_ContentModuleMultipleChoice'))

    'Add a choice'
    WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModuleAddChoice'))

    'Add a choice'
    WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModuleAddChoice'))

    'Add a choice'
    WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModuleAddChoice'))

    'Wait for the choice descriptions to appear'
    WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleChoice1'), 30)

    'Set choice 1'
    WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleChoice1'), 'C')

    'Set choice 2'
    WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleChoice2'), 'G')

    'Set choice 3'
    WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleChoice3'), 'U')

    'Set choice 4'
    WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleChoice4'), 'S')

    'Set choice 5'
    WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleChoice5'), 'A')

    'Create a second chain'
    WebUI.click(findTestObject('Page_PCTE Portal/span_Content_ContentModuleCreateNewChain'))

    'Enter a question'
    WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleTaskName'), 'Who is the mascot for UCF\'s athletic programs?')

    'Enter the correct answer'
    WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_ContentModuleShortAnswer1'), 'Knightro')
}

'Wait for the Create button to be clickable'
for (int i = 0; i < 10; i++) {
    if (WebUI.waitForElementClickable(findTestObject('Page_PCTE Portal/button_Content_ContentModuleCreate'), 30, FailureHandling.OPTIONAL)) {
        break
    }
}

'Click create!'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_ContentModuleCreate'))

def setup() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Check to see if the test case is being run inside of a test case'
    if (!(support.isInTestSuite())) {
        'Log into the portal'
        support.login(GlobalVariable.username, GlobalVariable.password)
    }
}

def teardown() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Delete the module that was created, but only if the test case is not being run inside of a test stuite'
    if (!(support.isInTestSuite())) {
        'Delete our module and use SharedTestData since we are in teardown'
        SharedTestData data = SharedTestData.getInstance()

        data.deleteContentModule('PCTE Scalability ' + GlobalVariable.username)

        'Log out of the portal'
        support.logout()
    }
}

