package ua.terra.menu.property.shape.border

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import ua.terra.menu.icon.functional.IFuncIcon
import ua.terra.menu.property.PageProperty
import ua.terra.menu.utils.editMeta
import ua.terra.menu.utils.funcIcon

class TopPageShape(override val property: PageProperty) : IBorderPageShape {

    override var icon: IFuncIcon = funcIcon(-1, ItemStack(Material.LADDER).apply {
        editMeta {
            it.displayName = null
        }
    })

    override fun setup() {
        for (x in property.left..property.right) {
            property.setIcon(property.getIndex(x,property.top), icon)
        }
    }
}