package dgroomes.simple_arithmetic;

import static java.lang.System.out;
// This is an unused import. Normally, our IDE would highlight this and we would remove it. But for the sake of this
// project, we'll let the OpenRewrite recipe find it and propose a change to remove it.
import java.lang.Double;

/**
 * Please see the README for more information.
 */
public class SimpleArithmeticRunner {
    public static void main(String[] args) {
        {
            int sum = 5 + 3;
            out.printf("""
                    Addition
                        int sum = 5 + 3
                        'sum' is equal to %d
                    %n""", sum);
        }

        {
            int difference = 5 - 3;
            out.printf("""
                    Subtraction
                        int difference = 5 - 3
                        'difference' is equal to %d
                    %n""", difference);
        }

        {
            int product = 5 * 3;
            out.printf("""
                    Multiplication
                        int product = 5 * 3
                        'product' is equal to %d
                    %n""", product);
        }

        {
            int quotient = 5 / 3;
            out.printf("""
                    Division
                        int quotient = 5 / 3
                        'quotient' is equal to %d
                    %n""", quotient);
        }

        {
            int remainder = 5 % 3;
            out.printf("""
                    Modulus
                        int remainder = 5 %% 3
                        'remainder' is equal to %d
                    %n""", remainder);
        }

        {
            int overflowSum = 2_147_483_647 + 1; // This is an overflow
            out.printf("""
                    Overflow
                        int overflowSum = 2_147_483_647 + 1
                        'overflowSum' is equal to %d
                    %n""", overflowSum);
        }
    }
}
