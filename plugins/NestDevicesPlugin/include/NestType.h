#ifndef NEST_DEVICES_TYPE_H
#define NEST_DEVICES_TYPE_H

#include <Poco/Logger.h>

#define REST_NEST_HOST      "https://developer-api.nest.com/"
#define URL_DEVICES_PATH    "devices/"

#define CONFIG_CAPABILITIES_FLIENAME    "config_capabilities.xml"


class CapabilityInfo;

typedef std::map<long, CapabilityInfo> Capabilities;

typedef std::map<long, std::string> Properties;
typedef std::map<long, std::string>::iterator PropertiesIterator;

typedef std::map<std::string, Properties> Devices;
typedef std::map<std::string, Properties>::iterator DevicesIterator;

typedef std::vector<std::string> StringVector;


class CapabilityInfo {
private:
    std::string name;
    std::string type;
    std::string constrain_type;
    std::string constraints_json;
    std::string default_value;
    std::string description;
    std::string read_write;

public:
    std::string getName() { return this->name; }
    void setName(std::string name) { this->name = name; }
    std::string getType() { return this->type; }
    void setType(std::string type) { this->type = type; }
    std::string getConstrainType() { return this->constrain_type; }
    void setConstrainType(std::string constrain_type) { this->constrain_type = constrain_type; }
    std::string getConstraintsJson() { return this->constraints_json; }
    void setConstraintsJson(std::string constraints_json) { this->constraints_json = constraints_json; }
    std::string getDefaultValue() { return this->default_value; }
    void setDefaultValue(std::string default_value) { this->default_value = default_value; }
    std::string getDescription() { return this->description; }
    void setDescription(std::string description) { this->description = description; }
    std::string getReadWrite() { return this->read_write; }
    void setReadWrite(std::string read_write) { this->read_write = read_write; }

    void clear() {
        name.clear();
        type.clear();
        constrain_type.clear();
        constraints_json.clear();
        default_value.clear();
        description.clear();
        read_write.clear();
    }
};


class NestType{
private:
    std::string token;
    std::string devicesJson;

    Capabilities capabilities;
    StringVector serials;
    Devices devices;

    std::string work_dir;

    void requestSerialsList(std::string nestType);

    std::string requestStrDeviceProperty(std::string nestType, std::string serial, std::string propertyName);

    std::string parseStrDeviceProperty(std::string serial, std::string propertyName);

public:
    NestType(std::string token, std::string work_dir);
    virtual ~NestType();
    
    virtual void init() = 0;
    void init(std::string nestType);

    Devices getDevices();
    void setDevices(Devices devices);
    void addDevice(std::string serial, Properties properties);

    virtual void initCapabilities() = 0;
    void initCapabilities(std::string nestType);
    Capabilities getCapabilities();
    void setCapabilities(Capabilities capabilities);
    void addCapability(unsigned int id, CapabilityInfo capability);

    std::string getToken();
    void setToken(std::string token);

    virtual void setStrDeviceProperty(std::string serial, std::string propertyName, std::string propertyValue) = 0;
    void setStrDeviceProperty(std::string nestType, std::string serial, std::string propertyName, std::string propertyValue);

    std::string getCapabilityAttribute(void* xmlConfig, std::string nestType, std::string attribName, unsigned int index);

    std::string getWorkDir();
};

#endif // NEST_DEVICES_TYPE_H
