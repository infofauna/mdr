package ch.cscf.jeci.domain.entities.utility;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="PROCESSINGSTATUS", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="PID_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="PID_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PID_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "PID_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "PID_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "PID_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "PID_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "PID_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "PID_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "PID_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_IMPORTPROTOCOLHEADER")
public class TaskStatus extends BaseEntity {

    @Column(name = "PID_CURRENTSTEP", length = 4000)
    private String currentStep;

    @Column(name = "PID_NUMBERRECORD")
    private Integer numOfRecords;

    @Column(name = "PID_SECONDSTOTAL")
    private Integer secondsTotal;

    @Column(name = "PID_SECONDSLEFT")
    private Integer secondsLeft;

    @Column(name = "PID_PERCENTPROCESSED")
    private Integer percentageProcessed;

    @Transient
    private boolean done;

    @Override
    public String toString() {
        return "TaskStatus{" +
                "ID='" + id + '\'' +
                ", currentStep='" + currentStep + '\'' +
                ", numOfRecords=" + numOfRecords +
                ", secondsTotal=" + secondsTotal +
                ", secondsLeft=" + secondsLeft +
                ", percentageProcessed=" + percentageProcessed +
                ", done=" + done +
                '}';
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public Integer getNumOfRecords() {
        return numOfRecords;
    }

    public Integer getSecondsTotal() {
        return secondsTotal;
    }

    public Integer getSecondsLeft() {
        return secondsLeft;
    }

    public Integer getPercentageProcessed() {
        return percentageProcessed;
    }
}
