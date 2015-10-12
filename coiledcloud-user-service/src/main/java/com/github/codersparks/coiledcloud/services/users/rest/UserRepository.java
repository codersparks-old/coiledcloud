package com.github.codersparks.coiledcloud.services.users.rest;

import com.github.codersparks.coiledcloud.services.users.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by codersparks on 12/10/2015.
 */

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends MongoRepository<User, Long> {
    
}
