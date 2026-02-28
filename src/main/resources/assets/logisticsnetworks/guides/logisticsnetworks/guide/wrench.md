---
item_ids: [logisticsnetworks:wrench]
navigation:
  title: Wrench
  parent: index.md
  icon: logisticsnetworks:wrench
  position: 3
---

# Wrench

The wrench is the main tool for interacting with nodes. It has three modes:

1. Wrench
2. Copy Paste
3. Mass Placement

Switch modes by holding Shift and scrolling the mouse wheel. This works with the wrench in main hand or off hand.

<RecipeFor id="logisticsnetworks:wrench" />

## Wrench Mode

Right click a node to open its configuration screen.

Shift right click a node to remove it. Removing a node drops:

1. The node item
2. All filters in all channels
3. All installed upgrades

## Copy Paste Mode

This mode lets you copy a node's full configuration and paste it onto other nodes.

1. Right click a node to copy its setup into the wrench clipboard.
2. Shift right click a node to paste clipboard data to that node.
3. Ctrl right click a node to paste to all connected nodes on connected blocks of the same type. Only pastes to blocks that already have a node attached.

Clipboard paste applies channel settings, upgrades, and filter setup from the copied node.

### Air Actions

1. Right click in air opens the clipboard editor.
2. Shift right click in air sends a chat preview of clipboard data.

### Auto Paste

If you place a new node while holding a wrench with clipboard data in the off hand, the clipboard config is automatically pasted onto the new node.

## Mass Placement Mode

This mode lets you place many nodes in one action.

### Controls

1. Right click a block to select or unselect it.
2. Ctrl right click a block to toggle connected blocks of the same type. If the clicked block is already selected, connected selected blocks are removed. If not selected, connected valid blocks are added.
3. Right click in air to open the mass placement screen.
4. Click Place Nodes to place nodes on all selected blocks in this dimension.

Selected blocks are shown with an orange outline while you are in this mode with the wrench held.

### Mass Placement Screen

The screen shows:

1. Selected block count
2. Required node count
3. Required upgrade count
4. Required filter count
5. Status indicator for whether placement is possible or blocked
6. Required item list with availability status per item

If the wrench clipboard has a copied node config, that config is automatically pasted on every placed node.
