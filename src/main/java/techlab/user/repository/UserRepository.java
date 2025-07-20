package techlab.user.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techlab.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <UserEntity, String> {

    List<UserEntity> findByUserNameOrPhone(String userName, int phone);

    List<UserEntity> findByIdOrUserNameOrName(Long id, String username, String name);

    boolean existsByUserName(String userName);

    Optional<UserEntity> findByUserName(@NonNull String userName);
}
