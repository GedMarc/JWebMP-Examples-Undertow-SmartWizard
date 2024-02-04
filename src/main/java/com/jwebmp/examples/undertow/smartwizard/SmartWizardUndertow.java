/*
 * Copyright (C) 2017 GedMarc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jwebmp.examples.undertow.smartwizard;

import com.jwebmp.core.Page;
import com.jwebmp.core.base.html.Div;
import com.jwebmp.core.base.html.SmallText;
import com.guicedee.guicedinjection.GuiceContext;

import com.jwebmp.plugins.smartwizard4.SmartWizard;
import com.jwebmp.plugins.smartwizard4.SmartWizardStep;
import com.jwebmp.plugins.smartwizard4.SmartWizardStepItem;
import com.jwebmp.plugins.smartwizard4.SmartWizardThemes;
import com.jwebmp.plugins.smartwizard4.options.SmartWizardTransitionEffects;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import jakarta.servlet.ServletException;
import java.util.logging.Level;

public class SmartWizardUndertow
		extends Page
{
	public SmartWizardUndertow()
	{
		super("JWebSwing - Smart Wizard Demo");

		SmartWizard sw = new SmartWizard("test");
		sw.getSteps()
		  .add(new SmartWizardStep(new Div<>().add("Content 1"), new SmartWizardStepItem("Header", new SmallText("Description 1"))));
		sw.getSteps()
		  .add(new SmartWizardStep(new Div<>().add("Content 2"), new SmartWizardStepItem("Header 2", new SmallText("Description 2"))));
		sw.getSteps()
		  .add(new SmartWizardStep(new Div<>().add("Content 3"), new SmartWizardStepItem("Header 3", new SmallText("Description 3"))));
		getBody().add(sw);

		SmartWizard sw2 = new SmartWizard("testme");
		sw2.getFeature()
		   .getOptions()
		   .setContentCache(true);
		sw2.getFeature()
		   .getOptions()
		   .setCycleSteps(true);
		sw2.getFeature()
		   .getOptions()
		   .getLang()
		   .setNext("Next Text");
		sw2.getFeature()
		   .getOptions()
		   .setContentCache(true);

		sw2.getFeature()
		   .getOptions()
		   .setTheme(SmartWizardThemes.Circles);
		sw2.getFeature()
		   .getOptions()
		   .setTransitionEffect(SmartWizardTransitionEffects.slide);

		sw2.getSteps()
		   .add(new SmartWizardStep(new Div<>().add("Content 1"), new SmartWizardStepItem("Header", new SmallText("Description 1"))));
		sw2.getSteps()
		   .add(new SmartWizardStep(new Div<>().add("Content 2"), new SmartWizardStepItem("Header 2", new SmallText("Description 2"))));
		sw2.getSteps()
		   .add(new SmartWizardStep(new Div<>().add("Content 3"), new SmartWizardStepItem("Header 3", new SmallText("Description 3"))));

		getBody().add(sw2);
	}

	/**
	 * This part runs the web site :)
	 *
	 * @param args
	 *
	 * @throws ServletException
	 */
	public static void main(String[] args) throws ServletException
	{
		LogFactory.configureConsoleColourOutput(Level.FINE);

		DeploymentInfo servletBuilder = Servlets.deployment()
		                                        .setClassLoader(SmartWizardUndertow.class.getClassLoader())
		                                        .setContextPath("/")
		                                        .setDeploymentName("smartwizarddemo.war");

		DeploymentManager manager = Servlets.defaultContainer()
		                                    .addDeployment(servletBuilder);

		manager.deploy();
		GuiceContext.inject();

		HttpHandler jwebSwingHandler = manager.start();

		Undertow server = Undertow.builder()
		                          .addHttpListener(6002, "localhost")
		                          .setHandler(jwebSwingHandler)
		                          .build();

		server.start();
	}
}
