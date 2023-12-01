package ua.terra.menu.utils

import org.bukkit.inventory.ItemStack
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.icon.IIcon
import ua.terra.menu.icon.MenuIcon
import ua.terra.menu.icon.functional.FuncIcon
import ua.terra.menu.icon.functional.IFuncIcon
import ua.terra.menu.icon.functional.MenuIconSlider
import ua.terra.menu.page.IPage
import ua.terra.menu.updater.IconUpdater


fun confirmIcon(
    slot: Int,
    original: ItemStack,
    confirm: ItemStack,
    confirmAction: (IPage, MenuClickEvent) -> Unit
): IIcon {

    return menuIconSlider(slot, original) {
        times(1, false)
        addSlide(confirm.toFuncIcon {
            click { page, event ->
                when {
                    event.isRightClick -> reset(page)
                    event.isLeftClick -> {
                        confirmAction(page, event)
                        reset(page)
                    }
                }
            }
        })
    }
}

fun ItemStack.toFuncIcon(slot: Int, action: IFuncIcon.() -> Unit = { click { _, e -> e.isCancelled = true } }) =
    funcIcon(slot, this, action)

fun ItemStack.toFuncIcon(action: IFuncIcon.() -> Unit = { click { _, e -> e.isCancelled = true } }) =
    funcIcon(-1, this, action)

fun funcIcon(slot: Int, item: ItemStack, action: IFuncIcon.() -> Unit = {}) = FuncIcon(slot, item).apply(action)

fun IFuncIcon.updater(delay: Int, period: Int, action: (IPage, IIcon) -> Unit) {
    addUpdater(IconUpdater(this,delay,period,false, action))
}

fun ItemStack.toMenuIcon(slot: Int, action: IIcon.() -> Unit = { }) =
    menuIcon(slot, this, action)

fun ItemStack.toMenuIcon(action: IIcon.() -> Unit = { }) =
    menuIcon(-1, this, action)

fun menuIcon(slot: Int, item: ItemStack, action: MenuIcon.() -> Unit = {}) = MenuIcon(slot, item).apply(action)


fun menuIconSlider(
    slot: Int,
    item: ItemStack,
    action: MenuIconSlider.() -> Unit = {}
) = MenuIconSlider(slot, item, action)

fun menuIconSlider(
    delay: Int,
    period: Int,
    backTicking: Boolean,
    slot: Int,
    item: ItemStack,
    action: MenuIconSlider.() -> Unit = {}
) = MenuIconSlider(slot, item, delay, period, backTicking, action)
