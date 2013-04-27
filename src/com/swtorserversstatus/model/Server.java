package com.swtorserversstatus.model;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 17/12/11
 * Time: 16:45
 * Purpose:
 */
public class Server {

    private String  serverStatus;
    private String serverName;
    private int serverPopulation;
    private String serverType;
    private String serverTimezone;
    private String serverLanguage;

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerPopulation() {
        String population=null;

       if(this.serverPopulation==1){population="Light";};
       if(this.serverPopulation==2){population="Standard";};
       if(this.serverPopulation==3){population="Heavy";};
       if(this.serverPopulation==4){population="Very Heavy";};
       if(this.serverPopulation==5){population="Full";};

        return population;
    }

    public void setServerPopulation(int serverPopulation) {
        this.serverPopulation = serverPopulation;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getServerTimezone() {
        return serverTimezone;
    }

    public void setServerTimezone(String serverTimezone) {
        this.serverTimezone = serverTimezone;
    }

    public String getServerLanguage() {
        return serverLanguage;
    }

    public void setServerLanguage(String serverLanguage) {
        this.serverLanguage = serverLanguage;
    }

    public String toString()
    {
        return getServerName();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Server)) return false;

        Server server = (Server) o;

        if (serverName != null ? !serverName.equals(server.serverName) : server.serverName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return serverName != null ? serverName.hashCode() : 0;
    }


}
