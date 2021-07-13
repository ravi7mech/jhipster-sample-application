package com.apptium.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.apptium.domain.QuestionVotes.class.getName());
            createCache(cm, com.apptium.domain.AnswerVotes.class.getName());
            createCache(cm, com.apptium.domain.Student.class.getName());
            createCache(cm, com.apptium.domain.Student.class.getName() + ".questionVotes");
            createCache(cm, com.apptium.domain.Student.class.getName() + ".answerVotes");
            createCache(cm, com.apptium.domain.Teacher.class.getName());
            createCache(cm, com.apptium.domain.Teacher.class.getName() + ".questionVotes");
            createCache(cm, com.apptium.domain.Teacher.class.getName() + ".answerVotes");
            createCache(cm, com.apptium.domain.Question.class.getName());
            createCache(cm, com.apptium.domain.Question.class.getName() + ".questionVotes");
            createCache(cm, com.apptium.domain.Answer.class.getName());
            createCache(cm, com.apptium.domain.Answer.class.getName() + ".answerVotes");
            createCache(cm, com.apptium.domain.Customer.class.getName());
            createCache(cm, com.apptium.domain.Customer.class.getName() + ".custContacts");
            createCache(cm, com.apptium.domain.Customer.class.getName() + ".custStatistics");
            createCache(cm, com.apptium.domain.Customer.class.getName() + ".custChars");
            createCache(cm, com.apptium.domain.Customer.class.getName() + ".custCommunicationRefs");
            createCache(cm, com.apptium.domain.Customer.class.getName() + ".custPasswordChars");
            createCache(cm, com.apptium.domain.Customer.class.getName() + ".custNewsLetterConfigs");
            createCache(cm, com.apptium.domain.Customer.class.getName() + ".custSecurityChars");
            createCache(cm, com.apptium.domain.Customer.class.getName() + ".custRelParties");
            createCache(cm, com.apptium.domain.Customer.class.getName() + ".custISVRefs");
            createCache(cm, com.apptium.domain.Customer.class.getName() + ".shoppingSessionRefs");
            createCache(cm, com.apptium.domain.CustCommunicationRef.class.getName());
            createCache(cm, com.apptium.domain.CustChar.class.getName());
            createCache(cm, com.apptium.domain.CustSecurityChar.class.getName());
            createCache(cm, com.apptium.domain.CustPasswordChar.class.getName());
            createCache(cm, com.apptium.domain.CustBillingAcc.class.getName());
            createCache(cm, com.apptium.domain.CustCreditProfile.class.getName());
            createCache(cm, com.apptium.domain.CustPaymentMethod.class.getName());
            createCache(cm, com.apptium.domain.CustPaymentMethod.class.getName() + ".bankCardTypes");
            createCache(cm, com.apptium.domain.CustContact.class.getName());
            createCache(cm, com.apptium.domain.CustContact.class.getName() + ".geographicSiteRefs");
            createCache(cm, com.apptium.domain.CustContact.class.getName() + ".custContactChars");
            createCache(cm, com.apptium.domain.CustContactChar.class.getName());
            createCache(cm, com.apptium.domain.GeographicSiteRef.class.getName());
            createCache(cm, com.apptium.domain.CustISVRef.class.getName());
            createCache(cm, com.apptium.domain.CustISVRef.class.getName() + ".custISVChars");
            createCache(cm, com.apptium.domain.CustISVChar.class.getName());
            createCache(cm, com.apptium.domain.RoleTypeRef.class.getName());
            createCache(cm, com.apptium.domain.RoleTypeRef.class.getName() + ".custRelParties");
            createCache(cm, com.apptium.domain.CustRelParty.class.getName());
            createCache(cm, com.apptium.domain.ShoppingSessionRef.class.getName());
            createCache(cm, com.apptium.domain.Individual.class.getName());
            createCache(cm, com.apptium.domain.Individual.class.getName() + ".indContacts");
            createCache(cm, com.apptium.domain.Individual.class.getName() + ".indChars");
            createCache(cm, com.apptium.domain.Individual.class.getName() + ".indAuditTrials");
            createCache(cm, com.apptium.domain.Individual.class.getName() + ".custRelParties");
            createCache(cm, com.apptium.domain.Individual.class.getName() + ".shoppingSessionRefs");
            createCache(cm, com.apptium.domain.IndAuditTrial.class.getName());
            createCache(cm, com.apptium.domain.IndContact.class.getName());
            createCache(cm, com.apptium.domain.IndContact.class.getName() + ".indContactChars");
            createCache(cm, com.apptium.domain.IndContactChar.class.getName());
            createCache(cm, com.apptium.domain.IndChar.class.getName());
            createCache(cm, com.apptium.domain.Department.class.getName());
            createCache(cm, com.apptium.domain.Department.class.getName() + ".custRelParties");
            createCache(cm, com.apptium.domain.IndActivation.class.getName());
            createCache(cm, com.apptium.domain.NewsLetterType.class.getName());
            createCache(cm, com.apptium.domain.NewsLetterType.class.getName() + ".custNewsLetterConfigs");
            createCache(cm, com.apptium.domain.NewsLetterType.class.getName() + ".indNewsLetterConfs");
            createCache(cm, com.apptium.domain.Industry.class.getName());
            createCache(cm, com.apptium.domain.CustNewsLetterConfig.class.getName());
            createCache(cm, com.apptium.domain.IndNewsLetterConf.class.getName());
            createCache(cm, com.apptium.domain.AutoPay.class.getName());
            createCache(cm, com.apptium.domain.Eligibility.class.getName());
            createCache(cm, com.apptium.domain.CreditCheckVerification.class.getName());
            createCache(cm, com.apptium.domain.BankCardType.class.getName());
            createCache(cm, com.apptium.domain.CustStatistics.class.getName());
            createCache(cm, com.apptium.domain.CustBillingRef.class.getName());
            createCache(cm, com.apptium.domain.CustBillingRef.class.getName() + ".custPaymentMethods");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
