#ifndef LTEXTURE_INCLUDED_H
#define LTEXTURE_INCLUDED_H
#include <SDL2/SDL.h>
#include <SDl2/SDL_image.h>
#include <SDL2/SDL_ttf.h>
#include <string>

//Texture wrapper class
class LTexture
{
public:
    //Initializes variables
    LTexture();
    
    //Deallocates memory
    ~LTexture();
    
    //Loads image at specified path
    bool loadFromFile( SDL_Renderer * gRenderer, std::string path );
    
#ifdef _SDL_TTF_H
    //Creates image from font string
    bool loadFromRenderedText(SDL_Renderer *gRenderer, std::string textureText, SDL_Color textColor, TTF_Font *gFont );
#endif
    
    //Deallocates texture
    void free();
    
    //Set color modulation
    void setColor( Uint8 red, Uint8 green, Uint8 blue );
    
    //Set blending
    void setBlendMode( SDL_BlendMode blending );
    
    //Set alpha modulation
    void setAlpha( Uint8 alpha );
    
    //Renders texture at given point
    void render( SDL_Renderer * gRenderer, int x, int y, SDL_Rect* clip = NULL, double angle = 0.0, SDL_Point* center = NULL, SDL_RendererFlip flip = SDL_FLIP_NONE );
    
    //Gets image dimensions
    int getWidth();
    int getHeight();
    
private:
    //The actual hardware texture
    SDL_Texture* mTexture;
    
    //Image dimensions
    int mWidth;
    int mHeight;
};
#endif