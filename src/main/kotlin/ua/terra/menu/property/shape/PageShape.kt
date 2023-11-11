package ua.terra.menu.property.shape

import ua.terra.menu.property.PageProperty

interface PageShape {
    val property: PageProperty

    fun setup()
}