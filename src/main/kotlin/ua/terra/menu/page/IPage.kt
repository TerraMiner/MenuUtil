package ua.terra.menu.page

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import ua.terra.menu.Task
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.icon.IIcon
import ua.terra.menu.menu.IMenu
import ua.terra.menu.menuIcon
import ua.terra.menu.updater.IconUpdater

interface IPage {
    val index: Int
    val menu: IMenu
    val icons: MutableMap<Int, IIcon>

    val inventory: Inventory

    val emptySlots: MutableList<Int>

    val dynamicItems: MutableList<IconUpdater>

    val updater: Task

    fun InventoryClickEvent.clickEvent() {
        if (whoClicked !is Player) return
        if (this@IPage.inventory !== clickedInventory) return
        val icon = icons[slot] ?: return
        val event = MenuClickEvent(
            menu,
            icon,
            inventory,
            whoClicked as Player,
            click,
            action,
            cursor,
            slotType,
            slot
        )
        event.callEvent()
        icon.click(event)
        isCancelled = event.isCancelled
    }

    fun update() {
        icons.forEach { (index, icon) ->
            inventory.setItem(index,icon.stack)
        }
    }

    fun update(index: Int) {
        inventory.setItem(index,icons[index]?.stack)
    }

    fun addIcon(stack: ItemStack,
                event: MenuClickEvent.() -> Unit = { isCancelled = true },
                updater: IconUpdater? = null) {
        addIcon(menuIcon(-1,stack) { click(event); iconUpdater = updater})
    }

    fun addIcon(icon: IIcon) {
        val emptySlot = emptySlots.sorted().getOrElse(0) { throw ArrayIndexOutOfBoundsException("That menu page is already fully!") }
        setIcon(emptySlot,icon.clone())
    }

    fun setIcon(index: Int, icon: IIcon) {
        icon.slot = index
        icons[index] = icon.clone()

        icons[index]?.iconUpdater?.also { dynamicItems.add(it) }

        if (icon.stack.amount > 0) emptySlots.remove(icon.slot)
        else if (!emptySlots.contains(icon.slot)) emptySlots.add(icon.slot)
    }

    fun setIcon(icon: IIcon) {
        setIcon(icon.slot,icon.clone())
    }

    fun setIcon(index: Int, stack: ItemStack,
                event: MenuClickEvent.() -> Unit = { isCancelled = true },
                updater: IconUpdater? = null) {
        setIcon(menuIcon(index,stack) { click(event); iconUpdater = updater })
    }

    fun getIcon(index: Int) = icons[index]

    fun fillItem(stack: ItemStack, vararg slots: Int) {
        slots.forEach {
            setIcon(it, stack)
        }
    }

    fun fillItem(stack: ItemStack, range: IntRange) {
        fillItem(stack, *range.toList().toIntArray())
    }

    fun hasNextPage(): Boolean
    fun hasPrevPage(): Boolean
}