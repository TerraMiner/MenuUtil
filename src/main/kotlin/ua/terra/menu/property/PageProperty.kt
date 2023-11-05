package ua.terra.menu.property

import ua.terra.menu.icon.IIcon
import ua.terra.menu.icon.MenuIcon
import ua.terra.menu.menu.IMenu
import ua.terra.menu.page.IPage
import ua.terra.menu.property.button.NextPageButton
import ua.terra.menu.property.button.PreviousPageButton
import ua.terra.menu.property.shape.BottomPageShape
import ua.terra.menu.property.shape.BoundPageShape
import ua.terra.menu.property.shape.TopPageShape

class PageProperty(
    val menu: IMenu,
    action: PageProperty.() -> Unit
) {
    private val properties = mutableListOf<PageProperties>()

    val icons = mutableMapOf<Int, IIcon>()

    val nextButton = NextPageButton(this)

    val prevButton = PreviousPageButton(this)

    val topShape = TopPageShape(this)

    val bottomShape = BottomPageShape(this)

    val boundShape = BoundPageShape(this)


    /**
     * Fills right and left shapes menu with bound icon
     *
     * This may overlap with other shapes so use in the correct order
     */
    fun addBounds(): PageProperty {
        properties.add(PageProperties.BOUND)
        return this
    }

    fun addBounds(menuIcon: MenuIcon): PageProperty {
        boundShape.icon = menuIcon
        return addBounds()
    }

    /**
     * Fills bottom shape menu with bottom icon
     *
     * This may overlap with other shapes so use in the correct order
     */
    fun addBottom(): PageProperty {
        properties.add(PageProperties.BOTTOM)
        return this
    }

    fun addBottom(menuIcon: MenuIcon): PageProperty {
        bottomShape.icon = menuIcon
        return addBottom()
    }

    /**
     * Fills upper shape menu with top icon
     *
     * This may overlap with other shapes so use in the correct order
     */
    fun addTop(): PageProperty {
        properties.add(PageProperties.TOP)
        return this
    }

    fun addTop(menuIcon: MenuIcon): PageProperty {
        topShape.icon = menuIcon
        return addTop()
    }

    /**
     * Places buttons in specified locations
     *
     * This may overlap with other shapes so use in the correct order
     */
    fun addButtons(): PageProperty {
        properties.add(PageProperties.NAVIGATION_BUTTONS)
        return this
    }

    fun addButtons(prevButtonIcon: MenuIcon, nextButtonIcon: MenuIcon): PageProperty {
        prevButton.icon = prevButtonIcon
        nextButton.icon = nextButtonIcon
        return addButtons()
    }

    /**
     * Called automatically after the menu creation context has been processed
     */
    fun setup(page: IPage) {
        properties.forEach {
            when (it) {
                PageProperties.TOP -> {
                    topShape.setup()
                }

                PageProperties.BOTTOM -> {
                    bottomShape.setup()
                }

                PageProperties.BOUND -> {
                    boundShape.setup()
                }

                else -> {}
            }
        }

        icons.forEach { (slot, icon) ->
            page.setIcon(slot, icon)
        }
    }

    fun setupButtons(page: IPage) {
        if (properties.contains(PageProperties.NAVIGATION_BUTTONS)) {
            prevButton.setup(page)
            nextButton.setup(page)
        }
    }

    init {
        action(this)
    }
}