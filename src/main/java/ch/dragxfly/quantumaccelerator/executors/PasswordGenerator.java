package ch.dragxfly.quantumaccelerator.executors;

import controller.popupwindows.PasswordGeneratorController;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author jannis
 */
public class PasswordGenerator {

    private final PasswordGeneratorController controller;
    private int currentPwLength = 0, requestedPwLength = 0;
    private double mouseX, mouseY;
    private String password = "";
    private final double ARRAY_SYMBOLS_SIZE, ARRAY_SYMBOLS_PERCENT;
    private final BigDecimal bgXMultYPercent;
    private boolean isRunning = false;
    private final ArrayList<Character> symbolsAsList;
    private final Character symbols[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        '$', '_', '?', '!', '%', ')', '(', '/', '+', '*', '#', '[', ']', ':'};

    public PasswordGenerator(PasswordGeneratorController controller) {
        symbolsAsList = new ArrayList<>(Arrays.asList(symbols));
        Collections.shuffle(symbolsAsList);
        ARRAY_SYMBOLS_SIZE = symbols.length;
        ARRAY_SYMBOLS_PERCENT = 100 / ARRAY_SYMBOLS_SIZE;
        bgXMultYPercent = new BigDecimal(0.0015873015873016);
        this.controller = controller;
    }

    private void requestMouseCoordinates() {
        mouseX = controller.getMouseX();
        mouseY = controller.getMouseY();
    }

    public void startGenerating(int passwordLength) {
        isRunning = true;
        this.requestedPwLength = passwordLength;
        while (currentPwLength < passwordLength) {
            try {
                Thread.sleep(getRandomTime());
                if (controller.isIsMouseOnRectangle()) {
                    requestMouseCoordinates();
                    char nextPwChar = coordinatesIntoChar(mouseX, mouseY);
                    if (password.endsWith(Character.toString(nextPwChar))) {
                        //do nothing
                    } else {
                        password += nextPwChar;
                        currentPwLength = password.length();
                        controller.setPasswordText(password);
                    }
                }
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
            isRunning = false;
        }
    }

    private int getRandomTime() {
        Random rnd = new Random();
        return rnd.nextInt(200 - 50) + 200;
    }

    private char coordinatesIntoChar(double mouseX, double mouseY) {
        //Calculates the mouse coordinates into a char
        double xTimesY = mouseX * mouseY;
        BigDecimal xMultipliedWithY = new BigDecimal(xTimesY);
        BigDecimal xTimesYOfMax = xMultipliedWithY.multiply(bgXMultYPercent);
        //Rounds to next integer (index in array) when converted from percent
        BigDecimal percentageOfArray = new BigDecimal(ARRAY_SYMBOLS_PERCENT * (Math.ceil(Math.abs(xTimesYOfMax.doubleValue() / ARRAY_SYMBOLS_PERCENT))));
        //From percent to integer (index)
        BigDecimal index = percentageOfArray.multiply(new BigDecimal(ARRAY_SYMBOLS_SIZE)).divide(new BigDecimal(100));
        return symbolsAsList.get(index.intValue());
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void reset() {
        Collections.shuffle(symbolsAsList);
        currentPwLength = 0;
        isRunning = false;
        password = "";
    }
}
