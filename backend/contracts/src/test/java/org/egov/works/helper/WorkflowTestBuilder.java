package org.egov.works.helper;


import org.egov.works.web.models.Workflow;

import java.util.ArrayList;
import java.util.List;

public class WorkflowTestBuilder {
    private Workflow.WorkflowBuilder builder;

    public WorkflowTestBuilder() {
        this.builder = Workflow.builder();
    }

    public Workflow build() {
        return this.builder.build();
    }

    public static WorkflowTestBuilder builder() {
        return new WorkflowTestBuilder();
    }

    public WorkflowTestBuilder withWorkflow() {
        List<String> assignees = new ArrayList<>();
        assignees.add("some-assignees");

        this.builder.action("some-action")
                .assignees(assignees);
        return this;

    }
}
