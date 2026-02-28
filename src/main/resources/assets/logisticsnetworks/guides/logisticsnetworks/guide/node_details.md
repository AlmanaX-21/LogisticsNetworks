---
navigation:
  title: Node Details
  parent: index.md
  position: 5
---

# Node Details

This page covers every setting in the node configuration GUI.

## Top Controls

The top section of the channel page has:

1. Network name label
2. Visibility toggle for node render
3. Change button to return to network selection
4. Channel tabs from 0 to 8

## Channel Settings

Each channel has the following settings in the left panel:

1. **Status**: toggles the channel enabled or disabled.
2. **Mode**: cycles between Import and Export.
3. **Type**: cycles between Item, Fluid, Energy, Chemical, and Source. Chemical requires Mekanism Chemical Upgrade. Source requires Ars Source Upgrade.
4. **Side**: sets which block face the node uses for extraction or insertion.
5. **Redstone**: sets the redstone condition for the channel to run.
6. **Distribution**: picks target ordering for export channels.
7. **Priority**: a value used by Priority distribution mode. Ranges from -99 to 99.
8. **Batch**: max amount per transfer operation. Clamped to the current upgrade tier cap.
9. **Delay**: ticks between transfer runs. Has a minimum based on upgrade tier config. Energy type forces delay to 1 tick.

In Import mode, Distribution, Batch, and Delay are disabled in the UI.

## Input Shortcuts

1. Left click cycles a setting forward. Right click cycles backward.
2. Hold Shift for bigger numeric step changes.
3. Double click Priority, Batch, or Delay to type a number directly.
4. Hold Alt and click a numeric row to jump to min or max.

## Filter and Upgrade Slots

1. Each channel has a 3 by 3 filter grid (9 filter slots).
2. The node has one 2 by 2 upgrade grid (4 shared upgrade slots).

The filter mode button near the filter label toggles between Any and All for that channel.

## Network Selection Page

When no network is assigned or you click Change:

1. Network name input for creating new networks.
2. Create Network button.
3. Existing network list with scroll.
4. Click an entry to join that network.
