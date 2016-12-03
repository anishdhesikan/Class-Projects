#include "FourFunCalc.h"
// long define here - a frequent conditional pattern
// performs the appropriate action depending on the current number
#define ON_CUR_NUM(num1_chain, num1_doub, num2_chain, num2_doub, num2_unset)	\
if (operation == Operation::UNSET) {								\
	if (is1Chain_) { num1_chain	}									\
	else { num1_doub }												\
} else {															\
	if (is2Set_) {													\
		if (is2Chain_) { num2_chain }									\
		else { num2_doub }												\
	}																\
	else { num2_unset }															\
}

FourFunCalc::FourFunCalc()
{
	num1_ = new DigitChain();
	num1Double_ = 0.0;
	is1Chain_ = true;
	operation = Operation::UNSET;
	num2_ = new DigitChain();
	is2Set_ = false;
	num2Double_ = 0.0;
	is2Chain_ = true;
	memNum_ = 0.0;
}

// adds a new digit to the right end of the current number
// if the current number is stored as a double, the calculator resets to putting the new digit in as the first number - previous first number, second number are lost
void FourFunCalc::addDigit(short s)
{
	ON_CUR_NUM( /*If num1 is current and a chain*/
		num1_->addDigit(s);,
		/*if num1 is current and not a chain*/
		is1Chain_ = true;
	num1_ = new DigitChain();
	num1_->addDigit(s);,
		/*if num2 is current and a chain*/
		num2_->addDigit(s);,
		/*if num2 is current and not a chain*/
		is1Chain_ = true;
	num1_ = new DigitChain();
	num1_->addDigit(s);
	operation = Operation::UNSET;,
		/*if num2 is current but not set*/
		is2Set_ = true;
	is2Chain_ = true;
	num2_ = new DigitChain();
	num2_->addDigit(s);
	)
}

void FourFunCalc::addDecimal()
{
	ON_CUR_NUM(num1_->setDecimal();,
		,
		num2_->setDecimal();,
		,
		is2Set_ = true; num2_->setDecimal(););
}

// negates the current number, returns true if it is now negative
// works whether it is currently stored as a DigitChain or as a double
bool FourFunCalc::negate()
{
	ON_CUR_NUM(return num1_->switchNegative();,
		return 0 > (num1Double_ = -num1Double_);,
		return num2_->switchNegative();,
		return 0 > (num2Double_ = -num2Double_);,
		is2Set_ = true; return num2_->switchNegative();)
}

// sets the operation
// if operation was set, first calls this->evaluate(), then sets the operation
void FourFunCalc::setOperation(Operation op)
{
	if (op != Operation::ADD && op != Operation::SUB && op != Operation::MUL && op != Operation::DIV && op != Operation::UNSET)
	{
		throw std::invalid_argument("Attempted to set operation to something other than ADD, SUB, MUL, DIV, or UNSET");
	}
	if (operation == Operation::UNSET) {
		operation = op;
	}
	else {
		evaluate();
		operation = op;
	}
}

// evaluates the current expression. If second number is not set, uses the first number as both terms.
// stores the result by calling setNum1Double, resets the rest of the state to initial values.
// if operation is unset, throws runtime_error
double FourFunCalc::evaluate() {
	// check that there is an operation
	if (operation == Operation::UNSET) {
		throw std::runtime_error("Attempted evaluation of unset operation");
	}

	// set up numbers to operate on
	if (is1Chain_) {
		num1Double_ = num1_->computeValue();
		is1Chain_ = false;
		delete num1_;
		num1_ = NULL;
	} // else it's already there

	if (is2Set_) { // work with either num2_ or num2Double_
		if (is2Chain_) {
			num2Double_ = num2_->computeValue();
			is2Chain_ = false;
			delete num2_;
			num2_ = NULL;
		} // else it's already there
	}
	else { // no num2, using first number as both args
		num2Double_ = num1Double_;
	}
	// now evaluate the expression num1Double <operator> num2Double
	switch (operation)
	{
	case Operation::ADD:
		setNum1Double(num1Double_ + num2Double_);
		break;
	case Operation::SUB:
		setNum1Double(num1Double_ - num2Double_);
		break;
	case Operation::MUL:
		setNum1Double(num1Double_ * num2Double_);
		break;
	case Operation::DIV:
		setNum1Double(num1Double_ / num2Double_);
		break;
	default:
		throw std::runtime_error("Invalid operation - not ADD, SUB, MUL, DIV"); // should be impossible to get here
		break;
	}
	// reset operator and second number to initial values
	operation = Operation::UNSET;
	is2Set_ = false;
	is2Chain_ = true;
	num2_ = new DigitChain();
	return num1Double_;
}

// calls the backspace() method of the current number
// does nothing if the current number is stored as a double
void FourFunCalc::backspace()
{
	ON_CUR_NUM(num1_->backspace();, ;, num2_->backspace();, ;, ;) // backspace if chain, else nothing
}

// sets the first number as a double, sets flags to indicate this, returns the double
double FourFunCalc::setNum1Double(double num)
{
	if (is1Chain_) {
		delete num1_;
		num1_ = NULL;
		is1Chain_ = false;
	}
	return num1Double_ = num;
}

// sets the second number as a double, sets flags to indicate this, returns the double
double FourFunCalc::setNum2Double(double num)
{
	if (is2Chain_) {
		delete num2_;
		num2_ = NULL;
		is2Chain_ = false;
	}
	return num2Double_ = num;
}

// returns the current number as a double
double FourFunCalc::returnCurrentNumber() const
{
	ON_CUR_NUM(return num1_->computeValue();,
		return num1Double_;,
		return num2_->computeValue();,
		return num2Double_;,
		return is1Chain_ ? num1_->computeValue() : num1Double_;)
}

// adds the current number to memory
void FourFunCalc::memPlus()
{
	memNum_ += returnCurrentNumber();
}

// subtracts the current number from memory
void FourFunCalc::memMinus()
{
	memNum_ -= returnCurrentNumber();
}

// sets the current number to the memory number (as a double), returns the double
double FourFunCalc::memRecall()
{
	ON_CUR_NUM(delete num1_; num1_ = NULL; is1Chain_ = false; num1Double_ = memNum_;,
		num1Double_ = memNum_;,
	delete num2_; num2_ = NULL; is2Chain_ = false; num2Double_ = memNum_;,
		num2Double_ = memNum_;,
		delete num2_; num2_ = NULL; is2Chain_ = false; num2Double_ = memNum_;)
	return returnCurrentNumber();
}

// clears the memory number
void FourFunCalc::memClear()
{
	memNum_ = 0.0;
}

std::string * FourFunCalc::toString() const
{
	auto remove_trail_zero =
		[](std::string *str) { return &(str->erase(str->find_last_not_of('0'), std::string::npos)); };

	ON_CUR_NUM(return num1_->toString();,
		return remove_trail_zero(new std::string(std::to_string(num1Double_)));,
		return num2_->toString();,
		return remove_trail_zero(new std::string(std::to_string(num2Double_)));,
		return is1Chain_ ? num1_->toString() : new std::string(std::to_string(num1Double_));)
}



#undef ON_CUR_NUM