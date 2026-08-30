# Intelligent Network Monitoring and Automated Alert System

Course: CSE 318  
Semester: Summer 2026  
Student: Tirtha Babu Sarkar  
ID: 241002058  
Project type: Complex Engineering Problem

## Technology
- Java 17
- Java Swing GUI
- Java Networking (`InetAddress`, `Socket`)
- Multithreading / `ScheduledExecutorService`
- CSV reporting
- NetBeans + Apache Ant

## Main Features
1. Real-time host reachability monitoring
2. TCP service/port availability checks
3. Multi-host scheduled monitoring
4. ONLINE / DEGRADED / OFFLINE classification
5. Automatic state-change alerts
6. Live latency trend chart
7. Add/remove monitored hosts
8. Configurable monitoring interval
9. CSV report export
10. Modular, maintainable architecture

## Run in NetBeans
1. Extract the ZIP.
2. Open NetBeans.
3. File -> Open Project.
4. Select the extracted project folder.
5. Right-click project -> Run.

The project uses Apache Ant and does not require external libraries.

## Demonstration
Click `Start Monitoring`. The dashboard will begin checking the default hosts.
Use `Add Host` to monitor another host and TCP port.
Select a row and use `Remove` to remove a host.
Use `Export CSV` to save the latest monitoring report.
<img width="1487" height="932" alt="image" src="https://github.com/user-attachments/assets/b64840f0-1a48-4371-a1fe-79a281b6a39b" />

## Architecture
- `NetworkMonitorApp` - application entry point
- `MainFrame` - GUI/dashboard
- `NetworkMonitorService` - concurrent network checks
- `HostTarget` - monitored host model
- `HostStatus` - measurement/result model
- `AlertManager` - automatic state-change alert logic
- `CsvExporter` - report generation
- `LineChartPanel` - custom latency visualization
