#ifndef FF_CALC_INCLUDED_H
#define FF_CALC_INCLUDED_H
#include <stdexcept>
#include <string>
#include "DigitChain.h"
#include "Operation.h"

// holds two numbers, each as either a DigitChain or a double, and provides functions to manipulate them
// also holds one arithmetic operator
// also holds one number in the background, as per a caclulator's memory buttons
// the "current number" is the first number if the operator is unset, or the second if the operator is set
// if the current number is stored as a DigitChain, it can be manipulated, but if it is a double, it cannot.
// the current number will only be a double as a result of calling evaluate(), setNum1Double, or setNum2Double
class FourFunCalc
{
public:
	FourFunCalc();
	// adds a new digit to the right end of the current number
	// if the current number is stored as a double, it is lost and replaced with an empty DigitChain to put the new digit into.
	void addDigit(short);
	// add a decimal point to the current number
	// does nothing if the current number already has a decimal point or is stored as a double
	void addDecimal();
	// negates the current number, returns true if it is now negative
	bool negate();
	// sets the operation
	// if operation was set, first calls this->evaluate(), then sets the operation
	void setOperation(Operation);
	// evaluates the current expression. If second number is not set, uses the first number as both terms.
	// stores the result by calling setNum1Double
	// if operation is unset, throws runtime_error
	double evaluate();
	// calls the backspace() method of the current number
	// does nothing if the current number is stored as a double
	void backspace();
	// sets the first number as a double, sets flags to indicate this, returns the double
	// this method is only useful for external objects if we use this class as a core to build a scientific calculator on
	double setNum1Double(double num);
	// sets the second number as a double, sets flags to indicate this, returns the double
	// this method is only useful if we use this class as a core to build a scientific calculator on
	double setNum2Double(double num);
	// returns the current number as a double
	double returnCurrentNumber() const;
	// adds the current number to memory
	void memPlus();
	// subtracts the current number from memory
	void memMinus();
	// sets the current number to the memory number (as a double), returns the double
	double memRecall();
	// clears the memory number
	void memClear();
	// returns pointer the current number as a standard string - what should be displayed on the calculator screen. String is dynamically allocated.
	std::string * toString() const;
private:
	// the first number
	DigitChain* num1_;
	// the first number as a Double - used instead of DigitChains when a full number is given
	double num1Double_;
	// if is1Chain_, num1_ is a valid number and num1Double_ is not to be used. else, vice versa.
	// INVARIANT: num1_ will be NULL unless this is true
	bool is1Chain_;
	// the operation
	Operation operation;
	// the second number
	DigitChain* num2_;
	// has the second number been set?
	bool is2Set_;
	// the first number as a double - used instead of DigitChains when a full number is given
	double num2Double_;
	// if is1Chain_, num2_ is a valid number and num2Double_ is not to be used. else, vice versa.
	// INVARIANT: num2_ will be NULL unless this is true
	bool is2Chain_;
	// the stored memory number
	double memNum_;
};
#endif