package ch.dragxfly.quantumaccelerator.hardware;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.ApplicationFilesHelper;
import shell.CMD;

/**
 *
 * @author jannis
 */
public class HardwareMonitor {
    public int getCPUTemp(){
        String cpuTemp = new  CMD().execCommandReadOutput("cd " +new ApplicationFilesHelper().getInstallationPath() +"Hardware\\ && .\\GetCPUTemp.exe");
        return Integer.parseInt(cpuTemp.replaceAll(" ", "")); 
    }
}
