package com.iCareapi.sms;

import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Map;

@RestController
@RequestMapping("envayasms/outgoing")
public class iCareController {
    private final iCareService icareService;

    public iCareController(iCareService icareService) {
        this.icareService = icareService;
    }

    @CrossOrigin(origins = "http://localhost:82")
    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> handleRequest(HttpServletRequest request, @RequestBody SMSRequestPayload requestPayload) throws JSONException {
        Map<String, String[]> params = request.getParameterMap();

        String action = icareService.getParameterValue(params, "action");
        if (action == null) {
            return ResponseEntity.badRequest().build();
        } else if (iCareConfig.ACTION_OUTGOING.equals(action)){
            String event = icareService.getParameterValue(params, "event");
            if (iCareConfig.EVENT_SEND.equals(event)) {
                if (recipientsMatchCriteria(requestPayload)) {
                    icareService.processSMSRequest(requestPayload);

                    String sendStatus = icareService.getParameterValue(params, "send_status");
                    if (iCareConfig.ACTION_SEND_STATUS.equals(sendStatus)) {
                        String ID = icareService.getParameterValue(params, "id");
                        String status_message = icareService.getParameterValue(params, "status");
                        String server_ID = icareService.getParameterValue(params, "id");
                        String status = icareService.getParameterValue(params, "status");
                     //   String error = icareService.getParameterValue(params, "error");

                        if (iCareConfig.STATUS_SENT.equals(status_message)) {
                            String json = icareService.sentResponse(ID, status_message);
                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            return new ResponseEntity<>(json, headers, HttpStatus.OK);
                        } else if (iCareConfig.STATUS_QUEUED.equals(status)) {
                            String json = icareService.queuedResponse(server_ID, status);
                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            return new ResponseEntity<>(json, headers, HttpStatus.ACCEPTED);
                        }
                    }

                    return ResponseEntity.ok("SMS sent successfully.");
                } else {
                    return ResponseEntity.ok("Recipients do not match criteria.");
                }
            }
        }

        return ResponseEntity.ok("Action not recognized.");
    }

    public boolean recipientsMatchCriteria(SMSRequestPayload requestPayload) {
        Criteria criteria = requestPayload.getCriteria();
        Map<String, Object> criteriaMap = criteria.getCriteriaMap();

        if (criteriaMap.isEmpty()) {
            // If there are no criteria, consider all recipients as matching
            return true;
        }

        Recipient patient = requestPayload.getPatient();
        Recipient provider = requestPayload.getProvider();
        Recipient user = requestPayload.getUser();

        // Check each criterion to see if it matches
        for (Map.Entry<String, Object> criterion : criteriaMap.entrySet()) {
            String criterionName = criterion.getKey();
            Object criterionValue = criterion.getValue();

            // Check each recipient against the criterion
            boolean matchesPatient = patientMatchesCriterion(patient, criterionName, criterionValue);
            boolean matchesProvider = providerMatchesCriterion(provider, criterionName, criterionValue);
            boolean matchesUser = userMatchesCriterion(user, criterionName, criterionValue);

            // If any recipient matches the criterion, consider it a match
            if (matchesPatient || matchesProvider || matchesUser) {
                return true;
            }
        }

        // If none of the recipients match any criterion, consider it a non-match
        return false;
    }

    private boolean patientMatchesCriterion(Recipient patient, String criterionName, Object criterionValue) {
        // Using reflection to access fields or properties by name regardless of access modifier
        try {
            Field field = Recipient.class.getDeclaredField(criterionName);
            field.setAccessible(true);
            Object recipientValue = ((Field) field).get(patient);

            // Compare the retrieved value with the criterionValue
            return recipientValue.equals(criterionValue);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            // If an exception occurs (e.g., field doesn't exist), consider it a non-match
            return false;
        }
    }

    private boolean providerMatchesCriterion(Recipient provider, String criterionName, Object criterionValue) {
        // Using reflection to access fields or properties by name regardless of access modifier
        try {
            Field field = Recipient.class.getDeclaredField(criterionName);
            field.setAccessible(true);
            Object recipientValue = ((Field) field).get(provider);

            // Compare the retrieved value with the criterionValue
            return recipientValue.equals(criterionValue);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            // If an exception occurs (e.g., field doesn't exist), consider it a non-match
            return false;
        }
    }

    private boolean userMatchesCriterion(Recipient user, String criterionName, Object criterionValue) {
        // Using reflection to access fields or properties by name regardless of access modifier
        try {
            Field field = Recipient.class.getDeclaredField(criterionName);
            field.setAccessible(true);
            Object recipientValue = ((Field) field).get(user);

            // Compare the retrieved value with the criterionValue
            return recipientValue.equals(criterionValue);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            // If an exception occurs (e.g., field doesn't exist), consider it a non-match
            return false;
        }
    }

}


//    private boolean patientMatchesCriterion(Recipient patient, String criterionName, Object criterionValue) {
//        // Method assumes the criterionName corresponds to a recipient property
//        switch (criterionName) {
//            case "id":
//                // Checks if the recipient property "id" matches the criterionValue
//                return patient.getId().equals(criterionValue);
//            case "recipient":
//                // Checks if the recipient property "recipient" matches the criterionValue
//                return patient.getRecipient().equals(criterionValue);
//            case "message":
//                // Checks if the recipient property "message" matches the criterionValue
//                return patient.getMessage().equals(criterionValue);
//            default:
//                // If the criterionName doesn't match any recipient property, consider it a non-match
//                return false;
//        }
//    }
//
//    private boolean providerMatchesCriterion(Recipient provider, String criterionName, Object criterionValue) {
//        // Method assumes the criterionName corresponds to a recipient property
//        switch (criterionName) {
//            case "id":
//                // Checks if the recipient property "id" matches the criterionValue
//                return provider.getId().equals(criterionValue);
//            case "recipient":
//                // Checks if the recipient property "recipient" matches the criterionValue
//                return provider.getRecipient().equals(criterionValue);
//            case "message":
//                // Checks if the recipient property "message" matches the criterionValue
//                return provider.getMessage().equals(criterionValue);
//            default:
//                // If the criterionName doesn't match any recipient property, consider it a non-match
//                return false;
//        }
//    }
//
//    private boolean userMatchesCriterion(Recipient user, String criterionName, Object criterionValue) {
//        // Method assumes the criterionName corresponds to a recipient property
//        switch (criterionName) {
//            case "id":
//                // Checks if the recipient property "id" matches the criterionValue
//                return user.getId().equals(criterionValue);
//            case "recipient":
//                // Checks if the recipient property "recipient" matches the criterionValue
//                return user.getRecipient().equals(criterionValue);
//            case "message":
//                // Checks if the recipient property "message" matches the criterionValue
//                return user.getMessage().equals(criterionValue);
//            default:
//                // If the criterionName doesn't match any recipient property, consider it a non-match
//                return false;
//        }
//    }
//