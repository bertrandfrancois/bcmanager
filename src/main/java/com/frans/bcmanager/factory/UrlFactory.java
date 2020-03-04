package com.frans.bcmanager.factory;

import org.springframework.stereotype.Component;

@Component
public class UrlFactory {

    public String newEstimateDocumentLine(long clientId, long documentId) {
        return "/clients/" + clientId + "/estimates/" + documentId + "/addLine";
    }

    public String editEstimateDocumentLine(long clientId, long documentId, long documentLineId) {
        return "/clients/" + clientId + "/estimates/" + documentId + "/editLine/" + documentLineId;
    }

    public String newServiceInvoiceDocumentLine(long clientId, long documentId) {
        return "/clients/" + clientId + "/services/" + documentId + "/addLine";
    }

    public String editServiceInvoiceDocumentLine(long clientId, long documentId, long documentLineId) {
        return "/clients/" + clientId + "/services/" + documentId + "/editLine/" + documentLineId;
    }

    public String newProject(long clientId) {
        return "/clients/"+ clientId + "/projects/create";
    }

    public String editProject(long clientId, long projectId) {
        return "/clients/"+ clientId + "/projects/" + projectId +"/edit";
    }

    public String newProjectInvoiceDocumentLine(long clientId, long projectId, long documentId) {
        return "/clients/" + clientId + "/projects/" + projectId + "/documents/" + documentId + "/addLine";
    }

    public String editProjectInvoiceDocumentLine(long clientId, long projectId, long documentId, long documentLineId) {
        return "/clients/" + clientId + "/projects/" + projectId + "/documents/" + documentId + "/editLine/" + documentLineId;
    }
}
