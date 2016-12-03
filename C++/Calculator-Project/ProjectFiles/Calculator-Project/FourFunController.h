#ifndef FOURFUN_CONT_H
#define FOURFUN_CONT_H

#include "CalculatorController.h"
#include "Constants.h"
#include "FourFunCalc.h"

class FourFunController : CalculatorController
{
public:
    // Set the display of the calculator to the given string
    void setDisplay(std::string text);
    
    // Initialization function for SDL
    bool init();
    
    // Load SDL requirements
    bool loadMedia();
    
    // Memory management
    void close();
    
    // Main
    int main( int argc, char* args[] );
    
    FourFunCalc calc_model = FourFunCalc();
    
};

#endif