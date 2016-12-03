#ifndef SCI_CONTROLLER_CPP
#define SCI_CONTROLLER_CPP

#include "Constants.h"
#include "ExampleRunnable.h"
#include "SciController.h"


void SciController::setDisplay(std::string text) {
  displayText = text;
}

bool SciController::init()
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
    gWindow = SDL_CreateWindow( "SDL Tutorial", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN );
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

bool SciController::loadMedia()
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
    // Set          (3)
    gButtons[3].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 3, INIT_Y);
    gButtons[3].setText('->');
    gButtons[3].installRunnable(new SciParseRunnable(true, this));
    
    // Equals       (24)
    gButtons[24].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 4);
    gButtons[24].setText('=');
    gButtons[24].installRunnable(new SciParseRunnable(false, this));
    
    // Backspace    (4)
    gButtons[4].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4, INIT_Y);
    gButtons[4].setText('´');
    gButtons[4].installRunnable(new SciParseRunnable('´', this));
    
    // Variables    (0, 1, 2)
    gButtons[0].setPosition(INIT_X, INIT_Y);
    gButtons[0].setText('A');
    gButtons[0].installRunnable(new SciConcatRunnable('a', this));
    
    gButtons[1].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING), INIT_Y);
    gButtons[1].setText('B');
    gButtons[1].installRunnable(new SciConcatRunnable('b', this));
    
    gButtons[4].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 2, INIT_Y);
    gButtons[4].setText('C');
    gButtons[4].installRunnable(new SciConcatRunnable('c', this));
  
    // 0 button     (22)
    gButtons[22].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
                            INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 2);
    gButtons[22].setText('0');
    gButtons[22].installRunnable(new SciConcatRunnable('0', this));
    
    // 1-9 buttons  (7, 8, 9, 12, 13, 14, 17, 18, 19)
    gButtons[7].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 2,
                            INIT_Y + (BUTTON_HEIGHT + VERT_PADDING));
    gButtons[7].setText('7');
    gButtons[7].installRunnable(new SciConcatRunnable('7', this));
    
    gButtons[8].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 3,
                            INIT_Y + (BUTTON_HEIGHT + VERT_PADDING));
    gButtons[8].setText('8');
    gButtons[8].installRunnable(new SciConcatRunnable('8', this));
    
    gButtons[9].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
                            INIT_Y + (BUTTON_HEIGHT + VERT_PADDING));
    gButtons[9].setText('9');
    gButtons[9].installRunnable(new SciConcatRunnable('9', this));
    
    gButtons[12].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 2,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 2);
    gButtons[12].setText('4');
    gButtons[12].installRunnable(new SciConcatRunnable('4', this));
    
    gButtons[13].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 3,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 2);
    gButtons[13].setText('5');
    gButtons[13].installRunnable(new SciConcatRunnable('5', this));
    
    gButtons[14].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 2);
    gButtons[14].setText('6');
    gButtons[14].installRunnable(new SciConcatRunnable('6', this));
    
    gButtons[17].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 2,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 3);
    gButtons[17].setText('1');
    gButtons[17].installRunnable(new SciConcatRunnable('1', this));
    
    gButtons[18].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 3,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 3);
    gButtons[18].setText('2');
    gButtons[18].installRunnable(new SciConcatRunnable('2', this));
  
    gButtons[19].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 3);
    gButtons[19].setText('3');
    gButtons[19].installRunnable(new SciConcatRunnable('3', this));
 
    
    // Decimal      (23)
    gButtons[23].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 3);
    gButtons[23].setText('.');
    gButtons[23].installRunnable(new SciConcatRunnable('.', this));
    
    // LParen       (5)
    gButtons[5].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 1,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING));
    gButtons[5].setText('(');
    gButtons[5].installRunnable(new SciConcatRunnable('(', this));
    
    // RParen       (6)
    gButtons[6].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 1,
                             INIT_Y);
    gButtons[6].setText(')');
    gButtons[6].installRunnable(new SciConcatRunnable(')', this));
    
    // Exponent     (10)
    gButtons[10].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 2,
                             INIT_Y);
    gButtons[10].setText('^');
    gButtons[10].installRunnable(new SciConcatRunnable('^', this));
    
    // Mod          (11)
    gButtons[11].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 2,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING));
    gButtons[11].setText('%');
    gButtons[11].installRunnable(new SciConcatRunnable('%', this));
    
    // Multiply      (15)
    gButtons[15].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 3,
                             INIT_Y);
    gButtons[15].setText('*');
    gButtons[15].installRunnable(new SciConcatRunnable('*', this));
    
    // Divide       (16)
    gButtons[16].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 3,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING));
    gButtons[16].setText('/');
    gButtons[16].installRunnable(new SciConcatRunnable('/', this));
    
    // Plus         (20)
    gButtons[20].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
                             INIT_Y);
    gButtons[20].setText('+');
    gButtons[20].installRunnable(new SciConcatRunnable('+', this));
    
    // Minus        (21)
    gButtons[21].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
                             INIT_Y + (BUTTON_HEIGHT + VERT_PADDING));
    gButtons[21].setText('-');
    gButtons[21].installRunnable(new SciConcatRunnable('-', this));
  }
  
  if ( !gBackgroundImage.loadFromFile( gRenderer, "../Images/Calculator.png") ) {
    printf( "Failed to load background image \n" );
    success = false;
  }
  
  return success;
  
}

void SciController::close()
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


int SciController::main( int argc, char* args[] )
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
