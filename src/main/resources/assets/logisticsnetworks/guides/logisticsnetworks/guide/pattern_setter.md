---
item_ids: [logisticsnetworks:pattern_setter]
navigation:
  title: Pattern Setter
  parent: index.md
  icon: logisticsnetworks:pattern_setter
  position: 8
---

# Pattern Setter

The Pattern Setter writes AE2 encoded pattern inputs or outputs into a filter.

This item only works when Applied Energistics 2 is installed.

## How to Use

1. Right click with the Pattern Setter to open the GUI.
2. Place an AE2 encoded pattern in the top slot.
3. Place a filter in the bottom slot.
4. Click Input to write pattern inputs to the filter.
5. Click Output to write pattern outputs to the filter.

The filter is cleared before writing. Only entries that fit in the filter capacity are written.

## Multiplier

The number field below the buttons sets a multiplier.

All amounts from the pattern are multiplied by this value.

For example if a pattern has 1 Iron Ingot as input and the multiplier is 64, the filter entry is set to 64 Iron Ingot.

The multiplier range is 1 to 10000.

## Supported Pattern Types

All AE2 pattern types are supported:

1. Processing patterns
2. Crafting patterns
3. Stonecutting patterns
4. Smithing patterns

Crafting patterns with duplicate ingredients are merged into one filter entry with combined amounts.

<RecipeFor id="logisticsnetworks:pattern_setter" />
