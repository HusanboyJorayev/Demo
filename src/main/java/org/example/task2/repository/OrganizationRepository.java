package org.example.task2.repository;

import org.example.task2.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer>, JpaSpecificationExecutor<Organization> {

    @Query(value = "select count(o.id) as CountOrganization, sum(c.amount) as CountAmount\n" +
            "from calculation as c\n" +
            "           left join employee as e on e.id = c.employee_id\n" +
            "         left join organization as o on o.id = c.organization_id\n" +
            "         left join organization as o2 on o2.id = e.organization_id\n" +
            "         left join region as r on o.region_id =r.id or o2.region_id=r.id", nativeQuery = true)
    List<Object[]> countOrganizationAndAmount();
}
