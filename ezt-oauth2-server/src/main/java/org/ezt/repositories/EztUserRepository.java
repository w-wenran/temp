package org.ezt.repositories;

import org.ezt.models.EztUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangwr on 2016/4/6.
 */
public interface EztUserRepository extends JpaRepository<EztUser,Long> {


}
