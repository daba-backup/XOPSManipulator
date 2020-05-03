package com.github.dabasan.xops.properties;

import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.xms.ids.IDSManipulator;

public class WriteDataToIDS {
	public static void main(String[] args) {
		IDSManipulator manipulator=new IDSManipulator();
		WeaponData weapon_data=new WeaponData();
		
		weapon_data.SetName("TestWeapon");
		weapon_data.SetAttackPower(100);
		weapon_data.SetPosition(VectorFunctions.VGet(50.0f, 45.0f, -10.0f));
		weapon_data.SetRapidFireEnabledFlag(true);
		weapon_data.SetSuppressorEnabledFlag(true);
		
		manipulator.SetWeaponData(weapon_data);
		
		manipulator.Write("./TestData/test_weapon.ids");
	}
}
