package io.github.nul00000000.agent;

import io.github.nul00000000.Input;
import io.github.nul00000000.Main;

public class PlayerAgent extends AgentBase {

	public PlayerAgent() {
		super(Main.WIDTH / 2, Main.HEIGHT / 2, 0);
	}

	@Override
	public void act() {
		this.angle = Math.atan2(Input.mouseY - this.y, Input.mouseX - this.x);
		this.dx += (Input.mouseX - this.x) * 0.01;
		this.dy += (Input.mouseY - this.y) * 0.01;
	}

}
