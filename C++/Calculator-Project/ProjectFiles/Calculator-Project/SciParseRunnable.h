#ifndef SCI_PARSE_RUNNABLE_H
#define SCI_PARSE_RUNNABLE_H

#include "Runnable.h"
#include "SciController.h"

class SciParseRunnable : public Runnable
{
public:

	SciParseRunnable(bool assign, SciController * con);

	void run();
	
	void setController(SciController * con);

private:
	bool assign;
	FourFunController * con_;
};
#endif