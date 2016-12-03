#ifndef CALC_CONTROLLER_CPP
#define CALC_CONTROLLER_CPP

#include "Constants.h"
#include "ExampleRunnable.h"
#include "CalculatorController.h"


void CalculatorController::setDisplay(std::string text) {
    displayText = text;
}

bool CalculatorController::init()
{
    //Initialization flag
    bool success = true;
    
    displayText = " ";
    
    //Initialize SDL
    if( SDL_Init( SDL_INIT_VIDEO ) < 0 )
    {
        printf( "SDL could not initialize! SDL Error: %s\n", SDL_GetError() );
        success = false;
    }
    else
    {
        //Set texture filtering to linear
        if( !SDL_SetHint( SDL_HINT_RENDER_SCALE_QUALITY, "1" ) )
        {
            printf( "Warning: Linear texture filtering not enabled!" );
        }
        
        //Create window
        gWindow = SDL_CreateWindow( "My Calculator", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN );
        if( gWindow == NULL )
        {
            printf( "Window could not be created! SDL Error: %s\n", SDL_GetError() );
            success = false;
        }
        else
        {
            //Create vsynced renderer for window
            gRenderer = SDL_CreateRenderer( gWindow, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC );
            if( gRenderer == NULL )
            {
                printf( "Renderer could not be created! SDL Error: %s\n", SDL_GetError() );
                success = false;
            }
            else
            {
                //Initialize renderer color
                SDL_SetRenderDrawColor( gRenderer, 0xFF, 0xFF, 0xFF, 0xFF );
                
                //Initialize PNG loading
                int imgFlags = IMG_INIT_PNG;
                if( !( IMG_Init( imgFlags ) & imgFlags ) )
                {
                    printf( "SDL_image could not initialize! SDL_image Error: %s\n", IMG_GetError() );
                    success = false;
                }
                
                //Initialize SDL_ttf
                if( TTF_Init() == -1 )
                {
                    printf( "SDL_ttf could not initialize! SDL_ttf Error: %s\n", TTF_GetError() );
                    success = false;
                } else {
                    gFont = TTF_OpenFont( "EncodeSans.ttf", 28 );
                    dFont = TTF_OpenFont( "EncodeSans.ttf", 50 );
                }
            }
        }
    }
    
    return success;
}

bool CalculatorController::loadMedia()
{
    //Loading success flag
    bool success = true;

    //Load sprites
    if( !gButtonSpriteSheetTexture.loadFromFile( gRenderer, "button.png" ) )
    {
        printf( "Failed to load button sprite texture!\n" );
        success = false;
    }
    else
    {
        renderButtons();
    }
    
    if ( !gBackgroundImage.loadFromFile( gRenderer, "../Images/Calculator.png") ) {
        printf( "Failed to load background image \n" );
        success = false;
    }
    
        return success;
    
}

void CalculatorController::renderButtons()
{
    //Set all buttons
    for (int i = 0; i < NUM_ROWS; i++) {
        for (int j = 0; j < NUM_COLUMNS; j++) {
            int buttonIndex = 5 * i + j;
            gButtons[ buttonIndex ].setPosition( INIT_X + BUTTON_WIDTH * j + HORIZ_PADDING * j,
                                                INIT_Y + BUTTON_HEIGHT * i + VERT_PADDING * i);
            gButtons[ buttonIndex ].setText(std::to_string(buttonIndex));
            gButtons[ buttonIndex ].installRunnable(new ExampleRunnable(buttonIndex));
        }
    }
}

void CalculatorController::close()
{
    //Free loaded images
    gButtonSpriteSheetTexture.free();
    gBackgroundImage.free();
    
    //Destroy window	
    SDL_DestroyRenderer( gRenderer );
    SDL_DestroyWindow( gWindow );
    gWindow = NULL;
    gRenderer = NULL;
    
    //Quit SDL subsystems
    TTF_Quit();
    IMG_Quit();
    SDL_Quit();
}
    
    
int CalculatorController::main( int argc, char* args[] )
{
    //Start up SDL and create window
    if( !init() )
    {
        printf( "Failed to initialize!\n" );
    }
    else
    {
        //Load media
        if( !loadMedia() )
        {
            printf( "Failed to load media!\n" );
        }
        else
        {
            //Main loop flag
            bool quit = false;
            
            //Event handler
            SDL_Event e;
            
            //While application is running
            while( !quit )
            {
                //Handle events on queue
                while( SDL_PollEvent( &e ) != 0 )
                {
                    //User requests quit
                    if( e.type == SDL_QUIT )
                    {
                        quit = true;
                    }
                    
                    //Handle button events
                    for( int i = 0; i < TOTAL_BUTTONS; ++i )
                    {
                        gButtons[ i ].handleEvent( &e );
                    }
                }
                
                //Clear screen
                SDL_SetRenderDrawColor( gRenderer, 0xFF, 0xFF, 0xFF, 0xFF );
                SDL_RenderClear( gRenderer );
                
                
                //Render Background
                gBackgroundImage.render(gRenderer, 0, 0);
                
                //Render buttons
                for( int i = 0; i < TOTAL_BUTTONS; ++i )
                {
                    gButtons[ i ].render(&gButtonSpriteSheetTexture, gRenderer, gFont);
                }
                
                //Render Display
                SDL_Color textColor = { 0, 0, 0 };
                if( !gTextTexture.loadFromRenderedText( gRenderer, displayText, textColor, dFont ) )
                {
                    printf( "Failed to render text texture!\n" );
                } else {
                    gTextTexture.render(gRenderer, SCREEN_WIDTH - 50 - gTextTexture.getWidth(), 50);
                }
                
                
                //Update screen
                SDL_RenderPresent( gRenderer );
            }
        }
    }
    
    //Free resources and close SDL
    close();
    
    return 0;
}
#endif
