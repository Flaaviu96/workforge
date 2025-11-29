package dev.workforge.app.WorkForge.repository;

import dev.workforge.app.WorkForge.trigger.AbstractTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractTriggerRepository extends JpaRepository<AbstractTrigger, Long> {
}
