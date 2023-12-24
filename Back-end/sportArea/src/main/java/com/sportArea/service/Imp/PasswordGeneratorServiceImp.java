package com.sportArea.service.Imp;

import com.sportArea.service.PasswordGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Service
public class PasswordGeneratorServiceImp implements PasswordGeneratorService {

    private Set<String> randomPasswordSet = new HashSet<>();

    @Override
    public String generatePassword(int length) {
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";

        String allCharacters = lowercaseLetters + uppercaseLetters + digits;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Ensure at least one lowercase letter, one uppercase letter, and one digit
        password.append(getRandomChar(lowercaseLetters, random));
        password.append(getRandomChar(uppercaseLetters, random));
        password.append(getRandomChar(digits, random));

        // Add remaining characters randomly
        for (int i = 3; i < length; i++) {
            password.append(getRandomChar(allCharacters, random));
        }

        // Shuffle the password to mix characters
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = passwordArray[index];
            passwordArray[index] = passwordArray[i];
            passwordArray[i] = temp;
        }

        String temporaryPassword = new String(passwordArray);

        if(randomPasswordSet.contains(temporaryPassword)){
            temporaryPassword = generatePassword(length);
        }

        randomPasswordSet.add(temporaryPassword);
        return temporaryPassword;
    }

    private static char getRandomChar(String characters, SecureRandom random) {
        int index = random.nextInt(characters.length());
        return characters.charAt(index);
    }

    public Set<String> getRandomPasswordSet() {
        return randomPasswordSet;
    }
}
