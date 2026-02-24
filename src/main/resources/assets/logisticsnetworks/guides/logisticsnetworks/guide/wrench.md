---
item_ids: [logisticsnetworks:wrench]
navigation:
  title: Wrench
  parent: index.md
  icon: logisticsnetworks:wrench
  position: 3
---

# Wrench

The wrench has three modes.

1. Wrench
2. Copy Paste
3. Mass Placement

You switch modes by holding Shift and scrolling the mouse wheel.
This works if the wrench is in main hand or off hand.

## Wrench Mode

Right click a node to open node config.

Shift right click a node to remove it.
Removing a node drops:

1. The node item
2. All filters in all channels
3. All installed upgrades

## Copy Paste Mode

Right click a node to copy that node setup into the wrench clipboard.

Shift right click a node to paste clipboard data to that node.

Ctrl plus right click a node to paste clipboard data to all connected nodes on connected blocks of the same type.
This only pastes to blocks that already have a node attached.

Right click in air opens the clipboard editor.

Shift right click in air sends a chat preview of clipboard data.

If you place a new node while holding a wrench with clipboard data in off hand, the mod tries to auto paste to that node.

Clipboard paste applies channel settings, upgrades, and filter setup from the copied node.

## Mass Placement Mode

This mode lets you place many nodes in one action.

Controls:

1. Right click block: select or unselect that block.
2. Ctrl plus right click block: toggle connected blocks of the same type.
If the clicked block is already selected, connected selected blocks are removed.
If the clicked block is not selected, connected valid blocks are added.
3. Right click air: open mass placement screen.
4. Press Place Nodes in that screen: place nodes on all selected blocks in this dimension.

Selected blocks are shown with an orange outline while you are in this mode with the wrench.

The mass placement screen shows:

1. Selected block count
2. Required node count
3. Required upgrade count
4. Required filter count
5. Status mark for placement possible or blocked
6. Required item list with a check or cross per item and available over required count

If the wrench clipboard has a copied node config, that config is auto pasted on every placed node.
