package com.example.ammar.test.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelRead {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("response")
    @Expose
    private List<Response> response = null;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }


    public class Response {

        @SerializedName("p_id")
        @Expose
        private String pId;
        @SerializedName("p_name")
        @Expose
        private String pName;
        @SerializedName("p_quantity")
        @Expose
        private String pQuantity;

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public String getPQuantity() {
            return pQuantity;
        }

        public void setPQuantity(String pQuantity) {
            this.pQuantity = pQuantity;
        }

    }

}


