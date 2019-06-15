package com.daxie.xops.character;

import java.util.HashMap;
import java.util.Map;

public class CharacterData {
	private CharacterModelType model_type;
	private CharacterTextureType texture_type;
	private int hp;
	private CharacterAILevel ai_level;
	private Map<Integer, Integer> weapon_ids_map;
	private CharacterType type;
	
	public CharacterData() {
		model_type=CharacterModelType.MALE;
		texture_type=CharacterTextureType.SOLDIER_BLACK;
		hp=100;
		ai_level=CharacterAILevel.D;
		
		weapon_ids_map=new HashMap<>();
		weapon_ids_map.put(0, 0);
		weapon_ids_map.put(1, 0);
		
		type=CharacterType.HUMAN;
	}
	public CharacterData(CharacterData c) {
		model_type=c.GetModelType();
		texture_type=c.GetTextureType();
		hp=c.GetHP();
		ai_level=c.GetAILevel();
		weapon_ids_map=c.GetWeaponIDsMap();
		type=c.GetType();
	}
	
	@Override
	public String toString() {
		String ret="";
		String separator=System.getProperty("line.separator");
		
		ret+="model_type:";
		ret+=model_type.toString().toLowerCase();
		ret+=separator;
		
		ret+="texture_type:";
		ret+=texture_type.toString().toLowerCase();
		ret+=separator;
		
		ret+="hp:"+hp+separator;
		
		ret+="ai_level:";
		ret+=ai_level.toString().toLowerCase();
		ret+=separator;
		
		ret+="weapon_ids:"+separator;
		for(int i=0;i<weapon_ids_map.size();i++) {
			if(weapon_ids_map.containsKey(i)==false)continue;
			
			int weapon_id=weapon_ids_map.get(i);
			ret+=weapon_id+separator;
		}
		
		ret+="type:";
		ret+=type.toString().toLowerCase();
		
		return ret;
	}
	
	public void SetModelType(CharacterModelType model_type) {
		this.model_type=model_type;
	}
	public void SetTextureType(CharacterTextureType texture_type) {
		this.texture_type=texture_type;
	}
	public void SetHP(int hp) {
		this.hp=hp;
	}
	public void SetAILevel(CharacterAILevel ai_level) {
		this.ai_level=ai_level;
	}
	public void SetWeaponID(int i,int weapon_id) {
		weapon_ids_map.put(i, weapon_id);
	}
	public void SetType(CharacterType type) {
		this.type=type;
	}
	
	public CharacterModelType GetModelType() {
		return model_type;
	}
	public CharacterTextureType GetTextureType() {
		return texture_type;
	}
	public int GetHP() {
		return hp;
	}
	public CharacterAILevel GetAILevel() {
		return ai_level;
	}
	public int GetWeaponID(int index) {
		if(weapon_ids_map.containsKey(index)==false)return 0;
		return weapon_ids_map.get(index);
	}
	public Map<Integer, Integer> GetWeaponIDsMap() {
		return new HashMap<>(weapon_ids_map);
	}
	public CharacterType GetType() {
		return type;
	}
	
	public boolean WeaponIDExists(int index) {
		return weapon_ids_map.containsKey(index);
	}
}
