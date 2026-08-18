package org.egov.digit.expense.calculator.web.models.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A sign-off captured when a payment actor moves a bill through the PAYMENTS workflow,
 * carried onto the report so the voucher can show who signed and render their signature.
 * The image itself stays in filestore — only its id travels here.
 */
@Schema(description = "A sign-off captured against a bill, rendered into the payment voucher")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportSignature {

	/** Voucher slot for the "Payment advice prepared by" signature. */
	public static final String SLOT_PREPARED_BY = "PREPARED_BY";

	/** Voucher slot for the "Payment advice verified by" signature. */
	public static final String SLOT_VERIFIED_BY = "VERIFIED_BY";

	/** Voucher slot for the "Payment advice approved by" signature. */
	public static final String SLOT_APPROVED_BY = "APPROVED_BY";

	/**
	 * Which of the voucher's three sign-off slots this signature belongs in, resolved from
	 * the workflow action so both the excel and the pdf template place it consistently.
	 */
	@JsonProperty("slot")
	private String slot;

	@JsonProperty("printedName")
	private String printedName;

	@JsonProperty("fileStoreId")
	private String fileStoreId;

	/**
	 * The signature image as a base64 data uri, for the pdf template.
	 *
	 * pdf-service cannot fetch an image url out of the request payload — its image mapping takes
	 * either a url fixed in the template or one from an external api's response, never a payload
	 * path. A data uri is therefore the only way a per-bill signature reaches the pdf, and mustache
	 * substitutes it straight into the format config's image field.
	 *
	 * The template must reference this with triple braces, {{{...}}}: mustache escapes by default
	 * and base64 contains '/', which would be turned into &#x2F; and quietly break the image.
	 *
	 * Null when the image could not be fetched — the printed name still records the sign-off.
	 */
	@JsonProperty("imageDataUri")
	private String imageDataUri;

	/** Payment role the signature was made under, e.g. PAYMENT_APPROVER. */
	@JsonProperty("role")
	private String role;

	/** Workflow action the signature was captured for, e.g. PAYMENT_INITIATION. */
	@JsonProperty("action")
	private String action;

	@JsonProperty("signedTime")
	private Long signedTime;
}
