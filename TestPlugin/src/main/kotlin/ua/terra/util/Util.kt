package ua.terra.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import ua.terra.menu.toComponent

inline fun item(type: Material, amount: Int = 1, block: ItemStack.() -> Unit = {}) =
    ItemStack(type, amount).apply(block)

var ItemStack.customName
    get() = this.displayName().toText().replace("[", "").replace("]", "")//.getText()
    set(value) {
        displayName = value
    }

var ItemStack.displayName: String?
    set(value) = meta { displayName(value.toComponent()) }.unit()
    get() = meta?.displayName

inline fun ItemStack.meta(crossinline block: ItemMeta.() -> Unit = {}) = metaAs<ItemMeta>(block)

inline fun <reified T : ItemMeta> ItemStack.metaAs(crossinline block: T.() -> Unit = {}) =
    apply { meta = metaAs<T>()?.apply(block) }

inline fun <reified T : ItemMeta> ItemStack.metaAs() =
    meta?.safeCast<T>()

inline fun <reified C> Any?.safeCast() = this as? C

inline var ItemStack.meta: ItemMeta?
    get() = itemMeta
    set(value) {
        itemMeta = value
    }

fun Any?.unit() {}

fun Component.toText() =
    LegacyComponentSerializer.legacySection().serialize(this)

fun command(name: String, handler: (Player, Array<String>) -> Unit) =
    Bukkit.getCommandMap().register(name, object : Command(name) {
        override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
            if (sender !is Player) return true

            handler(sender, args)

            return true
        }
    })