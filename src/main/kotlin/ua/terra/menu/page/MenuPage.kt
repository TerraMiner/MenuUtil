package ua.terra.menu.page

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import ua.terra.menu.Task
import ua.terra.menu.every
import ua.terra.menu.icon.IIcon
import ua.terra.menu.menu.IMenu
import ua.terra.menu.on
import ua.terra.menu.toComponent
import ua.terra.menu.updater.IconUpdater

class MenuPage(
    override val index: Int,
    override val menu: IMenu,
) : IPage {

    override val icons = mutableMapOf<Int, IIcon>()
    override val inventory = Bukkit.createInventory(null, menu.inventorySize, "§0${menu.display} $index".toComponent())

    override val dynamicItems: MutableList<IconUpdater> = mutableListOf()

    override val updater: Task = every(0,1) {
        if (dynamicItems.isEmpty()) return@every
        dynamicItems.forEach {
            if (!it.backTicking && menu.currentPage() !== this) return@forEach
            println("Shaded tick")
            if (it.tick()) {
                it.action(it.icon)
                update(it.icon.slot)
            }
        }
    }

    override val emptySlots: MutableList<Int> = (0..<menu.inventorySize).toMutableList()

    override fun hasNextPage() = index < menu.pageCount - 1

    override fun hasPrevPage() = index > 0


    init {
        on<InventoryClickEvent> { clickEvent() }
    }
}