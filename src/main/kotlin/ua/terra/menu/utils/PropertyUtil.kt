package ua.terra.menu.utils

import ua.terra.menu.menu.IMenu
import ua.terra.menu.property.PageProperty

fun IMenu.property(action: PageProperty.() -> Unit) = PageProperty(this, action).also {
    property = it
}