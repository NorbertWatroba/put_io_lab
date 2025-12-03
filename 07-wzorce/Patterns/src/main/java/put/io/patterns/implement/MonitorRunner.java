package put.io.patterns.implement;

public class MonitorRunner {

    public static void main(String args[]){
// first create the monitor
        SystemMonitor monitor = new SystemMonitor();
// create the observer and add it to the monitor
        SystemStateObserver garbageObserver = new SystemGarbageCollectorObserver();
        SystemStateObserver coolerObserver = new SystemCoolerObserver();
        SystemStateObserver usbObserver = new USBDeviceObserver();

        monitor.addSystemStateObserver(garbageObserver);
        monitor.addSystemStateObserver(coolerObserver);
        monitor.addSystemStateObserver(usbObserver);
        while (true) {
            monitor.probe();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

