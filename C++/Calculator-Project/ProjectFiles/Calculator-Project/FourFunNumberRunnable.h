#ifndef FOURFUN_NUM_RUNNABLE_H
#define FOURFUN_NUM_RUNNABLE_H

#include "Runnable.h"
#include "FourFunController.h"

class FourFunNumberRunnable : public Runnable
{
public:

	FourFunNumberRunnable(short num, FourFunController * con);

	void run();
	
	void setController(FourFunController * con);

private:
	short number_;
	FourFunController * con_;
};
#endif