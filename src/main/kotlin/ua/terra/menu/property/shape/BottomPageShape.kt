package ua.terra.menu.property.shape

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import ua.terra.menu.icon.MenuIcon
import ua.terra.menu.menuIcon
import ua.terra.menu.property.PageProperty

class BottomPageShape(override val property: PageProperty) : PageShape {

    override var icon: MenuIcon = menuIcon(-1, ItemStack(Material.IRON_BARS).apply {
        editMeta {
            it.displayName(Component.empty())
        }
    })

    override fun setup() {
        for(i in property.menu.inventorySize - 9..<property.menu.inventorySize) {
            property.icons[i] = icon
        }
    }
}