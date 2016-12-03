#ifndef SCI_CONCAT_RUNNABLE_H
#define SCI_CONCAT_RUNNABLE_H

#include "Runnable.h"
#include "SciController.h"

class SciConcatRunnable : public Runnable
{
public:

	SciConcatRunnable(char c, SciController * con);

	void run();
	
	void setController(SciController * con);

private:
	char c_;
	SciController * con_;
};
#endif