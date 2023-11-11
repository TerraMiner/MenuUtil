# MenuUtil

A utility for creating menus with refreshable and clickable icons, as well as for automatically dividing content between pages.  
The utility is written in **Kotlin**, for **Minecraft** version **1.19.4** using **PaperAPI**.
---
## Usage Example

### Creating menu
```kotlin
//Creating Menu with parameters title, rows count and the player who will be the spectator.
    Menu("Menu Title", 6, player) {
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
```kotlin
    property {
        addBounds()
        addBottom()
        addTop()
        addButtons()
    }
```

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

![Alt text](https://i.imgur.com/USATCfI.png "a title")

### Creating icon
```kotlin
    //Creating icon, which you can put into menu.
    menuIcon(46, item(Material.ARROW) {
                editMeta { it.displayName("§fExample".toComponent()) }
    }) {
        //Any action in Icon context.
    }
```

### Creating functional item
```kotlin
    //Creating icon, which you can put into menu.
    funcIcon(46, item(Material.ARROW) {
                editMeta { it.displayName("§fExample".toComponent()) }
    }) {
        //Adding click action.
        click {
            //Any action with InventoryClickEvent context.
        }

        //Adding updater.
        updater(0, 2) {
            //Any action which be execute every 2 ticks.
        }
    }
    // Or you can turn ItemStack to FuncIcon.
    item(Material.STONE) {
        
    }.toFuncIcon {
        //Adding click action.
        click {
            //Any action with InventoryClickEvent context.
        }

        //Adding updater.
        updater(0, 2) {
            //Any action which be execute every 2 ticks.
        }
    }
```

---

## Repository
#### You must use ___JitPack___ repository (`https://jitpack.io/`)

## Gradle dependency
```gradle
dependencies {
    implementation 'com.github.TerraMiner:MenuUtil:v1.0.2-SNAPSHOT'
}
```
## Maven dependency
```xml
<dependency>
    <groupId>com.github.TerraMiner</groupId>
    <artifactId>MenuUtil</artifactId>
    <version>v1.0.2-SNAPSHOT</version>
</dependency>
```
