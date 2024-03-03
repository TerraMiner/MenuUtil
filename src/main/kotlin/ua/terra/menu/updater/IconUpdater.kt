package ua.terra.menu.updater

import ua.terra.menu.icon.IIcon
import ua.terra.menu.page.IPage


class IconUpdater(val icon: IIcon, var delay: Int, val period: Int, val backTicking: Boolean, val action: (IPage,IIcon) -> Unit) {

    private val delayTime = delay * 50L
    private val periodTime = period * 50L
    private var lastTickedTime = System.currentTimeMillis() + delayTime

    fun tick(): Boolean {
        if (System.currentTimeMillis() - lastTickedTime < 0) return false
        lastTickedTime = System.currentTimeMillis() + periodTime
        return true
    }
}

