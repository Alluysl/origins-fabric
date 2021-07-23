package io.github.apace100.origins.power;

import io.github.apace100.origins.Origins;
import io.github.ladysnake.pal.PlayerAbility;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerAbilityPower extends Power {

    PlayerAbility ability;

    public PlayerAbilityPower(PowerType<Power> type, PlayerEntity player, PlayerAbility ability){
        super(type, player);
        this.ability = ability;
        setTicking(true);
    }

    @Override
    public void tick() {
        if(!player.world.isClient) {
            boolean isActive = isActive();
            boolean hasAbility = hasAbility();
            if(isActive && !hasAbility) {
                grantAbility();
            } else if(!isActive && hasAbility) {
                revokeAbility();
            }
        }
    }

    // Origins' onChosen is not as consistent as Apoli's onGained so hopefully tick does the job and I don't need to add onAdded/onRespawn callbacks

    @Override
    public void onRemoved() {
        if(!player.world.isClient && hasAbility()) {
            revokeAbility();
        }
    }

    public boolean hasAbility() {
        return Origins.POWER_SOURCE.grants(player, ability);
    }

    public void grantAbility() {
        //Apoli.SCHEDULER.queue(server -> {
        Origins.POWER_SOURCE.grantTo(player, ability);
        //   Apoli.POWER_SOURCE.grantTo((PlayerEntity)entity, VanillaAbilities.FLYING);
        //}, 1);
    }

    public void revokeAbility() {
        Origins.POWER_SOURCE.revokeFrom(player, ability);
    }
}
