package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the secret code's length: ");
        String input = scanner.nextLine();
        if (!input.matches("[0-9]+")) {
            System.out.println(String.format("Error: %s isn't a valid number.", input));
            scanner.close();
            return;
        }
        int digits = Integer.parseInt(input);

        if (digits == 0) {
            System.out.println(String.format("Error: %s isn't a valid number.", input));
            scanner.close();
            return;          
        }
        
        if (digits > 36) {
            System.out.println(String.format("Error: can't generate a secret code with a length of %d because there aren't enough unique digits.", digits));
            scanner.close();
            return;
        }

        System.out.println("Input the number of possible symbols in the code: ");
        int symbols = Integer.parseInt(scanner.nextLine());

        if (digits > symbols) {
            System.out.println(String.format("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", digits, symbols));
            scanner.close();
            return;
        }

        if (symbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            scanner.close();
            return;
        }

        String asterisks = "";
        for (int i = 0; i < digits; i++) {
            asterisks += "*";
        }

        if (symbols <= 10) {
            System.out.println(String.format("The secret is prepared: %s (0-%d).", asterisks, symbols - 1));
        } else {
            System.out.println(String.format("The secret is prepared: %s (0-9,a-%c).", asterisks, symbols - 10 + 96));
        }

        System.out.println("Okay, let's start a game!");
       
        String secretCode = getSecretCode(digits, symbols);

        boolean result = false;
        int i = 1;
        while (!result) {
            System.out.println("Turn " + i);
            String answer = scanner.nextLine();
            if (answer.length() != digits) {
                continue;
            }
            result = game(digits, secretCode, answer);
            i++;
        }

        scanner.close();   
        
        System.out.println("Congratulations! You guessed the secret code.");
    }

    static String getSecretCode(int digits, int symbols) {
        String table = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();

        String code = "";
        while (code.length() != digits) {
            int index = Math.abs(random.nextInt()) % symbols;
            char digit = table.charAt(index);
            if (isUnique(digit, code)) {
                code += digit;
            }
        }
        return code;
    } 

    static boolean isUnique(char digit, String code) {
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == digit) {
                return false;
            }
        }
        return true;
    }

    static boolean game(int digits, String secretCode, String answer) {

        int bulls = 0;
        for (int i = 0; i < digits; i++) {
            if (secretCode.charAt(i) == answer.charAt(i) ) {
                bulls++;
            }
        }

        int cows = 0;
        for (int i = 0; i < digits; i++) {
            for (int j = 0; j < digits; j++) {
                if (i == j) {
                    continue;
                }
                if (secretCode.charAt(i) == answer.charAt(j)) {
                    cows++;
                }
            }
        }

        if (bulls == 0 && cows == 0) {
            System.out.println(String.format("Grade: none"));
        } else if (bulls == 0) {
            System.out.println(String.format("Grade: %d cow(s)", cows));
        } else if (cows == 0) {
            System.out.println(String.format("Grade: %d bull(s)", bulls));
        } else {
            System.out.println(String.format("Grade: %d bull(s) and %d cow(s)", bulls, cows));
        }

        return digits == bulls;
    }
}
