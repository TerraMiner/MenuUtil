package ua.terra.menu.property.shape.border

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import ua.terra.menu.icon.functional.IFuncIcon
import ua.terra.menu.property.PageProperty
import ua.terra.menu.utils.funcIcon

class BottomPageShape(override val property: PageProperty) : BorderPageShape {

    override var icon: IFuncIcon = funcIcon(-1, ItemStack(Material.IRON_BARS).apply {
        editMeta {
            it.displayName(Component.empty())
        }
    })

    override fun setup() {
        for(x in property.left..property.right) {
            property.setIcon(property.getIndex(x,property.bottom), icon)
        }
    }
}