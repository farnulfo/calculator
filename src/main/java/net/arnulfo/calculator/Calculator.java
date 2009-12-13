/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.arnulfo.calculator;

import java.math.BigInteger;
import java.util.Stack;

/**
 *
 * @author franck
 */
public class Calculator {

  private void checkExpression(String expression) {
    if (expression == null) {
      throw new IllegalArgumentException("Expression cannot be null");
    }
    if (expression.equals("")) {
      throw new IllegalArgumentException("Expression cannot be empty");
    }
  }

  public enum State {

    OPERANDE, OPERATOR
  };

  public enum Operator {

    LEFT_PARENTHESIS, RIGHT_PARENTHESIS, ADDITION, SUBSTRACTION, MULTIPLICATION, DIVISION, UNARY_MINUS, POWER ;

    private void operate(Stack<BigInteger> operandes) {
      BigInteger op1;
      BigInteger op2;

      switch(this) {
        case ADDITION:
          op2 = operandes.pop();
          op1 = operandes.pop();
          operandes.push(op1.add(op2));
          break;

        case SUBSTRACTION:
          op2 = operandes.pop();
          op1 = operandes.pop();
          operandes.push(op1.subtract(op2));
          break;

        case MULTIPLICATION:
          op2 = operandes.pop();
          op1 = operandes.pop();
          operandes.push(op1.multiply(op2));
          break;

        case DIVISION:
          op2 = operandes.pop();
          op1 = operandes.pop();
          operandes.push(op1.divide(op2));
          break;

        case UNARY_MINUS:
          op1 = operandes.pop();
          operandes.push(op1.negate());
          break;

        case POWER:
          op2 = operandes.pop();
          op1 = operandes.pop();
          operandes.push(op1.pow(op2.intValue()));
          break;

        default:
          throw new UnsupportedOperationException("operate function for operator " + this.name() + " not yet implemented.");
      }
    }

  };

  /*
   * 2+3*4/2-4
   *
   * 2
   *
   * 2
   * 
   *
   */
  public BigInteger eval(String expression) {
    checkExpression(expression);

    Stack<Operator> operators = new Stack<Operator>();
    operators.add(Operator.LEFT_PARENTHESIS);
    Stack<BigInteger> operandes = new Stack<BigInteger>();

    State currentState = State.OPERANDE;
    int currentCharIndex = 0;

    expression = expression + ')';
    boolean stop = false;
    while (!stop) {

      char c = expression.charAt(currentCharIndex);
      switch (currentState) {
        case OPERANDE:
          if (Character.isDigit(c)) {
            int startDigit = currentCharIndex;

            while (Character.isDigit(expression.charAt(currentCharIndex + 1))) {
              currentCharIndex++;
            }
            int endDigit = currentCharIndex;

            String digit = expression.substring(startDigit, endDigit + 1);
            BigInteger i = new BigInteger(digit);

            operandes.add(i);
            currentState = State.OPERATOR;
 
          } else if (c == '(') {
            operators.add(Operator.LEFT_PARENTHESIS);
          } else if (c == '-') {
            operators.add(Operator.UNARY_MINUS);
          } else {
            throw new IllegalArgumentException("Unexpected operand character '" + c + "'");
          }

          currentCharIndex++;
          break;
        case OPERATOR:
          Operator currentOp = null;
          switch (c) {
            case '+':
              currentOp = Operator.ADDITION;
              break;
            case '-':
              currentOp = Operator.SUBSTRACTION;
              break;
            case '*':
              currentOp = Operator.MULTIPLICATION;
              break;
            case '/':
              currentOp = Operator.DIVISION;
              break;
            case '(':
              currentOp = Operator.LEFT_PARENTHESIS;
              break;
            case ')':
              currentOp = Operator.RIGHT_PARENTHESIS;
              break;
            case '^':
              currentOp = Operator.POWER;
              break;
            default:
              throw new IllegalArgumentException("Unknown operator '" + c + "'");
          }

          
          while(operators.peek().ordinal() > currentOp.ordinal()) {
            Operator op = operators.pop();
            op.operate(operandes);
          }
          
          if (currentOp == Operator.RIGHT_PARENTHESIS){
            Operator op = operators.pop();
            if (op != Operator.LEFT_PARENTHESIS) {
              throw new IllegalArgumentException("Right parenthesis without corresponding left parenthesis");
            }
            currentState = State.OPERATOR;
          } else {
            operators.add(currentOp);
            currentState = State.OPERANDE;
          }

          
          currentCharIndex++;
          break;
        default:
          break;
      }

      if (currentCharIndex + 1 > expression.length()) {
        stop = true;
      }
    }
    return operandes.pop();
  }
}
