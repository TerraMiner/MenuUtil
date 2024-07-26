package ua.terra.menu.page

import org.bukkit.inventory.Inventory
import ua.terra.menu.icon.IIcon
import ua.terra.menu.menu.IMenu
import ua.terra.menu.updater.IconUpdater
import ua.terra.menu.utils.MenuTask
import ua.terra.menu.utils.MiniSerializer
import ua.terra.menu.utils.createWindow
import ua.terra.menu.utils.every
import java.util.concurrent.ConcurrentHashMap

class MenuPage(
    override val index: Int,
    override val menu: IMenu,
) : IPage {

    override val icons = mutableMapOf<Int, IIcon>()

    override val window: Inventory = createWindow(this, menu.menuType,
        MiniSerializer.deserialize("<color:black>${MiniSerializer.serialize(menu.display)}".replace("%page%","${index + 1}"))
    )

    override val dynamicItems: MutableSet<IconUpdater> = ConcurrentHashMap.newKeySet()

    override var allowedClicksInMainInventory: Boolean = false

    override var updater: MenuTask = every(0,1, this::updaterAction)

    override val emptySlots: MutableList<Int> = (0..<menu.inventorySize).toMutableList()

    fun updaterAction() {
        safetyUpdate()
        if (dynamicItems.isEmpty()) return
        dynamicItems.forEach {
            if (!it.backTicking && menu.currentPage() !== this) return@forEach
            if (it.tick()) {
                it.action(this,it.icon)
                update(it.icon.slot)
            }
        }
    }
}