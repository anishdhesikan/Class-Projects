#ifndef CHAIN_INCLUDED_H
#define CHAIN_INCLUDED_H
#include <list>
#include <string>

// Handles numbers as a list of digits, possibly including a decimal point
class DigitChain
{
public:
	DigitChain();
	// add a digit to the number. Throws invalid_argument exception if not from 0 to 9. Does not store leading zeroes before the decimal.
	void addDigit(short);
	// set the decimal place at the current end of the number. Does nothing if decimal point is already set.
	void setDecimal();
	// switch this from negative to positive and vice versa. Returns post-switch state of isNegative_
	bool switchNegative();
	// remove the last digit from the number (backspace). Removes decimal point if that's the last. Does nothing if there's nothing set.
	void backspace();
	// determine and return the number represented by this digit chain
	double computeValue() const;
	// return pointer to this DigitChain as a string (string is dynamically allocated)
	std::string * toString() const;
private:
	// the digits in the number
	std::list<short> digits_;
	// the amount of digits added since the decimal point was placed. -1 for not yet placed.
	int decLoc_;
	// is this negative?
	bool isNegative_;
};
#endif