package com.cloudgarden.jigloo.typewise;

/*
 * Licensed Material - Property of PhilemonWorks B.V.
 * 
 * (c) Copyright PhilemonWorks 2004 - All rights reserved.
 * Use, duplication, distribution or disclosure restricted. 
 * See http://www.philemonworks.com for information.
 * 
 * VERSION HISTORY
 * 25-apr-04: created
 *
 */

import java.awt.Color;

import com.philemonworks.typewise.UIBuilder;
import com.philemonworks.typewise.cwt.Button;
import com.philemonworks.typewise.cwt.Label;
import com.philemonworks.typewise.cwt.Screen;
import com.philemonworks.typewise.server.ApplicationModel;

/**
 * @author emicklei
 *
 */
public class Calculator extends ApplicationModel {

	public static void main(String[] args) {
		// for development only
		new Calculator().mainScreen().preview();
	}

	public Screen mainScreen() {
		UIBuilder ui = new UIBuilder();
		ui.addScreen("Calculator", this, 30, 30);
		ui.setLeftEdge(2);
		ui.setTopEdge(2);
		Label display = ui.addLabel("display", "0,", 28);
		display.setAlignment(Label.ALIGNMENT_RIGHT);
		display.setBackground(Color.black);
		display.setForeground(Color.white);

		ui.cr(2);
		this.addDigitsTo(ui);
		this.addSignAndDotTo(ui);

		return ui.topScreen();
	}
	private void addSignAndDotTo(UIBuilder ui) {
		ui.setPosition(11, 9);
		Button sign = (Button) ui.addButton("sign", "+/-", 3);
		sign.onSendTo(EVENT_CLICKED, "pressedSign", this);
		sign.setBackground(Color.white);
		sign.setForeground(Color.green.darker());

		ui.space(2);
		Button dot = (Button) ui.addButton("dot", ".", 3);
		dot.onSendTo(EVENT_CLICKED,"pressedDot", this);
		dot.setBackground(Color.white);
		dot.setForeground(Color.green.darker());
	}

	private void addDigitsTo(UIBuilder ui) {
		ui.setTopEdge(5);
		ui.setLeftEdge(4);
		// positive number buttons
		for (int n = 0; n < 3; n++) {
			for (int m = 0; m < 3; m++) {
				int num = 7 + m - (3 * n);
				String key = String.valueOf(num);
				Button button = (Button) ui.addButton(key, key, 3);
				button.setBackground(Color.yellow);
				button.setForeground(Color.blue);
				button.onSendToWith(EVENT_CLICKED, "pressedKey", this, new Integer(num));
				ui.space(2);
			}
			ui.cr(2);
		}
		Button button = (Button) ui.addButton("0", "0", 3);
		button.setBackground(Color.yellow);
		button.setForeground(Color.blue);
		button.onSendToWith(Button.EVENT_CLICKED,"pressedKey", this, new Integer(0));
	}

}
