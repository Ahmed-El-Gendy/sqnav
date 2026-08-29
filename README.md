# SqNav 🚀

[![](https://jitpack.io/v/Ahmed-El-Gendy/sqnav.svg)](https://jitpack.io/#Ahmed-El-Gendy/sqnav)
[![License: MIT](https://img.shields.io/badge/License-MIT-purple.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)

A modern, fluid, and customizable **Squircle Bottom Navigation Bar** for Android with floating elevated card transitions and glowing neon flash indicators.

---

## 📸 Preview

- **Floating Squircle Card**: Elevated active state with smooth spring overshoot animations.
- **Neon Flash Indicator**: Glowing spark sitting in front on the active card.
- **Full Color & Geometry Customization**: Background, card size, stroke, corner radius, and glow colors.

---

## 📦 Installation

### Step 1: Add JitPack repository

In your `settings.gradle.kts` (or root `build.gradle`):

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
```

### Step 2: Add the dependency

In your app module `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.Ahmed-El-Gendy:sqnav:v1.0.0")
}
```

---

## 🚀 Usage

### In XML Layout:

```xml
<com.sagendy.sqnav.SqNav
    android:id="@+id/bottomNav"
    android:layout_width="match_parent"
    android:layout_height="72dp"
    android:layout_gravity="bottom"
    app:sq_backgroundColor="#231C30"
    app:sq_cardColor="#231C30"
    app:sq_cardStrokeColor="#4E3B70"
    app:sq_cardRadius="8dp"
    app:sq_cardSize="58dp"
    app:sq_selectedColor="#D4BBFF"
    app:sq_unselectedColor="#E0DCF0"
    app:sq_glowDotColor="#D4BBFF"
    app:sq_showGlowDot="true" />
```

### In Java / Kotlin:

```java
SqNav sqNav = findViewById(R.id.bottomNav);

// Add items dynamically
sqNav.addItem(new SqNavItem(1, "Home", R.drawable.ic_home));
sqNav.addItem(new SqNavItem(2, "Search", R.drawable.ic_search));
sqNav.addItem(new SqNavItem(3, "Downloads", R.drawable.ic_downloads));
sqNav.addItem(new SqNavItem(4, "Profile", R.drawable.ic_profile));

// Listen for tab selection
sqNav.setOnItemSelectedListener(itemId -> {
    switch (itemId) {
        case 1:
            // Navigate to Home
            break;
        case 2:
            // Navigate to Search
            break;
        case 3:
            // Navigate to Downloads
            break;
        case 4:
            // Navigate to Profile
            break;
    }
});

// Programmatic selection
sqNav.setSelectedItemId(1, true);
```

---

## 🎨 Customizable XML Attributes

| Attribute | Format | Default | Description |
|---|---|---|---|
| `app:sq_backgroundColor` | color | `#231C30` | Bar background color |
| `app:sq_backgroundRadius` | dimension | `20dp` | Top corner radius of the bar |
| `app:sq_cardColor` | color | `#231C30` | Floating squircle card background |
| `app:sq_cardStrokeColor` | color | `#4E3B70` | Border stroke color of the card |
| `app:sq_cardRadius` | dimension | `8dp` | Corner radius of the card |
| `app:sq_cardSize` | dimension | `58dp` | Width & height of the floating card |
| `app:sq_selectedColor` | color | `#D4BBFF` | Active icon & glow color |
| `app:sq_unselectedColor` | color | `#E0DCF0` | Inactive icon & label color |
| `app:sq_glowDotColor` | color | `#D4BBFF` | Top neon flash dot color |
| `app:sq_showGlowDot` | boolean | `true` | Show or hide the top glow dot |

---

## 📄 License

```
Copyright (c) 2026 Ahmed El-Gendy

Licensed under the MIT License.
```
