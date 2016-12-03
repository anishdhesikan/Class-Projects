#ifndef RUNNABLE_INCLUDED_H
#define RUNNABLE_INCLUDED_H
//#include "CalculatorController.h"
class CalculatorController;

//An interface for objects that can run()
class Runnable
{
public:
    virtual void run () {}
    void setController (CalculatorController *control);
    
protected:
    CalculatorController *controller;
};
#endif