CPPS := LTexture.cpp LButton.cpp CalculatorController.cpp ExampleRunnable.cpp FourFunCalc.cpp FourFunController.cpp DigitChain.cpp FourFunLambdaRunnable.cpp FourFunNumberRunnable.cpp

HEADERS = LTexture.h LButton.h CalculatorController.h ExampleRunnable.h FourFunCalc.h FourFunController.h DigitChain.h Operation.h
OBJECTS = LTexture.o LButton.o CalculatorController.o ExampleRunnable.o FourFunCalc.o FourFunController.o DigitChain.o

CXX = g++
# Update these paths to match your installation
# You may also need to update the linker option rpath, which sets where to look for
# the SDL2 libraries at runtime to match your install
SDL_LIB = -L/usr/local/lib -lSDL2 -lSDL2_Image -lSDL2_ttf -Wl,-rpath,/usr/local/lib
SDL_INCLUDE = -I/usr/local/include
# You may need to change -std=c++11 to -std=c++0x if your compiler is a bit older
CXXFLAGS = -Wall -c -std=c++11 $(SDL_INCLUDE)
LDFLAGS = $(SDL_LIB)
EXE = main

all: $(EXE)

$(EXE): main.o $(CPPS)
	$(CXX) -std=c++11 $^ $(LDFLAGS) -o $@

%.o: %.cpp $(HEADERS)
	$(CXX) $(CXXFLAGS) -c $, -o $@

main.o: main.cpp
	$(CXX) $(CXXFLAGS) $< -o $@

clean:
	rm *.o && rm $(EXE)