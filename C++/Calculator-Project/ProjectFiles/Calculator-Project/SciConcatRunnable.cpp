#include "SciConcatRunnable.h"

SciConcatRunnable::SciConcatRunnable(char c, SciController * con)
	: c_(c)
	, con_(con)
{ }

void SciConcatRunnable::run()
{
	con_->calc_model.addChar(c_);
	std::string * disp = con_->calc_model.getString();
	con_->setDisplay(*disp);
	delete disp;
}

void SciConcatRunnable::setController(SciController * con)
{
	con_ = con;
}