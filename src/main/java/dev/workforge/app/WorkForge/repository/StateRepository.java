package dev.workforge.app.WorkForge.repository;

import dev.workforge.app.WorkForge.model.State;
import dev.workforge.app.WorkForge.model.StateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    @Query(
            "SELECT st FROM State st " +
            "WHERE st.stateType = :stateType"
    )
    State findStateByStateType(@Param("stateType") StateType stateType);
}
