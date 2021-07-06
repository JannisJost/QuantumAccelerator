package ch.dragxfly.quantumaccelerator.executors.web.Browser;

/**
 *
 * @author jannis
 */
public interface Browser {
    public abstract void clearCache();
    public abstract void clearHistory();
    public abstract void clearCookies();
    public abstract void kill();
}
