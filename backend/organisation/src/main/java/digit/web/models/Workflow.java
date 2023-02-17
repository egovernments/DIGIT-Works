package digit.web.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Workflow
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Workflow {
	@JsonProperty("action")
	@NotNull

	private String action = null;

	@JsonProperty("comment")

	private String comment = null;

	@JsonProperty("assignees")

	private List<String> assignees = null;

	public Workflow addAssigneesItem(String assigneesItem) {
		if (this.assignees == null) {
			this.assignees = new ArrayList<>();
		}
		this.assignees.add(assigneesItem);
		return this;
	}

}
