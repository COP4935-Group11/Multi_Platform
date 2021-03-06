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


def numIterations = (testRunTime / 60) * timeBetweenTickets

for (int i = 0; i < numIterations; i++) {
   
   'Wait for the user menu to become available'
   WebUI.waitForElementClickable(findTestObject('Page_PCTE Portal/div_UserMenu'), 300)

   'Click on the user menu'
   WebUI.click(findTestObject('Page_PCTE Portal/div_UserMenu'))

   'Wait for the Contact Support button to appear'
   WebUI.waitForElementClickable(findTestObject('Page_PCTE Portal/span_Dashboard_ContactSupport'), 300)

   'Click on the Contact Support button'
   WebUI.click(findTestObject('Page_PCTE Portal/span_Dashboard_ContactSupport'))

   'Check to see if the language prompt has appeared'
   if (WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/input_HelpCenter_LanguageContinue'), 10, FailureHandling.OPTIONAL)) {
	   WebUI.click(findTestObject('Page_PCTE Portal/input_HelpCenter_LanguageContinue'), FailureHandling.OPTIONAL)
   }
   
   'Check to see if the avatar prompt has appeared'
   if (WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/input_HelpCenter_Next'), 10, FailureHandling.OPTIONAL)) {
	   WebUI.click(findTestObject('Page_PCTE Portal/input_HelpCenter_Next'), FailureHandling.OPTIONAL)
   }

   'Wait for the support search box to appear'
   WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/input_HelpCenter_SupportSearch'), 300)
   
   'Input Request into the support search box'
   WebUI.setText(findTestObject('Page_PCTE Portal/input_HelpCenter_SupportSearch'), 'Request')
   
   'Wait for the Request Help link to appear'
   WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/a_HelpCenter_RequestTechnicalSupport'), 300)
   
   'Click on the Request Help link'
   WebUI.click(findTestObject('Page_PCTE Portal/a_HelpCenter_RequestTechnicalSupport'))

   'Wait for the Summary field to be present'
   WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/input_HelpCenter_Summary'), 300)
   
   'Form the title for the ticket'
   def ticketSummary = 'Scaling Help Ticket ' + i.toString() + '!!'

   'Input a summary for the report'
   WebUI.setText(findTestObject('Page_PCTE Portal/input_HelpCenter_Summary'), ticketSummary)
   
   'Wait for the description text area to appear'
   WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/textarea_HelpCenter_Description'), 300)
   
   'Enter a description of the ticket'
   WebUI.setText(findTestObject('Page_PCTE Portal/textarea_HelpCenter_Description'), 'This is a test.')

   'Wait for the component dropdown to appear'
   WebUI.waitForElementVisible(findTestObject('Page_PCTE Portal/ul_HelpCenter_ReportComponentDropdown'), 300)

   'Click on the component dropdown'
   WebUI.click(findTestObject('Page_PCTE Portal/ul_HelpCenter_ReportComponentDropdown'))
   
   'Wait for the Range Config component item to be present'
   WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/div_HelpCenter_RangeConfigComponent'), 300)
   
   'Click on the Range Config component item'
   WebUI.click(findTestObject('Page_PCTE Portal/div_HelpCenter_RangeConfigComponent'))

   'Wait for the create ticket button to be clickable'
   WebUI.waitForElementClickable(findTestObject('Page_PCTE Portal/button_HelpCenter_CreateTicket'), 300)
   
   'Cick on the create ticket button'
   WebUI.click(findTestObject('Page_PCTE Portal/button_HelpCenter_CreateTicket'))
   
   'Wait for the specified delay between creating tickets'
   WebUI.delay(timeBetweenTickets)
   
   'Navigate to the home page to prepare for the next ticket creation'
   WebUI.navigateToUrl(GlobalVariable.portal_url)
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

    'Log out of the portal'
    testSupport.logout()
}

