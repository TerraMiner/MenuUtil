package ua.terra.menu.property.shape.button

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import ua.terra.menu.icon.functional.IFuncIcon
import ua.terra.menu.property.PageProperty
import ua.terra.menu.utils.funcIcon
import ua.terra.menu.utils.toComponent

class PreviousPageButton(override val property: PageProperty) : PageButtonShape {

    override var icon: IFuncIcon = funcIcon(property.run { getIndex(left + 1, bottom) }, ItemStack(Material.ARROW).apply {
        editMeta {
            it.displayName("§7Previous page".toComponent())
        }
    }) {
        click { _, e ->
            property.menu.flipBack()
            e.isCancelled = true
        }
    }

    override fun setup() {
        property.setIcon(icon)
    }
}