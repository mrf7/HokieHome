package data;

import controllers.LightController;

public class Light {
	private int id;
	private int brightness;
	private static LightController controller = LightController.getInstance();

	// Create a new Light with given fields
	public Light(int id) {
		this.id = id;
		brightness = 0;
	}

	// Sets the brightness of the light using the light controller
	public void setBrightness(int bright) {
		controller.setBrightness(this, bright);
		brightness = bright;
	}

	// Turns brightness to max
	public void turnOn() {
		setBrightness(100);
	}

	// Turns light off
	public void turnOff() {
		setBrightness(0);
	}

	// Getters
	public int getBrightness() {
		return brightness;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Light " + id + " Brightness: " + brightness;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() == this.getClass()) {
			Light comp = (Light) o;
			return this.id == comp.getId();
		}
		return false;
	}

}
