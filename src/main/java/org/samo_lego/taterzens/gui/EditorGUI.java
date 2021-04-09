package org.samo_lego.taterzens.gui;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import eu.pb4.sgui.ClickType;
import eu.pb4.sgui.GuiElement;
import eu.pb4.sgui.SimpleGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class EditorGUI extends SimpleGui {
    public EditorGUI(CommandContext<ServerCommandSource> context, ServerPlayerEntity player, CommandNode<ServerCommandSource> parentNode, EditorGUI previousScreen) {
        super(ScreenHandlerType.GENERIC_9X6, player, true);
        this.setTitle(new LiteralText("NPC editing " + parentNode.getName()));
        this.setAutoUpdate(true);


        ItemStack back = new ItemStack(Items.MAGENTA_GLAZED_TERRACOTTA);
        back.setCustomName(new LiteralText("Back"));
        this.setSlot(52, back, (i, clickType, slotActionType) -> {
            if(previousScreen == null)
                player.closeHandledScreen();
            else
                previousScreen.open();
        });

        ItemStack close = new ItemStack(Items.BARRIER);
        close.setCustomName(new LiteralText("Close"));
        this.setSlot(53, back, (i, clickType, slotActionType) -> {
            player.closeHandledScreen();
        });

        AtomicInteger i = new AtomicInteger();
        parentNode.getChildren().forEach(node -> {
            ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
            stack.setCustomName(new LiteralText(node.getName()));
            this.setSlot(i.getAndIncrement(), new GuiElement(stack, (index, clickType, slotActionType) -> {
                ItemStack item = this.getSlot(index).getItem();
                if (clickType == ClickType.MOUSE_LEFT) {
                    Collection<CommandNode<ServerCommandSource>> children = node.getChildren();
                    if(!children.isEmpty()) {
                        EditorGUI childGUI = new EditorGUI(context, player, node, this);
                        this.close();
                        childGUI.open();
                    } else {
                        try {
                            player.closeHandledScreen();
                            node.getCommand().run(context);
                        } catch(CommandSyntaxException e) {
                            player.sendMessage(new LiteralText(e.getMessage()), false);
                        }
                    }
                } else if(clickType == ClickType.MOUSE_RIGHT) {
                    try {
                        player.closeHandledScreen();
                        node.getCommand().run(context);
                    } catch(CommandSyntaxException e) {
                        player.sendMessage(new LiteralText(e.getMessage()), false);
                    }
                }
                this.updateSlot(index, item);
            }));
        });
    }
}
