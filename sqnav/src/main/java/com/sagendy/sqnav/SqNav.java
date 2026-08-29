package com.sagendy.sqnav;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * SqNav - Modern Squircle Bottom Navigation Bar with Floating Elevated Cards and Neon Glow Indicators.
 * Open-source library published on JitPack.
 *
 * @author Ahmed El-Gendy (https://github.com/Ahmed-El-Gendy/sqnav)
 */
public class SqNav extends FrameLayout {

    private int barBackgroundColor = 0xFF231C30;
    private float barCornerRadius = 20f;
    private int cardColor = 0xFF231C30;
    private int cardStrokeColor = 0xFF4E3B70;
    private float cardCornerRadius = 8f;
    private float cardSizeDp = 58f;
    private int selectedColor = 0xFFD4BBFF;
    private int unselectedColor = 0xFFE0DCF0;
    private int glowDotColor = 0xFFD4BBFF;
    private boolean showGlowDot = true;

    private static final long ANIMATION_DURATION = 260; // ms

    private View backgroundView;
    private LinearLayout tabsContainer;
    private final List<TabViewHolder> tabHolders = new ArrayList<>();
    private final List<SqNavItem> items = new ArrayList<>();
    private int selectedItemId = -1;
    private OnItemSelectedListener onItemSelectedListener;

    public SqNav(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public SqNav(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SqNav(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setClipChildren(false);
        setClipToPadding(false);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SqNav);
            barBackgroundColor = a.getColor(R.styleable.SqNav_sq_backgroundColor, barBackgroundColor);
            barCornerRadius = a.getDimension(R.styleable.SqNav_sq_backgroundRadius, dpToPx(barCornerRadius));
            cardColor = a.getColor(R.styleable.SqNav_sq_cardColor, cardColor);
            cardStrokeColor = a.getColor(R.styleable.SqNav_sq_cardStrokeColor, cardStrokeColor);
            cardCornerRadius = a.getDimension(R.styleable.SqNav_sq_cardRadius, dpToPx(cardCornerRadius));
            cardSizeDp = a.getDimension(R.styleable.SqNav_sq_cardSize, dpToPx(cardSizeDp));
            selectedColor = a.getColor(R.styleable.SqNav_sq_selectedColor, selectedColor);
            unselectedColor = a.getColor(R.styleable.SqNav_sq_unselectedColor, unselectedColor);
            glowDotColor = a.getColor(R.styleable.SqNav_sq_glowDotColor, glowDotColor);
            showGlowDot = a.getBoolean(R.styleable.SqNav_sq_showGlowDot, showGlowDot);
            a.recycle();
        }

        // 1. Background Bar
        backgroundView = new View(context);
        GradientDrawable bgShape = new GradientDrawable();
        bgShape.setShape(GradientDrawable.RECTANGLE);
        bgShape.setColor(barBackgroundColor);
        bgShape.setCornerRadii(new float[]{barCornerRadius, barCornerRadius, barCornerRadius, barCornerRadius, 0, 0, 0, 0});
        bgShape.setStroke((int) dpToPx(1f), 0xFF3B3156);
        backgroundView.setBackground(bgShape);
        addView(backgroundView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // 2. Tabs Container
        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setClipChildren(false);
        tabsContainer.setClipToPadding(false);
        tabsContainer.setGravity(Gravity.CENTER_VERTICAL);
        addView(tabsContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void addItem(SqNavItem item) {
        items.add(item);
        buildTabs();
    }

    public void setItems(List<SqNavItem> newItems) {
        items.clear();
        items.addAll(newItems);
        buildTabs();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    public void setSelectedItemId(int itemId, boolean animate) {
        selectTab(itemId, animate, false);
    }

    public int getSelectedItemId() {
        return selectedItemId;
    }

    private void buildTabs() {
        tabsContainer.removeAllViews();
        tabHolders.clear();

        for (SqNavItem item : items) {
            FrameLayout tabView = new FrameLayout(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
            tabView.setLayoutParams(lp);
            tabView.setClipChildren(false);
            tabView.setClipToPadding(false);
            tabView.setClickable(true);
            tabView.setFocusable(true);

            // A. Card Layer
            FrameLayout card = new FrameLayout(getContext());
            int cardSizePx = (int) (cardSizeDp > 100 ? cardSizeDp : dpToPx(cardSizeDp));
            LayoutParams cardLp = new LayoutParams(cardSizePx, cardSizePx, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            cardLp.topMargin = (int) dpToPx(7f);
            card.setLayoutParams(cardLp);
            card.setAlpha(0f);
            card.setScaleX(0.85f);
            card.setScaleY(0.85f);

            GradientDrawable cardShape = new GradientDrawable();
            cardShape.setShape(GradientDrawable.RECTANGLE);
            cardShape.setColor(cardColor);
            cardShape.setCornerRadius(cardCornerRadius > 50 ? cardCornerRadius : dpToPx(cardCornerRadius));
            cardShape.setStroke((int) dpToPx(1.5f), cardStrokeColor);
            card.setBackground(cardShape);
            tabView.addView(card);

            // B. Glow Dot
            View dot = new View(getContext());
            int dotSizePx = (int) dpToPx(8f);
            LayoutParams dotLp = new LayoutParams(dotSizePx, dotSizePx, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            dotLp.topMargin = (int) dpToPx(3.5f);
            dot.setLayoutParams(dotLp);
            dot.setAlpha(0f);
            dot.setElevation(dpToPx(10f));
            dot.setBackgroundResource(R.drawable.sq_bg_neon_dot);
            if (!showGlowDot) dot.setVisibility(GONE);
            tabView.addView(dot);

            // C. Icon
            ImageView icon = new ImageView(getContext());
            int iconSizePx = (int) dpToPx(22f);
            LayoutParams iconLp = new LayoutParams(iconSizePx, iconSizePx, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            iconLp.topMargin = (int) dpToPx(15f);
            icon.setLayoutParams(iconLp);
            icon.setElevation(dpToPx(12f));
            icon.setImageResource(item.getIconRes());
            ImageViewCompat.setImageTintList(icon, ColorStateList.valueOf(unselectedColor));
            tabView.addView(icon);

            // D. Label
            TextView label = new TextView(getContext());
            LayoutParams labelLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            labelLp.bottomMargin = (int) dpToPx(8f);
            label.setLayoutParams(labelLp);
            label.setText(item.getTitle());
            label.setTextSize(11f);
            label.setTextColor(unselectedColor);
            tabView.addView(label);

            TabViewHolder holder = new TabViewHolder(item.getId(), tabView, dot, card, icon, label, selectedColor, unselectedColor);
            tabHolders.add(holder);

            tabView.setOnClickListener(v -> selectTab(item.getId(), true, true));
            tabsContainer.addView(tabView);
        }

        if (selectedItemId != -1) {
            int toSelect = selectedItemId;
            selectedItemId = -1;
            selectTab(toSelect, false, false);
        } else if (!items.isEmpty()) {
            selectTab(items.get(0).getId(), false, false);
        }
    }

    private void selectTab(int itemId, boolean animate, boolean fromUserClick) {
        if (selectedItemId == itemId) return;

        int previousItemId = selectedItemId;
        selectedItemId = itemId;

        float cardFloatPx = dpToPx(20f);
        float iconCenterOffsetPx = dpToPx(10f);

        for (TabViewHolder tab : tabHolders) {
            boolean isSelected = (tab.id == itemId);
            boolean isDeselected = (tab.id == previousItemId);

            if (isSelected) {
                tab.animateSelected(cardFloatPx, iconCenterOffsetPx, animate);
            } else if (isDeselected || previousItemId == -1) {
                tab.animateUnselected(animate);
            }
        }

        if (fromUserClick && onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(itemId);
        }
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    private static class TabViewHolder {
        final int id;
        final View container;
        final View dot;
        final View card;
        final ImageView icon;
        final TextView label;
        final int activeColor;
        final int inactiveColor;

        TabViewHolder(int id, View container, View dot, View card, ImageView icon, TextView label, int activeColor, int inactiveColor) {
            this.id = id;
            this.container = container;
            this.dot = dot;
            this.card = card;
            this.icon = icon;
            this.label = label;
            this.activeColor = activeColor;
            this.inactiveColor = inactiveColor;
        }

        void animateSelected(float cardFloatDist, float iconCenterOffset, boolean animate) {
            if (!animate) {
                dot.setAlpha(1f);
                dot.setScaleX(1f);
                dot.setScaleY(1f);
                dot.setTranslationY(-cardFloatDist);

                card.setAlpha(1f);
                card.setScaleX(1f);
                card.setScaleY(1f);
                card.setTranslationY(-cardFloatDist);

                icon.setTranslationY(-iconCenterOffset);
                icon.setScaleX(1.0f);
                icon.setScaleY(1.0f);
                ImageViewCompat.setImageTintList(icon, ColorStateList.valueOf(activeColor));

                label.setAlpha(0f);
                label.setTranslationY(12f);
                return;
            }

            dot.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .translationY(-cardFloatDist)
                    .setDuration(ANIMATION_DURATION)
                    .setInterpolator(new OvershootInterpolator(1.3f))
                    .start();

            card.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .translationY(-cardFloatDist)
                    .setDuration(ANIMATION_DURATION)
                    .setInterpolator(new OvershootInterpolator(1.3f))
                    .start();

            icon.animate()
                    .translationY(-iconCenterOffset)
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(ANIMATION_DURATION)
                    .setInterpolator(new OvershootInterpolator(1.3f))
                    .start();

            ImageViewCompat.setImageTintList(icon, ColorStateList.valueOf(activeColor));

            label.animate()
                    .alpha(0f)
                    .translationY(12f)
                    .setDuration((long) (ANIMATION_DURATION * 0.7))
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }

        void animateUnselected(boolean animate) {
            if (!animate) {
                dot.setAlpha(0f);
                dot.setScaleX(0f);
                dot.setScaleY(0f);
                dot.setTranslationY(0f);

                card.setAlpha(0f);
                card.setScaleX(0.85f);
                card.setScaleY(0.85f);
                card.setTranslationY(0f);

                icon.setTranslationY(0f);
                icon.setScaleX(1f);
                icon.setScaleY(1f);
                ImageViewCompat.setImageTintList(icon, ColorStateList.valueOf(inactiveColor));

                label.setAlpha(1f);
                label.setTranslationY(0f);
                label.setTextColor(inactiveColor);
                return;
            }

            dot.animate()
                    .alpha(0f)
                    .scaleX(0f)
                    .scaleY(0f)
                    .translationY(0f)
                    .setDuration(ANIMATION_DURATION)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();

            card.animate()
                    .alpha(0f)
                    .scaleX(0.85f)
                    .scaleY(0.85f)
                    .translationY(0f)
                    .setDuration(ANIMATION_DURATION)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();

            icon.animate()
                    .translationY(0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(ANIMATION_DURATION)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();

            ImageViewCompat.setImageTintList(icon, ColorStateList.valueOf(inactiveColor));

            label.setTextColor(inactiveColor);
            label.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(ANIMATION_DURATION)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }
    }
}
