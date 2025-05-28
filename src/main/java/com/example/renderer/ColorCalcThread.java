package com.example.renderer;

public class ColorCalcThread implements Runnable {
    
    private int startX, endX, startY, endY, recursion, n;
    private Screen screen;

    public ColorCalcThread(int startX, int endX, int startY, int endY, int n, int recursion, Screen screen) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.screen = screen;
        this.recursion = recursion;
        this.n = n;
    }

    @Override
    public void run() {
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                ImageColor color = Calculate.colorToImageColor(screen.SSAA(x, y, n, recursion));
                screen.image.plotPixel(x, y, color);
            }
        }
    }

}
