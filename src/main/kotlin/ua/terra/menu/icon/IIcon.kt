package ua.terra.menu.icon

import org.bukkit.inventory.ItemStack
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.menuIcon
import ua.terra.menu.page.IPage
import ua.terra.menu.updater.IconUpdater

interface IIcon {
    var slot: Int
    var stack: ItemStack
    var clicks: MutableList<(IPage, MenuClickEvent) -> Unit>
    var accessor: IconAccessor
    val iconUpdaters: MutableList<IconUpdater>

    fun clone() = menuIcon(slot, stack.clone()) builder@{
        clicks.addAll(this@IIcon.clicks)
        iconUpdaters.addAll(this@IIcon.iconUpdaters.map {
            IconUpdater(this, it.delay, it.period, it.backTicking, it.action)
        })
    }

    fun addUpdater(icon: IIcon, delay: Int, period: Int, backTicking: Boolean = false, action: (IPage,IIcon) -> Unit) {
        iconUpdaters.add(IconUpdater(icon, delay, period, backTicking , action))
    }

    fun addUpdater(updater: IconUpdater?) {
        iconUpdaters.add(updater ?: return)
    }

    fun click(action: (IPage, MenuClickEvent) -> Unit) {
        clicks.add(action)
    }

    fun visibleAccess(validate: (IPage, IIcon) -> Boolean) {
        accessor.visibleAccess(validate)
    }

    fun clickAccess(validate: (IPage, MenuClickEvent) -> Boolean) {
        accessor.clickAccess(validate)
    }
}