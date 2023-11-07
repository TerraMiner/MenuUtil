package ua.terra.menu.icon

import org.bukkit.inventory.ItemStack
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.page.IPage
import ua.terra.menu.updater.IconUpdater

class MenuIcon(
    override var slot: Int,
    override var stack: ItemStack
) : IIcon {
    override var clicks: MutableList<(IPage, MenuClickEvent) -> Unit> = mutableListOf({ _, e -> e.isCancelled = true })
    override var iconUpdaters: MutableList<IconUpdater> = mutableListOf()
    override var accessor: IconAccessor = IconAccessor(this)
}