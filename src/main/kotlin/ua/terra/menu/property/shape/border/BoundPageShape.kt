package ua.terra.menu.property.shape.border

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import ua.terra.menu.icon.IIcon
import ua.terra.menu.property.PageProperty
import ua.terra.menu.utils.menuIcon

class BoundPageShape(override val property: PageProperty) : IBorderPageShape {


    override var icon: IIcon = menuIcon(-1, ItemStack(Material.LADDER).apply {
        itemMeta = itemMeta?.apply {
            setDisplayName(null)
        }
    })

    override fun setup() {
        for (y in property.top..property.bottom) {
            property.setIcon(property.getIndex(property.left,y), icon)
            property.setIcon(property.getIndex(property.right,y), icon)
        }
    }

}