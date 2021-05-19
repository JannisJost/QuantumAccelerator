package ch.dragxfly.quantumaccelerator.booster;

import java.util.List;

/**
 *
 * @author janni
 */
public class BackgroundProcessKiller {

    private List<String> neededProcesses;

    public BackgroundProcessKiller() {
        //Adds all essential Windows processes
        neededProcesses.add("explorer.exe");
        neededProcesses.add("taskmgr.exe");
        neededProcesses.add("spoolsv.exe");
        neededProcesses.add("lsass.exe");
        neededProcesses.add("csrss.exe");
        neededProcesses.add("smss.exe");
        neededProcesses.add("winlogon.exe");
        neededProcesses.add("svchost.exe");
        neededProcesses.add("services.exe");

    }
}
