package ua.terra.menu.menu

import org.bukkit.entity.Player
import ua.terra.menu.page.IPage
import ua.terra.menu.property.PageProperty
import ua.terra.menu.utils.MenuApiListener

class Menu(
    override val display: String,
    override val menuType: MenuType,
    override val viewer: Player,
    override val action: IMenu.() -> Unit = {}
) : IMenu {

    constructor(display: String, size: Int, viewer: Player, action: IMenu.() -> Unit) : this(
        display,
        MenuType.fromAlias(size),
        viewer,
        action
    )

    override var page = 0
    override val pages = mutableMapOf<Int, IPage>()
    override var slided = false

    override var pageCount = 0

    override var property: PageProperty? = null

    init {
        action()

        if (pages.isEmpty()) addPage {  }

        pages.values.forEach {
            it.update()
        }

        MenuApiListener.registerLazyEvents
    }


}