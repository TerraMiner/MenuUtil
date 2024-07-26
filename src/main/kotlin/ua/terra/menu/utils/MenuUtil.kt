package ua.terra.menu.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import ua.terra.menu.menu.MenuType

fun createWindow(owner: InventoryHolder, type: MenuType, title: Component): Inventory {
    val display = LegacyComponentSerializer.legacySection().serialize(title)
    return when (type.type) {
        InventoryType.CHEST -> Bukkit.createInventory(owner, type.sizeX * type.sizeY, display)
        else -> Bukkit.createInventory(owner, type.type, display)
    }
}