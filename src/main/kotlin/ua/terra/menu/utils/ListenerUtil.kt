package ua.terra.menu.utils

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.page.IPage

object MenuApiListener : Listener {
    val registerLazyEvents by lazy {
        println("[MenuUtil]: Rigistered InventoryCloseEvent, InventoryClickEvent, MenuClickEvent")
        on<InventoryCloseEvent> {
            val holder = inventory.holder.safeCast<IPage>() ?: return@on
            val menu = holder.menu
            if (holder.index !in menu.pages.map { it.value.index }) return@on

            if (menu.slided) {
                menu.slided = false
                return@on
            }

            menu.pages.values.forEach { page ->
                page.updater.cancel()
            }
        }

        on<InventoryClickEvent> {

            whoClicked.openInventory.also {
                if ((clickedInventory !== it.bottomInventory)) return@also
                it.topInventory.holder.safeCast<IPage>()?.apply {
                    if (!allowedClicksInMainInventory) isCancelled = true
                }
            }

            inventory.holder.safeCast<IPage>()?.apply { clickEvent() }
        }

        on<MenuClickEvent> {
            if (!icon.accessor.clickValid(page, this)) {
                isCancelled = true
                return@on
            }

            icon.clicks.forEach {
                it(page, this)
            }
        }
    }
}

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

