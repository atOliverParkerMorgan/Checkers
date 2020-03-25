package Checkers;

import cz.gyarab.util.light.LightColor;

public enum Color {
    WHITE(LightColor.CHESSBOARD_WHITE), BLACK(LightColor.CHESSBOARD_BLACK);
    private final LightColor color;

    // I solve the problem differently (I don't need a direction in enum)
    Color(LightColor color) {
        this.color = color;

    }

    public LightColor getColor() {
        return color;
    }

}

