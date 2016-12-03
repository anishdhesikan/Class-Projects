#ifndef EXAMPLE_RUNNABLE_CPP
#define EXAMPLE_RUNNABLE_CPP

#include "CalculatorController.h"
#include "ExampleRunnable.h"
#include <string>

ExampleRunnable::ExampleRunnable(int toAdd) {
    numToAdd = toAdd;
}
    
void ExampleRunnable::run () {
    controller->setDisplay(std::to_string(numToAdd));
}
#endif