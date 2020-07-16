package ua.com.executor;


import lombok.SneakyThrows;
import ua.com.ControllerAdvice.ExceptionA;
import ua.com.ControllerAdvice.ExceptionB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Executor {
    public static void execute() {
        catchExceptions();
        rethrowingExceptionFirst();
    }

    @SneakyThrows
    private static int ReadInteger() {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(input);
        try {
            return Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bufferedReader != null) {
            input.close();
            bufferedReader.close();
        }
        return 0;
    }

    private static void catchExceptions() {
        int firstNumber, secondNumber, opCode;
        loop:
        while (true) {
            System.out.println("=================RULES=================");
            System.out.println("-max numbers to 10;\n-reply with negative numbers are not accepted, only>0;\n-other actions will lead to exceptions");
            System.out.println("=================RULES=================");
            System.out.println("Choose arithmetic operations 1)+ , 2)- , 3)* , 4)/ 5)exit to rethrowingException");
            opCode = ReadInteger();
            if (opCode == 5) {
                break loop;
            }
            System.out.println(" \nInput first number");
            firstNumber = ReadInteger();
            System.out.println(" \nInput second number");
            secondNumber = ReadInteger();

            switch (opCode) {
                case 1 -> {
                    if (firstNumber < 10 && secondNumber < 10) {
                        System.out.println(firstNumber + secondNumber);
                    } else {
                        try {
                            throw new ExceptionA("This exception catch both of exceptions: ExceptionA");
                        } catch (ExceptionA exceptionA) {
                            exceptionA.printStackTrace();
                            System.out.println("This exception accepted numbers to 10");
                        }
                    }
                }
                case 2 -> {
                    if (firstNumber > secondNumber) {
                        System.out.println(firstNumber - secondNumber);
                    } else {
                        try {
                            if (firstNumber > 10 && secondNumber > 10) {
                                throw new ExceptionA("numbers must be to 10: ExceptionA");
                            } else if (firstNumber < secondNumber && -firstNumber > -secondNumber) {
                                throw new ExceptionB("negative numbers are not accepted: ExceptionB");
                            }
                        } catch (ExceptionA | ExceptionB ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                case 3 -> {
                    try {
                        if (firstNumber != 0 || secondNumber != 0) {
                            System.out.println(firstNumber * secondNumber);
                        }
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
                case 4 -> {
                    if (secondNumber != 0) {
                        System.out.println(firstNumber / secondNumber);
                    } else throw new ArithmeticException();
                }
                case 5 -> {
                    break loop;
                }
                default -> System.out.println("Enter right number operation");
            }
        }
    }

    private static void rethrowingExceptionFirst() {
        try {
            rethrowingExceptionSecond();
        } catch (NullPointerException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }

    private static void rethrowingExceptionSecond() throws NoSuchFieldException {
        throw new NoSuchFieldException();
    }
}
