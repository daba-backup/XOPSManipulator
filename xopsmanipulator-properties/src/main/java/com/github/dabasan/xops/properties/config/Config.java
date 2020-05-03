package com.github.dabasan.xops.properties.config;

/**
 * Config
 * 
 * @author Daba
 *
 */
public class Config {
	private KeyCode turn_up;
	private KeyCode turn_down;
	private KeyCode turn_left;
	private KeyCode turn_right;
	private KeyCode move_forward;
	private KeyCode move_backward;
	private KeyCode move_left;
	private KeyCode move_right;
	private KeyCode walk;
	private KeyCode jump;
	private KeyCode reload;
	private KeyCode drop_weapon;
	private KeyCode zoom;
	private KeyCode fire_mode;
	private KeyCode switch_weapon;
	private KeyCode weapon1;
	private KeyCode weapon2;
	private KeyCode fire;

	private int mouse_sensitivity;
	private int brightness;
	private WindowMode window_mode;
	private boolean enable_sound;
	private boolean enable_blood;
	private boolean invert_mouse;
	private boolean frame_skip;
	private boolean another_gunsight;
	private String name;

	public Config() {
		this.SetDefault();
	}
	public Config(Config c) {
		turn_up = c.GetTurnUp();
		turn_down = c.GetTurnDown();
		turn_left = c.GetTurnLeft();
		turn_right = c.GetTurnRight();
		move_forward = c.GetMoveForward();
		move_backward = c.GetMoveBackward();
		move_left = c.GetMoveLeft();
		move_right = c.GetMoveRight();
		walk = c.GetWalk();
		jump = c.GetJump();
		reload = c.GetReload();
		drop_weapon = c.GetDropWeapon();
		zoom = c.GetZoom();
		fire_mode = c.GetFireMode();
		switch_weapon = c.GetSwitchWeapon();
		weapon1 = c.GetWeapon1();
		weapon2 = c.GetWeapon2();
		fire = c.GetFire();

		mouse_sensitivity = c.GetMouseSensitivity();
		brightness = c.GetBrightness();
		window_mode = c.GetWindowMode();
		enable_sound = c.GetEnableSound();
		enable_blood = c.GetEnableBlood();
		invert_mouse = c.GetInvertMouse();
		frame_skip = c.GetFrameSkip();
		another_gunsight = c.GetAnotherGunsight();
		name = c.GetName();
	}
	public void SetDefault() {
		turn_up = KeyCode.KEY_UP;
		turn_down = KeyCode.KEY_DOWN;
		turn_left = KeyCode.KEY_LEFT;
		turn_right = KeyCode.KEY_RIGHT;
		move_forward = KeyCode.KEY_W;
		move_backward = KeyCode.KEY_S;
		move_left = KeyCode.KEY_A;
		move_right = KeyCode.KEY_D;
		walk = KeyCode.KEY_TAB;
		jump = KeyCode.KEY_SPACE;
		reload = KeyCode.KEY_R;
		drop_weapon = KeyCode.KEY_G;
		zoom = KeyCode.KEY_SHIFT;
		fire_mode = KeyCode.KEY_X;
		switch_weapon = KeyCode.KEY_Z;
		weapon1 = KeyCode.KEY_1;
		weapon2 = KeyCode.KEY_2;
		fire = KeyCode.KEY_MOUSE_L;

		mouse_sensitivity = 25;
		brightness = 4;
		window_mode = WindowMode.FULL_SCREEN;
		enable_sound = true;
		enable_blood = true;
		invert_mouse = false;
		frame_skip = false;
		another_gunsight = false;
		name = "xopsplayer";
	}

	@Override
	public String toString() {
		String ret = "";
		final String separator = System.getProperty("line.separator");

		ret += "turn_up:" + turn_up + separator;
		ret += "turn_down:" + turn_down + separator;
		ret += "turn_left:" + turn_left + separator;
		ret += "turn_right:" + turn_right + separator;
		ret += "move_forward:" + move_forward + separator;
		ret += "move_backward:" + move_backward + separator;
		ret += "move_left:" + move_left + separator;
		ret += "move_right:" + move_right + separator;
		ret += "walk:" + walk + separator;
		ret += "jump:" + jump + separator;
		ret += "reload:" + reload + separator;
		ret += "drop_weapon:" + drop_weapon + separator;
		ret += "zoom:" + zoom + separator;
		ret += "fire_mode:" + fire_mode + separator;
		ret += "switch_weapon:" + switch_weapon + separator;
		ret += "weapon1:" + weapon1 + separator;
		ret += "weapon2:" + weapon2 + separator;
		ret += "fire:" + fire + separator;
		ret += "mouse_sensitivity:" + mouse_sensitivity + separator;
		ret += "brightness:" + brightness + separator;
		ret += "window_mode:" + window_mode + separator;
		ret += "enable_sound:" + enable_sound + separator;
		ret += "enable_blood:" + enable_blood + separator;
		ret += "invert_mouse:" + invert_mouse + separator;
		ret += "frame_skip:" + frame_skip + separator;
		ret += "another_gunsight:" + another_gunsight + separator;
		ret += "name:" + name;

		return ret;
	}

	public KeyCode GetTurnUp() {
		return turn_up;
	}
	public KeyCode GetTurnDown() {
		return turn_down;
	}
	public KeyCode GetTurnLeft() {
		return turn_left;
	}
	public KeyCode GetTurnRight() {
		return turn_right;
	}
	public KeyCode GetMoveForward() {
		return move_forward;
	}
	public KeyCode GetMoveBackward() {
		return move_backward;
	}
	public KeyCode GetMoveLeft() {
		return move_left;
	}
	public KeyCode GetMoveRight() {
		return move_right;
	}
	public KeyCode GetWalk() {
		return walk;
	}
	public KeyCode GetJump() {
		return jump;
	}
	public KeyCode GetReload() {
		return reload;
	}
	public KeyCode GetDropWeapon() {
		return drop_weapon;
	}
	public KeyCode GetZoom() {
		return zoom;
	}
	public KeyCode GetFireMode() {
		return fire_mode;
	}
	public KeyCode GetSwitchWeapon() {
		return switch_weapon;
	}
	public KeyCode GetWeapon1() {
		return weapon1;
	}
	public KeyCode GetWeapon2() {
		return weapon2;
	}
	public KeyCode GetFire() {
		return fire;
	}
	public int GetMouseSensitivity() {
		return mouse_sensitivity;
	}
	public int GetBrightness() {
		return brightness;
	}
	public WindowMode GetWindowMode() {
		return window_mode;
	}
	public boolean GetEnableSound() {
		return enable_sound;
	}
	public boolean GetEnableBlood() {
		return enable_blood;
	}
	public boolean GetInvertMouse() {
		return invert_mouse;
	}
	public boolean GetFrameSkip() {
		return frame_skip;
	}
	public boolean GetAnotherGunsight() {
		return another_gunsight;
	}
	public String GetName() {
		return name;
	}

	public void SetTurnUp(KeyCode turn_up) {
		this.turn_up = turn_up;
	}
	public void SetTurnDown(KeyCode turn_down) {
		this.turn_down = turn_down;
	}
	public void SetTurnLeft(KeyCode turn_left) {
		this.turn_left = turn_left;
	}
	public void SetTurnRight(KeyCode turn_right) {
		this.turn_right = turn_right;
	}
	public void SetMoveForward(KeyCode move_forward) {
		this.move_forward = move_forward;
	}
	public void SetMoveBackward(KeyCode move_backward) {
		this.move_backward = move_backward;
	}
	public void SetMoveLeft(KeyCode move_left) {
		this.move_left = move_left;
	}
	public void SetMoveRight(KeyCode move_right) {
		this.move_right = move_right;
	}
	public void SetWalk(KeyCode walk) {
		this.walk = walk;
	}
	public void SetJump(KeyCode jump) {
		this.jump = jump;
	}
	public void SetReload(KeyCode reload) {
		this.reload = reload;
	}
	public void SetDropWeapon(KeyCode drop_weapon) {
		this.drop_weapon = drop_weapon;
	}
	public void SetZoom(KeyCode zoom) {
		this.zoom = zoom;
	}
	public void SetFireMode(KeyCode fire_mode) {
		this.fire_mode = fire_mode;
	}
	public void SetSwitchWeapon(KeyCode switch_weapon) {
		this.switch_weapon = switch_weapon;
	}
	public void SetWeapon1(KeyCode weapon1) {
		this.weapon1 = weapon1;
	}
	public void SetWeapon2(KeyCode weapon2) {
		this.weapon2 = weapon2;
	}
	public void SetFire(KeyCode fire) {
		this.fire = fire;
	}
	public void SetMouseSensitivity(int mouse_sensitivity) {
		this.mouse_sensitivity = mouse_sensitivity;
	}
	public void SetBrightness(int brightness) {
		this.brightness = brightness;
	}
	public void SetWindowMode(WindowMode window_mode) {
		this.window_mode = window_mode;
	}
	public void SetEnableSound(boolean enable_sound) {
		this.enable_sound = enable_sound;
	}
	public void SetEnableBlood(boolean enable_blood) {
		this.enable_blood = enable_blood;
	}
	public void SetInvertMouse(boolean invert_mouse) {
		this.invert_mouse = invert_mouse;
	}
	public void SetFrameSkip(boolean frame_skip) {
		this.frame_skip = frame_skip;
	}
	public void SetAnotherGunsight(boolean another_gunsight) {
		this.another_gunsight = another_gunsight;
	}
	public void SetName(String name) {
		this.name = name;
	}
}
