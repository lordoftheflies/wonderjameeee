/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

import hu.cherubits.wonderjam.ChristeamServerApplicationTests;
import hu.cherubits.wonderjam.dal.LocaleRepository;
import hu.cherubits.wonderjam.dal.ResourceRepository;
import hu.cherubits.wonderjam.entities.LocaleEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author lordoftheflies
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLocalization  extends ChristeamServerApplicationTests {

    @Autowired
    private LocaleRepository localeDao;
    
    @Autowired
    private ResourceRepository resourceDao;
    
    @Before
    public void setUp() throws Exception {
        
    }
    
    @After
    public void tearDown() throws Exception {
        
    }
    
    @Test
    public void test() {
        LocaleEntity englishLocale = new LocaleEntity();
        englishLocale.setDisplayName("English");
        englishLocale.setLanguageCode("en");
        localeDao.save(englishLocale);
        
        LocaleEntity polishLocale = new LocaleEntity();
        polishLocale.setDisplayName("Polskie");
        polishLocale.setLanguageCode("po");
        localeDao.save(polishLocale);
        
        LocaleEntity hungarianLocale = new LocaleEntity();
        hungarianLocale.setDisplayName("Magyar");
        hungarianLocale.setLanguageCode("hu");
        localeDao.save(hungarianLocale);
    }
}
