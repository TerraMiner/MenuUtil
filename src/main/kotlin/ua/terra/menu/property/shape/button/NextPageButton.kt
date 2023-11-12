package ua.terra.menu.property.shape.button

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import ua.terra.menu.icon.functional.IFuncIcon
import ua.terra.menu.property.PageProperty
import ua.terra.menu.utils.funcIcon

class NextPageButton(override val property: PageProperty) : IPageButtonShape {

    override var icon: IFuncIcon = funcIcon(property.run { getIndex(right - 1, bottom) }, ItemStack(Material.ARROW).apply {
        itemMeta = itemMeta?.apply {
            setDisplayName("§7Next page")
        }
    }) {
        click { _, e ->
            property.menu.flipForward()
            e.isCancelled = true
        }
    }

    override fun setup() {
        property.setIcon(icon)
    }
}