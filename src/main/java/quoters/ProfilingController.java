package quoters;

import javax.management.DynamicMBean;

public class ProfilingController implements ProfilingControllerMBean {
    //метод который будет включать Profiling
    //А включать через MBEan
    private boolean enabled=true;
    //Булеан по умолчанию в дефолте false

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
