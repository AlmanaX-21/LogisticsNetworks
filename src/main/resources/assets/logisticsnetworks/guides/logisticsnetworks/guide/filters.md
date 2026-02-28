---
navigation:
  title: Filters
  parent: index.md
  position: 7
---

# Filters

Filters control what resources a channel accepts or rejects. They are checked on both ends of a transfer:

1. Export channel filter slots on the source node.
2. Import channel filter slots on the target node.

Filters work on Item, Fluid, and Chemical channels. Energy and Source channels do not use filters.

## Filter Mode

The channel filter mode controls whitelist behavior:

1. **Any**: at least one whitelist filter must match.
2. **All**: every whitelist filter must match.

Blacklist filter matches always block the transfer regardless of mode.

## Base Filters

The Small, Medium, and Big filters are the primary filter items. They include built-in support for Amount, Tag, and NBT filtering per slot.

### Keybinds

1. **Amount**: hover over a slot and scroll to set an amount threshold.
   - Normal scroll: +/- 1 (or 50mB for fluids)
   - Shift scroll: +/- 8 (or 500mB for fluids)
   - Ctrl scroll: +/- 64 (or 1000mB for fluids)
   - Alt scroll: jump to maximum or minimum
2. **Tag mode**: Ctrl left click on a slot.
3. **NBT mode**: Ctrl right click on a slot.

### Amount Filtering

Sets a stock threshold for transfers.

1. On the Export side, keeps stock in the source. Only moves amounts above the threshold.
2. On the Import side, caps stock in the destination. Stops filling at the threshold.
3. If multiple amount filters exist on a channel, export uses the highest threshold, import uses the lowest.

### Tag Filtering

Stores one tag value per slot. Set it by typing a tag name or using an item in the extractor slot to pick from its tags. Supports Items, Fluids, and Chemicals.

### NBT Filtering

Stores one selected NBT path and exact value per slot. Put an item or fluid container in the extractor slot to browse its data components. Pick a path and the value is stored. The match requires exact equality on the selected value.

## Specialized Filters

These filters provide unique matching behavior that base filters do not cover.

<SubPages icons={true} />
