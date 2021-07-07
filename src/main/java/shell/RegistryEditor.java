package shell;

/**
 *
 * @author jannis
 */
public class RegistryEditor {

    public void setOrCreateKey(String location, String name, String value) {
        PowerShell ps = new PowerShell();
        ps.executeCommand("New-Item -Path \'" + location + "\' -ItemType Directory");
        ps.executeCommand("New-ItemProperty -Path \'" + location + "\' -Name " + name + " -Value " + value + " -Force");
    }

    public String readKey(String location, String name) {
        try {
            CMD cmd = new CMD();
            String value = cmd.execCommandReadOutput("reg query \"" + location + "\" /v \"" + name + "\"");
            value = value.replaceAll("\n", "");
            value = value.substring(value.lastIndexOf("REG_SZ") + 6);
            value = value.trim();
            return value;
        } catch (Exception e) {
            return "";
        }
    }
}
