package com.iCareapi.sms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class iCareService{

    // METHODS FOR SEND_STATUS 'TO THE SERVER' ACTIONS AND RESPONSE IN JSON FORMAT

    public String errorResponse(String errorMessage) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = getErrorResponse(errorMessage);

        jsonObject.put("Error",jsonArray);

        return jsonObject.toString();
    }
    private static JSONArray getErrorResponse(String error_message) throws JSONException {
        JSONArray reasonForError = new JSONArray();
        JSONObject reason_for_error = new JSONObject();

        reason_for_error.put("message", error_message);
        reasonForError.put(reason_for_error);
        return reasonForError;
    }

    public String sentResponse(String ID,String status_message) throws JSONException {
        JSONObject sentResponse = new JSONObject();
        JSONArray response = getSentResponse(ID,status_message);

        sentResponse.put("Event",response);

        return sentResponse.toString();
    }

    private JSONArray getSentResponse(String id, String statusMessage) throws JSONException {
        JSONArray event = new JSONArray();
        JSONObject status_event = new JSONObject();

        JSONArray statusArray = new JSONArray();
        JSONObject statusObject = new JSONObject();

        statusObject.put("id",id);
        statusObject.put("status",statusMessage);
        statusArray.put(statusObject);

        status_event.put("message",statusArray);
        event.put(status_event);
        return event;
    }

    public String queuedResponse(String server_ID,String status) throws JSONException {
        JSONArray queuedArray = new JSONArray();
        JSONObject queuedObject = new JSONObject();

        JSONArray eventQueued = new JSONArray();
        JSONObject objectQueued = new JSONObject();
        objectQueued.put("server_id",server_ID);
        objectQueued.put("status",status);
        eventQueued.put(objectQueued);

        queuedObject.put("event",eventQueued);
        queuedArray.put(queuedObject);
        return queuedArray.toString();
    }

    public String getParameterValue(Map<String, String[]> params, String paramName) {
        String[] values = params.get(paramName);
        return values != null && values.length > 0 ? values[0]:null;
    }


    private final RecipientRepository recipientRepository;
    private final SMSEntityRepository smsEntityRepository;

    private SMSEntity smsEntity;

    @Autowired
    public iCareService(RecipientRepository recipientRepository, SMSEntityRepository smsEntityRepository) {
        this.recipientRepository = recipientRepository;
        this.smsEntityRepository = smsEntityRepository;
    }

    public void processSMSRequest(SMSRequestPayload requestPayload) {
        // Extract recipients based on criteria from the payload
        Recipient patient = requestPayload.getPatient();
        Recipient provider = requestPayload.getProvider();
        Recipient user = requestPayload.getUser();

        //Extract the rest
        Criteria criteria = requestPayload.getCriteria();
        String status = requestPayload.getStatus();
        String message = requestPayload.getMessage();

        // Generate SMSEntity instance
        SMSEntity smsEntity = new SMSEntity(patient, provider, user, criteria, status, message);

        // Save the SMSEntity instance into the database
        saveSMSEntity(smsEntity);
    }

    private void saveSMSEntity(SMSEntity smsEntity) {
        smsEntityRepository.save(smsEntity);
    }

}
