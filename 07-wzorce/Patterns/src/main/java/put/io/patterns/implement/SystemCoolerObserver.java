package put.io.patterns.implement;

public class SystemCoolerObserver implements SystemStateObserver {
    @Override
    public void update(SystemMonitor monitor) {
        SystemState state = monitor.getLastSystemState();

        if (state.getCpuTemp() > 60.00) {
            System.out.println("> Increasing cooling of the CPU...");
        }
    }
}