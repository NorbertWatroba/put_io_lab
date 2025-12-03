package put.io.patterns.implement;

public class USBDeviceObserver implements SystemStateObserver {
    private int lastDeviceCount = -1;

    @Override
    public void update(SystemMonitor monitor) {
        SystemState state = monitor.getLastSystemState();
        int currentDeviceCount = state.getUsbDevices();

        if (lastDeviceCount != currentDeviceCount) {
            if (currentDeviceCount > lastDeviceCount) {
                System.out.println("> USB connected, count: " + currentDeviceCount);
            } else {
                System.out.println("> USB disconnected, count: " + currentDeviceCount);
            }

            lastDeviceCount = currentDeviceCount;
        }
    }
}