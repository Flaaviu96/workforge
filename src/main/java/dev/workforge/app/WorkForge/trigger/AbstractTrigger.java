package dev.workforge.app.WorkForge.trigger;

import jakarta.persistence.*;

@Entity
@Table(name = "triggers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="trigger_type",
        discriminatorType = DiscriminatorType.INTEGER)
public abstract class AbstractTrigger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract Object fire();
}
