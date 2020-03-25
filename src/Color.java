import cz.gyarab.util.light.LightColor;

public enum Color {
    WHITE(LightColor.CHESSBOARD_WHITE), BLACK(LightColor.CHESSBOARD_BLACK);
    private final LightColor color;

    // I solve the problem differently (I don't need a direction in enum)
    Color(LightColor barva) {
        this.color = barva;

    }

    public LightColor getColor() {
        return color;
    }

}

