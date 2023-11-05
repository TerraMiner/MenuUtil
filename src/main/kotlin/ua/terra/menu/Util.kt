package ua.terra.menu

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import ua.terra.menu.icon.MenuIcon
import ua.terra.menu.menu.IMenu
import ua.terra.menu.menu.Menu
import ua.terra.menu.property.PageProperty

typealias Task = BukkitTask

val Plugin by lazy { JavaPlugin.getProvidingPlugin(Menu::class.java) }

fun menuIcon(slot: Int, item: ItemStack, icon: MenuIcon.() -> Unit = {}) = MenuIcon(slot,item).apply(icon)

fun IMenu.property(action: PageProperty.() -> Unit) = PageProperty(this,action).also {
    property = it
}

fun every(delay: Int, period: Int, action: () -> Unit) =
    Bukkit.getScheduler().runTaskTimer(Plugin, Runnable(action), delay.toLong(), period.toLong())

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

fun String?.toComponent() = this?.let { component ->
    TextComponent.fromLegacyText(component)
        .asSequence()
        .filterIsInstance<TextComponent>()
        .map {
            Component.text(it.text)
                .color(TextColor.color(it.color.color.rgb))
                .decoration(TextDecoration.BOLD, it.isBold)
                .decoration(TextDecoration.ITALIC, it.isItalic)
                .decoration(TextDecoration.STRIKETHROUGH, it.isStrikethrough)
                .decoration(TextDecoration.UNDERLINED, it.isUnderlined)
                .decoration(TextDecoration.OBFUSCATED, it.isObfuscated)
        }
        .toList()
        .toTypedArray()
        .let(Component::textOfChildren)
} ?: Component.empty()