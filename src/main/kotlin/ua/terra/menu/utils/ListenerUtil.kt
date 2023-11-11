package ua.terra.menu.utils

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

object MenuApiListener : Listener

inline fun <reified T : Event> on(
    eventPriority: EventPriority = EventPriority.NORMAL,
    noinline eventHandler: T.() -> Unit,
) {
    Plugin.server.pluginManager.registerEvent(
        T::class.java,
        MenuApiListener,
        eventPriority,
        { _, event -> if (T::class.java == event::class.java) eventHandler.invoke(event as T) },
        Plugin
    )
}