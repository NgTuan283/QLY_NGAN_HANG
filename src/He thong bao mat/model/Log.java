// model/Log.java
package model;

import java.time.LocalDateTime;

public class Log {
    private String logId;
    private String employeeId;
    private String action;
    private String target;
    private String targetId;
    private LocalDateTime actionTime;

    public Log() {}

    // Getter và Setter
    public String getLogId() { return logId; }
    public void setLogId(String logId) { this.logId = logId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }

    public LocalDateTime getActionTime() { return actionTime; }
    public void setActionTime(LocalDateTime actionTime) { this.actionTime = actionTime; }
}