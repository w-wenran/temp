package org.ezt.repositories;

import org.ezt.models.GrantAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangwr on 2016/4/1.
 */
public interface GrantAccessTokenRepository extends JpaRepository<GrantAccessToken,Long> {
}
