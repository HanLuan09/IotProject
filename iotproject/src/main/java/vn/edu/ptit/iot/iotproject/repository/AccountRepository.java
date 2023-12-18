package vn.edu.ptit.iot.iotproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.ptit.iot.iotproject.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	
	Account findByCodeAndPassword(String code, String password);
	
	@Query("SELECT a FROM Account a JOIN a.student st WHERE st.rfid = :rfidToCheck")
	Account findStudentByRfid(@Param("rfidToCheck") String rfid);
	
}
