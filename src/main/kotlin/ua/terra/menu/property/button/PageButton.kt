package ua.terra.menu.property.button

import ua.terra.menu.icon.MenuIcon
import ua.terra.menu.page.IPage
import ua.terra.menu.property.PageProperty

interface PageButton {
    val property: PageProperty

    var icon: MenuIcon

    fun setup(page: IPage)
}