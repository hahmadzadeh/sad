package ir.sharif.sad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomRepository<V, K> extends JpaRepository<V, K>, JpaSpecificationExecutor<V> {
}
