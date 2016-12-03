#ifndef SCI_CONT_H
#define SCI_CONT_H

#include "CalculatorController.h"
#include "Constants.h"
#include "SciCalc.h"

class SciController : CalculatorController
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
  
  SciCalc calc_model = SciCalc();
  
};

#endif