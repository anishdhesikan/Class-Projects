#ifndef CONSTANTS
#define CONSTANTS

#include <SDL2/SDL.h>
#include <SDl2/SDL_image.h>
#include <SDl2/SDL_ttf.h>
#include "LTexture.h"
#include "LButton.h"
#include <stdio.h>
#include <string>
#include <cmath>

// global constants:

//Screen dimension constants
static const int SCREEN_WIDTH = 432;
static const int SCREEN_HEIGHT = 576;

//Button constants
static const int BUTTON_WIDTH = 62;
static const int BUTTON_HEIGHT = 50;
static const int HORIZ_PADDING = 9;
static const int VERT_PADDING = 25;
static const int INIT_X = 42;
static const int INIT_Y = 180;

static const int NUM_ROWS = 5;
static const int NUM_COLUMNS = 5;
static const int TOTAL_BUTTONS = NUM_ROWS * NUM_COLUMNS;

//Display text texture
static LTexture gTextTexture;

//The window to render to
static SDL_Window* gWindow = NULL;

//The window renderer
static SDL_Renderer* gRenderer = NULL;

//Mouse button sprites
static LTexture gButtonSpriteSheetTexture;

//Background Image
static LTexture gBackgroundImage;

//String to display as resulting number
static std::string displayText;

//Globally used font for buttons
static TTF_Font *gFont = NULL;

//Font for Display
static TTF_Font *dFont = NULL;

//Buttons objects
static LButton gButtons[TOTAL_BUTTONS];

#endif
