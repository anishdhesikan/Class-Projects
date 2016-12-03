/* Generated file, do not edit */

#ifndef CXXTEST_RUNNING
#define CXXTEST_RUNNING
#endif

#define _CXXTEST_HAVE_STD
#define _CXXTEST_HAVE_EH
#define _CXXTEST_ABORT_TEST_ON_FAIL
#include <cxxtest/TestListener.h>
#include <cxxtest/TestTracker.h>
#include <cxxtest/TestRunner.h>
#include <cxxtest/RealDescriptions.h>
#include <cxxtest/TestMain.h>
#include <cxxtest/ParenPrinter.h>
#include <cxxtest/Win32Gui.h>

int main( int argc, char *argv[] ) {
 int status;
    CxxTest::GuiTuiRunner<CxxTest::Win32Gui, CxxTest::ParenPrinter>  tmp;
    CxxTest::RealWorldDescription::_worldName = "cxxtest";
    status = CxxTest::Main< CxxTest::GuiTuiRunner<CxxTest::Win32Gui, CxxTest::ParenPrinter>  >( tmp, argc, argv );
    return status;
}
bool suite_CalculatorTestSuite_init = false;
#include "..\..\..\CalculatorTestSuite.h"

static CalculatorTestSuite suite_CalculatorTestSuite;

static CxxTest::List Tests_CalculatorTestSuite = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CalculatorTestSuite( "../../../CalculatorTestSuite.h", 7, "CalculatorTestSuite", suite_CalculatorTestSuite, Tests_CalculatorTestSuite );

static class TestDescription_suite_CalculatorTestSuite_testBlank : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CalculatorTestSuite_testBlank() : CxxTest::RealTestDescription( Tests_CalculatorTestSuite, suiteDescription_CalculatorTestSuite, 23, "testBlank" ) {}
 void runTest() { suite_CalculatorTestSuite.testBlank(); }
} testDescription_suite_CalculatorTestSuite_testBlank;

#include "..\..\..\digitchaintestsuite.h"

static DigitChainTestSuite suite_DigitChainTestSuite;

static CxxTest::List Tests_DigitChainTestSuite = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_DigitChainTestSuite( "../../../digitchaintestsuite.h", 7, "DigitChainTestSuite", suite_DigitChainTestSuite, Tests_DigitChainTestSuite );

static class TestDescription_suite_DigitChainTestSuite_testBasics : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_DigitChainTestSuite_testBasics() : CxxTest::RealTestDescription( Tests_DigitChainTestSuite, suiteDescription_DigitChainTestSuite, 23, "testBasics" ) {}
 void runTest() { suite_DigitChainTestSuite.testBasics(); }
} testDescription_suite_DigitChainTestSuite_testBasics;

static class TestDescription_suite_DigitChainTestSuite_testDecimal : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_DigitChainTestSuite_testDecimal() : CxxTest::RealTestDescription( Tests_DigitChainTestSuite, suiteDescription_DigitChainTestSuite, 35, "testDecimal" ) {}
 void runTest() { suite_DigitChainTestSuite.testDecimal(); }
} testDescription_suite_DigitChainTestSuite_testDecimal;

static class TestDescription_suite_DigitChainTestSuite_testBackspace : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_DigitChainTestSuite_testBackspace() : CxxTest::RealTestDescription( Tests_DigitChainTestSuite, suiteDescription_DigitChainTestSuite, 53, "testBackspace" ) {}
 void runTest() { suite_DigitChainTestSuite.testBackspace(); }
} testDescription_suite_DigitChainTestSuite_testBackspace;

static class TestDescription_suite_DigitChainTestSuite_testNegative : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_DigitChainTestSuite_testNegative() : CxxTest::RealTestDescription( Tests_DigitChainTestSuite, suiteDescription_DigitChainTestSuite, 87, "testNegative" ) {}
 void runTest() { suite_DigitChainTestSuite.testNegative(); }
} testDescription_suite_DigitChainTestSuite_testNegative;

static class TestDescription_suite_DigitChainTestSuite_testInvalidDigit : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_DigitChainTestSuite_testInvalidDigit() : CxxTest::RealTestDescription( Tests_DigitChainTestSuite, suiteDescription_DigitChainTestSuite, 103, "testInvalidDigit" ) {}
 void runTest() { suite_DigitChainTestSuite.testInvalidDigit(); }
} testDescription_suite_DigitChainTestSuite_testInvalidDigit;

#include <cxxtest/Root.cpp>
const char* CxxTest::RealWorldDescription::_worldName = "cxxtest";
