package ca.bc.gov.open.pssg.rsbc.dps.sftp.starter;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Logger;

public class JschDebugLogger implements Logger {
    public boolean isEnabled(int level) { return true; }
    public void log(int level, String message) { System.out.println("JSch: " + message); }
}
