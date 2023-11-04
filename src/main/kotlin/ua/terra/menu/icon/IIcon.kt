package ua.terra.menu.icon

import org.bukkit.inventory.ItemStack
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.menuIcon
import ua.terra.menu.page.IPage
import ua.terra.menu.updater.IconUpdater

interface IIcon {
    var slot: Int
    val stack: ItemStack
    val click: (MenuClickEvent) -> Unit
    val iconUpdater: IconUpdater?

    fun clone() = menuIcon(slot, stack.clone()) builder@{
        this@builder.click(this@IIcon.click)
        this@builder.iconUpdater = this@IIcon.iconUpdater?.let {
            IconUpdater(this@builder, it.delay, it.period, it.backTicking, it.action)
        }
    }
}