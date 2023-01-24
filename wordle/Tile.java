package org.cis1200.wordle;

import java.awt.*;

public class Tile {
    public static final int SIZE = 50;
    private Color color;
    private int posX;
    private int posY;
    private char letter;
    boolean show;

    public Tile(char letter, int r, int g, int b, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.color = new Color(r, g, b);
        this.letter = letter;
        show = true;
    }

    // initialize blank tile with no character or state
    public Tile(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        color = Color.gray;
        show = false;
    }

    public Tile setChar(char c) {
        letter = c;
        return this;
    }

    public void showTile() {
        show = true;
    }

    public void drawTile(Graphics g) {
        if (show) {
            g.setColor(this.color);
        } else {
            g.setColor(Color.GRAY);
        }
        g.fillRect(posX, posY, SIZE, SIZE);
        g.setColor(Color.BLACK);
        if ((int) letter != 0) {
            g.setFont(new Font("arial", Font.PLAIN, 24));
            g.drawString(String.valueOf(letter), posX + 17, posY + 34);
        }
    }

    public void changeTileColor(int state) {
        if (state == 0) {
            color = Color.red;
        } else if (state == 1) {
            color = Color.yellow;
        } else if (state == 2) {
            color = Color.green;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // for testing
    public String getColor() {
        if (this.color.equals(Color.red)) {
            return "red";
        } else if (this.color.equals(Color.yellow)) {
            return "yellow";
        } else if (this.color.equals(Color.green)) {
            return "green";
        } else {
            return "gray";
        }
    }

    @Override
    public String toString() {
        if (show) {
            return letter + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue()
                    + " " + posX + " " + posY + ", ";
        }
        return "";
    }
}
