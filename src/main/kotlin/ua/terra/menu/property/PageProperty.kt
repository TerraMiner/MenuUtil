package ua.terra.menu.property

import ua.terra.menu.icon.IIcon
import ua.terra.menu.icon.functional.IFuncIcon
import ua.terra.menu.menu.IMenu
import ua.terra.menu.page.IPage
import ua.terra.menu.property.shape.border.BottomPageShape
import ua.terra.menu.property.shape.border.BoundPageShape
import ua.terra.menu.property.shape.border.TopPageShape
import ua.terra.menu.property.shape.button.NextPageButton
import ua.terra.menu.property.shape.button.PreviousPageButton
import ua.terra.menu.property.shape.pattern.PatternPageShape

class  PageProperty(
    val menu: IMenu,
    action: PageProperty.() -> Unit
) {
    private val properties = mutableSetOf<PageProperties>()

    private val icons = mutableMapOf<Int, IIcon>()

    val nextButton = NextPageButton(this)

    val prevButton = PreviousPageButton(this)

    val topShape = TopPageShape(this)

    val bottomShape = BottomPageShape(this)

    val boundShape = BoundPageShape(this)

    val patternShape = PatternPageShape(this)

    var allowedClicksInMainInventory = false

    fun setIcon(icon: IIcon) {
        setIcon(icon.slot,icon)
    }

    fun setIcon(index: Int, icon: IIcon) {
        icons[index] = icon
    }

    fun allowClicksInMainInventory() {
        allowedClicksInMainInventory = true
    }


    /**
     * Fills right and left shapes menu with bound icon
     *
     * This may overlap with other shapes so use in the correct order
     */
    fun addBounds(): PageProperty {
        properties.add(PageProperties.BOUND)
        return this
    }

    fun addBounds(menuIcon: IFuncIcon): PageProperty {
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

    fun addBottom(menuIcon: IFuncIcon): PageProperty {
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

    fun addTop(menuIcon: IFuncIcon): PageProperty {
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

    fun addButtons(prevButtonIcon: IFuncIcon, nextButtonIcon: IFuncIcon): PageProperty {
        prevButton.icon = prevButtonIcon
        nextButton.icon = nextButtonIcon
        return addButtons()
    }

    /**
     * Use to create complex menus
     */
    fun pattern(action: (PatternPageShape) -> Unit): PageProperty {
        action(patternShape)
        properties.add(PageProperties.PATTERN)
        return this
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

                PageProperties.NAVIGATION_BUTTONS -> {
                    prevButton.setup()
                    nextButton.setup()
                }

                PageProperties.PATTERN -> {
                    patternShape.setup()
                }
            }
        }

        page.allowedClicksInMainInventory = allowedClicksInMainInventory

        icons.forEach { (slot, icon) ->
            if (slot !in 0..<page.menu.inventorySize) return@forEach
            page.setIcon(slot, icon)
        }
    }

    val top get() = menu.menuType.top
    val bottom get() = menu.menuType.bottom
    val left get() = menu.menuType.left
    val right get() = menu.menuType.right

    fun getIndex(x: Int, y: Int) = menu.menuType.getIndex(x,y)
    fun getCoords(index: Int) = menu.menuType.getCoords(index)

    init {
        action(this)
    }
}