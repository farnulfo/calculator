/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.arnulfo.calculator;

import java.math.BigInteger;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author franck
 */
public class CalculatorTest {

  private Calculator calculator = new Calculator();

  @Test
  public void testCompareTo() {
    //Assert.assertTrue(Calculator.Operator.RIGHT_PARENTHESIS.compareTo(Calculator.Operator.ADDITION) > 0);
    //Assert.assertTrue(Calculator.Operator.RIGHT_PARENTHESIS.compareTo(Calculator.Operator.ADDITION) > 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull() {

    calculator.eval(null);
    // TODO review the generated test code and remove the default call to fail.
    //fail("The test case is a prototype.");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmpty() {

    calculator.eval("");
    // TODO review the generated test code and remove the default call to fail.
    //fail("The test case is a prototype.");
  }

  @Test
  public void testSimpleInteger() {
    Assert.assertEquals(new BigInteger("123"), calculator.eval("123"));
  }

  @Test
  public void testUnary() {
    Assert.assertEquals(new BigInteger("-123"), calculator.eval("-123"));
  }

  @Test
  public void testSimpleAddition() {
    Assert.assertEquals(new BigInteger("4"), calculator.eval("1+3"));
    Assert.assertEquals(new BigInteger("15"), calculator.eval("12+3"));
    Assert.assertEquals(new BigInteger("33"), calculator.eval("1+32"));
    Assert.assertEquals(new BigInteger("1266"), calculator.eval("1233+33"));
  }

  @Test
  public void testAdditionSubstraction() {
    Assert.assertEquals(new BigInteger("1"), calculator.eval("2+3-4"));
  }

  @Test
  public void testAdditionMultiplication() {
    Assert.assertEquals(new BigInteger("14"), calculator.eval("2+3*4"));
  }

  @Test
  public void testAdditionMultiplicationAddition() {
    Assert.assertEquals(new BigInteger("21"), calculator.eval("2+3*4+7"));
  }

  @Test
  public void testMultiplicationAddition() {
    Assert.assertEquals(new BigInteger("10"), calculator.eval("2*3+4"));
  }

  @Test
  public void testMultiplicationDivision() {
    Assert.assertEquals(new BigInteger("4"), calculator.eval("2*8/4"));
  }

  @Test
  public void testParenthesis() {
    Assert.assertEquals(new BigInteger("14"), calculator.eval("2*(3+4)"));
    Assert.assertEquals(new BigInteger("10"), calculator.eval("(2*3)+4"));
    Assert.assertEquals(new BigInteger("-30"), calculator.eval("(2+3)*4+5*(4-7*2)"));
    Assert.assertEquals(new BigInteger("-250"), calculator.eval("((2+3)*4+5)*(4-7*2)"));
    Assert.assertEquals(new BigInteger("-10"), calculator.eval("(-(-2+3)*4+5)*(4-7*2)"));
  }

  @Test
  public void testPower() {
    Assert.assertEquals(new BigInteger("-25"), calculator.eval("-5^2"));
    Assert.assertEquals(new BigInteger("25"), calculator.eval("(-5)^2"));
  }
}
