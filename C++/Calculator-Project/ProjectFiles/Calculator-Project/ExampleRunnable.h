#ifndef EXRUNNABLE_INCLUDED_H
#define EXRUNNABLE_INCLUDED_H
#include "Runnable.h"

class ExampleRunnable: public Runnable
{
public:
    ExampleRunnable(int toAdd);
    
    void run ();
    
private:
    int numToAdd;
};
#endif