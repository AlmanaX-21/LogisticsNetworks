package me.almana.logisticsnetworks.client.screen;

import me.almana.logisticsnetworks.menu.ComputerMenu;
import me.almana.logisticsnetworks.network.RequestNetworkNodesPayload;
import me.almana.logisticsnetworks.network.SyncNetworkListPayload;
import me.almana.logisticsnetworks.network.SyncNetworkNodesPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

public class ComputerScreen extends AbstractContainerScreen<ComputerMenu> {

    private enum Page {
        NETWORK_LIST,
        ITEM_IO_GRAPH,
        NODE_MAP
    }

    // Constants
    private static final int GUI_WIDTH = 320;
    private static final int GUI_HEIGHT = 240;
    private static final int NETWORKS_PER_PAGE = 8;
    private static final int NETWORK_ENTRY_HEIGHT = 24;
    private static final int NETWORK_LIST_X = 8;
    private static final int NETWORK_LIST_Y = 30;
    private static final int NETWORK_LIST_WIDTH = 120;
    private static final int DIVIDER_X = 132;
    private static final int DETAIL_PANEL_X = 136;
    private static final int DETAIL_PANEL_Y = 30;
    private static final int DETAIL_PANEL_WIDTH = 176;
    private static final int DETAIL_PANEL_HEIGHT = 190;

    // Button dimensions for the 2 options
    private static final int OPTION_BTN_WIDTH = 150;
    private static final int OPTION_BTN_HEIGHT = 30;
    private static final int OPTION_BTN_GAP = 16;

    // Node map constants
    private static final int NODE_ENTRY_HEIGHT = 18;
    private static final int NODES_PER_PAGE = 10;
    private static final int GROUP_HEADER_HEIGHT = 20;

    // Colors
    private static final int COLOR_BG = 0xC0101010;
    private static final int COLOR_PANEL = 0xFF2B2B2B;
    private static final int COLOR_HOVER = 0xFF3B3B3B;
    private static final int COLOR_BORDER = 0xFF8B8B8B;
    private static final int COLOR_TEXT = 0xFFFFFFFF;
    private static final int COLOR_TEXT_SECONDARY = 0xFFAAAAAA;
    private static final int COLOR_SLOT_BG = 0xFF1A1A1A;
    private static final int COLOR_BTN = 0xFF383838;
    private static final int COLOR_BTN_HOVER = 0xFF484848;
    private static final int COLOR_BTN_BORDER = 0xFF5A5A5A;
    private static final int COLOR_BTN_BORDER_HOVER = 0xFF7A9ABB;
    private static final int COLOR_BTN_TOP_EDGE = 0xFF4A4A4A;
    private static final int COLOR_BACK_BTN = 0xFF3A3A3A;
    private static final int COLOR_BACK_BTN_HOVER = 0xFF4A4A4A;
    private static final int COLOR_GROUP_HEADER = 0xFF333344;
    private static final int COLOR_ACCENT_TEXT = 0xFF7A9ABB;

    // State
    private Page currentPage = Page.NETWORK_LIST;
    private List<SyncNetworkListPayload.NetworkEntry> networkList = new ArrayList<>();
    private int networkScrollOffset = 0;
    private UUID selectedNetworkId = null;
    private String selectedNetworkName = "";

    // Node map state
    private List<SyncNetworkNodesPayload.NodeInfo> nodeInfoList = new ArrayList<>();
    private int groupMode = 0; // 0=None, 1=Block, 2=Label
    private static final int GROUP_MODE_COUNT = 3;
    private int nodeMapScrollOffset = 0;

    public ComputerScreen(ComputerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = GUI_WIDTH;
        this.imageHeight = GUI_HEIGHT;
        this.inventoryLabelY = 10000; // Hide player inventory label
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        // Main background
        g.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, COLOR_BG);
        g.renderOutline(leftPos, topPos, imageWidth, imageHeight, COLOR_BORDER);

        switch (currentPage) {
            case NETWORK_LIST -> renderNetworkListPage(g, mouseX, mouseY);
            case ITEM_IO_GRAPH -> renderItemIOGraphPage(g, mouseX, mouseY);
            case NODE_MAP -> renderNodeMapPage(g, mouseX, mouseY);
        }
    }

    // ==================== NETWORK LIST PAGE ====================

    private void renderNetworkListPage(GuiGraphics g, int mouseX, int mouseY) {
        // Wrench slot background (top right)
        int slotX = leftPos + 292 - 1;
        int slotY = topPos + 8 - 1;
        g.fill(slotX, slotY, slotX + 18, slotY + 18, COLOR_SLOT_BG);
        g.renderOutline(slotX, slotY, 18, 18, COLOR_BORDER);

        // Network list
        renderNetworkList(g, mouseX, mouseY);

        // Vertical divider line
        int dividerX = leftPos + DIVIDER_X;
        g.fill(dividerX, topPos + NETWORK_LIST_Y, dividerX + 1, topPos + imageHeight - 10, COLOR_BORDER);

        // Detail panel with 2 option buttons
        if (selectedNetworkId != null) {
            renderOptionButtons(g, mouseX, mouseY);
        }
    }

    private void renderNetworkList(GuiGraphics g, int mouseX, int mouseY) {
        int startX = leftPos + NETWORK_LIST_X;
        int startY = topPos + NETWORK_LIST_Y;

        // Network list header
        g.drawString(font, "Your Networks:", startX, startY - 12, COLOR_TEXT);

        if (networkList.isEmpty()) {
            Component noNetworks = Component.translatable("gui.logisticsnetworks.computer.no_networks");
            g.drawString(font, noNetworks, startX + 4, startY + 10, COLOR_TEXT_SECONDARY);
            return;
        }

        // Render visible networks
        int maxScroll = Math.max(0, networkList.size() - NETWORKS_PER_PAGE);
        networkScrollOffset = Math.max(0, Math.min(networkScrollOffset, maxScroll));

        for (int i = 0; i < NETWORKS_PER_PAGE && (i + networkScrollOffset) < networkList.size(); i++) {
            int index = i + networkScrollOffset;
            SyncNetworkListPayload.NetworkEntry entry = networkList.get(index);
            int entryX = startX;
            int entryY = startY + (i * NETWORK_ENTRY_HEIGHT);

            boolean hovered = isHovering(NETWORK_LIST_X, NETWORK_LIST_Y + (i * NETWORK_ENTRY_HEIGHT),
                    NETWORK_LIST_WIDTH, NETWORK_ENTRY_HEIGHT - 2, mouseX, mouseY);

            renderNetworkEntry(g, entry, entryX, entryY, NETWORK_LIST_WIDTH, hovered);
        }

        // Pagination info
        if (networkList.size() > NETWORKS_PER_PAGE) {
            int currentPage = (networkScrollOffset / NETWORKS_PER_PAGE) + 1;
            int totalPages = (int) Math.ceil((double) networkList.size() / NETWORKS_PER_PAGE);
            String pageInfo = "Page " + currentPage + "/" + totalPages;
            int pageInfoX = leftPos + (imageWidth / 2) - (font.width(pageInfo) / 2);
            g.drawString(font, pageInfo, pageInfoX, topPos + imageHeight - 15, COLOR_TEXT_SECONDARY);
        }
    }

    private void renderNetworkEntry(GuiGraphics g, SyncNetworkListPayload.NetworkEntry entry,
                                     int x, int y, int width, boolean hovered) {
        boolean selected = entry.id().equals(selectedNetworkId);
        int bgColor = selected ? 0xFF4A4A4A : (hovered ? COLOR_HOVER : COLOR_PANEL);
        g.fill(x, y, x + width, y + NETWORK_ENTRY_HEIGHT, bgColor);
        g.renderOutline(x, y, width, NETWORK_ENTRY_HEIGHT, selected ? 0xFFFFFFFF : COLOR_BORDER);

        String name = entry.name();
        if (font.width(name) > width - 8) {
            name = font.plainSubstrByWidth(name, width - 13) + "...";
        }
        g.drawString(font, name, x + 4, y + 4, COLOR_TEXT);

        String nodeCount = String.valueOf(entry.nodeCount());
        g.drawString(font, nodeCount, x + 4, y + 13, COLOR_TEXT_SECONDARY);
    }

    private void renderOptionButtons(GuiGraphics g, int mouseX, int mouseY) {
        int panelX = leftPos + DETAIL_PANEL_X;
        int panelY = topPos + DETAIL_PANEL_Y;

        // Background
        g.fill(panelX, panelY, panelX + DETAIL_PANEL_WIDTH, panelY + DETAIL_PANEL_HEIGHT, COLOR_PANEL);
        g.renderOutline(panelX, panelY, DETAIL_PANEL_WIDTH, DETAIL_PANEL_HEIGHT, COLOR_BORDER);

        // Network name header
        String header = selectedNetworkName;
        if (font.width(header) > DETAIL_PANEL_WIDTH - 16) {
            header = font.plainSubstrByWidth(header, DETAIL_PANEL_WIDTH - 21) + "...";
        }
        int headerX = panelX + (DETAIL_PANEL_WIDTH / 2) - (font.width(header) / 2);
        g.drawString(font, header, headerX, panelY + 12, COLOR_TEXT);

        // Center the buttons vertically in the remaining space
        int buttonsAreaY = panelY + 35;
        int totalButtonsHeight = (OPTION_BTN_HEIGHT * 2) + OPTION_BTN_GAP;
        int startBtnY = buttonsAreaY + ((DETAIL_PANEL_HEIGHT - 45) - totalButtonsHeight) / 2;

        int btnX = panelX + (DETAIL_PANEL_WIDTH - OPTION_BTN_WIDTH) / 2;

        // Button 1: I/O Graph
        int btn1Y = startBtnY;
        boolean btn1Hovered = mouseX >= btnX && mouseX < btnX + OPTION_BTN_WIDTH
                && mouseY >= btn1Y && mouseY < btn1Y + OPTION_BTN_HEIGHT;
        renderOptionButton(g, btnX, btn1Y, "I/O Graph", btn1Hovered);

        // Button 2: Node Map
        int btn2Y = startBtnY + OPTION_BTN_HEIGHT + OPTION_BTN_GAP;
        boolean btn2Hovered = mouseX >= btnX && mouseX < btnX + OPTION_BTN_WIDTH
                && mouseY >= btn2Y && mouseY < btn2Y + OPTION_BTN_HEIGHT;
        renderOptionButton(g, btnX, btn2Y, "Node Map", btn2Hovered);
    }

    private void renderOptionButton(GuiGraphics g, int x, int y, String label, boolean hovered) {
        int bgColor = hovered ? COLOR_BTN_HOVER : COLOR_BTN;
        int borderColor = hovered ? COLOR_BTN_BORDER_HOVER : COLOR_BTN_BORDER;

        // Button body
        g.fill(x, y, x + OPTION_BTN_WIDTH, y + OPTION_BTN_HEIGHT, bgColor);
        // Top highlight edge (subtle bevel)
        g.fill(x + 1, y + 1, x + OPTION_BTN_WIDTH - 1, y + 2, COLOR_BTN_TOP_EDGE);
        // Border
        g.renderOutline(x, y, OPTION_BTN_WIDTH, OPTION_BTN_HEIGHT, borderColor);

        int textColor = hovered ? COLOR_TEXT : COLOR_TEXT_SECONDARY;
        int textX = x + (OPTION_BTN_WIDTH / 2) - (font.width(label) / 2);
        int textY = y + (OPTION_BTN_HEIGHT / 2) - (font.lineHeight / 2);
        g.drawString(font, label, textX, textY, textColor);
    }

    // ==================== ITEM I/O GRAPH PAGE ====================

    private void renderItemIOGraphPage(GuiGraphics g, int mouseX, int mouseY) {
        // Title bar
        String title = "I/O Graph - " + selectedNetworkName;
        if (font.width(title) > imageWidth - 80) {
            title = font.plainSubstrByWidth(title, imageWidth - 85) + "...";
        }
        int titleX = leftPos + (imageWidth / 2) - (font.width(title) / 2);
        g.drawString(font, title, titleX, topPos + 8, COLOR_TEXT);

        // Back button
        renderBackButton(g, mouseX, mouseY);

        // Content area
        int contentX = leftPos + 8;
        int contentY = topPos + 28;
        int contentW = imageWidth - 16;
        int contentH = imageHeight - 38;

        g.fill(contentX, contentY, contentX + contentW, contentY + contentH, COLOR_PANEL);
        g.renderOutline(contentX, contentY, contentW, contentH, COLOR_BORDER);

        // Placeholder
        String placeholder = "I/O tracking coming soon";
        int phX = contentX + (contentW / 2) - (font.width(placeholder) / 2);
        int phY = contentY + (contentH / 2) - (font.lineHeight / 2);
        g.drawString(font, placeholder, phX, phY, COLOR_TEXT_SECONDARY);
    }

    // ==================== NODE MAP PAGE ====================

    private void renderNodeMapPage(GuiGraphics g, int mouseX, int mouseY) {
        // Title bar
        String title = "Node Map - " + selectedNetworkName;
        if (font.width(title) > imageWidth - 120) {
            title = font.plainSubstrByWidth(title, imageWidth - 125) + "...";
        }
        int titleX = leftPos + (imageWidth / 2) - (font.width(title) / 2);
        g.drawString(font, title, titleX, topPos + 8, COLOR_TEXT);

        // Back button
        renderBackButton(g, mouseX, mouseY);

        // Group toggle button
        renderGroupToggle(g, mouseX, mouseY);

        // Content area
        int contentX = leftPos + 8;
        int contentY = topPos + 28;
        int contentW = imageWidth - 16;
        int contentH = imageHeight - 38;

        g.fill(contentX, contentY, contentX + contentW, contentY + contentH, COLOR_PANEL);
        g.renderOutline(contentX, contentY, contentW, contentH, COLOR_BORDER);

        if (nodeInfoList.isEmpty()) {
            String noNodes = "No nodes in this network";
            int nnX = contentX + (contentW / 2) - (font.width(noNodes) / 2);
            int nnY = contentY + (contentH / 2) - (font.lineHeight / 2);
            g.drawString(font, noNodes, nnX, nnY, COLOR_TEXT_SECONDARY);
            return;
        }

        if (groupMode > 0) {
            renderGroupedNodeMap(g, contentX, contentY, contentW, contentH, mouseX, mouseY);
        } else {
            renderFlatNodeMap(g, contentX, contentY, contentW, contentH, mouseX, mouseY);
        }
    }

    private void renderFlatNodeMap(GuiGraphics g, int contentX, int contentY,
                                    int contentW, int contentH, int mouseX, int mouseY) {
        int maxScroll = Math.max(0, nodeInfoList.size() - NODES_PER_PAGE);
        nodeMapScrollOffset = Math.max(0, Math.min(nodeMapScrollOffset, maxScroll));

        int listY = contentY + 6;
        for (int i = 0; i < NODES_PER_PAGE && (i + nodeMapScrollOffset) < nodeInfoList.size(); i++) {
            int index = i + nodeMapScrollOffset;
            SyncNetworkNodesPayload.NodeInfo info = nodeInfoList.get(index);
            int entryY = listY + (i * NODE_ENTRY_HEIGHT);

            boolean hovered = mouseX >= contentX + 4 && mouseX < contentX + contentW - 4
                    && mouseY >= entryY && mouseY < entryY + NODE_ENTRY_HEIGHT - 2;
            int bgColor = hovered ? COLOR_HOVER : 0xFF1F1F1F;
            g.fill(contentX + 4, entryY, contentX + contentW - 4, entryY + NODE_ENTRY_HEIGHT - 2, bgColor);

            // Block name + optional label
            String blockName = info.blockName();
            if (!info.nodeLabel().isEmpty()) {
                blockName = blockName + " [" + info.nodeLabel() + "]";
            }
            if (font.width(blockName) > contentW - 100) {
                blockName = font.plainSubstrByWidth(blockName, contentW - 105) + "...";
            }
            g.drawString(font, blockName, contentX + 8, entryY + 2, COLOR_TEXT);

            // Position
            String posStr = "(" + info.attachedPos().getX() + ", " + info.attachedPos().getY()
                    + ", " + info.attachedPos().getZ() + ")";
            int posX = contentX + contentW - font.width(posStr) - 8;
            g.drawString(font, posStr, posX, entryY + 2, COLOR_TEXT_SECONDARY);
        }

        // Scroll indicator
        if (nodeInfoList.size() > NODES_PER_PAGE) {
            String scrollInfo = (nodeMapScrollOffset + 1) + "-"
                    + Math.min(nodeMapScrollOffset + NODES_PER_PAGE, nodeInfoList.size())
                    + " of " + nodeInfoList.size();
            int scrollX = contentX + contentW - font.width(scrollInfo) - 8;
            g.drawString(font, scrollInfo, scrollX, contentY + contentH - 14, COLOR_TEXT_SECONDARY);
        }
    }

    private void renderGroupedNodeMap(GuiGraphics g, int contentX, int contentY,
                                       int contentW, int contentH, int mouseX, int mouseY) {
        // Group nodes by block name or label depending on mode
        Map<String, List<SyncNetworkNodesPayload.NodeInfo>> groups = new LinkedHashMap<>();
        if (groupMode == 2) {
            // Group by label
            for (SyncNetworkNodesPayload.NodeInfo info : nodeInfoList) {
                String key = info.nodeLabel().isEmpty() ? "Unlabeled" : info.nodeLabel();
                groups.computeIfAbsent(key, k -> new ArrayList<>()).add(info);
            }
        } else {
            // Group by block name
            for (SyncNetworkNodesPayload.NodeInfo info : nodeInfoList) {
                groups.computeIfAbsent(info.blockName(), k -> new ArrayList<>()).add(info);
            }
        }

        // Build flat render list with headers and entries
        List<RenderEntry> renderEntries = new ArrayList<>();
        for (Map.Entry<String, List<SyncNetworkNodesPayload.NodeInfo>> group : groups.entrySet()) {
            renderEntries.add(new RenderEntry(group.getKey(), group.getValue().size()));
            for (SyncNetworkNodesPayload.NodeInfo info : group.getValue()) {
                renderEntries.add(new RenderEntry(info));
            }
        }

        int maxScroll = Math.max(0, renderEntries.size() - NODES_PER_PAGE);
        nodeMapScrollOffset = Math.max(0, Math.min(nodeMapScrollOffset, maxScroll));

        int listY = contentY + 6;
        for (int i = 0; i < NODES_PER_PAGE && (i + nodeMapScrollOffset) < renderEntries.size(); i++) {
            int index = i + nodeMapScrollOffset;
            RenderEntry entry = renderEntries.get(index);
            int entryY = listY + (i * NODE_ENTRY_HEIGHT);

            if (entry.isHeader) {
                // Group header
                g.fill(contentX + 4, entryY, contentX + contentW - 4, entryY + NODE_ENTRY_HEIGHT - 2,
                        COLOR_GROUP_HEADER);
                String headerText = entry.headerName + " (" + entry.headerCount + ")";
                g.drawString(font, headerText, contentX + 8, entryY + 2, COLOR_ACCENT_TEXT);
            } else {
                // Node entry
                boolean hovered = mouseX >= contentX + 4 && mouseX < contentX + contentW - 4
                        && mouseY >= entryY && mouseY < entryY + NODE_ENTRY_HEIGHT - 2;
                int bgColor = hovered ? COLOR_HOVER : 0xFF1F1F1F;
                g.fill(contentX + 4, entryY, contentX + contentW - 4, entryY + NODE_ENTRY_HEIGHT - 2, bgColor);

                // Show block name (for label grouping) or position (for block grouping)
                if (groupMode == 2) {
                    // Label grouping: show block name + position
                    String blockName = entry.nodeInfo.blockName();
                    g.drawString(font, "  " + blockName, contentX + 12, entryY + 2, COLOR_TEXT);
                    String posStr = "(" + entry.nodeInfo.attachedPos().getX() + ", "
                            + entry.nodeInfo.attachedPos().getY() + ", "
                            + entry.nodeInfo.attachedPos().getZ() + ")";
                    int posX = contentX + contentW - font.width(posStr) - 8;
                    g.drawString(font, posStr, posX, entryY + 2, COLOR_TEXT_SECONDARY);
                } else {
                    // Block grouping: show indented position
                    String posStr = "  (" + entry.nodeInfo.attachedPos().getX() + ", "
                            + entry.nodeInfo.attachedPos().getY() + ", "
                            + entry.nodeInfo.attachedPos().getZ() + ")";
                    g.drawString(font, posStr, contentX + 12, entryY + 2, COLOR_TEXT_SECONDARY);
                }
            }
        }

        // Scroll indicator
        if (renderEntries.size() > NODES_PER_PAGE) {
            String scrollInfo = (nodeMapScrollOffset + 1) + "-"
                    + Math.min(nodeMapScrollOffset + NODES_PER_PAGE, renderEntries.size())
                    + " of " + renderEntries.size();
            int scrollX = contentX + contentW - font.width(scrollInfo) - 8;
            g.drawString(font, scrollInfo, scrollX, contentY + contentH - 14, COLOR_TEXT_SECONDARY);
        }
    }

    // Helper for grouped node map rendering
    private static class RenderEntry {
        final boolean isHeader;
        final String headerName;
        final int headerCount;
        final SyncNetworkNodesPayload.NodeInfo nodeInfo;

        RenderEntry(String headerName, int count) {
            this.isHeader = true;
            this.headerName = headerName;
            this.headerCount = count;
            this.nodeInfo = null;
        }

        RenderEntry(SyncNetworkNodesPayload.NodeInfo nodeInfo) {
            this.isHeader = false;
            this.headerName = null;
            this.headerCount = 0;
            this.nodeInfo = nodeInfo;
        }
    }

    // ==================== SHARED UI ELEMENTS ====================

    private static final int BACK_BTN_X = 4;
    private static final int BACK_BTN_Y = 5;
    private static final int BACK_BTN_W = 40;
    private static final int BACK_BTN_H = 16;

    private void renderBackButton(GuiGraphics g, int mouseX, int mouseY) {
        int bx = leftPos + BACK_BTN_X;
        int by = topPos + BACK_BTN_Y;
        boolean hovered = mouseX >= bx && mouseX < bx + BACK_BTN_W
                && mouseY >= by && mouseY < by + BACK_BTN_H;
        int bgColor = hovered ? COLOR_BACK_BTN_HOVER : COLOR_BACK_BTN;
        g.fill(bx, by, bx + BACK_BTN_W, by + BACK_BTN_H, bgColor);
        g.renderOutline(bx, by, BACK_BTN_W, BACK_BTN_H, COLOR_BORDER);

        String label = "< Back";
        int lx = bx + (BACK_BTN_W / 2) - (font.width(label) / 2);
        int ly = by + (BACK_BTN_H / 2) - (font.lineHeight / 2);
        g.drawString(font, label, lx, ly, COLOR_TEXT);
    }

    private boolean isBackButtonClicked(double mouseX, double mouseY) {
        int bx = leftPos + BACK_BTN_X;
        int by = topPos + BACK_BTN_Y;
        return mouseX >= bx && mouseX < bx + BACK_BTN_W
                && mouseY >= by && mouseY < by + BACK_BTN_H;
    }

    private static final int GROUP_BTN_W = 90;
    private static final int GROUP_BTN_H = 14;

    private void renderGroupToggle(GuiGraphics g, int mouseX, int mouseY) {
        String label = switch (groupMode) {
            case 1 -> "Group: Block";
            case 2 -> "Group: Label";
            default -> "Group: None";
        };
        int bx = leftPos + imageWidth - GROUP_BTN_W - 8;
        int by = topPos + 6;
        boolean hovered = mouseX >= bx && mouseX < bx + GROUP_BTN_W
                && mouseY >= by && mouseY < by + GROUP_BTN_H;
        int bgColor = groupMode > 0 ? COLOR_BTN_HOVER : (hovered ? COLOR_BACK_BTN_HOVER : COLOR_BACK_BTN);
        g.fill(bx, by, bx + GROUP_BTN_W, by + GROUP_BTN_H, bgColor);
        g.renderOutline(bx, by, GROUP_BTN_W, GROUP_BTN_H, COLOR_BORDER);

        int lx = bx + (GROUP_BTN_W / 2) - (font.width(label) / 2);
        int ly = by + (GROUP_BTN_H / 2) - (font.lineHeight / 2);
        g.drawString(font, label, lx, ly, COLOR_TEXT);
    }

    private boolean isGroupToggleClicked(double mouseX, double mouseY) {
        int bx = leftPos + imageWidth - GROUP_BTN_W - 8;
        int by = topPos + 6;
        return mouseX >= bx && mouseX < bx + GROUP_BTN_W
                && mouseY >= by && mouseY < by + GROUP_BTN_H;
    }

    // ==================== INPUT HANDLING ====================

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        super.render(g, mouseX, mouseY, partialTick);
        this.renderTooltip(g, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        switch (currentPage) {
            case NETWORK_LIST -> {
                // Scroll network list
                if (networkList.size() > NETWORKS_PER_PAGE) {
                    networkScrollOffset -= (int) scrollY;
                    int maxScroll = Math.max(0, networkList.size() - NETWORKS_PER_PAGE);
                    networkScrollOffset = Math.max(0, Math.min(networkScrollOffset, maxScroll));
                    return true;
                }
            }
            case NODE_MAP -> {
                if (!nodeInfoList.isEmpty()) {
                    int totalEntries;
                    if (groupMode == 1) {
                        Set<String> blockNames = new LinkedHashSet<>();
                        for (SyncNetworkNodesPayload.NodeInfo info : nodeInfoList) {
                            blockNames.add(info.blockName());
                        }
                        totalEntries = nodeInfoList.size() + blockNames.size();
                    } else if (groupMode == 2) {
                        Set<String> labels = new LinkedHashSet<>();
                        for (SyncNetworkNodesPayload.NodeInfo info : nodeInfoList) {
                            labels.add(info.nodeLabel().isEmpty() ? "Unlabeled" : info.nodeLabel());
                        }
                        totalEntries = nodeInfoList.size() + labels.size();
                    } else {
                        totalEntries = nodeInfoList.size();
                    }
                    if (totalEntries > NODES_PER_PAGE) {
                        nodeMapScrollOffset -= (int) scrollY;
                        int maxScroll = Math.max(0, totalEntries - NODES_PER_PAGE);
                        nodeMapScrollOffset = Math.max(0, Math.min(nodeMapScrollOffset, maxScroll));
                        return true;
                    }
                }
            }
            default -> {}
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            switch (currentPage) {
                case NETWORK_LIST -> {
                    if (handleNetworkListClick(mouseX, mouseY)) return true;
                    if (selectedNetworkId != null && handleOptionButtonClick(mouseX, mouseY)) return true;
                }
                case ITEM_IO_GRAPH -> {
                    if (isBackButtonClicked(mouseX, mouseY)) {
                        currentPage = Page.NETWORK_LIST;
                        return true;
                    }
                }
                case NODE_MAP -> {
                    if (isBackButtonClicked(mouseX, mouseY)) {
                        currentPage = Page.NETWORK_LIST;
                        nodeInfoList.clear();
                        nodeMapScrollOffset = 0;
                        return true;
                    }
                    if (isGroupToggleClicked(mouseX, mouseY)) {
                        groupMode = (groupMode + 1) % GROUP_MODE_COUNT;
                        nodeMapScrollOffset = 0;
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean handleNetworkListClick(double mouseX, double mouseY) {
        for (int i = 0; i < NETWORKS_PER_PAGE && (i + networkScrollOffset) < networkList.size(); i++) {
            int index = i + networkScrollOffset;
            if (isHovering(NETWORK_LIST_X, NETWORK_LIST_Y + (i * NETWORK_ENTRY_HEIGHT),
                    NETWORK_LIST_WIDTH, NETWORK_ENTRY_HEIGHT - 2, (int) mouseX, (int) mouseY)) {
                SyncNetworkListPayload.NetworkEntry clickedEntry = networkList.get(index);
                if (clickedEntry.id().equals(selectedNetworkId)) {
                    selectedNetworkId = null;
                    selectedNetworkName = "";
                } else {
                    selectedNetworkId = clickedEntry.id();
                    selectedNetworkName = clickedEntry.name();
                }
                return true;
            }
        }
        return false;
    }

    private boolean handleOptionButtonClick(double mouseX, double mouseY) {
        int panelX = leftPos + DETAIL_PANEL_X;
        int panelY = topPos + DETAIL_PANEL_Y;

        int buttonsAreaY = panelY + 35;
        int totalButtonsHeight = (OPTION_BTN_HEIGHT * 2) + OPTION_BTN_GAP;
        int startBtnY = buttonsAreaY + ((DETAIL_PANEL_HEIGHT - 45) - totalButtonsHeight) / 2;
        int btnX = panelX + (DETAIL_PANEL_WIDTH - OPTION_BTN_WIDTH) / 2;

        // Button 1: Item I/O Graph
        int btn1Y = startBtnY;
        if (mouseX >= btnX && mouseX < btnX + OPTION_BTN_WIDTH
                && mouseY >= btn1Y && mouseY < btn1Y + OPTION_BTN_HEIGHT) {
            currentPage = Page.ITEM_IO_GRAPH;
            return true;
        }

        // Button 2: Node Map
        int btn2Y = startBtnY + OPTION_BTN_HEIGHT + OPTION_BTN_GAP;
        if (mouseX >= btnX && mouseX < btnX + OPTION_BTN_WIDTH
                && mouseY >= btn2Y && mouseY < btn2Y + OPTION_BTN_HEIGHT) {
            currentPage = Page.NODE_MAP;
            nodeMapScrollOffset = 0;
            groupMode = 0;
            // Request node data from server
            PacketDistributor.sendToServer(new RequestNetworkNodesPayload(selectedNetworkId));
            return true;
        }

        return false;
    }

    // ==================== DATA RECEIVERS ====================

    public void receiveNetworkList(List<SyncNetworkListPayload.NetworkEntry> networks) {
        System.out.println("[ComputerScreen] Received network list with " + networks.size() + " entries");
        for (SyncNetworkListPayload.NetworkEntry entry : networks) {
            System.out.println("[ComputerScreen]   - " + entry.name() + " (" + entry.nodeCount() + " nodes)");
        }
        this.networkList = new ArrayList<>(networks);
        this.networkScrollOffset = Math.min(this.networkScrollOffset,
                Math.max(0, networkList.size() - NETWORKS_PER_PAGE));
        System.out.println("[ComputerScreen] Network list updated, now have " + this.networkList.size() + " networks");
    }

    public void receiveNetworkNodes(UUID networkId, List<SyncNetworkNodesPayload.NodeInfo> nodes) {
        if (networkId.equals(selectedNetworkId)) {
            this.nodeInfoList = new ArrayList<>(nodes);
            this.nodeMapScrollOffset = 0;
        }
    }
}
