package studio.robotmonkey1000.boogereater.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import studio.robotmonkey1000.boogereater.BoogerMain;
import studio.robotmonkey1000.boogereater.common.configuration.ModConfigurations;
import studio.robotmonkey1000.boogereater.common.sound.BoogerSounds;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;

public class EntityBoogerEater extends MonsterEntity {

	public EntityBoogerEater(EntityType<? extends MonsterEntity> EntType, World world) {
		super(BoogerMain.BOOGEREATER.get(), world);
	}
	
	//Sets the HP of the Entity
	public void setHealth(float health) {
		super.setHealth(health);
	}
	
	//Defines the attributes like max health and damage
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return EntityBoogerEater.createMobAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.MOVEMENT_SPEED, 0.31D).add(Attributes.ATTACK_DAMAGE, 3.0D);
	}

	//Entity Data to store the message to be displayed on the speech bubble
	public static final DataParameter<String> MESSAGE = EntityDataManager.defineId(EntityBoogerEater.class, DataSerializers.STRING);
	
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
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData entityData, CompoundNBT nbt) {
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
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.6D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new SwimGoal(this));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
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
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		
		//Read Message From NBT Data
		setMessage(compound.getString("Message"));
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		
		//Save Message to NBT Data
		compound.putString("Message", getMessage());
	}
	
//
	@Override
	public IPacket<?> getAddEntityPacket() {
		return super.getAddEntityPacket();
	}
}
