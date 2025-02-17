package org.samo_lego.taterzens.commands.edit;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import org.samo_lego.taterzens.commands.NpcCommand;
import org.samo_lego.taterzens.npc.TaterzenNPC;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Pose;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static org.samo_lego.taterzens.Taterzens.config;
import static org.samo_lego.taterzens.compatibility.LoaderSpecific.permissions$checkPermission;
import static org.samo_lego.taterzens.util.TextUtil.joinText;
import static org.samo_lego.taterzens.util.TextUtil.successText;

public class TagsCommand {
    public static void registerNode(LiteralCommandNode<CommandSourceStack> editNode) {
        LiteralCommandNode<CommandSourceStack> tagsNode = literal("tags")
                .requires(src -> permissions$checkPermission(src, "taterzens.npc.edit.tags", config.perms.npcCommandPermissionLevel))
                .then(literal("leashable")
                        .requires(src -> permissions$checkPermission(src, "taterzens.npc.edit.tags.leashable", config.perms.npcCommandPermissionLevel))
                        .then(argument("leashable", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    boolean leashable = BoolArgumentType.getBool(ctx, "leashable");
                                    return setTag(ctx, "leashable", leashable, npc -> npc.setLeashable(leashable));
                                })
                        )
                )
                .then(literal("pushable")
                        .requires(src -> permissions$checkPermission(src, "taterzens.npc.edit.tags.pushable", config.perms.npcCommandPermissionLevel))
                        .then(argument("pushable", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    boolean pushable = BoolArgumentType.getBool(ctx, "pushable");
                                    return setTag(ctx, "pushable", pushable, npc -> npc.setPushable(pushable));
                                })
                        )
                )
                .then(literal("jumpWhileAttacking")
                        .requires(src -> permissions$checkPermission(src, "taterzens.npc.edit.tags.jump_while_attacking", config.perms.npcCommandPermissionLevel))
                        .then(argument("perform jumps", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    boolean jumpWhileAttacking = BoolArgumentType.getBool(ctx, "perform jumps");
                                    return setTag(ctx, "jumpWhileAttacking", jumpWhileAttacking, npc -> npc.setPerformAttackJumps(jumpWhileAttacking));
                                })
                        )
                )
                .then(literal("allowEquipmentDrops")
                        .requires(src -> permissions$checkPermission(src, "taterzens.npc.edit.equipment.drops", config.perms.npcCommandPermissionLevel))
                        .then(argument("drop", BoolArgumentType.bool()).executes(EquipmentCommand::setEquipmentDrops))
                )
                .then(literal("sneakNameType")
                        .requires(src -> permissions$checkPermission(src, "taterzens.npc.edit.tags.sneakNameType", config.perms.npcCommandPermissionLevel))
                        .then(argument("sneak type name", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    boolean sneakNameType = BoolArgumentType.getBool(ctx, "sneak type name");
                                    return setTag(ctx, "sneakNameType", sneakNameType, npc -> npc.setShiftKeyDown(sneakNameType));
                                })
                        )
                )
                .then(literal("allowSounds")
                        .requires(src -> permissions$checkPermission(src, "taterzens.npc.edit.tags.allow_sounds", config.perms.npcCommandPermissionLevel))
                        .then(argument("allow sounds", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    boolean allowSounds = BoolArgumentType.getBool(ctx, "allow sounds");
                                    return setTag(ctx, "allowSounds", allowSounds, npc -> npc.setAllowSounds(allowSounds));
                                })
                        )
                )
                .then(literal("showCustomName")
                    .requires(src -> permissions$checkPermission(src, "taterzens.npc.edit.tags.allow_sounds", config.perms.npcCommandPermissionLevel))
                    .then(argument("show custom name", BoolArgumentType.bool())
                            .executes(TagsCommand::editNameVisibility)
                    )
                )
                .build();

        editNode.addChild(tagsNode);
    }

    private static int editNameVisibility(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean showName = BoolArgumentType.getBool(context, "show custom name");
        CommandSourceStack source = context.getSource();
        final Pose POSE = Pose.SPIN_ATTACK;
        return setTag(context, "showCustomName", showName, npc -> {
            npc.setPose(showName ? Pose.STANDING : POSE);

            String oldName = npc.getName().getString();
            npc.setShiftKeyDown(!showName);

            if(!showName) {
                String newName = String.valueOf(oldName.toCharArray()[0]);
                npc.setCustomName(new TextComponent(newName));

                source.sendSuccess(
                        joinText("taterzens.command.tags.hide_name_hint.desc.1", ChatFormatting.GOLD, ChatFormatting.BLUE, newName, POSE.toString())
                                .append("\n")
                                .append(joinText("taterzens.command.tags.hide_name_hint.desc.2", ChatFormatting.GOLD, ChatFormatting.BLUE, oldName))
                                .withStyle(ChatFormatting.GOLD),
                        false
                );
            }

        });
    }

    private static int setTag(CommandContext<CommandSourceStack> context, String flagName, boolean flagValue, Consumer<TaterzenNPC> flag) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        return NpcCommand.selectedTaterzenExecutor(source.getEntityOrException(), taterzen -> {
            flag.accept(taterzen);
            source.sendSuccess(successText("taterzens.command.tags.changed", flagName, String.valueOf(flagValue)), false);
        });
    }
}
