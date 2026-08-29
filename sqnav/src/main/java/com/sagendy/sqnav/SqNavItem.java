package com.sagendy.sqnav;

import androidx.annotation.DrawableRes;

public class SqNavItem {
    private final int id;
    private final String title;
    private final int iconRes;

    public SqNavItem(int id, String title, @DrawableRes int iconRes) {
        this.id = id;
        this.title = title;
        this.iconRes = iconRes;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getIconRes() { return iconRes; }
}
