package ua.terra.menu.menu

import org.bukkit.event.inventory.InventoryType

enum class MenuType(
    val type: InventoryType,
    val sizeX: Int,
    val sizeY: Int
) {
    MENU_9X1(InventoryType.CHEST, 9,1),
    MENU_9X2(InventoryType.CHEST, 9,2),
    MENU_9X3(InventoryType.CHEST, 9,3),
    MENU_9X4(InventoryType.CHEST, 9,4),
    MENU_9X5(InventoryType.CHEST, 9,5),
    MENU_9X6(InventoryType.CHEST, 9,6),
    DISPENSER(InventoryType.DISPENSER, 3, 3),
    DROPPER(InventoryType.DROPPER, 3, 3),
    HOPPER(InventoryType.HOPPER,1,5);

    fun getIndex(x: Int, y: Int): Int {
        return x.coerceIn(0,sizeX-1) + y.coerceIn(0,sizeY-1) * sizeX
    }

    fun getCoords(index: Int): Pair<Int, Int> {
        return (index % sizeX).coerceIn(0, sizeX - 1) to (index / sizeX).coerceIn(0, sizeY - 1)
    }

    companion object {
        fun fromAlias(sizeY: Int): MenuType {
            return when (sizeY) {
                1 -> MENU_9X1
                2 -> MENU_9X2
                3 -> MENU_9X3
                4 -> MENU_9X4
                5 -> MENU_9X5
                6 -> MENU_9X6
                else -> MENU_9X6
            }
        }
    }
}