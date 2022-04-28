package io.weidongxu.util.releaseautomation;

public class Configure {

    // the service name at https://github.com/Azure/azure-rest-api-specs/tree/main/specification
    private String swagger;

    // preview release
    private boolean preview;

    // used only if preview=false
    // if preview=true, uses current next release version in https://github.com/Azure/azure-sdk-for-java/blob/main/eng/versioning/version_client.txt
    private String version;

    // tag in readme.md
    // prompt in stdin, if not provided
    private String tag;

    // the service name at https://github.com/Azure/azure-sdk-for-java/tree/main/sdk
    // same as swagger, if not provided
    private String service;

    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

    public boolean isPreview() {
        return preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
