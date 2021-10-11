package ch.dragxfly.quantumaccelerator.notifications;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author janni
 */
public class NotificationManager extends Observable {

    private boolean notificationsShown = false;
    private List<Notification> allNotifications = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    public void showWindowsNotification(String title, String message) {
        showNotification(title, message);
    }

    private void showNotification(String title, String message) {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");
            TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);
            //Displays notification
            trayIcon.displayMessage(
                    message, "Java Notification Demo", MessageType.INFO);
        } catch (AWTException e) {

        }
    }

    public boolean notificationIsShowing() {
        return notificationsShown;
    }

    public void changeViewed() {
        notificationsShown = !notificationsShown;
    }

    public void createNotification(String title, String message) {
        Notification n = new Notification(title, message);
        allNotifications.clear();
        allNotifications.add(n);
        setChanged();
        notifyObservers(n);
    }

    public void attachObserver(Observer observer) {
        observers.add(observer);
    }

}
