#include <SDL2/SDL.h>
#include <SDl2/SDL_image.h>
#include <SDl2/SDL_ttf.h>
#include <string>
#include "LButton.h"
#include "LTexture.h"
#include "Runnable.h"

const int BUTTON_WIDTH = 62;
const int BUTTON_HEIGHT = 50;

SDL_Rect gSpriteClips[ BUTTON_SPRITE_TOTAL ];

LButton::LButton()
{
    mPosition.x = 0;
    mPosition.y = 0;
    
    mCurrentSprite = BUTTON_SPRITE_MOUSE_OUT;
    
    //Set sprites
    for( int i = 0; i < BUTTON_SPRITE_TOTAL; ++i )
    {
        gSpriteClips[ i ].x = 0;
        gSpriteClips[ i ].y = i * BUTTON_HEIGHT;
        gSpriteClips[ i ].w = BUTTON_WIDTH;
        gSpriteClips[ i ].h = BUTTON_HEIGHT;
    }
}

void LButton::installRunnable(Runnable *toRun) {
    runnable = toRun;
}


void LButton::setPosition( int x, int y )
{
    mPosition.x = x;
    mPosition.y = y;
}

void LButton::setText(std::string textToSet)
{
    buttonText = textToSet;
}

void LButton::handleEvent( SDL_Event* e )
{
    //If mouse event happened
    if( e->type == SDL_MOUSEMOTION || e->type == SDL_MOUSEBUTTONDOWN || e->type == SDL_MOUSEBUTTONUP )
    {
        //Get mouse position
        int x, y;
        SDL_GetMouseState( &x, &y );
        
        //Check if mouse is in button
        bool inside = true;
        
        //Mouse is left of the button
        if( x < mPosition.x )
        {
            inside = false;
        }
        //Mouse is right of the button
        else if( x > mPosition.x + BUTTON_WIDTH )
        {
            inside = false;
        }
        //Mouse above the button
        else if( y < mPosition.y )
        {
            inside = false;
        }
        //Mouse below the button
        else if( y > mPosition.y + BUTTON_HEIGHT )
        {
            inside = false;
        }
        
        //Mouse is outside button
        if( !inside )
        {
            mCurrentSprite = BUTTON_SPRITE_MOUSE_OUT;
        }
        //Mouse is inside button
        else
        {
            //Set mouse over sprite
            switch( e->type )
            {
                case SDL_MOUSEMOTION:
                    mCurrentSprite = BUTTON_SPRITE_MOUSE_OVER_MOTION;
                    break;
                    
                case SDL_MOUSEBUTTONDOWN:
                    mCurrentSprite = BUTTON_SPRITE_MOUSE_DOWN;
                    runnable->run();
                    break;
                    
                case SDL_MOUSEBUTTONUP:
                    mCurrentSprite = BUTTON_SPRITE_MOUSE_UP;
                    break;
            }
        }
    }
}

LTexture gTextTexture2;

void LButton::render(LTexture *gButtonSpriteSheetTexture, SDL_Renderer* gRenderer, TTF_Font *gFont)
{
    //Show current button sprite
    gButtonSpriteSheetTexture->render( gRenderer, mPosition.x, mPosition.y, &gSpriteClips[ mCurrentSprite ] );
    
    SDL_Color textColor = { 255, 255, 255 };
    if( !gTextTexture2.loadFromRenderedText( gRenderer, buttonText, textColor, gFont ) )
    {
        printf( "Failed to render text texture!\n" );
    } else {
        gTextTexture2.render(gRenderer,
                            BUTTON_WIDTH / 2 + mPosition.x - gTextTexture2.getWidth() / 2,
                            BUTTON_HEIGHT / 2 + mPosition.y - gTextTexture2.getHeight() / 2);
    }
}






