package za.co.mmagon.jwebswing.examples.undertow.smartwizard;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import za.co.mmagon.guiceinjection.GuiceContext;
import za.co.mmagon.jwebswing.Page;
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
