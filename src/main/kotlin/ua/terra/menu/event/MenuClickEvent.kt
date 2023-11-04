package ua.terra.menu.event

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryType.SlotType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import ua.terra.menu.icon.IIcon
import ua.terra.menu.menu.IMenu

class MenuClickEvent(
    val menu: IMenu,
    val icon: IIcon,
    val inventory: Inventory,
    val whoClicked: Player,
    val clickType: ClickType,
    val action: InventoryAction,
    val clickedWithItem: ItemStack?,
    val slotType: SlotType,
    val slot: Int
) : Event(false), Cancellable {
    override fun getHandlers(): HandlerList = Companion.handlers

    private var _cancel = false

    override fun isCancelled(): Boolean {
        return _cancel
    }

    override fun setCancelled(cancel: Boolean) {
        _cancel = cancel
    }

    companion object {
        val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlers
    }
}