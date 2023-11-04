package ua.terra.menu.updater

import ua.terra.menu.icon.IIcon
import ua.terra.menu.every


class IconUpdater(val icon: IIcon, var delay: Int, val period: Int, val backTicking: Boolean, val action: IIcon.() -> Unit) {
    private var ticker = delay
        get() {
            if (field < 0) field = period
            return field
        }

    fun tick() = --ticker == 0
}

