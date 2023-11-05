# MenuUtil

A utility for creating menus with refreshable and clickable icons, as well as for automatically dividing content between pages.  
The utility is written in **Kotlin**, for **Minecraft** version **1.19.4** using **PaperAPI**.
---
## Usage Example

### Creating icon
```kotlin
//Creating icon, which you can put into menu.
menuIcon(46, item(Material.ARROW) {
                editMeta { it.displayName("§fPrevious page".toComponent()) }
}) {
  //Adding click action.
  click {
    //Any action with InventoryClickEvent context  .
  }

   //Adding updater.
  updater(0, 2) {
    //Any action which be execute every 2 ticks.
  }
}
```

### Creating menu
```kotlin
//Creating Menu with parameters title, rows count and the player who will be the spectator.
Menu("Menu Title", 6, player) {
    //Setup menu properties.
    property {
        addBounds()
        addBottom()
        addTop()
        addButtons()
    }
  //Any action in Menu context.
}.openMenu()
//Menu#openMenu opens the menu to the player.
```
---

## Repository
#### You must use ___JitPack___ repository (`https://jitpack.io/`)

## Gradle dependency
```gradle
dependencies {
    implementation 'com.github.TerraMiner:MenuUtil:v1.0-SNAPSHOT'
}
```
## Maven dependency
```xml
<dependency>
    <groupId>com.github.TerraMiner</groupId>
    <artifactId>MenuUtil</artifactId>
    <version>v1.0-SNAPSHOT</version>
</dependency>
```
