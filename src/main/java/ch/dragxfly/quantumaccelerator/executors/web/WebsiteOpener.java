package ch.dragxfly.quantumaccelerator.executors.web;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jannis
 */
public class WebsiteOpener {

    public void openWebsite(String webURL) {
        try {
            Desktop.getDesktop().browse(new URL(webURL).toURI());
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebsiteOpener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(WebsiteOpener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
