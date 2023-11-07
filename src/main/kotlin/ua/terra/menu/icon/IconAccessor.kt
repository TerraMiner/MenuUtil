package ua.terra.menu.icon

import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.page.IPage
import java.util.function.BiPredicate

class IconAccessor(
    val icon: IIcon
) {
    private var visibleAccess: BiPredicate<IPage, IIcon> = BiPredicate { _,_ -> true }

    private var clickAccess: BiPredicate<IPage, MenuClickEvent> = BiPredicate { _,_ -> true }

    fun visionValid(page: IPage, icon: IIcon) = visibleAccess.test(page,icon)

    fun clickValid(page: IPage, clickEvent: MenuClickEvent) = clickAccess.test(page, clickEvent)

    fun visibleAccess(validate: (IPage, IIcon) -> Boolean) {
        visibleAccess = BiPredicate(validate)
    }

    fun clickAccess(validate: (IPage, MenuClickEvent) -> Boolean) {
        clickAccess = BiPredicate(validate)
    }
}