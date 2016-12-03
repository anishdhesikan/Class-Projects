#include "DigitChain.h"
#include <cmath>

DigitChain::DigitChain()
{
	digits_ = std::list<short>();
	decLoc_ = -1;
	isNegative_ = false;
}

// add a digit to the number. Throws invalid_argument exception if not from 0 to 9. Does not store leading zeroes before the decimal.
void DigitChain::addDigit(short s)
{
	if ((s >= 0) && (s <= 9)) // all valid digits are between 0 and 9
	{
		if ((s > 0) || decLoc_ != -1 || digits_.size() > 0) // 0 is a special case - invalid if there's no other numbers and no decimal
		{													// to avoid inserting leading zeros
			digits_.push_back(s);
			if (decLoc_ != -1) // if the decimal point was set, increment its location
			{
				decLoc_ += 1;
			}
		}
	}
	else // other things are invalid
	{
		throw std::invalid_argument(s + " is not a digit.");
	}
}

// set the decimal place at the current end of the number. Does nothing if decimal point is already set.
void DigitChain::setDecimal()
{
	if (decLoc_ == -1)
	{
		decLoc_ = 0;
	}
}

// switch this from negative to positive and vice versa. Returns post-switch state of isNegative_
bool DigitChain::switchNegative()
{
	return (isNegative_ = !isNegative_);
}

// remove the last digit from the number (backspace). Removes decimal point if that's the last. Does nothing if there's nothing set.
void DigitChain::backspace()
{
	if (decLoc_ == 0) // the decimal place was the last input
	{
		decLoc_ = -1;
	}
	else if (digits_.size() != 0) // there is a digit present to backspace
	{
		digits_.pop_back();
		if (decLoc_ != -1) // if the decimal point was placed, decrement it
		{
			decLoc_ -= 1;
		}
	}
	// else there's nothing to be done
}

// determine and return the number represented by this digit chain
double DigitChain::computeValue() const
{
	std::list<short>::const_iterator iter = digits_.cbegin(); // iterator over digits, from the first
	int size = digits_.size(); // unsure if this helps - makes it accessible without having to dereference digits
	double value = 0.0; // accumulates value
	// initialized current place at the power-of-ten of the first digit, will be reduced each loop
	// decLoc_ is -1 when not placed, so check that.
	double curPlace = (decLoc_ > 0) ? pow(10, size - 1 - decLoc_) : pow(10, size - 1);

	for (int i = 0; i < size; i++, curPlace /= 10, iter++)
	{
		value += (*iter * curPlace); // add in the value of the current digit
	}

	return isNegative_ ? -value : value;
}

std::string * DigitChain::toString() const
{
	std::string * str = new std::string();

	for (std::list<short>::const_iterator iter = digits_.cbegin(); iter != digits_.cend(); ++iter) // for each digit
	{
		str->append(std::to_string(*iter)); // put that digit in the string
	}

	// if there is a decimal point put it in
	if (decLoc_ != -1)
	{
		str->insert(str->size() - decLoc_, 1, '.');
	}

	// if there is no number, or if the string starts with the decimal point, start it with a 0
	if (digits_.size() == 0 || decLoc_ == digits_.size())
	{
		str->insert(0, 1, '0');
	}

	// if it's negative insert a -
	if (isNegative_)
	{
		str->insert(0, "-");
	}

	return str;
}
