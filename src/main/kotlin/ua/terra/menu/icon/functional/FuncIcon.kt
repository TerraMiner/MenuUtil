package ua.terra.menu.icon.functional

import org.bukkit.inventory.ItemStack
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.page.IPage
import ua.terra.menu.updater.IconUpdater

class FuncIcon(
    override var slot: Int,
    override var stack: ItemStack
) : IFuncIcon {
    override var clicks: MutableList<(IPage, MenuClickEvent) -> Unit> = mutableListOf({ _, e -> e.isCancelled = true })
    override var iconUpdaters: MutableList<IconUpdater> = mutableListOf()
    override var accessor: IconAccessor = IconAccessor(this)
}