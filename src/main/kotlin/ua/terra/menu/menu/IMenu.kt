package ua.terra.menu.menu

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.icon.IIcon
import ua.terra.menu.page.IPage
import ua.terra.menu.page.MenuPage
import ua.terra.menu.property.PageProperty
import ua.terra.menu.updater.IconUpdater

interface IMenu {
    val display: String
    val menuType: MenuType
    val action: IMenu.() -> Unit
    val pages: MutableMap<Int, IPage>
    val viewer: Player
    var page: Int
    var slided: Boolean
    var pageCount: Int

    var property: PageProperty?

    val sizeX get() = menuType.sizeX

    val sizeY get() = menuType.sizeY

    val inventorySize get() = sizeX * sizeY

    fun setIcon(x: Int, y: Int, icon: IIcon) {
        setIcon(menuType.getIndex(x, y), icon)
    }

    fun setIcon(
        x: Int, y: Int, stack: ItemStack, updater: IconUpdater? = null,
        event: (IPage, MenuClickEvent) -> Unit = { _, e -> e.isCancelled = true }
    ) {
        setIcon(menuType.getIndex(x, y), stack, updater, event)
    }

    fun setIcon(index: Int, icon: IIcon) {
        val targetPage = index / (inventorySize)
        val pagedIndex = index % (inventorySize)

        val page = pages.getOrPut(targetPage) { addPage { } }

        page.setIcon(pagedIndex, icon)
    }

    fun setIcon(index: Int, stack: ItemStack, updater: IconUpdater?, event: (IPage, MenuClickEvent) -> Unit) {
        val targetPage = index / (inventorySize)
        val pagedIndex = index % (inventorySize)

        val page = pages.getOrPut(targetPage) { addPage { } }

        page.setIcon(pagedIndex, stack, updater, event)
    }

    fun getIcon(index: Int): IIcon? {
        val targetPage = index / (inventorySize)
        val pagedIndex = index % (inventorySize)

        val page = pages[targetPage] ?: return null

        return page.getIcon(pagedIndex)
    }

    fun addIcon(stack: ItemStack, updater: IconUpdater?, event: (IPage, MenuClickEvent) -> Unit) {
        val page = pages.values.find { it.emptySlots.isNotEmpty() } ?: addPage { }
        page.addIcon(stack, updater, event)
    }

    fun addIcon(icon: IIcon) {
        val page = pages.values.find { it.emptySlots.isNotEmpty() } ?: addPage { }
        page.addIcon(icon)
    }


    fun addPage(action: IPage.() -> Unit) = MenuPage(pageCount, this).apply {
        pages[pageCount++] = this
        action()
        property?.setup(this)
        update()
    }

    fun flipForward() {
        page = (page + 1).coerceAtMost(pageCount - 1)
        slided = true
        openMenu()
    }

    fun flipBack() {
        page = (page - 1).coerceAtLeast(0)
        slided = true
        openMenu()
    }

    fun currentPage() = pages[page]

    fun openMenu() {
        openMenu(currentPage()?.index ?: 0)
    }

    fun openMenu(page: Int) {
        this.page = page
        viewer.openInventory(pages[page]?.inventory ?: return)
    }
}