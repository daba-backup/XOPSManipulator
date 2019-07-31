package com.daxie.xops.weapon;

import com.daxie.basis.vector.Vector;

public class WeaponData {
	private String name;
	private String model_filename;
	private String texture_filename;
	private int attack_power;
	private int penetration;
	private int firing_interval;
	private int bullet_speed;
	private int number_of_bullets;
	private int reloading_time;
	private int recoil;
	private int error_range_min;
	private int error_range_max;
	private Vector position;
	private Vector flash_position;
	private Vector cartridge_position;
	private Vector cartridge_velocity;
	private boolean rapid_fire_enabled_flag;
	private WeaponScopeMode scope_mode;
	private float scale;
	private int sound_id;
	private int sound_volume;
	private boolean suppressor_enabled_flag;
	private WeaponShootingStance shooting_stance;
	private int changeable_weapon;
	private int number_of_projectiles;
	
	public WeaponData() {
		name="";
		model_filename="";
		texture_filename="";
		attack_power=0;
		penetration=0;
		firing_interval=0;
		bullet_speed=0;
		number_of_bullets=0;
		reloading_time=0;
		recoil=0;
		error_range_min=0;
		error_range_max=0;
		position=new Vector();
		flash_position=new Vector();
		cartridge_position=new Vector();
		cartridge_velocity=new Vector();
		rapid_fire_enabled_flag=false;
		scope_mode=WeaponScopeMode.NONE;
		scale=1.0f;
		sound_id=0;
		sound_volume=0;
		suppressor_enabled_flag=false;
		shooting_stance=WeaponShootingStance.RIFLE;
		changeable_weapon=-1;
		number_of_projectiles=1;
	}
	public WeaponData(WeaponData w) {
		name=w.GetName();
		model_filename=w.GetModelFilename();
		texture_filename=w.GetTextureFilename();
		attack_power=w.GetAttackPower();
		penetration=w.GetPenetration();
		firing_interval=w.GetFiringInterval();
		bullet_speed=w.GetBulletSpeed();
		number_of_bullets=w.GetNumberOfBullets();
		reloading_time=w.GetReloadingTime();
		recoil=w.GetRecoil();
		error_range_min=w.GetErrorRangeMin();
		error_range_max=w.GetErrorRangeMax();
		position=w.GetPosition();
		flash_position=w.GetFlashPosition();
		cartridge_position=w.GetCartridgePosition();
		cartridge_velocity=w.GetCartridgeVelocity();
		rapid_fire_enabled_flag=w.GetRapidFireEnabledFlag();
		scope_mode=w.GetScopeMode();
		scale=w.GetScale();
		sound_id=w.GetSoundID();
		sound_volume=w.GetSoundVolume();
		suppressor_enabled_flag=w.GetSuppressorEnabledFlag();
		shooting_stance=w.GetShootingStance();
		changeable_weapon=w.GetChangeableWeapon();
		number_of_projectiles=w.GetNumberOfProjectiles();
	}
	
	@Override
	public String toString() {
		String ret="";
		String separator=System.getProperty("line.separator");
		
		ret+="name:"+name+separator;
		ret+="model_filename:"+model_filename+separator;
		ret+="texture_filename:"+texture_filename+separator;
		ret+="attack_power:"+attack_power+separator;
		ret+="penetration:"+penetration+separator;
		ret+="firing_interval:"+firing_interval+separator;
		ret+="bullet_speed:"+bullet_speed+separator;
		ret+="number_of_bullets:"+number_of_bullets+separator;
		ret+="reloading_time:"+reloading_time+separator;
		ret+="recoil:"+recoil+separator;
		ret+="error_range_min:"+error_range_min+separator;
		ret+="error_range_max:"+error_range_max+separator;
		ret+="position:"+position+separator;
		ret+="flash_position:"+flash_position+separator;
		ret+="cartridge_position:"+cartridge_position+separator;
		ret+="cartridge_velocity:"+cartridge_velocity+separator;
		ret+="rapid_fire_enabled_flag:"+rapid_fire_enabled_flag+separator;
		
		ret+="scope_mode:";
		ret+=scope_mode.toString().toLowerCase();
		ret+=separator;
		
		ret+="scale:"+scale+separator;
		ret+="sound_id:"+sound_id+separator;
		ret+="sound_volume:"+sound_volume+separator;
		ret+="suppressor_enabled_flag:"+suppressor_enabled_flag+separator;
		
		ret+="shooting_stance:";
		ret+=shooting_stance.toString().toLowerCase();
		ret+=separator;
	
		ret+="changeable_weapon:"+changeable_weapon+separator;
		ret+="number_of_projectiles:"+number_of_projectiles;
		
		return ret;
	}
	
	public void SetName(String name) {
		this.name=name;
	}
	public void SetModelFilename(String model_filename) {
		this.model_filename=model_filename;
	}
	public void SetTextureFilename(String texture_filename) {
		this.texture_filename=texture_filename;
	}
	public void SetAttackPower(int attack_power) {
		this.attack_power=attack_power;
	}
	public void SetPenetration(int penetration) {
		this.penetration=penetration;
	}
	public void SetFiringInterval(int firing_interval) {
		this.firing_interval=firing_interval;
	}
	public void SetBulletSpeed(int bullet_speed) {
		this.bullet_speed=bullet_speed;
	}
	public void SetNumberOfBullets(int number_of_bullets) {
		this.number_of_bullets=number_of_bullets;
	}
	public void SetReloadingTime(int reloading_time) {
		this.reloading_time=reloading_time;
	}
	public void SetRecoil(int recoil) {
		this.recoil=recoil;
	}
	public void SetErrorRangeMin(int error_range_min) {
		this.error_range_min=error_range_min;
	}
	public void SetErrorRangeMax(int error_range_max) {
		this.error_range_max=error_range_max;
	}
	public void SetPosition(Vector position) {
		this.position=position;
	}
	public void SetFlashPosition(Vector flash_position) {
		this.flash_position=flash_position;
	}
	public void SetCartridgePosition(Vector cartridge_position) {
		this.cartridge_position=cartridge_position;
	}
	public void SetCartridgeVelocity(Vector cartridge_velocity) {
		this.cartridge_velocity=cartridge_velocity;
	}
	public void SetRapidFireEnabledFlag(boolean rapid_fire_enabled_flag) {
		this.rapid_fire_enabled_flag=rapid_fire_enabled_flag;
	}
	public void SetScopeMode(WeaponScopeMode scope_mode) {
		this.scope_mode=scope_mode;
	}
	public void SetScale(float scale) {
		this.scale=scale;
	}
	public void SetSoundID(int sound_id) {
		this.sound_id=sound_id;
	}
	public void SetSoundVolume(int sound_volume) {
		this.sound_volume=sound_volume;
	}
	public void SetSuppressorEnabledFlag(boolean suppressor_enabled_flag) {
		this.suppressor_enabled_flag=suppressor_enabled_flag;
	}
	public void SetShootingStance(WeaponShootingStance shooting_stance) {
		this.shooting_stance=shooting_stance;
	}
	public void SetChangeableWeapon(int changeable_weapon) {
		this.changeable_weapon=changeable_weapon;
	}
	public void SetNumberOfProjectiles(int number_of_projectiles) {
		this.number_of_projectiles=number_of_projectiles;
	}
	
	public String GetName() {
		return name;
	}
	public String GetModelFilename() {
		return model_filename;
	}
	public String GetTextureFilename() {
		return texture_filename;
	}
	public int GetAttackPower() {
		return attack_power;
	}
	public int GetPenetration() {
		return penetration;
	}
	public int GetFiringInterval() {
		return firing_interval;
	}
	public int GetBulletSpeed() {
		return bullet_speed;
	}
	public int GetNumberOfBullets() {
		return number_of_bullets;
	}
	public int GetReloadingTime() {
		return reloading_time;
	}
	public int GetRecoil() {
		return recoil;
	}
	public int GetErrorRangeMin() {
		return error_range_min;
	}
	public int GetErrorRangeMax() {
		return error_range_max;
	}
	public Vector GetPosition() {
		return new Vector(position);
	}
	public Vector GetFlashPosition() {
		return new Vector(flash_position);
	}
	public Vector GetCartridgePosition() {
		return new Vector(cartridge_position);
	}
	public Vector GetCartridgeVelocity() {
		return new Vector(cartridge_velocity);
	}
	public boolean GetRapidFireEnabledFlag() {
		return rapid_fire_enabled_flag;
	}
	public WeaponScopeMode GetScopeMode() {
		return scope_mode;
	}
	public float GetScale() {
		return scale;
	}
	public int GetSoundID() {
		return sound_id;
	}
	public int GetSoundVolume() {
		return sound_volume;
	}
	public boolean GetSuppressorEnabledFlag() {
		return suppressor_enabled_flag;
	}
	public WeaponShootingStance GetShootingStance() {
		return shooting_stance;
	}
	public int GetChangeableWeapon() {
		return changeable_weapon;
	}
	public int GetNumberOfProjectiles() {
		return number_of_projectiles;
	}
}
