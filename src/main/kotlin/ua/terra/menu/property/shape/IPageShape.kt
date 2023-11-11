package ua.terra.menu.property.shape

import ua.terra.menu.property.PageProperty

interface IPageShape {
    val property: PageProperty

    fun setup()
}