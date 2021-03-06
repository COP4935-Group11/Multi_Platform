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

'Wait for the Content app button to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Content'), 30)

'Select the Content app'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content'))

'Wait for the Training Packages area to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/span_Content_TrainingPackages'), 30)

'Click on "Training Packages"'
WebUI.click(findTestObject('Page_PCTE Portal/span_Content_TrainingPackages'))

'Wait for the New Training Package button to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/button_Content_NewTrainingPackage'), 30)

'Click on "New Training Package"'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_NewTrainingPackage'))

'Enter name for Event Package'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Content_TrainingPackageName'), packageName)

'Enter description for Event Package'
WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_TrainingPackageDescription'), 'Create an event package with content and network.')

'Select Intermediate difficulty'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageDifficultyIntermediate'))

'Select Team package type'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageTypeIndividual'))

'Advance to next tab'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageDetailsNext'))

'Click to add an objective'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageAddCreateObjective'))

'Specify the objective description'
WebUI.setText(findTestObject('Page_PCTE Portal/textarea_Content_TrainingPackageObjectiveDescription'), packageName + ' Objective #1 Test Description')

'Click the blue action drop down'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageBlueAction'))

'Wait for the page to load'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageBlueActionProtect'), 30)

'Select "Protect"'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageBlueActionProtect'))

'Click the red severity drop down'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageRedAction'))

'Wait for the red action control to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageRedActionPersistence'), 30)

'Select "Persistence"'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageRedActionPersistence'))

'Click the blue assets drop down'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageBlueAssets'))

'Wait for the blue assets dropdown choices to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageBlueAssets'), 30)

'Select level 2'
WebUI.click(findTestObject('Page_PCTE Portal/div_Content_TrainingPackageBlueAssetsLevel2'))

'Select "Pass/Fail" evaluation'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageEvaluationPassFail'))

'Select time requirement of "None"'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageTimeRequirementsNone'))

'Set degradation threshold to 14'
WebUI.setText(findTestObject('Page_PCTE Portal/input_Content_TrainingPackageDegradationThreshold'), '14')

'Advance to next tab'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageObjectiveNext'))

'No prerequisites'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackagePrerequisitesNext'))

'Click "Add Content Modules"'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageAddContentModules'))

'Fetch the test object for the content module entry'
TestObject contentModuleObj = findTestObject('Page_PCTE Portal/div_Content_ContentModuleListSpecificTest', [('specificTestName') : moduleName])

'Wait for the content module entry to appear'
WebUI.waitForElementPresent(contentModuleObj, 30)

'Select our content module'
WebUI.click(contentModuleObj)

'Click "Add to Package"'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageAddToPackage'))

'Wait for the Next button to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageModulesNext'), 30)

'Advance to next tab'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageModulesNext'))

'Advance to next tab'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageModulesNext'))

'Advance to next tab'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageModulesNext'))

'Wait for the Confirm button to appear'
WebUI.waitForElementPresent(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageConfirm'), 30)

'Click "Confirm" to create the training package'
WebUI.click(findTestObject('Page_PCTE Portal/button_Content_TrainingPackageConfirm'))

def setup() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Check to see if this test case is being run inside of a test suite'
    if (!(support.isInTestSuite())) {
        'Log into the portal'
        support.login(GlobalVariable.username, GlobalVariable.password)

        'Create a content module to use'
        SharedTestData data = SharedTestData.getInstance()

        data.createContentModule(moduleName)

        TestSupport.delay(3)
    }
}

def teardown() {
    'Get the instance of the test support class'
    TestSupport support = TestSupport.getInstance()

    'Check to see if this test case is being run inside of a test suite'
    if (!(support.isInTestSuite())) {
        'Delete content module'
        SharedTestData data = SharedTestData.getInstance()

        data.deleteContentModule(moduleName)

        'Delete our training package and use SharedTestData since we are in teardown'
        data.deleteTrainingPackage(packageName)

        'Log out of the portal'
        support.logout()
    }
}

