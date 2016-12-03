#ifndef CHAIN_TEST_INCLUDED_H
#define CHAIN_TEST_INCLUDED_H
#include "cxxtest-4.4/cxxtest/TestSuite.h"
#include <iostream>
#include "DigitChain.cpp"

class DigitChainTestSuite : public CxxTest::TestSuite
{
public:
	DigitChain * chain;

	void setUp()
	{
		chain = new DigitChain();
	}

	void tearDown()
	{
		delete chain;
	}

	// tests for adding digits and computing value
	void testBasics()
	{
		TS_ASSERT_EQUALS(chain->computeValue(), 0.0);
		chain->addDigit(0);
		chain->addDigit(3);
		TS_ASSERT_EQUALS(chain->computeValue(), 3.0);
		chain->addDigit(0);
		chain->addDigit(5);
		TS_ASSERT_EQUALS(chain->computeValue(), 305.0);
	}

	// tests for a decimal point
	void testDecimal()
	{
		TS_ASSERT_EQUALS(chain->computeValue(), 0.0);
		chain->addDigit(0);
		chain->addDigit(2);
		TS_ASSERT_EQUALS(chain->computeValue(), 2.0);
		chain->addDigit(1);
		chain->addDigit(8);
		TS_ASSERT_EQUALS(chain->computeValue(), 218.0);
		chain->setDecimal();
		TS_ASSERT_EQUALS(chain->computeValue(), 218.0);
		chain->addDigit(7);
		TS_ASSERT_EQUALS(chain->computeValue(), 218.7);
		chain->addDigit(3);
		TS_ASSERT_EQUALS(chain->computeValue(), 218.73);
	}

	// tests for backspacing, including backspacing a decimal point
	void testBackspace()
	{
		chain->addDigit(0);
		chain->addDigit(2);
		chain->addDigit(1);
		chain->addDigit(8);
		chain->setDecimal();
		chain->addDigit(7);
		chain->addDigit(3);
		TS_ASSERT_EQUALS(chain->computeValue(), 218.73);
		chain->backspace();
		TS_ASSERT_EQUALS(chain->computeValue(), 218.7);
		chain->backspace();
		TS_ASSERT_EQUALS(chain->computeValue(), 218.0);
		chain->addDigit(3);
		TS_ASSERT_EQUALS(chain->computeValue(), 218.3);
		chain->backspace();
		chain->backspace(); // testing that backspace cancelled decimal point
		TS_ASSERT_EQUALS(chain->computeValue(), 218.0);
		chain->addDigit(8);
		TS_ASSERT_EQUALS(chain->computeValue(), 2188.0);
		chain->backspace();
		TS_ASSERT_EQUALS(chain->computeValue(), 218.0);
		chain->backspace();
		TS_ASSERT_EQUALS(chain->computeValue(), 21.0);
		chain->backspace();
		TS_ASSERT_EQUALS(chain->computeValue(), 2.0);
		chain->backspace();
		TS_ASSERT_EQUALS(chain->computeValue(), 0.0);
		chain->backspace();
		TS_ASSERT_EQUALS(chain->computeValue(), 0.0);
	}

	// tests for negative numbers
	void testNegative()
	{
		TS_ASSERT_EQUALS(chain->computeValue(), 0.0);
		chain->addDigit(0);
		chain->addDigit(2);
		chain->addDigit(1);
		chain->addDigit(8);
		chain->setDecimal();
		chain->addDigit(7);
		chain->addDigit(3);
		chain->switchNegative();
		TS_ASSERT_EQUALS(chain->computeValue(), -218.73);
		chain->switchNegative();
		TS_ASSERT_EQUALS(chain->computeValue(), 218.73);
	}

	void testInvalidDigit()
	{
		TS_ASSERT_THROWS(chain->addDigit(12), std::invalid_argument);
	}

	// tests for toString with and without decimal and negative
	void testToString()
	{
		TS_ASSERT_EQUALS(*(chain->toString()), "0");
		chain->addDigit(4);
		TS_ASSERT_EQUALS(*(chain->toString()), "4");
		chain->addDigit(9);
		TS_ASSERT_EQUALS(*(chain->toString()), "49");
		chain->setDecimal();
		TS_ASSERT_EQUALS(*(chain->toString()), "49.");
		chain->addDigit(3);
		TS_ASSERT_EQUALS(*(chain->toString()), "49.3");
		chain->switchNegative();
		TS_ASSERT_EQUALS(*(chain->toString()), "-49.3");
	}
};

#endif