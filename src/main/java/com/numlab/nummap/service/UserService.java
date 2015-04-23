package com.numlab.nummap.service;

import com.numlab.nummap.domain.*;
import com.numlab.nummap.domain.enumerations.CategoryEnum;
import com.numlab.nummap.domain.enumerations.FieldEnum;
import com.numlab.nummap.repository.AuthorityRepository;
import com.numlab.nummap.repository.PersistentTokenRepository;
import com.numlab.nummap.repository.UserRepository;
import com.numlab.nummap.security.SecurityUtils;
import com.numlab.nummap.service.util.RandomUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @Inject
    MailService mailService;


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

    public User createUserInformation(String login, String password, String email, Location location,
        CategoryEnum category, String description, String raisonSociale,
        PersonContactInformation personContactInformation, CompanyContactInformation companyContactInformation,
         List<String> competencies, List<String> sectors,
         List<FieldEnum> fields, String langKey) {
        User newUser = new User();
        Authority authority = authorityRepository.findOne("ROLE_USER");
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setEmail(email);
        newUser.setLocation(location);
        newUser.setCategory(category);
        newUser.setDescription(description);
        newUser.setRaisonSociale(raisonSociale);
        newUser.setPersonContactInformation(personContactInformation);
        newUser.setCompanyContactInformation(companyContactInformation);
        newUser.setCompetencies(competencies);
        newUser.setSectors(sectors);
        newUser.setFields(fields);
        newUser.setLangKey(langKey);
        // new user is not active
        System.out.println(newUser.getLogin());
        System.out.println(newUser);
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    /* TODO : Mettre à jour les réseaux sociaux */
    public void updateUserInformation(String email, CategoryEnum category,
        String description, String raisonSociale, PersonContactInformation personContactInformation,
        CompanyContactInformation companyContactInformation, List<String> competencies, List<String> sectors,
        List<FieldEnum> fields) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentLogin()).ifPresent(u -> {
            u.setEmail(email);
            u.setCategory(category);
            u.setDescription(description);
            u.setRaisonSociale(raisonSociale);
            u.setPersonContactInformation(personContactInformation);
            u.setCompanyContactInformation(companyContactInformation);
            u.setCompetencies(competencies);
            u.setSectors(sectors);
            u.setFields(fields);
            userRepository.save(u);
            log.debug("Changed Information for User: {}", u);
        });
    }


    /**
     * Fonction permettant aux administrateurs de mettre à jour le profile des utilisateurs identifiés par le parametre String login
     */
    public void updateUserByAdminInformation(String login, String email, CategoryEnum category,
                                             String description, String raisonSociale, PersonContactInformation personContactInformation,
                                             CompanyContactInformation companyContactInformation, List<String> competencies, List<String> sectors,
                                             List<FieldEnum> fields) {
        userRepository.findOneByLogin(login).ifPresent(u -> {
            u.setEmail(email);
            u.setCategory(category);
            u.setDescription(description);
            u.setRaisonSociale(raisonSociale);
            u.setPersonContactInformation(personContactInformation);
            u.setCompanyContactInformation(companyContactInformation);
            u.setCompetencies(competencies);
            u.setSectors(sectors);
            u.setFields(fields);
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

    /**
     * Fonction créant une clé, cette clé permet à l'utilisateur de réinitialiser son mot de passe
     * @param loginOrEmail
     * @param baseUrl
     * @return
     */
    public ResponseEntity<?> sendresetKey(String loginOrEmail, String baseUrl) {
        String newPassword = UUID.randomUUID().toString();
        String tmpresetKey = UUID.randomUUID().toString();
        final String resetKey;
        /* Pour empecher le changement du mot de passe après plus d'un jour : */
        LocalDate date = new LocalDate();
        resetKey = tmpresetKey.concat(date.toString());

        Optional<User> opt = userRepository.findOneByLogin(loginOrEmail);
        if(opt.isPresent()){
            opt.map(u -> {
                u.setResetKey(resetKey);
                userRepository.save(u);
                mailService.sendNewPassWordEmail(u, baseUrl, newPassword, resetKey);
                log.debug("Password reset for User: {}", u);
                return new ResponseEntity<>(HttpStatus.OK);
            });
        }else{
            opt = userRepository.findOneByEmail(loginOrEmail);
            if(opt.isPresent()){
                opt.map(u -> {
                    u.setResetKey(resetKey);
                    userRepository.save(u);
                    mailService.sendNewPassWordEmail(u, baseUrl, newPassword, resetKey);
                    log.debug("Password reset for User: {}", u);
                    return new ResponseEntity<>(HttpStatus.OK);
                });
            }else{
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
       return new ResponseEntity<>(HttpStatus.OK);
    }



    public ResponseEntity<?> resetPassword(String resetKey, String newPassword) {
        Optional<User> opt = userRepository.findOneByResetKey(resetKey);
        String encryptedPassword  = passwordEncoder.encode(newPassword);
        if(opt.isPresent() && checkDate(resetKey)){
            opt.map(u -> {
                u.setPassword(encryptedPassword);
                u.setResetKey(null);
                userRepository.save(u);
                log.debug("Password changed for User: {}", u);
                return new ResponseEntity<>(HttpStatus.OK);
            });
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Fonction permettant de vérifier que la resetKey n'a pas été générée, il y a plus d'un jour
     * @param resetKey
     * @return
     */
    public boolean checkDate(String resetKey){
        /* La date est sous la fome YYYY-DD-MM -> 10 caractère */
        String resetDate = resetKey.substring(resetKey.length()-10, resetKey.length());
        LocalDate date = new LocalDate();
        return(resetDate.equals(date.toString()));
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
