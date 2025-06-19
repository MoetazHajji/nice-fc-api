package matawan.nicefcapi.repository;

import jdk.jfr.Registered;
import matawan.nicefcapi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface ITeamRepository extends JpaRepository<Team, Long> {
}
