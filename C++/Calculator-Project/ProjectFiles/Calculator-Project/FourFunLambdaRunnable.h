#ifndef FOUR_FUN_LM_RUNNABLE_H
#define FOUR_FUN_LM_RUNNABLE_H

#include "Runnable.h"
#include "FourFunController.h"
#include <functional>

class FourFunLambdaRunnable : public Runnable
{
public:

	FourFunLambdaRunnable(FourFunController * con, std::function<void(FourFunController*)> fn);

	void run();

	void setController(FourFunController * con);

private:
	FourFunController * con_;
	std::function<void(FourFunController*)> fn_;
};
#endif