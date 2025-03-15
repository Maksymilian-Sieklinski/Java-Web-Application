package uk.ac.ucl.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main
{
  private static final int DEFAULT_PORT = 8080;
  private static final String DEFAULT_WEBAPP_DIR = "src/main/webapp/";
  private static final String DEFAULT_TARGET_CLASSES = "target/classes";
  private static final String WEB_INF_CLASSES = "/WEB-INF/classes";

  public static Thread addShutdown(final Tomcat tomcat)
  {
    Thread shutdownHook = new Thread(() -> {
      try
      {
        if (tomcat != null)
        {
          tomcat.stop();
          tomcat.destroy();
        }
      } catch (Exception e)
      {

      }
    });
    Runtime.getRuntime().addShutdownHook(shutdownHook);
    return shutdownHook;
  }

  private static Context getContext(Path webappDirectory, Tomcat tomcat)
  {
    if (!Files.exists(webappDirectory) || !Files.isDirectory(webappDirectory))
    {
      throw new IllegalArgumentException("Webapp directory does not exist: " + webappDirectory);
    }
    return tomcat.addWebapp("/", webappDirectory.toAbsolutePath().toString());
  }

  private static void setResources(Context context, Path targetClassesDirectory)
  {
    WebResourceRoot resources = new StandardRoot(context);
    resources.addPreResources(new DirResourceSet(resources, WEB_INF_CLASSES,
      targetClassesDirectory.toAbsolutePath().toString(), "/"));
    context.setResources(resources);
  }

  public static void main(String[] args)
  {
    final Path webappDirectory = Paths.get(DEFAULT_WEBAPP_DIR);
    final Path targetClassesDirectory = Paths.get(DEFAULT_TARGET_CLASSES);
    final Tomcat tomcat = new Tomcat();
    Connector connector = new Connector("HTTP/1.1");
    connector.setPort(8080);
    connector.setMaxPostSize(500 * 1024 * 1024);
    tomcat.getService().addConnector(connector);

    try
    {
      tomcat.setPort(DEFAULT_PORT);
      tomcat.getConnector();
      addShutdown(tomcat);

      Context context = getContext(webappDirectory, tomcat);
      setResources(context, targetClassesDirectory);

      tomcat.start();
      tomcat.getServer().await();
    } catch (IllegalArgumentException e)
    {
    } catch (Exception e)
    {

    }
  }
}