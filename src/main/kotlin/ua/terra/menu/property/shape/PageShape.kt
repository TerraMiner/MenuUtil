package ua.terra.menu.property.shape

import ua.terra.menu.icon.MenuIcon
import ua.terra.menu.property.PageProperty

interface PageShape {
    val property: PageProperty

    var icon: MenuIcon

    fun setup()
}