package ua.terra.menu.icon.functional

import org.bukkit.inventory.ItemStack
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.icon.IIcon
import ua.terra.menu.page.IPage
import ua.terra.menu.updater.IconUpdater
import ua.terra.menu.utils.safeCast

class MenuIconSlider private constructor(
    override var slot: Int,
    override var stack: ItemStack,
    var delay: Int,
    var period: Int,
    var backTicking: Boolean = false,
    val autoSlide: Boolean,
    override var clicks: MutableList<(IPage, MenuClickEvent) -> Unit> = mutableListOf({ _, e -> e.isCancelled = true }),
    val action: MenuIconSlider.() -> Unit
) : IFuncIcon {

    override var iconUpdaters: MutableList<IconUpdater> = mutableListOf()
    override var accessor: IconAccessor = IconAccessor(this)

    private val slides = buildList<IIcon> {
        add(this@MenuIconSlider)
    }.toMutableList()

    private var slideId = 0
    private var times = -1
    private var canBackToParent = false

    private var defaultTimes = times

    private var skip = false

    override fun clone(): IFuncIcon =
        MenuIconSlider(slot, stack, delay, period, backTicking, autoSlide, clicks, action).also {
            it.iconUpdaters = iconUpdaters
            it.accessor = accessor
            it.slides.addAll(slides)
            it.slideId = slideId
            it.times = times
            it.canBackToParent = canBackToParent
            it.defaultTimes = defaultTimes
            it.skip = skip
        }

    override fun afterSetup() {
        slides.forEach {
            it.slot = slot
        }
    }


    init {

        if (autoSlide && period > 0) {
            iconUpdaters.add(IconUpdater(this, delay, period, backTicking) { page, _ ->
                slide(page)
            })
        } else {
            clicks.add { page, _ ->
                slide(page)
            }
        }

        action()

    }

    fun currentSlideId() = slideId

    fun currentSlide() = slides[slideId]

    fun nextSlide(): IIcon {
        val next = (slideId + 1).let {
            if (it >= slides.size) 0
            else it
        }
        return slides[next]
    }

    fun times(count: Int, canBackToParent: Boolean = false) {
        times = count
        this.canBackToParent = canBackToParent
        defaultTimes = times
    }

    fun addSlide(icon: IIcon) {
        slides.add(
            icon.apply {
                if (!autoSlide) safeCast<IFuncIcon>()?.clicks?.add { page, _ ->
                    slide(page)
                }
            }
        )
    }

    fun addSlides(vararg icons: IIcon) {
        addSlides(icons.toList())
    }

    fun addSlides(list: Collection<IIcon>) {
        list.forEach {
            addSlide(it)
        }
    }

    fun slide(page: IPage) {
        if (skip) {
            skip = false
            return
        }

        if (canBackToParent && times == 0) return

        if (++slideId >= slides.size) {
            if (times > 0) --times
            slideId = 0
        }

        if (!canBackToParent && times == 0) return

        setSlide(page)
    }

    fun reset(page: IPage) {
        times = defaultTimes
        slideId = 0
        skip = true
        setSlide(page)
    }

    private fun setSlide(page: IPage) {
        slides[slideId].also {
            iconUpdaters.clear()

            page.setIcon(slot, it)
        }

        page.update(slot)
    }


    constructor(
        slot: Int,
        stack: ItemStack,
        action: MenuIconSlider.() -> Unit
    ) : this(
        slot,
        stack,
        -1,
        -1,
        false,
        autoSlide = false,
        action = action
    )

    constructor(
        slot: Int,
        stack: ItemStack,
        delay: Int,
        period: Int,
        backTicking: Boolean,
        action: MenuIconSlider.() -> Unit
    ) : this(
        slot,
        stack,
        delay,
        period,
        backTicking,
        autoSlide = true,
        action = action
    )
}
