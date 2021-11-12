package clueGame;

import java.awt.Color;

// Author: blongho

/**
 * A class to get the Color value from a string color name
 */
public class MyColor {
    private static Color color;

 public MyColor(){
    color = Color.WHITE;
    }
/**
 * Get the color from a string name
 * 
 * @param col name of the color
 * @return White if no color is given, otherwise the Color object
 */
static Color getColor(String col) {
    switch (col.toLowerCase()) {
    case "black":
        color = Color.BLACK;
        break;
    case "blue":
        color = Color.BLUE;
        break;
    case "cyan":
        color = Color.CYAN;
        break;
    case "darkgray":
        color = Color.DARK_GRAY;
        break;
    case "gray":
        color = Color.GRAY;
        break;
    case "green":
        color = Color.GREEN;
        break;

    case "yellow":
        color = Color.YELLOW;
        break;
    case "lightgray":
        color = Color.LIGHT_GRAY;
        break;
    case "magenta":
        color = Color.MAGENTA;
        break;
    case "orange":
        color = Color.ORANGE;
        break;
    case "pink":
        color = Color.PINK;
        break;
    case "red":
        color = Color.RED;
        break;
    case "brown":
        color = new Color (165,42,42);
        break;
    case "silver":
        color = new Color (200, 200, 200);
        break;
    case "white":
        color = Color.WHITE;
        break;
        }
    return color;
    }
}