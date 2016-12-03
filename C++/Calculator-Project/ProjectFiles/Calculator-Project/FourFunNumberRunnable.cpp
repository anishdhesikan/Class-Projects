#include "FourFunNumberRunnable.h"

FourFunNumberRunnable::FourFunNumberRunnable(short num, FourFunController * con)
	: number_(num)
	, con_(con)
{ }

void FourFunNumberRunnable::run()
{
	con_->calc_model.addDigit(number_);
	std::string * disp = con_->calc_model.toString();
	con_->setDisplay(*disp);
	delete disp;
}

void FourFunNumberRunnable::setController(FourFunController * con)
{
	con_ = con;
}