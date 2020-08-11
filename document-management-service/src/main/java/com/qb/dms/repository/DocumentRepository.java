package com.qb.dms.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import com.qb.dms.data.DocumentDetails;

@Repository
@RepositoryRestResource(collectionResourceRel = "documentDetails", path = "documentDetails")
public interface DocumentRepository extends PagingAndSortingRepository<DocumentDetails, Long> {

  List<DocumentDetails> findByUserId(@Param("userId") String userId);
  
  DocumentDetails findById(@Param("id") long id);

}
