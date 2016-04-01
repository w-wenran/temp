package org.ezt.repositories;

import org.ezt.models.OAuthUserClient;
import org.ezt.models.pk.OAuthUserClientPK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangwr on 2016/4/1.
 */
public interface OAuthUserClientRepository extends JpaRepository<OAuthUserClient,OAuthUserClientPK> {
}
