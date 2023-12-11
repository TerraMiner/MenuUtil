package ua.terra.menu.page

import ua.terra.menu.icon.IIcon
import ua.terra.menu.menu.IMenu
import ua.terra.menu.updater.IconUpdater
import ua.terra.menu.utils.Task
import ua.terra.menu.utils.createWindow
import ua.terra.menu.utils.every
import java.util.concurrent.ConcurrentHashMap

class MenuPage(
    override val index: Int,
    override val menu: IMenu,
) : IPage {

    override val icons = mutableMapOf<Int, IIcon>()

    override val window = createWindow(this, menu.menuType, "§0${menu.display.replace("%page%","$index")}")

    override val dynamicItems: MutableSet<IconUpdater> = ConcurrentHashMap.newKeySet()

    override var allowedClicksInMainInventory: Boolean = false

    override val updater: Task = every(0,1) {
        safetyUpdate()
        if (dynamicItems.isEmpty()) return@every
        dynamicItems.forEach {
            if (!it.backTicking && menu.currentPage() !== this) return@forEach
            if (it.tick()) {
                it.action(this,it.icon)
                update(it.icon.slot)
            }
        }
    }

    override val emptySlots: MutableList<Int> = (0..<menu.inventorySize).toMutableList()
}