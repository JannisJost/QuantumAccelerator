package ch.dragxfly.quantumaccelerator.models;

import controller.main.NotificationsController;
import ch.dragxfly.quantumaccelerator.notifications.Notification;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.Pane;

/**
 *
 * @author janni
 */
public class NotificationModel implements Observer {

    private final List<Notification> allNotifications = new ArrayList<>();
    private NotificationsController controller;

    public List<Notification> getNotifications() {
        return allNotifications;
    }

    public void addToNotifications(Notification n) {
        allNotifications.add(n);
    }

    public void addToNotifications(List<Notification> n) {
        allNotifications.addAll(n);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.allNotifications.add((Notification) arg);
        controller.UpdateMessages();
    }

    public List<Pane> getNotificationsAsPane() {
        List<Pane> notificationsAsPanes = new ArrayList<>();
        for (Notification n : allNotifications) {
            notificationsAsPanes.add(n.createPane());
        }
        return notificationsAsPanes;
    }

    public void setController(NotificationsController c) {
        controller = c;
    }
}
