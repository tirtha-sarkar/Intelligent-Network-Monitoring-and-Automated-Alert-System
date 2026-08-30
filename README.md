# Intelligent Network Monitoring and Automated Alert System

![Java](https://img.shields.io/badge/Java-17-orange)
![Platform](https://img.shields.io/badge/Platform-Desktop-blue)
![GUI](https://img.shields.io/badge/GUI-Java%20Swing-green)
![Build](https://img.shields.io/badge/Build-Apache%20Ant-red)

## 📌 Project Overview

The **Intelligent Network Monitoring and Automated Alert System (INMAS)** is a Java-based desktop application designed to monitor network hosts and services in real time.

The system checks host reachability, TCP port availability, and network latency. It automatically detects changes in network conditions and classifies hosts as **ONLINE**, **DEGRADED**, or **OFFLINE**.

The application provides a graphical dashboard for monitoring multiple hosts, displaying latency trends, generating alerts, and exporting monitoring reports in CSV format.

---

## 🎯 Project Objectives

* Monitor network host availability in real time
* Check TCP service and port availability
* Measure network latency
* Detect network failures automatically
* Classify network conditions
* Generate automatic alerts when host status changes
* Monitor multiple hosts simultaneously
* Visualize latency trends
* Export monitoring results to CSV files

---

## ✨ Key Features

* 🌐 Real-time host reachability monitoring
* 🔌 TCP service and port availability checking
* 🖥️ Multi-host network monitoring
* 🟢 ONLINE status detection
* 🟡 DEGRADED status detection
* 🔴 OFFLINE status detection
* 🔔 Automatic state-change alerts
* 📊 Live latency trend chart
* ➕ Add new monitored hosts
* ➖ Remove monitored hosts
* ⏱️ Configurable monitoring interval
* 📁 CSV report export
* 🧵 Multithreaded monitoring system
* 🖥️ Interactive Java Swing dashboard

---

## 🛠️ Technologies Used

| Technology               | Purpose                        |
| ------------------------ | ------------------------------ |
| Java 17                  | Core programming language      |
| Java Swing               | Graphical User Interface       |
| Java Networking          | Host and port monitoring       |
| InetAddress              | Host reachability checking     |
| Socket                   | TCP port availability checking |
| Multithreading           | Concurrent network monitoring  |
| ScheduledExecutorService | Scheduled monitoring tasks     |
| Apache Ant               | Build automation               |
| NetBeans                 | Development environment        |
| CSV                      | Monitoring report export       |

---

## 📂 Project Structure

```text
IntelligentNetworkMonitoringAndAutomatedAlertSystem/
│
├── src/
│   └── inmas/
│       ├── AlertManager.java
│       ├── CsvExporter.java
│       ├── HostStatus.java
│       ├── HostTarget.java
│       ├── LineChartPanel.java
│       ├── MainFrame.java
│       ├── NetworkMonitorApp.java
│       └── NetworkMonitorService.java
│
├── nbproject/
│   ├── project.properties
│   └── project.xml
│
├── build.xml
│
└── README.md
```

---

## 🏗️ System Architecture

The application follows a modular architecture.

```text
                ┌──────────────────────┐
                │  NetworkMonitorApp   │
                │   Application Entry  │
                └──────────┬───────────┘
                           │
                ┌──────────▼───────────┐
                │      MainFrame       │
                │   GUI Dashboard      │
                └──────────┬───────────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
┌───────▼────────┐ ┌──────▼───────┐ ┌────────▼─────────┐
│ NetworkMonitor │ │ AlertManager │ │ LineChartPanel   │
│    Service     │ │              │ │ Latency Chart    │
└───────┬────────┘ └──────────────┘ └──────────────────┘
        │
 ┌──────┼───────────────┐
 │      │               │
▼       ▼               ▼
HostTarget         HostStatus       CsvExporter
```

---

## 📄 Main Components

### NetworkMonitorApp

The main entry point of the application.

### MainFrame

Provides the graphical user interface and monitoring dashboard.

### NetworkMonitorService

Handles network monitoring operations using concurrent threads.

### HostTarget

Represents a network host and its associated monitoring information.

### HostStatus

Stores the current monitoring status, latency, and connectivity information.

### AlertManager

Detects changes in host status and generates automatic alerts.

### CsvExporter

Exports monitoring results to CSV files.

### LineChartPanel

Displays live latency trends using graphical visualization.

---

## 🚀 How to Run the Project

### Method 1: Using NetBeans

1. Download or clone this repository.
2. Open **Apache NetBeans**.
3. Click:

```text
File → Open Project
```

4. Select the project folder:

```text
IntelligentNetworkMonitoringAndAutomatedAlertSystem
```

5. Right-click on the project.
6. Select:

```text
Run
```

The application will start automatically.

---

## 🔧 Requirements

Before running the project, make sure you have:

* Java Development Kit (JDK) 17
* Apache NetBeans
* Apache Ant

No external libraries are required.

---

## 💻 Build Using Apache Ant

To build the project:

```bash
ant jar
```

To run the project:

```bash
ant run
```

To clean the project:

```bash
ant clean
```

---

## 📊 How the System Works

1. The user starts the monitoring system.
2. The application checks the availability of configured hosts.
3. TCP ports are checked for service availability.
4. Network latency is measured.
5. The system classifies the host status.
6. Status changes trigger automatic alerts.
7. Monitoring data is displayed on the dashboard.
8. Latency trends are visualized using charts.
9. Users can export monitoring results as CSV files.

---

## 🟢 Status Classification

| Status   | Description                                                      |
| -------- | ---------------------------------------------------------------- |
| ONLINE   | Host is reachable and services are available                     |
| DEGRADED | Host is reachable but experiencing performance or service issues |
| OFFLINE  | Host is unreachable                                              |

---

## 📸 Application Features

The system allows users to:

* Start network monitoring
* Stop monitoring
* Add new hosts
* Remove monitored hosts
* Configure monitoring intervals
* View real-time host status
* Monitor network latency
* Receive automatic alerts
* Export reports to CSV format

---

## 🎓 Academic Information

**Course:** CSE 318 – Computer Networking Lab
**Semester:** Summer 2026
**Project Type:** Complex Engineering Problem

**Student Name:** Tirtha Babu Sarkar
**Student ID:** 241002058

---

## 🔮 Future Improvements

Possible future enhancements include:

* Email notification system
* SMS alerts
* Database integration
* Historical monitoring data
* Network topology visualization
* Remote monitoring support
* Web-based dashboard
* Mobile application integration
* AI-based network failure prediction

---

## 👨‍💻 Author

**Tirtha Babu Sarkar**

Computer Science and Engineering Student

---

## 📜 License

This project was developed for academic and educational purposes.

---

⭐ If you found this project useful, consider giving the repository a star!

