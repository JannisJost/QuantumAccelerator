package ch.dragxfly.quantumaccelerator.CustomControls;

/**
 * Written with help from Arial
 *
 * @author janni
 */
public final class ToolTipTexts {

    //Privacy view
    public final String getDeleteDNSCache() {
        return "The DNS cache stores data locally on your Computer, based on the websites you visit the most, to make the DNS queries from a server unnecessary and speeds up your requests."
                + "This may sound nice at first, but if someone hacks your computer he can read your DNS-cache and in this way collect data about you. Also if a website has a new IP but your computer"
                + "still uses the outdated local information stored on your computer you might not be able to reach your beloved website even tho it is online.";
    }

    public final String getCam() {
        return "Deactivates your webcam through the device manager. This is no real protection against webcam spies because they can reactivate it easily.";
    }

    public final String getTelemetry() {
        return "Telemetry is data which your computer directly sends to Microsoft."
                + "Microsoft uses this data to track you. This options do not completely deactivate telemetry related things but they block them.";
    }

    public final String getPwGen() {
        return "Create truly random passwords using your human unpredictability. Find more infos in the generator itself";
    }

    //Extras view
    public final String getZipBombIdentifier() {
        return "A so-called zip bomb is a normal zipped (compressed) file, which in its zipped form isn’t malicious at all."
                + "But if you decide to unzip a zip bomb, a nasty surprise awaits you, because all or a lot of your storage will be filled."
                + "This happens because zip bombs contain files that get compressed down to a minimum of their unzipped size. "
                + "This tool helps you to identify such files without unzipping them.";
    }

    public final String getEnableGodMode() {
        return "Creates a folder on your desktop which is also known as “godmode”. This folder contains shortcuts to almost all settings found in "
                + "the control panel.";
    }

    public final String getOrganizeDesktop() {
        return "Cleans your desktop by putting all of your text files and pictures etc. into folders.";
    }

    public final String getGodmode() {
        return "Creates a folder on your desktop which is also known as “godmode”. This folder contains shortcuts to almost all settings found in the control panel.";
    }

    public final String getRestorepoint() {
        return "Creates a “Windows restore point” which can be used to reset your Computer to the state it had when the rescue point was created. "
                + "This is useful if something breaks your computers software because you can easily restore it.";
    }

    public final String getStressTest() {
        return "Keeps the processor at a load that you can specify."
                + "This can be used to test the stability of your system and also to detect possible overheating problems.";
    }

    //password gen
    public final String getTrulyRandom() {
        return "Your computer is just a calculator, which means it wont be able to generate truly random numbers at all. Therefor computer "
                + "generated passwords can be predicted. This password generator uses computer generated random numbers too, "
                + "but paired with your cursor movement in a specific way (which is truly random because you are a human (hopefully) and not predictable)"
                + " it generates passwords that are not predictable by anyone";
    }
}
