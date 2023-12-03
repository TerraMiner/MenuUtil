package ua.terra.menu.icon.functional

import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.icon.IIcon
import ua.terra.menu.page.IPage
import ua.terra.menu.updater.IconUpdater
import ua.terra.menu.utils.funcIcon

interface IFuncIcon : IIcon {
    var clicks: MutableList<(IPage, MenuClickEvent) -> Unit>
    var accessor: IconAccessor
    val iconUpdaters: MutableList<IconUpdater>

    override fun clone() = funcIcon(slot, stack.clone()) builder@{
        clicks.addAll(this@IFuncIcon.clicks)
        iconUpdaters.addAll(this@IFuncIcon.iconUpdaters.map {
            IconUpdater(this, it.delay, it.period, it.backTicking, it.action)
        })
        accessor = this@IFuncIcon.accessor
    }

    fun addUpdater(icon: IIcon, delay: Int, period: Int, backTicking: Boolean = false, action: (IPage, IIcon) -> Unit) {
        iconUpdaters.add(IconUpdater(icon, delay, period, backTicking , action))
    }

    fun addUpdater(updater: IconUpdater?) {
        iconUpdaters.add(updater ?: return)
    }

    fun click(action: (IPage, MenuClickEvent) -> Unit) {
        clicks.add(action)
    }

    fun visibleAccess(validate: (IPage, IIcon) -> Boolean) {
        accessor.visibleAccess(validate)
    }

    fun clickAccess(validate: (IPage, MenuClickEvent) -> Boolean) {
        accessor.clickAccess(validate)
    }
}