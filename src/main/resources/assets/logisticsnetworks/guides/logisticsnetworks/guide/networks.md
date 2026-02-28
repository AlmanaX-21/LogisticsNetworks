---
navigation:
  title: Networks
  parent: index.md
  icon: logisticsnetworks:logistics_node
  position: 1
---

# Networks

A network is a logical group of nodes. Transfers only happen between nodes in the same network.

Networks are not spatial. Nodes in different dimensions can share the same network if they have a Dimensional Upgrade.

## Creating a Network

1. Place a node on a block.
2. Open the node with the wrench in Wrench mode.
3. Type a name in the network name input.
4. Click Create Network.

The node joins the new network and starts participating in transfers.

## Joining an Existing Network

1. Open the node with the wrench.
2. Scroll the existing network list.
3. Click the network you want to join.

## Renaming a Network

You can rename a network from the node GUI. Only the network owner or server operators can rename.

Network names have a maximum length of 32 characters.

## How Networks Work

1. Each node stores a network id.
2. The network registry tracks which nodes belong to each network.
3. Empty networks with no nodes are deleted automatically.
4. A node with no network does not transfer anything.
