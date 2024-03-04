package studio.robotmonkey1000.boogereater.common.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import studio.robotmonkey1000.boogereater.BoogerMain;

import javax.annotation.Nullable;

public class EntityBoogerEater extends Monster {

  public EntityBoogerEater(EntityType<? extends Monster> EntType, Level world) {
    super(BoogerMain.BOOGEREATER.get(), world);
  }

  //Sets the HP of the Entity
  public void setHealth(float health) {
    super.setHealth(health);
  }

  //Defines the attributes like max health and damage
  public static AttributeSupplier.Builder setCustomAttributes() {
    return EntityBoogerEater.createMobAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.MOVEMENT_SPEED, 0.31D).add(Attributes.ATTACK_DAMAGE, 3.0D);
  }

  //Location for the loot table. I don't think I registered it though.
  public static final ResourceLocation EATER_TABLE = new ResourceLocation(BoogerMain.MOD_ID, "loot_tables/entities/booger_eater.json");

  @Nullable
  @Override
  protected ResourceLocation getDefaultLootTable() {
    return EATER_TABLE;
  }


  @Nullable
  @Override
  protected SoundEvent getAmbientSound() {
    return BoogerMain.IDLE.get();
  }

  @Override
  protected SoundEvent getDeathSound() {
    return BoogerMain.DEATH.get();
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return BoogerMain.HURT.get();
  }

  //Register the AI goals and targets
  @Override
  public void registerGoals() {
    this.applyEntityAI();
  }

  public void applyEntityAI() {
    this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
    this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.6D));
    this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
    this.goalSelector.addGoal(6, new FloatGoal(this));
    this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
    this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.2D, false));
  }


  @Override
  public boolean doHurtTarget(Entity entityIn) {
    return super.doHurtTarget(entityIn) && entityIn.hurt(this.damageSources().mobAttack(this), 2.0F);
  }

  @Override
  public boolean isBaby() {
    return true; // This is always true because booger eaters are always children
  }
}
