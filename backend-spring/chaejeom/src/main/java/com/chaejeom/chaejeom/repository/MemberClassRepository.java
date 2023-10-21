package com.chaejeom.chaejeom.repository;

import com.chaejeom.chaejeom.domain.MemberClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberClassRepository extends JpaRepository<MemberClass, Long> {

}
