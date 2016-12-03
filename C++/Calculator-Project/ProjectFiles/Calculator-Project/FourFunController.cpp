#include "FourFunController.h"
#include "FourFunLambdaRunnable.h"

void FourFunController::setDisplay(std::string text) {
    displayText = text;
}

bool FourFunController::init()
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
        gWindow = SDL_CreateWindow( "Four-Function Calculator", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN );
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

void FourFunController::close()
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


int FourFunController::main( int argc, char* args[] )
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

bool FourFunController::loadMedia()
{
	//Loading success flag
	bool success = true;

	//Load sprites
	if (!gButtonSpriteSheetTexture.loadFromFile(gRenderer, "button.png"))
	{
		printf("Failed to load button sprite texture!\n");
		success = false;
	}
	else
	{
		// mem plus
		gButtons[0].setPosition(INIT_X, INIT_Y);
		gButtons[0].setText("M+");
		gButtons[0].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController * con) {con->calc_model.memPlus();}));

		// mem minus
		gButtons[1].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING), INIT_Y);
		gButtons[1].setText("M-");
		gButtons[1].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController * con) {con->calc_model.memMinus();}));

		// mem recall
		gButtons[2].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 2, INIT_Y);
		gButtons[2].setText("MR");
		gButtons[2].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController * con) {con->calc_model.memRecall();}));

		// mem clear
		gButtons[3].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 3, INIT_Y);
		gButtons[3].setText("MC");
		gButtons[3].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController * con) {con->calc_model.memClear();}));

		// backspace
		gButtons[4].setPosition(INIT_X + (BUTTON_WIDTH * HORIZ_PADDING) * 4,
			INIT_Y + (BUTTON_HEIGHT + VERT_PADDING));
		gButtons[4].setText("«");
		gButtons[4].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController * con) {con->calc_model.backspace();}));

		// clear equation
		gButtons[5].setPosition(INIT_X, INIT_Y + (BUTTON_HEIGHT + VERT_PADDING));
		gButtons[5].setText("CE");
		gButtons[5].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController * con) {con->calc_model.evaluate(); con->calc_model.addDigit((short)0);}));

		// Set number buttons
		// 1-9 (gButtons indexes 6, 7, 8, 11, 12, 13, 16, 17, 18)
		for (int i = 15; i > 0; i -= 5) // rows as indexed in gButtons
		{
			for (int j = 1; j < 4; ++j) // columns
			{
				short num_on_button = j + (3 - (i / 5)) * 3; // so it ends up counting 1 through 9
				gButtons[i + j].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * j,
					INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * i/5);
				gButtons[i + j].setText(std::to_string(num_on_button));
				gButtons[i + j].installRunnable(new FourFunLambdaRunnable(this,
					[num_on_button](FourFunController* con) {con->calc_model.addDigit(num_on_button);}));
			}
		}

		// plus
		gButtons[9].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
			INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 0);
		gButtons[9].setText("+");
		gButtons[9].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController* con) {con->calc_model.setOperation(Operation::ADD);}));

		// clear all
		gButtons[10].setPosition(INIT_X, INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 2);
		gButtons[10].setText("C");
		gButtons[10].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController * con) {
			con->calc_model.evaluate();
			con->calc_model.addDigit((short)0);
			con->calc_model.memClear();
			con->calc_model.setOperation(Operation::UNSET);
		}));

		// minus
		gButtons[14].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
			INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 1);
		gButtons[14].setText("-");
		gButtons[14].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController* con) {con->calc_model.setOperation(Operation::SUB);}));

		// button number 15 does nothing at the moment
		gButtons[15].setPosition(INIT_X, INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 3);
		gButtons[15].setText(" ");
		gButtons[15].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController* con) { }));

		// multiply
		gButtons[19].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
			INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 2);
		gButtons[19].setText("*");
		gButtons[19].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController* con) {con->calc_model.setOperation(Operation::MUL);}));

		// equals
		gButtons[20].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
                                 INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 4);
		gButtons[20].setText("=");
		gButtons[20].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController* con) {con->calc_model.evaluate();}));

		// zero
		gButtons[21].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING),
			INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 4);
		gButtons[21].setText("0");
		gButtons[21].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController* con) {con->calc_model.addDigit((short)0);}));

		// Set decimal point and negate
		// decimal
		gButtons[22].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 2,
			INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 4);
		gButtons[22].setText(".");
		gButtons[22].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController * con) {con->calc_model.negate();}));

		// negate
		gButtons[23].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 3,
			INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 4);
		gButtons[23].setText("±");
		gButtons[23].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController * con) {con->calc_model.negate();}));

		// divide
		gButtons[24].setPosition(INIT_X + (BUTTON_WIDTH + HORIZ_PADDING) * 4,
			INIT_Y + (BUTTON_HEIGHT + VERT_PADDING) * 3);
		gButtons[24].setText("÷");
		gButtons[24].installRunnable(new FourFunLambdaRunnable(this,
			[](FourFunController* con) {con->calc_model.setOperation(Operation::DIV);}));
	}

	if (!gBackgroundImage.loadFromFile(gRenderer, "../Images/Calculator.png")) {
		printf("Failed to load background image \n");
		success = false;
	}

	return success;

}
