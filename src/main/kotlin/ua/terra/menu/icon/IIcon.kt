package ua.terra.menu.icon

import org.bukkit.inventory.ItemStack
import ua.terra.menu.utils.menuIcon

interface IIcon {
    var slot: Int
    var stack: ItemStack

    fun clone(): IIcon = menuIcon(slot, stack.clone())

    fun afterSetup() {

    }
}