#ifndef CALCULATOR_TEST_INCLUDED_H
#define CALCULATOR_TEST_INCLUDED_H
#include "cxxtest-4.4/cxxtest/TestSuite.h"
#include <iostream>
#include "FourFunCalc.cpp"

class CalculatorTestSuite : public CxxTest::TestSuite
{
public:
	FourFunCalc * calc;

	void setUp()
	{
		calc = new FourFunCalc();
	}

	void tearDown()
	{
		delete calc;
	}

	// testing basic functionality: a single arithmetic problem
	void testBasics()
	{// 51 + 9 = 60
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 0.0);
		calc->addDigit(5);
		calc->addDigit(1);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 51.0);
		calc->setOperation(Operation::ADD);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 51.0); // still displays old number until you put something new in
		calc->addDigit(9);
		TS_ASSERT_EQUALS(calc->evaluate(), 60);
	}

	// testing post-evaluation behavior
	void testPostEval()
	{// 1 - 3 = -2
		calc->addDigit(1);
		calc->setOperation(Operation::SUB);
		calc->addDigit(3);
		TS_ASSERT_EQUALS(calc->evaluate(), -2.0);
		// now confirm that the calculator changes state properly
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), -2.0); // current number is result
		calc->setOperation(Operation::DIV);
		calc->addDigit(2);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 2.0);
		TS_ASSERT_EQUALS(calc->evaluate(), -1); // result can be used in new calculation
		calc->addDigit(8);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 8.0); // new digits overwrite result of previous calculation
	}

	// testing two consecutive operations, or an operation followed immediately by evaluate
	void testDoubleUp()
	{// 5 * + should resolve as 5 * 5 +
		calc->addDigit(5);
		calc->setOperation(Operation::MUL);
		calc->setOperation(Operation::ADD);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 25.0);
		// should now be 25.0 + [no number]
		TS_ASSERT_EQUALS(calc->evaluate(), 50.0);
	}

	// testing decimals
	void testDecimal()
	{
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 0.0);
		calc->addDigit(5);
		calc->addDecimal();
		calc->addDigit(1);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 5.1);
		calc->setOperation(Operation::ADD);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 5.1); // still displays old number until you put something new in
		calc->addDecimal();
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 0.0);
		calc->addDigit(9);
		TS_ASSERT_EQUALS(calc->evaluate(), 6.0);
	}

	// testing negative
	void testNegative()
	{
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 0.0);
		calc->addDigit(7);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 7.0);
		TS_ASSERT(calc->negate());
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), -7.0);
		calc->addDigit(9);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), -79.0);
		calc->setOperation(Operation::ADD);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), -79.0); // still displays old number until you put something new in
		TS_ASSERT(calc->negate());
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 0.0); // technically it's stored as -0 but that's not any different from 0
		calc->addDigit(9);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), -9.0);
		TS_ASSERT(!(calc->negate()));
		TS_ASSERT_EQUALS(calc->evaluate(), -70.0);
	}
	
	// testing backspace
	void testBspace()
	{
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 0.0);
		calc->addDigit(7);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 7.0);
		calc->addDecimal();
		calc->addDigit(9);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 7.9);
		calc->backspace();
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 7.0);
		calc->backspace(); // should get rid of the decimal point
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 7.0);
		calc->addDigit(3);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 73.0)
		calc->backspace();
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 7.0)
		calc->setOperation(Operation::ADD);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 7.0); // still displays old number until you put something new in
		calc->backspace();
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 7.0); // backspace does nothing here
		calc->addDigit(9);
		calc->backspace();
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 0.0);
	}

	// testing memory
	void testMem()
	{
		calc->addDigit(3);
		calc->memPlus();
		calc->addDigit(5);
		calc->memMinus();
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), 35.0);
		TS_ASSERT_EQUALS(calc->memRecall(), -32.0);
		TS_ASSERT_EQUALS(calc->returnCurrentNumber(), -32.0);
		calc->memClear();
		TS_ASSERT_EQUALS(calc->memRecall(), 0.0);
	}

	// testing error cases
	void testErr()
	{
		TS_ASSERT_THROWS(calc->setOperation(Operation(7)), std::invalid_argument);
		TS_ASSERT_THROWS(calc->evaluate(), std::runtime_error);
	}

	// testing toString
	void testToString()
	{
		TS_ASSERT_EQUALS(*(calc->toString()), "0");
		calc->addDigit(4);
		TS_ASSERT_EQUALS(*(calc->toString()), "4");
		calc->addDigit(9);
		TS_ASSERT_EQUALS(*(calc->toString()), "49");
		calc->addDecimal();
		TS_ASSERT_EQUALS(*(calc->toString()), "49.");
		calc->addDigit(3);
		TS_ASSERT_EQUALS(*(calc->toString()), "49.3");
		calc->negate();
		TS_ASSERT_EQUALS(*(calc->toString()), "-49.3");
	}
};
#endif