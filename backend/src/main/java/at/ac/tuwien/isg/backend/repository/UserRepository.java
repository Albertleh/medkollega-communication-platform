package at.ac.tuwien.isg.backend.repository;

import at.ac.tuwien.isg.backend.common.UserRole;
import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.UserContact;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static at.ac.tuwien.isg.backend.entity.QApplicationUser.applicationUser;
import static at.ac.tuwien.isg.backend.entity.QUserContact.userContact;

@Repository
public class UserRepository extends AbstractBaseRepository {

    public Optional<ApplicationUser> findUserByEmail(String email) {
        return getResult(query(applicationUser)
            .where(applicationUser.email.eq(email)));
    }

    public Optional<ApplicationUser> findUserById(String id) {
        return getResult(query(applicationUser)
            .where(applicationUser.id.eq(id)));
    }

    public Optional<UserContact> getUserContact(ApplicationUser user) {
        return getResult(query(userContact)
            .leftJoin(applicationUser).on(applicationUser.contact.eq(userContact))
            .where(applicationUser.eq(user)));
    }

    public List<ApplicationUser> getApplicationUsers(ApplicationUser user, String search) {
        return getResults(query(applicationUser)
            .leftJoin(applicationUser.contact, userContact)
            .where(applicationUser.ne(user),
                applicationUser.role.ne(UserRole.ADMIN),
                userContact.firstName.containsIgnoreCase(search).or(userContact.lastName.contains(search))));
    }

}
