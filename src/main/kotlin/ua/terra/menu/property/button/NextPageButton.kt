package ua.terra.menu.property.button

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import ua.terra.menu.icon.MenuIcon
import ua.terra.menu.menuIcon
import ua.terra.menu.page.IPage
import ua.terra.menu.property.PageProperty
import ua.terra.menu.toComponent

class NextPageButton(override val property: PageProperty) : PageButton {

    override var icon: MenuIcon = menuIcon(property.menu.size * 9 - 2, ItemStack(Material.ARROW).apply {
        editMeta {
            it.displayName("§7Next page".toComponent())
        }
    }) {
        click { _, e ->
            property.menu.flipForward()
            e.isCancelled = true
        }
    }

    override fun setup(page: IPage) {
        if (page.hasNextPage()) {
            page.setIcon(icon)
        }
    }
}