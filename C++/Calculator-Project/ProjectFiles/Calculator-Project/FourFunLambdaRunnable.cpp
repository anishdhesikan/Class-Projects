#include "FourFunLambdaRunnable.h"

FourFunLambdaRunnable::FourFunLambdaRunnable(FourFunController * con, std::function<void(FourFunController*)> fn)
	: con_(con)
	, fn_(fn)
{}

void FourFunLambdaRunnable::run()
{
	fn_(con_);
	std::string * disp = con_->calc_model.toString();
	con_->setDisplay(*disp);
	delete disp;
}

void FourFunLambdaRunnable::setController(FourFunController * con)
{
	con_ = con;
}