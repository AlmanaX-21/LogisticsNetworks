---
navigation:
  title: Filters
  parent: index.md
  position: 7
---

# Filters

Filters are read from both ends of a transfer.

For supported channel types, transfer checks:

1. Export channel filter slots on the source node
2. Import channel filter slots on the target node

So filters are bidirectional in practice.

The channel filter mode changes whitelist behavior:

1. Any means at least one whitelist filter can match.
2. All means all whitelist filters must match.
3. Blacklist matches always block.

## Base Filter Features and Keybinds

The base filters (Small, Medium, Big) include built-in capabilities for Amount, Tag, and NBT filtering without needing specialized filter items.

- **Amount Filtering:** Hover over a slot and scroll to set an amount.
  - Normal scroll: +/- 1 (or 50mB for fluids)
  - Shift + scroll: +/- 8 (or 500mB for fluids)
  - Ctrl + scroll: +/- 64 (or 1000mB for fluids)
  - Alt + scroll: Set to maximum / minimum
- **Tag Filtering:** `Ctrl + Left Click` on a slot to enter Tag mode.
- **NBT Filtering:** `Ctrl + Right Click` on a slot to enter NBT mode.

### Filtering Behaviors

**Amount Filtering**
Sets a stock threshold for transfers.
- On the **Export** side, it keeps stock in the source (allows moving anything *above* threshold).
- On the **Import** side, it caps stock in the destination (stops filling *at* threshold).
- If multiple amount filters exist, export uses the highest threshold, import uses the lowest.

**Tag Filtering**
Stores one tag value for a slot. You can select it by typing or using an item in the extractor slot. Supports Items, Fluids, and Chemicals (if Mekanism is present).

**NBT Filtering**
Stores one selected NBT path and exact value. Put an item or fluid container in the extractor slot to pick a path. The match must be exact equality on the selected value to pass the filter.

Open each filter page below for exact behavior.

<SubPages icons={true} />

