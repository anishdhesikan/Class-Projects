#ifndef LBUTTON_INCLUDED_H
#define LBUTTON_INCLUDED_H
#include <SDL2/SDL.h>
#include <SDl2/SDL_image.h>
#include <string>
#include "LTexture.h"
//#include "CalculatorController.h"
//#include "Runnable.h"
class Runnable;

enum LButtonSprite
{
    BUTTON_SPRITE_MOUSE_OUT = 0,
    BUTTON_SPRITE_MOUSE_OVER_MOTION = 1,
    BUTTON_SPRITE_MOUSE_DOWN = 2,
    BUTTON_SPRITE_MOUSE_UP = 3,
    BUTTON_SPRITE_TOTAL = 4
};

//The mouse button
class LButton
{
public:
    //Initializes internal variables
    LButton();
    
    //Installs a runnable to run when button is clicked
    void installRunnable(Runnable *toRun);
    
    //Sets top left position
    void setPosition( int x, int y );
    
    //Sets text to be written in this button
    void setText(std::string textToSet);
    
    //Handles mouse event
    void handleEvent( SDL_Event* e );
    
    void loadMedia();
    
    //Shows button sprite
    void render(LTexture* gButtonSpriteSheetTexture, SDL_Renderer* gRenderer, TTF_Font *gFont);
    
private:
    //To run when clicked
    Runnable *runnable;
    
    //Text on this button
    std::string buttonText;
    
    //Top left position
    SDL_Point mPosition;
    
    //Currently used global sprite
    LButtonSprite mCurrentSprite;
};
#endif