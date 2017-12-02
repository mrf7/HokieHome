package data;

import org.json.JSONException;
import org.json.JSONObject;

import controllers.LightController;

public class Light {
	private int id; // This replaces the ip address of the light to ensure the identifier doesnt change
	private int brightness;
	private static LightController controller = LightController.getInstance();

	// Create a new Light with given fields
	public Light(int id) {
		this.id = id;
		brightness = 0;
	}

	// Sets the brightness of the light using the light controller
	public boolean setBrightness(int bright) {
		if (controller.setBrightness(id, bright)) {
			brightness = bright;
			return true;
		} else {
			return false;
		}
		
	}

	// Turns brightness to max
	public boolean turnOn() {
		return setBrightness(100);
	}

	// Turns light off
	public boolean turnOff() {
		return setBrightness(0);
	}

	// Getters
	public int getBrightness() {
		return brightness;
	}

	public int getId() {
		return id;
	}
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("brightness", brightness);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return json;
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
