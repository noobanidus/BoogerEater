package studio.robotmonkey1000.boogereater.common.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import studio.robotmonkey1000.boogereater.BoogerMain;
import studio.robotmonkey1000.boogereater.common.configuration.ModConfigurations;
import studio.robotmonkey1000.boogereater.common.sound.BoogerSounds;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;

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

	//Entity Data to store the message to be displayed on the speech bubble
	public static final EntityDataAccessor<String> MESSAGE = SynchedEntityData.defineId(EntityBoogerEater.class, EntityDataSerializers.STRING);
	
	//Location for the loot table. I don't think I registered it though.
	public static final ResourceLocation EATER_TABLE = new ResourceLocation(BoogerMain.MOD_ID, "loot_tables/entities/booger_eater.json");	
	
	@Nullable
	@Override
	protected ResourceLocation getDefaultLootTable()
	{
		return EATER_TABLE;
	}
	

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return BoogerSounds.booger_eater_idle;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return BoogerSounds.booger_eater_death;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return BoogerSounds.booger_eater_hurt;
	}

	@Nullable
	@Override
	//Picks a random message to be added to the booger eater when they spawn
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData entityData, CompoundTag nbt) {
		this.setMessage(ModConfigurations.BOOGER_CONFIG.BoogerSayings.get(random.nextInt(ModConfigurations.BOOGER_CONFIG.BoogerSayings.size())));
		return super.finalizeSpawn(world, difficulty, reason, entityData, nbt);
	}


	//Gets the entities message from the datamanager
	public String getMessage() {
		return entityData.get(MESSAGE);
	}

	//Sets the entities message
	public void setMessage(String data) {
		entityData.set(MESSAGE, data);
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
		return super.doHurtTarget(entityIn) && entityIn.hurt(DamageSource.mobAttack(this), 2.0F);
	}

	@Override
	public boolean isBaby() {
		return true; // This is always true because booger eaters are always children
	}


	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(MESSAGE, "");
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		
		//Read Message From NBT Data
		setMessage(compound.getString("Message"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		
		//Save Message to NBT Data
		compound.putString("Message", getMessage());
	}
	
//
	@Override
	public Packet<?> getAddEntityPacket() {
		return super.getAddEntityPacket();
	}
}
