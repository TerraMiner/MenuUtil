package ua.terra.menu.icon

import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.page.IPage
import java.util.function.BiPredicate

class IconAccessor(
    val icon: IIcon
) {
    private var visibleAccess: BiPredicate<IPage, IIcon> = BiPredicate { _,_ -> true }

    private var clickAccess: BiPredicate<IPage, MenuClickEvent> = BiPredicate { _,_ -> true }

    var lastVisionCheck = false
    var lastClickCheck = false

    var isNeedUpdate = true
        get() = field.apply {
            isNeedUpdate = false
        }

    fun visionValid(page: IPage, icon: IIcon) = visibleAccess.test(page,icon).also {
        if (lastVisionCheck != it) {
            lastVisionCheck = it
            isNeedUpdate = true
        }
    }

    fun clickValid(page: IPage, clickEvent: MenuClickEvent) = clickAccess.test(page, clickEvent).also {
        if (lastClickCheck != it) {
            lastClickCheck = it
            isNeedUpdate = true
        }
    }

    fun visibleAccess(validate: (IPage, IIcon) -> Boolean) {
        visibleAccess = BiPredicate(validate)
    }

    fun clickAccess(validate: (IPage, MenuClickEvent) -> Boolean) {
        clickAccess = BiPredicate(validate)
    }
}