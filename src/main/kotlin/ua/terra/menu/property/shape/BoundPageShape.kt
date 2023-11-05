package ua.terra.menu.property.shape

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import ua.terra.menu.icon.MenuIcon
import ua.terra.menu.menuIcon
import ua.terra.menu.property.PageProperty

class BoundPageShape(override val property: PageProperty) : PageShape {


    override var icon: MenuIcon = menuIcon(-1, ItemStack(Material.LADDER).apply {
        editMeta {
            it.displayName(Component.empty())
        }
    })

    override fun setup() {
        for (i in 0..(property.menu.inventorySize) step 9) {
            if (i == 0 || i == 9 || i == 18 || i == 27 || i == 36 || i == 45) property.icons[i] = icon
        }
        for (i in 8..(property.menu.inventorySize) step 9) {
            if (i == 8 || i == 17 || i == 26 || i == 35 || i == 44 || i == 53) property.icons[i] = icon
        }
    }

}