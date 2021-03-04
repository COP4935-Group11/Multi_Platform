import com.ucf.pcte.CucumberKW
import com.ucf.pcte.gold.WebUI as WebUI
import static com.ucf.pcte.gold.WebUI.findTestObject
import cucumber.api.java.en.*
import com.ucf.pcte.CSVData
import com.constants.CSVSeparator
import com.configuration.RunConfiguration
import internal.GlobalVariable
import com.constants.*
import com.annotation.*

CucumberKW.runFeatureFile('Include/features/Register/Register-success.feature')

// def setup()
// {
//    print(A);
// }


// def teardown()
// {
//    print('-----teardown------');
// }
