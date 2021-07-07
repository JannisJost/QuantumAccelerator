package ch.dragxfly.quantumaccelerator.executors;

import shell.RegistryEditor;

/**
 *
 * @author jannis
 */
public class AppRightsManager {

    private final RegistryEditor regedit = new RegistryEditor();

    public void setRights(boolean cameraAccess, boolean locationAccess, boolean notificationsAccess, boolean microphoneAccess, boolean accountInfoAccess) {
        setCameraAccess(cameraAccess);
        setLocationAccess(locationAccess);
        setNotificationAccess(notificationsAccess);
        setMicrophoneAccess(microphoneAccess);
        setAccoutInfoAccess(accountInfoAccess);
    }

    private void setCameraAccess(boolean cameraAccess) {
        regedit.setOrCreateKey("HKCU:\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{E5323777-F976-4f5b-9B55-B94699C46E44}", "Value", (cameraAccess ? "Allow" : "Deny"));
    }

    private void setLocationAccess(boolean locationAccess) {
        regedit.setOrCreateKey("HKCU:\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{BFA794E4-F964-4FDB-90F6-51056BFE4B44}", "Value", (locationAccess ? "Allow" : "Deny"));
    }

    private void setNotificationAccess(boolean notificationsAccess) {
        regedit.setOrCreateKey("HKCU:\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{52079E78-A92B-413F-B213-E8FE35712E72}", "Value", (notificationsAccess ? "Allow" : "Deny"));
    }

    private void setMicrophoneAccess(boolean microphoneAccess) {
        regedit.setOrCreateKey("HKCU:\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{2EEF81BE-33FA-4800-9670-1CD474972C3F}", "Value", (microphoneAccess ? "Allow" : "Deny"));
    }

    private void setAccoutInfoAccess(boolean accountInfoAccess) {
        regedit.setOrCreateKey("HKCU:\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{C1D23ACC-752B-43E5-8448-8D0E519CD6D6}", "Value", (accountInfoAccess ? "Allow" : "Deny"));
    }
}
