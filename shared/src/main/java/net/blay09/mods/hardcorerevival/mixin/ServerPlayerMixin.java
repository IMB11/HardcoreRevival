package net.blay09.mods.hardcorerevival.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Unique
    private Component prevComponent = null;

    @Inject(method = "sendSystemMessage(Lnet/minecraft/network/chat/Component;Z)V", at = @At("HEAD"), cancellable = true)
    public void skipDeathAttackPlayerIfExecuted(Component $$0, boolean $$1, CallbackInfo ci) {
        if (prevComponent != null && prevComponent.copy().getContents() instanceof TranslatableContents contents) {
            if ($$0.copy().getContents() instanceof TranslatableContents prevContents) {
                // If prev = death.attack.executed or death.attack.executed.player
                // and current = death.attack.player
                if (contents.getKey().equals("death.attack.executed") || contents.getKey().equals("death.attack.executed.player")) {
                    ci.cancel();
                }
            }
        }

        prevComponent = $$0;
    }
}
