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



// Run the feature file
CucumberKW.runFeatureFile('Include/features/Portal-Login/ssoLoginTest.feature')


def setup()
{
   // Store parameters for test
   SharedTestData data = SharedTestData.getInstance()
   data.addTestParam("testCode", test_code)
   data.addTestParam("exerciseEventToUse", use_exercise_event_named)
   
   // Create support test data if running standalone (otherwise, a test suite that would make it)
   TestSupport support = TestSupport.getInstance()
   if (!support.isInTestSuite())
   {
      // Login as manager
      support.login(GlobalVariable.manager_username, GlobalVariable.manager_password, GlobalVariable.manager_key)

      // Make an exercise event for test to use and add a user
      String[] participants = [ GlobalVariable.user1_name ]
      data.createExerciseEvent(use_exercise_event_named)
      TestSupport.delay(1)
      data.addParticipantsToExerciseEvent(use_exercise_event_named, participants)
   
      // And finally logout
      support.logout()
   }
}


def teardown()
{
   // First, make sure we are logged out from any left over user
   TestSupport support = TestSupport.getInstance()
   support.logout()

   // Clean-up test data if running standalone (otherwise, a test suite will do it)
   if (!support.isInTestSuite())
   {
      // Login as manager
      support.login(GlobalVariable.manager_username, GlobalVariable.manager_password, GlobalVariable.manager_key)

      // Clean-up data we made
      SharedTestData data = SharedTestData.getInstance()
      data.archiveExerciseEvent(use_exercise_event_named)

      // And logout
      support.logout()
   }
}
