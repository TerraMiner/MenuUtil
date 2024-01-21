# MenuUtil

A utility for creating menus with refreshable and clickable icons, as well as for automatically dividing content between pages.  
The utility is written in **Kotlin**, for **Minecraft** 1.8+.
 Can be used with **Java**.
---
## Usage Example

### Creating menu
```kotlin
//Creating Menu with parameters title, rows count and the player who will be the spectator.
    Menu("Menu Title %page%", 6, player) { //%page% - replacement for display current page number
        //Setup menu properties.
        property {
        
        }
        
        //Adding icon.
        addIcon(icon)
    //Any action in Menu context.
    }.openMenu()
//Menu#openMenu opens the menu to the player.
```

### Property examples
___
```kotlin
    property {
        addBounds()
        addBottom()
        addTop()
        addButtons()
    }
```

___
```kotlin
    property {
        pattern {
            it.alias("#", item(Material.CARROT).toMenuIcon())
            it.alias("0", item(Material.DIAMOND).toMenuIcon())
            it.alias("*", item(Material.COBWEB).toMenuIcon())
            //"<", ">" - default keys for buttons.
            it.pattern(
                "#########",
                "#0     0#",
                "#0  *  0#",
                "#0 *** 0#",
                "#0  *  0#",
                "#<#####>#",
            )
        }
    }
```

![](https://i.imgur.com/USATCfI.png)

### Creating icon
```kotlin
    //Creating icon, which you can put into menu.
    menuIcon(46, item(Material.ARROW) {
        itemMeta = itemMeta?.apply {
            setDisplayName("§7Example")
        }
    }) {
        //Any action in Icon context.
    }
```

### Creating functional item
```kotlin
    //Creating icon, which you can put into menu.
    funcIcon(46, item(Material.ARROW) {
        itemMeta = itemMeta?.apply {
            setDisplayName("§7Example")
        }
    }) {
        //Adding click action.
        click {
            //Any action with InventoryClickEvent context.
        }

        //Adding updater.
        updater(0, 2) {
            //Any action which be execute every 2 ticks.
        }
     
        //Adding visible accessor. 
        //Adjusts the visibility of an item relative to the condition
        visibleAccess { page, icon ->
            //The item will only be visible in Creative mode
            page.menu.viewer.gameMode == GameMode.CREATIVE
        }

        //Adding click action accessor. 
        //Adjusts the ability to click an item relative to the condition
        clickAccess { page, icon ->
            //Ability to click on an item only in Creative mode
            page.menu.viewer.gameMode == GameMode.CREATIVE
        }
    }

    // Or you can turn ItemStack to FuncIcon.
    item(Material.STONE) {
        
    }.toFuncIcon {
        //Any context.
    }
```

---

## Repository
#### You must use ___JitPack___ repository (`https://jitpack.io/`)

## Gradle dependency
```gradle
dependencies {
    implementation 'com.github.TerraMiner:MenuUtil:v1.1.3-SNAPSHOT'
}
```
## Maven dependency
```xml
<dependency>
    <groupId>com.github.TerraMiner</groupId>
    <artifactId>MenuUtil</artifactId>
    <version>v1.1.3-SNAPSHOT</version>
</dependency>
```
