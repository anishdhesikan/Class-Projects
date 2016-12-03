#include "SciParseRunnable.h"

SciParseRunnable::SciParseRunnable(bool assign, SciController * con)
: assign_(assign)
, con_(con)
{ }

void SciParseRunnable::run()
{
	con_->calc_model.parse()
  con_->assign = assign_;
	std::string * disp = con_->calc_model.getString();
	con_->setDisplay(*disp);
	delete disp;
}

void SciParseRunnable::setController(SciController * con)
{
	con_ = con;
}