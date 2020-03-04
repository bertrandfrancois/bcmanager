package com.frans.bcmanager.controller;

import com.frans.bcmanager.model.Client;
import com.frans.bcmanager.model.Project;
import com.frans.bcmanager.service.ClientService;
import com.frans.bcmanager.service.ProjectService;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.format.DateTimeFormatter;

import static com.frans.bcmanager.testbuilder.ClientTestBuilder.client;
import static com.frans.bcmanager.testbuilder.ProjectTestBuilder.project;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WithMockUser
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest extends ControllerTest {

    @MockBean
    private ProjectService projectService;

    @MockBean
    private ClientService clientService;

    @Captor
    private ArgumentCaptor<Project> capturedProject;

    private Client client;

    private Project createdProject;

    @BeforeEach
    public void setUp() {
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        client = client().withId(1L).build();
        createdProject = project().withClient(client).withId(5L).build();
        given(clientService.find(1L)).willReturn(client);
        given(projectService.find(5L)).willReturn(createdProject);
    }
    @Test
    public void createProject() throws Exception {
        given(projectService.save(capturedProject.capture())).willReturn(createdProject);

        HtmlPage page = webClient.getPage("/clients/1/projects/create");
        HtmlForm form = page.getFormByName("createProject");
        form.getInputByName("description").setValueAttribute("description");
        form.getInputByName("address.street").setValueAttribute("street");
        form.getInputByName("address.postCode").setValueAttribute("12345");
        form.getInputByName("address.city").setValueAttribute("city");
        form.getInputByName("period.startDate").setValueAttribute("2017-01-01");
        form.getInputByName("period.endDate").setValueAttribute("2017-12-31");
        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                                                              "submit");
        Page detailPage = button.click();

        assertThat(capturedProject.getValue()).isEqualToIgnoringGivenFields(createdProject, "id", "address", "period");
        assertThat(capturedProject.getValue().getAddress()).isEqualToComparingFieldByField(createdProject.getAddress());
        assertThat(capturedProject.getValue().getPeriod()).isEqualToComparingFieldByField(createdProject.getPeriod());
        assertThat(detailPage.getUrl().getPath()).isEqualTo("/clients/1/projects/5");
    }
    @Test
    public void showProject() throws Exception {
        HtmlPage page = webClient.getPage("/clients/1/projects/5");

        HtmlTable table = page.getHtmlElementById("projectDetail");

        Assertions.assertThat(table.getCellAt(0, 1).asText()).isEqualTo(createdProject.getDescription());
        Assertions.assertThat(table.getCellAt(1, 1).asText()).isEqualTo(
                createdProject.getPeriod().getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Assertions.assertThat(table.getCellAt(2, 1).asText()).isEqualTo(
                createdProject.getPeriod().getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Assertions.assertThat(table.getCellAt(3, 1).asText()).isEqualTo(createdProject.getAddress().getStreet());
        Assertions.assertThat(table.getCellAt(4, 1).asText()).isEqualTo(createdProject.getAddress().getPostCode());
        Assertions.assertThat(table.getCellAt(5, 1).asText()).isEqualTo(createdProject.getAddress().getCity());
    }
    @Test
    public void editProject() throws Exception {
        HtmlPage page = webClient.getPage("/clients/1/projects/5/edit");
        HtmlForm form = page.getFormByName("editProject");
        form.getInputByName("description").setValueAttribute("editedDescription");
        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                                                              "submit");

        Page detailPage = button.click();

        verify(projectService).save(capturedProject.capture());
        assertThat(capturedProject.getValue().getDescription()).isEqualTo("editedDescription");
        assertThat(detailPage.getUrl().getPath()).isEqualTo("/clients/1/projects/5");
    }
}