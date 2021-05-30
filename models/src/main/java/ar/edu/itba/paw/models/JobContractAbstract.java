package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public class JobContractAbstract {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_contract_id_seq")
    @SequenceGenerator(sequenceName = "contract_contract_id_seq", name = "contract_contract_id_seq", allocationSize = 1)
    @Column(name = "contract_id", nullable = false)
    protected long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "contract_client_id_fkey"))
    protected User client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", foreignKey = @ForeignKey(name = "contract_package_id_fkey"))
    protected JobPackage jobPackage;

    @Column(length = 100, name = "contract_creation_date", nullable = false)
    protected LocalDateTime creationDate;

    @Column(length = 100, name = "contract_description", nullable = false)
    protected String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "contract_state", nullable = false, columnDefinition = "INT default 6")
    protected JobContract.ContractState state;
    
    public JobPackage getJobPackage() {
        return jobPackage;
    }

    public void setJobPackage(JobPackage jobPackage) {
        this.jobPackage = jobPackage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getProfessional() {
        return jobPackage.getJobPost().getUser();
    }

    public JobContract.ContractState getState() {
        return state;
    }

    public void setState(JobContract.ContractState state) {
        this.state = state;
    }
    
    @Override
    public String toString() {
        return "JobContract{" +
                "id=" + id +
                ", client=" + client +
                ", jobPackage=" + jobPackage +
                ", professional=" + getProfessional() +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        //fixme está bien este equals?
        if (this == o) return true;
        if (o == null || !getClass().isAssignableFrom(o.getClass())) return false;
        JobContractAbstract that = (JobContractAbstract) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
