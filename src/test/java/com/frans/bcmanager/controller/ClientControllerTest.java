package com.frans.bcmanager.controller;

import com.frans.bcmanager.model.Client;
import com.frans.bcmanager.service.ClientService;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static com.frans.bcmanager.testbuilder.ClientTestBuilder.client;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@WithMockUser
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ClientController.class)
public class ClientControllerTest {

    @Autowired
    private WebClient webClient;

    @MockBean
    private ClientService clientService;

    @Captor
    private ArgumentCaptor<Client> capturedClient, editedClient;

    private Client createdClient, otherClient;

    @BeforeEach
    public void setUp() {
        createdClient = client()
                .withId(1L)
                .build();

        otherClient = client()
                .withId(2L)
                .withFirstName("Jane")
                .build();
    }
@Test
    public void clients() throws Exception {
        given(clientService.findAll()).willReturn(newArrayList(createdClient, otherClient));

        HtmlPage page = webClient.getPage("/clients");
        HtmlTable table = page.getHtmlElementById("dataTable");

        assertThat(table.getRowCount()).isEqualTo(3);
        assertThat(table.getCellAt(1, 0).asText()).isEqualTo(createdClient.getLastName());
        assertThat(table.getCellAt(1, 1).asText()).isEqualTo(createdClient.getFirstName());
        assertThat(table.getCellAt(1, 2).asText()).isEqualTo(createdClient.getAddress().getStreet());
        assertThat(table.getCellAt(1, 4).asText()).isEqualTo(createdClient.getAddress().getCity());
        assertThat(table.getCellAt(1, 3).asText()).isEqualTo(createdClient.getAddress().getPostCode());
    }
    @Test
    public void createClient() throws Exception {
        given(clientService.save(capturedClient.capture())).willReturn(createdClient);
        given(clientService.find(1L)).willReturn(createdClient);

        HtmlPage page = webClient.getPage("/clients/create");
        HtmlForm form = page.getFormByName("createClient");
        form.getInputByName("lastName").setValueAttribute("Doe");
        form.getInputByName("firstName").setValueAttribute("John");
        form.getInputByName("address.street").setValueAttribute("street");
        form.getInputByName("address.postCode").setValueAttribute("12345");
        form.getInputByName("address.city").setValueAttribute("city");
        form.getInputByName("mail").setValueAttribute("mail@mail.com");
        form.getInputByName("phoneNumber").setValueAttribute("phoneNumber");
        form.getInputByName("taxNumber").setValueAttribute("taxNumber");

        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                                                              "submit");
        Page clientPage = button.click();

        assertThat(clientPage.getUrl().getPath()).isEqualTo("/clients/1");

        assertThat(capturedClient.getValue()).isEqualToIgnoringGivenFields(createdClient, "id");
    }

    @Test
    public void createClient_withValidationErrors_invalidMail() throws Exception {
        HtmlPage page = webClient.getPage("/clients/create");
        HtmlForm form = page.getFormByName("createClient");
        form.getInputByName("lastName").setValueAttribute("Doe");
        form.getInputByName("firstName").setValueAttribute("John");
        form.getInputByName("address.street").setValueAttribute("street");
        form.getInputByName("address.postCode").setValueAttribute("12345");
        form.getInputByName("address.city").setValueAttribute("city");
        form.getInputByName("mail").setValueAttribute("invalidMail");
        form.getInputByName("phoneNumber").setValueAttribute("phoneNumber");
        form.getInputByName("taxNumber").setValueAttribute("taxNumber");

        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                                                              "submit");

        button.click();

        verify(clientService, never()).save(Mockito.any(Client.class));
    }

    @Test
    public void showClient() throws Exception {
        given(clientService.find(1L)).willReturn(createdClient);
        HtmlPage page = webClient.getPage("/clients/1");
        HtmlTable table = page.getHtmlElementById("clientDetail");

        assertThat(table.getCellAt(0, 1).asText()).isEqualTo(createdClient.getLastName());
        assertThat(table.getCellAt(1, 1).asText()).isEqualTo(createdClient.getFirstName());
        assertThat(table.getCellAt(2, 1).asText()).isEqualTo(createdClient.getAddress().getStreet());
        assertThat(table.getCellAt(3, 1).asText()).isEqualTo(createdClient.getAddress().getPostCode());
        assertThat(table.getCellAt(4, 1).asText()).isEqualTo(createdClient.getAddress().getCity());
        assertThat(table.getCellAt(5, 1).asText()).isEqualTo(createdClient.getMail());
        assertThat(table.getCellAt(6, 1).asText()).isEqualTo(createdClient.getPhoneNumber());
        assertThat(table.getCellAt(7, 1).asText()).isEqualTo(createdClient.getTaxNumber());
    }

    @Test
    public void editClient() throws Exception {
        given(clientService.find(1L)).willReturn(createdClient);

        HtmlPage page = webClient.getPage("/clients/1/edit");
        HtmlForm form = page.getFormByName("editClient");
        form.getInputByName("firstName").setValueAttribute("Jane");
        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                                                              "submit");

        button.click();

        verify(clientService).save(editedClient.capture());
        assertThat(editedClient.getValue().getFirstName()).isEqualTo("Jane");
    }

    @Test
    public void editClient_withValidationErrors_invalidLastName() throws Exception {
        given(clientService.find(1)).willReturn(createdClient);

        HtmlPage page = webClient.getPage("/clients/1/edit");
        HtmlForm form = page.getFormByName("editClient");
        form.getInputByName("firstName").setValueAttribute("");
        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                                                              "submit");
        button.click();

        verify(clientService, never()).save(Mockito.any(Client.class));
    }

    @Test
    public void deleteClient() throws Exception {
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        given(clientService.find(1L)).willReturn(createdClient);
        HtmlPage page = webClient.getPage("/clients/1");
        HtmlPage deleteClientLink = page.getHtmlElementById("deleteClientLink").click();
        webClient.waitForBackgroundJavaScript(10000);

        HtmlForm form = deleteClientLink.getFormByName("deleteClient");
        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type", "submit");
        Page clientsPage = button.click();

        verify(clientService).delete(1L);
    }
}
