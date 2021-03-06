#ifndef FAKE_DEVICE_H
#define FAKE_DEVICE_H

#include <Poco/Logger.h>
#include "device.h"

class FakeDevice: public Device{
private:
    std::string name;
    std::string type;
    std::string serial;
    std::string description;
    std::string connectionInfo;
    Properties properties;
public:
    FakeDevice(){}
    FakeDevice(std::string type, std::string name, std::string serial, std::string description);
    virtual ~FakeDevice();

    std::string getName();
    std::string getType();
    std::string getSerial();
    std::string getDescription();
    std::string getConnectionInfo();
    void processDeviceCommand(std::string command);
    Properties getProperties();
    void connect();
    void setProperties(Properties properties);
    virtual void generateProperties() = 0;
};

#endif // FAKE_DEVICE_H
