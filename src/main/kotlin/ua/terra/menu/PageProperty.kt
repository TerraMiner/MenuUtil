package ua.terra.menu

import ua.terra.menu.icon.IIcon
import ua.terra.menu.page.IPage

class PageProperty(
    val prevButton: IIcon,
    val nextButton: IIcon,
    vararg val icons: IIcon
) {

    /**
     * Should be called first so as not to overlap the buttons
     */
    fun fillIcons(page: IPage) {
        icons.forEach { page.setIcon(it) }
    }

    /**
     * Places buttons in specified locations
     */
    fun setButtons(page: IPage) {
        if (page.hasPrevPage()) page.setIcon(prevButton)
        if (page.hasNextPage()) page.setIcon(nextButton)
    }
}