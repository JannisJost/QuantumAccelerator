package shellscripts;

/**
 *
 * @author janni
 */
public class RegistryEditor {

    public void setOrCreateKey (String location, String name, String value) {
        PowerShell ps = new PowerShell();
        ps.executeCommand("New-Item -Path \"" + location + "\" -ItemType Directory");
        ps.executeCommand("New-ItemProperty -Path \"" + location + "\" -Name " + name + " -Value " + value + " -Force");
    }
}
