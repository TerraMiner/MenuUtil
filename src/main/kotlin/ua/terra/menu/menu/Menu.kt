package ua.terra.menu.menu

import ua.terra.menu.PageProperty
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.icon.IIcon
import org.bukkit.event.inventory.InventoryCloseEvent
import ua.terra.menu.page.IPage
import ua.terra.menu.updater.IconUpdater
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ua.terra.menu.on

class Menu(
    override val display: String,
    override val size: Int,
    override val viewer: Player,
    override val action: IMenu.() -> Unit = {}
) : IMenu {

    override var page = 0
    override val pages = mutableMapOf<Int, IPage>()
    override var slided = false

    override var pageCount = 0

    override var property: PageProperty? = null

    override fun setIcon(index: Int, stack: ItemStack, event: MenuClickEvent.() -> Unit, updater: IconUpdater?) {
        val targetPage = index / (size * 9)
        val pagedIndex = index % (size * 9)

        val page = pages.getOrPut(targetPage) { addPage { } }

        page.setIcon(pagedIndex, stack, event, updater)
    }

    override fun getIcon(index: Int): IIcon? {
        val targetPage = index / (size * 9)
        val pagedIndex = index % (size * 9)

        val page = pages[targetPage] ?: return null

        return page.getIcon(pagedIndex)
    }

    override fun addIcon(stack: ItemStack, event: MenuClickEvent.() -> Unit, updater: IconUpdater?) {
        val page = pages.values.find { it.emptySlots.isNotEmpty() } ?: addPage { }
        page.addIcon(stack, event, updater)
    }

    override fun addIcon(icon: IIcon) {
        val page = pages.values.find { it.emptySlots.isNotEmpty() } ?: addPage { }
        page.addIcon(icon)
    }

    init {
        action()
        pages.values.forEach {
            property?.setButtons(it)
            it.update()
        }
        on<InventoryCloseEvent> {
            if (inventory !in pages.map { it.value.inventory }) return@on
            if (slided) {
                slided = false
                return@on
            }

            pages.values.forEach { page ->
                page.updater.cancel()
            }
        }
    }

}