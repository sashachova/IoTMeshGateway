#include "NetworkingPlugin.h"
#include <iostream>

NetworkingPlugin::NetworkingPlugin(){
    Poco::Logger& logger = Poco::Logger::get("NetworkingPlugin");
    this->pluginDetails.type = _PD_T_DEVICE;
    this->pluginDetails.apiVersion = UCL_PLUGINS_API_VERSION;
    this->pluginDetails.className = "NetworkingPlugin";
    this->pluginDetails.pluginName ="Networking Plugin";
    this->pluginDetails.pluginVersion = "0.0.1";
    logger.debug("Plugin Created");
}

NetworkingPlugin::~NetworkingPlugin(){
    Poco::Logger& logger = Poco::Logger::get("NetworkingPlugin");
    if( busClient != NULL ) {
        delete busClient;
        busClient = NULL;
    }
    logger.debug("Plugin Removed");
}

int NetworkingPlugin::startPlugin(){
    Poco::Logger& logger = Poco::Logger::get("NetworkingPlugin");
    if(this->busClient!=NULL){
        this->busClient->init();
        this->busClient->connect_async();
        logger.debug("Started");
        return 0;
    } else {
        logger.error("No IBus Client found: can't start", __FILE__, 26);
        return -1;
    }
}

int NetworkingPlugin::setIBusClient(InnerBusClientIF* client){
    Poco::Logger& logger = Poco::Logger::get("NetworkingPlugin");
    this->busClient = client;
    this->busClient->setListener(this);
    this->busClient->getInfo();

    return 0;
}

int NetworkingPlugin::setWorkDir(std::string path){
    return 0;
}

int NetworkingPlugin::executeCommand(std::string source, IBMessage message){
    Poco::Logger& logger = Poco::Logger::get("NetworkingPlugin");
    IBPayload payload = message.getPayload();

    logger.debug("executeCommand for {%s} msg{%s}", source, payload.toJSON());

    logger.debug("\"%s : %s : %s : %d\"", message.getId(), payload.toJSON(), message.getReference(), (int) message.getTimestamp());
    logger.debug("\"%s : %s : %s : %s\"", payload.getType(), payload.getValue(), payload.getCvalue(), payload.getContent());

    return 0;
}

int NetworkingPlugin::executeInternalCommand(std::string source, std::string message){
    Poco::Logger& logger = Poco::Logger::get("NetworkingPlugin");
    logger.debug("executeInternalCommand from {%s} msg{%s}", source, message);

    return 0;
}

int NetworkingPlugin::sendOccurrence(bool success, std::string cvalue, std::string content, std::string reference, std::string sender) {
      return 0;
}

int NetworkingPlugin::getCommandSet(){
    Poco::Logger& logger = Poco::Logger::get("NetworkingPlugin");
    logger.debug("getCommandSet");
    return 0;
}

int NetworkingPlugin::getCapabilitiesSet(){
    Poco::Logger& logger = Poco::Logger::get("NetworkingPlugin");
    logger.debug("getCapabilitiesSet");
    return 0;
}

PluginDetails* NetworkingPlugin::getPluginDetails(){
    Poco::Logger& logger = Poco::Logger::get("NetworkingPlugin");
    return &pluginDetails;
}

int NetworkingPlugin::stopPlugin(){
    Poco::Logger& logger = Poco::Logger::get("NetworkingPlugin");
    logger.debug("Stopped");
    return 0;
}
