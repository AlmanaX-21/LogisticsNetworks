---
item_ids: [logisticsnetworks:small_filter, logisticsnetworks:medium_filter, logisticsnetworks:big_filter]
navigation:
  title: Item Filters
  parent: filters.md
  icon: logisticsnetworks:small_filter
  position: 1
---

# Item Filters

The base slot-based filters come in three sizes:

1. **Small Filter**: 9 entries
2. **Medium Filter**: 18 entries
3. **Big Filter**: 27 entries

Each entry can store one target of type Item, Fluid, or Chemical. The target type is selected in the filter GUI. Duplicate entries are not allowed.

## Matching

1. Item entries match by item id.
2. Fluid entries match by fluid type with data components.
3. Chemical entries match by exact chemical id.

## Built-in Modes

Each slot in a base filter supports Amount, Tag, and NBT filtering without needing separate filter items. See the Filters overview page for keybinds and behavior details.

## Actions for each mode

### Amount
Hover over a slot and scroll to change the amount threshold.
- Normal scroll: +/- 1 (or 50mB for fluids)
- Shift scroll: +/- 8 (or 500mB for fluids)
- Ctrl scroll: +/- 64 (or 1000mB for fluids)
- Alt scroll: jump to maximum or minimum

### Tag Mode
Ctrl left click on a slot to enter Tag mode. Type a tag name directly, or place an item in the extractor slot and pick from its tags.

### NBT Mode
Ctrl right click on a slot to enter NBT mode. Place an item or fluid container in the extractor slot to browse its data components. Pick a path and the value is stored.

<RecipeFor id="logisticsnetworks:small_filter" />
<RecipeFor id="logisticsnetworks:medium_filter" />
<RecipeFor id="logisticsnetworks:big_filter" />
