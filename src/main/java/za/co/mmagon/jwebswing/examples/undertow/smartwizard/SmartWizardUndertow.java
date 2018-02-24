package za.co.mmagon.jwebswing.examples.undertow.smartwizard;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import za.co.mmagon.guiceinjection.GuiceContext;
import za.co.mmagon.jwebswing.Page;
import za.co.mmagon.jwebswing.base.html.Div;
import za.co.mmagon.jwebswing.base.html.SmallText;
import za.co.mmagon.jwebswing.plugins.smartwizard.SmartWizard;
import za.co.mmagon.jwebswing.plugins.smartwizard.SmartWizardStep;
import za.co.mmagon.jwebswing.plugins.smartwizard.SmartWizardStepItem;
import za.co.mmagon.jwebswing.plugins.smartwizard.SmartWizardThemes;
import za.co.mmagon.jwebswing.plugins.smartwizard.options.SmartWizardTransitionEffects;
import za.co.mmagon.logger.LogFactory;
import za.co.mmagon.logger.handlers.ConsoleSTDOutputHandler;

import javax.servlet.ServletException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmartWizardUndertow extends Page
{
	public SmartWizardUndertow()
	{
		super("JWebSwing - Smart Wizard Demo");

		SmartWizard sw = new SmartWizard("test");
		sw.getSteps()
				.add(new SmartWizardStep(new Div<>().add("Content 1"), new SmartWizardStepItem("Header", new SmallText("Description 1"))));
		sw.getSteps()
				.add(new SmartWizardStep(new Div<>().add("Content 2"),
				                         new SmartWizardStepItem("Header 2", new SmallText("Description 2"))));
		sw.getSteps()
				.add(new SmartWizardStep(new Div<>().add("Content 3"),
				                         new SmartWizardStepItem("Header 3", new SmallText("Description 3"))));
		add(sw);


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
				.add(new SmartWizardStep(new Div<>().add("Content 2"),
				                         new SmartWizardStepItem("Header 2", new SmallText("Description 2"))));
		sw2.getSteps()
				.add(new SmartWizardStep(new Div<>().add("Content 3"),
				                         new SmartWizardStepItem("Header 3", new SmallText("Description 3"))));

		add(sw2);
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
		Handler[] handles = Logger.getLogger("")
				                    .getHandlers();
		for (Handler handle : handles)
		{
			handle.setLevel(Level.FINE);
		}
		LogFactory.setDefaultLevel(Level.FINE);
		Logger.getLogger("")
				.addHandler(new ConsoleSTDOutputHandler(true));

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
