package ua.terra.menu.menu

import ua.terra.menu.PageProperty
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.icon.IIcon
import org.bukkit.entity.Player
import ua.terra.menu.page.IPage
import ua.terra.menu.page.MenuPage
import ua.terra.menu.updater.IconUpdater
import org.bukkit.inventory.ItemStack

interface IMenu {
    val display: String
    val size: Int
    val action: IMenu.() -> Unit
    val pages: MutableMap<Int, IPage>
    val viewer: Player
    var page: Int
    var slided: Boolean
    var pageCount: Int

    var property: PageProperty?

    fun setIcon(index: Int, stack: ItemStack,
                event: MenuClickEvent.() -> Unit = { isCancelled = true },
                updater: IconUpdater? = null)

    fun getIcon(index: Int): IIcon?

    fun addIcon(stack: ItemStack, event: MenuClickEvent.() -> Unit = { isCancelled = true },
                updater: IconUpdater? = null)

    fun addIcon(icon: IIcon)


    fun addPage(action: IPage.() -> Unit) = MenuPage(pageCount, this).apply {
        pages[pageCount++] = this
        action()
        property?.fillIcons(this)
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