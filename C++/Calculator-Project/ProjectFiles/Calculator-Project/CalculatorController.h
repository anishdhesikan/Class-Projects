#ifndef MAIN_CONTROLLER
#define MAIN_CONTROLLER

#include <SDL2/SDL.h>
#include <SDl2/SDL_image.h>
#include <SDl2/SDL_ttf.h>
#include "LTexture.h"
#include "LButton.h"
//#include "ExampleRunnable.h"
class ExampleRunnable;
//#include "CalculatorController.h"
#include <stdio.h>
#include <string>
#include <cmath>

// global constants:
class CalculatorController
{
public:
    // Set the display of the calculator to the given string
    void setDisplay(std::string text);
    
    // Initialization function for SDL
    bool init();
    
    // Load SDL requirements
    bool loadMedia();
    
    // Render the buttons on the calculator
    void renderButtons();
    
    // Memory management
    void close();
    
    // Main
    int main( int argc, char* args[] );
    
private:
};

#endif
