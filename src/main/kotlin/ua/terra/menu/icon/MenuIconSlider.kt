package ua.terra.menu.icon

import org.bukkit.inventory.ItemStack
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.page.IPage
import ua.terra.menu.updater.IconUpdater

class MenuIconSlider(
    override var slot: Int,
    override var stack: ItemStack,
    delay: Int,
    period: Int,
    backTicking: Boolean = false,
    autoSlide: Boolean,
    override var clicks: MutableList<(IPage, MenuClickEvent) -> Unit> = mutableListOf({ _, e -> e.isCancelled = true }),
    icons: MenuIconSlider.() -> List<IIcon>,
) : IIcon {

    constructor(slot: Int, stack: ItemStack, icons: MenuIconSlider.() -> List<IIcon>)
            : this(slot, stack, -1, -1, false, autoSlide = false, icons = icons)

    constructor(
        slot: Int,
        stack: ItemStack,
        delay: Int,
        period: Int,
        backTicking: Boolean,
        icons: MenuIconSlider.() -> List<IIcon>
    ) : this(slot, stack, delay, period, backTicking, autoSlide = true, icons = icons)

    override var iconUpdaters: MutableList<IconUpdater> = mutableListOf()

    var slideId = 0

    private val slides = buildList {
        add(this@MenuIconSlider)
        addAll(icons(this@MenuIconSlider).map { icon ->
            icon.apply {
                if (!autoSlide) clicks.add { page, _ -> slide(page) }
            }
        })
    }

    init {
        if (autoSlide && period > 0) {
            iconUpdaters.add(IconUpdater(this, delay, period, backTicking) { page, _ ->
                slide(page)
            })
        } else {
            clicks.add { page, _ ->
                slide(page)
                println("Updated and incremented")
            }
        }
    }

    fun slide(page: IPage) {
        if (++slideId >= slides.size) slideId = 0
        val currentStack = slides[slideId]
        currentStack.iconUpdaters.clear()
        page.setIcon(slot, currentStack)
        page.update(slot)
    }

}
