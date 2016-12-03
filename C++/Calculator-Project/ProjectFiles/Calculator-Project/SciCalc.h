#ifndef SCI_CALC_INCLUDED_H
#define SCI_CALC_INCLUDED_H
#include <stdexcept>
#include <string.h>
class SciCalc
{
public:
  //Constructor
	SciCalc SciCalc();
  // Returns the string to display to the screen
  string getString();
  // Sets the string to display to the screen (Testing only)
  void setDisplay(const string s);
  // Parses the final formula
  double parse();
  // Adds a character (or backspace) to the forlmula
  void addChar(const char c);
  // Gets the value of a variable
  double getVar(int i);
  // Evaluates the given op using d1 and d2 as arguments
  double eval(double d1, double d2, char op);
	
  bool assign;
  
private:
  typedef enum{LP, RP, OP, NUM, ANS, VAR} type;
  //double evaluate(char* toEval);
  bool isNum(const char c) {
    return (('0' <= c && c <= '9') || c == '.');
  }
  bool isOp(const char c) {
    return (c == '%' || c == '^' ||
            c == '+' || c == '-' ||
            c == '/' || c == '*' ||);
  }
  bool isVar(const char c) {
    return (c == 'a' || c == 'b' || c == 'c');
  }
  bool isParen(const char c) {
      return (c == '(' || c == ')');
  }
  int opValue(const char c);

  string display;
  bool working;
  type prev;
  double prev_ans;
  bool dotLock;
  double[3] vars;
  
};
#endif