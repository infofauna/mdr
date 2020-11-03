package ch.cscf.jeci.domain.dto.errors;

/**
 * @author: henryp
 */
public class Message implements Comparable<Message>{

    private String severity, message, field;

    private Long messageId;


    public Message(String severity, String message, String field, Long messageId) {
        this.severity = severity;
        this.message = message;
        this.field = field;
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getSeverityCode() {
        return severity;
    }

    public Severity getSeverity(){
        return Severity.valueOf(severity);
    }

    @Override
    /**
     * Comparison by severity and message.
     */
    public int compareTo(Message other) {
        int weightComparison = this.getSeverity().getWeight().compareTo(other.getSeverity().getWeight());
        if(weightComparison != 0){
            return weightComparison;
        }
        return this.getMessage().compareTo(other.getMessage());
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", message='" + message + '\'' +
                ", severity='" + severity + '\'' +
                '}';
    }
}
