package ua.terra.menu.menu

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.icon.IIcon
import ua.terra.menu.on
import ua.terra.menu.page.IPage
import ua.terra.menu.property.PageProperty
import ua.terra.menu.updater.IconUpdater

class Menu(
    override val display: String,
    override val menuType: MenuType,
    override val viewer: Player,
    override val action: IMenu.() -> Unit = {}
) : IMenu {

    constructor(display: String, size: Int, viewer: Player, action: IMenu.() -> Unit) : this(
        display,
        MenuType.fromAlias(size),
        viewer,
        action
    )

    override var page = 0
    override val pages = mutableMapOf<Int, IPage>()
    override var slided = false

    override var pageCount = 0

    override var property: PageProperty? = null

    override fun setIcon(index: Int, icon: IIcon) {
        val targetPage = index / (inventorySize)
        val pagedIndex = index % (inventorySize)

        val page = pages.getOrPut(targetPage) { addPage { } }

        page.setIcon(pagedIndex, icon)
    }

    override fun setIcon(index: Int, stack: ItemStack, updater: IconUpdater?, event: (IPage, MenuClickEvent) -> Unit) {
        val targetPage = index / (inventorySize)
        val pagedIndex = index % (inventorySize)

        val page = pages.getOrPut(targetPage) { addPage { } }

        page.setIcon(pagedIndex, stack, updater, event)
    }

    override fun getIcon(index: Int): IIcon? {
        val targetPage = index / (inventorySize)
        val pagedIndex = index % (inventorySize)

        val page = pages[targetPage] ?: return null

        return page.getIcon(pagedIndex)
    }

    override fun addIcon(stack: ItemStack, updater: IconUpdater?, event: (IPage, MenuClickEvent) -> Unit) {
        val page = pages.values.find { it.emptySlots.isNotEmpty() } ?: addPage { }
        page.addIcon(stack, updater, event)
    }

    override fun addIcon(icon: IIcon) {
        val page = pages.values.find { it.emptySlots.isNotEmpty() } ?: addPage { }
        page.addIcon(icon)
    }

    init {
        action()

        pages.values.forEach {
            property?.setupButtons(it)
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