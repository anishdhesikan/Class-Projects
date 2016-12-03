#include "SciCalc.h"
using namespace SciCalc;

// Constructor
void SciCalc()
{
  display = "";
  prev = ANS;
  prev_ans = 0.0;
  working = false;
  dotLock = false;
  vars = {0,0,0,0};
}

int opValue(const char c) {
  switch (c) {
    case '^':
      return 5;
    case '%':
      return 4;
    case '*':
      return 3;
    case '/':
      return 2;
    case '+':
      return 1;
    case '-':
      return 0;
    default:
      return -1;
  }
}

// Returns the string to display to the screen
string getString()
{
  if (working) {
    return this.display();
  }
  else {
    return std::to_string(prev_ans);
  }
}

void setDisplay(const string s) {
  display = s;
}

void addChar(const char c)
{
  if (assign) {
      switch (c) {
        case 'a':
          vars[0] = prev_ans;
          break;
        case 'b':
          vars[1] = prev_ans;
          break;
        case 'c':
          vars[2] = prev_ans;
          break;
        default:
          break;
    }
    assign = false;
    if (isVar(c)) {
      return;
    }
  }
  
  if (!working) {
    display = "";
    working = true;
  }
  
  // Backspace - remove character
  if (c == '´' && strlen(display) > 0) {
    char temp = &(strrchr(display, '\0') - 1);
    //delete char
    display += "\b\0\b";
    // if dot was deleted, unlock dots
    if (temp == '.') {
      dotLock = true;
    }
    // if "ans" was deleted, delete all of "ans"
    if (temp == 's') {
      display += "\b\b\0\0\b\b";
    }
  }
  // Decimal
  else if (c == '.' && dotLock) {
    display += c;
    dotLock = false;
  }
  // Number
  else if (isNum(c)) {
    display += c;
  }
  // Operation
  else if (isOp(c) && !isParen(c)){
    if (strlen(display) == 0) {
      display += "Ans";
    }
    display += c;
    dotLock = true;
  }
  // Parenthesis or Variable.
  else {
    display += c;
    dotLock = true;
  }
}

double getVar(int i)
{
  if (i < 0 || i >= 3) {
    printf("Error: Should not get here!")
  }
  else {
    return vars[i];
  }
}

// Converts the String into RPN (Reverse Polish Notation), using the
// Shunting-Yard algorithm, then calculates the answer from that.
double parse()
{
  // Add spaces to tokenize string
  char * s_ptr = display;
  char[500] input = "";
  bool success = true;
  for (int i = 0; i < strlen(display); i++) {
    // Previous Answer (always at front if available)
    if (s_ptr[i] = 'A') {
      i += 2;
      input += std::to_string(prev_ans);
      prev = ANS;
    }
    // Numbers
    else if (isNum(s_ptr[i])) {
      if (prev != NUM) {
        input += " ";
      }
      if (prev == VAR || prev == RP || prev == ANS) {
        input += "* ";
      }
      input += s_ptr[i];
      prev = NUM;
    }
    // Variables
    else if (isVar(s_ptr[i])) {
      input += " ";
      if (prev != LP && prev != OP) {
        input += "* ";
      }
      input += std::to_string(getVar(s_ptr[i]));
      prev = VAR;
    }
    // Operators
    else if (isOp(s_ptr[i])) {
      if (prev == LP || prev == OP) {
        // ERROR - DOUBLED OPERATORS
        success = false;
      }
      else {
        input += " ";
      }
      input += s_ptr[i];
      prev = OP;
    }
    // Left Paren
    else if (s_ptr[i] == '(') {
      input += " ";
      if (prev != LP && prev != OP) {
        input += "* ";
      }
      input += '(';
      prev = LP;
    }
    // Right Paren
    else if (s_ptr[i] == ')') {
      if (prev == LP) {
        input += "\b\b\0\0\b\b";
        prev = OP;
      }
      else {
        if (prev == OP) {
          // ERROR - MISSING VALUE
          success = false;
        }
        else {
          input += " )";
        }
        prev = RP;
      }
    }
    // Other
    else {
      // ERROR - UNRECOGNIZED SYMBOL
      success = false;
    }
  }
  
  if (!success) {
    prev_ans = 0.0;
    display = "ERR";
    return 0.0;
  }
  
  // Begin Shunting-Yard algorithm
  char[500] output = "";
  char[50] opStack = "";
  int sp = -1;
  char* cur_tok = strtok(input, " ");
  
  while (cur_tok != NULL) {
    // Operator
    if (isOp(cur_tok[0])) {
      while (sp >= 0 && opValue(cur_tok[0]) < opValue(opStack[sp])) {
        output += " ";
        output += opStack[sp--];
      }
      opStack[++sp] = cur_tok[0];
    }
    // Left Paren
    if (cur_tok[0] = '(') {
      opStack[++sp] = '(';
    }
    // Right Paren
    if (cur_tok[0] = ')') {
      while (sp >= 0 && opStack[sp--] != '(') {
        output += " ";
        output += opStack[sp + 1];
      }
      if (sp == -1) {
        //ERROR - MISMATCHED PARENTHESES: doesn't matter.
      }
    }
    // Number
    else {
      output += " ";
      output += cur_tok;
    }
  }
  char temp;
  while (sp >= 0) {
    temp = opStack[sp--]
    switch () {
      case '(':
        break;
      default:
        output += " ";
        output += c;
        break;
    }
  }
  // End Shunting-Yard algorithm
  
  if (!success) {
    prev_ans = 0.0;
    display = "ERR";
    return 0.0;
  }
  
  // Begin RPN processing
  double[50] numStack = {};
  double n1, n2;
  char* cur_tok = strtok(output, " ");
  
  while (cur_tok != NULL) {
    if (isOp(cur_tok[0])) {
      n2 = numStack[sp--];
      n1 = numStack[sp];
      if (sp < 0) {
        success = false;
      }
      numStack[sp] = eval(n1, n2, cur_tok[0]);
    }
    else {
      numStack[++sp] = std::stod(cur_tok, NULL);
    }
  }
  if (sp > 0) {
    success = false;
  }
  // End RPN Processing - Answer is only number left on stack.
  if (!success) {
    prev_ans = 0.0;
    display = "ERR";
    return 0.0;
  }
  
  working = false;
  return prev_ans = numStack[0];
}

double eval(double d1, double d2, char c) {
    switch (c) {
      case '^':
        return d1 ^ d2;
      case '%':
        return d1 % d2;
      case '*':
        return d1 * d2;
      case '/':
        return d1 / d2;
      case '+':
        return d1 + d2;
      case '-':
        return d1 - d2;
      default:
        return -1;
    }
}
















