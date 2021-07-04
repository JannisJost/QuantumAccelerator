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
 * @author janni
 */
public class WebsiteOpener {

    public void openWebsite(String StringURL) {
        try {
            Desktop.getDesktop().browse(new URL(StringURL).toURI());
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebsiteOpener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(WebsiteOpener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
