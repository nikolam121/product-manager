package hr.tis.academy.repository;

import hr.tis.academy.common.dto.*;
import hr.tis.academy.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{
    @Query("select s from Store s ")
    List<Store> fetchAll();

    @Query("select s from Store s where s.id= :id")
    Store fetchById(Long id);

}
