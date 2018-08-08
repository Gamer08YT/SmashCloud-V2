package net.smashmc.eu.utils.report;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReportItemBuilder {

    private ItemStack itemStack;
    /**
     * Constructor with params:
     *
     * @param material Material for the item
     */
    public ReportItemBuilder(Material material) {
        itemStack = new ItemStack(material);
    }

    /**
     * Constructor with params:
     *
     * @param material create a item with Material
     * @param amount   how many items you want to create
     * @param subid    set the item a subid
     */
    public ReportItemBuilder(Material material, int amount, int subid) {
        itemStack = new ItemStack(material, amount, (short) subid);
    }

    /**
     * Constructor with params:
     *
     * @param id     create a item with id
     * @param amount how many items you want to create
     * @param subid  set the item a subid
     */
    public ReportItemBuilder(int id, int amount, int subid) {
        itemStack = new ItemStack(id, amount, (short) subid);
    }

    /**
     * Set the Displayname with params:
     *
     * @param display the name which will be displayed
     * @return
     */
    public ReportItemBuilder setDisplayName(String display) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(display);
        itemStack.setItemMeta(meta);
        return this;
    }


    /**
     * create your item
     *
     * @return
     */
    public ItemStack build() {
        return itemStack;
    }
}

