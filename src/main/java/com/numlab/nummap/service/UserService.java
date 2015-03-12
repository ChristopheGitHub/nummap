package com.numlab.nummap.service;

import com.numlab.nummap.domain.Authority;
import com.numlab.nummap.domain.CompanyContactInformation;
import com.numlab.nummap.domain.PersonContactInformation;
import com.numlab.nummap.domain.User;
import com.numlab.nummap.domain.enumerations.CategoryEnum;
import com.numlab.nummap.domain.enumerations.CustomersTypeEnum;
import com.numlab.nummap.domain.enumerations.FieldEnum;
import com.numlab.nummap.domain.enumerations.SectorEnum;
import com.numlab.nummap.repository.AuthorityRepository;
import com.numlab.nummap.repository.PersistentTokenRepository;
import com.numlab.nummap.repository.UserRepository;
import com.numlab.nummap.security.SecurityUtils;
import com.numlab.nummap.service.util.RandomUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    PasswordEncoder passwordEncoder;

    @Inject
    UserRepository userRepository;

    @Inject
    PersistentTokenRepository persistentTokenRepository;

    @Inject
    AuthorityRepository authorityRepository;

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                userRepository.save(user);
                log.debug("Activated user: {}", user);
                return user;
            });
        return Optional.empty();
    }

    public User createUserInformation(String login, String password, String firstName, String lastName, String email,
        CategoryEnum category, String description, String raisonSociale,
        PersonContactInformation personContactInformation, CompanyContactInformation companyContactInformation,
         List<String> competencies, List<SectorEnum> sectors,
         List<FieldEnum> fields, List<CustomersTypeEnum> customers, String langKey) {
        User newUser = new User();
        Authority authority = authorityRepository.findOne("ROLE_USER");
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setEmail(email);
        newUser.setCategory(category);
        newUser.setDescription(description);
        newUser.setRaisonSociale(raisonSociale);
        newUser.setPersonContactInformation(personContactInformation);
        newUser.setCompanyContactInformation(companyContactInformation);
        newUser.setCompetencies(competencies);
        newUser.setSectors(sectors);
        newUser.setFields(fields);
        newUser.setCustomers(customers);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public void updateUserInformation(String firstName, String lastName, String email, CategoryEnum category
        String description, String raisonSociale, PersonContactInformation personContactInformation, 
        CompanyContactInformation companyContactInformation, List<String> competencies, List<SectorEnum> sectors,
        List<FieldEnum> fields, List<CustomersTypeEnum> customers) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentLogin()).ifPresent(u -> {
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setEmail(email);
            u.setCategory(category);
            u.setDescription(description);
            u.setRaisonSociale(raisonSociale);
            u.setPersonContactInformation(personContactInformation);
            u.setCompanyContactInformation(companyContactInformation);
            u.setCompetencies(competencies);
            u.setSectors(sectors);
            u.setFields(fields);
            u.setCustomers(customers);
            userRepository.save(u);
            log.debug("Changed Information for User: {}", u);
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentLogin()).ifPresent(u-> {
            String encryptedPassword = passwordEncoder.encode(password);
            u.setPassword(encryptedPassword);
            userRepository.save(u);
            log.debug("Changed password for User: {}", u);
        });
    }

    public User getUserWithAuthorities() {
        User currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin()).get();
        currentUser.getAuthorities().size(); // eagerly load the association
        return currentUser;
    }

    /**
     * Persistent Token are used for providing automatic authentication, they should be automatically deleted after
     * 30 days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at midnight.
     * </p>
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOldPersistentTokens() {
        LocalDate now = new LocalDate();
        persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).stream().forEach(token ->{
            log.debug("Deleting token {}", token.getSeries());
            persistentTokenRepository.delete(token);
        });
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        DateTime now = new DateTime();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }
}
