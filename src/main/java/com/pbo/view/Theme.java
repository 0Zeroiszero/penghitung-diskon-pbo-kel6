package com.pbo.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Theme {
    public static final Color BACKGROUND = new Color(255, 248, 243);
    public static final Color SURFACE = Color.WHITE;
    public static final Color SURFACE_SOFT = new Color(255, 243, 233);
    public static final Color SURFACE_BORDER = new Color(241, 227, 215);

    public static final Color PRIMARY = new Color(249, 115, 22);
    public static final Color PRIMARY_DARK = new Color(234, 88, 12);
    public static final Color PRIMARY_LIGHT = new Color(253, 186, 116);
    public static final Color PRIMARY_TINT = new Color(255, 237, 213);

    public static final Color SECONDARY = new Color(13, 148, 136);
    public static final Color SECONDARY_DARK = new Color(15, 118, 110);
    public static final Color SECONDARY_TINT = new Color(204, 251, 241);
    public static final Color ACCENT = new Color(251, 191, 36);

    public static final Color TEXT = new Color(31, 41, 55);
    public static final Color TEXT_SOFT = new Color(75, 85, 99);
    public static final Color TEXT_MUTED = new Color(107, 114, 128);
    public static final Color TEXT_ON_DARK = Color.WHITE;
    public static final Color TEXT_ON_DARK_SOFT = new Color(255, 235, 218);

    public static final Color BORDER = new Color(241, 227, 215);
    public static final Color BORDER_STRONG = new Color(229, 198, 176);
    public static final Color INPUT_BG = new Color(255, 252, 250);

    public static final Color SUCCESS = new Color(16, 185, 129);
    public static final Color SUCCESS_DARK = new Color(15, 118, 110);
    public static final Color SUCCESS_BG = new Color(236, 253, 245);
    public static final Color SUCCESS_BORDER = new Color(153, 246, 228);
    public static final Color ERROR = new Color(220, 38, 38);

    public static final Color SIDEBAR_START = new Color(251, 146, 60);
    public static final Color SIDEBAR_END = new Color(234, 88, 12);
    public static final Color TOAST_BG = new Color(31, 41, 55);

    public static final int RADIUS_SM = 10;
    public static final int RADIUS_MD = 14;
    public static final int RADIUS_LG = 22;
    public static final int RADIUS_XL = 28;
    public static final int RADIUS_PILL = 999;

    public static final int SPACE_XS = 4;
    public static final int SPACE_SM = 8;
    public static final int SPACE_MD = 16;
    public static final int SPACE_LG = 24;
    public static final int SPACE_XL = 32;

    private static final String FONT_FAMILY = pilihFont();

    private Theme() {
    }

    public static Font font(int gaya, int ukuran) {
        return new Font(FONT_FAMILY, gaya, ukuran);
    }

    public static Color alpha(Color warna, int alpha) {
        return new Color(warna.getRed(), warna.getGreen(), warna.getBlue(), Math.max(0, Math.min(255, alpha)));
    }

    public static Color mix(Color a, Color b, float rasio) {
        float t = Math.max(0f, Math.min(1f, rasio));
        int r = Math.round(a.getRed() + (b.getRed() - a.getRed()) * t);
        int g = Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bl = Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t);
        return new Color(r, g, bl);
    }

    private static String pilihFont() {
        Set<String> tersedia = new HashSet<>(Arrays.asList(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        for (String kandidat : new String[]{"Segoe UI Variable Text", "Segoe UI", "Inter", "Poppins"}) {
            if (tersedia.contains(kandidat)) {
                return kandidat;
            }
        }
        return Font.SANS_SERIF;
    }
}
