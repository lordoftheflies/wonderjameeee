/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

import hu.cherubits.wonderjam.ChristeamServerApplicationTests;
import hu.cherubits.wonderjam.services.ContentManagementService;
import hu.cherubits.wonderjam.dal.AccountRepository;
import hu.cherubits.wonderjam.dal.ContainerContentRepository;
import hu.cherubits.wonderjam.dal.ContentRepository;
import hu.cherubits.wonderjam.dal.MailBoxRepository;
import hu.cherubits.wonderjam.dal.MessageRepository;
import hu.cherubits.wonderjam.dal.NetworkTreeRepository;
import hu.cherubits.wonderjam.entities.AccountEntity;
import hu.cherubits.wonderjam.entities.ContainerContentEntity;
import hu.cherubits.wonderjam.entities.ContentEntity;
import hu.cherubits.wonderjam.entities.ImageContentEntity;
import hu.cherubits.wonderjam.entities.MailBoxEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeType;
import hu.cherubits.wonderjam.entities.ReferenceContentEntity;
import hu.cherubits.wonderjam.entities.TextContentEntity;
import hu.cherubits.wonderjam.entities.VideoContentEntity;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author lordoftheflies
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContentManagementServiceTest extends ChristeamServerApplicationTests {

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private ContentManagementService contentManagementServiceMock;

//    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(),
//            Charset.forName("utf8"));

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContainerContentRepository containerContentRepository;

    @Autowired
    private NetworkTreeRepository networkRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MailBoxRepository mailBoxRepository;

    private UUID textContentId;
    private UUID videoContentId;
    private UUID linkContentId;
    private UUID imageContentId;
    private UUID linkedTextContentId;
    private UUID containerContentId;
    private UUID containerContentId2;
    private UUID duckReferenceId;
    private UUID libaFileId;

//    @Autowired
//    void setConverters(HttpMessageConverter<?>[] converters) {
//
//        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
//                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
//
//        Assert.assertNotNull("the JSON message converter must not be null",
//                this.mappingJackson2HttpMessageConverter);
//    }

////    @Before
//    public void setUp() throws Exception {
//        super.setup();
//
//        ContainerContentEntity contentContainer = new ContainerContentEntity();
//        contentContainer.setContent("test-container-content");
//        contentContainer.setTitle("test-container-title");
////        contentContainer.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER);
//        containerContentId = contentRepository.save(contentContainer).getId();
////
//        ContentEntity imageContent = new ImageContentEntity();
//        imageContent.setTitle("test-image-content");
//        imageContent.setContent("https://www.google.hu/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjGifff4qXLAhWFNpoKHX4XBssQjRwIBw&url=http%3A%2F%2Fwww.trademagazin.hu%2Fhirek-es-cikkek%2Fpiaci-hirek%2Fszeretjuk-a-kacsahust.html&psig=AFQjCNHKGebKo-p4MfAbQfxvxMKMgTF_jg&ust=1457137611971834");
//        imageContent.setParent(contentContainer);
////        imageContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE);
//        imageContentId = contentRepository.save(imageContent).getId();
//
//        ContentEntity videoContent = new VideoContentEntity();
//        videoContent.setTitle("test-video-content");
//        videoContent.setContent("gaVtx2wfTcE");
//        videoContent.setParent(contentContainer);
////        videoContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO);
//        videoContentId = contentRepository.save(videoContent).getId();
//
//        ContentEntity linkedContent = new TextContentEntity();
//        linkedContent.setTitle("test-linked-content");
////        linkedContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT);
//        linkedContent.setContent("We linked here!");
//        linkedContent.setParent(contentContainer);
//        linkedContent = contentRepository.save(linkedContent);
//        linkedTextContentId = linkedContent.getId();
//
//        ContentEntity linkContent = new ReferenceContentEntity();
//        linkContent.setTitle("test-link-content");
////        linkContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_LINK);
//        linkContent.setContent(linkedContent.getId().toString());
//        linkContentId = contentRepository.save(linkContent).getId();
//
//        ContentEntity textContent = new TextContentEntity();
//        textContent.setTitle("test-text-content");
////        textContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT);
//        textContent.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod , sunt in culpa qui officia deserunt mollit anim id est laborum.");
//        textContentId = contentRepository.save(textContent).getId();
//    }C

////    @After
//    public void tearDown() {
////        contentRepository.deleteAll();
//    }

    /**
     * Test of page method, of class ContentManagementService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testPage() throws Exception {
        System.out.println("Test /page");
//        PageDto dto = contentManagementServiceMock.page(containerContentId.toString());
//        mockMvc.perform(get("/page/" + containerContentId.toString()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(contentType))
//                .andExpect(jsonPath("$.id", is(containerContentId.toString())));
    }

    /**
     * Test of save method, of class ContentManagementService.
     */
//    @Test
    public void testSave() {
//        System.out.println("save");
//        PageDto pageDto = new PageDto();
//        pageDto.setTitle("test-save-page");
//        pageDto.setSections(Arrays.asList(new SectionDto[]{
//            new SectionDto("keyimg", ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE, "http://images2.fanpop.com/images/photos/7400000/asd-kimberley-walsh-7455554-1600-1200.jpg"),
//            new SectionDto("keylink", ViewConstants.CONTENT_MANAGEMENT_WIDGET_LINK, linkedTextContentId.toString()),
//            new SectionDto("keytext", ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT, "Lorem ipsum."),
//            new SectionDto("keyvideo", ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO, "C0WXgZdwC4c"),
//        }));
//        contentManagementServiceMock.save(pageDto);
//        fail("The test case is a prototype.");
    }

    @Test
    public void atestCleanUp() {
        messageRepository.deleteAll();
        contentRepository.deleteAll();
        containerContentRepository.deleteAll();
        accountRepo.deleteAll();
        mailBoxRepository.deleteAll();
        networkRepo.deleteAll();
    }

    private NetworkNodeEntity heglasNode;

    private NetworkNodeEntity balazspeczelyNode;

//    @Test
    public void btestNetwork() {

        AccountEntity heglas = new AccountEntity();
        heglas.setEmail("heglas11@gmail.com");
        heglas.setName("Hegedűs László");
        heglas.setPassword("qwe123");
        heglas.setPreferredLanguage("en");
        heglas = accountRepo.save(heglas);
        heglasNode = new NetworkNodeEntity();
        heglasNode.setActive(true);
        heglasNode.setCodes(1);
        heglas.setState(NetworkNodeType.ADMIN);
        heglasNode = networkRepo.save(heglasNode);
        heglas.setNode(heglasNode);
        heglas = accountRepo.save(heglas);
        MailBoxEntity heglasMb = new MailBoxEntity();
        heglasMb.setOwner(heglasNode);
        mailBoxRepository.save(heglasMb);

        AccountEntity balazspeczely = new AccountEntity();
        balazspeczely.setEmail("peczely.balazs@digitaldefense.hu");
        balazspeczely.setName("Péczely Balázs");
        balazspeczely.setPassword("qwe123");
        balazspeczely.setPreferredLanguage("hu");
        balazspeczely = accountRepo.save(balazspeczely);
        balazspeczelyNode = new NetworkNodeEntity();
        balazspeczelyNode.setActive(true);
        balazspeczelyNode.setCodes(2);
        balazspeczely.setState(NetworkNodeType.ADMIN);
        balazspeczelyNode = networkRepo.save(balazspeczelyNode);
        balazspeczely.setNode(balazspeczelyNode);
        balazspeczely = accountRepo.save(balazspeczely);
        MailBoxEntity balazspeczelyMb = new MailBoxEntity();
        balazspeczelyMb.setOwner(balazspeczelyNode);
        mailBoxRepository.save(balazspeczelyMb);

        AccountEntity cseszkupopoveszku = new AccountEntity();
        cseszkupopoveszku.setEmail("cseszku@popoveszku.com");
        cseszkupopoveszku.setName("Cseszku popoveszku");
        cseszkupopoveszku.setPassword("qwe123");
        cseszkupopoveszku.setPreferredLanguage("po");
        cseszkupopoveszku = accountRepo.save(cseszkupopoveszku);
        NetworkNodeEntity cseszkupopoveszkuNode = new NetworkNodeEntity();
        cseszkupopoveszku.setState(NetworkNodeType.USER);
        cseszkupopoveszkuNode.setActive(true);
        cseszkupopoveszkuNode.setCodes(3);
        cseszkupopoveszkuNode = networkRepo.save(cseszkupopoveszkuNode);
        cseszkupopoveszku.setNode(cseszkupopoveszkuNode);
        cseszkupopoveszku = accountRepo.save(cseszkupopoveszku);
        MailBoxEntity cseszkupopoveszkuMb = new MailBoxEntity();
        cseszkupopoveszkuMb.setOwner(cseszkupopoveszkuNode);
        mailBoxRepository.save(cseszkupopoveszkuMb);

        AccountEntity parazita = new AccountEntity();
        parazita.setEmail("zita.para@gmail.com");
        parazita.setName("Para Zita");
        parazita.setPassword("qwe123");
        parazita.setPreferredLanguage("hu");
        parazita = accountRepo.save(parazita);
        NetworkNodeEntity parazitaNode = new NetworkNodeEntity();
        parazitaNode.setActive(true);
        parazitaNode.setCodes(4);
        parazita.setState(NetworkNodeType.USER);
        parazitaNode = networkRepo.save(parazitaNode);
        parazita.setNode(parazitaNode);
        parazita = accountRepo.save(parazita);
        MailBoxEntity parazitaMb = new MailBoxEntity();
        parazitaMb.setOwner(parazitaNode);
        mailBoxRepository.save(parazitaMb);

        AccountEntity tesztelek = new AccountEntity();
        tesztelek.setEmail("teszt.elek@gmail.com");
        tesztelek.setName("Teszt Elek");
        tesztelek.setPassword("qwe123");
        tesztelek.setPreferredLanguage("hu");
        tesztelek = accountRepo.save(tesztelek);
        NetworkNodeEntity tesztelekNode = new NetworkNodeEntity();
        tesztelekNode.setActive(true);
        tesztelek.setState(NetworkNodeType.USER);
        tesztelekNode.setCodes(5);
        tesztelekNode = networkRepo.save(tesztelekNode);
        tesztelek.setNode(tesztelekNode);
        tesztelek = accountRepo.save(tesztelek);
        MailBoxEntity tesztelekMb = new MailBoxEntity();
        tesztelekMb.setOwner(tesztelekNode);
        mailBoxRepository.save(tesztelekMb);

        AccountEntity feriahegyrol = new AccountEntity();
        feriahegyrol.setEmail("feriahegyrol@gmail.com");
        feriahegyrol.setName("Ferdinand Highlander");
        feriahegyrol.setPassword("qwe123");
        feriahegyrol = accountRepo.save(feriahegyrol);
        feriahegyrol.setPreferredLanguage("en");
        NetworkNodeEntity feriahegyrolNode = new NetworkNodeEntity();
        feriahegyrolNode.setActive(true);
        feriahegyrol.setState(NetworkNodeType.USER);
        feriahegyrolNode.setCodes(6);
        feriahegyrolNode = networkRepo.save(feriahegyrolNode);
        feriahegyrol.setNode(feriahegyrolNode);
        feriahegyrol = accountRepo.save(feriahegyrol);
        MailBoxEntity feriahegyrolMb = new MailBoxEntity();
        feriahegyrolMb.setOwner(feriahegyrolNode);
        mailBoxRepository.save(feriahegyrolMb);

        AccountEntity romanok = new AccountEntity();
        romanok.setName("Románia");
        romanok = accountRepo.save(romanok);
        NetworkNodeEntity romanokNode = new NetworkNodeEntity();
        romanokNode.setActive(true);
        romanokNode.setCodes(7);
        romanok.setState(NetworkNodeType.GROUP);
        romanokNode = networkRepo.save(romanokNode);
        romanok.setNode(romanokNode);
        romanok = accountRepo.save(romanok);

        AccountEntity gorogok = new AccountEntity();
        gorogok.setName("Görögország");
        gorogok = accountRepo.save(gorogok);
        NetworkNodeEntity gorogokNode = new NetworkNodeEntity();
        gorogokNode.setActive(true);
        gorogokNode.setCodes(8);
        gorogokNode = networkRepo.save(gorogokNode);
        gorogok.setNode(gorogokNode);
        gorogok.setState(NetworkNodeType.GROUP);
        gorogok = accountRepo.save(gorogok);

        AccountEntity magyarok = new AccountEntity();
        magyarok.setName("Magyarország");
        magyarok = accountRepo.save(magyarok);
        NetworkNodeEntity magyarokNode = new NetworkNodeEntity();
        magyarokNode.setActive(true);
        magyarokNode.setCodes(9);
        magyarok.setState(NetworkNodeType.GROUP);
        magyarokNode = networkRepo.save(magyarokNode);
        magyarok.setNode(magyarokNode);
        magyarok = accountRepo.save(magyarok);

        magyarokNode.setParent(heglasNode);
        networkRepo.save(magyarokNode);
        balazspeczelyNode.setParent(heglasNode);
        networkRepo.save(balazspeczelyNode);
        romanokNode.setParent(heglasNode);
        networkRepo.save(romanokNode);
        cseszkupopoveszkuNode.setParent(romanokNode);
        networkRepo.save(cseszkupopoveszkuNode);
        gorogokNode.setParent(balazspeczelyNode);
        networkRepo.save(gorogokNode);
        tesztelekNode.setParent(balazspeczelyNode);
        networkRepo.save(tesztelekNode);
        feriahegyrolNode.setParent(balazspeczelyNode);
        networkRepo.save(feriahegyrolNode);
        parazitaNode.setParent(balazspeczelyNode);
        networkRepo.save(parazitaNode);

    }

    /**
     * Test of publish method, of class ContentManagementService.
     */
//    @Test
    public void ctestCustomArticle() throws Exception {
        System.out.println("publish");
//        fail("The test case is a prototype.");

        ContainerContentEntity contentContainer = new ContainerContentEntity();
        contentContainer.setTitle("Cikk a kacsákról");
//        contentContainer.setLeaf(true);
//        contentContainer.setOrderIndex(0);
//        contentContainer.setHasEmbeddedFile(false);
        contentContainer.setNode(heglasNode);
        contentContainer.setDraft(true);
//        contentContainer.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER);
        containerContentId = containerContentRepository.save(contentContainer).getId();

        ContentEntity textContentOne = new TextContentEntity();
        textContentOne.setTitle("Mi a kacsa?");
        textContentOne.setOrderIndex(3);
        textContentOne.setParent(contentContainer);
//        textContentOne.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT);
        textContentOne.setContent("A házikacsa vagy röviden kacsa (Anas platyrhynchos domestica) a récefélék családjába tartozó baromfi, a tőkés réce (\"vadkacsa\") alfaja, háziasított változata.");
        textContentId = contentRepository.save(textContentOne).getId();
//
        ContentEntity imageContent = new ImageContentEntity();
        imageContent.setTitle("Így néz ki egy kacsa");
        imageContent.setOrderIndex(1);
        imageContent.setContent("http://www.vicclap.hu/static/media/201002/pic87066.jpg");
        imageContent.setParent(contentContainer);
//        imageContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE);
        imageContentId = contentRepository.save(imageContent).getId();

        ContentEntity textContentTwo = new TextContentEntity();
        textContentTwo.setTitle("Magyar kacsa");
        textContentTwo.setParent(contentContainer);
        textContentTwo.setOrderIndex(2);
//        textContentTwo.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT);
        textContentTwo.setContent("A 20. század elején a magyar parlagi kacsát már ősi magyar fajtaként említik, de származásáról biztos adataink ma sincsenek. A vízközeli falusi, tanyasi gazdaságok egyik legfontosabb baromfiféléje volt. Míg a gazdaasszony a libát eladásra nevelte, addig a kacsahús a család ellátására szolgált. A tájegységeknek kialakultak a saját típusaik. A leggyakoribb a fehér szín volt, amelyet a 20. század elején a pekingi kacsával akartak nemesíteni. A \"színes\", vagyis a tarka, vadas színű kacsa kisebb rangúnak számított, pedig ez a fajtaváltozat őrizte meg leginkább a magyar kacsa ősi formáját. Állománya erősen lecsökkent, Erdélyben és az alföldi tanyákon találhatóak kisebb állományai. Jelenleg a gödöllői Kisállattenyésztési Kutatóintézetben mindkét változatát tenyésztik. Nagyobb állománya található Szarvason is. 2004 óta a magyar kacsa a védett háziállataink közé tartozik.");
        textContentId = contentRepository.save(textContentTwo).getId();

        ContentEntity videoContent = new VideoContentEntity();
        videoContent.setTitle("Kacsatánc");
        videoContent.setContent("4KRw9sepREM");
        videoContent.setParent(contentContainer);
        videoContent.setOrderIndex(0);
//        videoContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO);
        videoContentId = contentRepository.save(videoContent).getId();

        ContainerContentEntity contentContainer2 = new ContainerContentEntity();
        contentContainer2.setTitle("Házi baromfik");
//        contentContainer2.setLeaf(false);
//        contentContainer2.setOrderIndex(1);
        contentContainer.setNode(balazspeczelyNode);
        contentContainer.setDraft(false);
//        contentContainer2.setHasEmbeddedFile(false);
//        contentContainer.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER);
        containerContentId2 = containerContentRepository.save(contentContainer2).getId();

        ContentEntity duckReference = new ReferenceContentEntity();
        duckReference.setTitle("Kacsa");
        duckReference.setOrderIndex(1);
        duckReference.setParent(contentContainer2);
//        textContentOne.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT);
        duckReference.setContent(contentContainer.getId().toString());
        duckReferenceId = contentRepository.save(duckReference).getId();
//
        ContainerContentEntity libaFile = new ContainerContentEntity();
        libaFile.setTitle("Liba");
        libaFile.setEmbeddedFile("pirosfekete.pdf");
//        libaFile.setHasEmbeddedFile(true);
//        libaFile.setLeaf(true);
//        libaFile.setOrderIndex(0);
        libaFile.setParent(contentContainer2);
//        imageContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE);
        libaFileId = containerContentRepository.save(libaFile).getId();
    }

}
