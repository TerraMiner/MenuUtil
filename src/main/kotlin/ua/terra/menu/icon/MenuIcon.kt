package ua.terra.menu.icon

import org.bukkit.inventory.ItemStack
import ua.terra.menu.Task
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.page.IPage
import ua.terra.menu.updater.IconUpdater

class MenuIcon(
    override var slot: Int,
    override val stack: ItemStack
): IIcon {
    override var click: (MenuClickEvent) -> Unit = { it.isCancelled = true }
    override var iconUpdater: IconUpdater? = null

    fun click(action: (MenuClickEvent) -> Unit) {
        click = action
    }

    fun updater(delay: Int, period: Int, backTicking: Boolean = false, action: IIcon.() -> Unit) {
        iconUpdater = IconUpdater(this, delay, period, backTicking , action)
    }

}