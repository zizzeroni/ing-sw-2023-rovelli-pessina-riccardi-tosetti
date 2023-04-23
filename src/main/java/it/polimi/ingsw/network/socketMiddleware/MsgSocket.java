package it.polimi.ingsw.network.socketMiddleware;

import it.polimi.ingsw.network.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MsgSocket<T> implements Serializable {
    private String senderID;
    private Action action;
    private List<T> params;

    public MsgSocket() {
        this.senderID=null;
        this.action=null;
        this.params=new ArrayList<>();
    }

    public MsgSocket(String senderID) {
        this.senderID = senderID;
        this.action=null;
        this.params=new ArrayList<>();
    }

    public MsgSocket(String senderID, Action action) {
        this.senderID = senderID;
        this.action = action;
        this.params=new ArrayList<>();
    }

    public MsgSocket(String senderID, Action action, List<T> params) {
        this.senderID = senderID;
        this.action = action;
        this.params = params;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public List<T> getParams() {
        return params;
    }

    public void setParams(List<T> params) {
        this.params = params;
    }

    public void addParam(T param) {
        this.params.add(param);
    }

    public void removeParam(T param) {
        this.params.remove(param);
    }

    @Override
    public String toString() {
        return "From: "+this.senderID+";Action: "+this.action + ";Params: "+(params!=null ? params : "None");
    }
}
