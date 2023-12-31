package ua.terra.menu.utils

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder
import ua.terra.menu.menu.MenuType

fun createWindow(owner: InventoryHolder, type: MenuType, display: String) = when (type.type) {
    InventoryType.CHEST -> Bukkit.createInventory(owner, type.sizeX * type.sizeY, display)
    else -> Bukkit.createInventory(owner, type.type, display)
}